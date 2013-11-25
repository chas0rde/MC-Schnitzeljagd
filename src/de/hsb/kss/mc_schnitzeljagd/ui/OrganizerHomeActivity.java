package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;

public class OrganizerHomeActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_home);
	}

	public void  startStartQuestActivity(View v)
	{
		TextView errorLabel = (TextView)findViewById(R.id.error_text);
		
		if(app != null && errorLabel != null)
		{			
			app.getGameCreation().createNewQuest();
			Intent i = new Intent(this, SetupGameActivity.class);
			startActivity(i);
		}
		else
		{
			errorLabel.setText(R.string.unkownError);
		}
	}
	

	public void startLoadQuestActivity(View v)
	{
		EditText code = (EditText)findViewById(R.id.load_game_code);
		
		if(code != null && errorLabel != null && app != null)
		{			
			if(app.getGameCreation().loadQuestByAccessCode(code.getText().toString()))
			{
				Intent i = new Intent(this, SetupGameActivity.class);
				startActivity(i);
			}
			else
			{
				errorLabel.setText(R.string.questNotFound);
			}
		}	
		else
		{
			errorLabel.setText(R.string.unkownError);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organizer_home, menu);
		return true;
	}
}
