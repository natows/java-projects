package subject;

import observer.OfferObserver;

public interface OfferSubject {
    void registerOfferObserver(OfferObserver observer);
    void removeOfferObserver(OfferObserver observer);
    void notifyOfferObservers();
}