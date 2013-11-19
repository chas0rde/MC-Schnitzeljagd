package de.hsb.kss.mc_schnitzeljagd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import de.hsb.kss.mc_schnitzeljagd.R;

public class OrganizerHomeActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_home);
	}

	public void  startStartQuestActivity(View v)
	{
		Intent i = new Intent(this, SetupGameActivity.class);
		startActivity(i);
	}
	

	public void startLoadQuestActivity(View v)
	{
		Intent i = new Intent(this, LoadGameActivity.class);
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organizer_home, menu);
		return true;
	}

}
