package com.workshop.metier;

import com.dev10.dao.enums.CRUD;
import com.workshop.metier.contrat.Contrat;
import com.workshop.metier.contrat.ContratDao;

import java.sql.Connection;
import java.time.LocalDate;

public class ContratFactory {

    public Contrat makeContratAndGenerateTableau(Connection con, String codeContrat, double montant, int duree, LocalDate dateDebut, String codeTaux) throws Throwable {

        // Create new contrat
        Contrat contrat = new Contrat();
        contrat.setCode(codeContrat);
        contrat.setCodeTaux(codeTaux);
        contrat.setDateDebut(dateDebut);
        contrat.setDuree(duree);
        contrat.setMontant(montant);

        // Generate TA
        contrat.setListEcheance(new CalculTableau().calculTableau(contrat));

        // Save to database
        new ContratDao("jdbc/datasourceName", "public").crud(con, contrat, CRUD.CREATE);

        return contrat;
    }
}
