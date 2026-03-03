package agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires pour la classe Client.
 */
class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("Jean", "Dupont", 1990);
    }

    @Test
    void constructor_shouldCreateClient() {
        assertThat(client.getFirstName()).isEqualTo("Jean");
        assertThat(client.getLastName()).isEqualTo("Dupont");
        assertThat(client.getBirthYear()).isEqualTo(1990);
    }

    @Test
    void toString_shouldContainClientInfo() {
        String result = client.toString();
        assertThat(result).contains("Jean");
        assertThat(result).contains("Dupont");
        assertThat(result).contains("1990");
    }

    @Test
    void equals_shouldReturnTrue_whenSameClient() {
        Client client2 = new Client("Jean", "Dupont", 1990);
        assertThat(client).isEqualTo(client2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentFirstName() {
        Client client2 = new Client("Pierre", "Dupont", 1990);
        assertThat(client).isNotEqualTo(client2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentLastName() {
        Client client2 = new Client("Jean", "Martin", 1990);
        assertThat(client).isNotEqualTo(client2);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentBirthYear() {
        Client client2 = new Client("Jean", "Dupont", 1985);
        assertThat(client).isNotEqualTo(client2);
    }

    @Test
    void equals_shouldReturnFalse_whenNull() {
        assertThat(client).isNotEqualTo(null);
    }

    @Test
    void equals_shouldReturnTrue_whenSameObject() {
        assertThat(client).isEqualTo(client);
    }

    @Test
    void hashCode_shouldBeEqual_whenClientsAreEqual() {
        Client client2 = new Client("Jean", "Dupont", 1990);
        assertThat(client.hashCode()).isEqualTo(client2.hashCode());
    }
}
