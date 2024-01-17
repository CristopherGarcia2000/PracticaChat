package net.salesianos.server.threads;

import net.salesianos.models.Chat;
import net.salesianos.models.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientHandler extends Thread {
    private ObjectInputStream clientObjInStream;
    private ObjectOutputStream clientObjOutStream;
    private ArrayList<ObjectOutputStream> connectedObjOutputStreamList;
    private Chat chat;

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream,
                         ArrayList<ObjectOutputStream> connectedObjOutputStreamList, Chat chat) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
        this.chat = chat;
    }

    @Override
    public void run() {

        try {
            User userReceived = null;
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            chat.showAllMessages(clientObjOutStream);
            userReceived = (User) this.clientObjInStream.readObject();
            clientObjOutStream.writeObject("Te damos la bienvenida, "+userReceived.getName());

            while (true) {
                userReceived = (User) this.clientObjInStream.readObject();
                if (userReceived.getMessage().startsWith("msg:")) {

                    String message = dateFormat.format(date) + " " + userReceived.getName() + ": " + userReceived.getMessage()
                            .replace("msg:", "");
                    chat.addMessages(message);

                    for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
                        if (otherObjOutputStream != this.clientObjOutStream) {
                            otherObjOutputStream.writeObject(message);
                        }
                    }

                } else if (userReceived.getMessage().equals("bye")) {
                    clientObjOutStream.writeObject("Adiós usuario");
                    for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
                        otherObjOutputStream.writeObject(userReceived.getName()+" se ha desconectado.");
                    }
                } else {
                    clientObjOutStream.writeObject("Empieza el mensaje por \"msg:\"");
                }
            }


        } catch (EOFException eofException) {
            this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
            System.out.println("CERRANDO CONEXIÓN CON ");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

