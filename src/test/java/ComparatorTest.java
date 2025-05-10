import org.example.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorTest {

    private Product createProduct(String name, double price, String category, int stock, int maxCapacity) {
        return new Product(name, price, category, stock, maxCapacity, "Info", LocalDate.of(2025, 12, 31));
    }

    @Test
    void testProductNameComparator_withNullName() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = createProduct(null, 1.5, "Snack", 10, 20);
        Product product2 = createProduct(null, 1.0, "Drink", 5, 15);

        assertThrows(NullPointerException.class, () -> {
            productNameComparator.compare(product1, product2);
        });
    }

    @Test
    void testProductNameComparator_withSameNameDifferentPrices() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = createProduct("ProductA", 10.0, "Snack", 10, 20);
        Product product2 = createProduct("ProductA", 15.0, "Drink", 5, 15);

        assertTrue(productNameComparator.compare(product1, product2) < 0);
    }

    @Test
    void testProductNameComparator_withDifferentNames() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = createProduct("ProductA", 10.0, "Snack", 10, 20);
        Product product2 = createProduct("ProductB", 5.0, "Drink", 5, 15);

        assertTrue(productNameComparator.compare(product1, product2) < 0);
    }

    @Test
    void testProductNameComparator_withSameNameAndPrice() {
        ProductNameComparator productNameComparator = new ProductNameComparator();
        Product product1 = createProduct("ProductA", 10.0, "Snack", 10, 20);
        Product product2 = createProduct("ProductA", 10.0, "Drink", 5, 15);

        assertEquals(0, productNameComparator.compare(product1, product2));
    }

    @Test
    void testCategoryComparator_withNullCategory() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = createProduct("ProductA", 1.5, null, 10, 20);
        Product product2 = createProduct("ProductB", 1.0, null, 5, 15);

        assertThrows(NullPointerException.class, () -> {
            categoryComparator.compare(product1, product2);
        });
    }

    @Test
    void testCategoryComparator_withSameCategoryDifferentPrices() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = createProduct("ProductA", 10.0, "Snack", 10, 20);
        Product product2 = createProduct("ProductB", 15.0, "Snack", 5, 15);

        assertTrue(categoryComparator.compare(product1, product2) < 0);
    }

    @Test
    void testCategoryComparator_withDifferentCategories() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = createProduct("ProductA", 10.0, "Snack", 10, 20);
        Product product2 = createProduct("ProductB", 5.0, "Drink", 5, 15);

        assertFalse(categoryComparator.compare(product1, product2) < 0);
    }

    @Test
    void testCategoryComparator_withSameCategoryAndPrice() {
        CategoryComparator categoryComparator = new CategoryComparator();
        Product product1 = createProduct("ProductA", 10.0, "Snack", 10, 20);
        Product product2 = createProduct("ProductB", 10.0, "Snack", 5, 15);

        assertEquals(0, categoryComparator.compare(product1, product2));
    }
}
