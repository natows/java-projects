
import java.util.List;

public class Division implements Operation {
    @Override
    public int apply(List<Integer> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Division requires at least two operands");
        }
        int result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            int operand = operands.get(i);
            if (operand == 0) {
                throw new IllegalArgumentException("Cannot divide by zero");
            }
            result /= operand;
        }
        return result;
    }
}