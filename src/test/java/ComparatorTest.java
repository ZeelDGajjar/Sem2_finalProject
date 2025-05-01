import org.example.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComparatorTest {

    @Test
    void testCategoryComparator_withDifferentCategories() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Soda", 1.0, "Drink", 5, 15);

        int result = categoryComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    void testCategoryComparator_withSameCategoryDifferentPrices() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Soda", 2.0, "Snack", 5, 15);

        int result = categoryComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    void testCategoryComparator_withSameCategoryAndSamePrice() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Soda", 1.5, "Snack", 5, 15);

        int result = categoryComparator.compare(product1, product2);

        assertEquals(0, result);
    }

    @Test
    void testCategoryComparator_withIdenticalProducts() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Chips", 1.5, "Snack", 10, 20);

        int result = categoryComparator.compare(product1, product2);

        assertEquals(0, result);
    }

    @Test
    void testProductNameComparator_withDifferentNames() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Soda", 1.0, "Drink", 5, 15);

        int result = productNameComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    void testProductNameComparator_withSameNameDifferentPrices() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Chips", 2.0, "Snack", 5, 15);

        int result = productNameComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    void testProductNameComparator_withSameNameAndSamePrice() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Chips", 1.5, "Snack", 5, 15);

        int result = productNameComparator.compare(product1, product2);

        assertEquals(0, result);
    }

    @Test
    void testProductNameComparator_withIdenticalProducts() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = new Product("Chips", 1.5, "Snack", 10, 20);
        Product product2 = new Product("Chips", 1.5, "Snack", 10, 20);

        int result = productNameComparator.compare(product1, product2);

        assertEquals(0, result);
    }

    @Test
    void testCategoryComparator_withNullCategory() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = new Product("Chips", 1.5, null, 10, 20);
        Product product2 = new Product("Soda", 1.0, "Drink", 5, 15);

        int result = categoryComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    void testProductNameComparator_withNullName() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = new Product(null, 1.5, "Snack", 10, 20);
        Product product2 = new Product("Soda", 1.0, "Drink", 5, 15);

        int result = productNameComparator.compare(product1, product2);

        assertTrue(result < 0);
    }
}
