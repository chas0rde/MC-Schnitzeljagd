package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class QuestDAOTestActivity extends Activity {
	private QuestDAO questDAO = null;
	private ListView questList = null;
	private EditText newQuestName = null;
	private Button saveQuestButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quest_daotest);
		
		this.questList = (ListView) findViewById(R.id.questList);
		
		
		this.newQuestName = (EditText) findViewById(R.id.newQuestName);
		this.saveQuestButton = (Button) findViewById(R.id.saveQuestButton);
		
		
		questDAO = new QuestDAO();
		
		
		OnTouchListener saveQuestListener = new OnTouchListener() {
	    	@Override
	        public boolean onTouch(View v, MotionEvent event) {
	    		int action = event.getActionMasked();
	    		if (action == MotionEvent.ACTION_DOWN)
	    		{
		    		Quest quest = new Quest();
		    	    quest.setAuthor("autor");
		    	    quest.setName(newQuestName.getText().toString());
		    	    newQuestName.setText("");
		    	    Point point = new Point();
		    	    point.setName("First Point");
		    	    quest.setPointList(Arrays.asList(point));
		    	    new QuestSaveTask().execute(quest);
	    		}
	    		return true;
	    	}
	    };
	    saveQuestButton.setOnTouchListener(saveQuestListener);
	    updateQuestList();	    
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.quest_data_test, menu);
		return true;
	}
	
	private void showDialog(String message) {
	    new AlertDialog.Builder(this)
	        .setMessage(message)
	        .setPositiveButton(android.R.string.ok,
	            new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	              }
	            }).show();
	  }
	private void updateQuestList(){
		new LoadQuestListTask().execute();
		
	}
	
	 private class QuestSaveTask extends AsyncTask<Quest, Integer, Boolean> {
	     protected Boolean doInBackground(Quest... params) {
	 	    	 	    
	 	     int count = params.length;
	         for (int i = 0; i < count; i++) {
	        	 try {
	        		 questDAO.insert(params[i]);
	 	   		} catch (IOException e1) {
	 	   			e1.printStackTrace();	
	 	   			return false;
	 	   		}	
	         }
	   		return true;
	 	 }

	     protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Boolean result) {
	         showDialog("Save Quest was successful: " + result);
	         updateQuestList();
	     }
	 }
	 
	 private class LoadQuestListTask extends AsyncTask<Void, Void, List<Quest>> {
		 
		 @Override
		    protected void onPreExecute() {
			 questList.setAdapter(null);
		    }
		 
		 @Override
		 protected List<Quest> doInBackground(Void... params) {
	 	    List<Quest> quests = null;
			try {
				quests = (questDAO.loadAll());
		 	    
			} catch (IOException e) {
				e.printStackTrace();
			}
			return quests;	   		
	 	 }	
		 
		 @Override
		 protected void onPostExecute(List<Quest> result) {
			 
			 if (result == null || result.size() < 1) {
		        if (result == null) {
		        	showDialog("QuestList is not available :(");
		        } else {
		        	showDialog("QuestList is empty :(");
		        }
		        questList.setAdapter(null);
		        return;
		      }
		      ListAdapter questsListAdapter = createQuestsListAdapter(result);

		      questList.setAdapter(questsListAdapter);
	     }
		 
		 private ListAdapter createQuestsListAdapter(List<Quest> quests) {
		      List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		      for (Quest quest : quests) {
		        Map<String, Object> map = new HashMap<String, Object>();

		        map.put("questID", quest.getQuestId());
		        map.put("questName", quest.getName());
		        data.add(map);
		      }
		      
		      SimpleAdapter adapter = new SimpleAdapter(QuestDAOTestActivity.this, data,
		          R.layout.quest_list_item, new String[] {"questID", "questName"},
		          new int[] {R.id.quest_id, R.id.quest_name});
		      return adapter;
		    }
	 }
}

