import org.example.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    // === dispenseItem Tests ===
    @Test
    void testDispenseItem_Successful() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Buyer buyer = new Buyer("John");
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.5, 1)));

        assertTrue(vm.dispenseItem(buyer, chips));
        assertEquals(4, chips.getStock());
        assertEquals(1, vm.getSalesLog().size());
    }

    @Test
    void testDispenseItem_ExpiredProduct() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2020, 1, 1), 100);
        Buyer buyer = new Buyer("John");
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));

        assertFalse(vm.dispenseItem(buyer, chips));
        assertEquals(5, chips.getStock());
        assertTrue(vm.getSalesLog().isEmpty());
    }

    @Test
    void testDispenseItem_InsufficientFunds() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Buyer buyer = new Buyer("John");
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(1.0, 1)));

        assertFalse(vm.dispenseItem(buyer, chips));
        assertEquals(5, chips.getStock());
        assertTrue(vm.getSalesLog().isEmpty());
    }

    @Test
    void testDispenseItem_NullProduct() {
        VendingMachine vm = new VendingMachine();
        Buyer buyer = new Buyer("John");
        vm.addMoney(new Money(Map.of(2.0, 1)));

        assertFalse(vm.dispenseItem(buyer, null));
        assertTrue(vm.getSalesLog().isEmpty());
    }

    @Test
    void testDispenseItem_OutOfStock() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 0, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Buyer buyer = new Buyer("John");
        vm.getInventory().add(chips);
        vm.addMoney(new Money(Map.of(2.0, 1)));

        assertFalse(vm.dispenseItem(buyer, chips));
        assertEquals(0, chips.getStock());
        assertTrue(vm.getSalesLog().isEmpty());
    }

    // === selectItem Tests ===
    @Test
    void testSelectItem_ExistingProduct() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        vm.getInventory().add(chips);

        assertEquals(chips, vm.selectItem(chips));
    }

    @Test
    void testSelectItem_ExpiredProduct() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2020, 1, 1), 100);
        vm.getInventory().add(chips);

        assertNull(vm.selectItem(chips));
    }

    @Test
    void testSelectItem_NullProduct() {
        VendingMachine vm = new VendingMachine();

        assertNull(vm.selectItem(null));
    }

    @Test
    void testSelectItem_OutOfStock() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 0, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        vm.getInventory().add(chips);

        assertNull(vm.selectItem(chips));
    }

    // === addMoney Tests ===
    @Test
    void testAddMoney_ValidAmount() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(1.0, 2));

        vm.addMoney(money);
        assertEquals(2.0, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    void testAddMoney_NullMoney() {
        VendingMachine vm = new VendingMachine();

        vm.addMoney(null);
        assertEquals(0.0, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    @Test
    void testAddMoney_MultipleDenominations() {
        VendingMachine vm = new VendingMachine();
        Money money = new Money(Map.of(1.0, 2, 0.5, 1));

        vm.addMoney(money);
        assertEquals(2.5, vm.getCurrentSessionMoney().calculateTotal(), 0.001);
    }

    // === showInventory Tests ===
    @Test
    void testShowInventory() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        vm.getInventory().add(chips);

        vm.showInventory(); // Should not throw exception
    }

    @Test
    void testShowInventory_Empty() {
        VendingMachine vm = new VendingMachine();

        vm.showInventory(); // Should not throw exception
    }

    // === reloadProduct Tests ===
    @Test
    void testReloadProduct_ValidAmount() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Operator operator = new Operator("Staff", AccessLevel.STAFF);
        vm.getInventory().add(chips);

        vm.reloadProduct(chips, 3, operator);
        assertEquals(8, chips.getStock());
    }

    @Test
    void testReloadProduct_ExpiredProduct() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2020, 1, 1), 100);
        Operator operator = new Operator("Staff", AccessLevel.STAFF);

        vm.reloadProduct(chips, 3, operator);
        assertEquals(5, chips.getStock());
        assertFalse(vm.getInventory().contains(chips));
    }

    @Test
    void testReloadProduct_InvalidAmount() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Operator operator = new Operator("Staff", AccessLevel.STAFF);
        vm.getInventory().add(chips);

        vm.reloadProduct(chips, 0, operator);
        assertEquals(5, chips.getStock());
    }

    @Test
    void testReloadProduct_NewProduct() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 0, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Operator operator = new Operator("Staff", AccessLevel.STAFF);

        vm.reloadProduct(chips, 5, operator);
        assertTrue(vm.getInventory().contains(chips));
        assertEquals(5, chips.getStock());
    }

    // === changePrice Tests ===
    @Test
    void testChangePrice_ValidPriceAdmin() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Operator operator = new Operator("Admin", AccessLevel.ADMIN);
        vm.getInventory().add(chips);

        vm.changePrice(chips, 2.0, operator);
        assertEquals(2.0, chips.getPrice(), 0.001);
    }

    @Test
    void testChangePrice_NonAdmin() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Operator operator = new Operator("Staff", AccessLevel.STAFF);
        vm.getInventory().add(chips);

        vm.changePrice(chips, 2.0, operator);
        assertEquals(1.5, chips.getPrice(), 0.001);
    }

    @Test
    void testChangePrice_NegativePrice() {
        VendingMachine vm = new VendingMachine();
        Snack chips = new Snack("Chips", 1.5, "snack", 5, 10, "salted", LocalDate.of(2030, 1, 1), 100);
        Operator operator = new Operator("Admin", AccessLevel.ADMIN);
        vm.getInventory().add(chips);

        vm.changePrice(chips, -1.0, operator);
        assertEquals(1.5, chips.getPrice(), 0.001);
    }
}
