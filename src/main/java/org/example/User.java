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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        User.nextId = nextId;
    }
}
