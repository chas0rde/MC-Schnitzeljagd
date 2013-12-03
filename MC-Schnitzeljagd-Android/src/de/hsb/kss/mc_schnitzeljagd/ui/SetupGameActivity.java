package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class SetupGameActivity extends SchnitzelActivity {

	private EditText authorTextField = null;
	private EditText gameNameTextField = null;	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_game);		
		initUi();
	}
	
	@Override
	protected void initUi()
	{
		super.initUi();
		TextView gameDescriptionTextField = (TextView)findViewById(R.id.setup_game_description_text_id);
		authorTextField = (EditText)findViewById(R.id.setup_game_author_id);
		gameNameTextField = (EditText)findViewById(R.id.setup_game_name_id);
		
		if(app != null && app.getGameCreation() != null && app.getGameCreation().getCurrentQuest() != null)
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
			//TODO: Replace by check new game
			if(app.getGameCreation().getCurrentQuest().getAccessCode() != null)
			{
				LinearLayout createLiveGameLayout = (LinearLayout)findViewById(R.id.setup_game_live_game_layout);
				
				if(createLiveGameLayout != null)
				{
					createLiveGameLayout.setVisibility(View.INVISIBLE);
				}
			}
		}
		else
		{
			if(errorLabel != null)
			{
				errorLabel.setText(R.string.unkownError);
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
