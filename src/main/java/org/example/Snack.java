package org.example;

import java.util.Objects;

public class Snack extends Product {
    public int weightInGrams;

    public Snack(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    public Snack(String name, double price, String category, int stock, int MaxCapacity, int weightInGrams) {
        super(name, price, category, stock, MaxCapacity);
        this.weightInGrams = weightInGrams;
    }

    @Override
    public String toString() {
        return "Snack{" +
                "weightInGrams=" + weightInGrams +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Snack snack = (Snack) o;
        return weightInGrams == snack.weightInGrams;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weightInGrams);
    }

    public int getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }
}
