package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HintActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hint);
		
		initView();
	}
		
	private void initView() {
		if(app != null)
		{
			TextView gameInfo = (TextView)findViewById(R.id.current_game_info);
			if(gameInfo != null)
			{
				gameInfo.setText(app.getGameLogic().getPlayer().getName());
			}
		}	
		
	}

	public void goToQuestActivity(View view) {
		Intent goToQuest = new Intent(this, QuestActivity.class);
		startActivity(goToQuest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hint, menu);
		return true;
	}

}
