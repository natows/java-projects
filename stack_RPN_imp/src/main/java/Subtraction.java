import java.util.List;

public class Subtraction implements Operation {
    @Override
    public int apply(List<Integer> operands) {
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Subtraction requires at least two operands");
        }
        int result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            result -= operands.get(i);
        }
        return result;
    }
}