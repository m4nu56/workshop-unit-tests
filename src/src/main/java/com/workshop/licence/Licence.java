package com.workshop.licence;

public class Licence {

    private long id;
    private LicenceType licenceType;
    private double value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LicenceType getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(LicenceType licenceType) {
        this.licenceType = licenceType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
