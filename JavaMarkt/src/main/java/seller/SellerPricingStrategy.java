package seller;
import product.Product;

public class SellerPricingStrategy implements PricingStrategy {



    @Override 
    public double calculatePrice(Product product, double markup, double inflationRate) {
        return product.getMakingCost() * (1 + markup) * (1 + inflationRate);
    }

    @Override
    public void updateProductPrices(Product[] products, double markup, double inflationRate) {
        for (Product product : products) {
            double newPrice = calculatePrice(product, markup, inflationRate);
            product.setPrice(newPrice);
        }
    }






    
}
