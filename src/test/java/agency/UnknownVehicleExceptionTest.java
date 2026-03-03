package agency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour la classe UnknownVehicleException.
 */
class UnknownVehicleExceptionTest {

    @Test
    void getMessage_shouldContainVehicleInfo() {
        Car car = new Car("Peugeot", "308", 2022, 5);
        UnknownVehicleException exception = new UnknownVehicleException(car);
        String message = exception.getMessage();
        assertThat(message).contains("Peugeot");
        assertThat(message).contains("308");
    }

    @Test
    void exception_shouldBeRuntimeException() {
        Car car = new Car("Peugeot", "308", 2022, 5);
        UnknownVehicleException exception = new UnknownVehicleException(car);
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
