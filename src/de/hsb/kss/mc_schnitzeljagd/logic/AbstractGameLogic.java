package de.hsb.kss.mc_schnitzeljagd.logic;


import de.hsb.kss.mc_schnitzeljagd.persistence.*;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

class AbstractGameLogic{
    protected Player player;
    protected Quest quest;
    protected Point currentPoint;
    protected List<Point>points=new ArrayList<Point>();
    protected List<Hint> hints = new ArrayList<Hint>();


    protected boolean loadQuest(String code, boolean creationMode)
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
                quest.setAccessCode("3000");
                Riddle r = new Riddle();
                r.getHintList().add(new Hint());
                r.getHintList().add(new Hint());                
                r.getHintList().add(new Hint());
                r.getHintList().add(new Hint());                
                r.getHintList().add(new Hint());
                Point p = new Point();
                p.getRiddles().add(r);
                quest.getPointList().add(p);
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
}
