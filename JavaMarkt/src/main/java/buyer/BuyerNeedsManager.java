package buyer;
import product.Product;
import java.util.HashMap;
import java.util.Map;

public class BuyerNeedsManager implements NeedsManager {
    private Map<Product, Integer> needs;
    
    public BuyerNeedsManager(Map<Product, Integer> initialNeeds) {
        this.needs = new HashMap<>(initialNeeds);
    }
    
    @Override
    public boolean checkIfNeeded(Product product) {
        if (needs.containsKey(product)) {
            int neededQuantity = needs.get(product);
            return neededQuantity > 0;
        }
        return false;
    } 
    
    @Override
    public void updateNeed(Product product, int quantity) {
        if (!needs.containsKey(product)) {
            return;
        }
        int currentNeed = needs.get(product);
        if (currentNeed - quantity > 0) {
            needs.put(product, currentNeed - quantity);
        } else {
            needs.remove(product);
        }
    }

    
    @Override
    public void addNeed(Product product, int quantity) {
        int currentNeed = needs.getOrDefault(product, 0);
        needs.put(product, currentNeed + quantity);
    }
    
    @Override
    public Map<Product, Integer> getNeeds() {
        return new HashMap<>(needs);
    }

    public void setNeeds(Map<Product, Integer> needs) {
        this.needs = needs;
    }
    



}