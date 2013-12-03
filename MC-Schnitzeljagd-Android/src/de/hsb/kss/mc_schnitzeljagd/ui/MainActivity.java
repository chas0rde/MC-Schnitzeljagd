package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import de.hsb.kss.mc_schnitzeljagd.R;

public class MainActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startOrganizerActivity(View v)
	{
		Intent i = new Intent(this, OrganizerHomeActivity.class);
		startActivity(i);
	}
	

	public void startPlayerActivity(View v)
	{
		Intent i = new Intent(this, PlayerHomeActivity.class);
		startActivity(i);
	}
	
	
	public void startActivityLocationTest(View v) {
		Intent i = new Intent(this, de.hsb.kss.mc_schnitzeljagd.location.LocationTest.class);
		startActivity(i);
	}
	public void startActivityQuestDAOTest(View v) {
		Intent i = new Intent(this, de.hsb.kss.mc_schnitzeljagd.persistence.QuestDAOTestActivity.class);
		startActivity(i);
	}
}
