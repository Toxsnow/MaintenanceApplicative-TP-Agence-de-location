package agency;

import java.util.function.Predicate;

/**
 * Critere de selection par prix maximum de location.
 */
public class MaxPriceCriterion implements Predicate<Vehicle> {

    private double maxPrice;

    /**
     * Constructeur du critere.
     * 
     * @param maxPrice le prix maximum de location journalier
     */
    public MaxPriceCriterion(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean test(Vehicle vehicle) {
        return vehicle.dailyRentalPrice() <= maxPrice;
    }
}
