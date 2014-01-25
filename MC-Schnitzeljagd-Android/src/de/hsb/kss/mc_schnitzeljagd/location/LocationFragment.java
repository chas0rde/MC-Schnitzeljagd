package de.hsb.kss.mc_schnitzeljagd.location;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
									        LocationListener {
	/*
	 * ############## START FIELD DEFINITIONS #########################################################################
	 */
	
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
	 * The location manager
	 */
	private LocationManager locationManager;
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
	private boolean updatesRequested = true;
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
	private float goalRadius = LocationUtils.DEFAULT_GEOFENCE_RADIUS;
	/**
	 * The ID of the goal.
	 */
	private String goalID;
    /**
     * The GeofenceRequester used to request geofences from the LocationClient.
     */
    private GameGeofenceRequester geofenceRequester;
    /**
     * The GeofenceRemover used to remove geofences from the LocationClient.
     */
    private GeofenceRemover geofenceRemover;
    /**
     * The state of the visualization of the geofence.
     */
    private boolean showGeofence = true;
	private BroadcastReceiver geofenceTransitionReceiver;
	private IntentFilter intentFilter;

	/*
	 * ############## END FIELD DEFINITIONS ###########################################################################
	 */

	/*
	 * ############## START FRAGMENT LIFECYCLE ########################################################################
	 */
	
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
		LocationUtils.servicesConnected(activity);
		
        // Configure the location client
		configureLocationClient();
		
		// Initialize the location manager
	    locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
	    // Initialize the location client
	    locationClient = new LocationClient(activity.getBaseContext(), this, this);
	    
		// configure use of geofences
		configureGeofences();
		
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
		Log.d(TAG + ".onStart()", "LocationFragment was started.");
	}
	/**
	 * Must be called if the parent Activity is resumed.
	 * Sixth step of the Fragment-lifecycle.
	 */
	@Override
	public void onResume() {
		super.onResume();
		// Connect the location client
		if (locationClient != null) {
			locationClient.connect();
		} else {
			Log.e(TAG + ".onResume()", "Could not connect location client - Nullpointer");
		}
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
        

        // Register the broadcast receiver to receive status updates
        LocalBroadcastManager.getInstance(activity).registerReceiver(geofenceTransitionReceiver, intentFilter);
        
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
		if(locationClient.isConnected()) {
			locationClient.disconnect();
		}
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
            /*
             * Remove geofences if they exist
             */
            clearGeofences();
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
	
	/*
	 * ############## END FRAGMENT LIFECYCLE ##########################################################################
	 */
	
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
    		SupportMapFragment locFrag = (SupportMapFragment) activity.getSupportFragmentManager().
    				findFragmentById(R.id.locationFragment);
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
	    // Check if a last location was even found
	    if (currentLocation != null) {
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
	    } else {
	    	Log.d(TAG + ".updateLocationInfos()", "Location failed. No last location known");
	    } 
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
     * Called by Location Services if the attempt to Location Services fails.
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
     * Called by Location Services when the request to connect the client finishes successfully. 
     * At this point, you can request the current location or start periodic updates
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
		
		// Set the location fields
		updateLocationInfos();

		// TODO set geofence if already a goal there
		if (currentGeofences != null) {
			try {
	            // Try to add geofences
	 			geofenceRequester.addGeofences(currentGeofences);
	        } catch (UnsupportedOperationException e) {
	            // Notify user that previous request hasn't finished.
	            Toast.makeText(activity, R.string.add_geofences_already_requested_error, Toast.LENGTH_LONG).show();
	        }
    	}
		
	    // Get a first fix to the Activity
		listener.onLocationChanged();
	}
	/**
     * Called by Location Services if the connection to the location client drops because of an error.
     */
	@Override
	public void onDisconnected() {        
		// Destroy the current location client
        locationClient = null;

		Log.d(TAG + "onDisconnected()", "LocationClient disconnected");
	}
	/**
	 * Called by location service upon changes in the current location.
	 * @param location the new location
	 */
	@Override
	public void onLocationChanged(Location location) {
		// Update internal state
		updateLocationInfos();
		
		// Visualize the geofence if enabled
		if (showGeofence) {
			addMarkerForFence();
		}
		// Report to the UI that the location was updated
		listener.onLocationChanged();
	}

    /*
	 * ############## START GEOFENCE METHODS ##########################################################################
	 */
    
    /**
     * configures the parameters for the use of geofences.
     */
    private void configureGeofences() {
		// Instantiate a new geofence storage area
        geofenceStorage = new SimpleGeofenceStore(activity.getBaseContext());
        // Instantiate the current List of geofences
        currentGeofences = new ArrayList<Geofence>();

        // The filter's action is ACTION_GEOFENCE_TRANSITION
        intentFilter = new IntentFilter(LocationUtils.ACTION_GEOFENCE_TRANSITION);        
        // Create a new broadcast receiver to receive updates from the listeners and service
        geofenceTransitionReceiver = new GeofenceTransitionReceiver();
        // Action for broadcast Intents that report successful addition of geofences
        intentFilter.addAction(LocationUtils.ACTION_GEOFENCES_ADDED);
        // Action for broadcast Intents that report successful removal of geofences
        intentFilter.addAction(LocationUtils.ACTION_GEOFENCES_REMOVED);
        // Action for broadcast Intents containing various types of geofencing errors
        intentFilter.addAction(LocationUtils.ACTION_GEOFENCE_ERROR);
        // All Location Services sample apps use this category
        intentFilter.addCategory(LocationUtils.CATEGORY_LOCATION_SERVICES);
        // Get a GeofenceRequester
        geofenceRequester = new GameGeofenceRequester(activity, locationClient);
        // Get a GeofenceRemover
        geofenceRemover = new GeofenceRemover(activity.getBaseContext(), locationClient);
    }
    /**
     * Clears all existing geofences using {@link GeofenceRemover}.
     */
    private void clearGeofences() {
    	List<String> ids = new ArrayList<String>();
    	for(Geofence g : currentGeofences) {
    		ids.add(g.getRequestId());
    	}
    	geofenceRemover.removeGeofencesById(ids);
    }
    /**
     * Get the geofence parameters for each geofence from the UI and add them to a List.
     */
    private void createGeofence() {
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
     * Creates a marker and a circle to visualize the geofence created with the goal.
     */
    private void addMarkerForFence(){
    	SimpleGeofence geofence = goalGeofence;
    	
    	if(geofence == null){
    	    // display an error message and return
    	   return;
    	}
    	map.addMarker( new MarkerOptions()
    	  .position( new LatLng(geofence.getLatitude(), geofence.getLongitude()) )
    	  .title("Geofence " + geofence.getId())
    	  .snippet("Radius: " + geofence.getRadius()) ).showInfoWindow();
    	 
    	//Instantiates a new CircleOptions object +  center/radius
    	CircleOptions circleOptions = new CircleOptions()
    	  .center( new LatLng(geofence.getLatitude(), geofence.getLongitude()) )
    	  .radius( geofence.getRadius() )
    	  .fillColor(0x40ff0000)
    	  .strokeColor(Color.TRANSPARENT)
    	  .strokeWidth(2);
    	 
    	// Get back the mutable Circle
    	map.addCircle(circleOptions);
    }
    
    /*
	 * ############## END GEOFENCE METHODS ############################################################################
	 */
    
    /*
     * ############## START GETTER AND SETTER #########################################################################
     */
    
	/**
	 * Returns a LatLng object containing the current location.
	 * @return the current location of the user as LatLng-object
	 */
	public LatLng getLocation() {
	     return currentLatLng;
	}
	/**
	 * Returns the current locations longitude as Double.
	 * @return the longitude of the location of the user
	 */
	public double getCurrentLongitude() {
		return currentLongitude;
	}
	/**
	 * Returns the current locations latituode as Double.
	 * @return the latitude of the location of the user
	 */
	public double getCurrentLatitude() {
		return currentLatitude;
	}
	/**
	 * Returns the accuracy of the current location data as Float.
	 * @return the accuracy of the current location data
	 */
	public float getAccuracy() {
		return currentLocation.getAccuracy();
	}
	/**
	 * Returns the current status of the connection to the location service.
	 * Is true if the LocationFragment is connected to the location service. 
	 * @return the status of the connection to the location service
	 */
	public boolean isConnected() {
		return locationClient.isConnected();
	}
	/**
	 * Returns the calculated axpproximate bee-line distance between the current location and the previously set goal.
	 * Value is a Float representing meters.
	 * @return the approximate distance between the users current position and previously set goal in meters
	 */
	public float getDistance() {
		return distance;
	}
	/**
	 * Returns the longitudinal component of the goal-location as Double.
	 * @return the longitude of the current goal.
	 */
	public double getGoalLongitude() {
		return goal.getLongitude();
	}
	/**
	 * Returns the latitudinal component of the goal-location as Double.
	 * @return the latitude of the current goal.
	 */
	public double getGoalLatitude() {
		return goal.getLatitude();
	}
	/**
	 * Returns the currently selected {@link MapType} of the {@link GoogleMap} shown to the user.
	 * @return the current MapType of the map.
	 */
	public MapType getMapType() {
		int type = map.getMapType();
		switch (type) {
		case GoogleMap.MAP_TYPE_HYBRID:
			return MapType.HYBRID;
		case GoogleMap.MAP_TYPE_NONE:
			return MapType.NONE;
		case GoogleMap.MAP_TYPE_NORMAL:
			return MapType.NORMAL;
		case GoogleMap.MAP_TYPE_SATELLITE:
			return MapType.SATELLITE;
		case GoogleMap.MAP_TYPE_TERRAIN:
			return MapType.TERRAIN;
		default:
			return MapType.NORMAL;
		}
	}
	/**
	 * Sets the hidden goal for the player to run to. The default radius is used for the geofence.
	 * @param id the ID of the goal
	 * @param longitude the longitude of the goal
	 * @param latitude the latitude of the goal
	 */
	public void setGoal(String id, Double longitude, Double latitude) {
		// Call overloaded method with the default geofence-radius
		setGoal(id, longitude, latitude, goalRadius);
	}
	/**
	 * Sets the hidden goal for the player to run to.
	 * @param goalID the ID of the goal
	 * @param longitude the longitude of the goal
	 * @param latitude the latitude of the goal
	 * @param radius the radius of the geofence
	 */
	public void setGoal(String id, Double longitude, Double latitude, Float radius) {
		goalID = id;
		Location loc = new Location(Context.LOCATION_SERVICE);
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		goal = loc;
		goalRadius = radius;
		
		Log.d(TAG + "setGoal()", "Goal " + goalID + " was set to " + longitude + "/" + latitude + 
				"with radius of " + radius);
		
		// Create geofence for goal
		createGeofence();
	}
	/**
	 * Enables the periodic updates of the user's location according to the settings.
	 * Update intervals etc. are configured in {@link LocationUtils}.
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
	 * Returns the current state of the visualization of the geofence.
	 * If true the geofence is visualized.
	 * @return the state of the visualization
	 */
	public boolean isShowGeofence() {
		return showGeofence;
	}
	/**
	 * Enabled or disables the visualization of the geofence.
	 * @param showGeofence true if geofence should be visualized
	 */
	public void setShowGeofence(boolean showGeofence) {
		this.showGeofence = showGeofence;
		if (!currentGeofences.isEmpty() && showGeofence == true) {
			addMarkerForFence();
		}
	}
	
	/*
	 * ############## END GETTER AND SETTER ###########################################################################
	 */
}
