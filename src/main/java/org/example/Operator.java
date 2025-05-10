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
     * @param vendingMachine The vendingMachine in which to restock the product
     */
    public void restockProduct(Product item, int amount, VendingMachine vendingMachine) {
        if (item == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (vendingMachine == null) {
            throw new IllegalArgumentException("Vending machine cannot be null");
        }

        vendingMachine.reloadProduct(item, amount, this);
        stockingHistory.merge(item, amount, Integer::sum);

        displayMessage("Restocked " + amount + " units of " + item.getName() +
                ". Expiry Date: " + item.getExpiryDate());
    }

    /**
     * Updates the selling price of a specific product in the vending machine
     * @param item The product whose price is being updates
     * @param price The new price to assign to the product
     * @param vendingMachine The vendingMachine in which to restock the product
     */
    public void updateProductPrice(Product item, double price, VendingMachine vendingMachine) {
        if (price < 0) {
            System.out.println("Invalid price. Price cannot be negative.");
            return;
        }
        if (item == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (vendingMachine == null) {
            throw new IllegalArgumentException("Vending machine cannot be null");
        }
        vendingMachine.changePrice(item, price, this);
    }

    /**
     * Reviews the contents of a profit sheet from a given file
     * @param vendingMachine The vending machine whose profit sheet needs to be reviewed
     */
    public void reviewProfitSheet(VendingMachine vendingMachine) {
        Path filePath = Path.of("../resources/ProfitSheet.txt");

        if (vendingMachine == null) {
            throw new IllegalArgumentException("Vending machine cannot be null");
        }
        if (accessLevel != AccessLevel.ADMIN) {
            displayMessage("Access denied: Only ADMIN can review profit sheets");
            return;
        }

        vendingMachine.readProfitSheet(filePath);
        if (!profitSheets.contains(filePath)) {
            profitSheets.add(filePath);
        }
        displayMessage("Profit sheet at " + filePath + " reviewed");
    }

    /**
     * Displays a custom message intended for the operator. e.g.: Successful restock into the system, etc...
     * @param message The message to be displayed
     */
    @Override
    public void displayMessage(String message) {
        if (message != null) {
            System.out.println("Operator " + getName() + ", please note:" + message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;        // include User’s fields
        if (getClass() != o.getClass()) return false;
        Operator that = (Operator) o;
        return accessLevel == that.accessLevel
                && Objects.equals(profitSheets, that.profitSheets)
                && Objects.equals(stockingHistory, that.stockingHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accessLevel, profitSheets, stockingHistory);
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
