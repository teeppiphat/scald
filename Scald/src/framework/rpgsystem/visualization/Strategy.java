package framework.rpgsystem.visualization;

/**
 * This class is responsible for the strategy used in the
 * camera, combining diferents types of strategys one can create
 * diferent behaviors
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Strategy {
 
	/**
	 * Constructor of the class, its responsible for
	 * initializing the atributes that this class needs
	 */
	public Strategy(){
		
	}	
	
	/**
	 * Method responsible for creating the behavior that
	 * already has a configuration and return it
	 * @param name the name of the behavior, a String
	 * @return the behavior that was asked or null if the behavior
	 * wasn´t found
	 */
	public CameraBehavior createBehavior(String name){
		//ask the list of behaviors to create the one we want		
		return listStrategys(name);
	}
	
	public CameraBehavior createFreeX5(){
		CameraBehavior temp = new CameraBehavior();
		temp.setType("Free");
		temp.setAxis('X');
		temp.setAngle(5.0f);
		return temp;
	}
	
	public CameraBehavior createFreeY5(){
		CameraBehavior temp = new CameraBehavior();
		temp.setType("Free");
		temp.setAxis('Y');
		temp.setAngle(5.0f);
		return temp;
	}

	public CameraBehavior createFreeZ5(){
		CameraBehavior temp = new CameraBehavior();
		temp.setType("Free");
		temp.setAxis('Z');
		temp.setAngle(5.0f);
		return temp;
	}
	
	public CameraBehavior listStrategys(String name){
		
		if(name.equals("FreeX5"))
			return createFreeX5();
		
		if(name.equals("FreeY5"))
			return createFreeY5();
		
		if(name.equals("FreeZ5"))
			return createFreeZ5();
		
		//behavior not found
		return null;
	}
}
 
