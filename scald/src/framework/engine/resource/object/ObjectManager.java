package framework.engine.resource.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.jdom.Element;

import com.jme.math.Vector3f;
import com.jme.scene.Node;

import framework.FactoryManager;
import framework.engine.event.Event;

/**
 * Responsible for the objects that exists in the world, both the static
 * objects, like houses, and dinamic objects, like the agents
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class ObjectManager {

	/**
	 * Responsible for the characters
	 */
	private Character character;
	
	/**
	 * Responsible for holding everything that was created, character, npc , everything except for the Terrain
	 */
	private HashMap<String,Agent> all;
	
	/**
	 * Holds all the terrains
	 */
	private ArrayList<Terrain> terrains;

	/**
	 * Has the place where all the models are described
	 */
	private String allModels;
	
	/**
	 * Constructor of the class, it initializes it´s objects
	 */
	public ObjectManager() {

		// initializing the objects
		character = new Character();
		all = new HashMap<String, Agent>();
		terrains = new ArrayList<Terrain>();		
	}	

	public Character getCharacter(String name) {
		return (Character)all.get(name);
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public Element loadModel(String name){
		
		//get the root node of the models
		Element load = FactoryManager.getFactoryManager().getScriptManager()
			.getReadScript().getRootElement(allModels);
		
		//get the model wanted
		load = load.getChild(name);
		
		//return the element wanted
		return load;		
	}

	public Node createCharacter(String name, String identifier,
			float scale, float posX, float posY, float posZ, float angleX,
			float angleY, float angleZ, String material, float density,
			float slide, float bounce, String type, String event, String eventPath) {
		
		// create the character
		character = new Character();

		//load the properties of the character
		character.loadModelProperties(loadModel(identifier));
		
		// create the character
		character.createCharacter(name, type, scale, posX, posY, posZ,
				angleX, angleY, angleZ, material, density, slide, bounce, event, eventPath);

		//put the character in the hash
		all.put(name, character);	
		
		// return the model
		return character.physic;

	}

	public Node createNPC(String name, String identifier, float scale,
			float posX, float posY, float posZ, float angleX, float angleY,
			float angleZ, String material, float density, float slide,
			float bounce, String type, String event, String eventPath) {

		// create the object
		NPC object = new NPC();

		//load the properties of the npc
		object.loadModelProperties(loadModel(identifier));
				
		// create the object
		object.createNPC(name, type, scale, posX, posY, posZ, angleX,
				angleY, angleZ, material, density, slide, bounce, event, eventPath);

		//put the npc in the hash
		all.put(name, object);
		
		// return the model
		return object.physic;
	}

	public Node createEnemy(String name, String identifier,
			float scale, float posX, float posY, float posZ, float angleX,
			float angleY, float angleZ, String material, float density,
			float slide, float bounce, String type, String event, String eventPath) {

		// create the object
		Enemy object = new Enemy();

		//load the properties of the npc
		object.loadModelProperties(loadModel(identifier));
		
		// create the object
		object.createEnemy(name, type, scale, posX, posY, posZ, angleX,
				angleY, angleZ, material, density, slide, bounce, event, eventPath);

		//put the enemy in the hash
		all.put(name, object);
		
		// return the model
		return object.physic;
	}

	public Node createObject(String name, String identifier,
			float scale, float posX, float posY, float posZ, float angleX,
			float angleY, float angleZ, String material, float density,
			float slide, float bounce, String type, String event, String eventPath) {

		// create the object
		Object object = new Object();

		//load the properties of the npc
		object.loadModelProperties(loadModel(identifier));
		
		// create the object
		object.createObject(name, type, scale, posX, posY, posZ, angleX,
				angleY, angleZ, material, density, slide, bounce, event, eventPath);

		//put the object in the hash
		all.put(name, object);
		
		// return the model
		return object.physic;
	}
	
	public Node createTerrain(String name, String identifier,
			float scale, float posX, float posY, float posZ, float angleX,
			float angleY, float angleZ, String material, float density,
			float slide, float bounce){
		
		// create the object
		Terrain object = new Terrain();

		//load the properties of the npc
		object.loadModelProperties(loadModel(identifier));

		// create the object
		object.createTerrain(name, scale, posX, posY, posZ, angleX,
				angleY, angleZ, material, density, slide, bounce);

		//put the terrain in its place
		terrains.add(object);
		
		// return the model
		return object.physic;
	}
	
	public void executeScript(Vector3f position, int number){
		//temporary object
		Agent object;
		
		//get the one that the collision happened with
		object = searchNode(position);
		
		//test if was found
		if(object != null){
			//will create the event to execute
			Event event = new Event("script",object.getEvent(),object.getEventPath(),number,0);
			//extra space used for this event, will pass the name of the one that collidied with
			event.setExtra(object.getName());
			//extra space, will pass the type of the one that collidied with
			event.setExtra2(object.getPhysic().getName());
			//extra space used to get the talk if has, only NPC or Object
			if(event.getExtra2().equals("NPC")){
				//System.out.println("NPC");
				//will set the current talk
				event.setExtra3(getNPC(event.getExtra()).getTalkCurrent());				
				//will set the part talk
				event.setExtra4(getNPC(event.getExtra()).getTalkPart());
			}
			
			if(event.getExtra2().equals("Object")){
				//will set the current talk
				event.setExtra3(getObject(event.getExtra()).getTalkCurrent());				
				//will set the part talk
				event.setExtra4(getObject(event.getExtra()).getTalkPart());
			}
			
			//will register the event
			FactoryManager.getFactoryManager().getEventManager().registerEvent(event);
		}
		
	}
	
	public Agent searchNode(Vector3f position){
		
		//temporary object
		Agent temp;
		Collection test;
				
		//get the values of the hash
		test = all.values();
		Iterator it = test.iterator();
				
		//look for the element
		while(it.hasNext()){
			//get the element
			temp = (Agent)it.next();

			//test the element
			if(temp.getPhysic().getLocalTranslation() == position){
				return temp;				
			}			
		}
		
		//if not found
		return null;		
		
	}

	public String getAllModels() {
		return allModels;
	}

	public void setAllModels(String allModels) {
		this.allModels = allModels;
	}

	public HashMap<String, Agent> getAll() {
		return all;
	}

	public void setAll(HashMap<String, Agent> all) {
		this.all = all;
	}
	
	public NPC getNPC(String name){
		return (NPC)all.get(name);
	}
	
	public Object getObject(String name){
		return (Object)all.get(name);
	}
	
}
