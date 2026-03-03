package agency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour la classe BrandCriterion.
 */
class BrandCriterionTest {

    @Test
    void test_shouldReturnTrue_whenBrandMatches() {
        BrandCriterion criterion = new BrandCriterion("Peugeot");
        Car car = new Car("Peugeot", "308", 2022, 5);
        assertThat(criterion.test(car)).isTrue();
    }

    @Test
    void test_shouldReturnFalse_whenBrandDoesNotMatch() {
        BrandCriterion criterion = new BrandCriterion("Renault");
        Car car = new Car("Peugeot", "308", 2022, 5);
        assertThat(criterion.test(car)).isFalse();
    }

    @Test
    void test_shouldWorkWithMotorbike() {
        BrandCriterion criterion = new BrandCriterion("Yamaha");
        Motorbike moto = new Motorbike("Yamaha", "MT-07", 2022, 689);
        assertThat(criterion.test(moto)).isTrue();
    }
}
