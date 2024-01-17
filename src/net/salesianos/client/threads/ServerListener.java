package net.salesianos.client.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import net.salesianos.models.User;

public class ServerListener extends Thread {

    private ObjectInputStream objInStream;

    public ServerListener(ObjectInputStream socketObjectInputStream) {
        this.objInStream = socketObjectInputStream;
    }

    @Override
    public void run() {
        try {
            String message ="";
            while (!message.equals("Adiós usuario")) {
                message = (String) this.objInStream.readObject();
                System.out.println(message);
            }
        } catch (IOException | ClassNotFoundException e2) {
            System.out.println("Se ha dejado de escuchar los envíos del servidor.");
        }
    }
}
