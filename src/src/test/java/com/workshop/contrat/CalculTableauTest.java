package com.workshop.contrat;

import com.dev10.exception.BadArgumentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculTableauTest {

    private CalculTableau calculTableau = new CalculTableau();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void onCalculTableau_withTaux0_shouldCalculTableau() throws BadArgumentException {
        // GIVEN
        Contrat contrat = Mockito.mock(Contrat.class);
        Mockito.doReturn(5).when(contrat).getDuree();
        double montantContrat = 10000.0;
        Mockito.doReturn(montantContrat).when(contrat).getMontant();
        LocalDate dateDebut = LocalDate.of(2019, Month.APRIL, 19);
        Mockito.doReturn(dateDebut).when(contrat).getDateDebut();

        // WHEN
        List<Echeance> listEcheance = calculTableau.calculTableau(contrat);

        // THEN
        assertEquals(6, listEcheance.size());
        Echeance echeanceTirage = listEcheance.get(0);
        assertEquals(montantContrat, echeanceTirage.getEncours());
        assertEquals(dateDebut, echeanceTirage.getDate());
        for (int i = 1; i < listEcheance.size(); i++) {
            assertEquals(Math.round(montantContrat / 5), listEcheance.get(i).getCapital());
            assertEquals(0, listEcheance.get(i).getInterets());
        }
        assertEquals(0, listEcheance.get(5).getEncours());
    }

    @Test
    void onCalculTableau_withDuree0_shouldReturnOnlyEcheanceTirage() throws BadArgumentException {
        // GIVEN
        Contrat contrat = Mockito.mock(Contrat.class);
        Mockito.doReturn(0).when(contrat).getDuree();
        double montantContrat = 10000.0;
        Mockito.doReturn(montantContrat).when(contrat).getMontant();
        LocalDate dateDebut = LocalDate.of(2019, Month.APRIL, 19);
        Mockito.doReturn(dateDebut).when(contrat).getDateDebut();

        // WHEN
        List<Echeance> listEcheance = calculTableau.calculTableau(contrat);

        // THEN
        assertEquals(1, listEcheance.size());
        Echeance echeanceTirage = listEcheance.get(0);
        assertEquals(montantContrat, echeanceTirage.getEncours());
        assertEquals(dateDebut, echeanceTirage.getDate());
    }

    @Test
    void onCalculTableau_withDebutNull_shouldThrowBadArgumentException() {
        // GIVEN
        Contrat contrat = Mockito.mock(Contrat.class);
        Mockito.doReturn(10).when(contrat).getDuree();
        double montantContrat = 10000.0;
        Mockito.doReturn(montantContrat).when(contrat).getMontant();
        Mockito.doReturn(null).when(contrat).getDateDebut();

        // WHEN
        BadArgumentException exception = assertThrows(
                BadArgumentException.class,
                () -> calculTableau.calculTableau(contrat)
        );

        // THEN
        assertEquals("dateDebut contrat mandatory", exception.getMessage());
    }

}