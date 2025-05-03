package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
     * @param item the item to dispense
     */
    public void dispenseItem(Buyer buyer, Product item) {
        if (processTransaction(buyer, item) && inventory.contains(item) && item.getStock() > 0) {
            item.setStock(item.getStock() - 1);
            System.out.println(item.getName() + " has been dispensed");
        } else {
            System.out.println(item.getName() + " has not been dispensed due to Transaction failure or item unavailability");
        }
    }

    /**
     * Allows a user to select an item from the inventory by name
     * @param itemName The name of the product the user wants to select
     * @return The matching Product if found; otherwise, null
     */
    public Product selectItem(String itemName) {
       for (Product item : inventory) {
           if (item.getName().equalsIgnoreCase(itemName)) {
               return item;
           }
       }
       return null;
    }

    /**
     * Adds money to the current session for purchasing products
     * @param money The Money object representing the amount inserted by the user
     */
    public void addMoney(Money money) {
        currentSessionMoney.add(money.getCashMap());
    }

    /**
     * Displays the list of available products in the inventory
     */
    public void showInventory() {
        inventory.forEach(item -> System.out.printf("%s - Stock: %d - Price: $%.2f%n",
                item.getName(), item.getStock(), item.getPrice()));
    }

    /**
     * Reloads or adds stock for an existing or new product with a specified price
     * @param item The product to be reloaded into inventory
     * @param amount The amount of item to add
     */
    public void reloadProduct(Product item, int amount) {
        int newStock = Math.min(item.getStock() + amount, item.getMaxCapacity());
        item.setStock(newStock);

        if (!inventory.contains(item)) {
            inventory.add(item);
        }
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
     * Processes a transaction by checking if the buyer has enough money to purchase the item
     * @param buyer The buyer attempting to purchase a product
     * @param item The product the buyer wants to purchase
     * @return true if the transaction is successful; false otherwise
     */
    @Override
    public boolean processTransaction(Buyer buyer, Product item) {
        double total = currentSessionMoney.calculateTotal();

        if (item.getStock() <= 0) {
            System.out.println("Item out of stock");
            return false;
        }

        if (total < item.getPrice()) {
            System.out.println("Insufficient funds");
            return false;
        }

        double change = total - item.getPrice();
        System.out.printf("Transaction successful. Change returned: $%.2f%n", change);
        currentSessionMoney.clear();
        return true;
    }

    /**
     * Reads and processes the profit sheet from a specified file
     * @param fileName The name of the file containing profit information
     */
    public void readProfitSheet(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            System.out.println("=== Profit Sheet ===");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read profit sheet from file: " + fileName);
        }
    }

    /**
     * Writes vending machine inventory and sales data to a file for record-keeping
     */
    public void writeToFile() {
        try (PrintWriter writer = new PrintWriter("Inventory_data.txt")) {
            for (Product item : inventory) {
                writer.println(item.getName() + "," + item.getPrice() + "," + item.getStock());
            }
            System.out.println("Inventory written to file.");
        } catch (FileNotFoundException e) {
            System.out.println("Could not write inventory to file. Please check the file path.");
        }
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
