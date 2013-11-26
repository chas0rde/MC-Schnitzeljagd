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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.locationtest);
		
		FragmentManager fManager = getSupportFragmentManager();
		locFrag = new LocationFragment();
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.map, locFrag);
        ft.addToBackStack(null);
        ft.commit();
        
        locFrag.setGoal("Test", Double.valueOf(8.83749747285), Double.valueOf(53.0663656578));
		
		lat = (TextView) findViewById(R.id.curLat);
		lng = (TextView) findViewById(R.id.curLng);
		quality = (TextView) findViewById(R.id.curQuality);
		conState = (TextView) findViewById(R.id.conState);
		refreshCount = (TextView) findViewById(R.id.count);
		distance = (TextView) findViewById(R.id.distance);
	}
	
	public void refreshLocation(View v) {
    	locFrag.toogleManualLocationRefresh(v);
    }
	public void toggleUpdates(View v) {
		locFrag.toggleUpdates(v);
		
	}

	public void onLocationChanged()  {
		count++;
 	    lat.setText(Double.toString(locFrag.getCurrentLatitude()));
 	    lng.setText(Double.toString(locFrag.getCurrentLongitude())); 
 	    quality.setText(Float.toString(locFrag.getAccuracy()));
 	    conState.setText(Boolean.toString(locFrag.isConnected()));
 	    refreshCount.setText(Integer.toString(count));
 	    distance.setText(Float.toString(locFrag.getDistance()));
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