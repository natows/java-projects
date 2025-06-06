package buyer;
import java.util.HashMap;
import java.util.Map;

import product.Product;

public class BuyerPurchaseStrategy implements PurchaseStrategy {
    private Map<Product, Integer> purchasedProducts;
    private int survivalRequirements;
    
     public BuyerPurchaseStrategy(int survivalRequirements) {
        this.purchasedProducts = new HashMap<>();
        this.survivalRequirements = survivalRequirements;
    }

    public Map<Product, Integer>  getPurchasedProducts() {
        return purchasedProducts;
    }


    @Override
    public void updatePurchasedProducts(Product product, int quantity) {
        if (purchasedProducts.containsKey(product)) {
            purchasedProducts.put(product, purchasedProducts.get(product) + quantity);
        } else {
            purchasedProducts.put(product, quantity);
        }
    }

    @Override
    public boolean shouldPurchase(Product product, double budget, 
                                double inflationRate, double totalCost) {
        if (budget < totalCost) {
            return false;
        }
        
        double probabilityOfPurchase = calculatePurchasePropensity(product, inflationRate);
        
        if (inflationRate > 0.5) { 
            if (product.getCategory() == Product.ProductType.ESSENTIAL) {
                probabilityOfPurchase *= 1.5; 
            } else {
                probabilityOfPurchase *= 0.5; 
        }
    }
        
        return Math.random() < probabilityOfPurchase;
    }
    
    
    public double calculatePurchasePropensity(Product product, double inflationRate) {
        double baseProbability;
        boolean survivalMet = areSurvivalRequirementsMet();
        if (product.getCategory() == Product.ProductType.ESSENTIAL) {
            if (!survivalMet) {
                baseProbability = 0.95; 
            } else {
                baseProbability = 0.6; 
            }
        } else { 
            if (!survivalMet) {
                baseProbability = 0.05; 
            } else {
                baseProbability = 0.4;  
            }
        }
        double priceRatio = product.getMakingCost() / product.getPrice();
        double inflationImpact = Math.max(0.3, 1 - inflationRate);
        double finalProbability = baseProbability * priceRatio * inflationImpact;
        return Math.min(1.0, Math.max(0.0, Math.max(0.1,finalProbability)));
    }


     @Override
    public boolean areSurvivalRequirementsMet() {
        int totalEssentialsBought = 0;
        for (Map.Entry<Product, Integer> entry : purchasedProducts.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getCategory() == Product.ProductType.ESSENTIAL) {
                totalEssentialsBought += quantity;
            }
        }
        return totalEssentialsBought >= survivalRequirements;
    }

}