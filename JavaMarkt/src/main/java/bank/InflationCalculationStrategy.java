package bank;

public interface InflationCalculationStrategy {
    double calculateInflation(double currentInflation, double totalTurnover, double desiredTaxIncome);
}
