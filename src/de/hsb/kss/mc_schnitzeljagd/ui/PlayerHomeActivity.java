package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.McSchnitzelJagdApplication;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerHomeActivity extends SchnitzelActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_home);
	}

	public void loadGame(View v){
		EditText code = (EditText)findViewById(R.id.load_game_code);
		TextView desc = (TextView)findViewById(R.id.game_description_text);
		
		if(code != null && desc != null && app != null)
		{			
			Quest q = app.getGameLogic().getQuestByAccessCode(code.getText().toString());
			desc.setText(q.toString());
		}
	}
	
	public void startQuest(View v)
	{
		EditText groupName = (EditText)findViewById(R.id.group_name_id);
		EditText code = (EditText)findViewById(R.id.load_game_code);
		
		if(groupName != null && code != null && app != null)
		{			
			app.getGameLogic().playNewGame(groupName.getText().toString(), code.getText().toString());
		}
		
		Intent i = new Intent(this, HintActivity.class);
		startActivity(i);				
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_home, menu);
		return true;
	}

}
