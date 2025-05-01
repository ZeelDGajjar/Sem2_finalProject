package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Money {
    private Map<Double, Integer> cashMap;

    public Money() {
        this.cashMap = new HashMap<Double, Integer>();
    }

    public Money(Map<Double, Integer> cashMap) {
        this.cashMap = cashMap;
    }

    /**
     * Adds value to the total money based on the cash to add
     * @param cash a map of the amount and it's kind in cash
     */
    public void add(Map<Double, Integer> cash) {
        for (Map.Entry<Double, Integer> entry : cash.entrySet()) {
            double denomination = entry.getKey();
            int count = entry.getValue();
            if (count <= 0) continue; // skip invalid or zero counts

            this.cashMap.put(denomination,
                    this.cashMap.getOrDefault(denomination, 0) + count);
        }
    }

    /**
     * Removes value from the total money based on the cash to remove
     * @param cash a map of the amount and it's kind in cash
     */
    public void subtract(Map<Double, Integer> cash) {
        for (Map.Entry<Double, Integer> entry : cash.entrySet()) {
            double denomination = entry.getKey();
            int countToSubtract = entry.getValue();
            if (countToSubtract <= 0) continue;

            int currentCount = this.cashMap.getOrDefault(denomination, 0);

            if (countToSubtract > currentCount) {
                throw new IllegalArgumentException("Not enough of $" + denomination + " to subtract.");
            }

            int remaining = currentCount - countToSubtract;

            if (remaining == 0) {
                this.cashMap.remove(denomination);
            } else {
                this.cashMap.put(denomination, remaining);
            }
        }
    }

    /**
     * Calculates the total amount
     * @return a map value of amount, and it's kind in cash
     */
    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Double, Integer> entry : cashMap.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Money{" +
                "cashMap=" + cashMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(cashMap, money.cashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cashMap);
    }

    public Map<Double, Integer> getCashMap() {
        return cashMap;
    }

    public void setCashMap(Map<Double, Integer> cashMap) {
        this.cashMap = cashMap;
    }
}
