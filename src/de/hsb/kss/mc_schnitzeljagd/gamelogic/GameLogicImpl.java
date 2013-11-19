package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;
import de.hsb.kss.mc_schnitzeljagd.persistence.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author suhaila
 *
 */
class GameLogicImpl implements GameLogic {

	private Player player;
    private Quest quest;
    private Point currentPoint;
    private Riddle mandatoryRiddle;
    private List<Riddle> additionalRiddle=new ArrayList<Riddle>();

    @Override
    public final Quest getQuestByAccessCode(String managementCode, Player player) {
        init();
        return quest;
    }

    //TODO: @Melanie nur zum testen drin 
    public void playNewGame(String name)
    {
    	this.player = new Player();
    	this.player.setName(name);    	
    }     
    
    public void playNewGame(String name, String code)
    {
        this.player = new Player();
        this.player.setName(name);
        getQuestByAccessCode(code,this.player);
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
        quest= new Quest();
        //currentPoint=quest.getPointList().get(0);
    }



}
