package framework.engine.input;

import java.util.Properties;

import org.jdom.Element;

import com.jme.input.InputHandler;
import com.jme.input.InputSystem;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.joystick.JoystickInput;

import framework.FactoryManager;
import framework.RPGSystem;
import framework.engine.event.Event;


/**
 * Class responsible for receiving the comands that the player make,
 * if the comands are made from the keyboard,joypad,pen, it doesn´t care,
 * the data received is translated to the game, this way only receiving
 * things that are useful for the game, and ignoring others
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class InputManager {
 		
	/**
	 * Object used to load the keys used in the keyboard
	 */
	Properties properties;
	
	FengJMEInputHandler mouse;
	
	InputHandler input;
	
	boolean moved;
	
	/**
	 * Constructor of the class, it initializes
	 * the class
	 */
	public InputManager(){
		
		properties = new Properties();
		input = new InputHandler();
				
	}
	
	/**
	 * Method used to clean the input of the keyboard, destroy the keyboard
	 */
	protected void cleanKeyboard(){
		KeyInput.destroyIfInitalized();
	}
	
	/**
	 * Method used to clean the input of the mouse, destroy the mouse
	 */
	protected void cleanMouse(){		
		MouseInput.get().removeListeners();
		MouseInput.destroyIfInitalized();
	}
	
	/**
	 * Method used to clean the input of the joystick, destroy the joystick
	 */
	protected void cleanJoystick(){
		JoystickInput.destroyIfInitalized();
	}
	
	/**
	 * Method used to clean all the inputs, destroy all of them
	 */
	public void cleanInputs(){	
		cleanKeyboard();
		cleanMouse();
		cleanJoystick();		
	}
	
	/**
	 * Method used to receive the input of the player
	 * @param tpf 
	 */
	public void updateInput(float tpf){
		InputSystem.update();
		input.update(tpf);
		if(isMouseCreated())
			updateMouse();
	}
	
	public InputHandler getInput(){
		return input;
	}
	
	/**
	 * Method used to load the keys used in the keyboard
	 * @param file receives an object of the class String that points
	 * to the file that has the keys used in the keyboard
	 */
	public void loadKeys(String file){

		Element properties = FactoryManager.getFactoryManager()
				.getScriptManager().getReadScript().getElementXML(file,
						"KeyboardSettings");
		
		//use the properties object to bind the keys

		//first parameter is used to give the name of the command
		//and the second the key that actives it

		//bind the action key
		KeyBindingManager.getKeyBindingManager().set("accept",
				KeyInput.get().getKeyIndex(properties.getChildText("Accept")));

		//bind the back or cancel key
		KeyBindingManager.getKeyBindingManager().set("back",
				KeyInput.get().getKeyIndex(properties.getChildText("Back")));

		//bind the run key, it´s an state
		KeyBindingManager.getKeyBindingManager().set("run",
				KeyInput.get().getKeyIndex(properties.getChildText("Run")));

		//bind the menu key
		KeyBindingManager.getKeyBindingManager().set("menu",
				KeyInput.get().getKeyIndex(properties.getChildText("Menu")));

		//bind the camera move right key
		KeyBindingManager.getKeyBindingManager().set("cameraR",
				KeyInput.get().getKeyIndex(properties.getChildText("CameraR")));

		//bind the camera move left key
		KeyBindingManager.getKeyBindingManager().set("cameraL",
				KeyInput.get().getKeyIndex(properties.getChildText("CameraL")));

		//bind the camera move up key
		KeyBindingManager.getKeyBindingManager().set("cameraU",
				KeyInput.get().getKeyIndex(properties.getChildText("CameraU")));

		//bind the camera move down key
		KeyBindingManager.getKeyBindingManager().set("cameraD",
				KeyInput.get().getKeyIndex(properties.getChildText("CameraD")));

		//bind the camera move far key
		KeyBindingManager.getKeyBindingManager().set("cameraF",
				KeyInput.get().getKeyIndex(properties.getChildText("CameraF")));

		//bind the camera move near key
		KeyBindingManager.getKeyBindingManager().set("cameraN",
				KeyInput.get().getKeyIndex(properties.getChildText("CameraN")));

		//bind the move up key
		KeyBindingManager.getKeyBindingManager().set("moveU",
				KeyInput.get().getKeyIndex(properties.getChildText("MoveU")));

		//bind the move down key
		KeyBindingManager.getKeyBindingManager().set("moveD",
				KeyInput.get().getKeyIndex(properties.getChildText("MoveD")));

		//bind the move right key
		KeyBindingManager.getKeyBindingManager().set("moveR",
				KeyInput.get().getKeyIndex(properties.getChildText("MoveR")));

		//bind the move left key
		KeyBindingManager.getKeyBindingManager().set("moveL",
				KeyInput.get().getKeyIndex(properties.getChildText("MoveL")));

		//bind the exit key
		KeyBindingManager.getKeyBindingManager().set("exit",
				KeyInput.get().getKeyIndex(properties.getChildText("Exit")));
		
	}
		 
	/**
	 * Method used to create the events according to the keys pressed
	 */
	public void ifKeysPressed(){
					
		//the first argument represents the command
		//the second argument represents if the command accept repetitive keys pressed
		
		//the keys will create events according to the state that the game is
		
		//exit can be pressed anywhere, with the exception of inside a battle
		//try for the exit key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit", false)) {			
			//use only what is needed for the actions
			Event exit = new Event("exit","","",0,0);			
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(exit);
	    }				
		
		//everywhere, create the event according to the state
		//try for the accept key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("accept", false)) {
			//create the event that represents the accept action			
			Event accept = new Event("accept","","",0,0);			
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(accept);

		}

		//everywhere, create the event according to the state
		//try for the back key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("back", false)) {
			//create the event that represents the back action	
			Event back = new Event("back","","",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(back);

		}
		
		//only in the map, if is Free pattern
		//try for the run key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("run", false)) {
			//create the event that represents the run action
			Event run = new Event("run","character","",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(run);
		}
		
		//only in the map without concerning the pattern and when the menu is not open
		//try for the menu key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("menu", false)) {
			//create the event that represents the menu action
			Event menu = new Event("menu","character","",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(menu);
		}

		//try for the camera right key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("cameraR", true)) {
			//create the event that represents the camera right action
			Event cameraR = new Event("move","camera","right",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(cameraR);
		}
		
		//try for the camera left key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("cameraL", true)) {
			//create the event that represents the camera left action
			Event cameraL = new Event("move","camera","left",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(cameraL);
		}
		
		//try for the camera up key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("cameraU", false)) {
			//create the event that represents the camera up action
			Event cameraU = new Event("move","camera","up",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(cameraU);
		}

		//try for the camera down key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("cameraD", false)) {
			//create the event that represents the camera down action
			Event cameraD = new Event("move","camera","down",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(cameraD);
		}
		
		//try for the camera far key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("cameraF", true)) {
			//create the event that represents the camera far action
			Event cameraF = new Event("move","camera","far",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(cameraF);
		}
		
		//try for the camera near key
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("cameraN", true)) {
			//create the event that represents the camera near action
			Event cameraN = new Event("move","camera","near",0,0);
			//uses the singleton, to don´t need to use a vector to pass the events
			FactoryManager.getFactoryManager().getEventManager().registerEvent(cameraN);
		}

		// if its using map of the type free and is not in a battle
		if (RPGSystem.getRPGSystem().getMap().getMapType()) {

			moved = false;
			
			// try for the move up key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveU", true)) {
				// create the event that represents the move up action
				Event moveU = new Event("move", "character", "up", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveU);
				
				moved = true;
			}

			// will be read according to the state where it is
			// try for the move down key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveD", true)) {
				// create the event that represents the move down action
				Event moveD = new Event("move", "character", "down", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveD);
				
				moved = true;
			}

			// will be read according to the state where it is
			// try for the move right key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveR", true)) {
				// create the event that represents the move right action
				Event moveR = new Event("move", "character", "right", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveR);
				
				moved = true;
			}

			// will be read according to the state where it is
			// try for the move left key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveL", true)) {
				// create the event that represents the move left action
				Event moveL = new Event("move", "character", "left", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveL);
				
				moved = true;
			}
			
			if(!moved){
				//create the event to animate the character when its stopped
				Event still = new Event("move","character","still",0,0);
				//pass the event
				FactoryManager.getFactoryManager().getEventManager().registerEvent(still);
				
			}
		} else {
			// else

			// will be read according to the state where it is
			// try for the move up key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveU", false)) {
				// create the event that represents the move up action
				Event moveU = new Event("move", "character", "up", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveU);
			}

			// will be read according to the state where it is
			// try for the move down key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveD", false)) {
				// create the event that represents the move down action
				Event moveD = new Event("move", "character", "down", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveD);
			}

			// will be read according to the state where it is
			// try for the move right key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveR", false)) {
				// create the event that represents the move right action
				Event moveR = new Event("move", "character", "right", 0, 0);
				// uses the singleton, to don´t need to use a vector to pass the
				// events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveR);
			}

			// will be read according to the state where it is
			// try for the move left key
			if (KeyBindingManager.getKeyBindingManager().isValidCommand(
					"moveL", false)) {
				// create the event that represents the move left action
				Event moveL = new Event("move", "character", "left", 0, 0);
				//uses the singleton, to don´t need to use a vector to pass the events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(moveL);
			}
		}
		

	}
	
	public boolean isMouseCreated(){
		if (mouse == null)
			return false;
		else
			return true;
	}
	
	public void createMouse(){
		//creates the mouse
		mouse = new FengJMEInputHandler();
		//create the cursor
		FactoryManager.getFactoryManager().getGraphicsManager().getGui()
				.createCursor();
						
	}
	
	public void updateMouse(){		
		mouse.update(0.0f);
	}
}
 
