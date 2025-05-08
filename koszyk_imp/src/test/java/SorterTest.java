import org.junit.jupiter.api.Test;

import cart.Cart;
import finder.DefaultProductFinder;
import optimizer.CalculateBestCombination;
import product.Product;
import sorter.DefaultProductSorter;


import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SorterTest {
    private Cart cart;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;

    @BeforeEach
    void setUp() {
        cart = new Cart(new DefaultProductSorter(), new DefaultProductFinder(), new CalculateBestCombination());
        product1 = new Product("P001", "ananas", 150);
        product2 = new Product("P002", "Product 2", 200);
        product3 = new Product("P003", "arbuz", 150);
        product4 = new Product("P004", "Product 4", 250);
        product5 = new Product("P005", "Product 5", 50);
    }

    @Test
    public void testSortByPriceAndAlphabeticaly() {
        cart.setProductSorter(new DefaultProductSorter());
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(product3);
        cart.addProduct(product4);
        cart.addProduct(product5);
        Product[] sortedProducts = cart.getProducts();
        Product[] expected = {product4, product2, product1, product3, product5};
        assertArrayEquals(expected, sortedProducts);
    }
}