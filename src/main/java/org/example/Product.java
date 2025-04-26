package org.example;

public class Product implements Comparable<Product> {
    private String name;
    private double price;
    private String category;
    private int stock;
    private int MaxCapacity;

    public Product(String name, double price, String category, int stock, int MaxCapacity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.MaxCapacity = MaxCapacity;
    }

    /**
     *
     * @param amount
     */
    public void reload(int amount) {

    }

    /**
     *
     */
    public void buy() {

    }

    @Override
    public int compareTo(Product o) {
        return 0;
    }
}
