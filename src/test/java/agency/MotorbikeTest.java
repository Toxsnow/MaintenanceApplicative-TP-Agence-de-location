package agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests unitaires pour la classe Motorbike.
 */
class MotorbikeTest {

    private Motorbike motorbike;

    @BeforeEach
    void setUp() {
        motorbike = new Motorbike("Yamaha", "MT-07", 2022, 689);
    }

    @Test
    void constructor_shouldCreateMotorbike() {
        assertThat(motorbike.getBrand()).isEqualTo("Yamaha");
        assertThat(motorbike.getModel()).isEqualTo("MT-07");
        assertThat(motorbike.getProductionYear()).isEqualTo(2022);
    }

    @Test
    void constructor_shouldThrowException_whenYearBefore1900() {
        assertThatThrownBy(() -> new Motorbike("Yamaha", "MT-07", 1899, 500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1899");
    }

    @Test
    void constructor_shouldThrowException_whenYearAfterCurrentYear() {
        assertThatThrownBy(() -> new Motorbike("Yamaha", "MT-07", 2030, 500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("2030");
    }

    @Test
    void constructor_shouldThrowException_whenCylinderCapacityLessThan50() {
        assertThatThrownBy(() -> new Motorbike("Yamaha", "MT-07", 2022, 49))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("49");
    }

    @Test
    void dailyRentalPrice_shouldReturn025PerCm3() {
        assertThat(motorbike.dailyRentalPrice()).isEqualTo(0.25 * 689);
    }

    @Test
    void dailyRentalPrice_shouldCalculateCorrectly_for500cm3() {
        Motorbike moto500 = new Motorbike("Honda", "CB500", 2020, 500);
        assertThat(moto500.dailyRentalPrice()).isEqualTo(125.0);
    }

    @Test
    void toString_shouldContainMotorbikeInfo() {
        String result = motorbike.toString();
        assertThat(result).contains("Motorbike");
        assertThat(result).contains("Yamaha");
        assertThat(result).contains("MT-07");
        assertThat(result).contains("2022");
        assertThat(result).contains("689cm³");
        assertThat(result).contains("€");
    }

    @Test
    void equals_shouldReturnTrue_whenSameMotorbike() {
        Motorbike moto2 = new Motorbike("Yamaha", "MT-07", 2022, 800);
        assertThat(motorbike).isEqualTo(moto2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentBrand() {
        Motorbike moto2 = new Motorbike("Honda", "MT-07", 2022, 689);
        assertThat(motorbike).isNotEqualTo(moto2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentModel() {
        Motorbike moto2 = new Motorbike("Yamaha", "MT-09", 2022, 689);
        assertThat(motorbike).isNotEqualTo(moto2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentYear() {
        Motorbike moto2 = new Motorbike("Yamaha", "MT-07", 2021, 689);
        assertThat(motorbike).isNotEqualTo(moto2);
    }

    @Test
    void equals_shouldReturnFalse_whenNull() {
        assertThat(motorbike).isNotEqualTo(null);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentClass() {
        Car car = new Car("Yamaha", "MT-07", 2022, 5);
        assertThat(motorbike).isNotEqualTo(car);
    }
}
