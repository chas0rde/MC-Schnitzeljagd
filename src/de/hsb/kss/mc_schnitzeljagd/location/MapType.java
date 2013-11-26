package de.hsb.kss.mc_schnitzeljagd.location;

/**
 * Possible types of maps
 */
public enum MapType {
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