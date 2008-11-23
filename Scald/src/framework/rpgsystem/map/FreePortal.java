package framework.rpgsystem.map;

import com.jme.math.Vector3f;

/**
 * Class responsible for holding the portals that are used
 * for the pattern of walking called Free
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FreePortal {

	/**
	 * Responsible for holding the identifier to a map
	 */
	private String name;
	
	/**
	 * Responsible for holding the initial position of
	 * where the place of changing a map begins
	 */
	private Vector3f initialPosition;
	
	/**
	 * Responsible for holding the final position of
	 * where the place of changing a map begins
	 */
	private Vector3f finalPosition;
	
	/**
	 * Constructor of the class
	 */
	public FreePortal(){
		
	}
	
	public void createPortal(String name, Vector3f initialP, Vector3f finalP){
		//receives the map
		this.name = name;
		//receives the initial of the portal
		this.initialPosition = initialP;
		//receives the final of the portal
		this.finalPosition = finalP;		
	}

	public String getName() {
		return name;
	}

	public Vector3f getFinalPosition() {
		return finalPosition;
	}

	public Vector3f getInitialPosition() {
		return initialPosition;
	}
	
	
		
}
