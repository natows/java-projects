package buyer;
import java.util.Map;

import product.Product;

public interface PurchaseStrategy {
    boolean shouldPurchase(Product product, double budget, double inflationRate, double totalCost);
    double calculatePurchasePropensity(Product product, double inflationRate);
    boolean areSurvivalRequirementsMet();
    void updatePurchasedProducts(Product product, int quantity);
    Map<Product, Integer> getPurchasedProducts();
}
