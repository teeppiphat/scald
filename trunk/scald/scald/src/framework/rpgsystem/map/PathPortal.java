package framework.rpgsystem.map;

import java.util.HashMap;

/**
 * Class that holds all the portals of the path pattern
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class PathPortal {
	
	/**
	 * Responsible for holding the point and the map
	 * it refers to
	 */
	private HashMap<String,String> portals;

	/**
	 * Constructor of the class, it initializes its objects
	 */
	public PathPortal(){
		portals = new HashMap<String, String>();
	}
	
	/**
	 * Method used to create a portal
	 * 
	 * @param point the identifier of the point, a String
	 * @param map the name of the map, a String
	 */
	public void createPortal(String point, String map){
		//puts the portal in the hash
		portals.put(point, map);
	}

	/**
	 * Method used to get a map that is in the hash
	 * 
	 * @param point use the point to get the map, a String
	 * @return the name of the map
	 */
	public String getMap(String point){
		return portals.get(point);
	}
	
	/**
	 * Method used to remove the portals that were loaded
	 */
	public void clearPortals(){
		portals.clear();
	}
	
}
