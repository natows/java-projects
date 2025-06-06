package seller;
import product.Product;

public interface SalesManager {
    double sellProduct(Product[] products, Product product, int quantity);
    void restockProducts(Product[] products);
    int calculateDemandBasedRestock(Product product);
}