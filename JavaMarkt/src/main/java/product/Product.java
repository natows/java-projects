package product;



public class Product {
    public enum ProductType {
    ESSENTIAL,  
    LUXURY      
    }
    private int code;
    private String name;
    private double price;
    private ProductType category;
    private double makingCost;
    private int quantity;
    private int soldCount;

    public Product(int code, String name, double price, ProductType category) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
        this.makingCost = price * 0.5;
        this.soldCount = 0;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getCategory() {
        return category;
    }

    public double getMakingCost() {
        return makingCost;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public void resetSoldCount() {
        this.soldCount = 0;
    }
}
