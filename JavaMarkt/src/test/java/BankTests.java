import bank.CentralBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import product.Product;
import seller.Seller;

public class BankTests {
    private CentralBank centralBank;

    @BeforeEach
    public void setUp() {
        centralBank = new CentralBank(2);
    }

    @Test
    public void testGetInflationRate() {
        assertEquals(2.0, centralBank.getInflationRate());
    }

    @Test
    public void testSetInflationRate() {
        centralBank.setInflationRate(3);
        assertEquals(3.0, centralBank.getInflationRate());
    }

    @Test
    public void testRegisterObserver() {
        Product[] products = new Product[1];
        Seller sellerObserver = new Seller(1, "Test Seller", products);
        centralBank.registerObserver(sellerObserver);
        assertEquals(1, centralBank.getObserverCount()); 
    
        
    }

    @Test
    public void testRemoveObserver() {
        Product[] products = new Product[1];
        Seller sellerObserver = new Seller(1, "Test Seller", products);
        centralBank.registerObserver(sellerObserver);
        assertEquals(1, centralBank.getObserverCount());
        centralBank.removeObserver(sellerObserver);
        assertEquals(0, centralBank.getObserverCount()); 
    }

    @Test
    public void testNotifyObservers() {
        Product product = new Product(1, "Test product", 10.0, Product.ProductType.ESSENTIAL);
        Product[] products = {product};
        Seller sellerObserver = new Seller(1, "Test Seller", products);
        centralBank.registerObserver(sellerObserver);
        centralBank.setInflationRate(3);
        assertEquals(3.0, sellerObserver.getInflationRate());
    }


    @Test 
    public void testCountInflation() {
        centralBank.setDesiredTaxIncome(200.0);
        centralBank.setTotalTurnover(1000);
        centralBank.countInflation();
        assertEquals(0.4, centralBank.getInflationRate());
    }

}
