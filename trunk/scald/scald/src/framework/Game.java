package framework;

import java.util.Random;

import com.jme.app.AbstractGame;
import com.jme.util.Timer;

/**
 * Core of the framework, its used for the union of the
 * architecture(?) of the engine, that is controlled by
 * the FactoryManager, and the rules of the RPG, that are
 * controlled by the RPGSystem  
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Game extends AbstractGame {

	/**
	 * Atribute of the class, it´s the
	 * system of the RPG
	 */
	private RPGSystem rpgSystem;
	/**
	 * Atribute of the class, it´s the
	 * engine
	 */
	private FactoryManager factoryManager;

	/**
	 * Simply an easy way to get at timer.getTimePerFrame(). Also saves time so
	 * you don't call it more than once per frame.
	 */
	protected float tpf;

	/**
	 * High resolution timer for jME.
	 */
	protected Timer timer;

	/**
	 * Atribute of the class, it´s used 
	 * to keep the application running
	 */
	public static boolean running = false;

	public static boolean menu = false;
	
	public static boolean battle = false;
	
	public static boolean cutScene = false;
	
	private boolean firstFrame = true;

	//private String settings;

	//private String firstMap;
	
	private String where;
	
	private Random random = new Random();

	public Game(String where){
		// uses the singleton
		factoryManager = FactoryManager.getFactoryManager();
		rpgSystem = RPGSystem.getRPGSystem();

		// set the file used to call the configuration of the others
		this.where = where;
		//to get the configurations
		rpgSystem.setWhere(where);
		
	}
	
	/**
	 * Constructor of the class
	 */
	/*public Game(String settings, String firstMap) {
		//uses the singleton
		factoryManager = FactoryManager.getFactoryManager();
		rpgSystem = RPGSystem.getRPGSystem();

		//sets the file used to configure the display and the keyboard
		this.settings = settings;
		this.firstMap = firstMap;

	}*/

	//executes to clean the input commands that are received
	//and cleans up the objects
	@Override
	protected void cleanup() {
		// TODO Auto-generated method stub
		//put the information in a log

		//the InputManager cleans the inputs
		factoryManager.getInputManager().cleanInputs();

		//destroy the audio system
		factoryManager.getResourceManager().getAudioManager()
				.destroyAudioSystem();

	}

	//first method that executes after had received the data of the display
	//it is used to initalize some objets
	@Override
	protected void initSystem() {

		//create the display used by the JME, returns it to the display
		factoryManager.getGraphicsManager().createDisplay(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Display"));
		
		//make the use of Z-Buffer
		factoryManager.getGraphicsManager().createZBuffer();

		//creates the display used by the GUI
		factoryManager.getGraphicsManager().getGui().createDisplay();

		//initalize other objects
		
		//get the timer, used to update the physic
		timer = Timer.getTimer();

		//bind the keyboard
		factoryManager.getInputManager().loadKeys(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Keyboard"));

		//bind the mouse, if is used, treat the mouse as the pen of the Nintendo DS
		factoryManager.getInputManager().createMouse();

		//initialize the sound system		
		factoryManager.getResourceManager().getAudioManager()
				.createAudioSystem();

		//get the configurations used to the audio system
		factoryManager.getResourceManager().getAudioManager()
				.setConfigurations(
						factoryManager.getScriptManager().getReadScript()
								.getFileElement(where, "Audio"));

		//create the engine used for the script
		factoryManager.getScriptManager().getReadScript().createJavaScriptEngine();
		
	}

	//used to reinitalize the application, unused
	@Override
	protected void reinit() {
		// TODO Auto-generated method stub		
	}

	//called each frame to refresh the display
	@Override
	protected void render(float interpolation) {
		// TODO Auto-generated method stub

		//refresh the display, already passing the objects that need to be draw		
		factoryManager.getGraphicsManager().getRender().refreshDisplay(
				factoryManager.getGraphicsManager().getRootNode());

		//refresh the display used by the GUI
		factoryManager.getGraphicsManager().getGui().displayDraw();

	}

	//called to refresh the display, before the method render is used to receive
	//the inputs and to move the objects
	@Override
	protected void update(float interpolation) {
		//update the time
		timer.update();
		//update the tpf to time per frame, to calculate the physic
		tpf = timer.getTimePerFrame();

		// for things to don´t happen to fast
		if ( tpf > 0.2 || Float.isNaN( tpf ) ) {            
            tpf = 0.2f;
        }
		//update the physic
		factoryManager.getEventManager().getPhysic().updatePhysic(tpf);
		
		//update the inputs and using the InputManager the inputs are treated 
		factoryManager.getInputManager().updateInput(tpf);

		//after receiving the inputs update according to what was pressed
		//pass the events directly to the EventManager thanks to the singleton
		//of the class FactoryManager
		factoryManager.getInputManager().ifKeysPressed();

		//refresh the audio system
		factoryManager.getResourceManager().getAudioManager()
				.updateAudioSytem();
		
		//treat the events
		factoryManager.getEventManager().treatEvents();
		
		//refresh the rootNode
		factoryManager.getGraphicsManager().getRootNode().updateRenderState();

		//refresh the vectors of the world, for the rotation
		factoryManager.getGraphicsManager().getRootNode().updateWorldVectors();
				
		//refresh the models translations
		factoryManager.getGraphicsManager().getRootNode().updateGeometricState(
				tpf, false);

		
		//to keep the battle going
		if(battle){
			//keep the battle going
			rpgSystem.getBattleSystem().battle();
		}
		
		//test to read a cut-scene
		if(cutScene){
			rpgSystem.getCutscene().execute("res/cutScene/","cutScene.js","scene");
			if(rpgSystem.getCutscene().isMoving()){				
				RPGSystem.getRPGSystem().getCutscene().moveAll();
			}
		}
		
		if ( firstFrame )
        {
            // drawing and calculating the first frame usually takes longer than the rest
            // to avoid a rushing simulation we reset the timer
            timer.reset();
            firstFrame = false;
        }
	}

	//called to begin the application, initalizing some objects
	//and having the loop to keep the game in execution
	@Override
	protected void initGame() {
		// TODO Auto-generated method stub

		// create the physic
		factoryManager.getEventManager().getPhysic().createPhysic();
		
		// only a test. creating a light
		factoryManager.getGraphicsManager().getRender().createLightState();
		
		//load the rules of the game
		factoryManager.getRulesManager().loadFile(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Rules"));
		
		//set the class network
		rpgSystem.getClassNetwork().setClassNetwork(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "ClassNetwork"));
		
		//set the place where the configuration is
		rpgSystem.getPlayerGroup().setWhere(where);
		
		//set the where to be used for the AI to load its place from
		factoryManager.getAiManager().setWhere(where);
		
		//put the random class in the script
		factoryManager.getScriptManager().getReadScript().mapToTheScript(
				"Random", random);
		
		//putting the player group in the script
		factoryManager.getScriptManager().getReadScript().mapToTheScript(
				"PlayerGroup", rpgSystem.getPlayerGroup());
		
		//put the factoryManager in the script
		factoryManager.getScriptManager().getReadScript().mapToTheScript(
				"FactoryManager", factoryManager);
		
		//put the rpgSystem in the script
		factoryManager.getScriptManager().getReadScript().mapToTheScript(
				"RPGSystem", rpgSystem);
		
		//put the Game in the script
		factoryManager.getScriptManager().getReadScript().mapToTheScript(
				"Game", this);
		
		//load the quests
		rpgSystem.getQuests().loadQuest(factoryManager.getScriptManager().getReadScript()
				.getFileElement(where, "Quests"));
		
		//load the character,class,Features, what is needed of these things
		rpgSystem.getPlayerGroup().createPlayerGroup(
				"Lynder",
				factoryManager.getRulesManager().getRulesSet()
						.getPlayersInGroup());
		
		rpgSystem.getPlayerGroup().createCharacter("Adan");
		
		//create the money
		rpgSystem.getPlayerGroup().createMoney();
		
		//testing
		//rpgSystem.getPlayerGroup().printGroup();
		
		//create the bag for the group
		rpgSystem.getPlayerGroup().createGroupBag();
				
		//initialize the battle system
		rpgSystem.getBattleSystem().init();
		
		//put the Battle system in the script
		factoryManager.getScriptManager().getReadScript().mapToTheScript(
				"BattleSystem", rpgSystem.getBattleSystem());
		
		//to know if changed in the right way the status
		
		
		//testing evolving, calculating experience
		rpgSystem.getPlayerGroup().groupExperience(20, 50, 20, 20);		
		
		//testing the use of a Feature
		//for(int i = 0; i < 10; i++){
		//	rpgSystem.getPlayerGroup().getCharacter(0).useClassFeature("Cut");
		//}
		//testing
		//rpgSystem.getPlayerGroup().printGroup();
		
		//testing
		rpgSystem.getPlayerGroup().addItem("Apple", 10);
		rpgSystem.getPlayerGroup().addItem("Apple", 1);
		rpgSystem.getPlayerGroup().addItem("Apple", 3);
		
		rpgSystem.getPlayerGroup().addItem("Nut", 6);
		
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);

		
		//to create the item use the identifier
		//rpgSystem.getPlayerGroup().addItem("Long_Sword", 1);	
		
		//rpgSystem.getPlayerGroup().addItem("Apple", 14);
		rpgSystem.getPlayerGroup().addItem("Bomb", 14);
		
		
		//test
		battle = false;
		
		//to remove the item use the name of the item
		//rpgSystem.getPlayerGroup().removeItem("Long Sword", 1);
		
		//testing
		//rpgSystem.getPlayerGroup().printItems();
				
		//tells the character to equip this item
		//rpgSystem.getPlayerGroup().getCharacter(0).equipItem(
		//		rpgSystem.getPlayerGroup().getBag().getItem("Long Sword"));
		
		//rpgSystem.getPlayerGroup().printItems();
		
		//rpgSystem.getPlayerGroup().getCharacter(0).removeItem("Long Sword");
		
		//rpgSystem.getPlayerGroup().printItems();
				
		//initalize some objects		
		//load the scene
		//factoryManager.getResourceManager().loadScene(firstMap);
		
		//load the map, it must be done by the RPGSystem, and it already gets the scene
		//rpgSystem.getMap().loadMap(firstMap);
		
		//set the file that holds the direction for all the maps
		rpgSystem.getMap().setAllMaps(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Maps"));
		
		//set the file that holds all the models descriptions
		factoryManager.getResourceManager().getObjectManager().setAllModels(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Models"));
		
		//say the map that must be loaded		
		rpgSystem.getMap().getMap("City");
		         
		//load the audio
		factoryManager.getResourceManager().loadAudio(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Audio"));

		//load the fonts
		factoryManager.getResourceManager().loadFonts(
				factoryManager.getScriptManager().getReadScript()
						.getFileElement(where, "Fonts"));
		
		//create the conversation box
		rpgSystem.getMenu().createConvesationBox("menu.xml",
				"res/properties/", "Conversation");
		
		//create the menu
		rpgSystem.getMenu().createPrimaryMenu("menu.xml", "res/properties/",
				"MainMenu");
		
		//create the battle menu
		rpgSystem.getMenu().createBattleMenu("menu.xml", "res/properties/",
				"BattleMenu");
		
		//for test purpose only
		factoryManager.getScriptManager().getReadScript().loadScript("res/scripts/rules/", "ongoing.js");
		
		//create the event to treat collision, lose the physic to use this
		factoryManager.getEventManager().createCollision();
		
		//calculates the distance so that can rotate the camera, testing
		rpgSystem.getCamera().calculatesDistance();
		
		//to test the cut-scene
		cutScene = false;
		rpgSystem.getPlayerGroup().setMoneyValue(200);
		
		//reset the timer
		timer.reset();
	}

	@Override
	protected void quit() {
		//used to close the display
		factoryManager.getGraphicsManager().getRender().close();
		//used to end the JVM
		System.exit(0);
	}

	@Override
	public void start() {

		//begin creating everything necessary
		if (!running) {

			//begins initalizing the system
			initSystem();

			//to make sure the display was created successfully
			factoryManager.getGraphicsManager().getRender()
					.assertDisplayCreated();

			//calls to begin the creation of the objects
			//of the game, the first part of the game
			initGame();

			//main loop
			while (!running
					&& !factoryManager.getGraphicsManager().getRender()
							.isClosing()) {

				//update game state, do not use interpolation parameter
				update(-1.0f);

				//render, do not use interpolation parameter
				render(-1.0f);

				//swap buffers
				factoryManager.getGraphicsManager().getRender().swapBuffers();

			}
		}

		//used to clean the objects
		cleanup();

		//get the display ready to close
		factoryManager.getGraphicsManager().getRender().reset();

		//clean the display
		quit();

	}
	
}
