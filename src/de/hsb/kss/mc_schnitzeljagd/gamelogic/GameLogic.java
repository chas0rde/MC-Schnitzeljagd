package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import com.google.android.gms.internal.ad;
import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements Logic{

    private Quest quest;
    private Stack<Point> points;
    private Point lastCalledPoint;

    @Override
    public Quest getQuestByManagementCode(String managementCode) {
        init();
        return quest;
    }

    private void init() {
        quest= new Quest();
        points= new Stack<Point>();
        points.addAll(quest.getPointList());
    }

    @Override
    public Point getNextPoint() {
        lastCalledPoint= points.pop();
        return lastCalledPoint;
    }

    @Override
    public Riddle getMandatoryRiddle() {
       for(Riddle riddle:lastCalledPoint.getRiddles()){
           if(riddle.isMandatory()){
               return riddle;
           }
       }
       return null;
    }

    @Override
    public List<Riddle> getAdditionalRiddles() {
        List<Riddle> riddles= new ArrayList<Riddle>();
        for(Riddle riddle:lastCalledPoint.getRiddles()){
            if(!riddle.isMandatory()){
                riddles.add(riddle);
            }
        }
        return riddles;
    }

    @Override
    public List<Tip> getTipsForRiddle(Riddle riddle) {
        return riddle.getTipList();
    }
}
