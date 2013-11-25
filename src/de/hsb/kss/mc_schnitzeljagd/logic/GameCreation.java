package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

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
