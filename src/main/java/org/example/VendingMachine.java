package org.example;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine implements TransactionHandler{
    private List<User> users;
    private List<Product> inventory;
    private Money currentSessionMoney;

    public VendingMachine() {
        users = new ArrayList<User>();
        inventory = new ArrayList<>();
        currentSessionMoney = new Money();
    }

    /**
     *
     * @param item
     */
    public void dispenseItem(Product item) {

    }

    /**
     *
     * @param name
     * @return
     */
    public Product selectItem(String name) {

        return null;
    }

    /**
     *
     * @param money
     */
    public void addMoney(Money money) {

    }

    /**
     *
     */
    public void showInventory() {

    }

    /**
     *
     */
    public void writeToFile() {

    }

    /**
     *
     * @param item
     * @param price
     */
    public void reloadProduct(Product item, double price) {

    }

    /**
     *
     * @param item
     * @param price
     */
    public void changePrice(Product item, double price) {

    }

    /**
     *
     * @param fileName
     */
    public void readProfitSheet(String fileName) {

    }

    /**
     *
     * @param buyer
     * @param item
     * @return
     */
    @Override
    public boolean processTransaction(Buyer buyer, Product item) {
        return false;
    }
}
