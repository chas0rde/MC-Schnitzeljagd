package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.List;

public class Riddle {
    private int riddleId;
    private boolean mandatory;
    private int maxPoints;
    private List<Tip> tipList;

    public int getRiddleId() {
        return riddleId;
    }

    public void setRiddleId(int riddleId) {
        this.riddleId = riddleId;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public List<Tip> getTipList() {
        return tipList;
    }

    public void setTipList(List<Tip> tipList) {
        this.tipList = tipList;
    }
}