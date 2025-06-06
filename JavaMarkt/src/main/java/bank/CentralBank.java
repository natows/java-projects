package bank;
import subject.Subject;
import observer.InflationObserver;
import observer.MarketObserver;
public class CentralBank implements Subject, MarketObserver {
    private double inflationRate;
    private InflationObserver[] observers;
    private int observerCount;
    private double desiredTaxIncome;
    private double totalTurnover;
    private double adjustmentRate;
    private InflationCalculationStrategy inflationCalculationStrategy;


    public CentralBank(double inflationRate) {
        this.inflationRate = inflationRate;
        this.observers = new InflationObserver[10]; 
        this.desiredTaxIncome = 0.01; 
        this.observerCount = 0;
        this.totalTurnover = 0;
        this.adjustmentRate = 0.2; 
        this.inflationCalculationStrategy = new InflationStrategy(adjustmentRate, 0.4, 0.01, 0.01, 1.03);
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(double inflationRate) {
        this.inflationRate = inflationRate;
        notifyObservers();
    }

    public void setTotalTurnover(double turnover) {
        this.totalTurnover = turnover;
    }

    public void setDesiredTaxIncome(double desiredTaxIncome) {
        this.desiredTaxIncome = desiredTaxIncome;
    }
    private void addObserver(InflationObserver observer) {
        if (observerCount == observers.length) {
            InflationObserver[] newObservers = new InflationObserver[observers.length + 10];
            System.arraycopy(observers, 0, newObservers, 0, observers.length);
            observers = newObservers;
        }
        observers[observerCount++] = observer;
    }


    @Override
    public void registerObserver(InflationObserver observer) {
        if (observer instanceof InflationObserver) {
            addObserver(observer);
        }
    }

    @Override
    public void removeObserver(InflationObserver observer) {
        for (int i = 0; i < observerCount; i++) {
            if (observers[i] == observer) {
                observers[i] = observers[--observerCount];
                observers[observerCount] = null;
                break;
            }
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observerCount; i++) {
            if (observers[i] instanceof InflationObserver) {
                ((InflationObserver) observers[i]).updateInflationRate(inflationRate);
            }
        }
    }


    public void countInflation() {
        double newInflationRate = inflationCalculationStrategy.calculateInflation(
            inflationRate, totalTurnover, desiredTaxIncome);
        setInflationRate(newInflationRate);
    }



    public InflationObserver[] getObservers(){
        return observers;
    }

    public int getObserverCount() {
        return observerCount;
    }


    @Override 
    public void updateTurnover(double turnover) {
        totalTurnover += turnover;
    } 
       

    public double getTurnover() {
        return totalTurnover;
    }


    
}
