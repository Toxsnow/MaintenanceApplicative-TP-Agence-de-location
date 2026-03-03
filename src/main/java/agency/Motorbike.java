package agency;

/**
 * Classe representant une moto.
 */
public class Motorbike extends AbstractVehicle {

    private int cylinderCapacity;

    /**
     * Constructeur de la classe Motorbike.
     * 
     * @param brand            la marque de la moto
     * @param model            le modele de la moto
     * @param productionYear   l'annee de production
     * @param cylinderCapacity la cylindree en cm3
     * @throws IllegalArgumentException si l'annee ou la cylindree est invalide
     */
    public Motorbike(String brand, String model, int productionYear, int cylinderCapacity) {
        super(brand, model, productionYear);
        if (cylinderCapacity < 50) {
            throw new IllegalArgumentException("Cylindree invalide : " + cylinderCapacity);
        }
        this.cylinderCapacity = cylinderCapacity;
    }

    @Override
    public double dailyRentalPrice() {
        return 0.25 * cylinderCapacity;
    }

    @Override
    protected String getDetails() {
        return "(" + cylinderCapacity + "cm³)";
    }

    @Override
    protected String getTypeName() {
        return "Motorbike";
    }
}
