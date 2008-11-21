package framework;

import framework.rpgsystem.battle.BattleSystem;
import framework.rpgsystem.cutscene.CutScene;
import framework.rpgsystem.evolution.RPGClassNetwork;
import framework.rpgsystem.evolution.PlayerGroup;
import framework.rpgsystem.map.Map;
import framework.rpgsystem.menu.Menu;
import framework.rpgsystem.quest.QuestManager;
import framework.rpgsystem.visualization.Camera;

/**
 * Responsible for controlling the rules that are important for
 * system of the RPG, receiving the information and passing it
 * when necessary for the game 
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RPGSystem {
 	
	private static RPGSystem reference;
	
	/**
	 * Responsible for the menus used in the game
	 */
	private Menu menu;
	
	/**
	 * Responsible for loading the maps used in the game
	 */
	private Map map;
	
	/**
	 * Responsible for the behavior of the camera
	 */
	private Camera camera;
	
	/**
	 * Responsible for having where to find each class
	 */
	private RPGClassNetwork classNetwork;
	
	/**
	 * Holds where all the configurations are
	 */
	private String where;
	
	/**
	 * Holds the group of characters
	 */
	private PlayerGroup playerGroup;
	
	/**
	 * Hold the quests
	 */
	private QuestManager quests;
	
	/**
	 * Controls the battles
	 */
	private BattleSystem battleSystem;
	
	/**
	 * Used to control the cutscene
	 */
	private CutScene cutscene;
	/**
	 * Constructor of the class, it initialize it´s objects
	 */
	private RPGSystem(){
		
		menu = new Menu();
		map = new Map();
		camera = new Camera();
		classNetwork = new RPGClassNetwork();
		playerGroup = new PlayerGroup();
		quests = new QuestManager();
		battleSystem = new BattleSystem();
		cutscene = new CutScene();
	}
	
	public static RPGSystem getRPGSystem(){
		//if the object doesn´t exists
		if (reference == null){
			//then create it
			reference = new RPGSystem();			
		}
		
		//return the object
		return reference;
	}

	/**
	 * Method used to get the menu
	 * 
	 * @return returns an object of the class Menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Method used to get the map
	 * 
	 * @return returns an object of the class Map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Method used to get the camera
	 * 
	 * @return returns an object of the class Camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Method used to get the classNetwork
	 * 
	 * @return returns an object of the class ClassNetwork
	 */
	public RPGClassNetwork getClassNetwork() {
		return classNetwork;
	}

	/**
	 * Method used to get where the configurations is
	 * @return a String
	 */
	public String getWhere() {
		return where;
	}
		
	/**
	 * Method used to set where the configuration is
	 * @param where a String
	 */
	public void setWhere(String where) {
		this.where = where;
	}

	/**
	 * Method used to get the player group
	 * @return an object of the class PlayerGroup
	 */
	public PlayerGroup getPlayerGroup() {
		return playerGroup;
	}

	/**
	 * Method used to get the quests
	 * @return an object of the class QuestManager
	 */
	public QuestManager getQuests() {
		return quests;
	}

	public BattleSystem getBattleSystem() {
		return battleSystem;
	}

	public CutScene getCutscene() {
		return cutscene;
	}	
			
}
 
