package optimizer;

import cart.Cart;
import product.Product;
import java.util.ArrayList;
import java.util.List;
import promotion.Promotion;

public class CalculateBestCombination implements PromotionOptimizer {

    @Override
    public void optimizePromotions(Cart cart) {
        Promotion[] promotions = cart.getPromotions();
        if (promotions.length == 0) return; 
        List<Promotion[]> allPermutations = new ArrayList<>();
        permutePromotions(promotions.clone(), 0, allPermutations);
        Product[] products = cart.getProducts();
        double[] originalPrices = new double[products.length];
        for (int i = 0; i < products.length; i++) {
            originalPrices[i] = products[i].getDiscountPrice();
        }
        
        double bestTotal = Double.MAX_VALUE;
        Promotion[] bestCombination = null;
        
        for (Promotion[] permutation : allPermutations) {
            cart.clearPromotions();
            resetPrices(cart, originalPrices);
            
            for (Promotion promotion : permutation) {
                cart.addPromotion(promotion);
            }
            cart.applyPromotions();
            
            double total = cart.getTotal();
            if (bestCombination == null || total < bestTotal) {
                bestTotal = total;
                bestCombination = permutation;
            }
        }
        
        cart.clearPromotions();
        resetPrices(cart, originalPrices);
        for (Promotion promotion : bestCombination) {
            cart.addPromotion(promotion);
        }
        cart.applyPromotions();
    }
    
    private void resetPrices(Cart cart, double[] originalPrices) {
        Product[] products = cart.getProducts();
        for (int i = 0; i < products.length; i++) {
            products[i].setDiscountPrice(originalPrices[i]);
        }
    }
    
    private void permutePromotions(Promotion[] array, int start, List<Promotion[]> result) {
        if (start == array.length - 1) {
            result.add(array.clone());
            return;
        }
        
        for (int i = start; i < array.length; i++) {
            swap(array, start, i);
            
            permutePromotions(array, start + 1, result);
            
            swap(array, start, i);
        }
    }
    
    private void swap(Promotion[] arr, int i, int j) {
        Promotion temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
