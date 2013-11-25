package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;
import de.hsb.kss.mc_schnitzeljagd.persistence.Player;

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
    public Point getNextPoint() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Riddle getMandatoryRiddleForCurrentPoint() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Riddle getNextAdditionalRiddle() {
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

    @Override
    public int getCurrentPointsForPlayer() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private void init() {
        //quest= new Quest();
        //currentPoint=quest.getPointList().get(0);
    }

}
