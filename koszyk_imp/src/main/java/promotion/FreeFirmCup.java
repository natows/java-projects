package promotion;
import product.Product;

import cart.Cart;
public class FreeFirmCup implements Promotion {
    @Override
    public void apply(Cart cart) {
        if (cart.getTotal() > 200) {
            for (Product product : cart.getProducts()) {
                if (product.getName().equals("Free Firm Cup")) {
                    return; 
                }
            }
            cart.addProduct(new Product("1234", "Free Firm Cup", 0));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FreeFirmCup;
    }
    
}
