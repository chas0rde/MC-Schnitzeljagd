package de.hsb.kss.mc_schnitzeljagd.logic;

import java.util.ArrayList;
import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

class GameCreationImpl extends AbstractGameLogic implements GameCreation {

    private Point currentCreatedPoint;
    private List<Point> pointList = new ArrayList<Point>();
    private Riddle currentCreatedRiddle;
    private List<Riddle> riddleList = new ArrayList<Riddle>();

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
        if (hints != null) {
            hints.add(hint);
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
        if (isIndexInList(index, hints.size())) {
            return hints.get(index);
        }
        return null;
    }

    @Override
    public List<Hint> getCurrentHintList() {
        return hints;
    }

    @Override
    public void addRiddle(Riddle riddle) {
        if (riddle != null) {
            riddleList.add(riddle);
        }
    }

    @Override
    public void deleteRiddle(int index) {
        if (isIndexInList(index, riddleList.size())) {
            riddleList.remove(index);
        }
    }

    @Override
    public Riddle getRiddle(int index) {
        if (isIndexInList(index, riddleList.size())) {
            riddleList.get(index);
        }
        return null;
    }

    @Override
    public List<Riddle> getCurrentRiddleList() {
        return riddleList;
    }

    @Override
    public void addPoint(Point point) {
        if(point!=null){
            pointList.add(point);
        }
    }

    @Override
    public void deletePoint(int index) {
        if(isIndexInList(index,pointList.size())){
            pointList.remove(index);
        }
    }

    @Override
    public Point getPoint(int index) {
       if(isIndexInList(index,pointList.size())){
           return pointList.get(index);
       }
        return null;
    }

    @Override
    public int getHintSize() {
        if (hints != null) {
            return currentPoint.getHintList().size();
        }
        return 0;
    }
}
