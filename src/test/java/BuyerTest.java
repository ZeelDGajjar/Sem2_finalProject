import org.example.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerTest {

    // === Constructor Tests ===
    @Test
    void testDefaultConstructor() {
        Buyer buyer = new Buyer();
        assertEquals("Unknown Buyer", buyer.getName());
        assertNull(buyer.getSelectedProduct());
        assertTrue(buyer.getPurchaseHistory().isEmpty());
    }

    @Test
    void testConstructorWithName() {
        Buyer buyer = new Buyer("Alice");
        assertEquals("Alice", buyer.getName());
        assertNull(buyer.getSelectedProduct());
        assertTrue(buyer.getPurchaseHistory().isEmpty());
    }

    // === chooseProduct Tests ===
    @Test
    void testChooseProduct_ValidProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100));
        Product selected = buyer.chooseProduct("Chips", products);
        assertNotNull(selected);
        assertEquals("Chips", selected.getName());
        assertEquals(selected, buyer.getSelectedProduct());
    }

    @Test
    void testChooseProduct_NonExistentProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100));
        Product selected = buyer.chooseProduct("Soda", products);
        assertNull(selected);
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testChooseProduct_NullName() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100));
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

    @Test
    void testChooseProduct_ExpiredProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().minusDays(1), 100));
        Product selected = buyer.chooseProduct("Chips", products);
        assertNull(selected);
        assertNull(buyer.getSelectedProduct());
    }

    // === cancelOrder Tests ===
    @Test
    void testCancelOrder_WithSelectedProduct() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
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
    void testCancelOrder_AfterChooseProduct() {
        Buyer buyer = new Buyer("John");
        List<Product> products = List.of(new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100));
        buyer.chooseProduct("Chips", products);
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testCancelOrder_MultipleCalls() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        buyer.setSelectedProduct(chips);
        buyer.cancelOrder();
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    // === buy Tests ===
    @Test
    void testBuy_SuccessfulPurchase() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(0.25, 6))); // Exact payment: 6 quarters = $1.50
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
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.0, 1))); // Only $1.00, not enough
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
        Product chips = new Snack("Chips", 1.5, "snack", 0, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(0.25, 6))); // $1.50
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
        assertEquals(0, chips.getStock());
    }

    @Test
    void testBuy_NullVendingMachine() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        buyer.setSelectedProduct(chips);
        buyer.buy(null);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testBuy_ExpiredProduct() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().minusDays(1), 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(0.25, 6))); // $1.50
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
        assertEquals(5, chips.getStock());
    }

    @Test
    void testBuy_InsufficientChange() {
        Buyer buyer = new Buyer("John");
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1))); // $2.00, needs $0.50 change
        // No change denominations added
        buyer.setSelectedProduct(chips);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().isEmpty());
        assertNull(buyer.getSelectedProduct());
        assertEquals(5, chips.getStock());
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

    // === addPurchaseHistory Tests ===
    @Test
    void testAddPurchaseHistory_ValidProduct() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
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
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        Product soda = new Snack("Soda", 2.0, "drink", 5, 10, "150 cal", LocalDate.now().plusDays(10), 150);
        buyer.addPurchaseHistory(chips);
        buyer.addPurchaseHistory(soda);
        assertEquals(2, buyer.getPurchaseHistory().size());
        assertTrue(buyer.getPurchaseHistory().contains(chips));
        assertTrue(buyer.getPurchaseHistory().contains(soda));
    }

    // === Getter and Setter Tests ===
    @Test
    void testGetSelectedProduct() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        buyer.setSelectedProduct(chips);
        assertEquals(chips, buyer.getSelectedProduct());
    }

    @Test
    void testSetSelectedProduct_Null() {
        Buyer buyer = new Buyer("John");
        buyer.setSelectedProduct(null);
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    void testGetPurchaseHistory_Unmodifiable() {
        Buyer buyer = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        buyer.addPurchaseHistory(chips);
        List<Product> history = buyer.getPurchaseHistory();
        assertThrows(UnsupportedOperationException.class, () -> history.add(new Snack("Soda", 2.0, "drink", 5, 10, "150 cal", LocalDate.now().plusDays(10), 150)));
    }

    // === toString, equals, and hashCode Tests ===
    @Test
    void testToString() {
        Buyer buyer = new Buyer("John");
        assertTrue(buyer.toString().contains("Buyer"));
        assertTrue(buyer.toString().contains("John"));
        assertTrue(buyer.toString().contains("selectedProduct=null"));
        assertTrue(buyer.toString().contains("purchaseHistory=[]"));
    }

    @Test
    void testEquals_SameObject() {
        Buyer buyer = new Buyer("John");
        assertEquals(buyer, buyer);
    }

    @Test
    void testEquals_DifferentObjectSameValues() {
        Buyer buyer1 = new Buyer("John");
        Buyer buyer2 = new Buyer("John");
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, "100 cal", LocalDate.now().plusDays(10), 100);
        buyer1.addPurchaseHistory(chips);
        buyer2.addPurchaseHistory(chips);
        assertEquals(buyer1, buyer2);
    }

    @Test
    void testEquals_DifferentValues() {
        Buyer buyer1 = new Buyer("John");
        Buyer buyer2 = new Buyer("Alice");
        assertNotEquals(buyer1, buyer2);
    }

    @Test
    void testHashCode_Consistency() {
        Buyer buyer = new Buyer("John");
        int hashCode = buyer.hashCode();
        assertEquals(hashCode, buyer.hashCode());
    }
}
