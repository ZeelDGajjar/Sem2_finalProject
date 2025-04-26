package org.example;

public class Drink extends Product{
    private int volumeInML;

    public Drink(String name, double price, String category, int stock, int MaxCapacity) {
        super(name, price, category, stock, MaxCapacity);
    }
}
