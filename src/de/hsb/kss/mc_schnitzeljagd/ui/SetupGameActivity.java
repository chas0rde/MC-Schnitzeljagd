package de.hsb.kss.mc_schnitzeljagd.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;

public class SetupGameActivity extends SchnitzelActivity {

	private EditText authorTextField = (EditText)findViewById(R.id.setup_game_author_id);
	private EditText gameNameTextField = (EditText)findViewById(R.id.setup_game_name_id);	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_game);		
	}
	
	@Override
	protected void initUi()
	{
		super.initUi();
		TextView gameDescriptionTextField = (TextView)findViewById(R.id.setup_game_description_text_id);
		
		if(app != null)
		{
			if(authorTextField != null)
			{				
				authorTextField.setText(app.getGameCreation().getCurrentQuest().getAuthor());
			}			
			if(gameNameTextField != null)
			{
				gameNameTextField.setText(app.getGameCreation().getCurrentQuest().getName());
			}
			if(gameDescriptionTextField != null)
			{
				gameDescriptionTextField.setText(app.getGameCreation().getCurrentQuest().toString());
			}
			if(!app.getGameCreation().getCurrentQuest().getAccessCode().isEmpty())
			{
				LinearLayout createLiveGameLayout = (LinearLayout)findViewById(R.id.setup_game_live_game_layout);
				
				if(createLiveGameLayout != null)
				{
					createLiveGameLayout.setVisibility(0);
				}
			}
		}
		else
		{
			if(errorLabel != null)
			{
				errorLabel.setText(R.string.requiredFiledNotSet);
			}
		}
	}
	
	public void startCreateQuest(View view) {
		if(authorTextField != null && gameNameTextField != null && app != null)
		{	
			if(!authorTextField.getText().toString().isEmpty() &&
				!gameNameTextField.getText().toString().isEmpty() )
			{
				Quest q = app.getGameCreation().getCurrentQuest();
				q.setAuthor(authorTextField.getText().toString());
				q.setName(gameNameTextField.getText().toString());
				Intent startQuest = new Intent(this, OrganizerCreatePoiActivity.class);
				startActivity(startQuest);
			}	
			else
			{
				if(errorLabel != null)
				{
					errorLabel.setText(R.string.requiredFiledNotSet);
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_game, menu);
		return true;
	}
}
