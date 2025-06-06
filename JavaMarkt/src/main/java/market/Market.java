package market;

import bank.CentralBank;
import buyer.Buyer;
import seller.Seller;
import product.Product;
import visit.Visitor;
import visit.MarketVisitor;


public class Market {
    private CentralBank centralBank;
    private Seller[] sellers;
    private Buyer[] buyers;
    private int currentTurn = 0;
    private Visitor visitor;


    public Market(CentralBank centralBank, Seller[] sellers, Buyer[] buyers) {
        this.centralBank = centralBank;
        this.sellers = sellers;
        this.buyers = buyers;
        this.visitor = new MarketVisitor();

    }

    public void runTurn() {
        currentTurn++;
        System.out.println("Turn " + currentTurn + " started.");
        centralBank.setTotalTurnover(0);

        for (Buyer buyer : buyers) {
            if (currentTurn % 3 == 0) {
                visitor.visit(buyer);
            }
            for (Seller seller : sellers) {
                seller.registerOfferObserver(buyer);
            }
        }

        for (Seller seller : sellers) {
            for (Product product : seller.getProducts()) {
                product.resetSoldCount();
            }
            seller.registerMarketObserver(centralBank);
            visitor.visit(seller); 
        }

        



        centralBank.countInflation();
        double totalTurnover = centralBank.getTurnover(); 

        System.out.println("Turn " + currentTurn + " ended.");
        System.out.println("Total turnover: " + totalTurnover);
        System.out.println("Current inflation rate: " + centralBank.getInflationRate());
        System.out.println("-----------------------------------");
    }


    public void runSimulation(int numberOfTurns) {
        for (int i = 0; i < numberOfTurns; i++) {
            runTurn();
        }
    }
}

