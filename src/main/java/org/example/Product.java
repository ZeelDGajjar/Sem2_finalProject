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
     * Reloads the inventory of a product by increasing its stock
     * @param amount The number of units to add to the product's inventory
     */
    public void reload(int amount) {}

    /**
     * Buys a product, decreasing its stock by one
     */
    public void buy() {}

    /**
     * Compares this product to another based on their name
     * @param o the object to be compared.
     * @return an int value to show how two objects are related
     */
    @Override
    public int compareTo(Product o) {
        return 0;
    }
}
