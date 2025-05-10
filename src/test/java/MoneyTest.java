import org.example.*;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    // === add Tests ===
    @Test
    void testAdd_ValidDenominations() {
        Money money = new Money();
        Map<Double, Integer> cash = Map.of(1.0, 2, 0.5, 1);

        money.add(cash);
        assertEquals(2, money.getCashMap().getOrDefault(1.0, 0));
        assertEquals(1, money.getCashMap().getOrDefault(0.5, 0));
    }

    @Test
    void testAdd_NullMap() {
        Money money = new Money();

        assertThrows(NullPointerException.class, () -> money.add(null));
        assertTrue(money.getCashMap().isEmpty());
    }

    @Test
    void testAdd_ZeroQuantity() {
        Money money = new Money();
        Map<Double, Integer> cash = Map.of(1.0, 0);

        money.add(cash);
        assertFalse(money.getCashMap().containsKey(1.0));
    }

    @Test
    void testAdd_NegativeQuantity() {
        Money money = new Money();
        Map<Double, Integer> cash = new HashMap<>();
        cash.put(1.0, -1);

        money.add(cash);
        assertFalse(money.getCashMap().containsKey(1.0));
    }

    @Test
    void testAdd_ExistingDenomination() {
        Money money = new Money(Map.of(1.0, 1));
        Map<Double, Integer> cash = Map.of(1.0, 2);

        money.add(cash);
        assertEquals(3, money.getCashMap().getOrDefault(1.0, 0));
    }

    // === subtract Tests ===
    @Test
    void testSubtract_ValidDenominations() {
        Money money = new Money(Map.of(1.0, 3, 0.5, 2));
        Map<Double, Integer> cash = Map.of(1.0, 1, 0.5, 1);

        money.subtract(cash);
        assertEquals(2, money.getCashMap().getOrDefault(1.0, 0));
        assertEquals(1, money.getCashMap().getOrDefault(0.5, 0));
    }

    @Test
    void testSubtract_NullMap() {
        Money money = new Money(Map.of(1.0, 1));

        assertThrows(NullPointerException.class, () -> money.subtract(null));
        assertEquals(1, money.getCashMap().getOrDefault(1.0, 0));
    }

    @Test
    void testSubtract_InsufficientQuantity() {
        Money money = new Money(Map.of(1.0, 1));
        Map<Double, Integer> cash = Map.of(1.0, 2);

        assertThrows(IllegalArgumentException.class, () -> money.subtract(cash));
        assertEquals(1, money.getCashMap().getOrDefault(1.0, 0));
    }

    @Test
    void testSubtract_ZeroQuantity() {
        Money money = new Money(Map.of(1.0, 1));
        Map<Double, Integer> cash = Map.of(1.0, 0);

        money.subtract(cash);
        assertEquals(1, money.getCashMap().getOrDefault(1.0, 0));
    }

    @Test
    void testSubtract_RemovesDenomination() {
        Money money = new Money(Map.of(1.0, 1));
        Map<Double, Integer> cash = Map.of(1.0, 1);

        money.subtract(cash);
        assertFalse(money.getCashMap().containsKey(1.0));
    }

    // === calculateTotal Tests ===
    @Test
    void testCalculateTotal_ValidDenominations() {
        Money money = new Money(Map.of(1.0, 2, 0.5, 1));

        assertEquals(2.5, money.calculateTotal(), 0.001);
    }

    @Test
    void testCalculateTotal_EmptyMap() {
        Money money = new Money();

        assertEquals(0.0, money.calculateTotal(), 0.001);
    }

    @Test
    void testCalculateTotal_SingleDenomination() {
        Money money = new Money(Map.of(1.0, 3));

        assertEquals(3.0, money.calculateTotal(), 0.001);
    }

    @Test
    void testCalculateTotal_ZeroQuantity() {
        Money money = new Money(Map.of(1.0, 0));

        assertEquals(0.0, money.calculateTotal(), 0.001);
    }

    // === clear Tests ===
    @Test
    void testClear_NonEmptyMap() {
        Money money = new Money(Map.of(1.0, 2));

        money.clear();
        assertTrue(money.getCashMap().isEmpty());
    }

    @Test
    void testClear_EmptyMap() {
        Money money = new Money();

        money.clear();
        assertTrue(money.getCashMap().isEmpty());
    }

    // === getChange Tests ===
    @Test
    void testGetChange_ExactChange() {
        Money money = new Money(Map.of(1.0, 2, 0.5, 1));

        Map<Double, Integer> change = money.getChange(1.5);
        assertEquals(Map.of(1.0, 1, 0.5, 1), change);
    }

    @Test
    void testGetChange_InsufficientDenominations() {
        Money money = new Money(Map.of(2.0, 1));

        Map<Double, Integer> change = money.getChange(0.5);
        assertEquals(Collections.emptyMap(), change);
    }

    @Test
    void testGetChange_ZeroAmount() {
        Money money = new Money(Map.of(1.0, 1));

        Map<Double, Integer> change = money.getChange(0.0);
        assertEquals(Collections.emptyMap(), change);
    }

    @Test
    void testGetChange_NegativeAmount() {
        Money money = new Money(Map.of(1.0, 1));

        assertThrows(IllegalArgumentException.class, () -> money.getChange(-0.5));
    }

    @Test
    void testGetChange_EmptyMap() {
        Money money = new Money();

        Map<Double, Integer> change = money.getChange(1.0);
        assertEquals(Collections.emptyMap(), change);
    }
}
