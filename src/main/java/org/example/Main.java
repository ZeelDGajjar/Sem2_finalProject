package org.example;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Vending Machine System Demo ===");

        // Initialize vending machine and products
        VendingMachine vm = new VendingMachine();
        List<Product> products = new ArrayList<>();
        products.add(new Snack("Chips", 1.50, "Snack", 5, 10, "150 kcal", LocalDate.now().plusDays(30), 50));
        products.add(new Drink("Cola", 2.00, "Drink", 3, 8, "120 kcal", LocalDate.now().plusDays(30), 500));
        products.add(new Drink("Pepsi", 2.00, "Drink", 6, 8, "130 kcal", LocalDate.now().plusDays(-1), 500)); // Expired
        products.add(new Snack("Candy", 1.00, "Snack", 7, 15, "200 kcal", LocalDate.now().plusDays(60), 30));
        vm.setInventory(products);

        System.out.println("\n=== Feature 1: Inventory Management ===");
        System.out.println("Initial Inventory:");
        for (Product p : vm.getInventory()) {
            System.out.println(p.displayLabel() + ", Stock: " + p.getStock());
        }

        System.out.println("\n<<--- Adding new product (Water) --->>:");
        Operator admin = new Operator("Carol", AccessLevel.ADMIN);
        Operator staff = new Operator("Carol", AccessLevel.STAFF);
        Product water = new Drink("Water", 1.20, "Drink", 0, 10, "0 kcal", LocalDate.now().plusDays(90), 500);
        vm.reloadProduct(water, 5, admin); // Adds Water with 5 units
        System.out.println("Inventory after adding Water:");
        for (Product p : vm.getInventory()) {
            System.out.println(p.displayLabel() + ", Stock: " + p.getStock());
        }

        System.out.println("\n<<--- Checking product availability --->>:");
        System.out.println("Chips available: " + (vm.selectItem(new Snack("Chips", 0, "", 0, 0, "", null, 0)) != null ? "Yes" : "No"));
        System.out.println("Soda (non-existent) available: " + (vm.selectItem(new Drink("Soda", 0, "", 0, 0, "", null, 0)) != null ? "Yes" : "No"));

        System.out.println("\n<<--- Removing expired products --->>");
        vm.removeExpiredProducts();
        System.out.println("Inventory after removing expired products:");
        for (Product p : vm.getInventory()) {
            System.out.println(p.displayLabel() + ", Stock: " + p.getStock());
        }

        System.out.println("\n<<--- Restocking existing product (Chips) --->>:");
        Product chips = vm.selectItem(new Snack("Chips", 0, "", 0, 0, "", null, 0));
        vm.reloadProduct(chips, 3, staff); // Restock 3 more Chips
        System.out.println("Inventory after restocking Chips:");
        for (Product p : vm.getInventory()) {
            System.out.println(p.displayLabel() + ", Stock: " + p.getStock());
        }

        // Feature 2: Transaction Processing
        System.out.println("\n=== Feature 2: Transaction Processing ===");
        Buyer buyer = new Buyer("Alice");
        buyer.chooseProduct("Chips", vm.getInventory());
        Money money = new Money();
        Map<Double, Integer> payment = new HashMap<>();
        payment.put(2.0, 1); // Exact amount to avoid change issues
        money.add(payment);
        vm.addMoney(money);
        System.out.println("Buyer purchases Chips ($1.50) with $1.50 (exact amount):");
        buyer.buy(vm);
        System.out.println("Purchase history: " + buyer.getPurchaseHistory().stream()
                .map(p -> p.getName() + " ($" + p.getPrice() + ")")
                .toList());

        // Feature 3: User Roles
        System.out.println("\n=== Feature 3: User Roles ===");
        Product candy = vm.selectItem(new Snack("Candy", 0, "", 0, 0, "", null, 0));
        System.out.println("Staff restocks Candy (5 units):");
        staff.restockProduct(candy, 5, vm);
        System.out.println("\nStaff attempts to change Candy price (should fail):");
        staff.updateProductPrice(candy, 1.25, vm);
        System.out.println("\nAdmin changes Candy price to $1.25:");
        admin.updateProductPrice(candy, 1.25, vm);

        // Feature 4: Product Sorting
        System.out.println("\n=== Feature 4: Product Sorting ===");
        System.out.println("Sorted by Category:");
        List<Product> sortedByCategory = new ArrayList<>(vm.getInventory());
        sortedByCategory.sort(new CategoryComparator());
        for (Product p : sortedByCategory) {
            System.out.println(p.displayLabel() + ", Stock: " + p.getStock());
        }
        System.out.println("\nSorted by Name:");
        List<Product> sortedByName = new ArrayList<>(vm.getInventory());
        sortedByName.sort(new ProductNameComparator());
        for (Product p : sortedByName) {
            System.out.println(p.displayLabel() + ", Stock: " + p.getStock());
        }

        // Feature 5: Data Persistence
        System.out.println("\n=== Feature 5: Data Persistence ===");
        System.out.println("Attempting to write inventory and transaction log:");
        vm.writeToFile();

        // Feature 6: Error Handling
        System.out.println("\n=== Feature 6: Error Handling ===");
        System.out.println("Purchasing Chips with insufficient funds ($1.00):");
        Money lowMoney = new Money();
        lowMoney.add(Collections.singletonMap(1.0, 1)); // $1.00
        vm.addMoney(lowMoney);
        buyer.chooseProduct("Chips", vm.getInventory());
        buyer.buy(vm);
        System.out.println("\nSetting negative price for Candy:");
        admin.updateProductPrice(candy, -1.00, vm);

        System.out.println("\n=== Demo Complete ===");
    }
}
