import org.junit.jupiter.api.Test;

import cart.Cart;
import finder.DefaultProductFinder;
import optimizer.CalculateBestCombination;
import product.Product;
import sorter.DefaultProductSorter;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;

public class CartTest {
    private Cart cart;
    private Product product1;
    private Product product2;


    @BeforeEach
    void setUp(){
        Cart cart = new Cart(new DefaultProductSorter(), new DefaultProductFinder(), new CalculateBestCombination());
        this.cart = cart;
        Product product1 = new Product("P001", "Product 1", 100);
        this.product1 = product1;
        Product product2 = new Product("P002", "Product 2", 200);
        this.product2 = product2;
    }
    @Test 
    public void TestAdd() {
        cart.addProduct(product1);
        assertEquals(1, cart.getProducts().length);

    }

    @Test
    public void TestRemove() {
        cart.addProduct(product1);
        cart.removeProduct(product1);
        assertEquals(0, cart.getProducts().length);
    }

    @Test
    public void TestGetTotal() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        assertEquals(300, cart.getTotal());
    }
}