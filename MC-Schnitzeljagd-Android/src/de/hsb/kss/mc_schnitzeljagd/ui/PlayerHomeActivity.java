package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.persistence.model.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

public class PlayerHomeActivity extends SchnitzelActivity {
	
	private Quest q = null;
	private EditText code;
	private TextView desc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_home);
		initUi();
	}

	protected void initUi()
	{
		 super.initUi();
		 code = (EditText)findViewById(R.id.load_game_code);
		 desc = (TextView)findViewById(R.id.game_description_text);
	}
	
	public void loadGame(View v) {
				
		if(code != null && desc != null && app != null)
		{			
			q = app.getGameLogic().getQuestByAccessCode(code.getText().toString(), new Player());
			if(q != null){
				desc.setText(app.getGameLogic().getCurrentQuestDescription());
			} else {
				setErrorMsg("Quest not found!");
			}
		}
	}
	
	public void startQuest(View v)
	{
		EditText groupName = (EditText)findViewById(R.id.group_name_id);
		EditText code = (EditText)findViewById(R.id.load_game_code);
		
		if(groupName != null && code != null && app != null)
		{			
			if(app.getGameLogic().playNewGame(groupName.getText().toString(), code.getText().toString()))
			{
				Intent i = new Intent(this, HintActivity.class);
				startActivity(i);
			}
			else
			{
				if(code.getEditableText().toString().isEmpty()) {
					setErrorMsg(R.string.errorEnterCode);				
				} else {
					setErrorMsg(R.string.errorCodeNotFound);				
				}
			}
		}					
		else
		{
			setErrorMsg(R.string.unkownError);	
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_home, menu);
		return true;
	}
}
