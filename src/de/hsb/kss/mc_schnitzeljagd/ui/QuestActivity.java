package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class QuestActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quest);
	}
	
	public void riddleActivity(View view) {
		Intent goToRiddle = new Intent(this, RiddleListActivity.class);
		startActivity(goToRiddle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quest, menu);
		return true;
	}

}
