package framework.engine.resource.object;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.material.Material;

import framework.FactoryManager;
import framework.util.Animation;

public class Terrain {
	/**
	 * Object used to keep a model
	 */
	Node model;

	/**
	 * Object used to perform the changes by the physic
	 */
	StaticPhysicsNode physic;

	/**
	 * Has the texture used for the model
	 */
	Texture texture;
	
	/**
	 */
	TextureState textureState;
	
	/**
	 * Object that indicates the way the forces of physic work
	 */
	Material material;

	/**
	 * These objects are used for the model
	 */
	String modelFile, modelPath, textureFile, texturePath, animationFile, animationPath;
	
	/**
	 * Holds the animations of the model
	 */
	private HashMap<String,Animation> animations;
	
	/**
	 * Identify the commom animations
	 */
	HashMap<String,String> basicAnimation;
	
	/**
	 * Holds the type used for the animations
	 */
	private String animationType;
	
	/**
	 * Used for the animation
	 */
	private KeyframeController key = null;
	
	/**
	 * Used for the animation
	 */
	private JointController joint = null;
	
	private String lastAnimation = "";
	
	//private int direc;
	
	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public Terrain() {
		model = null;
		animations = new HashMap<String, Animation>();
		basicAnimation = new HashMap<String, String>();
	}

	public void physicNode(String type, float density, float slide, float bounce) {
		
		//put the visual node in the physic node
		physic.attachChild(model);

		//create the material according to the type specified
		if (type.equals("Default")) {			
			//set the material
			material = Material.DEFAULT;
		} else if (type.equals("Ice")) {
			//set the material
			material = Material.ICE;
		} else if (type.equals("Wood")) {
			//set the material
			material = Material.WOOD;
		} else if (type.equals("Sponge")) {
			//set the material
			material = Material.SPONGE;
		} else if (type.equals("Concrete")) {
			//set the material
			material = Material.CONCRETE;
		} else if (type.equals("Ghost")) {
			// set the material
			material = Material.GHOST;
		} else if (type.equals("Glass")) {
			// set the material
			material = Material.GLASS;
		} else if (type.equals("Granite")) {
			// set the material
			material = Material.GRANITE;
		} else if (type.equals("Iron")) {
			// set the material
			material = Material.IRON;
		} else if (type.equals("Plastic")) {
			// set the material
			material = Material.PLASTIC;
		} else if (type.equals("Osmium")) {
			// set the material
			material = Material.OSMIUM;
		} else if (type.equals("Rubber")) {
			// set the material
			material = Material.RUBBER;
		} else if (type.equals("Custom")) {
			// create the material used for the node, must use a type
			material = new Material("");
			// set the density of the material
			material.setDensity(density);
			//create object used to definy the contact
			MutableContactInfo contact = new MutableContactInfo();
			//set the sliding
			contact.setMu(slide);
			//set the bounding
			contact.setBounce(bounce);
			//put the created values on the material
			material.putContactHandlingDetails(Material.DEFAULT, contact);
		}

		//set the material
		physic.setMaterial(material);	
		
		//compute the node
		physic.generatePhysicsGeometry(true);
		
		//physic.setMass(1.0f);
		//physic.setActive(true);
		//compute the mass - problem
		//physic.computeMass();

	};

