package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.ui.controls.HintsControl;
import de.hsb.kss.mc_schnitzeljagd.ui.controls.HintsControl.HintMode;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrganizerCreatePoiActivity extends SchnitzelActivity {
	private Button numberOfHintsButton;
	private int sizeOfHints;
	private int sizeOfRiddle;
	private HintMode hintmode = HintMode.HINT;

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
		TextView poiInfoText = (TextView) findViewById(R.id.poi_info_text_id);
		poiInfoText.setText("Current Point: "+ (gameCreation.getCurrentQuest().getPointList().indexOf(gameCreation.getCurrentPoint()) + 1) );
		
		sizeOfHints = gameCreation.getHintSize();
		sizeOfRiddle = gameCreation.getRiddleSize();
		
		HintsControl hintsControl = (HintsControl)findViewById(R.id.control_panel_hints);
		HintsControl riddleControl = (HintsControl)findViewById(R.id.control_panel_riddles);
		
		hintsControl.setHintmode(HintMode.HINT);
		hintsControl.setHintButtonText("Hint");
		hintsControl.setHintNumber(sizeOfHints);

		riddleControl.setHintmode(HintMode.RIDDLE);
		riddleControl.setHintButtonText("Riddle");
		riddleControl.setHintNumber(sizeOfRiddle);
	}
	
	
    
	public void publishQuest(View view) {
		
		Intent publishQuest = new Intent(this, PublishGameActivity.class);
		startActivity(publishQuest);
	}
	
	// Saves the current Poi and creates a new one
	public void savePoint(View view){
		
		if((gameCreation.getCurrentPoint().getHintList() != null) && (!gameCreation.getCurrentPoint().getHintList().isEmpty())){
			gameCreation.addPoint(new Point());		
			Toast.makeText(this, "Currend Point was saved", Toast.LENGTH_SHORT).show();
			initUi();	
		} else{
			setErrorMsg("Point has no Hints");
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.organizer_create_poi, menu);
		return true;
	}

}
