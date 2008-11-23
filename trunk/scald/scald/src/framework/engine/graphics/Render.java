package framework.engine.graphics;

import org.jdom.Attribute;
import org.jdom.Element;

import com.jme.light.DirectionalLight;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;

import framework.FactoryManager;

/**
 * Responsible for drawing all the objects that are present in the scene, taking
 * in account what the camera is showing and how the objects are
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class Render {

	/**
	 * Object responsible for the display that is created and used to paint
	 */
	DisplaySystem display;

	/**
	 * Object responsible for moving the camera used in the game
	 */
	Camera camera;

	protected LightState lightState;

	/**
	 * Constructor of the class
	 */
	public Render() {
		// initialize the display
		display = null;
		// initialize the camera
		camera = null;
	}

	/**
	 * 
	 * @param
	 */
	/**
	 * Method used to create the display used to draw and return it
	 * 
	 * @param properties
	 *            receives an object of the class Properties used to create the
	 *            display
	 * @return an object of the class DisplaySystem
	 */
	public void createDisplay(Element properties) {
				
		//use to manipulate the attributes
		Attribute renderer,width,heigth,depth,frequency,fullscreen,title;
		
		//get the value of the attributes
		renderer = properties.getChild("Renderer").getAttribute("value");
		width = properties.getChild("Width").getAttribute("value");
		heigth = properties.getChild("Heigth").getAttribute("value");
		depth = properties.getChild("Depth").getAttribute("value");
		frequency = properties.getChild("Frequency").getAttribute("value");
		fullscreen = properties.getChild("Fullscreen").getAttribute("value");
		title = properties.getChild("Title").getAttribute("value");
		
		try {
			// gets the renderer used by the application
			display = DisplaySystem.getDisplaySystem(renderer.getValue());
			
			// creates the window
			display.createWindow(Integer.parseInt(width.getValue()), Integer
					.parseInt(heigth.getValue()), Integer.parseInt(depth
					.getValue()), Integer.parseInt(frequency.getValue()),
					Boolean.parseBoolean(fullscreen.getValue()));

			// set the title of the display			
			display.setTitle(title.getValue());

			// create a camera specific to the display system
			camera = display.getRenderer().createCamera(display.getWidth(),
					display.getHeight());

		} catch (JmeException e) {
			/**
			 * If the displaysystem can't be initialized correctly, exit
			 * instantly.
			 */
			// logger.log(Level.SEVERE, "Could not create displaySystem", e);
			System.exit(1);
		}

		// choose a color to the background of the display
		display.getRenderer().setBackgroundColor(ColorRGBA.black.clone());

		//ask to configure the Camera
		//configureCamera();
		
		// assing the camera to this render
		display.getRenderer().setCamera(camera);

	}
	
	public void configureCameraFrustum(float angle,float aspect, float near, float far){
		//set how the camera sees
		camera.setFrustumPerspective(angle, aspect, near, far);
	}
	
	public void configureCameraPosition(Vector3f location,
			Quaternion orientation) {
		// set the position, using a vector, and the orientation, using a
		// quaternion
		camera.setFrame(location, orientation);
	}
	
	public void configureCameraPosition(Vector3f location,Vector3f left,Vector3f up,Vector3f direction){		
		//set the position of the camera, with orientation
		camera.setFrame(location, left, up, direction);		
	}
	
	public void refreshCamera(){
		//its used to indicate that the camera changed
		camera.update();
	}
	
	public void configureCamera(){
		// set how the camera sees
		camera.setFrustumPerspective(45.0f, (float) display.getWidth()
				/ (float) display.getHeight(), 1, 1000);
		
		// create the vectors used for the position and the orientation of the
		// camera
		Vector3f loc = new Vector3f(0.0f, 0.0f, 25.0f);
		Vector3f left = new Vector3f(-1.0f, 0.0f, 0.0f);
		Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
		Vector3f dir = new Vector3f(0.0f, 0f, -1.0f);

		// move the camera to a position and orientation
		camera.setFrame(loc, left, up, dir);
		
		// its used to indicate that the camera changed
		camera.update();		
	}

	/**
	 * Method used to refresh the display
	 */
	public void refreshDisplay(Node rootNode) {

		// clear statistics
		display.getRenderer().clearStatistics();

		// clear the previous rendered information
		display.getRenderer().clearBuffers();

		// draw the objects in the display if the rootNode isn´t null, and
		// already
		// draw all of it´s child
		if (rootNode != null)
			display.getRenderer().draw(rootNode);

	}

	public void createLightState() {
		/** Set up a basic, default light. */
		DirectionalLight light = new DirectionalLight();
		light.setDiffuse(ColorRGBA.green);
		//light.setDiffuse(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
		light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
		//light.setSpecular(new ColorRGBA(0.4f, 0.4f, 0.4f, 1.0f));
		light.setDirection(new Vector3f(1, -1, 0));
		light.setEnabled(true);

		/** Attach the light to a lightState and the lightState to rootNode. */
		// LightState lightState = display.getRenderer().createLightState();
		// lightState.setEnabled(true);
		// lightState.attach(light);
		/** Set up a basic, default light. */
		// PointLight light = new PointLight();
		// light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		// light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
		// light.setLocation(new Vector3f(100, 100, 100));
		// light.setEnabled(true);
		/** Attach the light to a lightState and the lightState to rootNode. */
		lightState = display.getRenderer().createLightState();
		lightState.setGlobalAmbient(new ColorRGBA(.2f, .2f, .2f, 1f));
		lightState.setEnabled(true);
		lightState.attach(light);
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.setRenderState(lightState);
	}

	/**
	 * Method used to get the display ready to close or to reinitalize
	 */
	public void reset() {
		if (display != null)
			display.reset();
	}

	/**
	 * Method used to destroy the display
	 */
	public void close() {
		if (display != null)
			display.close();
	}

	/**
	 * Method used to swap the buffers
	 */
	public void swapBuffers() {
		display.getRenderer().displayBackBuffer();
	}

	/**
	 * Method used to determine if the display system has been created
	 * successfully before use
	 * 
	 * @throws JmeException
	 *             if the display system was not successfully created
	 */
	public void assertDisplayCreated() throws JmeException {

		if (display == null) {
			throw new JmeException("Null - Window must be created during"
					+ " initialization.");
		}

		if (!display.isCreated()) {
			throw new JmeException(
					"Not initalized - Window must be created during"
							+ " initialization.");
		}
	}

	/**
	 * Method used to return if the window is closing
	 * 
	 * @return an object of the class boolean, if closing true if not false
	 */
	public boolean isClosing() {
		return display.isClosing();
	}

	public void rotateUpCamera(){
		
		Vector3f cam = camera.getDirection();
		cam.y = cam.y + 0.05f;
		camera.setDirection(cam);		
	}
	
	public void rotateDownCamera(){
		Vector3f cam = camera.getDirection();
		cam.y = cam.y - 0.03f;
		camera.setDirection(cam);		
	}
	
	public void playerDirection(){
		//camera.setDirection(FactoryManager.getFactoryManager()
			//	.getResourceManager().getObjectManager().getCharacter().getPhysicPosition());
	}
	

	public void moveUpCamera(float value,float maximunY) {
		Vector3f cam = camera.getLocation();
		if((cam.y + value) < maximunY){
			cam.y = cam.y + value;
			camera.setLocation(cam);
		}
		// camera.setDirection(FactoryManager.getFactoryManager()
		// .getResourceManager().getObjectManager().getCharacter()
		// .getModelPosition());
	}

	public void moveDownCamera(float value,float minimunY) {
		Vector3f cam = camera.getLocation();
		if((cam.y - value) > minimunY){
			cam.y = cam.y - value;
			camera.setLocation(cam);
		}
		// camera.setDirection(FactoryManager.getFactoryManager()
		// .getResourceManager().getObjectManager().getCharacter()
		// .getModelPosition());

	}

	public void moveLeftCamera(float value,float minimunX) {		
		Vector3f cam = camera.getLocation();		
		if((cam.x - value) > minimunX){
			cam.x = cam.x - value;
			camera.setLocation(cam);
		}
		// camera.setDirection(FactoryManager.getFactoryManager()
		// .getResourceManager().getObjectManager().getCharacter()
		// .getModelPosition());

	}

	public void moveRightCamera(float value, float maximunX) {		
		Vector3f cam = camera.getLocation();		
		if((cam.x + value) < maximunX){
			cam.x = cam.x + value;
			camera.setLocation(cam);			
		}
		// camera.setDirection(FactoryManager.getFactoryManager()
		// .getResourceManager().getObjectManager().getCharacter()
		// .getModelPosition());

	}

	public void moveNearCamera(float value, float minimunZ) {
		Vector3f cam = camera.getLocation();
		if((cam.z - value) > minimunZ){
			cam.z = cam.z - value;
			camera.setLocation(cam);
		}
		// camera.setDirection(FactoryManager.getFactoryManager()
		// .getResourceManager().getObjectManager().getCharacter()
		// .getModelPosition());

	}

	public void moveFarCamera(float value, float maximunZ) {
		Vector3f cam = camera.getLocation();
		if((cam.z + value) < maximunZ){
			cam.z = cam.z + value;
			camera.setLocation(cam);
		}
		// camera.setDirection(FactoryManager.getFactoryManager()
		// .getResourceManager().getObjectManager().getCharacter()
		// .getModelPosition());

	}

	public DisplaySystem getDisplay() {
		return display;
	}

	public Camera getCamera() {
		return camera;
	}
			
}