package promotion;
import java.util.Arrays;
import java.util.Comparator;

import cart.Cart;
import product.Product;
public class ThirdCheapestFree implements Promotion{
    @Override
    public void apply(Cart cart) {
        if (cart.getSize() >= 3) {
            Product[] products = cart.getProducts();
            Product[] discountedProducts = new Product[0];
            for (int i = 0; i < products.length; i++) {
                if (products[i].getDiscountPrice() != 0) {
                    discountedProducts = Arrays.copyOf(discountedProducts, discountedProducts.length + 1);
                    discountedProducts[discountedProducts.length - 1] = products[i];
                }
            }
            if (discountedProducts.length >= 3) {
                Arrays.sort(discountedProducts, Comparator.comparingDouble(Product::getDiscountPrice)); 
                discountedProducts[0].setDiscountPrice(0);
            }
            
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ThirdCheapestFree;
    }
}
