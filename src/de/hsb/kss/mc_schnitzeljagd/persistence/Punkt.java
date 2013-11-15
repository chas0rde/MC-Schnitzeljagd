package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.List;

public class Punkt {
    private int punktId;
    private double longitude;
    private double latitude;
    private String name;
    private String beschreibung;
    private List<Raetsel> raetsel;

    public int getPunktId() {
        return punktId;
    }

    public void setPunktId(int punktId) {
        this.punktId = punktId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<Raetsel> getRaetsel() {
        return raetsel;
    }

    public void setRaetsel(List<Raetsel> raetsel) {
        this.raetsel = raetsel;
    }
}
