package org.example;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a general product in the vending machine system.
 * Provides basic functionality such as stock management, price comparison,
 * and label display. Meant to be extended by specific product types (e.g., Snack, Drink).
 */
public class Product implements Comparable<Product> {
    private String name;
    private double price;
    private String category;
    private int stock;
    private int maxCapacity;
    private String nutritionalInfo;
    private LocalDate expiryDate;

    public Product() {
        this.name = "Unnamed Product";
        this.price = 0;
        this.category = "Unknown";
        this.stock = 0;
        this.maxCapacity = 5;
        this.nutritionalInfo = "";
        this.expiryDate = null;
    }

    public Product(String name, double price, String category, int stock, int maxCapacity, String nutritionalInfo, LocalDate expiryDate) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.maxCapacity = maxCapacity;
        this.nutritionalInfo = nutritionalInfo;
        this.expiryDate = expiryDate;
    }

    /**
     * Increases the product's stock by the specified quantity.
     * Throws an exception if the quantity is invalid or exceeds max capacity.
     * @param quantity the number of units to add to stock
     * @throws IllegalArgumentException if quantity is non-positive or exceeds max capacity
     */
    public void restock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be positive.");
        }
        if (stock + quantity > maxCapacity) {
            throw new IllegalArgumentException("Cannot exceed maximum capacity.");
        }
        stock += quantity;
    }

    /**
     * Decreases the product's stock by the specified quantity.
     * Throws an exception if the quantity is invalid or exceeds current stock.
     * @param quantity the number of units to remove from stock
     * @throws IllegalArgumentException if quantity is non-positive or exceeds available stock
     */
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Reduction quantity must be positive.");
        }
        if (quantity > stock) {
            throw new IllegalArgumentException("Not enough stock to reduce.");
        }
        stock -= quantity;
    }

    /**
     * Returns a formatted label for the product, including name, price,
     * remaining stock, and nutritional information.
     * Can be overridden by subclasses to include more details.
     * @return a string label with product details
     */
    public String displayLabel() {
        return String.format("%s - $%.2f (%d left)\nNutritional Info: %s",
                name, price, stock, getNutritionalInfo());
    }

    /**
     * Checks whether the product is expired.
     * Products without an expiry date are assumed to be non-perishable.
     * @return true if expired, false otherwise
     */
    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }
        return expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Compares products first by price, then by name if prices are equal.
     * @param o the product to compare with
     * @return comparison result based on price and name
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
                ", nutritionalInfo='" + nutritionalInfo + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 &&
                stock == product.stock &&
                maxCapacity == product.maxCapacity &&
                Objects.equals(name, product.name) &&
                Objects.equals(category, product.category) &&
                Objects.equals(nutritionalInfo, product.nutritionalInfo) &&
                Objects.equals(expiryDate, product.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category, stock, maxCapacity, nutritionalInfo, expiryDate);
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

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(String nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
