import org.example.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BuyerTest {

    @Test
    public void testChooseProduct_Valid() {
        Buyer buyer = new Buyer(1, "John Doe");
        List<Product> products = List.of(new Product("Soda", 1.50, "", 6, 12));
        Product selected = buyer.chooseProduct("Soda", products);
        assertEquals("Soda", selected.getName());
    }

    @Test
    public void testChooseProduct_Invalid() {
        Buyer buyer = new Buyer(1, "John Doe");
        List<Product> products = List.of(new Product("Soda", 1.50, "", 6, 12));
        Product selected = buyer.chooseProduct("Water", products);
        assertNull(selected);
    }

    @Test
    public void testChooseProduct_EmptyString() {
        Buyer buyer = new Buyer(1, "John Doe");
        List<Product> products = List.of(new Product("Soda", 1.50, "", 6, 12));
        Product selected = buyer.chooseProduct("", products);
        assertNull(selected);
    }

    @Test
    public void testChooseProduct_Null() {
        Buyer buyer = new Buyer(1, "John Doe");
        List<Product> products = List.of(new Product("Soda", 1.50, "", 6, 12));
        Product selected = buyer.chooseProduct(null, products);
        assertNull(selected);
    }

    @Test
    public void testCancelOrder() {
        Buyer buyer = new Buyer(1, "John Doe");
        buyer.setSelectedProduct(new Product("Soda", 1.50, "", 6, 12));
        buyer.cancelOrder();
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    public void testCancelOrder_NoSelection() {
        Buyer buyer = new Buyer(1, "John Doe");
        buyer.cancelOrder(); // nothing selected
        assertNull(buyer.getSelectedProduct());
    }

    @Test
    public void testBuy_AddsToHistory() {
        Buyer buyer = new Buyer(1, "John Doe");
        VendingMachine vm = new VendingMachine();
        Product soda = new Product("Soda", 1.50, "", 6, 12);
        Operator operator = new Operator();
        vm.reloadProduct(soda, 1, operator);
        buyer.setSelectedProduct(soda);
        buyer.buy(vm);
        assertTrue(buyer.getPurchaseHistory().contains(soda));
    }

    @Test
    public void testBuy_NoProductSelected() {
        Buyer buyer = new Buyer(1, "John Doe");
        VendingMachine vm = new VendingMachine();
        buyer.buy(vm); // should not throw
        assertTrue(buyer.getPurchaseHistory().isEmpty());
    }

    @Test
    public void testBuy_TwiceSameProduct() {
        Buyer buyer = new Buyer(1, "John Doe");
        VendingMachine vm = new VendingMachine();
        Product soda = new Product("Soda", 1.50, "", 6, 12);
        Operator operator = new Operator();
        vm.reloadProduct(soda, 1, operator);
        buyer.setSelectedProduct(soda);
        buyer.buy(vm);
        buyer.buy(vm); // should do nothing, selectedProduct already cleared
        assertEquals(1, buyer.getPurchaseHistory().size());
    }

    @Test
    public void testDisplayMessage() {
        Buyer buyer = new Buyer(1, "John Doe");
        assertDoesNotThrow(() -> buyer.displayMessage("Hello"));
    }

    @Test
    public void testDisplayMessage_EmptyString() {
        Buyer buyer = new Buyer(1, "John Doe");
        assertDoesNotThrow(() -> buyer.displayMessage(""));
    }

    @Test
    public void testDisplayMessage_Null() {
        Buyer buyer = new Buyer(1, "John Doe");
        assertDoesNotThrow(() -> buyer.displayMessage(null));
    }

    @Test
    public void testToString_ContainsName() {
        Buyer buyer = new Buyer(1, "John Doe");
        String output = buyer.toString();
        assertEquals(false, output.contains("John Doe"));
    }
}
