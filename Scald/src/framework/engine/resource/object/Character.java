package framework.engine.resource.object;

import org.jdom.Element;

/**
 * Represents the characters that the player is controlling, having the models
 * and the actions that are possible for them in the world
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class Character extends Agent {

	/**
	 * Object that determines if the character is running
	 */
	boolean run = false;

	/**
	 * Object used to set the value for walking
	 */
	final float walk = 0.05f;

	/**
	 * Object used to set the value for running
	 */
	final float running = 0.1f;

	/**
	 * These objects are used for the model
	 */
	String modelFile, modelPath, textureFile, texturePath, animationFile,
			animationPath;

	/**
	 * Constructor of the class
	 */
	public Character() {

	}

	/**
	 * Method used to create the Character in a scene, receiving the parameters
	 * necessary
	 * 
	 * @param name
	 *            The name for the model, a String object
	 * @param scale
	 *            The scale of the model, a float object
	 * @param posX
	 *            The position in the axis X, a float object
	 * @param posY
	 *            The position in the axis Y, a float object
	 * @param posZ
	 *            The position in the axis Z, a float object
	 * @param angleX
	 *            The angle to rotate the model in X, a float object
	 * @param angleY
	 *            The angle to rotate the model in Y, a float object
	 * @param angleZ
	 *            The angle to rotate the model in Z, a float object
	 * @param material
	 *            The type of material used for the model, a String object
	 * @param density
	 *            The density used for the model, a float object
	 * @param slide
	 *            The slide used for the model, a float object
	 * @param bounce
	 *            The bounce used for the model, a float object
	 */
	public void createCharacter(String name, String type, float scale, float posX,
			float posY, float posZ, float angleX, float angleY, float angleZ,
			String material, float density, float slide, float bounce, String event, String eventPath) {

		//set the name
		setName(name);
		//set the event
		setEvent(event);
		//set the event path
		setEventPath(eventPath);
		// loads the model in the .jme format
		loadModel(modelPath, modelFile);
		// loads the texture of the model
		loadTexture(texturePath, textureFile);
		// loads the animation of the model
		loadAnimation(animationPath, animationFile);
		// put the bounding box
		boundingBox();
		// puts a name in the node
		giveNameModel(type);
		// sets the scale of the model
		scaleModel(scale);
		// rotate the model
		rotateModel(angleX, angleY, angleZ);
		// sets the position of the model
		translateModel(posX, posY, posZ);
		// puts the physic
		physicNode(material, density, slide, bounce);
		// put the model and the physic in the same coordinates
		physic.setLocalTranslation(this.model.getLocalTranslation().clone());
		
		// update the models changes
		// updateGeometricState();
		// puts the node in the rootNode to be draw
		// addModelRootNode();

	}

	public void loadModelProperties(Element load) {

		if (load.getChild("Model") != null) {
			// get the elements of the file to load the model
			modelFile = load.getChild("Model").getChildText("File");
			modelPath = load.getChild("Model").getChildText("Path");
		}else{
			modelFile = "none";
			modelPath = "none";
		}

		if (load.getChild("Texture") != null) {
			// get the elements of the file to load the texture
			textureFile = load.getChild("Texture").getChildText("File");
			texturePath = load.getChild("Texture").getChildText("Path");
		}else{
			textureFile = "none";
			texturePath = "none";
		}

		if (load.getChild("Animation") != null) {
			// get the elements of the file to load the animation
			animationFile = load.getChild("Animation").getChildText("File");
			animationPath = load.getChild("Animation").getChildText("Path");
		}else{
			animationFile = "none";
			animationPath = "none";
		}
	}

	/**
	 * Used to change the flag
	 */
	public void run() {
		run = !run;
	}

	/**
	 * Method used to know if can move or not
	 * 
	 * @return true if can move, false if can´t
	 */
	public boolean isCanMove() {
		return canMove;
	}

	/**
	 * Method used to know if is running or walking
	 * 
	 * @return true if running, false if walking
	 */
	public boolean isRun() {
		return run;
	}

	/**
	 * Method used to get the value for running
	 * 
	 * @return a float object
	 */
	public float getRunning() {
		return running;
	}

	/**
	 * Method used to get the value for walking
	 * 
	 * @return a float object
	 */
	public float getWalk() {
		return walk;
	}

	/**
	 * Used to move up the character
	 */
	public void moveUp() {

		//test rotation
		rotateModel(0, 1, 0);
		// test if its running or walking
		if (run) {
			// call the method used to move by giving the speed
			move(1, running);
			//tells to animate
			basicAnimation("Run");
			//animate(basicAnimation.get("Run"));
		} else {
			// call the method used to move by giving the speed
			move(1, walk);
			//tells to animate
			basicAnimation("Walk");
			//animate(basicAnimation.get("Walk"));			
		}
	}

	/**
	 * used to move down the character
	 */
	public void moveDown() {

		rotateModel(0, 1, 0);		
		// test if its running or walking
		if (run) {
			// call the method used to move by giving the speed
			move(2, running);
			//tells to animate
			basicAnimation("Run");
			//animate(basicAnimation.get("Run"));
		} else {
			// call the method used to move by giving the speed
			move(2, walk);
			//tells to animate
			basicAnimation("Walk");
			//animate(basicAnimation.get("Walk"));
		}
	}

	/**
	 * Used to move left the character
	 */
	public void moveLeft() {

		rotateModel(0, 1, 0);
		// test if its running or walking
		if (run) {
			// call the method used to move by giving the speed
			move(3, running);
			//tells to animate
			basicAnimation("Run");
			//animate(basicAnimation.get("Run"));
		} else {
			// call the method used to move by giving the speed
			move(3, walk);
			//tells to animate
			basicAnimation("Walk");
			//animate(basicAnimation.get("Walk"));
		}
	}

	/**
	 * Used to move right the character
	 */
	public void moveRight() {

		rotateModel(0, 1, 0);
		// test if its running or walking
		if (run) {
			// call the method used to move by giving the speed
			move(4, running);
			//tells to animate
			basicAnimation("Run");
			//animate(basicAnimation.get("Run"));
		} else {
			// call the method used to move by giving the speed
			move(4, walk);
			//tells to animate
			basicAnimation("Walk");
			//animate(basicAnimation.get("Walk"));
		}
	}

}
