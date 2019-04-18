package com.workshop.contrat;

import java.time.LocalDate;

public class Echeance {

    private long id;
    private double capital;
    private double interets;
    private LocalDate date;
    private double encours;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
