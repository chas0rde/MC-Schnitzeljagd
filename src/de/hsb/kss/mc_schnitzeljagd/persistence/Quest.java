package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.List;

public class Quest {
    private int questId;
    private String author;
    private String managementCode;
    private List<Point> pointList;

    public String getManagementCode() {
        return managementCode;
    }

    public void setManagementCode(String managementCode) {
        this.managementCode = managementCode;
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
}
