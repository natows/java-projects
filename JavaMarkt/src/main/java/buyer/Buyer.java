package buyer;
import observer.InflationObserver;
import observer.OfferObserver;
import visit.Visitable;
import visit.Visitor;
import product.Product;

import java.util.HashMap;
import java.util.Map;
import seller.Seller;
public class Buyer implements InflationObserver, Visitable, OfferObserver {
    private int id;
    private String name;
    private double budget;
    private double inflationRate;
    private Map<Seller, Product[]> currentOffers;
    private NeedsManager needsManager;
    private PurchaseStrategy purchaseStrategy;

    public Buyer(int id, String name, double budget, Map<Product, Integer> needs) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.currentOffers = new HashMap<>();
        this.needsManager = new BuyerNeedsManager(needs);
        this.purchaseStrategy = new BuyerPurchaseStrategy(3);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }


    @Override 
    public void updateInflationRate(double inflationRate) {
        this.inflationRate = inflationRate;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public Map<Product, Integer> getPurchasedProducts() {
        return purchaseStrategy.getPurchasedProducts();
    }


    @Override 
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Map<Product, Integer> getNeeds() {
        return needsManager.getNeeds();
    }
    
   
    public void updatePurchasedProducts(Product product, int quantity) {
        purchaseStrategy.updatePurchasedProducts(product, quantity); 
    }
        

    public double calculatePurchasePropensity(Product product) {
        return purchaseStrategy.calculatePurchasePropensity(product, inflationRate);
    }

    public void considerPurchase(Seller seller, Product product, int quantity) {
        Map<Product, Integer> currentNeeds = needsManager.getNeeds();
        int quantityToBuy = Math.min(currentNeeds.get(product), product.getQuantity());
        double totalCost = quantityToBuy * product.getPrice();

        boolean shouldBuy = purchaseStrategy.shouldPurchase(product, budget, inflationRate, totalCost);
    
        if (quantityToBuy > 0 && budget >= totalCost && shouldBuy) {
            budget -= totalCost;
            seller.sellProduct(product, quantityToBuy);
            System.out.println("Buyer " + name + " purchased " + quantityToBuy + 
                        " of " + product.getName() + " from seller " + seller.getName());
            updatePurchasedProducts(product, quantityToBuy);
            needsManager.updateNeed(product, quantityToBuy);
        } 
    }

    @Override 
    public void updateOffer(Seller seller, Product[] products) {
        currentOffers.put(seller, products);

        for (Product product : products) {
            if (needsManager.checkIfNeeded(product)) {
                Map<Product, Integer> currentNeeds = needsManager.getNeeds();
                if (currentNeeds.containsKey(product)) { 
                    int quantity = currentNeeds.get(product);
                    considerPurchase(seller, product, quantity);
                }
            }    
        }

    }

    public NeedsManager getNeedsManager() {
        return needsManager;
    }

    public PurchaseStrategy getPurchaseStrategy() {
        return purchaseStrategy;
    }


       
}
