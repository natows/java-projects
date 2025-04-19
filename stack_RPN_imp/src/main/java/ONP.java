public class ONP {
    private String[] expression;
    private Stack stack;
    private StackEvaluator stackEvaluator;

    public ONP(String expression) {
        this.expression = ExpressionParser.parse(expression);
        this.stack = new Stack();
        this.stackEvaluator = new StackEvaluator(this.stack);
    }

    public int calculateONP() {
        for (String token : expression) {
            Operation operation = OperatorsFactory.getOperation(token);
            if (operation != null) {
                stackEvaluator.evaluate(operation, stack.getSize()); 
            } else {
                stack.push(token);
            }
        }
        if (stack.getSize() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        return Integer.parseInt(stack.pop());
    }
}