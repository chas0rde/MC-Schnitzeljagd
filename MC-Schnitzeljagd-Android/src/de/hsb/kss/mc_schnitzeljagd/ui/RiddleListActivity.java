package de.hsb.kss.mc_schnitzeljagd.ui;

import android.os.Bundle;
import android.view.Menu;
import de.hsb.kss.mc_schnitzeljagd.R;

public class RiddleListActivity extends SchnitzelActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riddle_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.riddle_list, menu);
		return true;
	}

}