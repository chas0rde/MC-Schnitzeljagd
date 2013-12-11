package de.hsb.kss.mc_schnitzeljagd.logic;

import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

class GameCreationImpl extends AbstractGameLogic implements GameCreation {

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
        if (quest != null) {
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
    public void addHint(Hint hint) {
        if (currentPoint != null) {
        	if(currentPoint.getHintList() == null){
        		currentPoint.setHintList(new ArrayList<Hint>());
        	} 
            currentPoint.getHintList().add(hint);
        }
    }

    @Override
    public void deleteHint(int index) {
        if (isIndexInList(index, hints.size())) {
            hints.remove(index);
        }
    }


    private boolean isIndexInList(int index, int size) {
        return (index >= 0 && index < size) ? true : false;
    }

    @Override
    public Hint getHint(int index) {
    	
    	if(currentPoint != null && currentPoint.getHintList() != null){
            if (isIndexInList(index, currentPoint.getHintList().size())) {
                return currentPoint.getHintList().get(index);
            }
    	}
        return null;
    }

    @Override
    public List<Hint> getCurrentHintList() {
        if (hints == null && currentPoint != null) {
            hints = currentPoint.getHintList();
        }
        return hints;
    }

    @Override
    public void addRiddle(Riddle riddle) {
        if (riddle != null ) { 
        	if(currentPoint.getRiddles() == null){
        		currentPoint.setRiddles(new ArrayList<Riddle>());
        	}
            currentPoint.getRiddles().add(riddle);
        }
    }

    @Override
    public void deleteRiddle(int index) {
        if (isIndexInList(index, currentPoint.getRiddles().size())) {
            currentPoint.getRiddles().remove(index);
        }
    }

    @Override
    public Riddle getRiddle(int index) {
        if (isIndexInList(index, currentPoint.getRiddles().size())) {
        	currentPoint.getRiddles().get(index);
        }
        return null;
    }

    @Override
    public List<Riddle> getCurrentRiddleList() {
        return currentPoint.getRiddles();
    }

    @Override
    public void addPoint(Point point) {
        if (point != null ) {
        	
            quest.getPointList().add(point);
            
            currentPoint=point;
    		currentPoint.setHintList(new ArrayList<Hint>());    		
    		currentPoint.setRiddles(new ArrayList<Riddle>());
        }
    }

    @Override
    public void deletePoint(int index) {
        if (isIndexInList(index, quest.getPointList().size())) {
        	quest.getPointList().remove(index);
        }
    }

    @Override
    public Point getPoint(int index) {
        if (isIndexInList(index, quest.getPointList().size())) {
            return quest.getPointList().get(index);
        }
        return null;
    }

    @Override
    public int getHintSize() {
        if ((currentPoint != null ) && (currentPoint.getHintList() != null)) {
            return currentPoint.getHintList().size();
        }
        return 0;
    }

	@Override
	public Point getCurrentPoint() {
		// TODO Auto-generated method stub
		return currentPoint;
	}

	@Override
	public String getCurrentQuestDescription() {
    	return "Author= " + quest.getAuthor() + "\n No. waypoints=" + quest.getPointList().size() + " Gamename: " + quest.getName();    
    }
}
