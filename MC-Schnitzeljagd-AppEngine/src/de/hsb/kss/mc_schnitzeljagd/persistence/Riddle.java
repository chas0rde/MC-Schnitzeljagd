package de.hsb.kss.mc_schnitzeljagd.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Riddle {
    private boolean mandatory;
    private int maxPoints;
    private int solution;
    private boolean isSolved=false;
    private String riddleText;
   

	private java.lang.String answer1; 
	private java.lang.String answer2;
    private java.lang.String answer3;
    private java.lang.String answer4;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
    
    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    
    public String getRiddleText() {
		return riddleText;
	}

	public void setRiddleText(String riddleText) {
		this.riddleText = riddleText;
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

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }
    
    
    public java.lang.String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(java.lang.String answer1) {
		this.answer1 = answer1;
	}

	public java.lang.String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(java.lang.String answer2) {
		this.answer2 = answer2;
	}

	public java.lang.String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(java.lang.String answer3) {
		this.answer3 = answer3;
	}

	public java.lang.String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(java.lang.String answer4) {
		this.answer4 = answer4;
	}

}
