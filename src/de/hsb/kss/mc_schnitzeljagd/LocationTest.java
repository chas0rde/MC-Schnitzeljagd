package de.hsb.kss.mc_schnitzeljagd;

import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationTest extends Activity {
	private static final String TAG = LocationTest.class.getSimpleName(); private TextView textview;
	private LocationManager manager;
	private LocationListener listener;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); setContentView(R.layout.locationtest);
		textview = (TextView) findViewById(R.id.textview);
	   
		manager = (LocationManager)getSystemService(LOCATION_SERVICE);
		List<String> providers = manager.getAllProviders();
	   
		for (String name : providers) {
			LocationProvider lp = manager.getProvider(name); Log.d(TAG, lp.getName() +
					" --- isProviderEnabled(): " +
					manager.isProviderEnabled(name));
		   	Log.d(TAG, "requiresCell(): " +
					         lp.requiresCell());
			Log.d(TAG, "requiresNetwork(): " +
				         lp.requiresNetwork());
			Log.d(TAG, "requiresSatellite(): " +
					         lp.requiresSatellite());
		}
		
		Criteria criteria = new Criteria(); criteria.setAccuracy(Criteria.ACCURACY_COARSE); criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		String name = manager.getBestProvider(criteria, true); Log.d(TAG, name);
		
		listener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				Log.d(TAG, "onStatusChanged()");
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				Log.d(TAG, "onProviderEnabled()");
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				Log.d(TAG, "onProviderDisabled()");
			}
			
			@Override
			public void onLocationChanged(Location location) {
				Log.d(TAG, "onLocationChanged()");
				if (location != null) {
					String s = "Breite: " +	location.getLatitude() +
					"\nLänge: " + location.getLongitude();
					textview.setText(s);
				}
			}
			
		}; 
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0,listener);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
		manager.removeUpdates(listener);
	}
}