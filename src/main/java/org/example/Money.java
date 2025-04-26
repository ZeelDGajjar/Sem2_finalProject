package org.example;

import java.util.Map;

public class Money {
    private Map<Double, Integer> cashMap;

    /**
     * Adds value to the total money based on the cash to add
     * @param cash a map of the amount and it's kind in cash
     */
    public void add(Map<Double, Integer> cash) {}

    /**
     * Removes value from the total money based on the cash to remove
     * @param cash a map of the amount and it's kind in cash
     */
    public void subtract(Map<Double, Integer> cash) {}

    /**
     * Calculates the total amount
     * @return a map value of amount, and it's kind in cash
     */
    public Map<Double, Integer> calculateTotal() {
        return null;
    }
}
