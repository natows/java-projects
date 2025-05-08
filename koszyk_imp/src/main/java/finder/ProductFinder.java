package finder;

import product.Product;

public interface ProductFinder {
    Product findMostExpensive(Product[] products, int size); 
    Product findCheapest(Product[] products, int size); 
    Product[] findNMostExpensive(Product[] products, int size, int n); 
    Product[] findNCheapest(Product[] products, int size, int n); 
}
