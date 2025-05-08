package org.example;

import java.nio.file.Path;
import java.util.*;

public class Operator extends User {
    private AccessLevel accessLevel;
    private List<Path> profitSheets;
    private Map<Product, Integer> stockingHistory;

    public Operator() {
        super("Unknown Operator");
        profitSheets = new ArrayList<>();
        accessLevel = AccessLevel.STAFF;
        stockingHistory = new LinkedHashMap<>();
    }

    public Operator(String name, AccessLevel accessLevel) {
        super(name);
        this.accessLevel = accessLevel;
        this.profitSheets = new ArrayList<>();
        stockingHistory = new LinkedHashMap<>();
    }

    public Operator(String name, AccessLevel accessLevel, List<Path> profitSheets) {
        super(name);
        this.accessLevel = accessLevel;
        this.profitSheets = profitSheets;
        stockingHistory = new LinkedHashMap<>();
    }

    /**
     * Restores a specified amount of the given product back into the vending machine inventory
     * @param item The product to be restored
     * @param amount The number of units to add to the inventory
     */
    public void restockProduct(Product item, int amount) {
        item.setStock(item.getStock() + amount);
        stockingHistory.put(item, amount);
    }

    /**
     * Updates the selling price of a specific product in the vending machine
     * @param item The product whose price is being updates
     * @param price The new price to assign to the product
     */
    public void updateProductPrice(Product item, double price) {
        if (this.accessLevel != AccessLevel.ADMIN) {
            displayMessage("Access denied");
            return;
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        item.setPrice(price);
    }

    /**
     * Reviews the contents of a profit sheet from a given file
     * @param vendingMachine The vending machine whose profit sheet needs to be reviewed
     */
    public void reviewProfitSheet(VendingMachine vendingMachine) {
        Path filename = Path.of("../resources/ProfitSheet.txt");
        vendingMachine.readProfitSheet(filename);
        profitSheets.add(filename);
    }

    /**
     * Displays a custom message intended for the operator. e.g.: Successful restock into the system, etc...
     * @param message The message to be displayed
     */
    @Override
    public void displayMessage(String message) {
        System.out.println("Please note:" + message);
    }

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

    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public List<Path> getProfitSheets() {
        return profitSheets;
    }

    public void setProfitSheets(List<Path> profitSheets) {
        this.profitSheets = profitSheets;
    }

    public Map<Product, Integer> getStockingHistory() {
        return stockingHistory;
    }

    public void setStockingHistory(Map<Product, Integer> stockingHistory) {
        this.stockingHistory = stockingHistory;
    }
}
