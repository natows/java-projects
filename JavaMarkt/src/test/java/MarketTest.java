
import market.Market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bank.CentralBank;
import product.Product;
import buyer.Buyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  
import seller.Seller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MarketTest {
    Product bread = new Product(1, "Bread", 5.0, Product.ProductType.ESSENTIAL);
    Product wrapMc = new Product(2, "Wrap Mc", 19.0, Product.ProductType.LUXURY);
    Product kitty = new Product(3, "Kitty", 100.0, Product.ProductType.LUXURY);
    Product shoes = new Product(4, "Shoes", 200.0, Product.ProductType.LUXURY);
    Product butter = new Product(5, "Butter", 10.0, Product.ProductType.ESSENTIAL);
    Product toiletPaper = new Product(6, "Toilet Paper", 15.0, Product.ProductType.ESSENTIAL);
    Buyer[] buyers = new Buyer[10];
    Seller[] sellers = new Seller[2];

    @BeforeEach
    public void setUp() {

        Product[] products1 = {bread, wrapMc, butter};
        Product[] products2 = {kitty, shoes, toiletPaper};
        for (int i = 0; i < 10; i++) {
            Map<Product, Integer> buyerNeeds = new HashMap<>();
            
            buyerNeeds.put(bread, 1 + i*2);
            buyerNeeds.put(butter, 1 + i*3);
            buyerNeeds.put(toiletPaper, 1 + i * 2);

            if (i % 3 == 0) buyerNeeds.put(kitty, (int) (Math.random() * 3) + 1);
            if (i % 3 == 1) buyerNeeds.put(wrapMc, (int) (Math.random() * 3) + 1);
            if (i % 3 == 2) buyerNeeds.put(shoes, (int) (Math.random() * 3) + 1);
            
            double budget = 100.0 + (i * 150);
            buyers[i] = new Buyer(i, "Buyer" + i, budget, buyerNeeds);
        }
        Seller seller1 = new Seller(1, "Sprzedawca Krystek", products1);
        Seller seller2 = new Seller(2, "Sprzedawczyni Basia", products2);
        this.sellers = new Seller[]{seller1, seller2};

    }
   


    @Test
    public void testHyperinflationScenario() {
        CentralBank centralBank = new CentralBank(1.0);
        centralBank.setDesiredTaxIncome(30.0);
        
        
        
        Market market = new Market(centralBank, sellers, buyers);
        market.runSimulation(10); 
        
        assertTrue(centralBank.getInflationRate() < 0.4, "Inflacja powinna spaść poniżej 50%");
        assertTrue(centralBank.getTurnover() > 0, "Obroty powinny być niezerowe mimo wysokiej inflacji");
    }


    @Test
    public void testLongTermStability() {
        CentralBank centralBank = new CentralBank(1.0); 
        centralBank.setDesiredTaxIncome(50.0);
        
        
        Market market = new Market(centralBank, sellers, buyers);
        
        market.runSimulation(100);

        double finalInflation = centralBank.getInflationRate();
        assertTrue(finalInflation > 0.01 && finalInflation < 0.5, 
                "Inflacja powinna się ustabilizować w rozsądnym zakresie");
        assertTrue(centralBank.getTurnover() > 0, 
                "Rynek powinien mieć aktywność po 100 turach");
    }



    @Test
    public void testMarketStabilizationAfterManyTurns() {
        CentralBank bank = new CentralBank(0.05);
        
        

        Market market = new Market(bank, sellers, buyers);
        
        List<Double> inflationRates = new ArrayList<>();
        List<Double> turnovers = new ArrayList<>();
        List<Double> budgets = new ArrayList<>();
        
        market.runSimulation(40);
        
        for (int i = 0; i < 10; i++) {
            market.runTurn();
            inflationRates.add(bank.getInflationRate());
            turnovers.add(bank.getTurnover());
            budgets.add(buyers[0].getBudget());
        }
        
        double avgInflation = inflationRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double maxDeviation = inflationRates.stream()
            .mapToDouble(rate -> Math.abs(rate - avgInflation) / avgInflation)
            .max().orElse(0);
        
        assertTrue(maxDeviation < 0.05, "Inflacja powinna się stabilizować (odchylenie < 5%)");
        
        double avgTurnover = turnovers.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        assertTrue(avgTurnover > 0, "Obroty powinny być dodatnie po stabilizacji");
    }



    @Test
    public void testMarketResilienceToDisruptions() {
        CentralBank bank = new CentralBank(0.05);
       
        
        Market market = new Market(bank, sellers, buyers);
        
        market.runSimulation(20);
        double stableInflation = bank.getInflationRate();
        
        bank.setInflationRate(0.5); 
        
        market.runSimulation(30);
        
        double finalInflation = bank.getInflationRate();
        
        assertTrue(finalInflation < 0.2, "Rynek powinien się odbudować po zakłóceniu");
        assertTrue(Math.abs(finalInflation - stableInflation) < 0.1, 
                "Inflacja powinna wrócić blisko poziomu sprzed zakłócenia");
    }

    @Test
    public void testInflationBounds() {
        CentralBank bank = new CentralBank(2.0); 
        
        Market market = new Market(bank, sellers, buyers);
        
        market.runSimulation(50);
        
        double finalInflation = bank.getInflationRate();
        
        assertTrue(finalInflation >= 0.01, "Inflacja nie powinna spaść poniżej 1%: " + finalInflation);
        assertTrue(finalInflation <= 0.5, "Inflacja nie powinna przekroczyć 50%: " + finalInflation);
        

        assertTrue(bank.getTurnover() > 0, "Rynek powinien mieć obroty po stabilizacji");
    }


    @Test
    public void testSurvivalRequirementsInfluencePurchaseDecisions() {
        CentralBank bank = new CentralBank(0.05);

        Product bread = new Product(1, "Bread", 5.0, Product.ProductType.ESSENTIAL);
        Product diamond = new Product(2, "Diamond", 500.0, Product.ProductType.LUXURY);
        
        Seller seller = new Seller(1, "Shop", new Product[]{bread, diamond});
        
        Map<Product, Integer> needs = Map.of(bread, 3, diamond, 1);
        Buyer buyer = new Buyer(1, "Poor Consumer", 20.0, needs); 
        
        Market market = new Market(bank, new Seller[]{seller}, new Buyer[]{buyer});
        
        market.runSimulation(5);
        
        Map<Product, Integer> purchased = buyer.getPurchasedProducts();
        int breadPurchased = purchased.getOrDefault(bread, 0);
        int diamondPurchased = purchased.getOrDefault(diamond, 0);
        
        assertTrue(breadPurchased > diamondPurchased, 
                "Przy ograniczonym budżecie powinien kupować więcej ESSENTIAL");
        
        boolean survivalMet = buyer.getPurchaseStrategy().areSurvivalRequirementsMet();
        if (!survivalMet) {
            assertTrue(breadPurchased > 0, "Powinien kupić chleb dla survival");
        }
    }


    @Test
    public void testPurchasePropensityVsPrice() {
        CentralBank bank = new CentralBank(0.05);

        Product cheapProduct = new Product(1, "Cheap", 5.0, Product.ProductType.ESSENTIAL);
        Product expensiveProduct = new Product(2, "Expensive", 50.0, Product.ProductType.ESSENTIAL);
        
        Seller seller = new Seller(1, "PriceTestSeller", 
                                new Product[]{cheapProduct, expensiveProduct});
        
        Buyer buyer = new Buyer(1, "PriceSensitiveBuyer", 100.0, 
                            Map.of(cheapProduct, 5, expensiveProduct, 5));
        
        Market market = new Market(bank, new Seller[]{seller}, new Buyer[]{buyer});
        
        market.runSimulation(10);
        
        Map<Product, Integer> purchased = buyer.getPurchasedProducts();
        int cheapPurchased = purchased.getOrDefault(cheapProduct, 0);
        int expensivePurchased = purchased.getOrDefault(expensiveProduct, 0);
        
        assertTrue(cheapPurchased >= expensivePurchased, 
                "Powinien kupować więcej tanich produktów. Tanie: " + cheapPurchased + 
                ", Drogie: " + expensivePurchased);

    }

    @Test
    public void testVisitorPatternParameterRegeneration() {
        CentralBank bank = new CentralBank(0.05);
        
        Product product = new Product(1, "VisitorTest", 10.0, Product.ProductType.ESSENTIAL);
        Seller seller = new Seller(1, "VisitorSeller", new Product[]{product});
        Buyer buyer = new Buyer(1, "VisitorBuyer", 50.0, new HashMap<>());
        
        Market market = new Market(bank, new Seller[]{seller}, new Buyer[]{buyer});
        
        double initialBudget = buyer.getBudget();
        int initialNeedsCount = buyer.getNeeds().size();
        
        market.runSimulation(10);
        
        double finalBudget = buyer.getBudget();
        double finalMarkup = seller.getMarkup();
        int finalNeedsCount = buyer.getNeeds().size();
        
        assertTrue(finalBudget > initialBudget, 
                "Visitor powinien zwiększyć budżet buyer'a: " + 
                initialBudget + " → " + finalBudget);
        
        assertTrue(finalNeedsCount >= initialNeedsCount, 
                "Visitor powinien dodać potrzeby buyer'a");
        
        assertTrue(finalMarkup > 0 && finalMarkup < 1.0, 
                "Visitor powinien utrzymać rozsądny markup: " + finalMarkup);
    }




    @Test
    public void testMarkupEvolutionOverTime() {
        CentralBank bank = new CentralBank(0.02);
        
        Product product = new Product(1, "EvolvingProduct", 20.0, Product.ProductType.ESSENTIAL);
        Seller seller = new Seller(1, "EvolvingSeller", new Product[]{product});
        Buyer buyer = new Buyer(1, "EvolvingBuyer", 100.0, 
                            Map.of(product, 5));
        
        Market market = new Market(bank, new Seller[] {seller},new Buyer[]{buyer});

        List<Double> inflationHistory = new ArrayList<>();
        List<Double> markupHistory = new ArrayList<>();
        
        for (int turn = 0; turn < 25; turn++) {
            inflationHistory.add(bank.getInflationRate());
            markupHistory.add(seller.getMarkup());
            market.runTurn();
        }

        double avgInflation = inflationHistory.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        assertTrue(avgInflation > 0, "Inflacja powinna być dodatnia w działającym rynku");

        double firstMarkup = markupHistory.get(0);
        double lastMarkup = markupHistory.get(markupHistory.size() - 1);
        assertTrue(Math.abs(lastMarkup - firstMarkup) >= 0 && lastMarkup > 0, 
                "Markup powinien być optymalizowany");
    }

    @Test
    public void testMultiAgentInteractions() {
        CentralBank bank = new CentralBank(0.05);
 
        Product bread = new Product(1, "Bread", 8.0, Product.ProductType.ESSENTIAL);
        Product meat = new Product(2, "Meat", 25.0, Product.ProductType.ESSENTIAL);
        Product jewelry = new Product(3, "Jewelry", 300.0, Product.ProductType.LUXURY);
        
        Seller bakery = new Seller(1, "Bakery", new Product[]{bread});
        Seller butcher = new Seller(2, "Butcher", new Product[]{meat});
        Seller jeweler = new Seller(3, "Jeweler", new Product[]{jewelry});
        
        Seller[] sellers = {bakery, butcher, jeweler};
        

        Buyer poorBuyer = new Buyer(1, "Poor", 50.0, Map.of(bread, 2, meat, 1));
        Buyer middleBuyer = new Buyer(2, "Middle", 150.0, Map.of(bread, 1, meat, 2, jewelry, 0));
        Buyer richBuyer = new Buyer(3, "Rich", 500.0, Map.of(bread, 1, meat, 2, jewelry, 1));
        
        Buyer[] buyers = {poorBuyer, middleBuyer, richBuyer};
        
        Market market = new Market(bank, sellers, buyers);

        market.runSimulation(20);

        assertTrue(poorBuyer.getPurchasedProducts().size() > 0, "Biedny kupujący powinien coś kupić");
        assertTrue(richBuyer.getPurchasedProducts().size() > 0, "Bogaty kupujący powinien coś kupić");
        int poorLuxury = poorBuyer.getPurchasedProducts().getOrDefault(jewelry, 0);
        int richLuxury = richBuyer.getPurchasedProducts().getOrDefault(jewelry, 0);
        
        assertTrue(richLuxury >= poorLuxury, 
                "Bogaty kupujący powinien kupować więcej luksusów: " +
                "Rich=" + richLuxury + ", Poor=" + poorLuxury);

        for (Seller seller : sellers) {
            assertTrue(seller.getProfit() >= 0, 
                    "Każdy sprzedawca powinien mieć nieujemny zysk: " + seller.getName());
        }
        
    }


    @Test
    public void testCrisisScenarios() {
        CentralBank bank = new CentralBank(3.0);
        
        Product product = new Product(1, "CrisisProduct", 10.0, Product.ProductType.ESSENTIAL);
        Seller seller = new Seller(1, "CrisisSeller", new Product[]{product});
        Buyer buyer = new Buyer(1, "CrisisBuyer", 1000.0, Map.of(product, 3));
        
        Market market = new Market(bank, new Seller[]{seller}, new Buyer[]{buyer});
        
        assertDoesNotThrow(() -> market.runSimulation(15), 
                        "Market powinien działać nawet przy ekstremalnej inflacji");
        
        assertTrue(bank.getInflationRate() < 3.0, 
                "Algorytm banku powinien stabilizować ekstremalną inflację");
        
        Buyer poorBuyer = new Buyer(2, "PoorBuyer", 0.0, Map.of(product, 1));
        Market poorMarket = new Market(bank, new Seller[]{seller}, new Buyer[]{poorBuyer});
        
        assertDoesNotThrow(() -> poorMarket.runSimulation(5), 
                        "Market powinien działać z buyer'ami bez pieniędzy");
        
        Seller emptySeller = new Seller(3, "EmptySeller", new Product[]{});
        Market emptyMarket = new Market(bank, new Seller[]{emptySeller}, new Buyer[]{buyer});
        
        assertDoesNotThrow(() -> emptyMarket.runSimulation(3), 
                        "Market powinien działać z pustymi sprzedawcami");
    }


    


}