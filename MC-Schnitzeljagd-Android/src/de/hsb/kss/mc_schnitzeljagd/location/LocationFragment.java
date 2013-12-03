package de.hsb.kss.mc_schnitzeljagd.location;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import de.hsb.kss.mc_schnitzeljagd.R;

/**
 * Facade to ease the use of the location frameworks
 * uses
 * 		google Maps API
 * 		google Play Services Location API
 * 
 * @author Ingo Pohlschneider
 *
 */
public class LocationFragment extends SupportMapFragment implements GooglePlayServicesClient.ConnectionCallbacks, 
											GooglePlayServicesClient.OnConnectionFailedListener,
											OnAddGeofencesResultListener,
									        LocationListener {
    /**
     * Tag for logging purposes
     */
    private String TAG = "LocationFragmentActivity";
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
	private float distance = Float.valueOf((float) 0.0);
	/**
	 * The MapFragment holding the GoogleMap
	 */
	private SupportMapFragment mapFragment;
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
	 * The hidden goal to be reached by the user
	 */
	private Location goal;
	/**
	 * Enables/Disables periodic location updates
	 */
	private boolean updatesRequested = false;
	/**
	 * Holds the preferences for the location service
	 */
	private SharedPreferences prefs;
	/**
	 * Holds the editor for the preferences of the location service
	 */
	private SharedPreferences.Editor editor;
	/**
	 * Holds the reference to the parent activity
	 */
	private FragmentActivity activity;
	/**
	 * Stores the view
	 */
	View view;
	/**
	 * Holds the reference to the parent activity as OnLocationChangedListener
	 */
	private OnLocationChangedListener listener;
	/**
	 *  Internal List of Geofence objects
	 */
    private List<Geofence> currentGeofences;
    /**
     *  Persistent storage for geofences
     */
    private SimpleGeofenceStore geofenceStorage;
    /**
     * The internal representation of the geofence
     */
    private SimpleGeofence goalGeofence;
    /**
     * The radius around the location of the goal for the geofence in meters.
     * Default value: 30m
     */
	private float goalRadius = 30;
	/**
	 * The ID of the goal.
	 */
	private String goalID;
	/**
	 * Stores the PendingIntent used to request geofence monitoring.
	 */
    private PendingIntent geofenceRequestIntent;
    /**
     * The request-type for the geofence.
     */
    private LocationUtils.REQUEST_TYPE requestType;
    /**
     * Flag that indicates if a request is underway.
     */
    private boolean inProgress;
	private PendingIntent transitionPendingIntent;


	/**
	 * Listener interface for the Activity to implement in order to be informed once location changes occur
	 */
	public interface OnLocationChangedListener {
		public void onLocationChanged();
	}
	
	/**
	 * Called upon attaching the fragment to the activity.
	 * First step of the Fragment-lifecycle.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof OnLocationChangedListener) {
			listener = (OnLocationChangedListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement " +
					"LocationFragment.OnLocationChangedListener");
		}
		
		// Log method call
		Log.d(TAG + ".onAttach()", "LocationFragment was successful attached.");
	}
	/**
     * Called upon creation of the fragment.
     * Second step of the Fragment-lifecycle.
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Map the FragmentActivity
		activity = getActivity();
		
		// Check if Google Services are available
		servicesConnected();
		// Start with the request flag set to false
        inProgress = false;
		// Instantiate a new geofence storage area
        geofenceStorage = new SimpleGeofenceStore(activity);
        // Instantiate the current List of geofences
        currentGeofences = new ArrayList<Geofence>();
        
        // Configure the location client
		configureLocationClient();
		// Initialize the location manager
	    locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
	    // Initialize the location client
	    locationClient = new LocationClient(activity.getBaseContext(), this, this);
	    
		Log.d(TAG + ".onCreate()", "LocationFragment was successful created.");
	}
	/**
	 * Called upon creation of the fragment.
	 * Third step of the Fragment-lifecycle.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.map_fragment, container, false);
		// Initialize the map
        initilizeMap();
		Log.d(TAG + ".onCreateView()", "LocationFragment View was successful inflated.");
		return view;
	}
	/**
	 * Called upon creation of the fragment.
	 * Fourth step of the Fragment-lifecycle.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	}
	/**
	 * Must be called if the parent Activity is started.
	 * Fifth step of the Fragment-lifecycle.
	 */
	@Override
	public void onStart() {
		super.onStart();
		// Connect the location client
		if (locationClient != null) {
			locationClient.connect();
		} else {
			Log.e(TAG + ".onStart()", "Could not connect location client - Nullpointer");
		}
		Log.d(TAG + ".onStart()", "LocationFragment was started.");
	}
	/**
	 * Must be called if the parent Activity is resumed.
	 * Sixth step of the Fragment-lifecycle.
	 */
	@Override
	public void onResume() {
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
		Log.d(TAG + ".onResume()", "LocationFragment was resumed.");
	}
	/**
	 * Must be called if the parent Activity is paused.
	 * Seventh step of the Fragment-lifecycle.
	 */
	@Override
	public void onPause() {
		// Save the current setting for updates
        editor.putBoolean("KEY_UPDATES_ON", updatesRequested);
        editor.commit();
        // Disconnect the location client
		locationClient.disconnect();
		Log.d(TAG + ".onPause()", "LocationFragment was paused.");
		super.onPause();
	}
	/**
	 * Must be called if the parent Activity is stopped.
	 * Eighth step of the Fragment-lifecycle.
	 */
	@Override
	public void onStop() {
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
		Log.d(TAG + ".onStop()", "LocationFragment was stopped.");
        super.onStop();
	}
	/**
	 * Called upon detaching the fragment from the activity.
	 * Eleventh step of the Fragment-lifecycle.
	 */
	@Override
	public void onDetach() {
		super.onDetach();
	    listener = null;
		Log.d(TAG + ".onDetach()", "LocationFragment was detached.");
	}
    
	/**
	 * Define a DialogFragment that displays the error dialog.
	 */
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
    /**
     * Handle results returned to the FragmentActivity by Google Play services.
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data an Intent with data
     */
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // Decide what to do based on the original request code
	        switch (requestCode) {
	            case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :
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
    /**
     * Check availability of Google Play Services.
     * @return state of the Google Play Services
     */
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity.getBaseContext());
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(TAG + ".servicesConnected()", "Google Play services is available.");
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            Log.d(TAG + ".servicesConnected()", "Google Play services is unavailable or outdated: " + resultCode);
            // Get the error dialog from Google Play services
            try {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 
                		LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST).show();
            } catch (Exception e) {
                Log.e("Error: GooglePlayServiceUtil: ", "" + e);
            }
           
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 
            		LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(activity.getSupportFragmentManager(), "Location Updates");
            }
            
            return false;
        }
    }
    /**
	 * configures update parameters for the location client.
	 */
	private void configureLocationClient() {
		// Create the LocationRequest object
        locationRequest = LocationRequest.create();
        // Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        locationRequest.setInterval(LocationUtils.UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        locationRequest.setFastestInterval(LocationUtils.FASTEST_INTERVAL);
        
        // Open the shared preferences
        prefs = activity.getSharedPreferences("SharedPreferences",Context.MODE_PRIVATE);
        // Get a SharedPreferences editor
        editor = prefs.edit();

		Log.d(TAG + ".configureLocationClient()", "LocationClient was configured.");
	}
	/**
     * Function to load map. If map is not created it will create it for you.
     **/
    private void initilizeMap() {
    	if (map == null) {
    		// Get the fragment via the Activity
    		SupportMapFragment locFrag = (SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.locationFragment);
    		// Get the map from the SupportMapFragment
            map = locFrag.getMap();
 
            // check if map is created successfully or not
            if (map == null) {
            	Log.e(TAG, "Sorry! unable to create maps");
            } else {
            	// Set users location on the map
        		map.setMyLocationEnabled(true);
        		// Enable MyLocation-Button
        		map.getUiSettings().setMyLocationButtonEnabled(true);
        		Log.d(TAG, "Map has been initialized");
            }
        }
    }
    /**
     * Forces a manual update of the location data.
     * @param v the current view
     */
    public void toggleManualLocationRefresh(View v) {
    	updateLocationInfos();

		Log.d(TAG + ".toggleManualLocationRefresh()", "Manual location refresh was triggered.");
    }
    /**
     * Updates the location info after the location has changed.
     */
    private void updateLocationInfos() {
	    currentLocation  = locationClient.getLastLocation();
		currentLatitude  = currentLocation.getLatitude();
        currentLongitude = currentLocation.getLongitude();
        currentLatLng    = new LatLng(currentLatitude, currentLongitude);
        distance		 = currentLocation.distanceTo(goal);
        
        // Center map on users location
 		CameraUpdate center = CameraUpdateFactory.newLatLng(currentLatLng);
 	    map.moveCamera(center);
 	    
 	    // If zoom is less than 15 zoom in to 15
 		if (map.getCameraPosition().zoom < 15) {
 			CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
 	 	    map.animateCamera(zoom);
 		} 
 	    
        Log.d(TAG + ".updateLocationInfos()", "Location was updated. Current location: " 
        		+ currentLatitude + "/" + currentLongitude);  
    }
	/**
	 * Alters the current map type of the map.
	 * @param type mapType The type to alter the map type to
	 */
	public void setMapType(MapType type) {
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
     * @param connectionResult the result of the connection attempt to the Location Service
     */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.d(TAG + ".onConnectionFailed()", "LocationClient could not be connected.");
		/*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(activity, 
                		LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
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
     * @param bundle with the connection hint
     */
	@Override
	public void onConnected(Bundle connectionHint) {
		Log.d(TAG + "onConnected()", "LocationClient connected");
		// If already requested, start periodic updates
		if (updatesRequested) {
            locationClient.requestLocationUpdates(locationRequest, this);
    		Log.d(TAG + "onConnected()", "Location updates enabled.");
        }
		
		// send request to set a geofence
//		switch (requestType) {
//	        case ADD :
//	            // Get the PendingIntent for the request
//	            transitionPendingIntent = getTransitionPendingIntent();
//	            // Send a request to add the current geofences
//	            locationClient.addGeofences(currentGeofences, transitionPendingIntent, this);
//		}

		// Set the location fields
		updateLocationInfos();
		
	    // Get a first fix to the Activity
		listener.onLocationChanged();
	}
	/**
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
	@Override
	public void onDisconnected() {
		// Turn off the request flag
        inProgress = false;
        
     // Destroy the current location client
        locationClient = null;

		Log.d(TAG + "onDisconnected()", "LocationClient disconnected");
	}
	/**
	 * Called by location service upon changes in the current location
	 * @param location the new location
	 */
	@Override
	public void onLocationChanged(Location location) {
		// Update internal state
		updateLocationInfos();
		
		// Report to the UI that the location was updated
		listener.onLocationChanged();
	}
	/**
	 * Enables the periodic updates of the user's location according to the settings
	 * @param v the calling view
	 */
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
	/**
	 * @return the current location of the user as LatLng-object
	 */
	public LatLng getLocation() {
	     return currentLatLng;
	}
	/**
	 * @return the longitude of the location of the user
	 */
	public double getCurrentLongitude() {
		return currentLongitude;
	}
	/**
	 * @return the latitude of the location of the user
	 */
	public double getCurrentLatitude() {
		return currentLatitude;
	}
	/**
	 * 
	 * @return the accuracy of the current location data
	 */
	public float getAccuracy() {
		return currentLocation.getAccuracy();
	}
	/**
	 * @return the status of the connection to the location service
	 */
	public boolean isConnected() {
		return locationClient.isConnected();
	}
	/**
	 * @return the approximate distance between the users current position and previously set goal in meters
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * @return the longitude of the current goal.
	 */
	public double getGoalLongitude() {
		return goal.getLongitude();
	}
	/**
	 * @return the latitude of the current goal.
	 */
	public double getGoalLatitude() {
		return goal.getLatitude();
	}
	/**
	 * Sets the hidden goal for the player to run to. The default radius is used for the geofence.
	 * @param goalID the ID of the goal
	 * @param longitude the longitude of the goal
	 * @param latitude the latitude of the goal
	 */
	public void setGoal(String goalID, Double longitude, Double latitude) {
		Location loc = new Location(Context.LOCATION_SERVICE);
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		goal = loc;
		Log.d(TAG + "setGoal()", "Goal " + goalID + " was set to " + longitude + "/" + latitude + 
				"with default radius of " + goalRadius);
		
		// Create geofence for goal
		//createGeofence();
	}
	/**
	 * Sets the hidden goal for the player to run to.
	 * @param goalID the ID of the goal
	 * @param longitude the longitude of the goal
	 * @param latitude the latitude of the goal
	 * @param radius the radius of the geofence
	 */
	public void setGoal(String goalID, Double longitude, Double latitude, Float radius) {
		Location loc = new Location(Context.LOCATION_SERVICE);
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		goal = loc;
		goalRadius = radius;
		
		Log.d(TAG + "setGoal()", "Goal " + goalID + " was set to " + longitude + "/" + latitude + 
				"with radius of " + radius);
		
		// Create geofence for goal
		//createGeofence();
	}
	/**
     * Get the geofence parameters for each geofence from the UI
     * and add them to a List.
     */
    public void createGeofence() {
    	//TODO: check for previously existing fences
        /*
         * Create an internal object to store the data. Set its
         * ID to goalID. This is a "flattened" object that contains
         * a set of strings
         */
        goalGeofence = new SimpleGeofence(
                goalID,
                goal.getLatitude(),
                goal.getLongitude(),
                goalRadius ,
                LocationUtils.GEOFENCE_EXPIRATION_TIME,
                // This geofence records only entry transitions
                Geofence.GEOFENCE_TRANSITION_ENTER);
        // Store this flat version
        geofenceStorage.setGeofence(goalID, goalGeofence);
        currentGeofences.add(goalGeofence.toGeofence());
        
		Log.d(TAG + "createGeofence()", "Geofence was created");
    }
    /**
     * Provide the implementation of OnAddGeofencesResultListener.onAddGeofencesResult.
     * Handle the result of adding the geofences
     */
    @Override
    public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
        // If adding the geofences was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
            //TODO: Handle successful addition of geofences here.You can send out a broadcast intent or update the UI. geofences into the Intent's extended data.

        } else {
        // If adding the geofences failed
            //TODO:  Report errors here.log the error using Log.e() or update the UI.
        }
        // Turn off the in progress flag
        inProgress = false;
    }

	/**
     * Start a request for geofence monitoring by calling LocationClient.connect().
     */
    public void addGeofences() {
        // Start a request to add geofences
        requestType = LocationUtils.REQUEST_TYPE.ADD;
        
        //TODO: bullshit demo code
        // If a request is not already underway
        if (!inProgress) {
            // Indicate that a request is underway
            inProgress = true;
            // Request a connection from the client to Location Services
            locationClient.connect();
        } else {
            /*
             * A request is already underway. You can handle
             * this situation by disconnecting the client,
             * re-setting the flag, and then re-trying the
             * request.
             */
        }
    }
    
    /*
     * Create a PendingIntent that triggers an IntentService in your
     * app when a geofence transition occurs.
     */
	private PendingIntent getTransitionPendingIntent() {
    	// Create an explicit Intent
        Intent intent = new Intent(activity, ReceiveTransitionsIntentService.class);
        /*
         * Return the PendingIntent
         */
        return PendingIntent.getService(activity.getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
	public void toggleGeofence(View v) {
		// TODO Auto-generated method stub
	}
}
