package net.salesianos.client.threads;

import java.io.IOException;
import java.io.ObjectInputStream;

import net.salesianos.models.User;

public class ServerListener extends Thread {

    private ObjectInputStream objInStream;

    public ServerListener(ObjectInputStream socketObjectInputStream) {
        this.objInStream = socketObjectInputStream;
    }

    @Override
    public void run() {
        try {
            while (true) {
                User newServerPerson = (User) this.objInStream.readObject();
                System.out.println("El servidor registró a una nueva persona: " + newServerPerson.getName());
            }
        } catch (ClassNotFoundException e1) {
            System.out.println("No se ha encontrado la clase Person");
        } catch (IOException e2) {
            System.out.println("Se ha dejado de escuchar los envíos del servidor.");
        }
    }
}
