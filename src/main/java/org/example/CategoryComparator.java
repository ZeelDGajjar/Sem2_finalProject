package org.example;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Product> {
    /**
     * Compares two objects first based on their category and then by their price
     * @param o1 the first object to be compared
     * @param o2 the second object to be compared
     * @return an int value showing which one is bigger/smaller to compare
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getCategory().compareTo(o2.getCategory())
                + (int) (o1.getPrice() - o2.getPrice());
    }
}
