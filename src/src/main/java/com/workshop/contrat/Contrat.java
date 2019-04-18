package com.workshop.contrat;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;

public class Contrat {

    private long id;

    private String code;

    private double montant;

    private double taux;

    private int duree;

    @Nonnull
    private LocalDate dateDebut;

    private List<Echeance> listEcheance;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
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

    @Override
    public String toString() {
        return "Contrat(" + this.getCode() + ", montant=" + this.getMontant() + ", duree=" + this.getDuree() + ", debut=" + this.getDateDebut() + ", taux=" + this.getTaux() + ", listEcheance=" + this.getListEcheance() + ")";
    }

}
