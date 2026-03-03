package util;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour la classe TimeProvider.
 */
class TimeProviderTest {

    @Test
    void currentYearValue_shouldReturnCurrentYear() {
        // Given
        int expectedYear = Year.now().getValue();

        // When
        int actualYear = TimeProvider.currentYearValue();

        // Then
        assertThat(actualYear).isEqualTo(expectedYear);
    }

    @Test
    void currentYearValue_shouldReturnPositiveValue() {
        // When
        int year = TimeProvider.currentYearValue();

        // Then
        assertThat(year).isPositive();
    }

    @Test
    void currentYearValue_shouldReturnReasonableYear() {
        // When
        int year = TimeProvider.currentYearValue();

        // Then
        assertThat(year).isGreaterThanOrEqualTo(2024);
        assertThat(year).isLessThanOrEqualTo(2100);
    }
}
