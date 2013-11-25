package de.hsb.kss.mc_schnitzeljagd.logic;


import de.hsb.kss.mc_schnitzeljagd.persistence.*;

import java.util.ArrayList;
import java.util.List;

class AbstractGameLogic{
    protected Player player;
    protected Quest quest;
    protected Point currentPoint;
    protected List<Tip> hints = new ArrayList<Tip>();
    protected Riddle mandatoryRiddle;
    protected List<Riddle> additionalRiddle=new ArrayList<Riddle>();

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