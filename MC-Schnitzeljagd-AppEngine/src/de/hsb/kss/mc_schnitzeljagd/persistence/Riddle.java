package de.hsb.kss.mc_schnitzeljagd.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

public class Riddle {
    private int riddleId;
    private boolean mandatory;
    private int maxPoints;
    private String solution;
    private boolean isSolved=false;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

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

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
