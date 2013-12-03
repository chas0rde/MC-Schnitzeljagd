package de.hsb.kss.mc_schnitzeljagd.location;

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
    public static final String TAG = "geofence Detection";

    // Intent actions
    public static final String ACTION_CONNECTION_ERROR = 
            "com.example.android.geofence.ACTION_CONNECTION_ERROR";

    public static final String ACTION_CONNECTION_SUCCESS =
            "com.example.android.geofence.ACTION_CONNECTION_SUCCESS";

    public static final String ACTION_GEOFENCES_ADDED =
            "com.example.android.geofence.ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED =
            "com.example.android.geofence.ACTION_GEOFENCES_DELETED";

    public static final String ACTION_GEOFENCE_ERROR =
            "com.example.android.geofence.ACTION_GEOFENCES_ERROR";

    public static final String ACTION_GEOFENCE_TRANSITION =
            "com.example.android.geofence.ACTION_GEOFENCE_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
                    "com.example.android.geofence.ACTION_GEOFENCE_TRANSITION_ERROR";

    // The Intent category used by all Location Services sample apps
    public static final String CATEGORY_LOCATION_SERVICES =
                    "com.example.android.geofence.CATEGORY_LOCATION_SERVICES";

    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE =
                    "com.example.android.EXTRA_CONNECTION_CODE";

    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "com.example.android.geofence.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "com.example.android.geofence.EXTRA_CONNECTION_ERROR_MESSAGE";

    public static final String EXTRA_GEOFENCE_STATUS =
            "com.example.android.geofence.EXTRA_GEOFENCE_STATUS";

    /**
     * Keys for flattened geofences stored in SharedPreferences
     */
    public static final String KEY_LATITUDE = "com.example.android.geofence.KEY_LATITUDE";

    public static final String KEY_LONGITUDE = "com.example.android.geofence.KEY_LONGITUDE";

    public static final String KEY_RADIUS = "com.example.android.geofence.KEY_RADIUS";

    public static final String KEY_EXPIRATION_DURATION =
            "com.example.android.geofence.KEY_EXPIRATION_DURATION";

    public static final String KEY_TRANSITION_TYPE =
            "com.example.android.geofence.KEY_TRANSITION_TYPE";

    /**
     *  The prefix for flattened geofence keys.
     */
    public static final String KEY_PREFIX =
            "com.example.android.geofence.KEY";

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

}
