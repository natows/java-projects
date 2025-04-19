import java.util.ArrayList;
import java.util.List;

public class StackEvaluator {
    private Stack stack;

    public StackEvaluator(Stack stack) {
        this.stack = stack;
    }

    public void evaluate(Operation operation, int numOperands) {
        if (stack.getSize() < numOperands || numOperands <= 1) {
            throw new IllegalArgumentException("Invalid expression: not enough operands for operation");
        }
        List<Integer> operands = new ArrayList<>();
        for (int i = 0; i < numOperands; i++) {
            operands.add(0, Integer.parseInt(stack.pop())); 
        }
        int result = operation.apply(operands);
        stack.push(String.valueOf(result));
    }
}