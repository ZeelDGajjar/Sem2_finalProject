import org.example.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class MoneyTest {

    @Test
    public void testAdd_EmptyMap() {
        Money money = new Money();
        money.add(new HashMap<>());
        assertTrue(money.getCashMap().isEmpty());
    }

    @Test
    public void testAdd_ZeroOrNegativeQuantities() {
        Money money = new Money();
        Map<Double, Integer> badCash = Map.of(
                1.0, 0,
                2.0, -3
        );
        money.add(badCash);
        assertTrue(money.getCashMap().isEmpty());
    }

    @Test
    public void testAdd_NewDenomination() {
        Money money = new Money();
        Map<Double, Integer> cashToAdd = Map.of(5.0, 3);
        money.add(cashToAdd);
        assertEquals(3, money.getCashMap().get(5.0));
    }

    @Test
    public void testAdd_NullInput() {
        Money money = new Money();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            money.add(null);
        });
        assertTrue(exception.getMessage().contains("Cannot add null"));
    }

    @Test
    public void testAdd_NullValueInMap() {
        Money money = new Money();
        Map<Double, Integer> cashWithNullValue = Map.of(1.0, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            money.add(cashWithNullValue);
        });
        assertTrue(exception.getMessage().contains("Invalid quantity"));
    }

    @Test
    public void testSubtract_EmptyMap() {
        Money money = new Money();
        money.subtract(new HashMap<>());
        assertTrue(money.getCashMap().isEmpty());
    }

    @Test
    public void testSubtract_NonExistentDenomination() {
        Money money = new Money();
        money.add(Map.of(1.0, 2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            money.subtract(Map.of(5.0, 1));
        });

        assertTrue(exception.getMessage().contains("Not enough"));
    }

    @Test
    public void testSubtract_ExactRemoval() {
        Money money = new Money();
        money.add(Map.of(0.5, 1));
        money.subtract(Map.of(0.5, 1));
        assertFalse(money.getCashMap().containsKey(0.5));
    }

    @Test
    public void testSubtract_NullInput() {
        Money money = new Money();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            money.subtract(null);
        });
        assertTrue(exception.getMessage().contains("Cannot subtract null"));
    }

    @Test
    public void testSubtract_NullValueInMap() {
        Money money = new Money();
        money.add(Map.of(1.0, 3));
        Map<Double, Integer> badCash = Map.of(1.0, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            money.subtract(badCash);
        });
        assertTrue(exception.getMessage().contains("Invalid quantity"));
    }

    @Test
    public void testCalculateTotal_SimpleTotal() {
        Money money = new Money();
        money.add(Map.of(1.0, 4, 2.0, 3, 0.5, 2));

        double expectedTotal = 1.0 * 4 + 2.0 * 3 + 0.5 * 2;
        assertEquals(expectedTotal, money.calculateTotal(), 0.0001);
    }

    @Test
    public void testCalculateTotal_EmptyMap() {
        Money money = new Money();
        assertEquals(0.0, money.calculateTotal(), 0.0001);
    }

    @Test
    public void testCalculateTotal_ZeroAndNegativeQuantities() {
        Money money = new Money();
        money.add(Map.of(1.0, 0, 2.0, -2));
        assertEquals(0.0, money.calculateTotal(), 0.0001);
    }

    @Test
    public void testCalculateTotal_NullMap() {
        Money money = new Money();
        money.add(null);
        assertEquals(0.0, money.calculateTotal(), 0.0001);
    }
}
