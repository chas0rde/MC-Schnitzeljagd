package de.hsb.kss.mc_schnitzeljagd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

public class LocationTest extends Activity {
	private static final String TAG = LocationTest.class.getSimpleName();
	private GoogleMap map;
	private LocationFacadeImpl lfi;

	TextView lat;
	TextView lng;
	TextView quality;
	TextView conState;
	TextView count;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.locationtest);
		
	    try {
            // Loading map
//	    	lfi = LocationFacadeImpl.getInstance();
//	    	lfi.init(this);
	    	
	    	map = lfi.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void updateUI(double lat, double lng, float quality, 
			boolean conSta, int cnt){
		this.lat.setText(Double.toString(lat));
		this.lng.setText(Double.toString(lng));
		this.count.setText(Integer.toString(cnt));
		this.quality.setText(Float.toString(quality));
		this.conState.setText(Boolean.toString(conSta));
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
		lfi.onStart();
	}
	
	@Override
	protected void onPause() {
		lfi.onPause();
		super.onPause();
		Log.d(TAG, "onPause()");
	}
	
	@Override
	protected void onStop() {
		lfi.onStop();
		super.onStop();
		Log.d(TAG, "onStop()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		lfi.onResume();
		Log.d(TAG, "onResume()");
	}
}