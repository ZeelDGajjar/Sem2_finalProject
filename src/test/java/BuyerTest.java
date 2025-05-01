
import org.example.Buyer;
import org.example.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuyerTest {

    private Buyer buyer;

    @BeforeEach
    void setUp() {
        // Set up a Buyer instance for each test
        buyer = new Buyer(1, "John Doe");
    }

    @Test
    void testChooseProduct() {
        // Assuming you will have an inventory to choose products from
        // This test will check if a product can be selected by name from the inventory.

        // Example product that you would expect to be in your inventory
        Product coke = new Product("Coke", 1.50, "", 4, 66); // You can expand Product to hold more attributes like price

        // Add a mock inventory here once you implement it. For now, it should return null or a valid product.
        Product result = buyer.chooseProduct("Coke");

        assertNotNull(result, "Product should be found in the inventory");
        assertEquals("Coke", result.getName(), "Product name should match");

        // If you implement the logic for product not found, this should be tested
        result = buyer.chooseProduct("Pepsi");
        assertNull(result, "Product should not be found if not in inventory");
    }

    @Test
    void testCancelOrder() {
        // Verifying that canceling an order doesn't throw any exception
        // This would be more meaningful when cancelOrder actually resets a selection or order state

        // For now, it just ensures no exceptions are thrown
        assertDoesNotThrow(() -> buyer.cancelOrder(), "Cancel order should not throw any exception");

        // Optionally: If you plan to track selected products or orders, check that it is reset
        // assertNull(buyer.getSelectedProduct(), "Order should be reset after canceling");
    }

    @Test
    void testBuy() {
        // Ensures the buy method can be called without errors
        // Implement logic to check if the selected product is actually being bought and money is handled

        // Example: Set a product selection before calling buy (once it's implemented)
        Product selectedProduct = new Product("Coke", 1.50,"", 4, 66); // Assuming the buyer selects this product
        buyer.chooseProduct("Coke");

        assertDoesNotThrow(() -> buyer.buy(), "Buy method should not throw any exception");

        // Optionally: Check if the product purchase is processed, such as money handling or inventory updates
        // assertTrue(buyer.hasPurchased(), "Buyer should have completed the purchase after calling buy()");
    }

    @Test
    void testDisplayMessage() {
        // This is for testing if the message is displayed correctly
        // If displayMessage interacts with UI or logging, you should capture or mock output

        assertDoesNotThrow(() -> buyer.displayMessage("Test Message"), "Display message should not throw any exception");

        // Optionally: Check if the message is actually displayed or logged
        // This will depend on how you implement displayMessage. You may need to mock the UI or console output.
    }

    @Test
    void testToString() {
        // Verifying the toString method works correctly, appending the parent class's toString
        String expected = "Buyer{}" + buyer.toString(); // Expecting Buyer{} plus the parent User's toString result
        assertEquals(expected, buyer.toString(), "toString should return the correct string format");
    }
}
