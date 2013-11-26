package de.hsb.kss.mc_schnitzeljagd.location;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * 
 * @author Ingo Pohlschneider
 * Represents a easy to use facade to location services supplied by Google Maps API and Google Play Services Location API
 * to abstract location based services from the app and supply methods to gather the required information.
 *
 */
public interface LocationFacade {
	/**
	 * 
	 * @return the current location of the user as LatLng-object
	 */
	public LatLng getLocation();
	
	/**
	 * 
	 * @param location
	 * @return the longitude
	 */
	public double getLongitude(Location location);
	
	/**
	 * 
	 * @param location
	 * @return the ladtitude
	 */
	public double getLatitude(Location location);
	
	/**
	 * Returns a GoogleMap object zoomed into the current position of the user
	 * @return GoogleMap map
	 */
	public GoogleMap getMap();
}
