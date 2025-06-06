package observer;
import seller.Seller;
import product.Product;

public interface OfferObserver {
    void updateOffer(Seller seller, Product[] products);
    
}
