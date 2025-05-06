package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Buyer extends User{
    private Product selectedProduct;
    private final List<Product> purchaseHistory;

    public Buyer() {
        super(getNextId() + 1, "");
        purchaseHistory = new ArrayList<>();
        this.selectedProduct = null;
    }

    public Buyer(int id, String name) {
        super(id, name);
        this.selectedProduct = null;
        this.purchaseHistory = new ArrayList<>();
    }

    /**
     * Allows the buyer to choose a product by its name from the vending machine inventory
     * @param name The name of the product the buyer wants to select
     * @param products The list of products available for selection
     * @return The selected Product if found; otherwise, null
     */
    public Product chooseProduct(String name, List<Product> products) {
        if (products == null || products.isEmpty()) {
            displayMessage("The product list is empty or not available.");
            return null;
        }

        Product found = products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    displayMessage("No such product found");
                    return null;
                });

        this.selectedProduct = found;
        return found;
    }

    /**
     * Cancels the current order and resets any selections made by the buyer
     */
    public void cancelOrder() {
        if (selectedProduct != null) {
            System.out.println(selectedProduct.getName() + " has been cancelled.");
            selectedProduct = null;
        }
        displayMessage("No products selected to cancel.");
    }

    /**
     * Finalizes the purchase of the selected product
     */
    public void buy(VendingMachine vendingMachine) {
        if (selectedProduct == null) {
            displayMessage("You have not selected any product to buy.");
            return;
        }
        vendingMachine.dispenseItem(this, selectedProduct);
        addPurchaseHistory(selectedProduct);
        selectedProduct = null;
    }

    /**
     * Displays a custom message intended for the buyer
     * @param message The message to be displayed
     */
    @Override
    public void displayMessage(String message) {
        System.out.println("Please note:" + message);
    }

    /**
     * Adds product to purchase history of the user to retrieve typically in the VendingMachine class
     * @param product the given product to add to the history of the user
     */
    public void addPurchaseHistory(Product product) {
        if (product != null) {
            purchaseHistory.add(product);
        }
    }

    @Override
    public String toString() {
        return "Buyer{" + super.toString() +
                "selectedProduct=" + selectedProduct +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(selectedProduct, buyer.selectedProduct) && Objects.equals(purchaseHistory, buyer.purchaseHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectedProduct, purchaseHistory);
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Product> getPurchaseHistory() {
        return purchaseHistory;
    }
}
