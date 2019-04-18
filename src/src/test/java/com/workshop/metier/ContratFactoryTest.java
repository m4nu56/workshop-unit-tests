package com.workshop.metier;

import com.workshop.metier.contrat.ContratDao;
import com.workshop.metier.echeance.EcheanceDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseTestsHelper;

import java.sql.Connection;

import static utils.DatabaseTestsParams.getConnectionConfig;

class ContratFactoryTest {

    private Connection con;
    private ContratDao contratDao;
    private EcheanceDao echeanceDao;

    @BeforeAll
    static void setUpBeforeClass() {
        DatabaseTestsHelper.setUpBeforeClass();
    }

    @BeforeEach
    void setUp() throws Exception {
        con = getConnectionConfig().getConnexion();
        con.setAutoCommit(true);
        DatabaseTestsHelper.emptyBaseTest(con);
        contratDao = new ContratDao("jdbc/datasourceName", "public", getConnectionConfig());
        echeanceDao = new EcheanceDao("jdbc/datasourceName", "public", getConnectionConfig());
    }

    // TODO
    @Test
    void onMakeContratAndGenerateTableau_shouldSaveContratAndTableauToDatabase() throws Throwable {
        // GIVEN
        // WHEN
        // THEN
    }
}