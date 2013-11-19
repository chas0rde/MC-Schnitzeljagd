package de.hsb.kss.mc_schnitzeljagd;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Facade to ease the use of the location frameworks
 * uses
 * 		google Maps API
 * 		google Play Services Location API
 * 
 * @author Ingo Pohlschneider
 *
 */
public class LocationFragmentActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks, 
											GooglePlayServicesClient.OnConnectionFailedListener,
									        LocationListener {
	// Global constants
    /**
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    /**
     * Tag for logging purposes
     */
    private String TAG = "LocationFacadeImpl";
	/**
	 *  Milliseconds per second
	 */
    private static final int MILLISECONDS_PER_SECOND = 1000;
    /**
     *  Update frequency in seconds
     */
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    /**
     *  Update frequency in milliseconds
     */
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    /**
     *  The fastest update frequency, in seconds
     */
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    /**
     *  A fast frequency ceiling in milliseconds
     */
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    /**
	 * Possible types of maps
	 * @author Ingo Pohlschneider
	 *
	 */
	public enum mapType {
		/**
		 * Normal map view
		 */
		NORMAL,
		/**
		 * Hybrid map (mixes normal and satellite view)
		 */
		HYBRID,
		/**
		 * Satellite view map
		 */
		SATELLITE,
		/**
		 * Terrain view map
		 */
		TERRAIN,
		/**
		 * No map type
		 */
		NONE
	}
	/**
	 * The map object
	 */
	private GoogleMap map;
	/**
	 * The current location of the user
	 */
	private Location currentLocation;
	/**
	 * The latitude and longitude of the user as double values
	 */
	private double currentLatitude, currentLongitude;
	/**
	 * The latitude and longitude of the user as LatLng-object
	 */
	private LatLng currentLatLng;
	/**
	 * Approximate distance between the users position and another location
	 */
	private int distance;
	/**
	 * The MapFragment holding the GoogleMap
	 */
	private MapFragment mapFragment;
	/**
	 * The location manager
	 */
	private LocationManager locationManager;
	/**
	 * The location listener
	 */
	private LocationListener locationListener;
	/**
	 * The location service client
	 */
	private LocationClient locationClient;
	/**
	 * The location request
	 */
	LocationRequest locationRequest;
	

	private boolean updatesRequested;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private int count = 0;
	
	private TextView lat;
	private TextView lng;
	private TextView quality;
	private TextView conState;
	private TextView refreshCount;
	private TextView listenerName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_facade);
		
		lat = (TextView) findViewById(R.id.curLat);
		lng = (TextView) findViewById(R.id.curLng);
		quality = (TextView) findViewById(R.id.curQuality);
		conState = (TextView) findViewById(R.id.conState);
		refreshCount = (TextView) findViewById(R.id.count);
		listenerName = (TextView) findViewById(R.id.listener);
		
		servicesConnected();
		
		mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		if (mapFragment != null){
			initilizeMap();
			configureLocationClient();
			
		    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	        // Start with updates turned off
	        updatesRequested = true;

		    locationClient = new LocationClient(this, this, this);
		    
		}
		Log.d(TAG, "LocationFacade has been initialized");
	}
	
	// Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    /*
     * Handle results returned to the FragmentActivity
     * by Google Play services
     */
    @Override
    protected void onActivityResult(
        int requestCode, int resultCode, Intent data) {
	        // Decide what to do based on the original request code
	        switch (requestCode) {
	            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
	            /*
	             * If the result code is Activity.RESULT_OK, try
	             * to connect again
	             */
                switch (resultCode) {
                    case Activity.RESULT_OK :
                    /*
                     * Try the request again
                     */
                    break;
                }
	        }
     }
    
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Get the error code
            int errorCode = resultCode;//connectionResult.getErrorCode();
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this,CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getSupportFragmentManager(),"Location Updates");
            }
            
            return false;
        }
    }


	private void configureLocationClient() {
		// Create the LocationRequest object
        locationRequest = LocationRequest.create();
        // Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        locationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        
        // Open the shared preferences
        prefs = getSharedPreferences("SharedPreferences",Context.MODE_PRIVATE);
        // Get a SharedPreferences editor
        editor = prefs.edit();
	}

	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
    	if (map == null) {
            map = mapFragment.getMap();
 
            // check if map is created successfully or not
            if (map == null) {
            	Log.e(TAG, "Sorry! unable to create maps");
            } else {
            	// Set users location on the map
        		map.setMyLocationEnabled(true);
        		Log.d(TAG, "Map has been initialized");
            }
        }
    }
    
    public void refreshLocation(View v) {
    	updateLocationInfos();
    }
    
    /**
     * Updates the location infos after the location has changed
     */
    private void updateLocationInfos() {
	    currentLocation  = locationClient.getLastLocation();
		currentLatitude  = currentLocation.getLatitude();
        currentLongitude = currentLocation.getLongitude();
        currentLatLng    = new LatLng(currentLatitude, currentLongitude);
        
        // Center map on users location
 		CameraUpdate center = CameraUpdateFactory.newLatLng(currentLatLng);
 		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

 	    map.moveCamera(center);
 	    map.animateCamera(zoom);
        
 	    lat.setText(Double.toString(currentLatitude));
 	    lng.setText(Double.toString(currentLongitude)); 
 	    quality.setText(Float.toString(currentLocation.getAccuracy()));
 	    conState.setText(Boolean.toString(locationClient.isConnected()));
 	    refreshCount.setText(Integer.toString(count));
 	    listenerName.setText(locationClient.toString());
 	    
        Log.d(TAG, "Current location: " + currentLatitude + "/" + currentLongitude);  
    }
	/**
	 * Alters the current map type of the map
	 * @param type mapType The type to alter the map type to
	 */
	public void setMapType(mapType type) {
		switch (type) {
		case NORMAL:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case HYBRID:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case SATELLITE:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case TERRAIN:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case NONE:
			map.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		default:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		}		
	}
	/**
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //TODO: showErrorDialog(connectionResult.getErrorCode());
        }
    }

	/**
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
	@Override
	public void onConnected(Bundle connectionHint) {
		Log.d(TAG, "LocationClient connected");
		// If already requested, start periodic updates
		if (updatesRequested) {
            locationClient.requestLocationUpdates(locationRequest, this);
        }

		// Set the location fields
		updateLocationInfos();
	}
	/**
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
	@Override
	public void onDisconnected() {
		Log.d(TAG, "LocationClient disconnected");
	}
	/**
	 * Must be called if the parent Activity is started
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the location client
		locationClient.connect();
	}
	/**
	 * Must be called if the parent Activity is paused
	 */
	@Override
	protected void onPause() {
		// Save the current setting for updates
        editor.putBoolean("KEY_UPDATES_ON", updatesRequested);
        editor.commit();
        // Disconnect the location client
		locationClient.disconnect();
		super.onPause();
	}
	/**
	 * Must be called if the parent Activity is stopped
	 */
	@Override
	protected void onStop() {
		// If the client is connected
        if (locationClient.isConnected()) {
            /*
             * Remove location updates for a listener.
             * The current Activity is the listener, so
             * the argument is "this".
             */
            locationClient.removeLocationUpdates(this);
        }
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        locationClient.disconnect();
        super.onStop();
	}
	/**
	 * Must be called if the parent Activity is resumed
	 */
	@Override
	protected void onResume() {
		super.onResume();
		/*
         * Get any previous setting for location updates
         * Gets "false" if an error occurs
         */
        if (prefs.contains("KEY_UPDATES_ON")) {
            updatesRequested = prefs.getBoolean("KEY_UPDATES_ON", false);
        // Otherwise, turn off location updates
        } else {
            editor.putBoolean("KEY_UPDATES_ON", false);
            editor.commit();
        }
	}
	/**
	 * @return the current location of the user as LatLng-object
	 */
	public LatLng getLocation() {
	     return currentLatLng;
	}
	/**
	 * @return a GoogleMap zoomed into the current position of the user
	 */
	public GoogleMap getMap() {
		return map;
	}
	/**
	 * @return the longitude of the location
	 */
	public double getLongitude(Location location) {
		return currentLongitude;
	}
	/**
	 * @return the latitude of the location
	 */
	public double getLatitude(Location location) {
		return currentLatitude;
	}
	/**
	 * @param location
	 * @return the approximate distance between the users current position and location in meters
	 */
	public int getApproximateDistance(Location location) {
		return distance;
	}

	@Override
	public void onLocationChanged(Location location) {
		count++;
		// Report to the UI that the location was updated
		updateLocationInfos();
	}
	public void toggleUpdates(View v) {
		boolean updatesRequested = ((ToggleButton) v).isChecked();
		if(updatesRequested) {
            locationClient.requestLocationUpdates(locationRequest, this);
            Log.d(TAG, "Location updates enabled");			
		} else {
			locationClient.removeLocationUpdates(this);
            Log.d(TAG, "Location updates disabled");
		}
	}
}
