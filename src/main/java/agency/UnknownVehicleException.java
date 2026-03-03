package agency;

/**
 * Exception levee lorsqu'un vehicule n'est pas trouve dans l'agence.
 */
public class UnknownVehicleException extends RuntimeException {

    private Vehicle vehicle;

    /**
     * Constructeur de l'exception.
     * 
     * @param vehicle le vehicule non trouve
     */
    public UnknownVehicleException(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String getMessage() {
        return "Le vehicule n'existe pas dans l'agence : " + vehicle.toString();
    }
}
