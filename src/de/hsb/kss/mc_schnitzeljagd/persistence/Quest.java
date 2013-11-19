package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int questId;
    private String author;
    private String accessCode;
    private List<Point> pointList= new ArrayList<Point>();

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }
    
    //TODO: Meleanie sch�n machen mit Strings xml und so
    public String toString()
    {
    	return "Author= " + getAuthor() + "\n No. waypoints= 2";    
    }
}
