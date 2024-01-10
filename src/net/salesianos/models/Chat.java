package net.salesianos.models;

import java.util.ArrayList;

public class Chat {
    private ArrayList<String> messages;


    public synchronized void addMessages(String message){
        messages.add(message);
    }

    public synchronized void showAllMessages(){
        messages.forEach(mgs -> System.out.println(mgs + "\n"));
    }
}
