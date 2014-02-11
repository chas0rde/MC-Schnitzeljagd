package de.hsb.kss.mc_schnitzeljagd.task;

import java.io.IOException;

import android.os.AsyncTask;
import de.hsb.kss.mc_schnitzeljagd.persistence.QuestDAO;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class SaveQuest extends AsyncTask<Quest, Integer, Quest> {

	private QuestDAO questDAO;

	protected Quest doInBackground(Quest... params) {
		questDAO = new QuestDAO();
		Quest quest = null;
		try {
			quest = questDAO.insert(params[0]);
		} catch (IOException e1) {;
			e1.printStackTrace();
			return null;
		}
		return quest;
	}
}
