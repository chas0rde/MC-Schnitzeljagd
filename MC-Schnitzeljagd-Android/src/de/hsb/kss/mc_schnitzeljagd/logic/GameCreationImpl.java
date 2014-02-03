package de.hsb.kss.mc_schnitzeljagd.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;
import de.hsb.kss.mc_schnitzeljagd.task.SaveQuest;

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
//		quest.setAccessCode("42"); // TODO: Create New Code;
		quest.setAccessCode(new AccessCodeGenerator().getGeneratedAccessCode());
		// TODO: Save in DataStore
		boolean gotSaved = true;
		try {
			gotSaved = new SaveQuest().execute(quest).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gotSaved;
	}

	@Override
	public void addHint(Hint hint) {
		if (currentPoint != null) {
			if (currentPoint.getHintList() == null) {
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
		if (currentPoint != null && currentPoint.getHintList() != null) {
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
		if (riddle != null) {
			if (currentPoint.getRiddleList() == null) {
				currentPoint.setRiddleList(new ArrayList<Riddle>());
			}
			currentPoint.getRiddleList().add(riddle);
		}
	}

	@Override
	public void deleteRiddle(int index) {
		if (isIndexInList(index, currentPoint.getRiddleList().size())) {
			currentPoint.getRiddleList().remove(index);
		}
	}

	@Override
	public Riddle getRiddle(int index) {
		if (currentPoint != null && currentPoint.getRiddleList() != null) {
			if (isIndexInList(index, currentPoint.getRiddleList().size())) {
				return currentPoint.getRiddleList().get(index);
			}
		}
		return null;
	}

	@Override
	public List<Riddle> getCurrentRiddleList() {
		return currentPoint.getRiddleList();
	}

	@Override
	public void addPoint(Point point) {
		if (point != null) {

			quest.getPointList().add(point);

			currentPoint = point;
			currentPoint.setHintList(new ArrayList<Hint>());
			currentPoint.setRiddleList(new ArrayList<Riddle>());
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
		if ((currentPoint != null) && (currentPoint.getHintList() != null)) {
			return currentPoint.getHintList().size();
		}
		return 0;
	}

	public int getRiddleSize() {
		if ((currentPoint != null) && (currentPoint.getRiddleList() != null)) {
			return currentPoint.getRiddleList().size();
		}
		return 0;
	}

	@Override
	public Point getCurrentPoint() {
		// TODO Auto-generated method stub
		return currentPoint;
	}

	public String getCurrentQuestDescription() {
		// TODO Auto-generated method stub
		return buildCurrentQuestDescription();
	}

}
