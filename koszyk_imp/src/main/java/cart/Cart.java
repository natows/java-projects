package cart;
import java.util.Arrays;
import product.Product;
import promotion.Promotion;
import sorter.*;
import finder.*;
import optimizer.PromotionOptimizer;


public class Cart {
    private Product[] products;
    private int size;
    private double total;
    private Promotion[] promotions; 
    private int promotionCount;
    private ProductSorter productSorter;
    private ProductFinder productFinder; 
    private PromotionOptimizer promotionOptimizer;
    public Cart(ProductSorter productSorter, ProductFinder productFinder, PromotionOptimizer promotionOptimizer) {
        this.products = new Product[10];
        this.size = 0;
        this.total = 0;
        this.promotions = new Promotion[4];
        this.promotionCount = 0;
        this.productSorter = productSorter;
        this.productFinder = productFinder;
        this.promotionOptimizer = promotionOptimizer;
    }

    private void sortProducts() {
        productSorter.sort(products, size); 
    }

    public void setProductSorter(ProductSorter productSorter) {
        this.productSorter = productSorter;
    }

    public void addProduct( Product product) {
        if (size == products.length) {
            Product[] newProducts = new Product[products.length + 10];
            System.arraycopy(products, 0, newProducts, 0, products.length);
            products = newProducts;
        }
        products[size++] = product;
        total += product.getDiscountPrice();
        sortProducts();
    }

    public void removeProduct(Product product) {
        for (int i = 0; i< size; i ++) {
            if (products[i].equals(product)){
                for (int j = i; j < size - 1; j++) {
                    products[j] = products[j + 1];
                }
                products[--size] = null; 
                break;

            }
        }
        total -= product.getDiscountPrice();
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, size); 
    }

    public Promotion[] getPromotions() {
        return Arrays.copyOf(promotions, promotionCount); 
    }

    public double getTotal() {
        total = 0;
        for (int i = 0; i < size; i++) {
            total += products[i].getDiscountPrice();
        }
        return total;
    }

    public int getSize() {
        return size;
    }

    public void setProductFinder(ProductFinder productFinder) {
        this.productFinder = productFinder;
    }
    
    public Product getMostExpensiveProduct() {
        return productFinder.findMostExpensive(products, size);
    }

    public Product getCheapestProduct() {
        return productFinder.findCheapest(products, size);
    }

    public Product[] getNMostExpensiveProducts(int n) {
        return productFinder.findNMostExpensive(products, size, n);
    }

    public Product[] getNCheapestProducts(int n) {
        return productFinder.findNCheapest(products, size, n);
    }

    public void addPromotion(Promotion promotion) { 
        for (int i = 0; i < promotionCount; i++) {
            if (promotions[i] != null && promotions[i].equals(promotion)) {
                return; 
            }
        }
        if (promotionCount == promotions.length) {
            Promotion[] newPromotions = new Promotion[promotions.length + 5];
            System.arraycopy(promotions, 0, newPromotions, 0, promotions.length);
            promotions = newPromotions;
        }
        promotions[promotionCount++] = promotion;
    }

    public void applyPromotions() {
        for (int i = 0; i < size; i++) {
            products[i].setDiscountPrice(products[i].getPrice());
        }
        for (int i = 0; i < promotionCount; i++) {
            promotions[i].apply(this);
        }
    } 

    public void clearPromotions() {
        promotions = new Promotion[4]; 
        promotionCount = 0;
    }
    
    public void optimizePromotions() {
        if (promotionOptimizer != null) {
            promotionOptimizer.optimizePromotions(this);
        }
    }
}