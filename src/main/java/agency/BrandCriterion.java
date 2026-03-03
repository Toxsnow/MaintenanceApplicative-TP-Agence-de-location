package agency;

import java.util.function.Predicate;

/**
 * Critere de selection par marque de vehicule.
 */
public class BrandCriterion implements Predicate<Vehicle> {

    private String brand;

    /**
     * Constructeur du critere.
     * 
     * @param brand la marque recherchee
     */
    public BrandCriterion(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean test(Vehicle vehicle) {
        return vehicle.getBrand().equals(brand);
    }
}
