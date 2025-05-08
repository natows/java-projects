package sorter;
import product.Product;
import java.util.Arrays;

public class DefaultProductSorter implements ProductSorter {
    @Override
    public void sort(Product[] products, int size) {
        if (size <= 1) {
            return; 
        }
        Arrays.sort(products, 0, size, (p1, p2) -> {
            int comparison =  Double.compare(p2.getDiscountPrice(), p1.getDiscountPrice());

            if (comparison ==0 ) {
                return p1.getName().compareTo(p2.getName());
            } 

            return comparison;

        });
    }
}
