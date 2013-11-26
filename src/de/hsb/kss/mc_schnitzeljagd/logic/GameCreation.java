package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;

public interface GameCreation {

	boolean loadQuestByAccessCode(String accessCode);
	void createNewQuest();
	Quest getCurrentQuest();
	void setQuestInfo(String name, String author);
	boolean save();
	void addHint(Hint hint);
	void deleteHint(int index);
	Hint getHint(int index);
	int getHintSize();
}
