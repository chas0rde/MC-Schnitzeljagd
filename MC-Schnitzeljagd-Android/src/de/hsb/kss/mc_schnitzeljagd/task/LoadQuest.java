package de.hsb.kss.mc_schnitzeljagd.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.QuestDAO;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class LoadQuest extends AsyncTask<Long, Void, Quest> {

	private QuestDAO questDAO;

	@Override
	protected Quest doInBackground(Long... params) {
		questDAO = new QuestDAO();
		Quest quest = null;
		try {
			quest = (questDAO.load(params[0]));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return quest;
	}
}
