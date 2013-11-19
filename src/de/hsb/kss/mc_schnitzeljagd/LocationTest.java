package de.hsb.kss.mc_schnitzeljagd;

import android.os.Bundle;
import android.util.Log;

public class LocationTest extends LocationFragmentActivity {
	private static final String TAG = LocationTest.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.locationtest);
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