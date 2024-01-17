package net.salesianos.models;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Chat {
    private ArrayList<String> messages = new ArrayList<>();


    public synchronized void addMessages(String message){
        messages.add(message);
    }

    public synchronized void showAllMessages(ObjectOutputStream objOutStream){

        if(!messages.isEmpty()) {
            messages.forEach(msg -> {
                try {
                    objOutStream.writeObject(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
