package framework.rpgsystem.visualization;

import org.jdom.Element;

/**
 * This class is responsible for the different behaviors
 * that can exist in a camera, it will have different behaviors
 * already implemented and it´s going to have the possibility
 * of implementing other types of behaviors
 * @author Diego Antônio Tronquini Costi
 *
 */
public class CameraBehavior {
 	
	/**
	 * responsible for the type of behavior used,
	 * it can be Fixed, Determined and Free
	 */
	private String type;
	
	/**
	 * Indicates the axis that can move
	 */
	private char axis;
	
	/**
	 * Indicates the initial angle of this behavior
	 */
	private int initialAngle;
	
	/**
	 * Indicates the final angle of this behavior
	 */
	private int finalAngle;
	
	/**
	 * Indicates if the rotation is anti-clock wise or
	 * clock wise, true for anti-clock wise and false
	 * for clock wise
	 */
	private boolean rotation;
	
	/**
	 * Indicates the quantity of division that have between the
	 * initial and final angle
	 */
	private int division;
	
	/**
	 * Indicates how much rotates at a time, depends of the number of
	 * divisions
	 */
	private float angle;
	
	/**
	 * Constructor of the class, it initialize the
	 * atributes that needs it
	 */
	public CameraBehavior(){
		
	}
	
	public void createCustomizedBehavior(Element behavior){
		//create according to the type of the behavior
		type = behavior.getChildText("Type");
		
		//ask what type it is, in a way the Fixed type
		//is always used, because it only moves and the others
		//are used to have rotation
		if(type.equals("Determined")){
			//get everything that is needed
			
			//get the axis
			axis = behavior.getChildText("Axis").charAt(0);
			
			//get the initial angle
			initialAngle = Integer.parseInt(behavior.getChildText("InitialAngle"));
			
			//get the final angle
			finalAngle = Integer.parseInt(behavior.getChildText("FinalAngle"));
			
			//get the rotation
			rotation = Boolean.parseBoolean(behavior.getChildText("Rotation"));
			
			//get the divisions
			division = Integer.parseInt(behavior.getChildText("Division"));
			
			//set the angle, still need to trunc the value
			angle = (finalAngle - initialAngle)/division;
			
			//for now
			if (angle < 0){
				angle = -angle;
			}
			
		}else if(type.equals("Free")){
			//just gets the axis
			axis = behavior.getChildText("Axis").charAt(0);
						
			//and then say how much move at a time, that will be
			//1 at a time
			angle = 0.01f;			
		}else{
			//if its Fixed only move the camera according to the movement
			//of the character
			
		}
	}

	public float getAngle() {
		return angle;
	}

	public char getAxis() {
		return axis;
	}

	public int getDivision() {
		return division;
	}

	public int getFinalAngle() {
		return finalAngle;
	}

	public int getInitialAngle() {
		return initialAngle;
	}

	public boolean isRotation() {
		return rotation;
	}

	public String getType() {
		return type;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setAxis(char axis) {
		this.axis = axis;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	public void setFinalAngle(int finalAngle) {
		this.finalAngle = finalAngle;
	}

	public void setInitialAngle(int initialAngle) {
		this.initialAngle = initialAngle;
	}

	public void setRotation(boolean rotation) {
		this.rotation = rotation;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
 
