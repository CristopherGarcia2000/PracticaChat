package net.salesianos.client;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.models.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        final Scanner SCANNER = new Scanner(System.in);

        Socket socket = new Socket("localhost", 55000);
        ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());

        String message = "";

        System.out.println("Va a enviar datos de persona al servidor.");
        User user = new User();

        System.out.print("Introduzca nombre: ");
        user.setName(SCANNER.nextLine());

        ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream());
        ServerListener serverListener = new ServerListener(objInStream);
        serverListener.start();

        while (!(message.equals("bye"))) {
            System.out.print("Introduzca el mensaje: ");
            message = SCANNER.nextLine();
            System.out.println("1"+message);
            user.setMessage(message);
            System.out.println("2"+message);
            objOutStream.writeObject(user);
            objOutStream.reset();
        }

        SCANNER.close();
        objOutStream.close();
        socket.close();
    }

}
