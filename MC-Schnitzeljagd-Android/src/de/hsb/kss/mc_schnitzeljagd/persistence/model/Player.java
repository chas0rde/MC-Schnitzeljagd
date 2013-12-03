package de.hsb.kss.mc_schnitzeljagd.persistence.model;

public class Player {
	private String name;
    private int currentPoints;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }
}
