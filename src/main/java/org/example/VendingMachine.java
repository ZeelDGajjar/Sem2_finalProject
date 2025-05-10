package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

public class VendingMachine {
    private List<User> users;
    private List<Product> inventory;
    private Money currentSessionMoney;
    private List<String> salesLog;

    public VendingMachine() {
        this.users = new ArrayList<>();
        this.inventory = new LinkedList<>();
        this.currentSessionMoney = new Money();
        this.salesLog = new ArrayList<>();
    }

    public VendingMachine(List<User> users, List<Product> inventory, Money currentSessionMoney) {
        this.users = users;
        this.inventory = inventory;
        this.currentSessionMoney = currentSessionMoney;
        this.salesLog = new ArrayList<>();
    }

    /**
     * Dispenses the selected item once the transaction is successful.
     * @param buyer The given buyer performing the action
     * @param item The given item to dispense
     * @return a boolean value showing if the action was successful or not
     */
    public boolean dispenseItem(Buyer buyer, Product item) {
        if (item == null) {
            System.out.println("No item selected to dispense.");
            return false;
        }
        if (!inventory.contains(item) || item.getStock() <= 0) {
            System.out.println(item.getName() + " is not available.");
            return false;
        }
        if (item.isExpired()) {
            System.out.println("Cannot dispense " + item.getName() + " because it is expired (expired on " + item.getExpiryDate() + ").");
            return false;
        }
        if (!processTransaction(buyer, item)) {
            System.out.println("Selected item has not been dispensed due to transaction failure.");
            return false;
        }

        item.reduceStock(1);
        System.out.println(item.getName() + " has been dispensed");
        String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String log = String.format("%s - %s sold for $%.2f", time, item.getName(), item.getPrice());
        salesLog.add(log);
        return true;
    }

    /**
     * Returns the product from inventory matching by name, if in stock.
     * @param product The given product to select from the vending machine
     * @return A product value selected to continue the process
     */
    public Product selectItem(Product product) {
        if (product == null) return null;
        for (Product p : inventory) {
            if (p.getName().equals(product.getName()) && p.getStock() > 0) {
                if (p.isExpired()) {
                    System.out.println("Cannot select " + p.getName() + " because it is expired.");
                    return null;
                }
                return p;
            }
        }
        System.out.println("Product " + product.getName() + " is out of stock or not found");
        return null;
    }

    /**
     * Adds money to the current session.
     * @param money The given money to add to the session
     */
    public void addMoney(Money money) {
        if (money != null && money.calculateTotal() > 0) {
            currentSessionMoney.add(money.getCashMap());
        }
    }

    /**
     * Displays all products in stock.
     */
    public void showInventory() {
        inventory.forEach(Product::displayLabel);
    }

    /**
     * Reloads stock for a product, respecting max capacity.
     * @param item the given item to reload
     * @param amount the given amount to reload by
     * @param operator the given operator performing the action
     */
    public void reloadProduct(Product item, int amount, Operator operator) {
        if (amount <= 0) {
            System.out.println("Invalid amount to reload. Must be positive.");
            return;
        }
        if (item.isExpired()) {
            System.out.println("Cannot reload " + item.getName() + " because it is expired.");
            return;
        }
        item.restock(amount);
        if (!inventory.contains(item)) {
            inventory.add(item);
        }
        operator.getStockingHistory().put(item, amount);
        System.out.println("Product reloaded: " + item.getName() + " by " + amount + " units.");
    }

    /**
     * Changes product price if operator has ADMIN access.
     * @param item the item whose price needs to be changed
     * @param price the given price to set
     * @param operator the given operator performing the action
     */
    public void changePrice(Product item, double price, Operator operator) {
        if (price < 0) {
            System.out.println("Invalid price. Price cannot be negative.");
            return;
        }
        if (operator.getAccessLevel() == AccessLevel.ADMIN) {
            item.setPrice(price);
            System.out.println("Successfully changed price of " + item.getName() + ". New price: " + price);
        } else {
            System.out.println("You don't have access to perform this operation.");
        }
    }

    /**
     * Processes the payment and dispenses change if possible.
     * @param buyer the buyer doing the transaction
     * @param item the item involved transaction
     * @return a boolean value to show if it's process was successful or not
     */
    public boolean processTransaction(Buyer buyer, Product item) {
        Product available = selectItem(item);
        if (available == null) {
            System.out.println("Selected item not available.");
            return false;
        }
        double total = currentSessionMoney.calculateTotal();
        if (total < available.getPrice()) {
            System.out.println("Insufficient funds");
            return false;
        }
        double change = total - available.getPrice();
        Map<Double, Integer> changeMap = change > 0
                ? currentSessionMoney.getChange(change)
                : Collections.emptyMap();
        if (change > 0 && changeMap.isEmpty()) {
            System.out.printf("Unable to provide exact change ($%.2f). Transaction cancelled.%n", change);
            return false;
        }
        if (!changeMap.isEmpty()) {
            currentSessionMoney.subtract(changeMap);
            System.out.println("Change dispensed: " + changeMap);
        }
        buyer.addPurchaseHistory(item);
        currentSessionMoney.clear();
        System.out.printf("Transaction successful. Change returned: $%.2f%n", change);
        return true;
    }

    /**
     * Removes expired products
     */
    public void removeExpiredProducts() {
        Iterator<Product> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.isExpired()) {
                iterator.remove();
                System.out.println("Removed expired product: " + product.getName());
            }
        }
    }

    /**
     * Reads a profit sheet from a file path.
     * @param fileName The given file from which to read the profit from
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
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading profit sheet: " + e.getMessage());
        }
    }

    /**
     * Writes inventory and sales data to a history file.
     */
    public void writeToFile() {
        try (PrintWriter writer = new PrintWriter("./resources/VendingMachine_History.txt")) {
            writer.println("=== INVENTORY ===");
            for (Product item : inventory) {
                writer.printf("%s,%.2f,%d%n", item.getName(), item.getPrice(), item.getStock());
            }
            writer.println("\n=== TRANSACTIONS ===");
            if (!salesLog.isEmpty()) {
                salesLog.forEach(writer::println);
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
