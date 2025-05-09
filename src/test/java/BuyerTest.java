import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerTest {

    // === chooseProduct Tests ===
    @Test
    void testChooseProduct_ValidProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, 100));
        Product selected = buyer.chooseProduct("Chips", products);
        assertEquals("Chips", selected.getName());
        assertEquals(selected, buyer.getSelectedProduct());
    }

    @Test
    void testChooseProduct_NonExistentProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, 100));
        Product selected = buyer.chooseProduct("Soda", products);
        assertNull(selected);
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testChooseProduct_NullName() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, 100));
        Product selected = buyer.chooseProduct(null, products);
        assertNull(selected);
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testChooseProduct_EmptyProducts() {
        Buyer buyer = new Buyer("John");
        List<Product> products = new ArrayList<>();
        Product selected = buyer.chooseProduct("Chips", products);
        assertNull(selected);
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testChooseProduct_NullProducts() {
        Buyer buyer = new Buyer("John");
        Product selected = buyer.chooseProduct("Chips", null);
        assertNull(selected);
        assertNull(buyer.getSelectedProduct());
    }

    // === cancelOrder Tests ===
    @Test
    void testCancelOrder_WithSelectedProduct() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        buyer.setSelectedProduct(chips);
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testCancelOrder_NoSelectedProduct() {
        Buyer buyer = new Buyer("John");
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testCancelOrder_AfterPurchase() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testCancelOrder_MultipleCalls() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        buyer.setSelectedProduct(chips);
        buyer.cancelOrder();
        buyer.cancelOrder(); // Second call should be safe
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testCancelOrder_AfterChooseProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, 100));
        buyer.chooseProduct("Chips", products);
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    // === buy Tests ===
    @Test
    void testBuy_SuccessfulPurchase() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);

        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().contains(chips));
        assertNull(buyer.getSelectedProduct());
        assertEquals(4, chips.getStock());
    }

    @Test
    void testBuy_NoSelectedProduct() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testBuy_InsufficientFunds() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.0, 1)));
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
        assertEquals(5, chips.getStock());
    }

    @Test
    void testBuy_OutOfStock() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 0, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
        assertEquals(0, chips.getStock());
    }

    @Test
    void testBuy_NullVendingMachine() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        buyer.setSelectedProduct(chips);
        buyer.buy(null);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
    }

    // === displayMessage Tests ===
    @Test
    void testDisplayMessage_ValidMessage() {
        Buyer buyer = new Buyer("John");
        assertDoesNotThrow(() -> buyer.displayMessage("Test message"));
    }

    @Test
    void testDisplayMessage_NullMessage() {
        Buyer buyer = new Buyer("John");
        assertDoesNotThrow(() -> buyer.displayMessage(null));
    }

    @Test
    void testDisplayMessage_EmptyMessage() {
        Buyer buyer = new Buyer("John");
        assertDoesNotThrow(() -> buyer.displayMessage(""));
    }

    @Test
    void testDisplayMessage_AfterPurchase() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        buyer.setSelectedProduct(chips);
        buyer.buy(vm); // Triggers success message
        assertDoesNotThrow(() -> buyer.displayMessage("Post-purchase message"));
    }

    // === addPurchaseHistory Tests ===
    @Test
    void testAddPurchaseHistory_ValidProduct() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        buyer.addPurchaseHistory(chips);
        assertTrue(buyer.getPurchaseHistory().contains(chips));
        assertEquals(1, buyer.getPurchaseHistory().size());
    }

    @Test
    void testAddPurchaseHistory_NullProduct() {
        Buyer buyer = new Buyer("John");
        buyer.addPurchaseHistory(null);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
    }

    @Test
    void testAddPurchaseHistory_MultipleProducts() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        Product soda = new Snack("Soda", 2.0, "drink", 5, 10, 150);
        buyer.addPurchaseHistory(chips);
        buyer.addPurchaseHistory(soda);
        assertEquals(2, buyer.getPurchaseHistory().size());
        assertTrue(buyer.getPurchaseHistory().contains(chips));
        assertTrue(buyer.getPurchaseHistory().contains(soda));
    }

    @Test
    void testAddPurchaseHistory_SameProductTwice() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        buyer.addPurchaseHistory(chips);
        buyer.addPurchaseHistory(chips);
        assertEquals(2, buyer.getPurchaseHistory().size());
        assertTrue(buyer.getPurchaseHistory().contains(chips));
    }

    @Test
    void testAddPurchaseHistory_AfterFailedPurchase() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.0, 1))); // Insufficient funds
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
    }
}
