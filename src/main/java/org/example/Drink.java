package org.example;

import java.time.LocalDate;
import java.util.Objects;

public class Drink extends Product{
    private int volumeInML;

    public Drink() {
        super();
        this.volumeInML = 0;
    }

    public Drink(String name, double price, String category, int stock, int MaxCapacity, String nutritionalInfo, LocalDate expiryDate, int volumeInML) {
        super(name, price, category, stock, MaxCapacity, nutritionalInfo, expiryDate);
        this.volumeInML = volumeInML;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "volumeInML=" + volumeInML +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Drink drink = (Drink) o;
        return volumeInML == drink.volumeInML;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volumeInML);
    }

    public int getVolumeInML() {
        return volumeInML;
    }

    public void setVolumeInML(int volumeInML) {
        this.volumeInML = volumeInML;
    }
}
