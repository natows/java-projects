import java.util.EmptyStackException;

public class Stack implements StackOperations {
    private String[] stack;
    private int size;

    public Stack() {
        this.stack = new String[10];
        this.size = 0;
    }

    @Override
    public void push(String elem) {
        if (size == stack.length) {
            resize();
        }
        stack[size++] = elem;
    }

    @Override
    public String pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        String elem = stack[--size];
        stack[size] = null;
        return elem;
    }

    @Override
    public String peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return stack[size - 1];
    }

    @Override
    public int getSize() {
        return size;
    }

    private void resize() {
        String[] newStack = new String[stack.length * 2];
        System.arraycopy(stack, 0, newStack, 0, stack.length);
        stack = newStack;
    }
}