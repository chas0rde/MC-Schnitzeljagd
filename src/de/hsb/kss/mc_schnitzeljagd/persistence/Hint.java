package de.hsb.kss.mc_schnitzeljagd.persistence;

public class Hint {
    private int hintId;
    private String description;
    private boolean isFree =false;
    private HintType hintType = HintType.TEXT;
    
	public enum HintType{
    	TEXT,
    	IMAGE,
    	SOUND,
    	VIDEO
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
    
    public HintType getHintType() {
		return hintType;
	}

	public void setHintType(HintType hintType) {
		this.hintType = hintType;
	}
}
