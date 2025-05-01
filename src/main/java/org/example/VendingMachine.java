package org.example;

import java.util.*;

public class VendingMachine implements TransactionHandler{
    private List<User> users;
    private List<Product> inventory;
    private Money currentSessionMoney;

    public VendingMachine() {
        users = new ArrayList<User>();
        inventory = new LinkedList<>();
        currentSessionMoney = new Money();
    }

    public VendingMachine(List<User> users, List<Product> inventory, Money currentSessionMoney) {
        this.users = users;
        this.inventory = inventory;
        this.currentSessionMoney = currentSessionMoney;
    }

    /**
     * Despenses selected item once the transaction is successfully completed
     * @param buyer the buyer doing the performance
     * @param item the to dispense
     */
    public void dispenseItem(Buyer buyer, Product item) {
        if (processTransaction(buyer, item) || inventory.contains(item)) {
            inventory.remove(item);
        }
    }

    /**
     * Allows a user to select an item from the inventory by name
     * @param itemName The name of the product the user wants to select
     * @return The matching Product if found; otherwise, null
     */
    public Product selectItem(String itemName) {
       if (inventory.contains(itemName)) {
           return inventory.get(inventory.indexOf(itemName));
       }
       return null;
    }

    /**
     * Adds money to the current session for purchasing products
     * @param money The Money object representing the amount inserted by the user
     */
    public void addMoney(Money money) {
        currentSessionMoney.add((Map<Double, Integer>) money);
    }

    /**
     * Displays the list of available products in the inventory
     */
    public void showInventory() {
        inventory.stream()
                .map(p -> Map.of(p.getName(), p.getStock()))
                .forEach(System.out::println);  // Make sure it actually prints both the name and the stock
    }

    /**
     * Writes vending machine inventory and sales data to a file for record-keeping
     */
    public void writeToFile() {}

    /**
     * Reloads or adds stock for an existing or new product with a specified price
     * @param item The product to be reloaded into inventory
     * @param amount The amount of item to add
     */
    public void reloadProduct(Product item, int amount) {
        if (amount <= item.getMaxCapacity()) {
            for (int i = 0; i < amount; i++) {
                inventory.add(item);
            }
        }
        item.setStock(item.getMaxCapacity());
    }

    /**
     * Changes the selling price of an existing product in the inventory
     * @param item The product whose price is to be updated
     * @param price The new price for the product
     */
    public void changePrice(Product item, double price) {
        item.setPrice(price);
    }

    /**
     *  Reads and processes the profit sheet from a specified file
     * @param fileName The name of the file containing profit information
     */
    public void readProfitSheet(String fileName) {}

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

    @Override
    public String toString() {
        return "VendingMachine{" +
                "users=" + users +
                ", inventory=" + inventory +
                ", currentSessionMoney=" + currentSessionMoney +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VendingMachine that = (VendingMachine) o;
        return Objects.equals(users, that.users) && Objects.equals(inventory, that.inventory) && Objects.equals(currentSessionMoney, that.currentSessionMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, inventory, currentSessionMoney);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public void setInventory(List<Product> inventory) {
        this.inventory = inventory;
    }

    public Money getCurrentSessionMoney() {
        return currentSessionMoney;
    }

    public void setCurrentSessionMoney(Money currentSessionMoney) {
        this.currentSessionMoney = currentSessionMoney;
    }
}
