import org.example.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testReload_WithinCapacity() {
        Product product = new Product("Laptop", 1000.0, "Electronics", 5, 20);
        product.reload(10);
        assertEquals(15, product.getStock());
    }

    @Test
    public void testReload_ExceedsCapacity() {
        Product product = new Product("Laptop", 1000.0, "Electronics", 18, 20);
        product.reload(10);
        assertEquals(20, product.getStock());
    }

    @Test
    public void testReload_NegativeAmount() {
        Product product = new Product("Laptop", 1000.0, "Electronics", 5, 20);
        product.reload(-3);
        assertEquals(5, product.getStock());
    }

    @Test
    public void testBuy_Successful() {
        Product product = new Product("Laptop", 1000.0, "Electronics", 5, 20);
        product.buy();
        assertEquals(4, product.getStock());
    }

    @Test
    public void testBuy_OutOfStock() {
        Product product = new Product("Laptop", 1000.0, "Electronics", 0, 20);
        Exception exception = assertThrows(IllegalStateException.class, product::buy);
        assertTrue(exception.getMessage().contains("Product out of stock"));
    }

    @Test
    public void testBuy_ZeroStock() {
        Product product = new Product("Laptop", 1000.0, "Electronics", 1, 20);
        product.buy();
        assertEquals(0, product.getStock());
    }

    @Test
    public void testCompareTo_LowerPrice() {
        Product product1 = new Product("Laptop", 1000.0, "Electronics", 5, 20);
        Product product2 = new Product("Phone", 500.0, "Electronics", 10, 20);
        assertTrue(product1.compareTo(product2) > 0);
    }

    @Test
    public void testCompareTo_HigherPrice() {
        Product product1 = new Product("Phone", 500.0, "Electronics", 10, 20);
        Product product2 = new Product("Laptop", 1000.0, "Electronics", 5, 20);
        assertTrue(product1.compareTo(product2) < 0);
    }

    @Test
    public void testCompareTo_SamePrice() {
        Product product1 = new Product("Phone", 500.0, "Electronics", 10, 20);
        Product product2 = new Product("Tablet", 500.0, "Electronics", 5, 20);
        assertTrue(product1.compareTo(product2) < 0);
    }
}
