package org.example;

import java.util.List;

public class Operator extends User {
    private String accessLevel;
    private List<String> profitSheets;

    public Operator(int id, String name) {
        super(id, name);
    }

    /**
     *
     * @param item
     * @param amount
     */
    public void restoreProduct(Product item, int amount) {

    }

    /**
     *
     * @param item
     * @param price
     */
    public void updateProductPrice(Product item, double price) {

    }

    /**
     *
     * @param fileName
     */
    public void reviewProfitSheet(String fileName) {

    }

    /**
     *
     * @param message
     */
    @Override
    public void displayMessage(String message) {

    }
}
