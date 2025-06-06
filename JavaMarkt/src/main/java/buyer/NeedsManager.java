package buyer;
import product.Product;
import java.util.Map;

public interface NeedsManager {
    boolean checkIfNeeded(Product product);
    void updateNeed(Product product, int quantity);
    void addNeed(Product product, int quantity);
    Map<Product, Integer> getNeeds();
}
