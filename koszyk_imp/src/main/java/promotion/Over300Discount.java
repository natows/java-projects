package promotion;

import cart.Cart;
import product.Product;
public class Over300Discount implements Promotion {
    @Override
    public void apply(Cart cart) {
        if (cart.getTotal() > 300) {
            for (int i=0; i< cart.getSize(); i++) {
                Product product = cart.getProducts()[i];
                product.setDiscountPrice(product.getDiscountPrice() * 0.95); 
            }

        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Over300Discount;
    }
}