package de.hsb.kss.mc_schnitzeljagd.location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

import de.hsb.kss.mc_schnitzeljagd.R;

/**
 * Class for connecting to Location Services and requesting geofences.
 * <b>
 * Note: Clients must ensure that Google Play services is available before requesting geofences.
 * </b> Use GooglePlayServicesUtil.isGooglePlayServicesAvailable() to check.
 *
 *
 * To use a GeofenceRequester, instantiate it and call AddGeofence(). Everything else is done
 * automatically.
 *
 */
public class GameGeofenceRequester implements OnAddGeofencesResultListener, ConnectionCallbacks,
										  OnConnectionFailedListener {
	private String TAG = "GeofenceRequester";

    /**
     *  Storage for a reference to the calling client.
     */
    private final Activity activity;
    /**
     *  Stores the PendingIntent used to send geofence transitions back to the app.
     */
    private PendingIntent geofenceRequestIntent;
    /**
     *  Stores the current list of geofences.
     */
    private ArrayList<Geofence> currentGeofences;
    /**
     *  Stores the current instantiation of the location client.
     */
    private LocationClient locationClient;

    /**
     * The constructor.
     * @param activityContext the Activity calling
     * @param locationClient the location client to use
     */
    public GameGeofenceRequester(Activity activityContext, LocationClient locationClient) {
        // Save the context
        activity = activityContext;

        // Initialize the globals to null
        geofenceRequestIntent = null;
        this.locationClient = locationClient;
        Log.d(TAG + ".GameGeofenceRequester()", "GeofenceRequester constructed.");
    }

    /**
     * Returns the current PendingIntent to the caller.
     *
     * @return The PendingIntent used to create the current set of geofences
     */
    public PendingIntent getRequestPendingIntent() {
        return createRequestPendingIntent();
    }

    /**
     * Start adding geofences. Save the geofences, then start adding them by requesting a connection.
     *
     * @param geofences A List of one or more geofences to add
     */
    public void addGeofences(List<Geofence> geofences) throws UnsupportedOperationException {
    	Log.d(TAG + ".addGeofences()", "Starting to add geofence");
    	
        /*
         * Save the geofences so that they can be sent to Location Services once the
         * connection is available.
         */
        currentGeofences = (ArrayList<Geofence>) geofences;

        Log.d(TAG + ".addGeofences()", "Continuing to add geofence");

        // Get a PendingIntent that Location Services issues when a geofence transition occurs
        geofenceRequestIntent = createRequestPendingIntent();

        // Send a request to add the current geofences
        locationClient.addGeofences(currentGeofences, geofenceRequestIntent, this);
        
        Log.d(TAG + ".addGeofences()", "geofence was added");
    }

    /**
     * Handle the result of adding the geofences.
     */
    @Override
    public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {

        // Create a broadcast Intent that notifies other components of success or failure
        Intent broadcastIntent = new Intent();

        // Temp storage for messages
        String msg;

        // If adding the geocodes was successful
        if (LocationStatusCodes.SUCCESS == statusCode) {
            // Create a message containing all the geofence IDs added.
            msg = activity.getString(R.string.add_geofences_result_success, Arrays.toString(geofenceRequestIds));

            // In debug mode, log the result
            Log.d(TAG + ".onAddGeofencesResult()", msg);

            // Create an Intent to broadcast to the app
            broadcastIntent.setAction(LocationUtils.ACTION_GEOFENCES_ADDED)
                           .addCategory(LocationUtils.CATEGORY_LOCATION_SERVICES)
                           .putExtra(LocationUtils.EXTRA_GEOFENCE_STATUS, msg);
        // If adding the geofences failed
        } else {

            /*
             * Create a message containing the error code and the list
             * of geofence IDs you tried to add
             */
            msg = activity.getString(R.string.add_geofences_result_failure, statusCode, 
            		Arrays.toString(geofenceRequestIds));

            // Log an error
            Log.e(TAG + ".onAddGeofencesResult()", msg);

            // Create an Intent to broadcast to the app
            broadcastIntent.setAction(LocationUtils.ACTION_GEOFENCE_ERROR)
                           .addCategory(LocationUtils.CATEGORY_LOCATION_SERVICES)
                           .putExtra(LocationUtils.EXTRA_GEOFENCE_STATUS, msg);
        }

        // Broadcast whichever result occurred
        LocalBroadcastManager.getInstance(activity).sendBroadcast(broadcastIntent);
    }

    /**
     * Get a PendingIntent to send with the request to add Geofences. 
     * Location Services issues the Intent inside this PendingIntent whenever a geofence transition occurs for 
     * the current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private PendingIntent createRequestPendingIntent() {

        // If the PendingIntent already exists
        if (null != geofenceRequestIntent) {

            // Return the existing intent
            return geofenceRequestIntent;

        // If no PendingIntent exists
        } else {

            // Create an Intent pointing to the IntentService
                // TODO correct service?
            Intent intent = new Intent(activity, ReceiveTransitionsIntentService.class);
            /*
             * Return a PendingIntent to start the IntentService.
             * Always create a PendingIntent sent to Location Services
             * with FLAG_UPDATE_CURRENT, so that sending the PendingIntent
             * again updates the original. Otherwise, Location Services
             * can't match the PendingIntent to requests made with it.
             */
            return PendingIntent.getService(
                    activity,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    /**
     * Implementation of OnConnectionFailedListener.onConnectionFailed.
     * If a connection or disconnection request fails, report the error connectionResult is passed in 
     * from Location Services
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
                connectionResult.startResolutionForResult(activity,
                		LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }

        /*
         * If no resolution is available, put the error code in
         * an error Intent and broadcast it back to the main Activity.
         * The Activity then displays an error dialog.
         * is out of date.
         */
        } else {

            Intent errorBroadcastIntent = new Intent(LocationUtils.ACTION_CONNECTION_ERROR);
            errorBroadcastIntent.addCategory(LocationUtils.CATEGORY_LOCATION_SERVICES)
                                .putExtra(LocationUtils.EXTRA_CONNECTION_ERROR_CODE,
                                        connectionResult.getErrorCode());
            LocalBroadcastManager.getInstance(activity).sendBroadcast(errorBroadcastIntent);
        }
    }

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
