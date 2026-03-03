package agency;

/**
 * Interface representant un vehicule dans l'agence de location.
 */
public interface Vehicle {

    /**
     * Retourne la marque du vehicule.
     * 
     * @return la marque du vehicule
     */
    String getBrand();

    /**
     * Retourne le modele du vehicule.
     * 
     * @return le modele du vehicule
     */
    String getModel();

    /**
     * Retourne l'annee de fabrication du vehicule.
     * 
     * @return l'annee de fabrication
     */
    int getProductionYear();

    /**
     * Retourne le prix de location journalier du vehicule.
     * 
     * @return le prix par jour en euros
     */
    double dailyRentalPrice();

    /**
     * Retourne une representation textuelle du vehicule.
     * Format : Type Marque Modele Annee (details) : prix€
     * 
     * @return la chaine de caracteres representant le vehicule
     */
    String toString();
}
