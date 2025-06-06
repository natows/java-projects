import org.junit.jupiter.api.Test;
import bank.CentralBank;
import product.Product;
import seller.Seller;



import static org.junit.jupiter.api.Assertions.*;


public class SimulationTest {

    
    @Test
    public void testInflationAffectsSellerPrices() {
        CentralBank bank = new CentralBank(0.0);
        
        Product bread = new Product(1, "Bread", 5.0, Product.ProductType.ESSENTIAL);
        Product[] products = {bread};
        
        Seller seller = new Seller(1, "Bakery", products);
        double markup = 0.2;
        seller.setMarkup(markup); 
        
        bank.registerObserver(seller);
        
        double inflationRate = 0.1; 
        bank.setInflationRate(inflationRate); 
        

        double expected = bread.getMakingCost() * (1 + markup) * (1 + inflationRate);
        assertEquals(expected, bread.getPrice(), 0.001);
    }
}