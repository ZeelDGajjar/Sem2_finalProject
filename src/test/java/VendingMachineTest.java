import org.example.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    // === dispenseItem Tests ===
    @Test
    void testDispenseItem_Successful() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        Buyer buyer = new Buyer("John");

        vm.dispenseItem(buyer, chips);
        assertEquals(4, chips.getStock());
        assertEquals(1, vm.getSalesLog().size());
        assertTrue(vm.getSalesLog().getFirst().contains("Chips sold for $1.50"));
        assertEquals(0.0, vm.getCurrentSessionMoney().calculateTotal());
    }

    @Test
    void testDispenseItem_InsufficientFunds() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.0, 1)));
        Buyer buyer = new Buyer("John");

        vm.dispenseItem(buyer, chips);
        assertEquals(5, chips.getStock());
        assertTrue(vm.getSalesLog().isEmpty());
        assertEquals(1.0, vm.getCurrentSessionMoney().calculateTotal());
    }

    @Test
    void testDispenseItem_OutOfStock() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 0, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        Buyer buyer = new Buyer("John");

        vm.dispenseItem(buyer, chips);
        assertEquals(0, chips.getStock());
        assertTrue(vm.getSalesLog().isEmpty());
    }

    @Test
    void testDispenseItem_NullProduct() {
        VendingMachine vm = new VendingMachine();
        vm.addMoney(new Money(Map.of(2.0, 1)));
        Buyer buyer = new Buyer("John");

        vm.dispenseItem(buyer, null);
        assertTrue(vm.getSalesLog().isEmpty());
        assertEquals(2.0, vm.getCurrentSessionMoney().calculateTotal());
    }

    // === selectItem Tests ===
    @Test
    void testSelectItem_ExistingProductInStock() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);

        assertEquals(chips, vm.selectItem(chips));
    }

    @Test
    void testSelectItem_ProductOutOfStock() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 0, 10, 100);
        vm.getInventory().add(chips);

        assertNull(vm.selectItem(chips));
    }

    @Test
    void testSelectItem_NullProduct() {
        VendingMachine vm = new VendingMachine();
        assertNull(vm.selectItem(null));
    }

    // === addMoney Tests ===
    @Test
    void testAddMoney_SingleDenomination() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(2.0, 1));

        vm.addMoney(money);
        assertEquals(2.0, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    void testAddMoney_MultipleDenominations() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(1.0, 2, 0.5, 1));

        vm.addMoney(money);
        assertEquals(2.5, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    void testAddMoney_NullMoney() {
        VendingMachine vm = new VendingMachine();
        vm.addMoney(null);
        assertEquals(0.0, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    void testAddMoney_ZeroAmount() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(0.0, 1));

        vm.addMoney(money);
        assertEquals(0.0, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    // === showInventory Tests ===
    @Test
    void testShowInventory_Empty() {
        VendingMachine vm = new VendingMachine();
        vm.showInventory(); // Should not throw exception
    }

    @Test
    void testShowInventory_SingleItem() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);

        vm.showInventory();
    }

    @Test
    void testShowInventory_MultipleItems() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        Product coke = new Drink("Coke", 2.0, "drink", 10, 10, 350);
        vm.getInventory().add(chips);
        vm.getInventory().add(coke);

        vm.showInventory();
    }

    // === reloadProduct Tests ===
    @Test
    void testReloadProduct_IncreaseStock() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.0, "snack", 5, 10, 100);
        Operator operator = new Operator("John", AccessLevel.STAFF);

        vm.reloadProduct(chips, 3, operator);
        assertEquals(8, chips.getStock());
        assertEquals(3, operator.getStockingHistory().get(chips).intValue());
    }

    @Test
    void testReloadProduct_AtMaxCapacity() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.0, "snack", 9, 10, 100);
        Operator operator = new Operator("John", AccessLevel.STAFF);

        vm.reloadProduct(chips, 3, operator);
        assertEquals(10, chips.getStock());
    }

    @Test
    void testReloadProduct_ZeroAmount() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.0, "snack", 5, 10, 100);
        Operator operator = new Operator("John", AccessLevel.STAFF);

        vm.reloadProduct(chips, 0, operator);
        assertEquals(5, chips.getStock());
    }

    @Test
    void testReloadProduct_NegativeAmount() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.0, "snack", 5, 10, 100);
        Operator operator = new Operator("John", AccessLevel.STAFF);

        vm.reloadProduct(chips, -1, operator);
        assertEquals(5, chips.getStock());
    }

    @Test
    void testReloadProduct_NewProduct() {
        VendingMachine vm = new VendingMachine();
        Product candy = new Snack("Candy", 1.0, "snack", 0, 10, 100);
        Operator operator = new Operator("John", AccessLevel.STAFF);

        vm.reloadProduct(candy, 5, operator);
        assertTrue(vm.getInventory().contains(candy));
        assertEquals(5, candy.getStock());
    }

    // === changePrice Tests ===
    @Test
    void testChangePrice_ValidPriceAdmin() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        Operator operator = new Operator("John", AccessLevel.ADMIN);

        vm.changePrice(chips, 2.0, operator);
        assertEquals(2.0, chips.getPrice(), 0.001);
    }

    @Test
    void testChangePrice_ZeroPriceAdmin() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        Operator operator = new Operator("John", AccessLevel.ADMIN);

        vm.changePrice(chips, 0.0, operator);
        assertEquals(0.0, chips.getPrice(), 0.001);
    }

    @Test
    void testChangePrice_NegativePrice() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        Operator operator = new Operator("John", AccessLevel.ADMIN);

        vm.changePrice(chips, -1.0, operator);
        assertEquals(1.5, chips.getPrice(), 0.001); // Price should not change
    }

    @Test
    void testChangePrice_NonAdmin() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        Operator operator = new Operator("John", AccessLevel.STAFF);

        vm.changePrice(chips, 2.0, operator);
        assertEquals(1.5, chips.getPrice(), 0.001); // Price should not change
    }

    // === processTransaction Tests ===
    @Test
    void testProcessTransaction_Success() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        Buyer buyer = new Buyer("John");

        assertTrue(vm.processTransaction(buyer, chips));
        assertEquals(0.0, vm.getCurrentSessionMoney().calculateTotal());
    }

    @Test
    void testProcessTransaction_InsufficientMoney() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.0, 1)));
        Buyer buyer = new Buyer("John");

        assertFalse(vm.processTransaction(buyer, chips));
        assertEquals(1.0, vm.getCurrentSessionMoney().calculateTotal());
    }

    @Test
    void testProcessTransaction_OutOfStock() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 0, 10, 100);
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));
        Buyer buyer = new Buyer("John");

        assertFalse(vm.processTransaction(buyer, chips));
    }

    @Test
    void testProcessTransaction_NullProduct() {
        VendingMachine vm = new VendingMachine();
        vm.addMoney(new Money(Map.of(2.0, 1)));
        Buyer buyer = new Buyer("John");

        assertFalse(vm.processTransaction(buyer, null));
    }

    // === writeToFile Tests ===
    @Test
    void testWriteToFile_Success() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);

        String filePath = "./test_VendingMachine_History.txt";
        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.println("Test");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create test file: " + e.getMessage());
        }

        vm.writeToFile();
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        file.delete();
    }

    @Test
    void testWriteToFile_EmptyInventory() {
        VendingMachine vm = new VendingMachine();

        String filePath = "./test_VendingMachine_History.txt";
        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.println("Test");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create test file: " + e.getMessage());
        }

        vm.writeToFile();
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        file.delete();
    }

    @Test
    void testWriteToFile_NoSales() {
        VendingMachine vm = new VendingMachine();
        Product chips = new Snack("Chips", 1.5, "snack", 5, 10, 100);
        vm.getInventory().add(chips);

        String filePath = "./test_VendingMachine_History.txt";
        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.println("Test");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create test file: " + e.getMessage());
        }

        vm.writeToFile();
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        file.delete();
    }

    // === readProfitSheet Tests ===
    @Test
    void testReadProfitSheet_Success() {
        VendingMachine vm = new VendingMachine();
        String filePath = "./test_profitSheet.txt";

        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.println("Item: Chips, Sold: 5, Profit: 7.50");
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create test file: " + e.getMessage());
        }

        VendingMachine.readProfitSheet(new File(filePath).toPath());
        File file = new File(filePath);
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    void testReadProfitSheet_FileNotFound() {
        VendingMachine.readProfitSheet(new File("nonexistent.txt").toPath());
        // Should not throw exception
    }

    @Test
    void testReadProfitSheet_EmptyFile() {
        VendingMachine vm = new VendingMachine();
        String filePath = "./test_profitSheet.txt";

        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Could not create empty file: " + e.getMessage());
        }

        VendingMachine.readProfitSheet(new File(filePath).toPath());
        File file = new File(filePath);
        assertTrue(file.exists());

        file.delete();
    }
}
