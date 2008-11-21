package framework.rpgsystem.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;

/**
 * Represents a map used in the game, already
 * having a connection with others maps
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Map {
 	
	/**
	 * Responsible for the pattern used to walk, the path pattern
	 */
	private WalkPattern<Path> walkPath;
	
	/**
	 * Responsible for the pattern used to walk, the free pattern
	 */
	private WalkPattern<Free> walkFree;
	
	/**
	 * Responsible for having the file that has
	 * the indication to all maps
	 */
	private String allMaps;
	
	/**
	 * Responsible for holding the way used to move 
	 */
	private String type;
	
	/**
	 * Responsible for holding the place where the character is on the map,
	 * path type pattern
	 */
	private String character;
	 
	/**
	 * Responsible for having the maps that have a connection
	 * with this map
	 * *** change to an array of portals, a portal is a map
	 * that has a connection, but there´s no need to have the entire
	 * map in it
	 * 
	 * it indicates the maps that have a connection to this one,
	 * having them hear takes the need of having them in other classes
	 */
	//private Map[] maps;
	
	/**
	 * Responsible for indicating the maps that have a connection
	 * to this one, disregarding how the portals are created, this
	 * way only the portal is different between the ways to walk
	 */
	private HashMap<String,String> maps;
	
	/**
	 * Constructor of the class
	 */
	public Map(){
		//walkPattern = null;	
		walkPath = null;
		walkFree = null;
		maps = new HashMap<String,String>();
	}
	
	/**
	 * Method used to get the walk patter, if its true the walk
	 * pattern is Free, if its false its Path
	 * @return a boolean representing the walk pattern
	 */
	public boolean getMapType(){
		if(type.equals("Path"))
			return false;
		return true;
	}
		
	public void setAllMaps(String allMaps) {
		this.allMaps = allMaps;
	}

	public void enterMap(){
		//get the identifier of the map
		String map = maps.get(character);
				
		if(map != null){
			
			//need to clean everything
			
			// load the map, look for the map in the archive that
			// has all the maps
			getMap(map);
			
		}
		
	}
	
	public void enterPortal(float x, float y, float z){
		
		//using the position of the Character test
		//if passed by a portal		
		
		//get the portal
		String map = walkFree.getPattern().passedPortal(x, y, z);
				
		//test the map, if not null passed a portal
		if(map != null){
			
			//passed a portal, then load the map
			getMap(maps.get(map));	
			//got the map
			System.out.println("load");
		}		
	}
	
	/**
	 * Responsible for cleaning whats necessary to get the
	 * map ready to draw
	 */
	public void getMapReady(){
		//clean the objects
		walkPath = null;
		walkFree = null;
		maps.clear();
		FactoryManager.getFactoryManager().getGraphicsManager().getGui()
				.cleanDisplay();		
	}
	
	/**
	 * Method used to get a map that is registered in the
	 * archive of maps
	 * @param file receives the identifier of the map to load,a String
	 */
	public void getMap(String name){
		
		//look for the map in the xml		
		Element map = null;
		Element newMap = null;
		
		//get the map ready to load
		getMapReady();
		
		//get the root node
		map = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getRootElement(allMaps);
		
		//get the map that must be loaded
		map = map.getChild(name);
		
		//with the map that must be loaded found, then finally get its root
		newMap = FactoryManager.getFactoryManager().getScriptManager()
			.getReadScript().getRootElement(map.getChildText("Path") + map.getChildText("File"));
		
		//ask it to be load
		loadMap(newMap);
		
	}
	
	/**
	 * Method used to load a map, receives the map to be loaded, that
	 * is a child in the xml file
	 * @param map the map to be loaded, an Element
	 */
	public void loadMap(Element map){
		
		//get the type of movement used
		type = map.getChildText("Movement");
			
		//tells the rpgSystem to configure the camera
		RPGSystem.getRPGSystem().getCamera().createCameraBehavior(
				map.getChild("Camera"));
		
		//create the pattern of movement according to the type
		//of movement used
		if(type.equals("Path")){
			//create the pattern, PATH
			walkPath = new WalkPattern<Path>();
			
			//set the pattern
			walkPath.setPattern(new Path());
					
			//get the portals it has
			loadPathPortals(map.getChild("Portals"));
			
			//load the points
			loadPathPoints(map.getChild("Scene"));
		
			//create the scene
			loadPathScene(map.getChild("Scene"));
			
		}else if(type.equals("Free")){
			//create the pattern, FREE
			walkFree = new WalkPattern<Free>();
			
			//set the pattern
			walkFree.setPattern(new Free());

			//get the portals it has
			loadFreePortals(map.getChild("Portals"));
			
			//create the scene
			loadFreeScene(map.getChild("Scene"));

		}		
	}
	
	public void placeCharacterPoint(String where){
		
		//position of the character in the scene, just fot test
		int x = walkPath.getPattern().getPlace(where).getPositionX();
		int y = walkPath.getPattern().getPlace(where).getPositionY();
				
		//gives the position
		FactoryManager.getFactoryManager().getGraphicsManager().getGui().placeCharacter(x, y);
	}
	
	public void placeCharacterPath(int move){
		
		//get the place where the character is
		Point place = walkPath.getPattern().getPlace(character);
		//try to know if the place has something in the direction it wants to move
		switch(move){
		case 0:{
			//up			
			if(!place.getUp().equals("none")){
				//find the point and place the character there
				placeCharacterPoint(place.getUp());
				//refresh the position of the character
				character = place.getUp();
			}
			break;
		}
		case 1:{			
			//down			
			if(!place.getDown().equals("none")){
				//find the point and place the character there
				placeCharacterPoint(place.getDown());
				//refresh the position of the character
				character = place.getDown();				
			}
			break;
		}
		case 2:{
			//left			
			if(!place.getLeft().equals("none")){
				//find the point and place the character there
				placeCharacterPoint(place.getLeft());
				//refresh the position of the character
				character = place.getLeft();
			}			
			break;
		}
		case 3:{
			//right
			if(!place.getRight().equals("none")){
				//find the point and place the character there
				placeCharacterPoint(place.getRight());
				//refresh the position of the character
				character = place.getRight();
			}
			break;
		}
		
		}
		
	}
	
	/**
	 * Responsible for loading the scene in the path pattern
	 * 
	 * @param scene the scene to be loaded, an Element
	 */
	public void loadPathScene(Element scene){
		
		//calls the ResourceManager to create it
		FactoryManager.getFactoryManager().getResourceManager().loadPathScene(
				scene);
		
		//get the place where the character is positioned
		character = scene.getChild("Character").getChildText("Point");
		
		//insert the character in the display, only once
		FactoryManager.getFactoryManager().getGraphicsManager().getGui().insertCharacter();		
		
		//place the character according to the point where it must be
		placeCharacterPoint(character);
	
	}
	
	/**
	 * Responsible for loading the portals of the path
	 * pattern
	 * 
	 * @param portals the portals to be loaded, an Element
	 */
	public void loadPathPortals(Element portals){		
		//get the list of portals
		
		// get the children to a list
		List list = portals.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();			
			//create the map
			createMap(temp);
			//walkPath.getPattern().createPortal(temp.getChildText("Point"),
			//	temp.getChildText("Map"));
			
		}
		
	}
	
	public void createMap(Element map){	
		//create the map
		maps.put(map.getChildText("Name"), map.getChildText("Map"));		
	}
	
	/**
	 * Responsible for loading the points of the map
	 * that represents places that can be acessed
	 * 
	 * @param points  the points to be loaded, an Element
	 */
	public void loadPathPoints(Element points){
		//calls the pattern, used for the path		
		//get the list of portals
		
		// get the children to a list
		List list = points.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();

			//if is a point, create it
			if(temp.getName().equals("Point")){
				//create the point
				walkPath.getPattern().createPoint(temp.getChildText("Name"),
						Integer.parseInt(temp.getChildText("PosX")),
						Integer.parseInt(temp.getChildText("PosY")),						
						temp.getChildText("Up"),temp.getChildText("Down"),
						temp.getChildText("Left"),temp.getChildText("Right"));
			}
		}
	}	
	
	/**
	 * Responsible for loading the scene in the free pattern
	 * 
	 * @param scene the scene to be loaded, an Element
	 */
	public void loadFreeScene(Element scene){
		//calls the resource manager
		FactoryManager.getFactoryManager().getResourceManager()
				.loadScene(scene);
	}	
	
	/**
	 * Responsible for loading the portals of the free
	 * pattern
	 * 
	 * @param portals the portals to be loaded, an Element
	 */
	public void loadFreePortals(Element portals){
		//calls the pattern, who will hold the portals
		//get the list of portals
		
		// get the children to a list
		List list = portals.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;

		// get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			//create the portal
			walkFree.getPattern().createPortal(temp.getChildText("Name"),
					Float.parseFloat(temp.getChildText("InitialX")), 
					Float.parseFloat(temp.getChildText("InitialY")),
					Float.parseFloat(temp.getChildText("InitialZ")),
					Float.parseFloat(temp.getChildText("FinalX")),
					Float.parseFloat(temp.getChildText("FinalY")),
					Float.parseFloat(temp.getChildText("FinalZ")));
			
			//create the map
			createMap(temp);
		}
		
	}

}
 
