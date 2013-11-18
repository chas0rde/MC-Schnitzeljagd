package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class GameLogicImpl implements GameLogic {

    private Quest quest;
    private Point currentPoint;
    private Riddle mandatoryRiddle;
    private List<Riddle> additionalRiddle=new ArrayList<Riddle>();

    @Override
    public final Quest getQuestByAccessCode(String managementCode) {
        init();
        return quest;
    }

    @Override
    public Point getNextPoint() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Riddle getMandatoryRiddleForCurrentPoint() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Riddle getNextAdditionalRiddles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tip getNextTipForCurrentMandatoryRiddle() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Tip getNextTipForAdditionalRiddle() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private void init() {
        quest= new Quest();
        currentPoint=quest.getPointList().get(0);
    }



}