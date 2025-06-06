package subject;

import observer.MarketObserver;

public interface MarketSubject {
    void registerMarketObserver(MarketObserver observer);
    void removeMarketObserver(MarketObserver observer);
    void notifyMarketObservers();
}