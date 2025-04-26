package org.example;

public class Buyer extends User{

    public Buyer(int id, String name) {
        super(id, name);
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
}
