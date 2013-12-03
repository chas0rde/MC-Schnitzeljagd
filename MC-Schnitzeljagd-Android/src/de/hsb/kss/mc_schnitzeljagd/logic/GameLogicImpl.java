package de.hsb.kss.mc_schnitzeljagd.logic;

import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.persistence.model.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

class GameLogicImpl extends AbstractGameLogic implements GameLogic{

    protected Player player;
    protected Riddle currentMandatoryRiddle = new Riddle();
    protected Riddle currentAdditionalRiddle;
    protected int indexOfCurrentPoint = 0;
    protected Point currentPoint;
    private List<Hint> freeHintsForPoint = new ArrayList<Hint>();
    private List<Hint> payHintsForPoint = new ArrayList<Hint>();


    @Override
    public final Quest getQuestByAccessCode(String managementCode, Player player) {
       // init();
        if( !loadQuest(managementCode, false) )
    	{
        	quest = null;
    	}
        return quest;
    }

    //TODO: @Melanie nur zum testen drin 
    public boolean playNewGame(String name) {
        return playNewGame(name, quest.getAccessCode());
    }

    public boolean playNewGame(String name, String code) {
        boolean loaded = false;

        this.player = new Player();
        this.player.setName(name);
        loaded = getQuestByAccessCode(code, this.player) == null;
        return loaded;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Point goToNextPoint() {
        if (currentMandatoryRiddle.getSolved()) {
            indexOfCurrentPoint++;
            if (indexOfCurrentPoint < points.size()) {
                currentPoint = points.get(indexOfCurrentPoint);
            }
            currentAdditionalRiddle = null;
            currentMandatoryRiddle = null;
        }
        return null;
    }

    @Override
    public Riddle getMandatoryRiddleForCurrentPoint() {
        for (Riddle riddle : currentPoint.getRiddles()) {
            if (riddle.getMandatory()) {
                currentMandatoryRiddle = riddle;
                return currentMandatoryRiddle;
            }
        }
        return null;
    }

    @Override
    public Riddle getNextAdditionalRiddle() {
        for (Riddle riddle : currentPoint.getRiddles()) {
            if (!riddle.getMandatory() && !riddle.getSolved()) {
                currentAdditionalRiddle = riddle;
                return currentAdditionalRiddle;
            }
        }
        return null;
    }

    @Override
    public List<Hint> getFreeHintsForCurrentPoint() {
        if (currentPoint != null) {
            for (Hint hint : currentPoint.getHintList()) {
                if (hint.getFree()) {
                    freeHintsForPoint.add(hint);
                }
            }
            return freeHintsForPoint;
        }
        return null;
    }

    @Override
    public List<Hint> getAvailableHintsForCurrentPoint() {
        if(currentPoint!=null){
            for(Hint hint: currentPoint.getHintList()){
                payHintsForPoint.add(hint);
            }
            return payHintsForPoint;
        }
        return null;
    }

    @Override
    public int getCurrentPointsForPlayer() {
        return player.getCurrentPoints();
    }

    @Override
    public boolean checkSolutionForMandatoryRiddle(String possibleSolution) {
        if (possibleSolution.equals(currentMandatoryRiddle.getSolution())) {
            currentMandatoryRiddle.setSolved(true);
            //TODO: calculate Points for Riddle
            goToNextPoint();
            return true;
        }
        return false;
    }

    @Override
    public boolean checkSolutionForAdditionalRiddle(String possibleSolution) {
        if (possibleSolution.equals(currentAdditionalRiddle.getSolution())) {
            //TODO: calculate Points for Riddle
            currentAdditionalRiddle.setSolved(true);
            return true;
        }
        return false;
    }


    private void init() {
        //quest= new Quest();
        //currentPoint=quest.getPointList().get(0);
    }

}
