package framework.rpgsystem.battle;

import java.util.Random;
import java.util.Vector;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;
import framework.engine.event.Event;
import framework.rpgsystem.item.Item;
import framework.util.ChangeFeature;
import framework.util.StrategyAction;

/**
 * Responsible for controlling the order of the actions
 * during the game that is based in queue turn
 * 
 * @author Diego Antônio Tronquini costi
 *
 */
public class QueueTurnbased extends TurnBased {
 
	/**
	 * Holds all the actions
	 */
	private Vector<Action> queue;
	
	/**
	 * Holds the actions in order
	 */
	private Vector<Action> order;
	
	/**
	 * Used to warm that the configuration for the battle has been loaded
	 */
	private boolean start;
	
	/**
	 * To test if the player won
	 */
	private boolean victory;
	
	/**
	 * To test if the player lost
	 */
	private boolean defeat;
			
	/**
	 * Used for the size of the characters party
	 */
	private short size;
	
	/**
	 * Used for the enemy action
	 */
	private StrategyAction ene;
		
	/**
	 * Used for the Z of the camera
	 */
	private float cameraZ;
	
	/**
	 * Used for the action that must execute
	 */
	private int i;
		
	/**
	 * Used to stop the execution
	 */
	private boolean stop;
	
	/**
	 * Used for the test of each execution
	 */
	private Action temp;
	
	/**
	 * Used for the enemy
	 */
	private Random rand;
	
	public QueueTurnbased(){
		queue = new Vector<Action>();
		order = new Vector<Action>();		
		start = false;
		victory = false;
		defeat = false;
		rand = new Random();
		ene = new StrategyAction();	
		moment = null;
	}
	
