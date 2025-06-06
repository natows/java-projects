import product.Product;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
public class ProductTests {
    private Product product;
    @BeforeEach
    public void setUp() {
        product = new Product(1, "Test Product", 100.0, Product.ProductType.ESSENTIAL);
    }

    @Test
    public void testGetCode() {
        assertEquals(1, product.getCode());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Product", product.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(100.0, product.getPrice());
    }

    @Test
    public void testGetCategory() {
        assertEquals(Product.ProductType.ESSENTIAL, product.getCategory());
    }

    @Test
    public void testGetMakingCost() {
        assertEquals(50.0, product.getMakingCost());
    }

    @Test
    public void testSetPrice() {
        product.setPrice(120.0);
        assertEquals(120.0, product.getPrice());
    }
    @Test
    public void testMakingCost() {
        Product luxuryProduct = new Product(2, "Luxury Product", 200.0, Product.ProductType.LUXURY);
        assertEquals(100.0, luxuryProduct.getMakingCost());
    }
    
}
