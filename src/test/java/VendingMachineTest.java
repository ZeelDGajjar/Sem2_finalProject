import org.example.*;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class VendingMachineTest {

    @Test
    void testDispenseItem_withValidItem() {
        VendingMachine vm = new VendingMachine();
        Product product = new Product("Chips", 2.0, "Snack", 10, 20);

        vm.dispenseItem(product);

        // Should reduce product stock by one
        // Should not allow stock to drop below zero
        // Should update sales or inventory data if needed
        // Should not throw exceptions
    }

    @Test
    void testDispenseItem_withNullItem() {
        VendingMachine vm = new VendingMachine();

        vm.dispenseItem(null);

        // Should handle null input without crashing
        // Should not attempt to update inventory
        // Should skip or return early
    }

    @Test
    void testSelectItem_withExistingName() {
        VendingMachine vm = new VendingMachine();
        Product product = new Product("Water", 1.5, "Drink", 5, 10);
        vm.reloadProduct(product, 1.5);

        Product selected = vm.selectItem("Water");

        // Should return a matching product object
        // Should match name case-sensitively or insensitively (decide in implementation)
        // Should not return null
    }

    @Test
    void testSelectItem_withNonexistentName() {
        VendingMachine vm = new VendingMachine();

        Product selected = vm.selectItem("Unknown");

        // Should return null if product is not found
        // Should not throw exception
    }

    @Test
    void testSelectItem_withNullName() {
        VendingMachine vm = new VendingMachine();

        Product selected = vm.selectItem(null);

        // Should return null or fail gracefully
        // Should not throw NullPointerException
    }

    @Test
    void testAddMoney_withValidMoney() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money();
        Map<Double, Integer> cash = new HashMap<>();
        cash.put(1.0, 2);
        money.add(cash);

        vm.addMoney(money);

        // Should add values to current session money
        // Should merge denominations correctly
        // Should be reflected in transaction total
    }

    @Test
    void testAddMoney_withNullMoney() {
        VendingMachine vm = new VendingMachine();

        vm.addMoney(null);

        // Should safely ignore null money input
        // Should not crash or affect session state
    }

    @Test
    void testShowInventory_whenInventoryHasItems() {
        VendingMachine vm = new VendingMachine();
        vm.reloadProduct(new Product("Soda", 1.0, "Drink", 5, 10), 1.0);

        vm.showInventory();

        // Should display or return all available products
        // Should include name, price, stock, and category
        // Should not crash or hang
    }

    @Test
    void testShowInventory_whenInventoryIsEmpty() {
        VendingMachine vm = new VendingMachine();

        vm.showInventory();

        // Should display a message indicating no items are available
        // Should not crash or misbehave
    }

    @Test
    void testWriteToFile_afterAddingItems() {
        VendingMachine vm = new VendingMachine();
        vm.reloadProduct(new Product("Juice", 2.0, "Drink", 3, 10), 2.0);

        vm.writeToFile();

        // Should write inventory state to a file
        // Should include name, stock, price, and other metadata
        // Should create or update the file correctly
    }

    @Test
    void testReloadProduct_withNewProduct() {
        VendingMachine vm = new VendingMachine();
        Product newProduct = new Product("Cookies", 1.5, "Snack", 0, 15);

        vm.reloadProduct(newProduct, 1.5);

        // Should add product to inventory
        // Should set its price as given
        // Should not exceed maxCapacity
    }

    @Test
    void testReloadProduct_withExistingProduct() {
        VendingMachine vm = new VendingMachine();
        Product product = new Product("Gum", 0.5, "Snack", 2, 10);
        vm.reloadProduct(product, 0.5);
        vm.reloadProduct(product, 0.75);

        // Should increase stock of existing product
        // Should update price if necessary
        // Should not duplicate product entries
    }

    @Test
    void testReloadProduct_withNullProduct() {
        VendingMachine vm = new VendingMachine();

        vm.reloadProduct(null, 1.0);

        // Should ignore null input
        // Should not throw exceptions or modify inventory
    }

    @Test
    void testChangePrice_withValidProduct() {
        VendingMachine vm = new VendingMachine();
        Product product = new Product("Choco Bar", 1.0, "Snack", 4, 10);
        vm.reloadProduct(product, 1.0);

        vm.changePrice(product, 1.5);

        // Should update price of the product
        // Should reflect change in inventory state
        // Should maintain product identity
    }

    @Test
    void testChangePrice_withNegativePrice() {
        VendingMachine vm = new VendingMachine();
        Product product = new Product("Soda", 1.0, "Drink", 4, 10);
        vm.reloadProduct(product, 1.0);

        vm.changePrice(product, -2.0);

        // Should not accept or apply negative prices
        // Should keep the old price intact
        // Should handle input validation
    }

    @Test
    void testChangePrice_withNullProduct() {
        VendingMachine vm = new VendingMachine();

        vm.changePrice(null, 1.0);

        // Should safely ignore null input
        // Should not crash or log errors unnecessarily
    }

    @Test
    void testReadProfitSheet_withValidFile() {
        VendingMachine vm = new VendingMachine();

        vm.readProfitSheet("profits_april.txt");

        // Should open and read the file if it exists
        // Should parse data correctly
        // Should update relevant state if applicable
    }

    @Test
    void testReadProfitSheet_withNonexistentFile() {
        VendingMachine vm = new VendingMachine();

        vm.readProfitSheet("missing_file.csv");

        // Should catch and handle file not found
        // Should provide a meaningful error message
        // Should not crash
    }

    @Test
    void testReadProfitSheet_withNullFileName() {
        VendingMachine vm = new VendingMachine();

        vm.readProfitSheet(null);

        // Should handle null input safely
        // Should not throw NullPointerException
        // Should return early or log an error
    }

    @Test
    void testProcessTransaction_withSufficientMoney() {
        VendingMachine vm = new VendingMachine();
        Buyer buyer = new Buyer(1, "John");
        Product product = new Product("Snack", 1.5, "Snack", 5, 10);
        Money money = new Money();
        Map<Double, Integer> cash = new HashMap<>();
        cash.put(1.0, 2);
        money.add(cash);
        vm.reloadProduct(product, 1.5);
        vm.addMoney(money);

        boolean result = vm.processTransaction(buyer, product);

        // Should return true if buyer has enough money
        // Should deduct amount from session money
        // Should dispense product
    }

    @Test
    void testProcessTransaction_withInsufficientMoney() {
        VendingMachine vm = new VendingMachine();
        Buyer buyer = new Buyer(2, "Jane");
        Product product = new Product("Drink", 2.0, "Drink", 3, 10);
        Money money = new Money();
        Map<Double, Integer> cash = new HashMap<>();
        cash.put(1.0, 1);
        money.add(cash);
        vm.reloadProduct(product, 2.0);
        vm.addMoney(money);

        boolean result = vm.processTransaction(buyer, product);

        // Should return false if money is insufficient
        // Should not dispense item
        // Should leave session state unchanged
    }

    @Test
    void testProcessTransaction_withNullInputs() {
        VendingMachine vm = new VendingMachine();

        boolean result = vm.processTransaction(null, null);

        // Should handle null buyer or item safely
        // Should return false or exit early
        // Should not throw exceptions
    }
}
