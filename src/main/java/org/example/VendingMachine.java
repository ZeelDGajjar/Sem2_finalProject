package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

public class VendingMachine implements TransactionHandler{
    private List<User> users;
    private List<Product> inventory;
    private Money currentSessionMoney;
    private List<String> salesLog;

    public VendingMachine() {
        users = new ArrayList<>();
        inventory = new LinkedList<>();
        currentSessionMoney = new Money();
        salesLog = new ArrayList<>();
    }

    public VendingMachine(List<User> users, List<Product> inventory, Money currentSessionMoney) {
        this.users = users;
        this.inventory = inventory;
        this.currentSessionMoney = currentSessionMoney;
        salesLog = new ArrayList<>();
    }

    /**
     * Dispenses selected item once the transaction is successfully completed
     * @param buyer the buyer doing the performance
     * @param item the item to dispense
     */
    public void dispenseItem(Buyer buyer, Product item) {
        if (item != null && processTransaction(buyer, item) && selectItem(item) != null) {
            item.setStock(item.getStock() - 1);
            System.out.println(item.getName() + " has been dispensed");

            String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String log = String.format("%s - %s sold for $%.2f", time, item.getName(), item.getPrice());
            salesLog.add(log);
        } else {
            System.out.println("Selected item has not been dispensed due to Transaction failure.");
        }
    }

    /**
     * Allows a user to select an item from the inventory by name
     * @param product The name of the product the user wants to select
     * @return The matching Product if found; otherwise, null
     */
    public Product selectItem(Product product) {
       for (Product p : inventory) {
           if (p.getName().equals(p.getName()) && p.getStock() > 0) {
               return p;
           }
       }

       System.out.println("Product is out of stock");
       return null;
    }

    /**
     * Adds money to the current session for purchasing products
     * @param money The Money object representing the amount inserted by the user
     */
    public void addMoney(Money money) {
        if (money != null && money.calculateTotal() != 0) {
            currentSessionMoney.add(money.getCashMap());
        }
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
     * @param operator The operator who is reloading the product
     */
    public void reloadProduct(Product item, int amount, Operator operator) {
        if (amount <= 0) {
            System.out.println("Invalid amount to reload. Must be a positive integer.");
            return;
        }

        int newStock = Math.min(item.getStock() + amount, item.getMaxCapacity());
        item.setStock(newStock);

        if (!inventory.contains(item)) {
            inventory.add(item);
        }

        operator.getStockingHistory().put(item,amount);
        System.out.println("Product reloaded: " + item.getName() + " by " + amount + " units.");
    }

    /**
     * Changes the selling price of an existing product in the inventory
     * @param item The product whose price is to be updated
     * @param price The new price for the product
     */
    public void changePrice(Product item, double price, Operator operator) {
        if (price < 0) {
            System.out.println("Invalid price. Price cannot be negative.");
            return;
        }

        if (operator.getAccessLevel() == AccessLevel.ADMIN) {
            item.setPrice(price);
            System.out.println("Successfully changed price of " + item.getName() + ". New price set to: " + price + " dollars.");
            return;
        }
        System.out.println("You don't have access to perform this operation.");
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

        if (selectItem(item) == null) {
            return false;
        }

        if (total < item.getPrice()) {
            System.out.println("Insufficient funds");
            return false;
        }

        double change = total - item.getPrice();
        System.out.printf("Transaction successful. Change returned: $%.2f", change);
        currentSessionMoney.clear();
        return true;
    }

    /**
     * Reads and processes the profit sheet from a specified file
     * @param fileName The name of the file containing profit information
     */
    public static void readProfitSheet(Path fileName) {
        File file = fileName.toFile();
        if (!file.exists()) {
            System.out.println("Profit sheet file not found: " + fileName);
            return;
        }

        System.out.println("=== PROFIT SHEET: " + fileName + " ===");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading profit sheet: " + e.getMessage());
        }
    }

    /**
     * Writes vending machine inventory stocking and sales data to a file for record-keeping
     */
    public void writeToFile() {
        try (PrintWriter writer = new PrintWriter("./resources/VendingMachine_History.txt")) {

            writer.println("=== INVENTORY ===");
            for (Product item : inventory) {
                writer.printf("%s,%.2f,%d%n", item.getName(), item.getPrice(), item.getStock());
            }

            writer.println("\n=== TRANSACTIONS ===");
            if (salesLog != null && !salesLog.isEmpty()) {
                for (String logEntry : salesLog) {
                    writer.println(logEntry);
                }
            } else {
                writer.println("No transactions recorded.");
            }

            System.out.println("Vending machine data written to file.");

        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file. Please check the file path.");
        }
    }

    @Override
    public String toString() {
        return "VendingMachine{" +
                "users=" + users +
                ", inventory=" + inventory +
                ", currentSessionMoney=" + currentSessionMoney +
                ", salesLog=" + salesLog +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VendingMachine that = (VendingMachine) o;
        return Objects.equals(users, that.users) && Objects.equals(inventory, that.inventory) && Objects.equals(currentSessionMoney, that.currentSessionMoney) && Objects.equals(salesLog, that.salesLog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, inventory, currentSessionMoney, salesLog);
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

    public List<String> getSalesLog() {
        return salesLog;
    }

    public void setSalesLog(List<String> salesLog) {
        this.salesLog = salesLog;
    }
}
