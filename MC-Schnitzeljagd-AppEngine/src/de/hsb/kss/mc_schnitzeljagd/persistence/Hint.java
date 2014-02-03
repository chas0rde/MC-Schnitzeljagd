package de.hsb.kss.mc_schnitzeljagd.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Hint {
    private String description;
    private boolean isFree =false;
    private HintType hintType = HintType.TEXT;
            
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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
    
    public HintType getHintType() {
 		return hintType;
 	}

 	public void setHintType(HintType hintType) {
 		this.hintType = hintType;
 	}
}
