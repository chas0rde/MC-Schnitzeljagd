package de.hsb.kss.mc_schnitzeljagd;

import android.location.Location;

import com.google.android.gms.location.LocationClient;

/**
 * 
 * @author Ingo Pohlschneider
 * Represents a easy to use facade to location services supplied by Google Maps API and Google Play Services Location API
 * to abstract location based services from the app and supply methods to gather the required information.
 *
 */
public class LocationFacade {
	/**
	 * Singlton: Location client
	 */
	private static LocationClient locationClient;
	
	
	/**
	 * 
	 * @return the current location of the user
	 */
	public Location getLocation() {
		Location currentLocation = locationClient.getLastLocation();
		return currentLocation;
	}
	
	public void getMap(Location location) {
		
	}
}
