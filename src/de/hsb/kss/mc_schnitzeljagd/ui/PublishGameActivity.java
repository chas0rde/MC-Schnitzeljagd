package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PublishGameActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_game);	
		if(app != null)
		{
			app.getGameCreation().save();
		}
		initUi();
	}
	
	protected void initUi()
	{
		super.initUi();
		TextView code = (TextView)findViewById(R.id.share_access_code_id); 
		if(code != null && app != null)
		{
			code.setText(app.getGameCreation().getCurrentQuest().getAccessCode());
		}
	}

	public void backToMainActivity(View view){
		Intent goBackToMain = new Intent(this, MainActivity.class);
		startActivity(goBackToMain);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publish_game, menu);
		return true;
	}

}
