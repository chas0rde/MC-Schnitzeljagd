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
        loaded = getQuestByAccessCode(code, this.player) != null;
        if(loaded)
    	{
        	if(quest.getPointList() != null)
           	{
               	currentPoint = quest.getPointList().get(0);
           	}
    	}
        return loaded;
    }

    public Player getPlayer() {
        return this.player;
    }
    
    @Override
    public Point goToNextPoint(boolean solved) {   	
    	
        if (solved || (currentMandatoryRiddle.getSolved() != null && currentMandatoryRiddle.getSolved())) {
            indexOfCurrentPoint++;
            if (indexOfCurrentPoint < quest.getPointList().size()) {
                currentPoint = quest.getPointList().get(indexOfCurrentPoint);
            } else {
            	return null;
            }
            currentAdditionalRiddle = null;
            currentMandatoryRiddle = null;
        }
        return currentPoint;
    }

    @Override
    public Riddle getMandatoryRiddleForCurrentPoint() {
        for (Riddle riddle : currentPoint.getRiddleList()) {
            if (riddle.getMandatory()) {
                currentMandatoryRiddle = riddle;
                return currentMandatoryRiddle;
            }
        }
        return null;
    }

    @Override
    public Riddle getNextRiddle() {
        for (Riddle riddle : currentPoint.getRiddleList()) {
           
        	if (riddle.getSolved() == null || !riddle.getSolved()) {
                currentAdditionalRiddle = riddle;
                return currentAdditionalRiddle;
            }
        }
        return null;
    }

    @Override
    public List<Hint> getFreeHintsForCurrentPoint() {
   
        if (currentPoint != null) {
        	freeHintsForPoint.clear();
            for (Hint hint : currentPoint.getHintList()) {
                if (hint.getFree() != null && hint.getFree()) {
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
            	// Todo only provide payed hints inside list
                payHintsForPoint.add(hint);
            }
            return payHintsForPoint;
        }
        return null;
    }

    /**
     * currently owned user points(scores)
     */
    @Override
    public int getCurrentPointsForPlayer() {
        return player.getCurrentPoints();
    }

    @Override
    public boolean checkSolutionForMandatoryRiddle(String possibleSolution) {
        if (possibleSolution.equals(currentMandatoryRiddle.getSolution())) {
            currentMandatoryRiddle.setSolved(true);
            //TODO: calculate Points for Riddle
            //goToNextPoint();
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

   	@Override
   	public boolean freeNextHint() {
   		if(currentPoint != null)
   		{
   			for(Hint h : currentPoint.getHintList())
   			{
   				if(!h.getFree())
   				{
   					h.setFree(true);
   					break;
   				}
   			}
   		}
   		return freeNextHint();
   	}
    

	@Override
	public String getCurrentQuestDescription() {
		return buildCurrentQuestDescription();
	}

	@Override
	public String getCurrentGameInfo() {
		
		String infoMessage="";
		if(getPlayer() != null){
			infoMessage += "\nUsername: " + getPlayer().getName();
			//infoMessage += "Points: " + getPlayer().getCurrentPoints();
		}
		
		if(currentPoint.getHintList() != null) {
			infoMessage += "\nTotal Number of Hints: " + currentPoint.getHintList().size();
		}
		
		if(getFreeHintsForCurrentPoint() != null){
			infoMessage += "\nNumber of free Hints: " + getFreeHintsForCurrentPoint().size();
		}
		
		if(currentPoint.getRiddleList() != null) {
			infoMessage += "\nNumber of Riddles: " + currentPoint.getRiddleList().size();
		}
				
		return infoMessage;
	}

}
