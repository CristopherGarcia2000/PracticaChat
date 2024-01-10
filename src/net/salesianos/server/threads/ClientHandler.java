package net.salesianos.server.threads;

import net.salesianos.models.Chat;
import net.salesianos.models.User;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private ObjectInputStream clientObjInStream;
    private ObjectOutputStream clientObjOutStream;
    private ArrayList<ObjectOutputStream> connectedObjOutputStreamList;
    private Chat chat = new Chat();

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream,
                         ArrayList<ObjectOutputStream> connectedObjOutputStreamList) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
    }

    @Override
    public void run() {

        try {
            User userReceived = null;
            while (true) {
                userReceived = (User) this.clientObjInStream.readObject();
                System.out.println(userReceived.getName() + " envía: " + userReceived.getMessage());
                chat.addMessages(userReceived.getMessage());
                for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
                    if (otherObjOutputStream != this.clientObjOutStream) {
                        otherObjOutputStream.writeObject(userReceived);
                    }
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

