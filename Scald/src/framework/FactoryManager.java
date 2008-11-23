package framework;

import framework.engine.artificialinteligence.AIManager;
import framework.engine.event.EventManager;
import framework.engine.graphics.GraphicsManager;
import framework.engine.input.InputManager;
import framework.engine.resource.ResourceManager;
import framework.engine.rules.RulesManager;
import framework.engine.script.ScriptManager;

/**
 * Responsible for all the managers, having them and using
 * them for the management of the resources necessaries and
 * used by the engine
 * 
 * Is a singleton so that any of the components of the engine
 * can ask for things of the others components without a
 * direct connection
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FactoryManager {
 		 
	/**
	 * Atribute responsible for the artificial inteligence
	 */
	private AIManager aiManager;
	 
	/**
	 * Atribute responsible for the rules
	 */
	private RulesManager rulesManager;
	 
	/**
	 * Atribute responsible for the input 
	 */
	private InputManager inputManager;
	 
	/**
	 * Atribute responsible for the script
	 */
	private ScriptManager scriptManager;
	 
	/**
	 * Atribute responsible for the event
	 */
	private EventManager eventManager;
	 
	/**
	 * Atribute responsible for the graphics
	 */
	private GraphicsManager graphicsManager;
	 
	/**
	 * Atribute responsible for the resources
	 */
	private ResourceManager resourceManager;
	
	private static FactoryManager reference;
	
	
	/**
	 * Constructor of the class,initialize
	 * the objects of the factory
	 */
	private FactoryManager(){
		
		//initialize the objects
		aiManager = new AIManager();
		rulesManager = new RulesManager();		
		inputManager = new InputManager();
		scriptManager = new ScriptManager();
		eventManager = new EventManager();
		graphicsManager = new GraphicsManager();
		resourceManager = new ResourceManager();
		
	}	
	
	public static FactoryManager getFactoryManager(){
		
		//if the object doesn´t exists
		if (reference == null){
			//then create it
			reference = new FactoryManager();			
		}
		
		//return the object
		return reference;
	}
	
	//this way don´t create a clone
	public Object clone()
		throws CloneNotSupportedException{
			throw new CloneNotSupportedException();		
	}
	

	/**
	 * Is used to return the input manager
	 * @return returns an object of the class InputManager
	 */
	public InputManager getInputManager() {
		return inputManager;
	}

	/**
	 * Is used to return the graphics manager
	 * @return returns an object of the class GraphicsManager
	 */
	public GraphicsManager getGraphicsManager() {
		return graphicsManager;
	}

	/**
	 * Is used to return the event manager
	 * @return returns an object of the class EventManager
	 */
	public EventManager getEventManager() {
		return eventManager;
	}

	/**
	 * Is used to return the resource manager
	 * @return returns an object of the class ResourceManager
	 */
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public AIManager getAiManager() {
		return aiManager;
	}

	public RulesManager getRulesManager() {
		return rulesManager;
	}

	public ScriptManager getScriptManager() {
		return scriptManager;
	}	
	
}
 
