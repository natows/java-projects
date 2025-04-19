
import java.util.List;

public class Multiplication implements Operation {
    @Override
    public int apply(List<Integer> operands) {
        int result = 1;
        for (int operand : operands) {
            result *= operand;
        }
        return result;
    }
}
