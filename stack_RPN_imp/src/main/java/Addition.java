
import java.util.List;

public class Addition implements Operation {
    @Override
    public int apply(List<Integer> operands) {
        int result = 0;
        for (int operand : operands) {
            result += operand;
        }
        return result;
    }
}