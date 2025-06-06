package subject;
import observer.InflationObserver;

public interface Subject {
    void registerObserver(InflationObserver observer);
    void removeObserver(InflationObserver observer);
    void notifyObservers();

    
}