	//describes a simple attack
	public Action simpleAttack(boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, int where){
		//create the AttackAction
		Action action = new AttackAction();
		
		//the action
		action.setWhat("Attack");
		
		//what is used
		action.setUsed("Simple");
		
		//user - the group
		action.setUserGroup(userGroup);
		//user - the position
		action.setUserPosition(userPosition);
		//target - the group
		action.setTargetGroup(targetGroup);
		//target - the position
		action.setTargetPosition(targetPosition);
		
		//will create according to who is doing the action
		if(userGroup){
			//character			
			//the name of the one doing the action
			action.setName(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getName());
			//the speed of the action
			//will set the speed of the action to the fastest possible, in this case the max value that can have
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).
						getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}	
						
		}else{
			//the name of the enemy doing the action
			action.setName(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getName());
			//the speed of the action
			action.setSpeed(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getStatusValue(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
		}
		
		return action;
		
	}
	
	//describes a combo attack
	public void comboAttack(Action action, boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, int where){
		
	}
	
	public Action defendAction(boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, int where){
		//create the defend Action
		Action action = new DefendAction();
		
		//the action
		action.setWhat("Defend");
		
		//what is used - nothing since is defending
		action.setUsed(null);
		
		//user - the group
		action.setUserGroup(userGroup);
		//user - the position
		action.setUserPosition(userPosition);
		//target - the group
		action.setTargetGroup(targetGroup);
		//target - the position
		action.setTargetPosition(targetPosition);
		
		//the speed for defending is always the greatest
		action.setSpeed(9999);
		
		//will create according to who is doing the action
		if(userGroup){
			//character			
			//the name of the one doing the action
			action.setName(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getName());						
		}else{
			//the name of the enemy doing the action
			action.setName(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getName());			
		}		
		
		return action;
	}
	
	public Action itemAction(boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, String used, int where){
		//create the ItemAction
		Action action = new ItemAction();
		
		//the action
		action.setWhat("Item");
		
		//what is used
		action.setUsed(used);
		
		//user - the group
		action.setUserGroup(userGroup);
		//user - the position
		action.setUserPosition(userPosition);
		//target - the group
		action.setTargetGroup(targetGroup);
		//target - the position
		action.setTargetPosition(targetPosition);
		
		//will create according to who is doing the action
		if(userGroup){
			//character			
			//the name of the one doing the action
			action.setName(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getName());
			//the speed of the action
			//will set the speed of the action to the fastest possible, in this case the max value that can have
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}			
		}else{
			//the name of the enemy doing the action
			action.setName(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getName());
			//the speed of the action
			action.setSpeed(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getStatusValue(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
		}
		
		return action;
	}
	
	public Action magicAction(boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, String used, int where){
		//create the MagicAction
		Action action = new MagicAction();
		
		//the action
		action.setWhat("Magic");
		
		//what is used
		action.setUsed(used);
		
		//user - the group
		action.setUserGroup(userGroup);
		//user - the position
		action.setUserPosition(userPosition);
		//target - the group
		action.setTargetGroup(targetGroup);
		//target - the position
		action.setTargetPosition(targetPosition);
		
		//will create according to who is doing the action
		if(userGroup){
			//character			
			//the name of the one doing the action
			action.setName(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getName());
			//the speed of the action
			//will set the speed of the action to the fastest possible, in this case the max value that can have
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}			
		}else{
			//the name of the enemy doing the action
			action.setName(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getName());
			//the speed of the action
			action.setSpeed(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getStatusValue(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
		}
		
		return action;
	}
	
	public Action runAction(boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, int where){
		
		//create the RunAction
		Action action = new RunAction();
		
		//the action
		action.setWhat("Run");
		
		//what is used - nothing since is running
		action.setUsed(null);
		
		//user - the group
		action.setUserGroup(userGroup);
		//user - the position
		action.setUserPosition(userPosition);
		//target - the group
		action.setTargetGroup(targetGroup);
		//target - the position
		action.setTargetPosition(targetPosition);
		
		//will create according to who is doing the action
		if(userGroup){
			//character			
			//the name of the one doing the action
			action.setName(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getName());
			//the speed of the action
			//will set the speed of the action to the fastest possible, in this case the max value that can have
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatus(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()) != null){
				action.setSpeed(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatusCurrent(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
			}			
		}else{
			//the name of the enemy doing the action
			action.setName(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getName());
			//the speed of the action
			action.setSpeed(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getStatusValue(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getOrderFactor()));
		}
		
		return action;
	}
	
	public void testVictoryAndDefeat(){
		//will only get the new situation if the fight isn´t over
		if(!victory && !defeat){
			//call the method in the script that is responsible for the test of the battle
			Object[] test = FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript("testBattle", null);
				
			//get the values read to the ones that represent it
			victory = Boolean.parseBoolean(test[0].toString());
			defeat = Boolean.parseBoolean(test[1].toString());			
		}
	}
	
	public void refreshBattleSituation(){
		
		//test if the execution was stopped
		if(stop){
			//test if the battle is over
			testVictoryAndDefeat();
		}
		
		//begin all again
		option = 1;
		whoActs = 1;
		characterActs = 0;
		
		//before clearing the queue
		for(int i = 0; i < queue.size(); i++){
			if(queue.get(i).getName().equals("Defend")){
				//must treat according to who is callig it
				if(queue.get(i).isUserGroup()){
					//the player
					//test where the status is
					if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(queue.get(i).getUserPosition()).getStatus("PhyDef") != null){					
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(queue.get(i).getUserPosition()).getStatus("PhyDef").getSkill().getFeature().changeCurrent(-50);
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(queue.get(i).getUserPosition()).getStatus("MagDef").getSkill().getFeature().changeCurrent(-50);
					}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(queue.get(i).getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("PhyDef") != null){
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(queue.get(i).getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("PhyDef").getSkill().getFeature().changeCurrent(-50);
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(queue.get(i).getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("PhyDef").getSkill().getFeature().changeCurrent(-50);
					}		
				}else{
					//the enemy
					RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(queue.get(i).getUserPosition()).getStatus("PhyDef").getSkill().getFeature().changeCurrent(-50);
					RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(queue.get(i).getUserPosition()).getStatus("MagDef").getSkill().getFeature().changeCurrent(-50);
				}
			}
		}
		
		//clean the queue
		queue.clear();
	}
	
	public void keepFighting(){
		
		//test if the battle is over
		if(!victory && !defeat){
			
			//to keep getting or doing what is wanted
			switch(whoActs){
			case 1:{
				//get the actions of the heroes
				getCharacterCommand();
				break;
			}
			case 2:{
				//get the actions of the enemies
				getEnemiesCommand();			
				break;
			}			
			case 3:{				
				//sort the actions according to one parameter
				sortOrder();			
				break;
			}
			case 4:{				
				//execute all the actions
				executeActions();				
				break;
			}
			case 5:{				
				//get the result for now
				refreshBattleSituation();				
				break;
			}
			}		
		}else{
			//there was a victory or defeat, either way must finish the battle
			Game.battle = false;
			start = false;
			
			//if there was victory give the win to the player
			if(victory && !defeat){
				//get the experience gained
				int exp[] = getGained();
				//give the experience to the player
				RPGSystem.getRPGSystem().getPlayerGroup().groupExperience(exp[1], exp[0], exp[2], exp[3]);								
				//need to give money to the player
				RPGSystem.getRPGSystem().getPlayerGroup().gainMoney(exp[4]);
				//go back to the map
				
				//will remove all the models that are extra
				
				//to go back only need to put the model back
				//to go back only need to put the model back
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().addModelRootNode();
				
				//will move the camera back a little
				//RPGSystem.getRPGSystem().getCamera().moveFar(5);
				
				//make the camera go back to its original Z
				FactoryManager.getFactoryManager().getGraphicsManager().getRender().getCamera().getLocation().z = cameraZ;
				
				//refresh the camera
				FactoryManager.getFactoryManager().getGraphicsManager().getRender().getCamera().update();
		
				//to remove the characters of the player group				
				for(int i = 0; i < size + 1; i++){
					//remove one node
					FactoryManager.getFactoryManager().getResourceManager()
							.getObjectManager().getAll().get(
									RPGSystem.getRPGSystem().getPlayerGroup()
											.getCharacter(i).getIdentifier()).getPhysic()
							.removeFromParent();
				}
				
				//to remove the enemies
				for(int i = 0; i < RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length; i++){
					//remove one node
					FactoryManager.getFactoryManager().getResourceManager()
							.getObjectManager().getAll().get(
									RPGSystem.getRPGSystem().getBattleSystem()
											.getEnemyGroup()[0].getEnemy(i)
											.getName()).getPhysic()
							.removeFromParent();
				}
				//will move the camera back a little
				//RPGSystem.getRPGSystem().getCamera().moveNear(-4);

			}else if(!victory && defeat){
				//if there was defeat end the game
								
				//use only what is needed for the actions
				Event exit = new Event("exit","","",0,0);			
				//uses the singleton, to don´t need to use a vector to pass the events
				FactoryManager.getFactoryManager().getEventManager().registerEvent(exit);

			}else if(victory && defeat){
				//if both are true than is running away
				//go back to the map
				
				
				//to remove the characters of the player group				
				for(int i = 0; i < size + 1; i++){
					//remove one node
					FactoryManager.getFactoryManager().getResourceManager()
							.getObjectManager().getAll().get(
									RPGSystem.getRPGSystem().getPlayerGroup()
											.getCharacter(i).getIdentifier()).getPhysic()
							.removeFromParent();
				}
				
				//to remove the enemies
				for(int i = 0; i < RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length; i++){
					//remove one node
					FactoryManager.getFactoryManager().getResourceManager()
							.getObjectManager().getAll().get(
									RPGSystem.getRPGSystem().getBattleSystem()
											.getEnemyGroup()[0].getEnemy(i)
											.getIdentifier()).getPhysic()
							.removeFromParent();
				}
				
				//to go back only need to put the model back
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().addModelRootNode();
				
				//will move the camera back a little
				//RPGSystem.getRPGSystem().getCamera().moveFar(5);
				
				//make the camera go back to its original Z
				FactoryManager.getFactoryManager().getGraphicsManager().getRender().getCamera().getLocation().z = cameraZ;
				
				//refresh the camera
				FactoryManager.getFactoryManager().getGraphicsManager().getRender().getCamera().update();
		
				
			}			
		}
	}
		
	//method used to begin the battle
	public void start(){	
		
		//test if the battle requirements were loaded or not
		if(start && Game.battle){
			//already loaded and must keep the fight
			keepFighting();	
						
		}else{			
			//need to load the script only once
			//load the script responsible for the rules used in the battle
			FactoryManager.getFactoryManager().getScriptManager().getReadScript()
					.loadScript(
							FactoryManager.getFactoryManager().getRulesManager()
									.getRulesSet().getOnGoingPath(),
							FactoryManager.getFactoryManager().getRulesManager()
									.getRulesSet().getOnGoingFile());
			
			//refresh the characters status
			RPGSystem.getRPGSystem().getPlayerGroup().refreashCharactersValues();
			
			//will get the initial values for the character and the camera positions
			//cameraPosition = FactoryManager.getFactoryManager().getGraphicsManager().getRender().getCamera().getLocation();
			
			//get the original Z
			cameraZ = FactoryManager.getFactoryManager().getGraphicsManager().getRender().getCamera().getLocation().z;
			
			//System.out.println("Z: " + cameraZ);
			
			//get the quantity of characters in the player group
			size = (short)RPGSystem.getRPGSystem().getPlayerGroup().getQuantity();
						
			//if the quantity is bigger than how many can enter the battle
			if(size > FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle()){
				//then take the quantity that can enter
				size = (short)FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle();
			}
			
			//need to create the fight model for each character
			
			//the position used for the models will depend on the position of the character			
			//will hide the character for the battle duration			
			//will remove from the scene			
			//FactoryManager.getFactoryManager().getResourceManager().getScene().detachChildNamed("Character");
			
			FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().getPhysic().removeFromParent();
			
			//System.out.println(FactoryManager.getFactoryManager().getGraphicsManager().getRootNode().getChildren().size());
			
			//temporary objects for the position			
			float distance = 10.0f;
			float pos = distance / size;
			String name = "Name"; // will be the identifier of the character
			String identifier = "Model"; // will be the battle model			
			float scale = 0.0f; // will get from the battle model
			float posX = 0.0f; // will depend on the character position
			float posY = 0.0f; // will depend on the character position
			float posZ = 0.0f; // will depend on the character position
			
			//will load and position the models for the characters
			//get the initial positions
			posX = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().getPhysic().getLocalTranslation().getX();
			posY = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().getPhysic().getLocalTranslation().getY() + 2;
			posZ = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().getPhysic().getLocalTranslation().getZ() + 2;
			pos = 0.0f;
			
			//will bring the camera a little back for the fight
			RPGSystem.getRPGSystem().getCamera().moveNear(-5);
			
			//the loop to load the character of the players
			for(int i = 0; i < size + 1; i++){
				//will get the values needed
				name = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(i).getIdentifier();
				identifier = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(i).getBattleModel();
				scale = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(i).getBattleScale();
				
				//refresh the position
				posZ = posZ + pos;
				
				//will load the character
				FactoryManager.getFactoryManager().getResourceManager()
						.loadModel("NPC", name, identifier, scale, posX,
								posY, posZ, 0.0f, 90.0f, 0.0f, "Default",
								0.0f, 0.0f, 0.0f, "none", "none");
				
				//will get the new position
				pos = pos + 2;	
				
				//will put the model in idle animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(name).basicAnimation("BattleIdle");
				
				//System.out.println("Loaded character " + name);
				//System.out.println(posX + ";" + posY + ";" + posZ);
			}
					
			// will load and position the models for the enemies
			//get the initial positions
			posX = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(0).getIdentifier()).getPhysic().getLocalTranslation().getX() - 1.5f;
			posY = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(0).getIdentifier()).getPhysic().getLocalTranslation().getY();
			posZ = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(0).getIdentifier()).getPhysic().getLocalTranslation().getZ();
			
			pos = 0.0f;
			
			//the loop to load the enemy characters
			for(int i = 0; i < RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length; i++){
				//will get the values needed
				name = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getName();
				identifier = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getBattleModel();				
				scale = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getBattleScale();
										
				//refresh the position
				posZ = posZ + pos;
				
				//will load the enemy
				FactoryManager.getFactoryManager().getResourceManager()
						.loadModel("Enemy", name, identifier, scale, posX,
								posY, posZ, 0.0f, 270.0f, 0.0f,  "Default",
								0.0f, 0.0f, 0.0f,"none","none");
				
				//will get the new position
				pos = pos + 1.0f;	
								
				//will put the model in idle animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(name).basicAnimation("BattleIdle");
				
				//System.out.println("Loaded character " + name);
				//System.out.println(posX + ";" + posY + ";" + posZ);
			}			
			
			//reset the victory and the defeat
			victory = false;
			defeat = false;
			
			//initialize the controllers for the player			
			option = 1;
			whoActs = 1;
			characterActs = 0;
			
			//clean the queue
			queue.clear();
			
			//can begin the fight
			start = true;
		}
				
	}
	
	public void getCharacterCommand(){
		
		//since the loop to draw ins´t here, then must use objects to control every change		
		switch(option){
		case 1:{
			//need to test if the character is alive
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(characterActs).getStatus("HP") != null){
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(characterActs).getStatusCurrent("HP") != 0){
					// must draw the battle menu for the character
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showBattleMenu();
					//change the option to choose the action
					option = 2;
				}else{
					//this character doesn´t play now because is dead
					option = 3;
				}
			}else{
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(characterActs).getCharacterClass().getStatusCurrent("HP") != 0){
					// must draw the battle menu for the character
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showBattleMenu();
					//change the option to choose the action
					option = 2;
				}else{
					//this character doesn´t play now because is dead
					option = 3;
				}
			}

			break;
		}
		case 2:{
			//must choose the action, wait for player entry, ask the menu to change it
						
			break;
		}
		
		case 3:{
			//the action was created and must pass to the next character
			//if all is true then increase the position of the character if can and initialize the rest
			//when the size of the characters is over then stop getting the characters command
						
			//test if has more characters in the group
			if(size > characterActs){
				//go to the next character
				characterActs++;
				//begin with the options again
				option = 1;				
			}else{
				System.out.println("Character Over");
				//pass the action to the enemies
				whoActs = 2;				
			}			
			break;
		}
		}
	}
	
	public void getEnemiesCommand(){
		
		//for each enemy in the group		
		for(int i = 0; i < RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length; i++){
			//if the enemy is alive then can use the strategy
			if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getStatusCurrent("HP") != 0){
				//randomize an action
				//ask for the action
				ene = FactoryManager.getFactoryManager().getAiManager()
						.getEnemyAction(
								rand.nextInt(100),
								RPGSystem.getRPGSystem().getBattleSystem()
										.getEnemyGroup()[0].getEnemy(i)
										.getStrategy());
				
				
				//create the action according to its type
				if(ene.getAction().equals("Attack")){
					//create the action
					createAction(ene.getAction(), false, i, true, rand.nextInt(size + 1), "", 0);
					
				}else if(ene.getAction().equals("Defend")){
					createAction(ene.getAction(), false, i, false, i, "", 0);
					
				}else if(ene.getAction().equals("Item")){
					
					//must get the item and test if its a damage or support type				
					//temporary item to use
					Item item = new Item();
					
					//get the item
					item.create(ene.getName());
					
					//test the item type of
					if(item.getUsable().getFeature().getTypeOf().get(1).equals("Damage")){
						createAction(ene.getAction(), false, i, false, i, ene.getName(), 0);
					}else{
						createAction(ene.getAction(), false, i, false, i, ene.getName(), 0);	
					}
				}else if(ene.getAction().equals("Magic")){
					//get the type of the magic, if is damage or support
					if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getMagic(ene.getName()).getMagic().getFeature().getTypeOf().get(1).equals("Damage")){
						//damage
						createAction(ene.getAction(), false, i, true, rand.nextInt(size), ene.getName(), 0);
					}else{
						//support
						createAction(ene.getAction(), false, i, false, rand.nextInt(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length), ene.getName(), 0);	
					}
				}else if(ene.getAction().equals("Run")){
					createAction(ene.getAction(), false, i, false, i, "", 0);
					
				}
			}			
		}
		
		System.out.println("Enemies Over");
		//must pass to sort the order after all the actons were created
		whoActs = 3;
	}
	
	public void sortOrder(){
		//call the script to make the sort (?) needs to specify the Feature used for the order
		//FactoryManager.getFactoryManager().getScriptManager().getReadScript().executeScript("battleOrder");
		
		//temporary objects
		Action temp;
		Action test;
			
		//use a bubble sort to organize
		for(int i = 0; i < queue.size() - 1; i++){
			for(int k = 0; k < queue.size() - 1; k++){
				//get the actions
				temp = queue.get(k);
				test = queue.get(k+1);
								
				//test then
				if(temp.getSpeed() < test.getSpeed()){
					//swap
					queue.set(k, test);
					queue.set(k+1, temp);
				}
				
			}			
		}
		
		//testing
		//for(int t = 0; t < queue.size(); t++){
		//	System.out.println(queue.get(t).getName());
		//	System.out.println(queue.get(t).getWhat());
		//	System.out.println(queue.get(t).getSpeed());
		//}
		
		//needs to tell that must begin from the 0 position
		i = 0;
		
		System.out.println("Sort Over");
		
		//after sorting must go on
		whoActs = 4;
	}
	
	/*	public void swap(int i, int j){
		Action temp = queue.get(i);
		queue.set(i, queue.get(j));
		queue.set(j, temp);		
	}
	if(queue.get(k).getSpeed() > queue.get(k+1).getSpeed()){
		//swap
		temp = queue.get(k);
		queue.set(k, queue.get(k+1));
		queue.set(k+1, temp);
	}

	 
	 */
	
	public void executeAction(Action action){
		//test the action
		if(action.getWhat().equals("Attack")){
			// if attack			
			//get the action
			AttackAction temp = (AttackAction)action;
			if(isTargetAlive(temp)){
				//call the place to attack
				computeChanges(temp.isUserGroup(), temp.getUserPosition(), temp.isTargetGroup(), temp.getTargetPosition(), "HP", "physicalDamage");
			}
			
		}else if(action.getWhat().equals("Defend")){
			//if defend			
			DefendAction temp = (DefendAction)action;

			//must treat according to who is callig it
			if(temp.isUserGroup()){
				//the player
				
				//test where the status is
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getStatus("PhyDef") != null){					
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getStatus("PhyDef").getSkill().getFeature().changeCurrent(50);
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getStatus("MagDef").getSkill().getFeature().changeCurrent(50);
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("PhyDef") != null){
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("PhyDef").getSkill().getFeature().changeCurrent(50);					
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("MagDef").getSkill().getFeature().changeCurrent(50);
				}
				
				//increase the PhyDef and MagDef of the player, when the turn is over then decrease it again
			}else{
				//the enemy
				RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getUserPosition()).getStatus("PhyDef").getSkill().getFeature().changeCurrent(50);
				RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getUserPosition()).getStatus("MagDef").getSkill().getFeature().changeCurrent(50);
			}
			
		}else if(action.getWhat().equals("Item")){
			//if item			
			ItemAction temp = (ItemAction)action;

			//must treat according to who is callig it
			if(temp.isUserGroup()){
				//test if the target is alive or if the item is of the type to revive
				if(isTargetAlive(temp) || RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItem(temp.getUsed()).getUsable().getFeature().getTypeOf().get(2).equals("Life")){
					//the player
					RPGSystem.getRPGSystem().getPlayerGroup().getBag().useItem(temp.getUsed(), temp.isTargetGroup(), temp.getTargetPosition());
				}
			}else{
				//the enemy
				//RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].
			}				
			
		}else if(action.getWhat().equals("Magic")){
			//if magic			
			MagicAction temp = (MagicAction)action;
			
			//must treat according to who is callig it
			if(temp.isUserGroup()){

				//test if the character or the class has
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getMagic(temp.getUsed()) != null){
					//test if the target is alive or if the magic is of the type to revive
					if(isTargetAlive(temp)){						
						//the player
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).useFeature(temp.getUsed(),temp.isUserGroup(),temp.getUserPosition(),temp.isTargetGroup(),temp.getTargetPosition());
					}else if((RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getMagic(temp.getUsed()).getMagic().getFeature().getTypeOf().get(2) != null && RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getMagic(temp.getUsed()).getMagic().getFeature().getTypeOf().get(2).equals("Life"))){
						//the player
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).useFeature(temp.getUsed(),temp.isUserGroup(),temp.getUserPosition(),temp.isTargetGroup(),temp.getTargetPosition());						
					}
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(temp.getUsed()) != null){
					
					//test if the target is alive or if the magic is of the type to revive
					if(isTargetAlive(temp)){
						//the player						
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).useClassFeature(temp.getUsed(),temp.isUserGroup(),temp.getUserPosition(),temp.isTargetGroup(),temp.getTargetPosition());
						
					}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(temp.getUsed()).getMagic().getFeature().getTypeOf().size() > 2  && RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(temp.getUsed()).getMagic().getFeature().getTypeOf().get(2).equals("Life")){
						//the player						
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).useClassFeature(temp.getUsed(),temp.isUserGroup(),temp.getUserPosition(),temp.isTargetGroup(),temp.getTargetPosition());
					}
				}
			}else{
				if(isTargetAlive(temp)){				
					//the enemy
					RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getUserPosition()).useFeature(temp.getUsed(),temp.isUserGroup(),temp.getUserPosition(),temp.isTargetGroup(),temp.getTargetPosition());
				}
			}
				
				
		}else if(action.getWhat().equals("Run")){
			//ir run			
			RunAction temp = (RunAction)action;

			//Try the running
			int playerGroup = 0;
			int enemyGroup = 0;
			
			//get everyone in the player group
			for(short i = 0; i < size; i++){				
				//test where the status is
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter((int)i).getStatus("Speed") != null){					
					playerGroup = playerGroup + RPGSystem.getRPGSystem().getPlayerGroup().getCharacter((int)i).getStatusCurrent("Speed");
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter((int)i).getCharacterClass().getFeatureNetwork().getStatus("Speed") != null){					
					playerGroup = playerGroup + RPGSystem.getRPGSystem().getPlayerGroup().getCharacter((int)i).getCharacterClass().getStatusCurrent("Speed");
				}
			}
			
			//make the division for the number
			playerGroup = playerGroup/size;
			
			//get everyone in the enemy group
			for(int j = 0; j < RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length; j++){
				//get the value
				enemyGroup = enemyGroup + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(j).getStatusCurrent("Speed");
			}
			
			//make the division in the enemy group
			enemyGroup = enemyGroup/RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length;
			
			//test the luck of the action
			int run = rand.nextInt(100);
			
			//must treat according to who is callig it
			if(temp.isUserGroup()){
				//the player			
				//get the percentage
				int percentage = (int)(50 + (playerGroup * 1.5) - (enemyGroup * 1.2));
				
				//test if was able to escape
				if(run < percentage){
					//just end the fight and escape
					victory = true;
					defeat = true;
				}
			}else{
				//the enemy				
				//get the percentage
				int percentage = (int)(50 + (enemyGroup * 1.5) - (playerGroup * 1.2));
				
				//test if was able to escape
				if(run < percentage){
					//if its the enemy, then remove the Enemy from that group
					RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].removeEnemy(temp.getName());					
					//test if the enemy that runned was alone
					if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getSize() == 0){
						//then finish the battle
						victory = true;
						defeat = true;
					}
				}
			}
		}		
	}
	
	public void executeActions(){
		
		//execute one action and make a test
				
		//used to get the action	
		temp = null;
				
		//get the action
		if( i < queue.size()){
			temp = queue.get(i);
		}
			
		//test if the user is alive
		if(temp != null && isUserAlive(temp) && !showAnimation && temp != moment){
			//execute the action
			executeAction(temp);
			//show the animation
			showAnimation(temp);
		}

		//if the animation already ended
		if(!showAnimation && !showEffect){	
						
			//test if the target is dead
			if(moment != null && isTargetAlive(moment) == false && temp != null){				
				//treat if died
				showDeathAnimation(moment);				
			}			
						
			if(!showAnimation && !showEffect){
				//test if the battle has ended
				testStopExecuting();				
			}			
			
			//increase the count
			if(i <= queue.size()){
				i++;
			}
		}
		
		if(stop || (!showAnimation && !showEffect )){			
			if(i > queue.size()){				
			//change who acts
			whoActs++;			
			}
		}
				
	}
	
	public void testStopExecuting(){
		
		//will only get the new situation if the fight isn´t over
		if(!victory && !defeat){
			//call the method in the script that is responsible for the test of the battle
			Object[] test = FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript("testBattle", null);
						
			//get the values read to the ones that represent it
			boolean vi = Boolean.parseBoolean(test[0].toString());
			boolean de = Boolean.parseBoolean(test[1].toString());	
			
			if(vi || de){
				stop = true;
			}
		}
		
	}
	
	public void showDeathAnimation(Action action){
		//will search for the one wanted, temporary objects
		String identifier;
		
		//get the model that died
		if(action.isTargetGroup()){
			//player
			identifier = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getTargetPosition()).getIdentifier();			
		}else{
			//enemy
			identifier = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getTargetPosition()).getName();			
		}
		
		//play the animation of death
		FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(identifier).battleBasicAnimation("Death", 0);
		showAnimation = true;
		
	}
	
	public void showAnimation(Action action){
		
		System.out.println("Show one animation " + action.getName());
		
		//test the action
		if(action.getWhat().equals("Attack")){
			// if attack		
			//player group
			if(action.isUserGroup()){
				
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getIdentifier();
				
				//its the attack animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleBasicAnimation("Attack", 0);
								
			}else{
				//enemy group
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getName();
				
				//its the attack animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleBasicAnimation("Attack", 0);
								
			}
			
			//tells to wait until the animation is over
			showAnimation = true;
			
		}else if(action.getWhat().equals("Defend")){
			//if defend			
			//player group
			if(action.isUserGroup()){
				
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getIdentifier();
				
				//its the attack animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleBasicAnimation("Defend", 1);
				
			}else{
				//enemy group
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getName();
				
				//its the attack animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleBasicAnimation("Defend", 1);
				
			}
			
			//tells to animate and wait for it
			showAnimation = true;
			//tells that must begin then go on
			continuos = true;
			
		}else if(action.getWhat().equals("Item")){
			//if item

			//will get the item
			Item item = new Item();

			//create a temporary item
			item.create(action.getUsed());
			
			//player group
			if(action.isUserGroup()){
				
				//get the identifier of the model			
				String id = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getIdentifier();
								
				//temporary objects
				String animation = null;
				String type = null;
				String name = null;
				
				//get the data
				animation = item.getAnimations().get(id);
				type = item.getEffectType();
				name = item.getEffectName();
								
				//call the animation, don´t repeat the movements
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleAnimation(animation, 0);
				
				//call the effect, if has using particles
				if(type != null && type.equals("Particle")){
					FactoryManager.getFactoryManager().getEventManager()
					.getPhysic().getParticleSystem().createParticle(
							"res/particles/", "particles.xml", name);
					
					//tells to wait for the particle
					showEffect = true;
				}				
				
				//tells to show the animation
				showAnimation = true;
				
			}else{
				//enemy group
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getName();
				
				//temporary objects
				String animation = null;
				String type = null;
				String name = null;
				
				//get the data
				animation = item.getAnimations().get(id);
				type = item.getEffectType();
				name = item.getEffectName();
								
				//call the animation, don´t repeat the movements
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleAnimation(animation, 0);
				
				// call the effect, if has using particles
				if(type != null && type.equals("Particle")){
					FactoryManager.getFactoryManager().getEventManager()
					.getPhysic().getParticleSystem().createParticle(
							"res/particles/", "particles.xml", name);
					
					//tells to wait for the particle
					showEffect = true;
				}				
				
				//tells to show the animation
				showAnimation = true;
				
			}		
			
		}else if(action.getWhat().equals("Magic")){
			//if magic			
					
			if(action.isUserGroup()){
								
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getIdentifier();
				String animation = null;
				String type = null;
				String name = null;
				//if the character has the magic
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getMagic(action.getUsed()) != null){
					//animation used for this character, use the identifier
					animation = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getMagic(action.getUsed()).getAnimations().get(id);					
					//effect type
					type = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getMagic(action.getUsed()).getEffectType();
					//effect name
					name = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getMagic(action.getUsed()).getEffectName();
										
				}else if (RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(action.getUsed()) != null) {
					//if the class has the magic
					// animation used for this character, use the identifier
					animation = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(action.getUsed()).getAnimations().get(id);					
					//effect type
					type = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(action.getUsed()).getEffectType();
					//effect name
					name = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getCharacterClass().getFeatureNetwork().getMagic(action.getUsed()).getEffectName();
					
				}
				
				//call the animation, don´t repeat the movements
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleAnimation(animation, 0);
				
				//call the effect, if has using particles
				if(type != null && type.equals("Particle")){
					FactoryManager.getFactoryManager().getEventManager()
					.getPhysic().getParticleSystem().createParticle(
							"res/particles/", "particles.xml", name);
					
					//tells to wait for the particle
					showEffect = true;
				}				
				
				//tells to show the animation
				showAnimation = true;
				
			}else{
				//enemy group
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getName();				
				String animation = null;
				String type = null;
				String name = null;
				
				//if the enemy has the magic
				if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()) != null){
					//animation used for this character, use the identifier
					animation = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getMagic(action.getUsed()).getAnimations().get(id);					
					//effect type
					type = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getMagic(action.getUsed()).getEffectType();
					//effect name
					name = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getMagic(action.getUsed()).getEffectName();
					
				}
								
				//call the animation, don´t repeat the movements
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleAnimation(animation, 0);
				
				//call the effect, if has using particles
				if(type != null && type.equals("Particle")){
					FactoryManager.getFactoryManager().getEventManager()
					.getPhysic().getParticleSystem().createParticle(
							"res/particles/", "particles.xml", name);
					
					//tells to wait for the particle
					showEffect = true;
				}				
				
				//tells to show the animation
				showAnimation = true;
				
			}		
			
			
		}else if(action.getWhat().equals("Run")){
			//ir run			
			//player group
			if(action.isUserGroup()){
				
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(action.getUserPosition()).getIdentifier();
				
				//its the attack animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleBasicAnimation("Run", 1);
				
			}else{
				//enemy group
				//will animate this one			
				String id = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(action.getUserPosition()).getName();
				
				//its the attack animation
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(id).battleBasicAnimation("Run", 1);
				
			}
			
			//tells to animate and wait for it
			showAnimation = true;
			//tells that must begin then go on
			continuos = true;
			
		}
		
		//to make sure the animation is controlled outside
		moment = action;
		
	}
	
	public boolean isUserAlive(Action temp){
		//test if the target is alive
		if(temp.isUserGroup()){
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getStatus("HP") != null){	
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getStatusCurrent("HP") != 0){
					return true;
				}else{
					return false;
				}				
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getStatus("HP") != null){
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getUserPosition()).getCharacterClass().getFeatureNetwork().getStatusCurrent("HP") != 0){
					return true;
				}else{
					return false;
				}
			}					
		}else{
			if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getUserPosition()).getStatusCurrent("HP") != 0){
				//System.out.println("Alive");
				//System.out.println(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getUserPosition()).getStatusCurrent("HP"));
				return true;
			}else{
				//System.out.println("Dead");
				//System.out.println(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getUserPosition()).getStatusCurrent("HP"));
				return false;
			}
		}		
		//won´t be called
		return false;		
	}
	
	public boolean isTargetAlive(Action temp){
		//test if the target is alive
		if(temp.isTargetGroup()){
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getTargetPosition()).getStatus("HP") != null){	
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getTargetPosition()).getStatusCurrent("HP") != 0){
					return true;
				}else{
					return false;
				}				
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getTargetPosition()).getCharacterClass().getFeatureNetwork().getStatus("HP") != null){
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(temp.getTargetPosition()).getCharacterClass().getFeatureNetwork().getStatusCurrent("HP") != 0){
					return true;
				}else{
					return false;
				}
			}					
		}else{
			if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getTargetPosition()).getStatusCurrent("HP") != 0){
				//System.out.println("Enemy Target");
				//System.out.println(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getTargetPosition()).getStatusCurrent("HP"));
				return true;
			}else{
				//System.out.println("Enemy Target");
				//System.out.println(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(temp.getTargetPosition()).getStatusCurrent("HP"));
				return false;
			}
		}		
		//won´t be called
		return false;
	}
		
	public void createAction(String type, boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, String used, int where){
		//create an action
		Action action = null;
		
		//create the action according to its type
		if(type.equals("Attack")){
			//create according to the rule used for the attack
			if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getAttackPhysical().equals("simple")){
				//create a simple attack
				action = simpleAttack(userGroup,userPosition,targetGroup,targetPosition,where);
			}else if (FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getAttackPhysical().equals("simple")){
				//create a combo attack
				comboAttack(action,userGroup,userPosition,targetGroup,targetPosition,where);
			}			
		}else if(type.equals("Defend")){
			//call the method to create the Defend action
			action = defendAction(userGroup,userPosition,targetGroup,targetPosition,where);
		}else if(type.equals("Item")){
			//call the method to create the Item action
			action = itemAction(userGroup, userPosition, targetGroup, targetPosition, used, where);
		}else if(type.equals("Magic")){
			//call the method to create the Magic action
			action = magicAction(userGroup, userPosition, targetGroup, targetPosition, used, where);
		}else if(type.equals("Run")){
			//call the method to create the Run action
			action = runAction(userGroup, userPosition, targetGroup, targetPosition, where);
		}		
				
		//put the action in the queue
		queue.add(action);		
	}
	
	//use this to calculate the value, them return a value
	public int callScript(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition, ChangeFeature change, String method){

		//create the array used for the test
		Object[] objs = new Object[6];
		objs[0] = userGroup;
		objs[1] = userPosition;
		objs[2] = targetGroup;
		objs[3] = targetPosition;
		objs[4] = change;
		objs[5] = this;
		
		//temporary object, to receive a value
		Object[] receive = null;
		//map the array of objects to the script, always mapping again to change the Feature values according
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().mapToTheScript("FeatureAttribute",objs);
		//test purpose
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().loadScript("res/scripts/rules/", "ongoing.js");
		//test send the values to the script
		receive = FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript(method, objs);
		
		//return the value found		
		return (int)Float.parseFloat(receive[0].toString());						
	}
		
	public void computeChanges(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition, String status, String method){		
		//calls the script to execute the rule for the use, will receive a new value, if has a rule for it
		int value = callScript(userGroup, userPosition, targetGroup, targetPosition, null, method);		
		
		//can´t give a 0 damage and can´t have a minus damage
		if(value <= 0)
			value = 1;
				
		//tells to change
		changeCurrentValue(targetGroup, targetPosition, status, value);
	}
		
	public void changeCurrentValue(boolean group, int position, String status, int value){
		// test where to calculate
		if(group){
			//player
			// test where the status is
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(status) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(status).getSkill().getFeature().changeCurrent(-value);			
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(status) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(status).getSkill().getFeature().changeCurrent(-value);
			}
		}else{
			//enemy
			RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(status).getSkill().getFeature().changeCurrent(-value);
		}
	}
	
	public int[] getGained(){
		//temporary object
		int gained[] = new int[5];
		//class
		gained[0] = 0;
		//character
		gained[1] = 0;
		//item
		gained[2] = 0;
		//feature
		gained[3] = 0;
		//get the money
		gained[4] = 0;
	
		
		//get all the experience gained for each type
		for(int i = 0; i < RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length; i++){
			//get the experience for each type
			//class
			gained[0] = gained[0] + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getExperienceClassGained();
			//character
			gained[1] = gained[1] + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getExperienceCharacterGained();
			//item
			gained[2] = gained[2] + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getExperienceItemGained();
			//feature
			gained[3] = gained[3] + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getExperienceFeatureGained();
			//money
			gained[4] = gained[4] + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getMoneyGained();
		}
		
		//pass the experience that was gained
		return gained;
	}
	
	public void getExperienceCharacter(){
		
	}
	
	public void getExperienceItem(){
		
	}
	
	public void getExperienceFeature(){
		
	}	
	
	public Action getMomentAction(){
		return queue.get(i);
	}
}
 
