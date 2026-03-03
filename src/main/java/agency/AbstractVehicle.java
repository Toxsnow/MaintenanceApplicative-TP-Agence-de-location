package agency;

import util.TimeProvider;

/**
 * Classe abstraite contenant le code commun aux vehicules.
 */
public abstract class AbstractVehicle implements Vehicle {

    protected String brand;
    protected String model;
    protected int productionYear;

    /**
     * Constructeur de la classe abstraite.
     * 
     * @param brand          la marque du vehicule
     * @param model          le modele du vehicule
     * @param productionYear l'annee de production
     * @throws IllegalArgumentException si l'annee est invalide
     */
    protected AbstractVehicle(String brand, String model, int productionYear) {
        if (productionYear < 1900 || productionYear > TimeProvider.currentYearValue()) {
            throw new IllegalArgumentException("Annee de production invalide : " + productionYear);
        }
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getProductionYear() {
        return productionYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractVehicle other = (AbstractVehicle) o;
        return productionYear == other.productionYear
                && brand.equals(other.brand)
                && model.equals(other.model);
    }

    /**
     * Retourne les details specifiques du vehicule pour toString.
     * 
     * @return les details entre parentheses
     */
    protected abstract String getDetails();

    /**
     * Retourne le nom du type de vehicule.
     * 
     * @return le type de vehicule
     */
    protected abstract String getTypeName();

    @Override
    public String toString() {
        return getTypeName() + " " + brand + " " + model + " " + productionYear
                + " " + getDetails() + " : " + dailyRentalPrice() + "€";
    }
}
