package de.hsb.kss.mc_schnitzeljagd.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

public class Hint {
    private int hintId;
    private String description;
    private boolean isFree =false;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

    public int getHintId() {
        return hintId;
    }

    public void setHintId(int hintId) {
        this.hintId = hintId;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
