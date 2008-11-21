package framework.engine.resource;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.Skybox;
import com.jme.scene.state.CullState;

import framework.FactoryManager;
import framework.engine.resource.audio.AudioManager;
import framework.engine.resource.font.FontManager;
import framework.engine.resource.object.ObjectManager;
import framework.engine.resource.picture.PictureManager;
import framework.engine.resource.video.VideoManager;

/**
 * Manager of the resources that are used in the game, resources like objects
 * that are loaded, images, sounds, etc.
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class ResourceManager {

	/**
	 * Responsible for the videos
	 */
	private VideoManager videoManager;

	/**
	 * Responsible for the sound effects and the background music
	 */
	private AudioManager audioManager;

	/**
	 * Responsible for the objects
	 */
	private ObjectManager objectManager;

	/**
	 * Responsible for the images and sprites
	 */
	private PictureManager pictureManager;

	/**
	 * Responsible for the fonts
	 */
	private FontManager fontManager;
	
	/**
	 * Responsible for holding the objects of the scene
	 */
	public Node scene;

	/**
	 * Constructor of the class, it initializes it´s objects
	 */
	public ResourceManager() {

		// initializing the objects
		audioManager = new AudioManager();
		videoManager = new VideoManager();
		objectManager = new ObjectManager();
		pictureManager = new PictureManager();
		fontManager = new FontManager();
	}

	/**
	 * Method used to return the audio manager
	 * 
	 * @return an object of the class AudioManager
	 */
	public AudioManager getAudioManager() {
		return audioManager;
	}

	/**
	 * Method used to return the object manager
	 * 
	 * @return an object of the class ObjectManager
	 */
	public ObjectManager getObjectManager() {
		return objectManager;
	}

	/**
	 * Method used to return the font manager
	 * 
	 * @return an object of the class FontManager
	 */
	public FontManager getFontManager() {
		return fontManager;
	}

	/**
	 * Method used to return the object manager
	 * 
	 * @return an object of the class PictureManager
	 */
	public PictureManager getPictureManager() {
		return pictureManager;
	}

	/**
	 * Method used to return the object manager
	 * 
	 * @return an object of the class VideoManager
	 */
	public VideoManager getVideoManager() {
		return videoManager;
	}

	/**
	 * Method used to load the audio used in the scene, needs to receive
	 * which file is used to load
	 * @param file The object used to get which musics load, a String object
	 */
	public void loadAudio(String file) {

		// the loader
		Element loader;

		// get the root in the xml file, the root of the audio
		loader = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getElementXML(file, "Sounds");

		// adjust to get all the children in the root

		// get the children to a list
		List list = loader.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();

			if (temp.getChildText("Type").equals("Music")) {
				getAudioManager().createMusic(temp.getChildText("Name"),
						temp.getChildText("Path") + temp.getChildText("File"),
						Float.parseFloat(temp.getChildText("Distance")),
						Float.parseFloat(temp.getChildText("Volume")));
			}

			if (temp.getChildText("Type").equals("Effect")) {
				getAudioManager().createSFX(temp.getChildText("Name"),
						temp.getChildText("Path") + temp.getChildText("File"),
						Float.parseFloat(temp.getChildText("Distance")),
						Float.parseFloat(temp.getChildText("Volume")));
			}
		}
	}

	/**
	 * Method used to load the fonts used, needs to receive
	 * which file is used to load
	 * @param file The object used to get which fonts load, a String object
	 */
	public void loadFonts(String file) {

		// create the objects needed

		// the loader
		Element loader;

		//get the root in the xml file, the root of the fonts
		loader = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getRootElement(file);
		
		// adjust to get all the children in the root

		// get the children to a list
		List list = loader.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			// create a new font
			fontManager.createFont(temp.getChildText("Name"), temp
					.getChildText("Image"), temp.getChildText("Letters"), temp
					.getChildText("Path"));
		}

	}
	
	public void loadModel(String type, String name, String identifier,
			float scale, float posX, float posY, float posZ, float angleX,
			float angleY, float angleZ, String material, float density,
			float slide, float bounce, String event, String eventPath) {
		
		Node object = new Node();
		
		if(type.equals("NPC")){
			object = objectManager.createNPC(name, identifier, scale, posX, posY, posZ, angleX, angleY, angleZ, material, density, slide, bounce, type, event, eventPath);
		}
		
		if(type.equals("Character")){
			object = objectManager.createCharacter(name, identifier, scale, posX, posY, posZ, angleX, angleY, angleZ, material, density, slide, bounce, type, event, eventPath);
		}
		

		if (type.equals("Object")) {
			object = objectManager.createObject(name, identifier, scale, posX, posY, posZ, angleX, angleY, angleZ, material, density, slide, bounce, type, event, eventPath);
		}
		
		if (type.equals("Terrain")) {
			object = objectManager.createTerrain(name, identifier, scale, posX, posY, posZ, angleX, angleY, angleZ, material, density, slide, bounce);			
		}
		
		if (type.equals("Enemy")) {
			object = objectManager.createEnemy(name, identifier, scale, posX, posY, posZ, angleX, angleY, angleZ, material, density, slide, bounce, type, event, eventPath);
		}
		
		//refresh the object that was loaded
		object.updateGeometricState(1, true);

		//refresh the scene
		scene.updateGeometricState(1, true);

		//put the object in the scene
		scene.attachChild(object);
		
		// put the scene that was loaded in the rootNode
//		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
//				.attachChild(node);
//		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
//				.attachChild(scenary);
		
	}
	
	/**
	 * Method used to load the objects used in the scene, needs to receive
	 * which file is used to load
	 * @param file The object used to get which objects load, a String object
	 */
	public void loadScene(Element loader) {

		// create the objects needed
		
		// the scene graph, for the objects
		Node node = new Node("Scene");
		//the scene graph, for other things
		Node scenary = new Node("Scenary");
	
		// adjust to get all the children in the root

		// get the children to a list
		List list = loader.getChildren();
		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();
		
			if (temp.getChildText("Type").equals("NPC")) {
				// create the NPC
				Node object = objectManager.createNPC(
						temp.getChildText("Name"), temp.getChildText("Model"),
						 Float.parseFloat(temp
								.getChildText("Scale")), Float.parseFloat(temp
								.getChildText("PosX")), Float.parseFloat(temp
								.getChildText("PosY")), Float.parseFloat(temp
								.getChildText("PosZ")), Float.parseFloat(temp
								.getChildText("RotateX")), Float
								.parseFloat(temp.getChildText("RotateY")),
						Float.parseFloat(temp.getChildText("RotateZ")), temp
								.getChildText("Material"), Float
								.parseFloat(temp.getChildText("Density")),
						Float.parseFloat(temp.getChildText("Slide")), Float
								.parseFloat(temp.getChildText("Bounce")), "NPC", temp.getChildText("Event"),temp.getChildText("PathEvent"));

				// puts the new node in the scene
				node.attachChild(object);
			}

			if (temp.getChildText("Type").equals("Character")) {
				// create the Character
				Node object = objectManager.createCharacter(temp
						.getChildText("Name"), temp.getChildText("Identifier"),
						Float.parseFloat(temp.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")), "Character", temp.getChildText("Event"),temp.getChildText("PathEvent"));
				
				// puts the new node in the scene
				node.attachChild(object);
			}

			if (temp.getChildText("Type").equals("Enemy")) {
				// create an Enemy
				Node object = objectManager.createEnemy(temp
						.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")), "Enemy", temp.getChildText("Event"),temp.getChildText("PathEvent"));

				// puts the new node in the scene
				node.attachChild(object);
			}

			if (temp.getChildText("Type").equals("Object")) {
				// create an Object
				Node object = objectManager.createObject(temp
						.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")), "Object", temp.getChildText("Event"),temp.getChildText("PathEvent"));

				// puts the new node in the scene
				node.attachChild(object);
			}
			
			if (temp.getChildText("Type").equals("Terrain")) {
				// create an Object
				Node object = objectManager.createTerrain(temp
						.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")));

				// puts the new node in the scene
				scenary.attachChild(object);				
			}
			
			if (temp.getChildText("Type").equals("Skybox")) {
				//create a skybox
				Skybox sb = new Skybox(temp.getChildText("Name"), Float
						.parseFloat(temp.getChildText("ExtentX")), Float
						.parseFloat(temp.getChildText("ExtentY")), Float
						.parseFloat(temp.getChildText("ExtentZ")));
				
				
				//load all the textures
				sb.setTexture(Skybox.NORTH, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("North"),
								temp.getChildText("Path")
										+ temp.getChildText("North")));

				sb.setTexture(Skybox.SOUTH, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("South"),
								temp.getChildText("Path")
										+ temp.getChildText("South")));

				sb.setTexture(Skybox.WEST, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("West"),
								temp.getChildText("Path")
										+ temp.getChildText("West")));

				sb.setTexture(Skybox.EAST, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("East"),
								temp.getChildText("Path")
										+ temp.getChildText("East")));

				sb.setTexture(Skybox.UP, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("Up"),
								temp.getChildText("Path")
										+ temp.getChildText("Up")));

				sb.setTexture(Skybox.DOWN, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("Down"),
								temp.getChildText("Path")
										+ temp.getChildText("Down")));

				  CullState cullState = FactoryManager.getFactoryManager()
						.getGraphicsManager().getRender().getDisplay()
						.getRenderer().createCullState();
				cullState.setCullMode(CullState.CS_NONE);
				cullState.setEnabled(true);
				sb.setRenderState(cullState);

				sb.updateRenderState();
				
				//put the skybox in the scene
				scenary.attachChild(sb);
				//show
				scenary.setCullMode(SceneElement.CULL_NEVER);

			}

		}
		
		// refresh the node
		node.updateGeometricState(1, true);
		
		//refresh the node
		scenary.updateGeometricState(1, true);

		//clean the rootNode, so it doesn´t have anything from another scene
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.detachAllChildren();
		
		//recreate the things that are important, like light and z-buffer
		FactoryManager.getFactoryManager().getGraphicsManager().createZBuffer();
		FactoryManager.getFactoryManager().getGraphicsManager().getRender()
				.createLightState();
		
		//put the two in the rootNode

		// put the scene that was loaded in the rootNode
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.attachChild(node);
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.attachChild(scenary);

		//get the reference for the scene
		scene = node;

	}
	
	/**
	 * Method used to load the objects used in the scene, needs to receive
	 * which file is used to load
	 * @param file The object used to get which objects load, a String object
	 */
	public void loadScene(String file) {

		// create the objects needed
		
		// the scene graph, for the objects
		Node node = new Node("Scene");
		//the scene graph, for other things
		Node scenary = new Node("Scenary");
		
		// the loader
		Element loader;

		// get the root in the xml file, the root of the scene
		loader = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getElementXML(file, "Scene");

		// adjust to get all the children in the root

		// get the children to a list
		List list = loader.getChildren();
		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();
		
			if (temp.getChildText("Type").equals("NPC")) {
				// create the NPC
				Node object = objectManager.createNPC(
						temp.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
								.getChildText("Scale")), Float.parseFloat(temp
								.getChildText("PosX")), Float.parseFloat(temp
								.getChildText("PosY")), Float.parseFloat(temp
								.getChildText("PosZ")), Float.parseFloat(temp
								.getChildText("RotateX")), Float
								.parseFloat(temp.getChildText("RotateY")),
						Float.parseFloat(temp.getChildText("RotateZ")), temp
								.getChildText("Material"), Float
								.parseFloat(temp.getChildText("Density")),
						Float.parseFloat(temp.getChildText("Slide")), Float
								.parseFloat(temp.getChildText("Bounce")),"NPC", temp.getChildText("Event"),temp.getChildText("PathEvent"));

				// puts the new node in the scene
				node.attachChild(object);
			}

			if (temp.getChildText("Type").equals("Character")) {
				// create the Character
				Node object = objectManager.createCharacter(temp
						.getChildText("Name"), temp.getChildText("Model"),Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")), "Character", temp.getChildText("Event"),temp.getChildText("PathEvent"));
				
				// puts the new node in the scene
				node.attachChild(object);
			}

			if (temp.getChildText("Type").equals("Enemy")) {
				// create an Enemy
				Node object = objectManager.createEnemy(temp
						.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")), "Enemy", temp.getChildText("Event"),temp.getChildText("PathEvent"));

				// puts the new node in the scene
				node.attachChild(object);
			}

			if (temp.getChildText("Type").equals("Object")) {
				// create an Object
				Node object = objectManager.createObject(temp
						.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")), "Object", temp.getChildText("Event"),temp.getChildText("PathEvent"));

				// puts the new node in the scene
				node.attachChild(object);
			}
			
			if (temp.getChildText("Type").equals("Terrain")) {
				// create an Object
				Node object = objectManager.createTerrain(temp
						.getChildText("Name"), temp.getChildText("Model"), Float.parseFloat(temp
						.getChildText("Scale")), Float.parseFloat(temp
						.getChildText("PosX")), Float.parseFloat(temp
						.getChildText("PosY")), Float.parseFloat(temp
						.getChildText("PosZ")), Float.parseFloat(temp
						.getChildText("RotateX")), Float.parseFloat(temp
						.getChildText("RotateY")), Float.parseFloat(temp
						.getChildText("RotateZ")), temp
						.getChildText("Material"), Float.parseFloat(temp
						.getChildText("Density")), Float.parseFloat(temp
						.getChildText("Slide")), Float.parseFloat(temp
						.getChildText("Bounce")));

				// puts the new node in the scene
				scenary.attachChild(object);				
			}
			
			if (temp.getChildText("Type").equals("Skybox")) {
				//create a skybox
				Skybox sb = new Skybox(temp.getChildText("Name"), Float
						.parseFloat(temp.getChildText("ExtentX")), Float
						.parseFloat(temp.getChildText("ExtentY")), Float
						.parseFloat(temp.getChildText("ExtentZ")));
				
				
				//load all the textures
				sb.setTexture(Skybox.NORTH, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("North"),
								temp.getChildText("Path")
										+ temp.getChildText("North")));

				sb.setTexture(Skybox.SOUTH, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("South"),
								temp.getChildText("Path")
										+ temp.getChildText("South")));

				sb.setTexture(Skybox.WEST, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("West"),
								temp.getChildText("Path")
										+ temp.getChildText("West")));

				sb.setTexture(Skybox.EAST, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("East"),
								temp.getChildText("Path")
										+ temp.getChildText("East")));

				sb.setTexture(Skybox.UP, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("Up"),
								temp.getChildText("Path")
										+ temp.getChildText("Up")));

				sb.setTexture(Skybox.DOWN, getPictureManager().getPicture()
						.getTexture(
								temp.getChildText("Down"),
								temp.getChildText("Path")
										+ temp.getChildText("Down")));

				  CullState cullState = FactoryManager.getFactoryManager()
						.getGraphicsManager().getRender().getDisplay()
						.getRenderer().createCullState();
				cullState.setCullMode(CullState.CS_NONE);
				cullState.setEnabled(true);
				sb.setRenderState(cullState);

				sb.updateRenderState();
				
				//put the skybox in the scene
				scenary.attachChild(sb);
				//show
				scenary.setCullMode(SceneElement.CULL_NEVER);

			}

		}
		
		// refresh the node
		node.updateGeometricState(1, true);
		
		//refresh the node
		scenary.updateGeometricState(1, true);
		
		//put the two in the rootNode

		// put the scene that was loaded in the rootNode
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.attachChild(node);
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.attachChild(scenary);

		//get the reference for the scene
		scene = node;

	}
	
	public void loadPathScene(Element scene){
		
		//get the elements of the scene and create it
		//in the case of the map, it will be displayed
		//by the GUI, and managed by the Map in the RPGSystem
		
		// get the children to a list
		List list = scene.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			
			//create the map
			if(temp.getName().equals("MapImage")){
				//create the map
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().createMapImage(temp.getChildText("File"),
								temp.getChildText("Path"),
								Integer.parseInt(temp.getChildText("PosX")),
								Integer.parseInt(temp.getChildText("PosY")),
								Integer.parseInt(temp.getChildText("SizeX")),
								Integer.parseInt(temp.getChildText("SizeY")));
			}
			
			//create a point in the map, can be a city, a dungeon, anything
			if(temp.getName().equals("Point")){
				//create a city
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().createCityImage(temp.getChildText("File"),
								temp.getChildText("Path"),
								Integer.parseInt(temp.getChildText("PosX")),
								Integer.parseInt(temp.getChildText("PosY")));
			}
			
			//create the character used in the map
			if(temp.getName().equals("Character")){
				//create the character at the map
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().createCharacterImage(
								temp.getChildText("File"),
								temp.getChildText("Path"));				
			}
			
		}
	}

	public Node getScene() {
		return scene;
	}

	public void setScene(Node scene) {
		this.scene = scene;
	}
}
