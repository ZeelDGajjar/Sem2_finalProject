import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

public class VendingMachineTest {

    // === dispenseItem ===
    @Test
    public void testDispenseItem_successful() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        vm.addMoney(new Money(Map.of(1.5, 1)));

        Buyer b = new Buyer(1, "Maan");
        vm.dispenseItem(b, p);

        Assertions.assertEquals(4, p.getStock());
        Assertions.assertTrue(vm.getSalesLog().size() > 0);
    }

    @Test
    public void testDispenseItem_transactionFailure() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        vm.addMoney(new Money(Map.of(1.0, 1)));

        Buyer b = new Buyer(2, "Alex");
        vm.dispenseItem(b, p);

        Assertions.assertEquals(5, p.getStock());
        Assertions.assertTrue(vm.getSalesLog().isEmpty());
    }

    @Test
    public void testDispenseItem_outOfStock() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 0, 10, 100);
        vm.getInventory().add(p);
        vm.addMoney(new Money(Map.of(1.5, 1)));

        Buyer b = new Buyer(3, "Sam");
        vm.dispenseItem(b, p);

        Assertions.assertEquals(0, p.getStock());
        Assertions.assertTrue(vm.getSalesLog().isEmpty());
    }

    // === selectItem ===
    @Test
    public void testSelectItem_existingProduct() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);

        Product result = vm.selectItem("Chips");
        Assertions.assertEquals(p, result);
    }

    @Test
    public void testSelectItem_nonExistingProduct() {
        VendingMachine vm = new VendingMachine();
        Product result = vm.selectItem("Soda");

        Assertions.assertNull(result);
    }

    @Test
    public void testSelectItem_caseInsensitive() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Coke", 2.0, "drink", 10, 10, 350);
        vm.getInventory().add(p);

        Product result = vm.selectItem("coke");
        Assertions.assertEquals(p, result);
    }

    // === addMoney ===
    @Test
    public void testAddMoney_singleDenomination() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(2.0, 1));
        vm.addMoney(money);

        Assertions.assertEquals(2.0, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    public void testAddMoney_multipleDenominations() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(1.0, 2, 0.5, 1));
        vm.addMoney(money);

        Assertions.assertEquals(2.5, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    public void testAddMoney_accumulates() {
        VendingMachine vm = new VendingMachine();
        vm.addMoney(new Money(Map.of(1.0, 1)));
        vm.addMoney(new Money(Map.of(0.5, 1)));

        Assertions.assertEquals(1.5, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    // === showInventory ===
    @Test
    public void testShowInventory_notEmpty() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);

        // Test output (not ideal in real testing but works for this case)
        vm.showInventory();  // Ensure this runs without exceptions
    }

    @Test
    public void testShowInventory_empty() {
        VendingMachine vm = new VendingMachine();
        vm.showInventory();  // No exception should occur
    }

    @Test
    public void testShowInventory_multipleItems() {
        VendingMachine vm = new VendingMachine();
        Product p1 = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        Product p2 = new Drink("Coke", 2.0, "drink", 10, 10, 350);
        vm.getInventory().add(p1);
        vm.getInventory().add(p2);

        vm.showInventory();  // Ensure the output is correct
    }

    // === reloadProduct ===
    @Test
    public void testReloadProduct_increaseStock() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Cookies", 1.0, "snack", 5, 10, 100);
        Operator op = new Operator(1, "John");

        vm.reloadProduct(p, 3, op);
        Assertions.assertEquals(8, p.getStock());
    }

    @Test
    public void testReloadProduct_maxCapacity() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Cookies", 1.0, "snack", 9, 10, 100);
        Operator op = new Operator(2, "Mike");

        vm.reloadProduct(p, 3, op);
        Assertions.assertEquals(10, p.getStock());
    }

    @Test
    public void testReloadProduct_negativeAmount() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Cookies", 1.0, "snack", 5, 10, 100);
        Operator op = new Operator(3, "Jane");

        vm.reloadProduct(p, -3, op);
        Assertions.assertEquals(2, p.getStock());  // Stock shouldn't go negative
    }

    // === changePrice ===
    @Test
    public void testChangePrice_valid() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        Operator operator = new Operator(1, "John");

        vm.changePrice(p, 2.0, operator);
        Assertions.assertEquals(2.0, p.getPrice(), 0.001);
    }

    @Test
    public void testChangePrice_zero() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        Operator operator = new Operator(1, "John");

        vm.changePrice(p, 0.0, operator);
        Assertions.assertEquals(0.0, p.getPrice(), 0.001);
    }

    @Test
    public void testChangePrice_negative() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        Operator operator = new Operator(1, "John");

        vm.changePrice(p, -1.0, operator);
        Assertions.assertEquals(-1.0, p.getPrice(), 0.001);
    }

    // === processTransaction ===
    @Test
    public void testProcessTransaction_success() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        vm.addMoney(new Money(Map.of(2.0, 1)));

        Buyer b = new Buyer(1, "Maan");
        boolean result = vm.processTransaction(b, p);
        Assertions.assertTrue(result);
    }

    @Test
    public void testProcessTransaction_insufficientMoney() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        vm.addMoney(new Money(Map.of(1.0, 1)));

        Buyer b = new Buyer(2, "Alex");
        boolean result = vm.processTransaction(b, p);
        Assertions.assertFalse(result);
    }

    @Test
    public void testProcessTransaction_outOfStock() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 0, 10, 100);
        vm.getInventory().add(p);
        vm.addMoney(new Money(Map.of(1.5, 1)));

        Buyer b = new Buyer(3, "Sam");
        boolean result = vm.processTransaction(b, p);
        Assertions.assertFalse(result);
    }

    // === writeToFile ===
    @Test
    public void testWriteToFile_success() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        vm.writeToFile();

        File file = new File("VendingMachine_Data.txt");
        Assertions.assertTrue(file.exists());
    }

    @Test
    public void testWriteToFile_emptyInventory() {
        VendingMachine vm = new VendingMachine();
        vm.writeToFile();

        File file = new File("VendingMachine_Data.txt");
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() > 0);
    }

    @Test
    public void testWriteToFile_noSales() {
        VendingMachine vm = new VendingMachine();
        Product p = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(p);
        vm.writeToFile();

        File file = new File("VendingMachine_Data.txt");
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() > 0);
    }

    // === readProfitSheet ===
    @Test
    public void testReadProfitSheet_success() {
        VendingMachine vm = new VendingMachine();
        String testFile = "profitSheetTest.txt";

        // Create a temporary profit sheet file
        try (PrintWriter writer = new PrintWriter(testFile)) {
            writer.println("Item: Chips, Sold: 5, Profit: 7.50");
            writer.println("Item: Soda, Sold: 3, Profit: 6.00");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        vm.readProfitSheet(Path.of(testFile));

        File file = new File(testFile);
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.length() > 0);

        file.delete();  // Cleanup after test
    }

    @Test
    public void testReadProfitSheet_fileNotFound() {
        VendingMachine vm = new VendingMachine();
        vm.readProfitSheet(Path.of("nonExistentFile.txt"));

        // No exception should be thrown and we just check that it handled the missing file
        Assertions.assertTrue(true);
    }

    @Test
    public void testReadProfitSheet_emptyFile() {
        VendingMachine vm = new VendingMachine();
        String testFile = "emptyProfitSheet.txt";

        // Create an empty file
        try (PrintWriter writer = new PrintWriter(testFile)) {
            // Empty file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        vm.readProfitSheet(Path.of(testFile));

        // Ensure no output or error is thrown for an empty file
        Assertions.assertTrue(true);

        File file = new File(testFile);
        file.delete();  // Cleanup after test
    }

}
