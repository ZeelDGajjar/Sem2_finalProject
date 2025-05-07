package org.example;

import java.util.Objects;

public class Product implements Comparable<Product> {
    private String name;
    private double price;
    private String category;
    private int stock;
    private int maxCapacity;

    public Product(String name, double price, String category, int stock, int maxCapacity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.maxCapacity = maxCapacity;
    }

    public Product() {
        this.name = "Unnamed Product";
        this.price = 0;
        this.category = "Unknown";
        this.stock = 0;
        this.maxCapacity = 5;
    }

    /**
     * Reloads the stock, ensuring it does not exceed the maximum capacity.
     * @param amount The amount to add to the stock
     */
    public void reload(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to reload cannot be negative.");
        }
        stock = Math.min(stock + amount, maxCapacity);
    }

    /**
     * Finalizes the purchase of the product by reducing the stock by 1.
     * Throws an exception if the product is out of stock.
     */
    public void buy() {
        if (stock <= 0) {
            throw new IllegalStateException("Product out of stock.");
        }
        this.stock--;
        System.out.println("Product " + name + " purchased successfully.");
    }

    /**
     * Compares products first by price, then by name if prices are the same.
     */
    @Override
    public int compareTo(Product o) {
        int priceComparison = Double.compare(this.price, o.price);
        if (priceComparison != 0) {
            return priceComparison;
        }
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", stock=" + stock +
                ", maxCapacity=" + maxCapacity +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 && stock == product.stock && maxCapacity == product.maxCapacity && Objects.equals(name, product.name) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category, stock, maxCapacity);
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
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
