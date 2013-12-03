package de.hsb.kss.mc_schnitzeljagd.logic;

import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

public interface GameCreation {

	boolean loadQuestByAccessCode(String accessCode);
	void createNewQuest();
	Quest getCurrentQuest();
	void setQuestInfo(String name, String author);
	boolean save();
	void addHint(Hint hint);
	void deleteHint(int index);
	Hint getHint(int index);
    List<Hint> getCurrentHintList();
    void addRiddle(Riddle riddle);
    void deleteRiddle(int index);
    Riddle getRiddle(int index);
    List<Riddle> getCurrentRiddleList();
    void addPoint(Point point);
    void deletePoint(int index);
    Point getPoint(int index);
    int getHintSize();
}
