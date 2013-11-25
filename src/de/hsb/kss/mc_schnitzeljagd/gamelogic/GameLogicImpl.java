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
class GameLogicImpl implements GameLogic, GameCreation {

	private Player player;
    private Quest quest;
    private Point currentPoint;
    private List<Tip> hints = new ArrayList<Tip>();
    private Riddle mandatoryRiddle;
    private List<Riddle> additionalRiddle=new ArrayList<Riddle>();

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
    
    private boolean loadQuest(String code, boolean creationMode)
    {    	  
    	boolean found = false;
    	
    	if( code.isEmpty() && creationMode )
    	{
    		quest = new Quest();
    		found = true;
    	}
    	else
    	{
    		//TODO: load from DataStore
    		//TODO: Remove Dummy Code
    		if(code.equals("3000"))
    		{    	
    			quest = new Quest();
    			quest.setName("Futurama");
    			quest.setAuthor("Nibbler");
    			found = true;
    		}
    		else
    		{
    			found = false;
    		}
    	}
    	return found; // If found else false;
    }

	@Override
	public boolean loadQuestByAccessCode(String code) {
		return loadQuest(code, true);		
	}

	@Override
	public void createNewQuest() {
		loadQuest("", true);	
	}

	@Override
	public Quest getCurrentQuest() {
		return quest;
	}

	@Override
	public void setQuestInfo(String name, String author) {
		if(quest != null)
		{
			quest.setName(name);
			quest.setAuthor(author);			
		}
		
	}

	@Override
	public boolean save() {
		quest.setAccessCode("42"); //TODO: Create New Code;
		//TODO: Save in DataStore
		return true;
	}

	@Override
	public void addHint(Tip t) {
		if(hints != null)
		{
			hints.add(t);
		}		
	}

	@Override
	public void deleteHint(int index) {
		if(hints != null)
		{
			hints.remove(index);
		}
	}

	@Override
	public Tip getHint(int index) {
		if(hints != null)
		{
			return hints.get(index);
		} 		
		return null;
	}
}
