import java.util.HashMap;
import java.util.Map;

public class OperatorsFactory {
    private static final Map<String, Operation> operators = new HashMap<>();

    static {
        operators.put("*", new Multiplication());
        operators.put("+", new Addition());
        operators.put("-", new Subtraction());
        operators.put("/", new Division());
    }

    public static void registerOperator(String symbol, Operation operation) {
        operators.put(symbol, operation);
    }

    public static Operation getOperation(String operator) {
        return operators.get(operator);
    }
}
