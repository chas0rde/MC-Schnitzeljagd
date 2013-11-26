package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.*;
import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;

class GameLogicImpl extends AbstractGameLogic implements GameLogic{



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
    public boolean playNewGame(String name)
    {
    	return playNewGame(name, quest.getAccessCode());
    }     
    
    public boolean playNewGame(String name, String code)
    {
    	boolean loaded = false;
    	
        this.player = new Player();
        this.player.setName(name);
        loaded = getQuestByAccessCode(code,this.player) == null;        
        return loaded;
    }   
    
    public Player getPlayer()
    {
    	return this.player;
    }
    
    @Override
    public Point goToNextPoint() {
        if(currentMandatoryRiddle.isSolved()){
            indexOfCurrentPoint++;
            if(indexOfCurrentPoint<points.size()){
                currentPoint=points.get(indexOfCurrentPoint);
            }
            currentAdditionalRiddle=null;
            currentMandatoryRiddle=null;
        }
        return null;
    }

    @Override
    public Riddle getMandatoryRiddleForCurrentPoint() {
        for(Riddle riddle:currentPoint.getRiddles()){
            if(riddle.isMandatory()){
                currentMandatoryRiddle =riddle;
                return currentMandatoryRiddle;
            }
        }
        return null;
    }

    @Override
    public Riddle getNextAdditionalRiddle() {
        for(Riddle riddle:currentPoint.getRiddles()){
            if(!riddle.isMandatory()&& !riddle.isSolved()){
                currentAdditionalRiddle=riddle;
                return currentAdditionalRiddle;
            }
        }
        return null;
    }

    @Override
    public Hint getNextHintForCurrentMandatoryRiddle() {
        if(currentMandatoryRiddle!=null){
           for(Hint hint:currentMandatoryRiddle.getHintList()){
               if(!hint.isVisible()){
                   return hint;
               }
           }
        }
        return null;
    }

    @Override
    public Hint getNextHintForAdditionalRiddle() {
        if(currentAdditionalRiddle!=null){
            for(Hint hint:currentAdditionalRiddle.getHintList()){
                if(!hint.isVisible()){
                    return hint;
                }
            }
        }
        return null;
    }

    @Override
    public int getCurrentPointsForPlayer() {
        return player.getCurrentPoints();
    }

    @Override
    public boolean checkSolutionForMandatoryRiddle(String possibleSolution) {
        if(possibleSolution.equals(currentMandatoryRiddle.getSolution())){
            currentMandatoryRiddle.setSolved(true);
            //TODO: calculate Points for Riddle
            goToNextPoint();
            return true;
        }
        return false;
    }

    @Override
    public boolean checkSolutionForAdditionalRiddle(String possibleSolution) {
        if(possibleSolution.equals(currentAdditionalRiddle.getSolution())){
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
