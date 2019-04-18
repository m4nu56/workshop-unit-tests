package mocker;

import com.dev10.dao.enums.CRUD;
import com.workshop.metier.contrat.Contrat;
import com.workshop.metier.contrat.ContratDao;

import java.sql.Connection;
import java.time.LocalDate;

public class MockContrat {

    public static Contrat mock(String codeContrat, double montant, int duree, LocalDate dateDebut, String codeTaux) {
        Contrat contrat = new Contrat();
        contrat.setCode(codeContrat);
        contrat.setCodeTaux(codeTaux);
        contrat.setDateDebut(dateDebut);
        contrat.setDuree(duree);
        contrat.setMontant(montant);
        return contrat;
    }

    public static Contrat mockAndPersist(Connection con, String codeContrat, double montant, int duree, LocalDate dateDebut, String codeTaux) throws Throwable {
        Contrat contrat = mock(codeContrat, montant, duree, dateDebut, codeContrat);
        new ContratDao("jdbc/datasourceName", "public").crud(con, contrat, CRUD.CREATE);
        return contrat;
    }
}
