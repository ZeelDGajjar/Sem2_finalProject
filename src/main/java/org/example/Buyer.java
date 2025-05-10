package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Buyer extends User {
    private Product selectedProduct;
    private final List<Product> purchaseHistory;

    public Buyer() {
        super("Unknown Buyer");
        this.purchaseHistory = new ArrayList<>();
        this.selectedProduct = null;
    }

    public Buyer(String name) {
        super(name);
        this.purchaseHistory = new ArrayList<>();
        this.selectedProduct = null;
    }

    /**
     * Allows the buyer to choose a product by its name from the vending machine inventory.
     * @param name The name of the product to select.
     * @param products The list of available products.
     * @return The selected Product if found; otherwise, null.
     */
    public Product chooseProduct(String name, List<Product> products) {
        if (products == null || products.isEmpty()) {
            displayMessage("The product list is empty or not available.");
            return null;
        }
        if (name == null) {
            displayMessage("Product name cannot be null.");
            return null;
        }

        for (Product product : products) {
            if (product.getName().equals(name)) {
                if (product.isExpired()) {
                    displayMessage("Cannot select " + product.getName() + " because it is expired (expired on " + product.getExpiryDate() + ").");
                    return null;
                }
                this.selectedProduct = product;
                return product;
            }
        }

        displayMessage("No such product found.");
        this.selectedProduct = null;
        return null;
    }

    /**
     * Cancels the current product selection.
     */
    public void cancelOrder() {
        if (selectedProduct != null) {
            displayMessage(selectedProduct.getName() + " has been cancelled.");
            selectedProduct = null;
        } else {
            displayMessage("No product selected to cancel.");
        }
    }

    /**
     * Attempts to purchase the selected product from the vending machine.
     * @param vendingMachine The vending machine to purchase from.
     */
    public void buy(VendingMachine vendingMachine) {
        if (selectedProduct == null) {
            displayMessage("No product selected to buy.");
            return;
        }
        if (vendingMachine == null) {
            displayMessage("Vending machine is not available.");
            selectedProduct = null;
            return;
        }

        if (selectedProduct.isExpired()) {
            displayMessage("Cannot purchase " + selectedProduct.getName() + " because it is expired (expired on " + selectedProduct.getExpiryDate() + ").");
            selectedProduct = null;
            return;
        }

        boolean success = vendingMachine.dispenseItem(this, selectedProduct);
        if (success) {
            addPurchaseHistory(selectedProduct);
            displayMessage("Successfully purchased " + selectedProduct.getName() + ".");
        } else {
            displayMessage("Purchase failed due to insufficient funds or out of stock.");
        }

        selectedProduct = null;
    }

    /**
     * Displays a message to the buyer.
     * @param message The message to display.
     */
    @Override
    public void displayMessage(String message) {
        if (message != null) {
            System.out.println("Buyer [" + getName() + "], please note: " + message);
        }
    }

    /**
     * Adds a product to the buyer's purchase history.
     * @param product The product to add.
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
        if (this == o) return true;
        if (!(o instanceof Buyer buyer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(selectedProduct, buyer.selectedProduct) &&
                Objects.equals(purchaseHistory, buyer.purchaseHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), selectedProduct, purchaseHistory);
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Product> getPurchaseHistory() {
        return Collections.unmodifiableList(purchaseHistory);
    }
}
