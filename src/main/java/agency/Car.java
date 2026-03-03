package agency;

import util.TimeProvider;

/**
 * Classe representant une voiture.
 */
public class Car extends AbstractVehicle {

    private int numberOfSeats;

    /**
     * Constructeur de la classe Car.
     * 
     * @param brand          la marque de la voiture
     * @param model          le modele de la voiture
     * @param productionYear l'annee de production
     * @param numberOfSeats  le nombre de sieges
     * @throws IllegalArgumentException si l'annee ou le nombre de sieges est invalide
     */
    public Car(String brand, String model, int productionYear, int numberOfSeats) {
        super(brand, model, productionYear);
        if (numberOfSeats < 1) {
            throw new IllegalArgumentException("Nombre de sieges invalide : " + numberOfSeats);
        }
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Indique si la voiture est un modele recent (5 ans ou moins).
     * 
     * @return true si la voiture a 5 ans ou moins
     */
    public boolean isNew() {
        return TimeProvider.currentYearValue() - productionYear <= 5;
    }

    @Override
    public double dailyRentalPrice() {
        if (isNew()) {
            return 40.0 * numberOfSeats;
        }
        return 20.0 * numberOfSeats;
    }

    @Override
    protected String getDetails() {
        if (numberOfSeats == 1) {
            return "(1 seat)";
        }
        return "(" + numberOfSeats + " seats)";
    }

    @Override
    protected String getTypeName() {
        return "Car";
    }
}
