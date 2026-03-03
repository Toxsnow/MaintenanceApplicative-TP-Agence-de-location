package agency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour la classe MaxPriceCriterion.
 */
class MaxPriceCriterionTest {

    @Test
    void test_shouldReturnTrue_whenPriceIsLessThanMax() {
        MaxPriceCriterion criterion = new MaxPriceCriterion(150.0);
        Car car = new Car("Peugeot", "308", 2015, 5); // prix = 100€
        assertThat(criterion.test(car)).isTrue();
    }

    @Test
    void test_shouldReturnTrue_whenPriceEqualsMax() {
        MaxPriceCriterion criterion = new MaxPriceCriterion(100.0);
        Car car = new Car("Peugeot", "308", 2015, 5); // prix = 100€
        assertThat(criterion.test(car)).isTrue();
    }

    @Test
    void test_shouldReturnFalse_whenPriceIsGreaterThanMax() {
        MaxPriceCriterion criterion = new MaxPriceCriterion(50.0);
        Car car = new Car("Peugeot", "308", 2015, 5); // prix = 100€
        assertThat(criterion.test(car)).isFalse();
    }

    @Test
    void test_shouldWorkWithMotorbike() {
        MaxPriceCriterion criterion = new MaxPriceCriterion(200.0);
        Motorbike moto = new Motorbike("Yamaha", "MT-07", 2022, 500); // prix = 125€
        assertThat(criterion.test(moto)).isTrue();
    }
}
