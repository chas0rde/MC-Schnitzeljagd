package de.hsb.kss.mc_schnitzeljagd.ui;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.R.layout;
import de.hsb.kss.mc_schnitzeljagd.R.menu;
import de.hsb.kss.mc_schnitzeljagd.location.LocationFragment;
import de.hsb.kss.mc_schnitzeljagd.location.OnGeofenceHit;
import de.hsb.kss.mc_schnitzeljagd.location.OnLocationChangedListener;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.ui.controls.HintsControl;
import de.hsb.kss.mc_schnitzeljagd.ui.controls.HintsControl.HintMode;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrganizerCreatePoiActivity extends SchnitzelActivity implements OnLocationChangedListener, OnGeofenceHit {
	private Button numberOfHintsButton;
	private int sizeOfHints;
	private int sizeOfRiddle;
	private HintMode hintmode = HintMode.HINT;
	LocationFragment locFrag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_organizer_create_poi);
		
		// Get a fragment manager (Support for 2.3.3 compatibility)
		FragmentManager fManager = getSupportFragmentManager();
		// Instantiate the LocationFragment
		locFrag = new LocationFragment();
		// Switch the map-fragment in your layout with the actual map-Fragment
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.map, locFrag);
        ft.commit();
		
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
	
	public void onLocationChanged()  {
		gameCreation.getCurrentPoint().setLatitude(locFrag.getCurrentLatitude());
		gameCreation.getCurrentPoint().setLongitude(locFrag.getCurrentLongitude());
	  
		// Count the location updates received
//		count++;
//		// Update UI with current location data stored in the LocationFragment
// 	    lat.setText(Double.toString(locFrag.getCurrentLatitude()));		// Users latitude
// 	    lng.setText(Double.toString(locFrag.getCurrentLongitude())); 	// Users longitude
// 	    quality.setText(Float.toString(locFrag.getAccuracy()));			// Accuracy of the measurement
// 	    conState.setText(Boolean.toString(locFrag.isConnected()));		// Status of the location client
// 	    refreshCount.setText(Integer.toString(count));					// The number of refreshs
// 	    distance.setText(Float.toString(locFrag.getDistance()));		// Approx. distance to goal
// 	    latGoal.setText(Double.toString(locFrag.getGoalLatitude()));	// Goal latitude
// 	    lngGoal.setText(Double.toString(locFrag.getGoalLongitude())); 	// Goal longitude
	}
    
	public void publishQuest(View view) {
		onLocationChanged();
		Intent publishQuest = new Intent(this, PublishGameActivity.class);
		startActivity(publishQuest);
	}
	
	// Saves the current Poi and creates a new one
	public void savePoint(View view){
		onLocationChanged();
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

	@Override
	public void onGeofenceHit() {
		// TODO Auto-generated method stub
		
	}

    public void onBackPressed() {
    	// to nothing
   }
}
