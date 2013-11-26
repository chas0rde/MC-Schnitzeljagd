package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private int pointId;
    private double longitude;
    private double latitude;
    private String name;
    private String description;
    private List<Riddle> riddles=new ArrayList<Riddle>();
    private List<Hint> hintList =new ArrayList<Hint>();

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Riddle> getRiddles() {
        return riddles;
    }

    public void setRiddles(List<Riddle> riddles) {
        this.riddles = riddles;
    }

    public List<Hint> getHintList() {
        return hintList;
    }

    public void setHintList(List<Hint> hintList) {
        this.hintList = hintList;
    }
}