	/**
	 * Method used to load a model
	 * @param path represents the place where the model is stored
	 * @param file represents the name of the model
	 */
	public void loadModel(String path, String file) {

		//object used to find the model
		URL url;

		try {
			//points to the place where the model is
			url = new URL("file:" + path + file);
			//creates the class used to import the model
			BinaryImporter importer = new BinaryImporter();
			//load the model
			model = (Node) importer.load(url.openStream());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// create the static node
		physic = FactoryManager.getFactoryManager().getEventManager()
				.getPhysic().getPhysicsSpace().createStaticNode();
		
		//try to load a texture that is needed, it will be very useful for the images
		/*try {
		 MultiFormatResourceLocator loc2 = 
		 new MultiFormatResourceLocator(ResourceLocatorTool.class.getResource("/framework/images/textures/"), ".jpg", ".png", ".tga");
		 ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, loc2);
		 } catch (URISyntaxException e) {
		 e.printStackTrace();
		 }
		 
		 URL u = ResourceLocatorTool.locateResource(ResourceLocatorTool.TYPE_TEXTURE, "wallbump.jpg");
		 System.out.println("FOUND URL: "+u);
		 */
	}
	
	public void loadTexture(String path, String file) {
		if (!path.equals("none")) {
			// get the texture wanted
			texture = FactoryManager.getFactoryManager().getResourceManager()
					.getPictureManager().getPicture().getTexture(file,
							path + file);

			// create the state for the texture
			textureState = FactoryManager.getFactoryManager()
					.getGraphicsManager().getRender().getDisplay()
					.getRenderer().createTextureState();

			// enable the texture state
			textureState.setEnabled(true);

			// put the texture in the state
			textureState.setTexture(texture);

			// put the texture in the model
			model.setRenderState(textureState);
		}
	}
	
	public void loadAnimation(String path, String file){
		
		// the loader
		Element loader;
		Element basic;
		
		if (!path.equals("none")) {
			// get the root in the xml file, the root of the fonts
			loader = FactoryManager.getFactoryManager().getScriptManager()
					.getReadScript().getRootElement(path + file);

			// get the type
			animationType = loader.getChildText("Type");

			// get the element that has the animations
			basic = loader.getChild("Animations");

			// get the children to a list
			List list = basic.getChildren();

			// create the iterator to access the list
			Iterator i = list.iterator();

			// temporary element used to get the elements
			Element temp;
			Animation ani;

			// get the elements from the list
			while (i.hasNext()) {
				// get the first object
				temp = (Element) i.next();
				// create a new animation
				ani = new Animation();
				ani.setName(temp.getChildText("Name"));
				ani.setBegin(Integer.parseInt(temp.getChildText("Begin")));
				ani.setEnd(Integer.parseInt(temp.getChildText("End")));
				// put the animation in the hash
				animations.put(ani.getName(), ani);
			}

			// will get the controller
			// set the controller of the animations to be stopped
			model.getController(0).setActive(false);

			// test
			try {
				joint = (JointController) model.getController(0);
			} catch (ClassCastException e) {
				System.out.println("Problem joint");
			}

			try {
				key = (KeyframeController) model.getController(0);
			} catch (ClassCastException e) {
				System.out.println("Problem key");
			}

			// will get the basic animations
			basic = loader.getChild("Basic");
			// will put each animation in the hash for them
			basicAnimation.put("Walk", basic.getChildText("Walk"));
			basicAnimation.put("Run", basic.getChildText("Run"));
			basicAnimation.put("Attack", basic.getChildText("Attack"));
			basicAnimation.put("Defend", basic.getChildText("Defend"));
			basicAnimation.put("WorldIdle", basic.getChildText("WorldIdle"));
			basicAnimation.put("BattleIdle", basic.getChildText("BattleIdle"));
			basicAnimation.put("Death", basic.getChildText("Death"));

		}
	}
	
	public void basicAnimation(String name){
		//tells to animate using a basic animation
		animate(basicAnimation.get(name));
	}
		
	public void animate(String name){
		
		//get the animation wanted
		Animation ani = animations.get(name);				
		
		//if the animation exists
		if(ani != null){
						
			//put the animation to play
			if(joint != null && !lastAnimation.equals(ani.getName())){
				//get the last animation
				lastAnimation = ani.getName();				
				//set to play
				joint.setActive(true);
				//set the times used for the animation
				joint.setTimes(ani.getBegin(), ani.getEnd());				
			}else if(key != null && !key.isActive()){
				//get the last animation
				lastAnimation = ani.getName();
				//set to play
				key.setActive(true);
				//set the times used for the animation
				key.setNewAnimationTimes(ani.getBegin(), ani.getEnd());
			}
		}
	}

	public void addModelRootNode() {
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.attachChild(model);
	}

	//IMPLEMENTS
	public void refreshFrame() {

	}

	/**
	 * Used to refresh the changes in the model
	 */
	public void updateGeometricState() {
		model.updateGeometricState(0, true);
	}

	/**
	 * Used to move the model
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translateModel(float x, float y, float z) {
		//translate the model
		model.setLocalTranslation(new Vector3f(x, y, z));
	}

	//new Vector3f(0.0f,0.0f,20.5f

	public void rotateModel(float angleX, float angleY, float angleZ) {
		//the rotation of the model		
		model.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI / 2,
				new Vector3f(angleX, angleY, angleZ)));
	}

	public void scaleModel(float scale) {
		//scale the model
		model.setLocalScale(scale);
	}

	//0.0025

	public void boundingBox() {
		//put a bounding box
		model.setModelBound(new BoundingBox());
		//update the bounding box
		model.updateModelBound();
	}

	public void giveNameModel(String name) {
		//gives a name to identify the model, same name is used to the physic
		model.setName(name);
		physic.setName(name);
	}

	public Vector3f getModelPosition() {
		return model.getLocalTranslation();
	}
	
	public void createTerrain(String name, 
			float scale, float posX, float posY, float posZ, float angleX,
			float angleY, float angleZ, String material2, float density,
			float slide, float bounce) {
		
		//loads the model in the .jme format
		loadModel(modelPath, modelFile);
		//loads the texture of the model
		loadTexture(texturePath, textureFile);
		// loads the animation of the model
		loadAnimation(animationPath, animationFile);
		//put the bounding box
		boundingBox();
		//puts a name in the node
		giveNameModel(name);
		//sets the scale of the model
		scaleModel(scale);
		//rotate the model
		rotateModel(angleX, angleY, angleZ);
		//sets the position of the model
		translateModel(posX, posY, posZ);		
		// puts the physic
		physicNode(material2, density, slide, bounce);
				
		//update the models changes
		//updateGeometricState();
		//puts the node in the rootNode to be draw
		//addModelRootNode();
		
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

	public String getAnimationType() {
		return animationType;
	}

	
}
