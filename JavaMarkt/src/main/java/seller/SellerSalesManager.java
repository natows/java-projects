package seller;
import product.Product;

public class SellerSalesManager implements SalesManager {

    @Override
    public void restockProducts(Product[] products) {
        for (Product p : products) {
            int baseRestock = 1; 
            int demandBasedRestock = calculateDemandBasedRestock(p);
            int totalRestock = baseRestock + demandBasedRestock;
            
            p.setQuantity(p.getQuantity() + totalRestock);
            System.out.println("Restocked " + totalRestock + " of " + p.getName() + 
                            " (base: " + baseRestock + ", demand-based: " + demandBasedRestock + ")");
        }
    }

    @Override
    public double sellProduct(Product[] products, Product product, int quantity) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getCode() == product.getCode()) {
                if (products[i].getQuantity() >= quantity) {
                    products[i].setQuantity(products[i].getQuantity() - quantity);
                    double profit = products[i].getPrice() * quantity;
                    products[i].setSoldCount(products[i].getSoldCount() + quantity);
                    return profit;
                } 
                else {
                    int availableQuantity = products[i].getQuantity();
                    double profit = products[i].getPrice() * availableQuantity;
                    products[i].setQuantity(0);   
                    products[i].setSoldCount(products[i].getSoldCount() + availableQuantity); 
                    return profit;
                }
                
            }
        }
        return 0.0; 
    }



    @Override
    public int calculateDemandBasedRestock(Product product) {
        int soldLastTurn = product.getSoldCount(); 
        
        if (soldLastTurn >= 3) {
            return 3 + (int)(Math.random() * 2);
        } else if (soldLastTurn >= 1) {
            return 1 + (int)(Math.random() * 2); 
        } else {
            return (int)(Math.random() * 2); 
        }
    }


    
}
