package org.example;

import java.util.*;

public class Operator extends User {
    private String accessLevel;
    private List<String> profitSheets;
    private Map<Product, Integer> stockingHistory;

    public Operator() {
        super(getNextId() + 1, "");
        profitSheets = new ArrayList<String>();
        accessLevel = "";
        stockingHistory = new LinkedHashMap<>();
    }

    public Operator(int id, String name) {
        super(id, name);
        profitSheets = new ArrayList<String>();
        accessLevel = "";
        stockingHistory = new LinkedHashMap<>();
    }

    public Operator(int id, String name, String accessLevel, List<String> profitSheets) {
        super(id, name);
        this.accessLevel = accessLevel;
        this.profitSheets = profitSheets;
        stockingHistory = new LinkedHashMap<>();
    }

    /**
     * Restores a specified amount of the given product back into the vending machine inventory
     * @param item The product to be restored
     * @param amount The number of units to add to the inventory
     */
    public void restoreProduct(Product item, int amount) {}

    /**
     * Updates the selling price of a specific product in the vending machine
     * @param item The product whose price is being updates
     * @param price The new price to assign to the product
     */
    public void updateProductPrice(Product item, double price) {}

    /**
     * Reviews the contents of a profit sheet from a given file
     * @param fileName The name of the profit sheet file to be reviewed
     */
    public void reviewProfitSheet(String fileName) {}

    /**
     * Displays a custom message intended for the operator. e.g.: Successful restock into the system, etc..
     * @param message The message to be displayed
     */
    @Override
    public void displayMessage(String message) {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(accessLevel, operator.accessLevel) && Objects.equals(profitSheets, operator.profitSheets) && Objects.equals(stockingHistory, operator.stockingHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessLevel, profitSheets, stockingHistory);
    }

    @Override
    public String toString() {
        return "Operator{" + super.toString() +
                "accessLevel='" + accessLevel + '\'' +
                ", profitSheets=" + profitSheets +
                ", stockingHistory=" + stockingHistory +
                '}';
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public List<String> getProfitSheets() {
        return profitSheets;
    }

    public void setProfitSheets(List<String> profitSheets) {
        this.profitSheets = profitSheets;
    }

    public Map<Product, Integer> getStockingHistory() {
        return stockingHistory;
    }

    public void setStockingHistory(Map<Product, Integer> stockingHistory) {
        this.stockingHistory = stockingHistory;
    }
}
