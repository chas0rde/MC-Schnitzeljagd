package de.hsb.kss.mc_schnitzeljagd.persistence;

public class Hinweis {
    private int hinweisId;
    private String beschreibung;

    public int getHinweisId() {
        return hinweisId;
    }

    public void setHinweisId(int hinweisId) {
        this.hinweisId = hinweisId;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
