package net.salesianos.client;

import net.salesianos.models.User;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        int userOption = 0;
        final Scanner SCANNER = new Scanner(System.in);

        System.out.println("¿Cómo te llamas?");
        String username = SCANNER.nextLine();

        Socket socket = new Socket("localhost", 55000);
        ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());

        objOutStream.writeUTF(username);

        String message = "";

        System.out.println("Va a enviar datos de persona al servidor.");
        User user = new User();

        System.out.print("Introduzca nombre: ");
        user.setName(SCANNER.nextLine());

        while (message != "bye") {
            System.out.print("Introduzca el mensaje: ");
            message = SCANNER.nextLine();
            user.setMessage(message);

            objOutStream.writeObject(user);
        }

        SCANNER.close();

        objOutStream.close();
        socket.close();
    }

}
