package framework.engine.resource.object;

import org.jdom.Element;


/**
 * Represents the agents that are controlled by the 
 * computer, but exists in the cities, the people of a
 * city, having actions like talking, selling or even giving
 * quests
 * 
 * @author Diego Ant�nio Tronquini Costi
 *
 */
public class NPC extends Agent{
	
	/**
	 * These objects are used for the model
	 */
	String modelFile, modelPath, textureFile, texturePath, animationFile, animationPath;
	
	int talkPart;
	
	int talkCurrent;
	
	/**
	 * Constructor of the class
	 */
	public NPC(){
		talkPart = 0;
		talkCurrent = 0;
	}
		
	public void createNPC(String name, String type, float scale,
			float posX, float posY, float posZ, float angleX, float angleY,
			float angleZ, String material, float density, float slide,
			float bounce, String event, String eventPath) {
		
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
		//rotate the model
		rotateModel(angleX,angleY,angleZ);
		//sets the position of the model
		translateModel(posX, posY, posZ);		
		//puts the physic
		physicNode(material, density, slide, bounce);		
		//update the models changes
		//updateGeometricState();
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

	public int getTalkCurrent() {
		return talkCurrent;
	}

	public void setTalkCurrent(int talkCurrent) {
		this.talkCurrent = talkCurrent;
	}

	public int getTalkPart() {
		return talkPart;
	}

	public void setTalkPart(int talkPart) {
		this.talkPart = talkPart;
	}
	
}
 