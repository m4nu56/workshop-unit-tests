package com.workshop.metier.contrat;

import com.dev10.bean.Bean;
import com.workshop.metier.echeance.Echeance;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;

public class Contrat extends Bean {

    @Nonnull
    private String code;

    @Nonnull
    private double montant;

    private String codeTaux;

    @Nonnull
    private int duree;

    @Nonnull
    private LocalDate dateDebut;

    private List<Echeance> listEcheance;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public List<Echeance> getListEcheance() {
        return listEcheance;
    }

    public void setListEcheance(List<Echeance> listEcheance) {
        this.listEcheance = listEcheance;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getCodeTaux() {
        return codeTaux;
    }

    public void setCodeTaux(String codeTaux) {
        this.codeTaux = codeTaux;
    }

    @Override
    public String toString() {
        return "Contrat(" + this.getCode() + ", montant=" + this.getMontant() + ", duree=" + this.getDuree() + ", debut=" + this.getDateDebut() + ", codeTaux=" + this.getCodeTaux() + ", listEcheance=" + this.getListEcheance() + ")";
    }

}
