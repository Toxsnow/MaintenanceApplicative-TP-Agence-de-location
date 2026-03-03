package agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests unitaires pour la classe Car.
 */
class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("Peugeot", "308", 2022, 5);
    }

    @Test
    void constructor_shouldCreateCar() {
        assertThat(car.getBrand()).isEqualTo("Peugeot");
        assertThat(car.getModel()).isEqualTo("308");
        assertThat(car.getProductionYear()).isEqualTo(2022);
    }

    @Test
    void constructor_shouldThrowException_whenYearBefore1900() {
        assertThatThrownBy(() -> new Car("Peugeot", "308", 1899, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1899");
    }

    @Test
    void constructor_shouldThrowException_whenYearAfterCurrentYear() {
        assertThatThrownBy(() -> new Car("Peugeot", "308", 2030, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("2030");
    }

    @Test
    void constructor_shouldThrowException_whenSeatsLessThanOne() {
        assertThatThrownBy(() -> new Car("Peugeot", "308", 2022, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0");
    }

    @Test
    void isNew_shouldReturnTrue_whenCarIsFiveYearsOrLess() {
        Car newCar = new Car("Peugeot", "308", 2022, 5);
        assertThat(newCar.isNew()).isTrue();
    }

    @Test
    void isNew_shouldReturnFalse_whenCarIsOlderThanFiveYears() {
        Car oldCar = new Car("Peugeot", "308", 2015, 5);
        assertThat(oldCar.isNew()).isFalse();
    }

    @Test
    void dailyRentalPrice_shouldReturn40PerSeat_whenCarIsNew() {
        Car newCar = new Car("Peugeot", "308", 2022, 5);
        assertThat(newCar.dailyRentalPrice()).isEqualTo(200.0);
    }

    @Test
    void dailyRentalPrice_shouldReturn20PerSeat_whenCarIsOld() {
        Car oldCar = new Car("Peugeot", "308", 2015, 5);
        assertThat(oldCar.dailyRentalPrice()).isEqualTo(100.0);
    }

    @Test
    void toString_shouldContainCarInfo() {
        String result = car.toString();
        assertThat(result).contains("Car");
        assertThat(result).contains("Peugeot");
        assertThat(result).contains("308");
        assertThat(result).contains("2022");
        assertThat(result).contains("5 seats");
        assertThat(result).contains("€");
    }

    @Test
    void toString_shouldUseSingular_whenOneSeat() {
        Car singleSeat = new Car("Smart", "Fortwo", 2020, 1);
        assertThat(singleSeat.toString()).contains("1 seat)");
    }

    @Test
    void equals_shouldReturnTrue_whenSameCar() {
        Car car2 = new Car("Peugeot", "308", 2022, 4);
        assertThat(car).isEqualTo(car2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentBrand() {
        Car car2 = new Car("Renault", "308", 2022, 5);
        assertThat(car).isNotEqualTo(car2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentModel() {
        Car car2 = new Car("Peugeot", "208", 2022, 5);
        assertThat(car).isNotEqualTo(car2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentYear() {
        Car car2 = new Car("Peugeot", "308", 2021, 5);
        assertThat(car).isNotEqualTo(car2);
    }

    @Test
    void equals_shouldReturnFalse_whenNull() {
        assertThat(car).isNotEqualTo(null);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentClass() {
        Motorbike motorbike = new Motorbike("Peugeot", "308", 2022, 500);
        assertThat(car).isNotEqualTo(motorbike);
    }
}
