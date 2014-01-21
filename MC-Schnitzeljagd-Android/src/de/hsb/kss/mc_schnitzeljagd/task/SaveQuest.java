package de.hsb.kss.mc_schnitzeljagd.task;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;
import de.hsb.kss.mc_schnitzeljagd.persistence.QuestDAO;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class SaveQuest extends AsyncTask<Quest, Integer, Boolean> {
	
	private QuestDAO questDAO;
	
	protected Boolean doInBackground(Quest... params) {
		questDAO=new QuestDAO();
		int count = params.length;
		for (int i = 0; i < count; i++) {
			try {
				questDAO.insert(params[i]);
			} catch (IOException e1) {
				Log.w("Schnitzeljagd",e1.toString());
				e1.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
