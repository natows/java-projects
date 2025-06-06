package seller;
import product.Product;
import subject.MarketSubject;
import subject.OfferSubject;
import java.util.List;
import observer.InflationObserver;
import visit.Visitable;
import visit.Visitor;
import observer.OfferObserver;
import java.util.ArrayList;
import observer.MarketObserver;
public class Seller implements InflationObserver, Visitable, OfferSubject, MarketSubject{
    private int id;
    private String name;
    private Product[] products;
    private double inflationRate;
    private double markup;
    private List<OfferObserver> offerObservers;
    private List<MarketObserver> marketObservers;
    private double profit;
    private PricingStrategy pricingStrategy;
    private SalesManager salesManager;

    public Seller(int id, String name, Product[] products) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.offerObservers = new ArrayList<>();
        this.marketObservers = new ArrayList<>();
        this.profit = 0.0;
        this.markup = 0.2;
        this.pricingStrategy = new SellerPricingStrategy();
        this.salesManager = new SellerSalesManager();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product[] getProducts() {
        return products;
    }

    public double getMarkup() {
        return markup;
    }

    public void setMarkup(double markup) {
        this.markup = markup;
        updateProductPrices();
    }

     public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double calculatePrice(Product product) {
        return pricingStrategy.calculatePrice(product, markup, inflationRate);
    }

    @Override
    public void updateInflationRate(double inflationRate) {
        this.inflationRate = inflationRate;
        updateProductPrices();
    }

    private void updateProductPrices() {
        pricingStrategy.updateProductPrices(products, markup, inflationRate);
        notifyOfferObservers();
    }

    @Override 
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void sellProduct(Product product, int quantity) {
        double saleProfit = salesManager.sellProduct(products, product, quantity);
        if (saleProfit > 0) {
            profit += saleProfit;
            notifyMarketObservers();
        }
        notifyOfferObservers();
    }

    public void restockProducts() {
        salesManager.restockProducts(products);
        notifyOfferObservers();
    }

    public double getInflationRate() {
        return inflationRate;
    }

    @Override
    public void registerOfferObserver(OfferObserver observer) {
        if (!offerObservers.contains(observer)) {
            offerObservers.add(observer);
        }
    }

    @Override
    public void removeOfferObserver(OfferObserver observer) {
        offerObservers.remove(observer);
    }

    @Override
    public void notifyOfferObservers() {
        for (OfferObserver observer : offerObservers) {
            observer.updateOffer(this, products);
        }
    }

    @Override 
    public void registerMarketObserver(MarketObserver observer) {
       marketObservers.add(observer);
    }

    @Override
    public void removeMarketObserver(MarketObserver observer) {
        marketObservers.remove(observer);
    }

    @Override
    public void notifyMarketObservers() {
        for (MarketObserver observer : marketObservers) {
            observer.updateTurnover(profit);
            profit = 0;
        }
    }
}