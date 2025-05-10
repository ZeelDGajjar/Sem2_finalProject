package org.example;

import java.util.*;

public class Money {
    private Map<Double, Integer> cashMap;

    public Money() {
        this.cashMap = new HashMap<>();
    }

    public Money(Map<Double, Integer> cashMap) {
        this.cashMap = (cashMap == null ? new HashMap<>() : new HashMap<>(cashMap));
    }

    /**
     * Adds value to the total money based on the cash to add.
     * @param cash a map of the amount and it's kind in cash
     */
    public void add(Map<Double, Integer> cash) {
        if (cash == null) {
            throw new NullPointerException("The provided map cannot be null");
        }

        for (Map.Entry<Double, Integer> entry : cash.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException("Invalid key or value in map");
            }

            double denomination = entry.getKey();
            int count = entry.getValue();
            if (count <= 0) {
                continue;
            }
            this.cashMap.put(denomination, this.cashMap.getOrDefault(denomination, 0) + count);
        }
    }

    /**
     * Removes value from the total money based on the cash to remove.
     * @param cash a map of the amount and it's kind in cash
     */
    public void subtract(Map<Double, Integer> cash) {
        if (cash == null) {
            throw new NullPointerException("Cannot subtract null");
        }

        for (Map.Entry<Double, Integer> entry : cash.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException("Invalid quantity");
            }
            
            double denomination = entry.getKey();
            int countToSubtract = entry.getValue();
            if (countToSubtract <= 0) {
                continue;
            }
            
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
     * Calculates the total amount.
     * @return the total amount in cash
     */
    public double calculateTotal() {
        if (this.cashMap == null) {
            throw new NullPointerException("Cash map cannot be null");
        }

        double total = 0.0;
        for (Map.Entry<Double, Integer> entry : this.cashMap.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException("Invalid key or value in cash map");
            }
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }

    /**
     * Method to clear out the money after each session in Vending Machine to maintain the amount of money to return.
     */
    public void clear() {
        this.cashMap.clear();
    }

    /**
     * Tries to compute the exact change using the current cashMap.
     * @param amount the amount of the change to return
     * @return a map of denominations to quantities, or null if change cannot be made.
     */
    public Map<Double, Integer> getChange(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Change amount cannot be negative.");
        }

        Map<Double, Integer> available = new TreeMap<>(Collections.reverseOrder());
        available.putAll(cashMap);

        Map<Double, Integer> change = new LinkedHashMap<>();

        for (Map.Entry<Double, Integer> entry : available.entrySet()) {
            double denom = entry.getKey();
            int availableCount = entry.getValue();

            if (denom > amount || availableCount == 0) {
                continue;
            }

            int needed = (int) (amount / denom);
            int toUse = Math.min(needed, availableCount);

            if (toUse > 0) {
                change.put(denom, toUse);
                amount -= denom * toUse;
                amount = Math.round(amount * 100.0) / 100.0; // round to avoid floating-point precision issues
            }

            if (amount == 0.0) {
                break;
            }
        }

        if (amount == 0.0) {
            return change;
        } else {
            return Collections.emptyMap();
        }
    }

    @Override
    public String toString() {
        return "Money{" + "cashMap=" + cashMap + '}';
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
        return new HashMap<>(cashMap);
    }

    public void setCashMap(Map<Double, Integer> cashMap) {
        this.cashMap = cashMap;
    }
}
