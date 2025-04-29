package org.example;

import java.util.HashMap;
import java.util.Map;

public class Money {
    private Map<Double, Integer> cashMap;

    public Money() {
        this.cashMap = new HashMap<Double, Integer>();
    }

    /**
     * Adds value to the total money based on the cash to add
     * @param cash a map of the amount and it's kind in cash
     */
    public void add(Map<Double, Integer> cash) {
        for (Map.Entry<Double, Integer> entry : cash.entrySet()) {
            this.cashMap.put(entry.getKey(), this.cashMap.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    /**
     * Removes value from the total money based on the cash to remove
     * @param cash a map of the amount and it's kind in cash
     */
    public void subtract(Map<Double, Integer> cash) {
        for (Map.Entry<Double, Integer> entry : cash.entrySet()) {
            double denomination = entry.getKey();
            int currentValue = this.cashMap.getOrDefault(denomination, 0);
            int result = currentValue - entry.getValue();

            if (result > 0) {
                this.cashMap.put(denomination, result);
            } else {
                this.cashMap.remove(denomination);
            }
        }
    }

    /**
     * Calculates the total amount
     * @return a map value of amount, and it's kind in cash
     */
    public Double calculateTotal() {
        double total = 0;
        for (Map.Entry<Double, Integer> entry : cashMap.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }
}
