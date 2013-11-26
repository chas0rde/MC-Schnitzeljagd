package de.hsb.kss.mc_schnitzeljagd.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Storage for geofence values, implemented in SharedPreferences.
 */
public class SimpleGeofenceStore {

    // The SharedPreferences object in which geofences are stored
    private final SharedPreferences mPrefs;
    // The name of the SharedPreferences
    private static final String SHARED_PREFERENCES = "SharedPreferences";
    // Create the SharedPreferences storage with private access only
    public SimpleGeofenceStore(Context context) {
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }
    /**
     * Returns a stored geofence by its id, or returns null
     * if it's not found.
     *
     * @param id The ID of a stored geofence
     * @return A geofence defined by its center and radius. See
     */
    public SimpleGeofence getGeofence(String id) {
        /*
         * Get the latitude for the geofence identified by id, or
         * INVALID_FLOAT_VALUE if it doesn't exist
         */
        double lat = mPrefs.getFloat(getGeofenceFieldKey(id, LocationUtils.KEY_LATITUDE), 
        		LocationUtils.INVALID_FLOAT_VALUE);
        /*
         * Get the longitude for the geofence identified by id, or
         * INVALID_FLOAT_VALUE if it doesn't exist
         */
        double lng = mPrefs.getFloat(getGeofenceFieldKey(id, LocationUtils.KEY_LONGITUDE),
        		LocationUtils.INVALID_FLOAT_VALUE);
        /*
         * Get the radius for the geofence identified by id, or
         * INVALID_FLOAT_VALUE if it doesn't exist
         */
        float radius = mPrefs.getFloat(getGeofenceFieldKey(id, LocationUtils.KEY_RADIUS), 
        		LocationUtils.INVALID_FLOAT_VALUE);
        /*
         * Get the expiration duration for the geofence identified
         * by id, or INVALID_LONG_VALUE if it doesn't exist
         */
        long expirationDuration = mPrefs.getLong(getGeofenceFieldKey(id, LocationUtils.KEY_EXPIRATION_DURATION),
        		LocationUtils.INVALID_LONG_VALUE);
        /*
         * Get the transition type for the geofence identified by
         * id, or INVALID_INT_VALUE if it doesn't exist
         */
        int transitionType = mPrefs.getInt(getGeofenceFieldKey(id, LocationUtils.KEY_TRANSITION_TYPE), 
        		LocationUtils.INVALID_INT_VALUE);
        // If none of the values is incorrect, return the object
        if (
            lat != LocationUtils.INVALID_FLOAT_VALUE &&
            lng != LocationUtils.INVALID_FLOAT_VALUE &&
            radius != LocationUtils.INVALID_FLOAT_VALUE &&
            expirationDuration != LocationUtils.INVALID_LONG_VALUE &&
            transitionType != LocationUtils.INVALID_INT_VALUE) {

            // Return a true Geofence object
            return new SimpleGeofence(id, lat, lng, radius, expirationDuration, transitionType);
        // Otherwise, return null.
        } else {
            return null;
        }
    }
    /**
     * Save a geofence.
     * @param geofence The SimpleGeofence containing the
     * values you want to save in SharedPreferences
     */
    public void setGeofence(String id, SimpleGeofence geofence) {
        /*
         * Get a SharedPreferences editor instance. Among other
         * things, SharedPreferences ensures that updates are atomic
         * and non-concurrent
         */
        Editor editor = mPrefs.edit();
        // Write the Geofence values to SharedPreferences
        editor.putFloat(getGeofenceFieldKey(id, LocationUtils.KEY_LATITUDE), (float) geofence.getLatitude());
        editor.putFloat(getGeofenceFieldKey(id, LocationUtils.KEY_LONGITUDE), (float) geofence.getLongitude());
        editor.putFloat(getGeofenceFieldKey(id, LocationUtils.KEY_RADIUS), geofence.getRadius());
        editor.putLong(getGeofenceFieldKey(id, LocationUtils.KEY_EXPIRATION_DURATION), 
        		geofence.getExpirationDuration());
        editor.putInt(getGeofenceFieldKey(id, LocationUtils.KEY_TRANSITION_TYPE), geofence.getTransitionType());
        // Commit the changes
        editor.commit();
    }
    public void clearGeofence(String id) {
        /*
         * Remove a flattened geofence object from storage by
         * removing all of its keys
         */
        Editor editor = mPrefs.edit();
        editor.remove(getGeofenceFieldKey(id, LocationUtils.KEY_LATITUDE));
        editor.remove(getGeofenceFieldKey(id, LocationUtils.KEY_LONGITUDE));
        editor.remove(getGeofenceFieldKey(id, LocationUtils.KEY_RADIUS));
        editor.remove(getGeofenceFieldKey(id, LocationUtils.KEY_EXPIRATION_DURATION));
        editor.remove(getGeofenceFieldKey(id, LocationUtils.KEY_TRANSITION_TYPE));
        editor.commit();
    }
    /**
     * Given a Geofence object's ID and the name of a field
     * (for example, KEY_LATITUDE), return the key name of the
     * object's values in SharedPreferences.
     *
     * @param id The ID of a Geofence object
     * @param fieldName The field represented by the key
     * @return The full key name of a value in SharedPreferences
     */
    private String getGeofenceFieldKey(String id, String fieldName) {
        return LocationUtils.KEY_PREFIX + "_" + id + "_" + fieldName;
    }
}
