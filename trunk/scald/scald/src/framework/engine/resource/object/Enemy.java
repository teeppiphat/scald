package framework.engine.resource.object;

import org.jdom.Element;

/**
 * Responsible for the enemys that are in the world,
 * more positioning them in the world and with the Artificial
 * Inteligence in the engine giving them behaviors, like patrolling 
 * an area of the map, used when the monsters are show in the map or
 * an important event
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Enemy extends Agent{
 	 
	/**
	 * These objects are used for the model
	 */
	String modelFile, modelPath, textureFile, texturePath, animationFile, animationPath;
	
	/**
	 * Constructor of the class
	 */
	public Enemy(){
		
	}
		
	public void createEnemy(String name, String type, float scale, float posX, 
			float posY, float posZ, float angleX,
			float angleY, float angleZ, String material, float density,
			float slide, float bounce, String event, String eventPath) {
		
		//set the name
		setName(name);
		//set the event
		setEvent(event);
		//set the event path
		setEventPath(eventPath);
		//loads the model in the .jme format
		loadModel(modelPath, modelFile);
		// loads the texture of the model
		loadTexture(texturePath, textureFile);
		// loads the animation of the model
		loadAnimation(animationPath, animationFile);
		//put the bounding box
		boundingBox();
		// puts a name in the node
		giveNameModel(type);
		// sets the scale of the model
		scaleModel(scale);
		//puts the physic
		physicNode(material, density, slide, bounce);		
		//rotate the model
		rotateModel(angleX,angleY,angleZ);
		//sets the position of the model
		translateModel(posX, posY, posZ);
		//update the models changes
		updateGeometricState();
		//put the model and the physic in the same coordinates
		physic.setLocalTranslation(this.model.getLocalTranslation().clone());
		//puts the node in the rootNode to be draw
		//addModelRootNode();
		
		//put the animation to begin
		basicAnimation("WorldIdle");
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

}
 
