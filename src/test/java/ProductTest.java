import org.example.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    // === restock Tests ===
    @Test
    void testRestock_ValidQuantity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        product.restock(3);
        assertEquals(8, product.getStock());
    }

    @Test
    void testRestock_ZeroQuantity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertThrows(IllegalArgumentException.class, () -> product.restock(0));
        assertEquals(5, product.getStock());
    }

    @Test
    void testRestock_NegativeQuantity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertThrows(IllegalArgumentException.class, () -> product.restock(-1));
        assertEquals(5, product.getStock());
    }

    @Test
    void testRestock_ExceedsMaxCapacity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertThrows(IllegalArgumentException.class, () -> product.restock(6));
        assertEquals(5, product.getStock());
    }

    @Test
    void testRestock_AtMaxCapacity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        product.restock(5);
        assertEquals(10, product.getStock());
    }

    // === reduceStock Tests ===
    @Test
    void testReduceStock_ValidQuantity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        product.reduceStock(2);
        assertEquals(3, product.getStock());
    }

    @Test
    void testReduceStock_ZeroQuantity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(0));
        assertEquals(5, product.getStock());
    }

    @Test
    void testReduceStock_NegativeQuantity() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(-1));
        assertEquals(5, product.getStock());
    }

    @Test
    void testReduceStock_ExceedsStock() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(6));
        assertEquals(5, product.getStock());
    }

    @Test
    void testReduceStock_ReduceToZero() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        product.reduceStock(5);
        assertEquals(0, product.getStock());
    }

    // === displayLabel Tests ===
    @Test
    void testDisplayLabel_ValidProduct() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        String label = product.displayLabel();
        assertEquals("Chips - $1.50 (5 left)\nNutritional Info: 100 calories", label);
    }

    @Test
    void testDisplayLabel_ZeroStock() {
        Product product = new Product("Chips", 1.5, "snack", 0, 10, "100 calories", LocalDate.of(2030, 1, 1));

        String label = product.displayLabel();
        assertEquals("Chips - $1.50 (0 left)\nNutritional Info: 100 calories", label);
    }

    @Test
    void testDisplayLabel_EmptyNutritionalInfo() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "", LocalDate.of(2030, 1, 1));

        String label = product.displayLabel();
        assertEquals("Chips - $1.50 (5 left)\nNutritional Info: ", label);
    }

    @Test
    void testDisplayLabel_ZeroPrice() {
        Product product = new Product("Chips", 0.0, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        String label = product.displayLabel();
        assertEquals("Chips - $0.00 (5 left)\nNutritional Info: 100 calories", label);
    }

    // === isExpired Tests ===
    @Test
    void testIsExpired_NotExpired() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertFalse(product.isExpired());
    }

    @Test
    void testIsExpired_Expired() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2020, 1, 1));

        assertTrue(product.isExpired());
    }

    @Test
    void testIsExpired_NullExpiryDate() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", null);

        assertFalse(product.isExpired());
    }

    @Test
    void testIsExpired_Today() {
        Product product = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2025, 5, 9));

        assertFalse(product.isExpired()); // Assumes not expired on the exact date
    }

    // === compareTo Tests ===
    @Test
    void testCompareTo_DifferentPrices() {
        Product chips = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));
        Product coke = new Product("Coke", 2.0, "drink", 5, 10, "150 calories", LocalDate.of(2030, 1, 1));

        assertTrue(chips.compareTo(coke) < 0);
        assertTrue(coke.compareTo(chips) > 0);
    }

    @Test
    void testCompareTo_SamePriceDifferentNames() {
        Product chips = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));
        Product apple = new Product("Apple", 1.5, "fruit", 5, 10, "80 calories", LocalDate.of(2030, 1, 1));

        assertTrue(chips.compareTo(apple) > 0); // Chips > Apple lexicographically
        assertTrue(apple.compareTo(chips) < 0);
    }

    @Test
    void testCompareTo_SamePriceAndName() {
        Product chips1 = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));
        Product chips2 = new Product("Chips", 1.5, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));

        assertEquals(0, chips1.compareTo(chips2));
    }

    @Test
    void testCompareTo_ZeroPrice() {
        Product chips = new Product("Chips", 0.0, "snack", 5, 10, "100 calories", LocalDate.of(2030, 1, 1));
        Product coke = new Product("Coke", 1.5, "drink", 5, 10, "150 calories", LocalDate.of(2030, 1, 1));

        assertTrue(chips.compareTo(coke) < 0);
    }
}
