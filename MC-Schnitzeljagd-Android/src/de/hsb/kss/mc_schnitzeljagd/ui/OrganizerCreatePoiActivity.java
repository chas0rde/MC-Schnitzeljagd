package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class OrganizerCreatePoiActivity extends SchnitzelActivity {
	private Button numberOfHintsButton;
	private int sizeOfHints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_create_poi);
		initUi();
	}
	
	protected void initUi()
	{
		super.initUi();
		// Initialize the Button and show the number of hints
		numberOfHintsButton = (Button) findViewById(R.id.number_of_hints_button);
		sizeOfHints = gameCreation.getHintSize();
		if (numberOfHintsButton != null) {
			numberOfHintsButton.setText(Integer.toString(sizeOfHints));
		} 

	}
	
	
	/**
	 * Starts an Activity that will contain and display a list of all recorded hints.
	 * @param view
	 */
	public void go_to_list_of_hints(View view) {
		Intent showListOfHints = new Intent(this, ListHintsActivity.class);
		startActivity(showListOfHints);
		
	}
	
	public void createRiddle(View view){
		Intent intent = new Intent(this, PlayerRiddleActivity.class);
		startActivity(intent);
	}

	public void createHint(View view){
		Spinner hintType = (Spinner)findViewById(R.id.record_hint_spinner_id);
		
		if(hintType != null)
		{	
			Intent intent = null;
			
			if(hintType.getSelectedItemPosition() == 0)
			{		
				intent = new Intent(this, PlayerTextHintActivity.class);
				startActivity(intent);
			}					
		}
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
