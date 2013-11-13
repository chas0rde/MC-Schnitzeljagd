package de.hsb.kss.mc_schnitzeljagd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

/**
 * Facade to ease the use of the location frameworks
 * uses
 * 		google Maps API
 * 		google Play Services Location API
 * 
 * @author Ingo Pohlschneider
 *
 */
public class LocationFacadeImpl implements LocationFacade, GooglePlayServicesClient.ConnectionCallbacks, 
											GooglePlayServicesClient.OnConnectionFailedListener,
									        LocationListener {
	String TAG = "LocationFacadeImpl";
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
	 * Singleton
	 */
	private static LocationFacadeImpl INSTANCE;
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
	 * The Activity's context
	 */
	private Context context;
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
	/**
	 * The parent Activity
	 */
	private Activity linkedActivity;
	private MapController mapController;
	private String provider;
	private MapView mapView;
	private boolean updatesRequested;
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private int count = 0;

	/**
	 * Private constructor - Singleton Design Pattern
	 */
	private LocationFacadeImpl() {
		super();
		//TODO: Implement check for google play services
		//TODO: Implement check for google maps API key
		/*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
	}
	
	/**
	 * Initializes the LocationFacadeImpl
	 */
	public void init(Activity activity) {
		setActivity(activity);
		if (mapFragment != null){
			initilizeMap();
			configureLocationClient();
			
		    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

	        // Start with updates turned off
	        updatesRequested = true;

		    locationClient = new LocationClient(context, this, this);
		    
		}
		Log.d(TAG, "LocationFacade has been initialized");
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
        prefs = linkedActivity.getSharedPreferences("SharedPreferences",Context.MODE_PRIVATE);
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
    /**
     * Callback-method called if location has changed
     */
    @Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "onStatusChanged(): new location: " + location.getLatitude() + "/" + location.getLongitude());
        count++;
        // Update the location information
        updateLocationInfos();        
	}
    /**
     * Updates the location infos after the location has changed
     */
    private void updateLocationInfos() {
	    currentLocation  = locationClient.getLastLocation();
		currentLatitude  = currentLocation.getLatitude();
        currentLongitude = currentLocation.getLatitude();
        currentLatLng    = new LatLng(currentLatitude, currentLongitude);
        
        // Center map on users location
 		CameraUpdate center = CameraUpdateFactory.newLatLng(currentLatLng);
 		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

 	    map.moveCamera(center);
 	    map.animateCamera(zoom);
        
 	    //TODO: update infos on screen
 	    LocationTest act = (LocationTest) linkedActivity;
 	    act.updateUI(currentLatitude, currentLongitude, 
 	    		currentLocation.getAccuracy(), locationClient.isConnected(), count);
 	    
        Log.d(TAG, "Current location: " + currentLatitude + "/" + currentLongitude);  
    }
	/**
	 * Getter for the single instance of the LocationFacadeImpl
	 * @return the single INSTANCE of the LocationFacadeImpl
	 */
	public static synchronized LocationFacadeImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LocationFacadeImpl();
	    }
	    return INSTANCE;
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
	    //TODO: handle this stuff
       Log.e(TAG, "LocationClient connect failed: " + connectionResult.getErrorCode());
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
	 * Sets the calling activity to inject map etc.
	 * @param activity
	 */
	public void setActivity(Activity activity) {
		if(activity != null) {
			linkedActivity = activity;
	    	mapFragment = (MapFragment) linkedActivity.getFragmentManager().findFragmentById(R.id.map);
	    	context = linkedActivity.getBaseContext();
		}
	}
	/**
	 * Must be called if the parent Activity is started
	 */
	public void onStart() {
		// Connect the location client
		locationClient.connect();
	}
	/**
	 * Must be called if the parent Activity is paused
	 */
	public void onPause() {
		// Save the current setting for updates
        editor.putBoolean("KEY_UPDATES_ON", updatesRequested);
        editor.commit();
        // Disconnect the location client
		locationClient.disconnect();
	}
	/**
	 * Must be called if the parent Activity is stopped
	 */
	public void onStop() {
		// If the client is connected
        if (locationClient.isConnected()) {
            /*
             * Remove location updates for a listener.
             * The current Activity is the listener, so
             * the argument is "this".
             */
            locationClient.removeLocationUpdates(this);
    		Log.d(TAG, "Periodic updates disabled");
        }
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        locationClient.disconnect();

	}
	/**
	 * Must be called if the parent Activity is resumed
	 */
	public void onResume() {
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
	@Override
	public double getLongitude(Location location) {
		return currentLongitude;
	}
	/**
	 * @return the latitude of the location
	 */
	@Override
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
}
