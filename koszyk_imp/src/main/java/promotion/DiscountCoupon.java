package promotion;
import cart.Cart;
import product.Product;

public class DiscountCoupon implements Promotion {
    private final Product product;
    private double discountPercentage;

    public DiscountCoupon(Product product, double discountPercentage) {
        this.product = product;
        this.discountPercentage = discountPercentage;

    }
    @Override
    public void apply(Cart cart) {
        Product[] products = cart.getProducts();
        for (int i=0; i< products.length; i ++) {
            if (products[i].getCode().equals(product.getCode())) {
                double discount = products[i].getDiscountPrice() * (discountPercentage / 100);
                products[i].setDiscountPrice(products[i].getDiscountPrice() - discount); 
            }
        }

        
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DiscountCoupon other = (DiscountCoupon) obj;
        return product != null && product.getCode()==other.product.getCode()
            && Math.abs(this.discountPercentage - other.discountPercentage) < 0.0001;
    }
    
}
