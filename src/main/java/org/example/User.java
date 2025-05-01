package org.example;

public abstract class User implements MessageDisplay {
    private int id;
    private String name;

    private static int nextId = 0;

    public User(int id, String name) {
        this.id = nextId++;
        this.name = name;
    }

    public abstract void displayMessage(String message);
}
