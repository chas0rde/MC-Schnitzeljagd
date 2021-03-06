package de.hsb.kss.mc_schnitzeljagd.location;

import android.support.v4.app.FragmentActivity;
import android.app.Dialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * This class defines constants used by location sample apps.
 */
public final class LocationUtils {

    /**
     *  Used to track what type of geofence removal request was made.
     */
    public enum REMOVE_TYPE {INTENT, LIST}

    /**
     *  Used to track what type of request is in process.
     */
    public enum REQUEST_TYPE {ADD, REMOVE}

    /**
     * A log tag for the application.
     */
    public static final String TAG = "LocationUtils";

    // Intent actions
    public static final String ACTION_CONNECTION_ERROR = 
            "de.hsb.kss.mc_schnitzeljagd.location.ACTION_CONNECTION_ERROR";

    public static final String ACTION_CONNECTION_SUCCESS =
            "de.hsb.kss.mc_schnitzeljagd.location.ACTION_CONNECTION_SUCCESS";

    public static final String ACTION_GEOFENCES_ADDED =
            "de.hsb.kss.mc_schnitzeljagd.location.ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED =
            "de.hsb.kss.mc_schnitzeljagd.location.ACTION_GEOFENCES_DELETED";

    public static final String ACTION_GEOFENCE_ERROR =
            "de.hsb.kss.mc_schnitzeljagd.location.ACTION_GEOFENCES_ERROR";

    public static final String ACTION_GEOFENCE_TRANSITION =
            "de.hsb.kss.mc_schnitzeljagd.location.ACTION_GEOFENCE_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
                    "de.hsb.kss.mc_schnitzeljagd.location.ACTION_GEOFENCE_TRANSITION_ERROR";

    // The Intent category used by all Location Services sample apps
    public static final String CATEGORY_LOCATION_SERVICES =
                    "de.hsb.kss.mc_schnitzeljagd.location.CATEGORY_LOCATION_SERVICES";

    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE =
                    "com.example.android.EXTRA_CONNECTION_CODE";

    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "de.hsb.kss.mc_schnitzeljagd.location.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "de.hsb.kss.mc_schnitzeljagd.location.EXTRA_CONNECTION_ERROR_MESSAGE";

    public static final String EXTRA_GEOFENCE_STATUS =
            "de.hsb.kss.mc_schnitzeljagd.location.EXTRA_GEOFENCE_STATUS";

    /**
     * Keys for flattened geofences stored in SharedPreferences
     */
    public static final String KEY_LATITUDE = "de.hsb.kss.mc_schnitzeljagd.location.KEY_LATITUDE";

    public static final String KEY_LONGITUDE = "de.hsb.kss.mc_schnitzeljagd.location.KEY_LONGITUDE";

    public static final String KEY_RADIUS = "de.hsb.kss.mc_schnitzeljagd.location.KEY_RADIUS";

    public static final String KEY_EXPIRATION_DURATION =
            "de.hsb.kss.mc_schnitzeljagd.location.KEY_EXPIRATION_DURATION";

    public static final String KEY_TRANSITION_TYPE =
            "de.hsb.kss.mc_schnitzeljagd.location.KEY_TRANSITION_TYPE";

    /**
     *  The prefix for flattened geofence keys.
     */
    public static final String KEY_PREFIX =
            "de.hsb.kss.mc_schnitzeljagd.location.KEY";

    /**
     *  Invalid values, used to test geofence storage when retrieving geofences
     */
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

    /**
     * Constants used in verifying the correctness of input values
     */
    public static final double MAX_LATITUDE = 90.d;
    public static final double MIN_LATITUDE = -90.d;
    public static final double MAX_LONGITUDE = 180.d;
    public static final double MIN_LONGITUDE = -180.d;
    public static final float MIN_RADIUS = 1f;
    
    /**
     * Number of seconds per hour.
     */
    private static final long SECONDS_PER_HOUR = 60;
    /**
     * Geofence will expire after this number of hours.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	/**
	 *  Milliseconds per second
	 */
    public static final int MILLISECONDS_PER_SECOND = 1000;
    /**
     * Calculated expiration time for the geofence (see {@link GEOFENCE_EXIRATION_IN_HOURS}).
     */
   	public static final long GEOFENCE_EXPIRATION_TIME = GEOFENCE_EXPIRATION_IN_HOURS * SECONDS_PER_HOUR *
           MILLISECONDS_PER_SECOND;
    /**
     *  Update frequency in seconds
     */
   	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    /**
     *  Update frequency in milliseconds
     */
   	public static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    /**
     *  The fastest update frequency, in seconds
     */
   	public static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    /**
     *  A fast frequency ceiling in milliseconds
     */
   	public static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

    /**
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    /**
     *  A string of length 0, used to clear out input fields.
     */
    public static final String EMPTY_STRING = new String();

    /**
     * The delimiter for geofence IDs.
     */
    public static final CharSequence GEOFENCE_ID_DELIMITER = ",";
    
    /**
     * The default radius for a geofence.
     */
    public static final float DEFAULT_GEOFENCE_RADIUS = 30;

    /**
     * Check availability of Google Play Services.
     * @return state of the Google Play Services
     */
    public static boolean servicesConnected(FragmentActivity activity) {
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
                Log.e(TAG + ".servicesConnected()", "Error: GooglePlayServiceUtil: " + e);
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
}
