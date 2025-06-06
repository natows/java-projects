package visit;
import java.util.HashMap;
import java.util.Map;

import buyer.Buyer;
import product.Product;
import seller.Seller;

public class MarketVisitor implements Visitor {

    @Override 
    public void visit(Seller seller) {
        System.out.println("Visiting seller: " + seller.getName());
        
        Product[] products = seller.getProducts();
        int totalQuantity = 0;
        int totalSold = 0;
        for (Product p : products) {
            totalQuantity += p.getQuantity();
            totalSold += p.getSoldCount();
        }

        double markupAdjustment = 0.0;
        
        if (totalSold < 2) {
            markupAdjustment -= 0.03;
        } else if (totalSold > 5) {
            markupAdjustment += 0.02;
        }


        if (totalQuantity < 3) {
            markupAdjustment += 0.03;
        } else if (totalQuantity > 7) {
            markupAdjustment -= 0.02;
        }

        double currentMarkup = seller.getMarkup();
        seller.setMarkup(Math.max(0.05, Math.min(0.5, currentMarkup + markupAdjustment)));
        seller.restockProducts();
        System.out.println("Seller " + seller.getName() + " restocked products.");

    }

    @Override
    public void visit(Buyer buyer) {
        double income = 50.0 + (buyer.getId() * 20);
        buyer.setBudget(buyer.getBudget() + income);
        Map<Product, Integer> existingNeeds = buyer.getNeeds();
        Map<Product, Integer> allKnownProducts = new HashMap<>(existingNeeds);

        allKnownProducts.putAll(buyer.getPurchasedProducts());
        
        for (Product knownProduct : allKnownProducts.keySet()) {
            if (knownProduct.getCategory() == Product.ProductType.ESSENTIAL && Math.random() < 0.6) {
                int newNeed = 1 + (int)(Math.random() * 3);
                buyer.getNeedsManager().addNeed(knownProduct, newNeed);
            } else if (knownProduct.getCategory() == Product.ProductType.LUXURY && Math.random() < 0.3) {
                int newNeed = 1;
                buyer.getNeedsManager().addNeed(knownProduct, newNeed);
            }
        }
    }

    
    
}
