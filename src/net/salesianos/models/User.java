package net.salesianos.models;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String message;

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
