package com.workshop.metier;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class CalculTauxInterets {

    /**
     * Recherche le taux d'intérêts correspondant à la date et au taux passés en paramètre
     *
     * @param codeTaux
     * @param date
     * @return
     */
    public double calcul(String codeTaux, LocalDate date) {
        // Traitement compliqué avec des appels Dao etc..
        // Pour notre calcul de tableau on veut simplement mocker le résultat de cette méthode
        return ThreadLocalRandom.current().nextDouble(-1.0, 4.44);
    }
}
