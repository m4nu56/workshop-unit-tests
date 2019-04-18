package com.workshop.metier;

import com.dev10.exception.BadArgumentException;
import com.workshop.metier.contrat.Contrat;
import com.workshop.metier.echeance.Echeance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CalculTableauTest_TauxInterets {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void onCalculTableau_withCodeTaux_shouldCalculTableauWithTauxInterets() throws BadArgumentException {
        // GIVEN
        Contrat contrat = Mockito.mock(Contrat.class);
        Mockito.doReturn(5).when(contrat).getDuree();
        double montantContrat = 10000.0;
        Mockito.doReturn(montantContrat).when(contrat).getMontant();
        LocalDate dateDebut = LocalDate.of(2019, Month.APRIL, 19);
        Mockito.doReturn(dateDebut).when(contrat).getDateDebut();
        Mockito.doReturn("EURIBOR03M").when(contrat).getCodeTaux();

        // On place un spy sur une nouvelle instance de CalculTauxInterets et sur une nouvelle CalculTableau
        CalculTauxInterets calculTauxInterets = Mockito.spy(new CalculTauxInterets());
        CalculTableau calculTableau = Mockito.spy(new CalculTableau());
        // On utilise notre spy depuis la classe CalculTableau
        Mockito.doReturn(calculTauxInterets).when(calculTableau).instancieCalculTauxInterets();
        // On Mock le comportement de notre instance de CalculTauxInterets
        Mockito.doReturn(1.0).when(calculTauxInterets).calcul(eq("EURIBOR03M"), any(LocalDate.class));

        // WHEN
        List<Echeance> listEcheance = calculTableau.calculTableau(contrat);

        // THEN
        Echeance echence1 = listEcheance.get(1);
        assertEquals(Math.round(montantContrat * 1 / 100), echence1.getInterets());
    }

    @Test
    void onCalculTableau_withCodeTaux_shouldCallCalculTauxInteretsXTimes() throws BadArgumentException {
        // GIVEN
        Contrat contrat = Mockito.mock(Contrat.class);
        Mockito.doReturn(10).when(contrat).getDuree();
        double montantContrat = 10000.0;
        Mockito.doReturn(montantContrat).when(contrat).getMontant();
        LocalDate dateDebut = LocalDate.of(2019, Month.APRIL, 19);
        Mockito.doReturn(dateDebut).when(contrat).getDateDebut();
        Mockito.doReturn("EURIBOR03M").when(contrat).getCodeTaux();

        // On place un spy sur une nouvelle instance de CalculTauxInterets et sur une nouvelle CalculTableau
        CalculTauxInterets calculTauxInterets = Mockito.spy(new CalculTauxInterets());
        CalculTableau calculTableau = Mockito.spy(new CalculTableau());
        // On utilise notre spy depuis la classe CalculTableau
        Mockito.doReturn(calculTauxInterets).when(calculTableau).instancieCalculTauxInterets();
        // On Mock le comportement de notre instance de CalculTauxInterets
        Mockito.doReturn(1.0).when(calculTauxInterets).calcul(eq("EURIBOR03M"), any(LocalDate.class));

        // WHEN
        calculTableau.calculTableau(contrat);

        // THEN
        // On vérifie que la méthode CalculTauxInterets.calcul a bien été appelée 10 fois
        verify(calculTauxInterets, times(10)).calcul(eq("EURIBOR03M"), any(LocalDate.class));
        verify(calculTauxInterets, atLeast(10)).calcul(eq("EURIBOR03M"), any(LocalDate.class));
        verify(calculTauxInterets, atLeastOnce()).calcul(eq("EURIBOR03M"), any(LocalDate.class));
    }

    @Test
    void onCalculTableau_verifyValidateContratIsCalled() throws BadArgumentException {
        // GIVEN
        Contrat contrat = Mockito.mock(Contrat.class);
        LocalDate dateDebut = LocalDate.of(2019, Month.APRIL, 19);
        Mockito.doReturn(dateDebut).when(contrat).getDateDebut();
        Mockito.doReturn("CODE_CONTRAT_X").when(contrat).getCode();

        CalculTableau calculTableau = Mockito.spy(new CalculTableau());

        // WHEN
        calculTableau.calculTableau(contrat);

        // THEN - Exemple de test sur le paramètre envoyée à une méthode d'une classe Mockée ou Spyée
        ArgumentCaptor<Contrat> argument = ArgumentCaptor.forClass(Contrat.class);
        verify(calculTableau).validateContrat(argument.capture());
        // On peut récupérer le metier qui a été envoyée à la méthode validateContrat de notre CalculTableau
        assertEquals("CODE_CONTRAT_X", argument.getValue().getCode());
    }

}