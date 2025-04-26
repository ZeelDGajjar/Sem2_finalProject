package org.example;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine implements TransactionHandler{
    private List<User> users;
    private List<Product> inventory;
    private Money currentSessionMoney;

    public VendingMachine() {
        users = new ArrayList<User>();
        inventory = new ArrayList<>();
        currentSessionMoney = new Money();
    }

    /**
     * Despenses selected item once the transaction is successfully completed
     * @param item
     */
    public void dispenseItem(Product item) {}

    /**
     * Allows a user to select an item from the inventory by name
     * @param name The name of the product the user wants to select
     * @return The matching Product if found; otherwise, null
     */
    public Product selectItem(String name) {
        return null;
    }

    /**
     * Adds money to the current session for purchasing products
     * @param money The Money object representing the amount inserted by the user
     */
    public void addMoney(Money money) {}

    /**
     * Displays the list pf available products in the inventory
     */
    public void showInventory() {}

    /**
     * Writes vending machine inventory and sales data to a file for record-keeping
     */
    public void writeToFile() {}

    /**
     * Reloads or adds stock for an existing or new product with a specified price
     * @param item The product to be reloaded into inventory
     * @param price The price to set for the reloaded product
     */
    public void reloadProduct(Product item, double price) {}

    /**
     * Changes the selling price of an existing product in the inventory
     * @param item The product whose price is to be updated
     * @param price The new price for the product
     */
    public void changePrice(Product item, double price) {

    }

    /**
     *  Reads and processes the profit sheet from a specified file
     * @param fileName The name of the file containing profit information
     */
    public void readProfitSheet(String fileName) {

    }

    /**
     * Processes a transaction by checking if the buyer has enough money to purchase the item
     * @param buyer The buyer attempting to purchase a product
     * @param item The product the buyer wants to purchase
     * @return true if the transaction is successful; false otherwise
     */
    @Override
    public boolean processTransaction(Buyer buyer, Product item) {
        return false;
    }
}
