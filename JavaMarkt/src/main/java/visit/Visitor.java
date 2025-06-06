package visit;
import buyer.Buyer;
import seller.Seller;

public interface Visitor {
    void visit(Buyer buyer);
    void visit(Seller seller);
  
}
