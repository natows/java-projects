import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ONPTest {

    @Test
    public void testAddition() {
        ONP onp = new ONP("9 3 +");
        int result = onp.calculateONP();
        assertEquals(12, result);
    }

    @Test
    public void testSubstractions(){
        ONP onp = new ONP("7 3 -");
        int result = onp.calculateONP();
        assertEquals(4, result);
    }

    @Test
    public void testMultiplication(){
        ONP onp = new ONP("3 4 *");
        int result = onp.calculateONP();
        assertEquals(12, result);
    }

    @Test
    public void testDivision(){
        ONP onp = new ONP("12 3 /");
        int result = onp.calculateONP();
        assertEquals(4, result);
    }

    @Test
    public void testMultipleOperations(){
        ONP onp = new ONP("12 3 + 4 * 5 - 2 /");
        int result = onp.calculateONP();
        assertEquals(27, result);
    }

    @Test
    public void testBigNumbers(){
        ONP onp = new ONP("100001 222222 + 222 -");
        int result = onp.calculateONP();
        assertEquals(322001, result);
    }

    @Test
    void testDividingByZero() {
        ONP onp = new ONP("5 0 /");
        assertThrows(IllegalArgumentException.class, onp::calculateONP);
    }

    @Test
    void testNotEnoughOperands() {
        ONP onp = new ONP("5 +");
        assertThrows(IllegalArgumentException.class, onp::calculateONP);
    }

    @Test
    void testEmptyInput() {
        ONP onp = new ONP("");
        assertThrows(IllegalArgumentException.class, onp::calculateONP);
    }

    @Test
    void testOnlyOneNumber() {
        ONP onp = new ONP("42");
        int result = onp.calculateONP();
        assertEquals(42, result);
    }

    @Test
    void testIncorrectInput() {
        ONP onp = new ONP("42 3 + 4");
        assertThrows(IllegalArgumentException.class, onp::calculateONP);
    }
    
    @Test
    void testNoOperand() {
        ONP onp = new ONP("5 5");
        assertThrows(IllegalArgumentException.class, onp::calculateONP);
    }
}
