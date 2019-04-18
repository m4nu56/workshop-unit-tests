package com.workshop.metier.contrat;

import com.dev10.dao.enums.CRUD;
import mocker.MockContrat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseTestsHelper;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static utils.DatabaseTestsParams.getConnectionConfig;

class ContratDaoTest {

    private Connection con;
    private ContratDao contratDao;

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
    }

    @Test
    void testCrud() throws Throwable {
        // GIVEN
        Contrat contrat = MockContrat.mock("CODE_CONTRAT", 10000.0, 5, LocalDate.of(2019, Month.APRIL, 19), null);
        // WHEN
        contratDao.crud(con, contrat, CRUD.CREATE);
        // THEN
        Contrat contrat1 = contratDao.getBeanById(con, contrat.getId());
        assertEquals("CODE_CONTRAT", contrat1.getCode());
        assertEquals(10000.0, contrat1.getMontant());
        assertEquals(5, contrat1.getDuree());
        assertEquals(LocalDate.of(2019, Month.APRIL, 19), contrat1.getDateDebut());
        assertNull(contrat1.getCodeTaux());
    }
}