
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import product.Product;
import seller.Seller;


public class SellerTests {
    private Seller seller;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        product1 = new Product(1, "Test Product 1", 100.0, Product.ProductType.ESSENTIAL);
        product2 = new Product(2, "Test Product 2", 200.0, Product.ProductType.LUXURY);
        Product[] products = {product1, product2};
        seller = new Seller(1, "Test Seller", products);
    }

    @Test
    public void testGetId() {
        assertEquals(1, seller.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Seller", seller.getName());
    }

    @Test

    public void testGetProducts() {
        Product[] products = seller.getProducts();
        assertEquals(2, products.length);
        assertEquals(product1, products[0]);
        assertEquals(product2, products[1]);
    }

    // @Test
    // public void testGetMarkup() {
    //     assertEquals(0.0, seller.getMarkup());
    // }

    // @Test
    // public void testSetMarkup() {
    //     seller.setMarkup(0.2);
    //     assertEquals(0.2, seller.getMarkup());
    // }

    @Test
    public void testCalculatePrice() {
        seller.setMarkup(0.2);
        double expectedPrice1 = product1.getMakingCost() * (1 + 0.2);
        double expectedPrice2 = product2.getMakingCost() * (1 + 0.2);
        assertEquals(expectedPrice1, seller.calculatePrice(product1));
        assertEquals(expectedPrice2, seller.calculatePrice(product2));
    }

    @Test
    public void testUpdateInflationRate() {
        seller.setMarkup(0.2);
        double inflationRate = 0.1;
        seller.updateInflationRate(inflationRate);
        double expectedPrice1 = product1.getMakingCost() * (1 + 0.2) * (1 + inflationRate);
        double expectedPrice2 = product2.getMakingCost() * (1 + 0.2) * (1 + inflationRate);
        assertEquals(expectedPrice1, seller.calculatePrice(product1));
        assertEquals(expectedPrice2, seller.calculatePrice(product2));
    }

    @Test
    public void testUpdateProductPrices() {
        seller.setMarkup(0.2);
        double inflationRate = 0.1;
        seller.updateInflationRate(inflationRate);
        assertEquals(product1.getPrice(), seller.calculatePrice(product1));
        assertEquals(product2.getPrice(), seller.calculatePrice(product2));
    }




}
