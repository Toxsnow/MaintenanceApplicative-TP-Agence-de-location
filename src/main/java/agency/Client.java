package agency;

/**
 * Classe representant un client de l'agence de location.
 */
public class Client {

    private String firstName;
    private String lastName;
    private int birthYear;

    /**
     * Constructeur de la classe Client.
     * 
     * @param firstName le prenom du client
     * @param lastName  le nom de famille du client
     * @param birthYear l'annee de naissance du client
     */
    public Client(String firstName, String lastName, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    /**
     * Retourne le prenom du client.
     * 
     * @return le prenom
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le nom de famille du client.
     * 
     * @return le nom de famille
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'annee de naissance du client.
     * 
     * @return l'annee de naissance
     */
    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return birthYear == client.birthYear
                && firstName.equals(client.firstName)
                && lastName.equals(client.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthYear;
        return result;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + birthYear + ")";
    }
}
