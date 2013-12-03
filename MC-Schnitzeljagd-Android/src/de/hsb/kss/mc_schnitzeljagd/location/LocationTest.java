package de.hsb.kss.mc_schnitzeljagd.location;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.location.LocationFragment.OnLocationChangedListener;

/**
 * This class is a test-bed and guide on how to use the LocationFragment.
 * 
 * The following things have to be done to use the LocationFragment:
 * 
 * 1) Your Activity extends FragmentActivity and implements OnLocationChangedListener
 *    - The extend is required for compatibility to 2.3.3
 *    - The implement is required to inform the activity once the location has changed
 * 
 * 2) Your layout can have a fragment with id "map" and name "com.google.android.gms.maps.SupportMapFragment"
 *    This fragment can then be replaced by the LocationFragment which in fact delivers a GoogleMap for the user.
 *    The map shows the current position of the user
 *    
 * 3) You have to instantiate a LocationFragment in order to use its services. This automatically starts a location
 *    client that tracks the users position. Location updates are announced via the OnLocationChangedListener-Interface.
 *  
 * 4) You can set a goal for the user to go/fly/drive/ride to. If you do so the approximate distance (beeline) in meters 
 *    will be updated with the users position.
 *    Also depending on how you set the goal (w/ or w/o the radius) a geofence will be set up to inform you once the 
 *    user arrives at the goal.
 *    
 *    
 * Additionally location updates can be toggled and refreshed manually.
 */
public class LocationTest extends FragmentActivity implements OnLocationChangedListener {
	private static final String TAG = LocationTest.class.getSimpleName();
	
	private int count = 0;
	
	private TextView lat;
	private TextView lng;
	private TextView quality;
	private TextView conState;
	private TextView refreshCount;
	private TextView distance;
	private LocationFragment locFrag;
	private TextView latGoal;
	private TextView lngGoal;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.locationtest);
		
		// Get a fragment manager (Support for 2.3.3 compatibility)
		FragmentManager fManager = getSupportFragmentManager();
		// Instantiate the LocationFragment
		locFrag = new LocationFragment();
		// Switch the map-fragment in your layout with the actual map-Fragment
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.map, locFrag);
        ft.addToBackStack(null);
        ft.commit();
        
        // Set a goal using the (overloaded!) method setGoal
        locFrag.setGoal("Test", Double.valueOf(8.83749747285), Double.valueOf(53.0663656578));
		
        // Some GUI references
		lat = (TextView) findViewById(R.id.curLat);
		lng = (TextView) findViewById(R.id.curLng);
		quality = (TextView) findViewById(R.id.curQuality);
		conState = (TextView) findViewById(R.id.conState);
		refreshCount = (TextView) findViewById(R.id.count);
		distance = (TextView) findViewById(R.id.distance);
		latGoal = (TextView) findViewById(R.id.goalLat);
		lngGoal = (TextView) findViewById(R.id.goalLng);
	}
	
	/** 
	 * Example on how to trigger a manual refresh of the user location. 
	 * @param v the current view
	 */
	public void refreshLocation(View v) {
		locFrag.toggleManualLocationRefresh(v);
    }
	/**
	 * Example on how to toggle location updates.
	 * @param v the current view
	 */
	public void toggleUpdates(View v) {
		locFrag.toggleUpdates(v);	
	}
	/**
	 * Example on how to toggle geofence.
	 * @param v the current view
	 */
	public void toggleGeofence(View v) {
		locFrag.toggleGeofence(v);	
	}
	/**
	 * Example on how to update location information once the user's location has altered.
	 */
	public void onLocationChanged()  {
		// Count the location updates received
		count++;
		// Update UI with current location data stored in the LocationFragment
 	    lat.setText(Double.toString(locFrag.getCurrentLatitude()));		// Users latitude
 	    lng.setText(Double.toString(locFrag.getCurrentLongitude())); 	// Users longitude
 	    quality.setText(Float.toString(locFrag.getAccuracy()));			// Accuracy of the measurement
 	    conState.setText(Boolean.toString(locFrag.isConnected()));		// Status of the location client
 	    refreshCount.setText(Integer.toString(count));					// The number of refreshs
 	    distance.setText(Float.toString(locFrag.getDistance()));		// Approx. distance to goal
 	    lat.setText(Double.toString(locFrag.getGoalLatitude()));		// Goal latitude
 	    lng.setText(Double.toString(locFrag.getGoalLongitude())); 	// Goal longitude
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");
	}
}