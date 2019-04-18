package com.workshop.metier.echeance;

import com.dev10.bean.Bean;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class Echeance extends Bean {

    @Nonnull
    private long idContrat;

    @Nonnull
    private double capital;

    @Nonnull
    private double interets;

    @Nonnull
    private LocalDate date;

    @Nonnull
    private double encours;

    public long getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(long idContrat) {
        this.idContrat = idContrat;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getInterets() {
        return interets;
    }

    public void setInterets(double interets) {
        this.interets = interets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getEncours() {
        return encours;
    }

    public void setEncours(double encours) {
        this.encours = encours;
    }

    @Override
    public String toString() {
        return "Echeance(" + this.getDate() + ", encours=" + this.getEncours() + ", capital=" + this.getCapital() + ", interets=" + this.getInterets() + ")";
    }

}
