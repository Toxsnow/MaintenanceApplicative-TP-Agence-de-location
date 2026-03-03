package agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests unitaires pour la classe RentalAgency.
 */
class RentalAgencyTest {

    private RentalAgency agency;
    private Car car1;
    private Car car2;
    private Motorbike moto1;
    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        agency = new RentalAgency();
        car1 = new Car("Peugeot", "308", 2022, 5);
        car2 = new Car("Renault", "Clio", 2020, 5);
        moto1 = new Motorbike("Yamaha", "MT-07", 2021, 689);
        client1 = new Client("Jean", "Dupont", 1990);
        client2 = new Client("Marie", "Martin", 1985);
    }

    @Test
    void constructor_shouldCreateEmptyAgency() {
        assertThat(agency.getVehicles()).isEmpty();
    }

    @Test
    void constructor_withList_shouldCreateAgencyWithVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car1);
        vehicles.add(moto1);
        RentalAgency agencyWithVehicles = new RentalAgency(vehicles);
        assertThat(agencyWithVehicles.getVehicles()).hasSize(2);
    }

    @Test
    void add_shouldAddVehicle() {
        boolean result = agency.add(car1);
        assertThat(result).isTrue();
        assertThat(agency.getVehicles()).contains(car1);
    }

    @Test
    void add_shouldReturnFalse_whenVehicleAlreadyExists() {
        agency.add(car1);
        boolean result = agency.add(car1);
        assertThat(result).isFalse();
    }

    @Test
    void remove_shouldRemoveVehicle() {
        agency.add(car1);
        agency.remove(car1);
        assertThat(agency.getVehicles()).doesNotContain(car1);
    }

    @Test
    void remove_shouldThrowException_whenVehicleNotInAgency() {
        assertThatThrownBy(() -> agency.remove(car1))
                .isInstanceOf(UnknownVehicleException.class);
    }

    @Test
    void contains_shouldReturnTrue_whenVehicleInAgency() {
        agency.add(car1);
        assertThat(agency.contains(car1)).isTrue();
    }

    @Test
    void contains_shouldReturnFalse_whenVehicleNotInAgency() {
        assertThat(agency.contains(car1)).isFalse();
    }

    @Test
    void select_shouldReturnVehiclesMatchingCriterion() {
        agency.add(car1);
        agency.add(car2);
        agency.add(moto1);
        List<Vehicle> result = agency.select(new BrandCriterion("Peugeot"));
        assertThat(result).containsExactly(car1);
    }

    @Test
    void select_shouldReturnEmptyList_whenNoCriterionMatches() {
        agency.add(car1);
        List<Vehicle> result = agency.select(new BrandCriterion("BMW"));
        assertThat(result).isEmpty();
    }

    // Tests de location

    @Test
    void rentVehicle_shouldRentVehicleToClient() {
        agency.add(car1);
        double price = agency.rentVehicle(client1, car1);
        assertThat(price).isEqualTo(car1.dailyRentalPrice());
        assertThat(agency.aVehicleIsRentedBy(client1)).isTrue();
    }

    @Test
    void rentVehicle_shouldThrowException_whenVehicleNotInAgency() {
        assertThatThrownBy(() -> agency.rentVehicle(client1, car1))
                .isInstanceOf(UnknownVehicleException.class);
    }

    @Test
    void rentVehicle_shouldThrowException_whenClientAlreadyRentsVehicle() {
        agency.add(car1);
        agency.add(car2);
        agency.rentVehicle(client1, car1);
        assertThatThrownBy(() -> agency.rentVehicle(client1, car2))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rentVehicle_shouldThrowException_whenVehicleAlreadyRented() {
        agency.add(car1);
        agency.rentVehicle(client1, car1);
        assertThatThrownBy(() -> agency.rentVehicle(client2, car1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void aVehicleIsRentedBy_shouldReturnFalse_whenClientHasNoRental() {
        assertThat(agency.aVehicleIsRentedBy(client1)).isFalse();
    }

    @Test
    void vehicleIsRented_shouldReturnTrue_whenVehicleIsRented() {
        agency.add(car1);
        agency.rentVehicle(client1, car1);
        assertThat(agency.vehicleIsRented(car1)).isTrue();
    }

    @Test
    void vehicleIsRented_shouldReturnFalse_whenVehicleIsNotRented() {
        agency.add(car1);
        assertThat(agency.vehicleIsRented(car1)).isFalse();
    }

    @Test
    void returnVehicle_shouldEndRental() {
        agency.add(car1);
        agency.rentVehicle(client1, car1);
        agency.returnVehicle(client1);
        assertThat(agency.aVehicleIsRentedBy(client1)).isFalse();
        assertThat(agency.vehicleIsRented(car1)).isFalse();
    }

    @Test
    void returnVehicle_shouldDoNothing_whenClientHasNoRental() {
        agency.returnVehicle(client1);
        assertThat(agency.aVehicleIsRentedBy(client1)).isFalse();
    }

    @Test
    void allRentedVehicles_shouldReturnRentedVehicles() {
        agency.add(car1);
        agency.add(car2);
        agency.rentVehicle(client1, car1);
        agency.rentVehicle(client2, car2);
        Collection<Vehicle> rented = agency.allRentedVehicles();
        assertThat(rented).containsExactlyInAnyOrder(car1, car2);
    }

    @Test
    void allRentedVehicles_shouldReturnEmpty_whenNoRentals() {
        agency.add(car1);
        assertThat(agency.allRentedVehicles()).isEmpty();
    }
}
