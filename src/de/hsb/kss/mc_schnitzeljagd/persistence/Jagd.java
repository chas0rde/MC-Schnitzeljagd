package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.List;

public class Jagd {
    private int jagdId;
    private String author;
    private String verwaltungscode;
    private List<Punkt> punkte;

    public String getVerwaltungscode() {
        return verwaltungscode;
    }

    public void setVerwaltungscode(String verwaltungscode) {
        this.verwaltungscode = verwaltungscode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getJagdId() {
        return jagdId;
    }

    public void setJagdId(int jagdId) {
        this.jagdId = jagdId;
    }

    public List<Punkt> getPunkte() {
        return punkte;
    }

    public void setPunkte(List<Punkt> punkte) {
        this.punkte = punkte;
    }
}
