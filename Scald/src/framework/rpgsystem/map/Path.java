package framework.rpgsystem.map;

import java.util.ArrayList;

/**
 * Represents the way of moving in a map when
 * the way is already determined
 *  
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Path {
  
	/**
	 * Responsible for having the places that can
	 * be accessed, an array list because
	 * the number of points isn´t determined
	 * 
	 */
	private ArrayList<Point> points;
	 
	/**
	 * Responsible for holding the maps that
	 * are accessed
	 */
	private PathPortal portals;
	
	/**
	 * Constructor of the class, initializes its
	 * objects
	 */
	public Path(){
		points = new ArrayList<Point>();
		portals = new PathPortal();
	}
	
	/**
	 * Method used to get the array of points in the map
	 * @return get the array of points, an ArrayList
	 */
	public ArrayList<Point> getPoint() {
		return points;
	}
	
	/**
	 * Method used to get a place that is being looked for
	 * @param point receives the name of the place, a String
	 * @return returns the place if found or null if not found
	 */
	public Point getPlace(String point){
		
		//must look for the point that needs
		for(int i=0; i < points.size(); i++){
			//test if is the only that we want
			if(points.get(i).getName().equals(point)){
				//found it, return it and finish
				return points.get(i);
			}
		}
		//not found
		return null;
	}
	
	/**
	 * Method used to clear the array of points
	 */
	public void cleanArrayList(){
		points.clear();
	}
	
	/**
	 * Method used to create a new Point
	 * 
	 * @param name the identifier of the point, a String
	 * @param x the position in the axis x, a float
	 * @param y the position in the axis y, a float
	 * @param z the position in the axis z, a float
	 */
	public void createPoint(String name, int x, int y,String up,String down,String left,String right){
		//create the new point
		Point point = new Point(name,x,y,up,down,left,right);
		//put the new point in the array
		points.add(point);
	}
	
	/**
	 * Method used to create a portal
	 * 
	 * @param point receives the identifier of the point, a String
	 * @param map receives the map that is being represented,a String
	 */
	public void createPortal(String point, String map){
		portals.createPortal(point, map);
	}
	
	/**
	 * Method used to remove all the portals that were loaded
	 */
	public void clearPortals(){
		portals.clearPortals();
	}
	
}
 
