package finder;

import product.Product;

import java.util.Arrays;

public class DefaultProductFinder implements ProductFinder {
    
    @Override
    public Product findMostExpensive(Product[] products, int size) {
        if (size <= 0) return null;
        
        Product mostExpensive = products[0];
        for (int i = 1; i < size; i++) {
            if (products[i].getDiscountPrice() > mostExpensive.getDiscountPrice()) {
                mostExpensive = products[i];
            }
        }
        return mostExpensive;
    }
    
    @Override
    public Product findCheapest(Product[] products, int size) {
        if (size <= 0) return null;
        
        Product cheapest = products[0];
        for (int i = 1; i < size; i++) {
            if (products[i].getDiscountPrice() < cheapest.getDiscountPrice()) {
                cheapest = products[i];
            }
        }
        return cheapest;
    }
    
    @Override
    public Product[] findNMostExpensive(Product[] products, int size, int n) {
        if (size == 0|| n <= 0) {
            return new Product[0];
        }
        if (n >= size) {
            return Arrays.copyOf(products, size);
        }
        Product[] copy = Arrays.copyOf(products, size);
        Arrays.sort(copy, 0, size, (p1, p2) -> Double.compare(p2.getDiscountPrice(), p1.getDiscountPrice()));
        
        return Arrays.copyOfRange(copy, 0, n);
    }

    
    @Override
    public Product[] findNCheapest(Product[] products, int size, int n) {
        if (size== 0 || n <= 0) {
            return new Product[0];
        }
        if (n >= size) {
            return Arrays.copyOf(products, size);
        }
        Product[] copy = Arrays.copyOf(products, size);
        Arrays.sort(copy, 0, size, (p1, p2) -> Double.compare(p1.getDiscountPrice(), p2.getDiscountPrice()));

        return Arrays.copyOfRange(copy, 0, n);
        
       
    }
}
