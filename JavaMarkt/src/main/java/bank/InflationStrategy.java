package bank;
public class InflationStrategy implements InflationCalculationStrategy {
    private final double adjustmentRate;
    private final double maxInflation;
    private final double minInflation;
    private final double lowTurnoverThreshold;
    private final double emergencyMultiplier;
    
    public InflationStrategy(double adjustmentRate, double maxInflation, 
                                   double minInflation, double lowTurnoverThreshold, 
                                   double emergencyMultiplier) {
        this.adjustmentRate = adjustmentRate;
        this.maxInflation = maxInflation;
        this.minInflation = minInflation;
        this.lowTurnoverThreshold = lowTurnoverThreshold;
        this.emergencyMultiplier = emergencyMultiplier;
    }
    
    @Override
    public double calculateInflation(double currentInflation, double totalTurnover, double desiredTaxIncome) {
        if (totalTurnover <= lowTurnoverThreshold) {
            return Math.min(maxInflation, currentInflation * emergencyMultiplier);
        } else {
            double targetInflation = desiredTaxIncome / totalTurnover;
            targetInflation = Math.min(targetInflation, maxInflation);
            double adjustedInflation = currentInflation + adjustmentRate * (targetInflation - currentInflation);
            return Math.max(minInflation, Math.min(maxInflation, adjustedInflation));
        }
    }
}