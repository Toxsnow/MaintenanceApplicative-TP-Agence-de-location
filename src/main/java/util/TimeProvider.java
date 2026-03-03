package util;

import java.time.Year;

/**
 * Classe utilitaire pour recuperer l'annee courante.
 */
public class TimeProvider {

    /**
     * Retourne la valeur de l'annee en cours.
     * 
     * @return l'annee courante
     */
    public static int currentYearValue() {
        return Year.now().getValue();
    }
}
