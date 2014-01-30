package de.hsb.kss.mc_schnitzeljagd.location;

import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import de.hsb.kss.mc_schnitzeljagd.R;
import de.hsb.kss.mc_schnitzeljagd.ui.MainActivity;

public class ReceiveTransitionsIntentService extends IntentService {
	private String TAG = "ReceiveTransitionsIntentService";
	
	/**
	 * Sets an identifier for this class' background thread.
	 */
	public ReceiveTransitionsIntentService() {
		super("ReceiveTransitionsIntentService");
		Log.d(TAG + "ReceiveTransitionsIntentService()", "Transition Receiver Service was started");
	}

	/**
	 * Handles incoming intents.
	 *
	 * @param intent
	 *        The Intent sent by Location Services. This Intent is provided to
	 *        Location Services (inside a PendingIntent) when you call
	 *        addGeofences()
	 */
	@Override
	protected void onHandleIntent(Intent intent) {

		// Create a local broadcast Intent
        Intent broadcastIntent = new Intent();

        // Give it the category for all intents sent by the Intent Service
        broadcastIntent.addCategory(LocationUtils.CATEGORY_LOCATION_SERVICES);

        // First check for errors
        if (LocationClient.hasError(intent)) {

            // Get the error code
            int errorCode = LocationClient.getErrorCode(intent);

            // Get the error message
            String errorMessage = LocationServiceErrorMessages.getErrorString(this, errorCode);

            // Log the error
            Log.e(TAG + ".onHandleIntent()", getString(R.string.geofence_transition_error_detail, errorMessage));

            // Set the action and error message for the broadcast intent
            broadcastIntent.setAction(LocationUtils.ACTION_GEOFENCE_ERROR)
                           .putExtra(LocationUtils.EXTRA_GEOFENCE_STATUS, errorMessage);

            // Broadcast the error *locally* to other components in this app
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

        // If there's no error, get the transition type and create a notification
        } else {

            // Get the type of transition (entry or exit)
            int transition = LocationClient.getGeofenceTransition(intent);

            // Test that a valid transition was reported
            if (
                    (transition == Geofence.GEOFENCE_TRANSITION_ENTER)
                    ||(transition == Geofence.GEOFENCE_TRANSITION_EXIT)) {

                // Post a notification
                List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
                String[] geofenceIds = new String[geofences.size()];
                for (int index = 0; index < geofences.size() ; index++) {
                    geofenceIds[index] = geofences.get(index).getRequestId();
                }
                String ids = TextUtils.join(LocationUtils.GEOFENCE_ID_DELIMITER, geofenceIds);
                String transitionType = getTransitionString(transition);

                sendNotification(transitionType, ids);
                sendBroadcast(transitionType, ids);

                // Log the transition type and a message
                Log.d(TAG + ".onHandleIntent()", getString(R.string.geofence_transition_notification_title, 
                		transitionType, ids));
                Log.d(TAG + ".onHandleIntent()", getString(R.string.geofence_transition_notification_text));

            // An invalid transition was reported
            } else {
                // Always log as an error
                Log.e(TAG + ".onHandleIntent()", getString(R.string.geofence_transition_invalid_type, transition));
            }
        }
    }

	/**
	 * Posts a broadcast to the app when a transisition is detected.
	 * @param transitionType The type of transition that occurred.
	 * @param ids the geofence-IDs.
	 */
	private void sendBroadcast(String transitionType, String ids) {
		/*
	     * Creates a new Intent containing a Uri object
	     * BROADCAST_ACTION is a custom Intent action
	     */
	    Intent localIntent =
	            new Intent(LocationUtils.ACTION_GEOFENCE_TRANSITION)
	            // Puts the status into the Intent
	            .putExtra(LocationUtils.EXTRA_GEOFENCE_STATUS, ids);
	    // Broadcasts the Intent to receivers in this app.
	    Log.w("Tag BROADCAST", "BEFORE BROADCAST");
	    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
	    Log.w("Tag BROADCAST", "AFTER BROADCAST");

	}
	
    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the main Activity.
     * @param transitionType The type of transition that occurred.
	 * @param ids the geofence-IDs.
     */
    private void sendNotification(String transitionType, String ids) {
    	Log.w("TAG MS","sendNotification Start");
        // Create an explicit content Intent that starts the main Activity
        Intent notificationIntent =
                new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the main Activity to the task stack as the parent
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Set the notification contents
        builder.setSmallIcon(R.drawable.ic_launcher)
        	   .setContentTitle(getString(R.string.geofence_transition_notification_title,
                               transitionType, ids))
               .setContentText(getString(R.string.geofence_transition_notification_text))
               .setContentIntent(notificationPendingIntent);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     * @param transitionType A transition type constant defined in Geofence
     * @return A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {

            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);

            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);

            default:
                return getString(R.string.geofence_transition_unknown);
        }
    }
}


