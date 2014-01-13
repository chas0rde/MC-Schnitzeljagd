package de.hsb.kss.mc_schnitzeljagd.logic;


import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.persistence.model.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

class AbstractGameLogic{
    protected Player player;
    protected Quest quest;
    protected Point currentPoint;
    protected List<Point>points=new ArrayList<Point>();
    protected List<Hint> hints = new ArrayList<Hint>();
    private Riddle currentMandatoryRiddle;
    


    /**
     * Creates or loads an existing quest. For new Quest creates a point.
     * @param code
     * @param creationMode
     * @return success
     */
    protected boolean loadQuest(String code, boolean creationMode)
    {
        boolean found = false;

        if( code.isEmpty() && creationMode )
        {
            quest = new Quest();
            
            currentPoint = new Point();
            quest.setPointList(new ArrayList<Point>());
    		quest.getPointList().add(currentPoint);
    		
            currentPoint.setRiddles(new ArrayList<Riddle>());            
            currentPoint.setHintList(new ArrayList<Hint>());
            
            found = true;
        }
        else
        {
        	if(code.equals("42")){
        		return true;
        	}
            //TODO: load from DataStore
            //TODO: Remove Dummy Code
            if(code.equals("3000"))
            {
                quest = new Quest();
                quest.setName("Futurama");
                quest.setAuthor("Nibbler");
                quest.setAccessCode("3000");
                Riddle r = new Riddle();                
                currentPoint = new Point();
                quest.setPointList(new ArrayList<Point>());
        		quest.getPointList().add(currentPoint);
                
                currentPoint.setRiddles(new ArrayList<Riddle>());
                currentPoint.getRiddles().add(r);    
                
                currentPoint.setHintList(new ArrayList<Hint>());

                
                for(int i=1; i<=35; i++){
                	Hint h = new Hint();
                	currentPoint.getHintList().add(h);
                	h.setDescription("Description for hint: " + i);
                	h.setHintType("TEXT");
                	h.setFree(true);
                }
                
                currentMandatoryRiddle = r;
                found = true;
            }
            else
            {
                found = false;
            }
        }
        return found; // If found else false;
    }


    public String buildCurrentQuestDescription() {
    	if(quest != null){
    		return "Author= " + quest.getAuthor() + "\n No. waypoints=" + quest.getPointList().size() + " Gamename: " + quest.getName();    
    	} else {
    		return "no quest selected";
    	}
    }

}
