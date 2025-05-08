import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import product.Product;
import sorter.DefaultProductSorter;
import cart.Cart;
import finder.DefaultProductFinder;
import optimizer.CalculateBestCombination;

public class FinderTest {
    private Cart cart;
    private Product product1;
    private Product product2;


    @BeforeEach
    void setUp() {
        cart = new Cart(new DefaultProductSorter(), new DefaultProductFinder(), new CalculateBestCombination());
        product1 = new Product("P001", "Product 1", 100);
        product2 = new Product("P002", "Product 2", 200);
    }



    @Test
    public void TestGetMostExpensiveProduct() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        assertEquals(product2, cart.getMostExpensiveProduct());
    }

    @Test
    public void TestGetCheapestProduct() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        assertEquals(product1, cart.getCheapestProduct());
    }

    @Test
    public void TestGet2MostExpensiveproducts() {
        Product product3 = new Product("P003", "Product 3", 300);
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);
        Product[] expected = {product3, product2};
        assertArrayEquals(expected, cart.getNMostExpensiveProducts(2));
    }

    @Test
    public void TestGet2CheapestProducts() {
        Product product3 = new Product("P003", "Product 3", 50);
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);
        Product[] expected = {product3, product1};
        assertArrayEquals(expected, cart.getNCheapestProducts(2));

    }
    
}
