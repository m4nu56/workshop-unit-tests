package com.workshop.metier;

import com.dev10.exception.BadArgumentException;
import com.workshop.metier.contrat.Contrat;
import com.workshop.metier.echeance.Echeance;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculTableau {

    public List<Echeance> calculTableau(@Nonnull Contrat contrat) throws BadArgumentException {

        validateContrat(contrat);

        List<Echeance> listEcheance = new ArrayList<>();

        // 1- echeance de tirage
        Echeance echeanceTirage = new Echeance();
        echeanceTirage.setDate(contrat.getDateDebut());
        echeanceTirage.setEncours(contrat.getMontant());
        listEcheance.add(echeanceTirage);

        // 2-
        double montant = contrat.getMontant();
        int nbEcheance = contrat.getDuree();
        double encours = contrat.getMontant();
        LocalDate prevDateEcheance = contrat.getDateDebut();

        while (nbEcheance > 0) {
            nbEcheance--;

            Echeance echeance = new Echeance();
            echeance.setDate(prevDateEcheance.plusYears(1));
            prevDateEcheance = echeance.getDate();

            // Seulement si le codeTaux est défini on calcul les intérêts
            if (StringUtils.isNotBlank(contrat.getCodeTaux())) {
                double tauxInterets = instancieCalculTauxInterets().calcul(contrat.getCodeTaux(), echeance.getDate());
                echeance.setInterets(Math.round(encours * tauxInterets / 100));
            }

            echeance.setCapital(Math.round(montant / contrat.getDuree()));
            encours = Math.round(encours - echeance.getCapital());
            echeance.setEncours(encours);

            listEcheance.add(echeance);
        }

        return listEcheance;
    }

    /**
     * Utilisée pour mocker le comportement de la class CalculTauxInterets
     *
     * @return
     */
    CalculTauxInterets instancieCalculTauxInterets() {
        return new CalculTauxInterets();
    }

    /**
     * Validate metier fields
     *
     * @param contrat
     * @throws BadArgumentException
     */
    void validateContrat(@Nonnull Contrat contrat) throws BadArgumentException {
        if (contrat.getDateDebut() == null) {
            throw new BadArgumentException("dateDebut metier mandatory");
        }
    }

}
