package seller;
import product.Product;

public interface PricingStrategy {
    double calculatePrice(Product product, double markup, double inflationRate);
    void updateProductPrices(Product[] products, double markup, double inflationRate);

    
} 
