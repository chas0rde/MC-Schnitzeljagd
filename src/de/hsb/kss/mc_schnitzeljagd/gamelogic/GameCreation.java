package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;
import de.hsb.kss.mc_schnitzeljagd.persistence.Player;

import java.util.List;

public interface GameCreation {

	boolean loadQuestByAccessCode(String accessCode);
	void createNewQuest();
	Quest getCurrentQuest();
	void setQuestInfo(String name, String author);
	boolean save();
	void addHint(Tip t);
	void deleteHint(int index);
	Tip getHint(int index);
}
