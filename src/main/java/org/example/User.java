package org.example;

import java.util.Objects;

public abstract class User implements MessageDisplay {
    private int id;
    private String name;

    private static int nextId = 0;

    public User() {
        this.id = nextId++;
        this.name = "Unknown User";
    }

    public User(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public abstract void displayMessage(String message);

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public int getId() {
        return id;
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
}
