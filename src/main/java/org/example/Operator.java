package org.example;

import java.util.List;

public class Operator extends User {
    private String accessLevel;
    private List<String> profitSheets;

    public Operator(int id, String name) {
        super(id, name);
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
}
