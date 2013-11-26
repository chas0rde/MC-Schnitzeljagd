package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;

import java.util.List;

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
}
