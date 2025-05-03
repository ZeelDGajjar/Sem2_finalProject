package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Buyer extends User{
    private List<Product> purchaseHistory;

    public Buyer(int id, String name) {
        super(id, name);
        purchaseHistory = new ArrayList<Product>();
    }

    /**
     * Allows the buyer to choose a product by its name from the vending machine inventory
     * @param name The name of the product the buyer wants to select
     * @return The selected Product if found; otherwise, null
     */
    public Product chooseProduct(String name) {
        return null;
    }

    /**
     * Cancels the current order and resets any selections made by the buyer
     */
    public void cancelOrder() {}

    /**
     * Finalizes the purchase of the selected product
     */
    public void buy() {}

    /**
     * Displays a custom message intended for the buyer
     * @param message The message to be displayed
     */
    @Override
    public void displayMessage(String message) {}

    /**
     * Adds product to purchase history of the user to retrieve typically in the VendingMachine class
     * @param product the given product to add to the history of the user
     */
    public void addPurchaseHistory(Product product) {
    }

    @Override
    public String toString() {
        return "Buyer{}" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(purchaseHistory, buyer.purchaseHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(purchaseHistory);
    }

    public List<Product> getPurchaseHistory() {
        return purchaseHistory;
    }
}
