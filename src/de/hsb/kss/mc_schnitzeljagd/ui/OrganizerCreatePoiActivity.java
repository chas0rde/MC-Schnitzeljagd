package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OrganizerCreatePoiActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_create_poi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organizer_create_poi, menu);
		return true;
	}

}
