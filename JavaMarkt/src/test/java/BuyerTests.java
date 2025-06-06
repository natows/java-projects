import buyer.Buyer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


// import observer.Observer;

import java.util.Map;

import product.Product;
import java.util.HashMap;

public class BuyerTests {
    private Buyer buyer;
    private Product product1;
    private Product product2;
    

    @BeforeEach
    public void setUp() {
        this.product1 = new Product(1, "Product1", 10.0, Product.ProductType.ESSENTIAL); 
        this.product2 = new Product(2, "Product2", 20.0, Product.ProductType.LUXURY);    
        // Map<Product, Integer> purchasedProducts = Map.of(this.product1, 2, this.product2, 1);
        buyer = new Buyer(1, "Test Buyer", 10.0, new HashMap<>());
    }

    @Test
    public void testGetId() {
        assertEquals(1, buyer.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Buyer", buyer.getName());
    }

    @Test
    public void testGetPurchasedProducts() {
        Map<Product, Integer> purchasedProducts = buyer.getPurchasedProducts();
        assertEquals(0, purchasedProducts.size());
        buyer.updatePurchasedProducts(product1, 1);
        buyer.updatePurchasedProducts(product2, 1);
        purchasedProducts = buyer.getPurchasedProducts();
        assertEquals(2, purchasedProducts.size());
        assertEquals(1, purchasedProducts.get(product1));
        assertEquals(1, purchasedProducts.get(product2));
    }

    @Test
    public void testGetBudget() {
        assertEquals(10.0, buyer.getBudget());
    }

    @Test
    public void testSetBudget() {
        buyer.setBudget(100.0);
        assertEquals(100.0, buyer.getBudget());
    }

    @Test
    public void testUpdateInflationRate() {
        buyer.updateInflationRate(0.05);
        assertEquals(0.05, buyer.getInflationRate());
    }

}
