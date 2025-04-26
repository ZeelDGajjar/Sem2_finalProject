package org.example;

import java.util.Objects;

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
     * Compares this product to another based on their price
     * @param o the object to be compared.
     * @return an int value to show how two objects are related
     */
    @Override
    public int compareTo(Product o) {
        return (int) (this.price - o.price)
                + this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", stock=" + stock +
                ", MaxCapacity=" + MaxCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 && stock == product.stock && MaxCapacity == product.MaxCapacity && Objects.equals(name, product.name) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category, stock, MaxCapacity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        MaxCapacity = maxCapacity;
    }
}
