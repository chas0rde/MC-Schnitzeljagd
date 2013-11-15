package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.List;

public class Raetsel {
    private int raetselId;
    private boolean mandatory;
    private int maxPunkte;
    private List<Hinweis> hinweise;

    public int getRaetselId() {
        return raetselId;
    }

    public void setRaetselId(int raetselId) {
        this.raetselId = raetselId;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public int getMaxPunkte() {
        return maxPunkte;
    }

    public void setMaxPunkte(int maxPunkte) {
        this.maxPunkte = maxPunkte;
    }

    public List<Hinweis> getHinweise() {
        return hinweise;
    }

    public void setHinweise(List<Hinweis> hinweise) {
        this.hinweise = hinweise;
    }
}
