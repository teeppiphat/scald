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
import com.jme.intersection.BoundingCollisionResults;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.material.Material;

import framework.FactoryManager;
import framework.RPGSystem;
import framework.util.Animation;

/**
 * Class that has the models used for the dinamic objects
 * used in the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Agent {

	/**
	 * Object used to keep the identifier of the model
	 */
	private String name;
	
	/**
	 * Holds the script of this agent
	 */
	private String event;
	
	/**
	 * Holds the path to this script
	 */
	private String eventPath;
	
	/**
	 * Object used to keep a model
	 */
	Node model;
	
	/**
	 * Has the texture used for the model
	 */
	Texture texture;
	
	/**
	 */
	TextureState textureState;

	/**
	 * Object used to perform the changes by the physic
	 */
	DynamicPhysicsNode physic;

	/**
	 * Object that indicates the way the forces of physic work
	 */
	Material material;

	/**
	 * Object used to hold the data of the collisions
	 */
	BoundingCollisionResults results = new BoundingCollisionResults();

	/**
	 * Object used to specify if the character can move
	 */
	boolean canMove;

	/**
	 * Object used to get the position and move the character
	 */
	Vector3f move;

	/**
	 * Object used to tell how many collisions are happening
	 */
	int collidingWith = 0;

	/**
	 * Object used to set the value for collision, how much go back
	 */
	final float colliding = 0.2f;

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
	
	private int direc;
	
	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public Agent() {
		model = null;
		animations = new HashMap<String, Animation>();
		basicAnimation = new HashMap<String, String>();
	}

	public Vector3f getPhysicPosition() {
		return physic.getLocalTranslation();
	}

	public void physicNode(String type, float density, float slide, float bounce) {

		//put the physic in the same position
		//physic.setLocalTranslation(getModelPosition());

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
		
		//test
		physic.setAffectedByGravity(true);
		
		//compute the node
		physic.generatePhysicsGeometry();
		//compute mass
		physic.computeMass();
		
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

		// create the dynamic node
		physic = FactoryManager.getFactoryManager().getEventManager()
				.getPhysic().getPhysicsSpace().createDynamicNode();

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
		
		//get the root in the xml file, the root of the fonts
		loader = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getRootElement(path + file);
		
		//get the type
		animationType = loader.getChildText("Type");
				
		//get the element that has the animations
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
			//create a new animation
			ani = new Animation();
			ani.setName(temp.getChildText("Name"));
			ani.setBegin(Integer.parseInt(temp.getChildText("Begin")));
			ani.setEnd(Integer.parseInt(temp.getChildText("End")));
			//put the animation in the hash
			animations.put(ani.getName(), ani);
		}
		
		//will get the controller
		//set the controller of the animations to be stopped		
		model.getController(0).setActive(false);
		
		//test
		try{
			joint = (JointController) model.getController(0);	
		}catch (ClassCastException e) {
			System.out.println("Problem joint");
		}
		
		try{
			key = (KeyframeController) model.getController(0);
		}catch (ClassCastException e) {
			System.out.println("Problem key");
		}	
		
		//will get the basic animations
		basic = loader.getChild("Basic");		
		//will put each animation in the hash for them
		basicAnimation.put("Walk", basic.getChildText("Walk"));
		basicAnimation.put("Run", basic.getChildText("Run"));
		basicAnimation.put("Attack", basic.getChildText("Attack"));
		basicAnimation.put("Defend", basic.getChildText("Defend"));
		basicAnimation.put("WorldIdle", basic.getChildText("WorldIdle"));
		basicAnimation.put("BattleIdle", basic.getChildText("BattleIdle"));
		basicAnimation.put("Death", basic.getChildText("Death"));		
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
	
	public void battleBasicAnimation(String name, int repeat){
		//tells to animate for battle using a basic animation
		battleAnimation(basicAnimation.get(name), repeat);
	}
		
	public void battleAnimation(String name, int repeat){
		//get the animation wanted
		Animation ani = animations.get(name);				
		
		//System.out.println("Name: " + model.getName());
		
		//if the animation exists
		if(ani != null){
						
			//put the animation to play
			if(joint != null && !lastAnimation.equals(ani.getName())){
				//get the last animation
				lastAnimation = ani.getName();	
				//set the repeat
				joint.setRepeatType(repeat);
				//set to play
				joint.setActive(true);
				//set the times used for the animation
				joint.setTimes(ani.getBegin(), ani.getEnd());				
			}else if(key != null && !key.isActive()){
				//get the last animation
				lastAnimation = ani.getName();
				//set the repeat
				key.setRepeatType(repeat);
				//set to play
				key.setActive(true);
				//set the times used for the animation
				key.setNewAnimationTimes(ani.getBegin(), ani.getEnd());
			}
		}
		
	}

	public void addModelRootNode() {		
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
				.attachChild(physic);
	}

	//IMPLEMENTS
	public void refreshFrame() {

	}

	/**
	 * Used to refresh the changes in the model
	 */
	public void updateGeometricState() {
		model.updateGeometricState(0, true);
		physic.updateGeometricState(0, true);
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
		//model.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.PI / 2,
		//		new Vector3f(angleX, angleY, angleZ)));
		
		//physic.getLocalRotation().fromAngleAxis(FastMath.DEG_TO_RAD*90,
		//		new Vector3f(angleX, angleY, angleZ));
		
		//System.out.println(-FastMath.PI / 2);
		//System.out.println(FastMath.DEG_TO_RAD*90);
		
		//create the transformer
		//SpatialTransformer transformer = new SpatialTransformer(1);
		//tell which object will be changed
		//transformer.setObject(physic, 0, -1);
		
		//create the rotation used for it, will rotate at time 0
		//Quaternion test = new Quaternion();
		//test.fromAngleAxis(FastMath.DEG_TO_RAD*90, new Vector3f(angleX, angleY, angleZ));
		//transformer.setRotation(0, 0, test);
		
		//put the new controller
		//physic.addController(transformer);
		
		switch(direc){
		case 1:
			//test purpose
			model.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.DEG_TO_RAD*180,
					new Vector3f(angleX, angleY, angleZ)));			
			break;
		case 2:
			// test purpose
			model.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.DEG_TO_RAD*360,
					new Vector3f(angleX, angleY, angleZ)));	
			break;
		case 3:
			// test purpose
			model.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*90,
					new Vector3f(angleX, angleY, angleZ)));	
			break;
		case 4:
			// test purpose
			model.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270,
					new Vector3f(angleX, angleY, angleZ)));	
			break;
		default:
			//temporary object
			int value = 0;;
		
			//test to know what is needed to change
			if(angleX != 0.0f){
				value = (int)angleX;							
			}else if(angleY != 0.0f){
				value = (int)angleY;
			}else if(angleZ != 0.0f){
				value = (int)angleZ;
			}
			
			//will rotate
			model.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*value,
					new Vector3f(angleX, angleY, angleZ)));
			break;
		}
		
		//test purpose
		
		
	}

	public void scaleModel(float scale) {
		//scale the model
		model.setLocalScale(scale);
	}

	public void move(int direction, float speed) {

		// correct form, using only the physic node
		move = physic.getLocalTranslation();
		
		direc = direction;
		// move according to the direction
		switch (direction) {
		case 1: {
			if (collisionBounding()) {
				// up
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z + speed);				
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveFar(speed*2);
			} else {
				//			put the character a little away, to be able to move elsewhere
				//  set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z - colliding);				
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveFar(-0.5f);
			}
			break;
		}
		case 2: {
			if (collisionBounding()) {
				// down
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z - speed);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveNear(speed*2);
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z + colliding);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveNear(-0.5f);
			}
			break;
		}
		case 3: {
			
			if (collisionBounding()) {
				// left
				// set the direction to move,according to the speed that will be
				// used				
				move.setX(move.x - speed);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveLeft(speed*2);				
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x + colliding);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveLeft(-0.5f);
			}
			//System.out.println("Moving left");
			break;
		}
		case 4: {
			if (collisionBounding()) {
				// right
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x + speed);
				// put the force created
				physic.addForce(move);
				//move the camera				
				RPGSystem.getRPGSystem().getCamera().moveRight(speed*2);
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x - colliding);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveRight(-0.5f);
			}
			break;
		}
		case 5: {
			if (collisionBounding()) {
				// up - left
				// up
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z + speed);
				// left
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x - speed);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveRight(speed);
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z - colliding);
				move.setX(move.x + colliding);
				// put the force created
				physic.addForce(move);
			}
			break;
		}
		case 6: {
			if (collisionBounding()) {
				// up - right
				// up
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z + speed);
				// right
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x + speed);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveRight(speed);
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z - colliding);
				move.setX(move.x - colliding);
				// put the force created
				physic.addForce(move);
			}
			break;
		}
		case 7: {
			if (collisionBounding()) {
				// down - left
				// down
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z - speed);
				// left
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x - speed);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveRight(speed);
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z + colliding);
				move.setX(move.x + colliding);
				// put the force created
				physic.addForce(move);
			}
			break;
		}
		case 8: {
			if (collisionBounding()) {
				// down - right
				// down
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z - speed);
				// right
				// set the direction to move,according to the speed that will be
				// used
				move.setX(move.x + speed);
				// put the force created
				physic.addForce(move);
				//move the camera
				RPGSystem.getRPGSystem().getCamera().moveRight(speed);
			} else {
				//put the character a little away, to be able to move elsewhere
				// set the direction to move,according to the speed that will be
				// used
				move.setZ(move.z + colliding);
				move.setX(move.x - colliding);
				// put the force created
				physic.addForce(move);
			}
			break;
		}

		}
		
		//refresh the model node
		model.getLocalTranslation().setX(physic.getLocalTranslation().getX());
		model.getLocalTranslation().setZ(physic.getLocalTranslation().getZ());
		
		//TODO must find out the problem that the model is sinking
	}

	/**
	 * Method use to indicate if a collision happened and tells if the character can move,
	 * returns a boolean object
	 * @return true if can move or false if can´t move
	 */
	public boolean collisionBounding() {

		//set the object to able to move
		canMove = true;
		// clean it
		results.clear();
		// find collisions
		physic.findCollisions(FactoryManager.getFactoryManager()
				.getGraphicsManager().getRootNode(), results);

		if (results.getNumber() > 0) {
			// collision detected
			// must know what object the collision happened

			// if collision with character = move
			// if collision with terrain = move
			// if collision with object = don´t move
			// if collision with NPC = don´t move
			// if collision with Enemy = don´t move

			// to get all the values of the collisions
			collidingWith = 0;

			// test, if there´s one collision thats not with itself or the
			// Terrain stop
			while (canMove && collidingWith < results.getNumber()) {				
				//test the collision
				if (results.getCollisionData(collidingWith).getTargetMesh()
						.getParent().getParent().getName().equals("NPC")
						|| results.getCollisionData(collidingWith)
								.getTargetMesh().getParent().getParent()
								.getName().equals("Enemy")
						|| results.getCollisionData(collidingWith)
								.getTargetMesh().getParent().getParent()
								.getName().equals("Object")) {
					//collision occured					
					if(results.getCollisionData(collidingWith).getTargetMesh().getParent().getParent().getName().equals("Enemy")){
						//temporary
						Node test = results.getCollisionData(collidingWith).getTargetMesh().getParent().getParent();
						//execute the script
						FactoryManager.getFactoryManager().getResourceManager().getObjectManager().executeScript(test.getLocalTranslation(),1);
										
					}
					
					if(results.getCollisionData(collidingWith).getTargetMesh().getParent().getParent().getName().equals("Object")){
						// temporary
						Node test = results.getCollisionData(collidingWith).getTargetMesh().getParent().getParent();
						//execute the script
						FactoryManager.getFactoryManager().getResourceManager().getObjectManager().executeScript(test.getLocalTranslation(),0);
					}
					
					if(results.getCollisionData(collidingWith).getTargetMesh().getParent().getParent().getName().equals("NPC")){
						// temporary
						Node test = results.getCollisionData(collidingWith).getTargetMesh().getParent().getParent();
						//execute the script
						FactoryManager.getFactoryManager().getResourceManager().getObjectManager().executeScript(test.getLocalTranslation(),0);
					}
					
					canMove = false;
				} else {				
					//continue checking if can collide with
					collidingWith++;
				}
			}
		}
		//return if the character can move
		return canMove;
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

	public HashMap<String, Animation> getAnimations() {
		return animations;
	}

	public String getAnimationType() {
		return animationType;
	}

	public Node getModel() {
		return model;
	}

	public DynamicPhysicsNode getPhysic() {
		return physic;
	}

	public JointController getJoint() {
		return joint;
	}

	public KeyframeController getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventPath() {
		return eventPath;
	}

	public void setEventPath(String eventPath) {
		this.eventPath = eventPath;
	}
}
