package org.example;

public class Snack extends Product {
    public int weightInGrams;

    public Snack(String name, double price, String category, int stock, int MaxCapacity) {
        super(name, price, category, stock, MaxCapacity);
    }
}
