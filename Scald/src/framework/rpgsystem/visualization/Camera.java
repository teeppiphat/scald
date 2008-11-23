package framework.rpgsystem.visualization;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

import framework.FactoryManager;

/**
 * Class responsible for having a camera and having its behavior,
 * and having all the behaviors that are possible to have in the game,
 * only needs to be told when to use the different types of behaviors 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Camera {

	/**
	 * Atribute responsible for the different behaviors that the camera can have
	 */
	private CameraBehavior cameraBehavior[];

	/**
	 * Responsible for getting the behaviors that already are specified
	 */
	private Strategy strategy;
	
	/**
	 * Used to calculate the angle for rotation
	 */
	private float angle;

	/**
	 * The distance of the camera and its center
	 */
	private float distance;
	
	/**
	 * Objects responsible for not letting the camera out of an area, in the
	 * axis X
	 */
	private float minimunX,maximunX;
	
	/**
	 * Objects responsible for not letting the camera out of an area, in the
	 * axis Z
	 */
	private float minimunZ,maximunZ;
	
	/**
	 * Objects responsible for not letting the camera out of an area, in the
	 * axis Y
	 */
	private float minimunY,maximunY;
	
	/**
	 * Object used to keep the y of the character for test
	 */
	private float temp;
	
	/**
	 * Constructor of the class, it initialize the atributes of the class
	 */
	public Camera() {

		cameraBehavior = new CameraBehavior[0];
		strategy = new Strategy();		
		angle = 0.0f;
		distance = 0.0f;
		minimunX  = minimunZ = minimunY = 0.0f;
		maximunX = maximunZ = maximunY = 0.0f;
	}

	/**
	 * Method responsible for creating the behavior used for the camera
	 * 
	 * @param camera
	 *            receives an Element that will be used to get the behavior
	 */
	public void createCameraBehavior(Element camera) {

		//load the parameters of the camera
		loadCameraParameters(camera.getChild("Parameters"));

		//first ask if it uses Strategy
		if (Boolean.parseBoolean(camera.getChildText("UsesStrategy"))) {
			//if uses ask for the camera behavior to be created
			//by asking for the Strategy

			//get the children
			List list = camera.getChild("Strategys").getChildren();
			//create an iterator
			Iterator i = list.iterator();
			//temp object
			Element temp;
			//to indicate the position of the array
			int pos = 0;
			//allocate the array
			allocateBehaviors(list.size());

			//loop to get all the Strategys
			while (i.hasNext()) {

				//get the element
				temp = (Element) i.next();

				//get the Strategy used
				cameraBehavior[pos] = strategy.createBehavior(temp.getText());

				//increment the position
				pos++;
			}

		} else {
			//if the behavior is customized
			//pass it to be created

			//get the children
			List list = camera.getChild("Customs").getChildren();
			//create an iterator
			Iterator i = list.iterator();
			//temp object
			Element temp;
			//the position of the array
			int pos = 0;
			//allocate the array
			allocateBehaviors(list.size());
			
			//loop to get all the Strategys
			while (i.hasNext()) {

				//get the element
				temp = (Element) i.next();

				//initialize the position
				cameraBehavior[pos] = new CameraBehavior();
				
				//get the Strategy used
				cameraBehavior[pos].createCustomizedBehavior(temp);

				//increment the position
				pos++;
			}
		}
		
		//if the behavior is different than Fixed, than must set the 
		//direction of the camera to the position of the character
		
	}

	/**
	 * Method used to load the parameters that are necessary for the camera
	 * 
	 * @param camera
	 *            an object of the class Element so that can ask for the
	 *            parameters
	 */
	private void loadCameraParameters(Element camera) {

		//ask to load the Frustrum parameters
		loadCameraFrustum(camera.getChild("Frustum"));

		//ask to load the position used for the camera
		loadCameraPosition(camera.getChild("Position"));
		
		//ask to load the area the camera can move
		loadCameraArea(camera.getChild("Area"));

		//ask to update the camera
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.refreshCamera();
	}
	
	/**
	 * Method used to load the area that the camera must remain,
	 * when walking, ignoring rotation
	 * 
	 * @param area an object of the class Element so that can ask
	 * for the parameters
	 */
	public void loadCameraArea(Element area){
		
		//if it can move then it has an area
		if(area != null){
			
			//get the minimun for each axis
			minimunX = Float.parseFloat(area.getChildText("MinimunX"));
			minimunY = Float.parseFloat(area.getChildText("MinimunY"));
			minimunZ = Float.parseFloat(area.getChildText("MinimunZ"));
			
			//get the maximun for each axis
			maximunX = Float.parseFloat(area.getChildText("MaximunX"));
			maximunY = Float.parseFloat(area.getChildText("MaximunY"));
			maximunZ = Float.parseFloat(area.getChildText("MaximunZ"));			
		}		
	}

	/**
	 * Method used to get the parameters for the position and direction for the
	 * camera
	 * 
	 * @param position
	 *            an object of the class Element so that can ask for the
	 *            parameters
	 */
	private void loadCameraPosition(Element position) {
		//create the temporary objects used for it
		Vector3f loc = new Vector3f(Float.parseFloat(position
				.getChildText("LocX")), Float.parseFloat(position
				.getChildText("LocY")), Float.parseFloat(position
				.getChildText("LocZ")));
		Vector3f left = new Vector3f(Float.parseFloat(position
				.getChildText("LeftX")), Float.parseFloat(position
				.getChildText("LeftY")), Float.parseFloat(position
				.getChildText("LeftZ")));
		Vector3f up = new Vector3f(Float.parseFloat(position
				.getChildText("UpX")), Float.parseFloat(position
				.getChildText("UpY")), Float.parseFloat(position
				.getChildText("UpZ")));
		Vector3f dir = new Vector3f(Float.parseFloat(position
				.getChildText("DirX")), Float.parseFloat(position
				.getChildText("DirY")), Float.parseFloat(position
				.getChildText("DirZ")));
			
		//configure the camera
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.configureCameraPosition(loc, left, up, dir);
				
	}

	/**
	 * Method used to get the parameters used for the Frustum of the camera
	 * 
	 * @param frustrum
	 *            an object of the class Element so that can ask for the
	 *            parameters
	 */
	private void loadCameraFrustum(Element frustrum) {
		//configure the frustrum used for the camera
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.configureCameraFrustum(
						Float.parseFloat(frustrum.getChildText("ViewAngle")),
						Float.parseFloat(frustrum.getChildText("AspectRatio")),
						Float.parseFloat(frustrum.getChildText("Near")),
						Float.parseFloat(frustrum.getChildText("Far")));
	}

	/**
	 * Method used to reallocate the array of behaviors, depending on the
	 * quantity of behaviors needed
	 * 
	 * @param size
	 *            the number of behaviors needed, an integer
	 */
	public void allocateBehaviors(int size) {
		//create the array with the behavior using the size of the list,
		//because its the quantity of behavior used
		cameraBehavior = new CameraBehavior[size];
	}

	//for all the directions
	//move if the character moved is this direction, and
	//its not at the limit of the map

	public void changeY(float test){
		
		//test if the character went up or down
		if(temp < test){			
			//if the character went down, goes down the same amount
			moveUp(test - temp);
		}else if (temp > test){			
			//if the character went up, goes up the same amount
			moveDown(temp - test);
		}
	}
	
	public void moveUp(float value) {
		//must change only when the character goes up
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.moveUpCamera(value, maximunY);
	}

	public void moveDown(float value) {
		//must change only when the character goes down
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.moveDownCamera(value, minimunY);
	}

	public void moveRight(float value) {
		
		//can move only if hasn´t passed the limit
		
		//move together with the character
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.moveRightCamera(value,maximunX);
	}

	public void moveLeft(float value) {
		
		//can move only if hasn´t passed the limit
		
		// move together with the character
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.moveLeftCamera(value,minimunX);
	}

	public void moveNear(float value) {
		
		//can move only if hasn´t passed the limit
		
		// move together with the character
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.moveNearCamera(value,minimunZ);		
	}

	public void moveFar(float value) {
		
		//can move only if hasn´t passed the limit
		
		// move together with the character
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.moveFarCamera(value,maximunZ);		
	}
	
	/**
	 * Method used to calculate the distance for the rotation
	 */
	public void calculatesDistance(){
		//calculates the distance of the camera and the character
		distance = FactoryManager.getFactoryManager().getGraphicsManager()
				.getRender().getCamera().getLocation().distance(
						FactoryManager.getFactoryManager().getResourceManager()
								.getObjectManager().getCharacter()
								.getPhysicPosition());		
	}
	
	public void canRotate(boolean direction, char axis){		
		//temporary objects		
		boolean canRotate = false;
		int i = 0;		
		//first find if can rotate, if has a behavior that let it rotate
		//in the determined axis
		while(canRotate == false && i < cameraBehavior.length){
			if(cameraBehavior[i].getAxis() == axis){
				canRotate = true;
			}else{
				i++;
			}
		}		
		//can rotate, needs to rotate according to the behavior
		if(canRotate){			
			//calls to rotate in one of the axis			
			//X
			if(cameraBehavior[i].getAxis() == 'X'){
				//calls the rotate method, needs the direction and the behavior
				rotateX(direction,i);				
			}			
			//Y
			if(cameraBehavior[i].getAxis() == 'Y'){
				//calls the rotate method, needs the direction and the behavior
				rotateY(direction,i);
			}			
			//Z
			if(cameraBehavior[i].getAxis() == 'Z'){
				// calls the rotate method, needs the direction and the behavior
				rotateZ(direction,i);
			}			
		}		
	}
	
	public void increaseAngle(boolean direction,int pos){
		//the Free type don´t need any control, only increase or decrease
		//the angle
		if(cameraBehavior[pos].getType().equals("Free")){
			//move according to the direction
			if (direction)
				angle = angle + cameraBehavior[pos].getAngle();
			else
				angle = angle - cameraBehavior[pos].getAngle();			
		}// if is Determined type
		else if(cameraBehavior[pos].getType().equals("Determined")){
			//move according to the direction
			if (direction){
				// also according to the specified rotation, clock wise or
				// anti-clock wise
				if(cameraBehavior[pos].isRotation()){
					// anti-clock wise
					if(angle < cameraBehavior[pos].getFinalAngle())
						angle = angle + cameraBehavior[pos].getAngle();
				}else{
					// clock wise
					if(angle > cameraBehavior[pos].getFinalAngle())
						angle = angle - cameraBehavior[pos].getAngle();
				}			
			}
			else{
				// also according to the specified rotation, clock wise or
				// anti-clock wise
				if(cameraBehavior[pos].isRotation()){
					// anti-clock wise
					if (angle > cameraBehavior[pos].getInitialAngle())
						angle = angle - cameraBehavior[pos].getAngle();
				}else{
					// clock wise
					if (angle < cameraBehavior[pos].getInitialAngle())
						angle = angle + cameraBehavior[pos].getAngle();
				}
			}
		}
	}

	public void rotateX(boolean direction,int pos) {
		//if is true than is positive
		//if is false is negative
		
		//center of the camera, the character
		Vector3f center = FactoryManager.getFactoryManager()
				.getResourceManager().getObjectManager().getCharacter()
				.getPhysicPosition();
		
		//get the position of the camera
		Vector3f cameraPos = FactoryManager.getFactoryManager()
				.getGraphicsManager().getRender().getCamera().getLocation();
		
		//calls the method that increase the angle
		increaseAngle(direction, pos);
			
		//set the location of the camera, need to use the position of the camera for 
		//the axis that don´t move		
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.getCamera().setLocation(
						new Vector3f(cameraPos.x, center.y + distance
								* FastMath.cos(angle), center.z + distance
								* FastMath.sin(angle)));
		//set the place to look at
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.getCamera().lookAt(center, Vector3f.UNIT_X);
	}

	public void rotateY(boolean direction,int pos) {
		//if is true than is positive
		//if is false is negative	
		
		//center of the camera, the character
		Vector3f center = FactoryManager.getFactoryManager()
				.getResourceManager().getObjectManager().getCharacter()
				.getPhysicPosition();
		
		//get the position of the camera		
		Vector3f cameraPos = FactoryManager.getFactoryManager()
				.getGraphicsManager().getRender().getCamera().getLocation();
		
		//according to the type of the behavior will rotate the camera
		
		//calls the method that increase the angle
		increaseAngle(direction, pos);
				
		//set the location of the camera, need to use the position of the camera for 
		//the axis that don´t move
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.getCamera().setLocation(
						new Vector3f(center.x + distance * FastMath.cos(angle),
								cameraPos.y, center.z + distance
										* FastMath.sin(angle)));
		//set the place to look at
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.getCamera().lookAt(center, Vector3f.UNIT_Y);
	}

	public void rotateZ(boolean direction,int pos) {
		//if is true than is positive
		//if is false is negative	
		
		//center of the camera, the character
		Vector3f center = FactoryManager.getFactoryManager()
				.getResourceManager().getObjectManager().getCharacter()
				.getPhysicPosition();
		
		//get the position of the camera
		Vector3f cameraPos = FactoryManager.getFactoryManager()
				.getGraphicsManager().getRender().getCamera().getLocation();
	
		//calls the method that increase the angle
		increaseAngle(direction, pos);
		
		//set the location of the camera, need to use the position of the camera for 
		//the axis that don´t move
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.getCamera().setLocation(
						new Vector3f(center.x + distance * FastMath.cos(angle),
								center.y + distance * FastMath.sin(angle),
								cameraPos.z));
		//set the place to look at
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.getCamera().lookAt(center, Vector3f.UNIT_Z);
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}
	
}
