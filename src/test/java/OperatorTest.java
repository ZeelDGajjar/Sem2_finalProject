import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void testRestockProduct_withValidAmount() {
        Operator operator = new Operator("Op1", AccessLevel.STAFF);
        Product product = new Product("Chips", 2.0, "Snack", 5, 20);

        operator.restockProduct(product, 10);

        assertEquals(15, product.getStock());
        assertEquals(10, operator.getStockingHistory().get(product));
    }

    @Test
    void testRestockProduct_withZeroAmount() {
        Operator operator = new Operator("Op2", AccessLevel.STAFF);
        Product product = new Product("Water", 1.0, "Drink", 5, 10);

        operator.restockProduct(product, 0);

        assertEquals(5, product.getStock());
        assertEquals(0, operator.getStockingHistory().get(product));
    }

    @Test
    void testRestockProduct_withNegativeAmount() {
        Operator operator = new Operator("Op3", AccessLevel.STAFF);
        Product product = new Product("Soda", 1.5, "Drink", 3, 15);

        operator.restockProduct(product, -5);

        assertEquals(-2, product.getStock());
        assertEquals(-5, operator.getStockingHistory().get(product));
    }

    @Test
    void testRestockProduct_withNullProduct() {
        Operator operator = new Operator("op1", AccessLevel.STAFF);
        Product nullProduct = null;

        assertThrows(NullPointerException.class, () -> operator.restockProduct(nullProduct, 5));
    }

    @Test
    void testUpdateProductPrice_withValidPriceAndAdmin() {
        Operator operator = new Operator( "Op5", AccessLevel.ADMIN, List.of());
        Product product = new Product("Tea", 1.25, "Drink", 10, 30);

        operator.updateProductPrice(product, 1.50);
        assertEquals(1.50, product.getPrice());
    }

    @Test
    void testUpdateProductPrice_withValidPriceAndStaff() {
        Operator operator = new Operator("Op6", AccessLevel.ADMIN);
        Product product = new Product("Coffee", 2.0, "Drink", 5, 15);

        operator.updateProductPrice(product, 1.75);

        assertEquals(2.0, product.getPrice());
    }

    @Test
    void testUpdateProductPrice_toNegativeValue() {
        Operator operator = new Operator("Op7", AccessLevel.ADMIN);
        Product product = new Product("Juice", 2.5, "Drink", 6, 20);

        assertThrows(IllegalArgumentException.class, () -> operator.updateProductPrice(product, -1.0));
    }

    @Test
    void testUpdateProductPrice_withNullProduct() {
        Operator operator = new Operator("Op8", AccessLevel.ADMIN, List.of());

        assertThrows(NullPointerException.class, () -> operator.updateProductPrice(null, 1.0));
    }

    @Test
    void testReviewProfitSheet_addsPathToHistory() {
        Operator operator = new Operator("Op9", AccessLevel.ADMIN, new java.util.ArrayList<>());
        VendingMachine vendingMachine = new VendingMachine();

        operator.reviewProfitSheet(vendingMachine);

        assertTrue(operator.getProfitSheets().stream()
                .anyMatch(p -> p.endsWith("ProfitSheet.txt")));
    }

    @Test
    void testDisplayMessage_withNormalMessage() {
        Operator operator = new Operator("Op10", AccessLevel.ADMIN);
        operator.displayMessage("Restock successful.");
        // Visually confirm output in console or wrap with output stream capture if needed
    }

    @Test
    void testDisplayMessage_withEmptyString() {
        Operator operator = new Operator("Op11", AccessLevel.ADMIN);
        operator.displayMessage("");
    }

    @Test
    void testDisplayMessage_withNullMessage() {
        Operator operator = new Operator("Op12", AccessLevel.ADMIN);
        operator.displayMessage(null);  // prints "Please note:null" if not null-checked
    }

    @Test
    void testDisplayMessage_withLongMessage() {
        Operator operator = new Operator("Op13", AccessLevel.ADMIN);
        String longMessage = "Restock completed. ".repeat(50);
        operator.displayMessage(longMessage);
    }
}
