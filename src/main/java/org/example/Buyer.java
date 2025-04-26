package org.example;

public class Buyer extends User{

    public Buyer(int id, String name) {
        super(id, name);
    }

    /**
     *
     * @param name
     * @return
     */
    public Product chooseProduct(String name) {
        return null;
    }

    /**
     *
     */
    public void cancelOrder() {}

    /**
     *
     */
    public void buy() {

    }

    /**
     *
     * @param message
     */
    @Override
    public void displayMessage(String message) {

    }
}
