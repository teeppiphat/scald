package framework.rpgsystem.map;

import java.util.ArrayList;

import com.jme.math.Vector3f;

/**
 * Represents the Free way the character can move in the map,
 * this one lets the player makes the way he wants
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class Free {
 	 
	/**
	 * Responsible for the events, creating them
	 */
	//private EventGenerator[] eventGenerator;
	
	/**
	 * Responsible for holding the portals that exists
	 */
	private ArrayList<FreePortal> portals;
	
	/**
	 * Responsible for counting the axis that passed in the test
	 */
	private short count;
	
	/**
	 * Constructor of the class
	 */
	public Free(){
		portals = new ArrayList<FreePortal>();
	}
	
	/**
	 * Method responsible for creating a portal and keeping it
	 * 
	 * @param name the map the portal takes to, a String
	 * @param iX the initial position axis X, a float
	 * @param iY the initial position axis Y, a float
	 * @param iZ the initial position axis Z, a float
	 * @param fX the final position axis X, a float
	 * @param fY the final position axis Y, a float
	 * @param fZ the final position axis Z, a float
	 */
	public void createPortal(String name, float iX, float iY, float iZ, float fX, float fY, float fZ){
		//creates the object
		FreePortal portal = new FreePortal();
		//creates the portal
		portal.createPortal(name, new Vector3f(iX, iY, iZ), new Vector3f(fX, fY,
				fZ));
		//add the new portal to the array of portals
		portals.add(portal);		
	}
	
	/**
	 * Method used to know if the character passed a portal
	 * 
	 * @param x the position in the axis X,a float
	 * @param y the position in the axis Y,a float
	 * @param z the position in the axis Z,a float
	 * @return if passed return the name of the portal, else null , a String
	 */
	public String passedPortal(float x, float y, float z){
		
		//temporary object
		FreePortal temp;
		
		//test all the portals
		for(int i=0; i < portals.size();i++){
			
			//get the portal
			temp = portals.get(i);
					
			//test if passed a portal
			if (testPortal(temp,x,y,z)){
				//passed a portal
				return temp.getName();
			}			
		}
		
		//didn´t pass
		return null;
		
	}
	
	/**
	 * Method used to test a portal and a position, if the
	 * position is between the portal its passed
	 * 
	 * @param portal the portal that will be tested, a FreePortal
	 * @param x the position in the axis X,a float
	 * @param y the position in the axis Y,a float
	 * @param z the position in the axis Z,a float
	 * @return true if passed, false not passed
	 */
	public boolean testPortal(FreePortal portal, float x, float y, float z){
		
		//there´s nothing to worry about precision, and need to pass in two axis to load
		
		//initialize before the test
		count = 0;
		
		//test the axis X
		if((portal.getInitialPosition().x < x) && (x < portal.getFinalPosition().x)){
			count++;
		}

		//test the axis Y
		if((portal.getInitialPosition().y < y) && (y < portal.getFinalPosition().y)){
			count++;
		}
		
		//test the axis Z
		if((portal.getInitialPosition().z < z) && (z < portal.getFinalPosition().z)){
			count++;
		}
		
		//test how many axis passed, needs at least 2
		if(count >= 2)
			return true;
		else 
			return false;
				
	}
	
}
 
