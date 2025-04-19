
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;


class StackTest {
    private Stack stack;

    @BeforeEach
    void setUp(){
        stack = new Stack();
        this.stack.push("First elem");
        this.stack.push("Second elem"); 
    }

    @Test
    void testAdd(){
        this.stack.push("Third elem");
        assertEquals(3, this.stack.getSize());
        assertEquals("Third elem", this.stack.peek());
    }

    @Test
    void testPop(){
        assertEquals("Second elem", this.stack.pop());
        assertEquals(1, this.stack.getSize());
        assertEquals("First elem", this.stack.pop());
        assertEquals(0, this.stack.getSize());
    }

    @Test
    void testPeek(){
        assertEquals(2, this.stack.getSize());
        assertEquals("Second elem", this.stack.peek());
        assertEquals(2, this.stack.getSize());
    }
}

class EmptyStackTest {
    private Stack stack;

    @BeforeEach
    void setUp(){
        stack = new Stack();
    }

    @Test
    void testPeekEmptyArray(){
        assertThrows(EmptyStackException.class, () -> stack.peek());
    }

    @Test
    void testPopEmptyArray(){
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }
}