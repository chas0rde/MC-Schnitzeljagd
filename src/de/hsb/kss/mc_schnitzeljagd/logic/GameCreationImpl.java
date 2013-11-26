package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;

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

    @Override
    public Hint getHint(int index) {
        if (isIndexInList(index, hints.size())) {
            return hints.get(index);
        }
        return null;
    }

	@Override
	public int getHintSize() {
		if(hints != null) {
			return currentMandatoryRiddle.getHintList().size(); 
		}
		return 0;
	}
}
