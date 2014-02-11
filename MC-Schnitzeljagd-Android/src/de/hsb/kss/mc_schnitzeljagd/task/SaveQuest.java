package de.hsb.kss.mc_schnitzeljagd.task;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;
import de.hsb.kss.mc_schnitzeljagd.persistence.QuestDAO;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class SaveQuest extends AsyncTask<Quest, Integer, Quest> {
	
	private QuestDAO questDAO;
	
	protected Quest doInBackground(Quest... params) {
		questDAO=new QuestDAO();
		int count = params.length;
		//TODO[ML]: unschön weil hier Standardmäßig nur eine Quest übergeben wird
		Quest quest=null;
		for (int i = 0; i < count; i++) {
			try {
				quest=questDAO.insert(params[i]);
			} catch (IOException e1) {
				Log.w("Schnitzeljagd",e1.toString());
				e1.printStackTrace();
				return null;
			}
		}
		return quest;
	}
}
