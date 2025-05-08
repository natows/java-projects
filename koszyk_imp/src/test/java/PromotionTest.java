import org.junit.jupiter.api.Test;

import cart.Cart;
import finder.DefaultProductFinder;
import optimizer.CalculateBestCombination;
import product.Product;
import promotion.*;
import sorter.DefaultProductSorter;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;

public class PromotionTest {
    private Cart cart;
    private Product product1;
    private Product product2;
    

    @BeforeEach
    void setUp() {
        cart = new Cart(new DefaultProductSorter(), new DefaultProductFinder(), new CalculateBestCombination());
        product1 = new Product("P001", "Product 1", 150);
        product2 = new Product("P002", "Product 2", 200);
    }
    

    @Test 
    public void test5PercentDiscount(){
        cart.addProduct(product1);
        cart.addProduct(product2);
        assertEquals(350, cart.getTotal());
        cart.addPromotion(new Over300Discount());
        cart.applyPromotions();
        assertEquals(332.5, cart.getTotal());
    }

    @Test
    public void testFreeFirmCup() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        assertEquals(350, cart.getTotal());
        cart.addPromotion(new FreeFirmCup());
        cart.applyPromotions();
        Product[] products = cart.getProducts();
        boolean hasFreeFirmCup = false;
        for (Product product : products) {
            if (product.getName().equals("Free Firm Cup")) {
                hasFreeFirmCup = true;
                break;
            }
        }
        assertEquals(true, hasFreeFirmCup);
        assertEquals(350, cart.getTotal()); 
    }


    @Test
    public void testThirdCheapestFree() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(new Product("P003", "Product 3", 50));
        assertEquals(400, cart.getTotal());
        cart.addPromotion(new ThirdCheapestFree());
        cart.applyPromotions();
        assertEquals(350, cart.getTotal());

    }

    @Test
    public void testDiscountCoupon30Percent() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addPromotion(new DiscountCoupon(product2, 30));
        cart.applyPromotions();
        assertEquals(290, cart.getTotal());
    }



    @Test 
    public void testMultiplePromotions() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addProduct(new Product("P003", "Product 3", 50));
        cart.addPromotion(new Over300Discount()); //380
        cart.addPromotion(new FreeFirmCup());
        cart.addPromotion(new ThirdCheapestFree()); //332,5
        cart.addPromotion(new DiscountCoupon(product2, 30)); //275.5
        cart.applyPromotions();
        assertEquals(275.5, cart.getTotal());
        assertEquals(4, cart.getSize());
    }
    // dodaj kupon do tego wyzej a nizej sprawdz wszystkie promocje czy mozna dodac szczegolnie rozne kupony czy mozna
    @Test
    public void testAddTheSamePromotionTwice() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addPromotion(new Over300Discount());
        cart.addPromotion(new Over300Discount());
        cart.applyPromotions();
        assertEquals(332.5, cart.getTotal());
    }


    @Test
    public void testAddTheSameCouponTwice() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addPromotion(new DiscountCoupon(product2, 30));
        cart.applyPromotions();
        cart.addPromotion(new DiscountCoupon(product2, 30));
        cart.applyPromotions();
        assertEquals(290, cart.getTotal());
    }
    @Test
    public void testAddTwoDifferentCoupons() {
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addPromotion(new DiscountCoupon(product1, 30));
        cart.addPromotion(new DiscountCoupon(product2, 20));
        cart.applyPromotions();
        assertEquals(265, cart.getTotal());
    }

    @Test 
    public void testAddTheCupTwice(){
        cart.addProduct(product1);
        cart.addProduct(product2);
        cart.addPromotion(new FreeFirmCup());
        cart.applyPromotions();
        cart.addPromotion(new FreeFirmCup());
        cart.applyPromotions();
        assertEquals(3, cart.getSize());
    }

    @Test
    public void testOptimizerFindsBestCombination() {
        cart.addProduct(product1); // 150
        cart.addProduct(product2); // 200
        cart.addProduct(new Product("P003", "Product 3", 50)); // 50

        cart.addPromotion(new DiscountCoupon(product2, 30));
        cart.addPromotion(new ThirdCheapestFree()); 
        cart.addPromotion(new Over300Discount()); 
    

        cart.applyPromotions();
        assertEquals(290, cart.getTotal()); 

        // cart.optimizePromotions(new CalculateBestCombination());
        cart.optimizePromotions();
        cart.applyPromotions();

        assertEquals(275.5, cart.getTotal());
    }

}