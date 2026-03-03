package agency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Classe representant une agence de location de vehicules.
 */
public class RentalAgency {

    private List<Vehicle> vehicles;
    private Map<Client, Vehicle> rentedVehicles;

    /**
     * Constructeur d'une agence sans vehicules.
     */
    public RentalAgency() {
        this.vehicles = new ArrayList<>();
        this.rentedVehicles = new HashMap<>();
    }

    /**
     * Constructeur d'une agence avec une liste de vehicules.
     * 
     * @param vehicles la liste des vehicules
     */
    public RentalAgency(List<Vehicle> vehicles) {
        this.vehicles = new ArrayList<>(vehicles);
        this.rentedVehicles = new HashMap<>();
    }

    /**
     * Ajoute un vehicule a l'agence s'il n'est pas deja present.
     * 
     * @param vehicle le vehicule a ajouter
     * @return true si le vehicule a ete ajoute, false sinon
     */
    public boolean add(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            return false;
        }
        return vehicles.add(vehicle);
    }

    /**
     * Retire un vehicule de l'agence.
     * 
     * @param vehicle le vehicule a retirer
     * @throws UnknownVehicleException si le vehicule n'est pas dans l'agence
     */
    public void remove(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            throw new UnknownVehicleException(vehicle);
        }
        vehicles.remove(vehicle);
    }

    /**
     * Teste si un vehicule est dans l'agence.
     * 
     * @param vehicle le vehicule a rechercher
     * @return true si le vehicule est dans l'agence, false sinon
     */
    public boolean contains(Vehicle vehicle) {
        return vehicles.contains(vehicle);
    }

    /**
     * Retourne la liste des vehicules de l'agence.
     * 
     * @return la liste des vehicules
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Selectionne les vehicules satisfaisant un critere.
     * 
     * @param criterion le critere de selection
     * @return la liste des vehicules satisfaisant le critere
     */
    public List<Vehicle> select(Predicate<Vehicle> criterion) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (criterion.test(vehicle)) {
                result.add(vehicle);
            }
        }
        return result;
    }

    /**
     * Affiche les vehicules satisfaisant un critere.
     * 
     * @param criterion le critere de selection
     */
    public void printSelectedVehicles(Predicate<Vehicle> criterion) {
        for (Vehicle vehicle : select(criterion)) {
            System.out.println(vehicle);
        }
    }

    /**
     * Permet a un client de louer un vehicule.
     * 
     * @param client  le client qui loue
     * @param vehicle le vehicule a louer
     * @return le prix de location journalier
     * @throws UnknownVehicleException si le vehicule n'est pas dans l'agence
     * @throws IllegalStateException   si le vehicule est deja loue ou si le client loue deja un vehicule
     */
    public double rentVehicle(Client client, Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            throw new UnknownVehicleException(vehicle);
        }
        if (rentedVehicles.containsKey(client)) {
            throw new IllegalStateException("Le client loue deja un vehicule");
        }
        if (rentedVehicles.containsValue(vehicle)) {
            throw new IllegalStateException("Le vehicule est deja loue");
        }
        rentedVehicles.put(client, vehicle);
        return vehicle.dailyRentalPrice();
    }

    /**
     * Verifie si un client loue actuellement un vehicule.
     * 
     * @param client le client a verifier
     * @return true si le client loue un vehicule, false sinon
     */
    public boolean aVehicleIsRentedBy(Client client) {
        return rentedVehicles.containsKey(client);
    }

    /**
     * Verifie si un vehicule est actuellement loue.
     * 
     * @param vehicle le vehicule a verifier
     * @return true si le vehicule est loue, false sinon
     */
    public boolean vehicleIsRented(Vehicle vehicle) {
        return rentedVehicles.containsValue(vehicle);
    }

    /**
     * Le client rend le vehicule qu'il a loue.
     * 
     * @param client le client qui rend le vehicule
     */
    public void returnVehicle(Client client) {
        rentedVehicles.remove(client);
    }

    /**
     * Retourne la collection des vehicules actuellement loues.
     * 
     * @return la collection des vehicules loues
     */
    public Collection<Vehicle> allRentedVehicles() {
        return rentedVehicles.values();
    }
}
