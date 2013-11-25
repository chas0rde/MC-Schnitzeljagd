package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class OrganizerCreatePoiActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_create_poi);
	}
	
	protected void initUi()
	{
		super.initUi();
	}
	
	public void createRiddle(View view){
		Intent intent = new Intent(this, PlayerRiddleActivity.class);
		startActivity(intent);
	}

	public void createHint(View view){
		Intent intent = new Intent(this, PlayerRiddleActivity.class);
		startActivity(intent);
	}
	public void publishQuest(View view)
	{
		Intent publishQuest = new Intent(this, PublishGameActivity.class);
		startActivity(publishQuest);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organizer_create_poi, menu);
		return true;
	}

}
