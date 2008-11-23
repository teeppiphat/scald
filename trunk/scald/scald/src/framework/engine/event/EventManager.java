package framework.engine.event;

import java.util.Vector;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;
import framework.engine.physic.Physic;
import framework.engine.resource.object.NPC;

/**
 * Responsible for having all the events and treating them, the engine is
 * oriented by events
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class EventManager {

	/**
	 * Atribute responsible for the physic
	 */
	private Physic physic;

	private Vector<Event> events;

	private boolean waitConversation = false;
	
	private boolean conversationFinished = false;
	//only for test

	private int maxParts;

	private int currentPart;
	
	private Event talk;

	/**
	 * Constructor of the class, it initialize the objects
	 */
	public EventManager() {

		// initialize the objects		
		physic = new Physic();
		events = new Vector<Event>();
		maxParts = 2;
		currentPart = 0;
		talk = null;
	}

	// method responsible for creating and registering the events

	// receive an event that was created by the input and resolve it
	// needs to solve all the events that were created and registrated
	// in the pool of events
	public void treatEvents() {

		if (events.size() > 0) {
			// identify the event
			Event eve = events.remove(0);


			if (eve.event.equals("exit")) {
				exitEvent();
			}
			
			//the talk in the event manager
			if(waitConversation){								
				//if the button was pressed to go on
				if(eve.event.equals("accept")){
					acceptEvent();
				}				
			}
			
			if(Game.cutScene){
				//will treat cut-scene events
				//more used for when there´s a move event to treat
				if (eve.event.equals("back")) {
					backEvent();				
				}

				if (eve.event.equals("accept")) {
					acceptEvent();
				}				
			}else if(Game.battle){
				
				//must treat only the battle events
				if(RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().isShowAnimation()){
					//will test if the animation is over					
					if(RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().getMoment() != null){

						// will search for the one wanted
						String identifier;
						int position;
						boolean group;

						group = RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().getMoment().isUserGroup();
						position = RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().getMoment().getUserPosition();

						if(group){
							//player
							identifier = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getIdentifier();

						}else{
							//enemy
							identifier = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getName();
							
						}
						
						//FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(identifier).getJoint().getRepeatType() == 1
						if(RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().isContinuos()){
														
							// will tell to go to the next animation							
							RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().setContinuos(false);
							RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().setShowAnimation(false);
						}else{
							
							//need to get the max time
							float max = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(identifier).getJoint().getMaxTime();
							//need to get the current time
							float current = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(identifier).getJoint().getCurrentTime();

							if(!(current < max)){

								//will put the character to play its battle animation
								FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(identifier).battleBasicAnimation("BattleIdle", 1);
								//will tell to go to the next animation
								RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().setShowAnimation(false);
							}else{
								
								int test = (int)(current*100);
								int test2 = (int)(max*100);
								
								if((test >= test2 )){
									//will put the character to play its battle animation
									FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(identifier).battleBasicAnimation("BattleIdle", 1);
									//will tell to go to the next animation
									RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().setShowAnimation(false);
								}
							}
						}
					}
				}
				
			}else{
				//outside of battle, in the world
				
				//if begins a battle
				if(eve.event.equals("script")){
					scriptEvent(eve);
				}
				
				if (eve.event.equals("run")) {
					runEvent();
				}

				if (eve.event.equals("back")) {
					backEvent();				
				}

				if (eve.event.equals("accept")) {
					acceptEvent();
				}

				if (eve.event.equals("menu")) {
					menuEvent();
				}

				if (eve.event.equals("move")) {

					if (eve.affected.equals("character")) {

						//must know what moves, in a way, must know the state at the moment, if its in the menu
						//if its in the map, path type of free type

						//ask the RPGSystem to what must do, and the RPGSystem will say what to do

						// if the menu is being show don´t move
						if (Game.menu) {


						}//move according to the walk pattern
						else {

							// true is free type movement
							if (RPGSystem.getRPGSystem().getMap().getMapType()) {

								// get the y of the character
								RPGSystem.getRPGSystem().getCamera().setTemp(FactoryManager.getFactoryManager()
										.getResourceManager().getObjectManager()
										.getCharacter().getPhysicPosition().y);							

								if (eve.direction.equals("still")){
									//make character play idle animation
									FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getCharacter().basicAnimation("WorldIdle");
								}								
								
								if (eve.direction.equals("up")) {
									//move the character
									FactoryManager.getFactoryManager()
									.getResourceManager()
									.getObjectManager().getCharacter()
									.moveUp();
								}

								if (eve.direction.equals("down")) {
									//move the character
									FactoryManager.getFactoryManager()
									.getResourceManager()
									.getObjectManager().getCharacter()
									.moveDown();
								}

								if (eve.direction.equals("left")) {
									//move the character
									FactoryManager.getFactoryManager()
									.getResourceManager()
									.getObjectManager().getCharacter()
									.moveLeft();								
								}

								if (eve.direction.equals("right")) {
									//move the character
									FactoryManager.getFactoryManager()
									.getResourceManager()
									.getObjectManager().getCharacter()
									.moveRight();								
								}

								//test if the y changed, if changed then must change the y of the camera
								RPGSystem.getRPGSystem().getCamera().changeY(
										FactoryManager.getFactoryManager()
										.getResourceManager()
										.getObjectManager().getCharacter()
										.getPhysicPosition().y);

								//in the free pattern, must know if moves through a portal,
								//if moves change map, according to the portal

								// test if passed a portal, don´t care about direction
								RPGSystem.getRPGSystem().getMap().enterPortal(
										FactoryManager.getFactoryManager()
										.getResourceManager()
										.getObjectManager().getCharacter()
										.getPhysicPosition().x,
										FactoryManager.getFactoryManager()
										.getResourceManager()
										.getObjectManager().getCharacter()
										.getPhysicPosition().y,
										FactoryManager.getFactoryManager()
										.getResourceManager()
										.getObjectManager().getCharacter()
										.getPhysicPosition().z);

							}// else is Path type movement
							else {

								// move in the map according to the direction

								if (eve.direction.equals("up")) {
									RPGSystem.getRPGSystem().getMap()
									.placeCharacterPath(0);
								}

								if (eve.direction.equals("down")) {
									RPGSystem.getRPGSystem().getMap()
									.placeCharacterPath(1);
								}

								if (eve.direction.equals("left")) {
									RPGSystem.getRPGSystem().getMap()
									.placeCharacterPath(2);
								}

								if (eve.direction.equals("right")) {
									RPGSystem.getRPGSystem().getMap()
									.placeCharacterPath(3);
								}
							}
						}
					}

					if (eve.affected.equals("camera")) {

						if (eve.direction.equals("up")) {					
							//RPGSystem.getRPGSystem().getCamera().rotateY(true);
							RPGSystem.getRPGSystem().getCamera().canRotate(true, 'Y');
						}

						if (eve.direction.equals("down")) {					
							//RPGSystem.getRPGSystem().getCamera().rotateY(false);
							RPGSystem.getRPGSystem().getCamera().canRotate(false, 'Y');
						}

						if (eve.direction.equals("left")) {
							//RPGSystem.getRPGSystem().getCamera().rotateX(false);	
							RPGSystem.getRPGSystem().getCamera().canRotate(false, 'X');
						}

						if (eve.direction.equals("right")) {					
							//RPGSystem.getRPGSystem().getCamera().rotateX(true);
							RPGSystem.getRPGSystem().getCamera().canRotate(true, 'X');
						}

						if (eve.direction.equals("far")) {						
							//RPGSystem.getRPGSystem().getCamera().rotateZ(true);
							RPGSystem.getRPGSystem().getCamera().canRotate(true, 'Z');
						}

						if (eve.direction.equals("near")) {						
							//RPGSystem.getRPGSystem().getCamera().rotateZ(false);
							RPGSystem.getRPGSystem().getCamera().canRotate(false, 'Z');
						}
					}
				}
			}
		}

	}

	public void exitEvent(){
		//use the flag that indicates to finish the game
		Game.running = true;
	}

	public void runEvent(){
		// change the flag of the character, if true then false, else true				
		FactoryManager.getFactoryManager().getResourceManager()
		.getObjectManager().getCharacter().run();
	}

	public void backEvent(){

		//must be in the menu or in a choice				
		if(Game.menu){

			//return one menu			

			//close the second menu
			FactoryManager.getFactoryManager().getGraphicsManager()
			.getGui().closeSecondaryMenu();	

			//RPGSystem.getRPGSystem().getMenu().changeText("s was pressed");

		}else{
					
			// create the particle - only for test
			FactoryManager.getFactoryManager().getEventManager()
			.getPhysic().getParticleSystem().createParticle(
					"res/particles/", "particles.xml", "Line");
		}
	}

	public void menuEvent(){
		//the menu can only be opened if its in the world and not
		//in battle, in battle the menu is different

		//calls the menu when in the game, but only if the menus
		//ins´t in the screen
		if (Game.menu == true) {
			//close the menu
			FactoryManager.getFactoryManager().getGraphicsManager()
			.getGui().closePrimaryMenu();

			//close the second menu
			FactoryManager.getFactoryManager().getGraphicsManager()
			.getGui().closeSecondaryMenu();	

			//FactoryManager.getFactoryManager().getGraphicsManager()
			//		.getGui().closeBattleMenu();
			//set to closed
			Game.menu = false;
		} else {
			//set to created
			Game.menu = true;

			//show the menu
			FactoryManager.getFactoryManager().getGraphicsManager()
			.getGui().showPrimaryMenu();

			//show the conversation box
			//FactoryManager.getFactoryManager().getGraphicsManager()
			//		.getGui().showConversationBox();

			//show the battle menu
			//FactoryManager.getFactoryManager().getGraphicsManager()
			//		.getGui().showBattleMenu();			

			//change the current text
			//load the script
			FactoryManager.getFactoryManager().getScriptManager()
			.getReadScript().loadScript("res/scripts/",
			"npc.js");

			//ask fot the chat needed
			RPGSystem.getRPGSystem().getMenu().changeText(
					FactoryManager.getFactoryManager()
					.getScriptManager().getReadScript()
					.getChat("talk", currentPart));			
		}
	}
		
	public void scriptEvent(Event eve){
		
		//will treat an script Event
		//System.out.println("Direction: " + eve.direction);
		//System.out.println("Affected: " + eve.affected);
		//System.out.println("Extra: " + eve.getExtra());
		//System.out.println("Extra2: " + eve.getExtra2());
		
		//will get the event
		if(talk == null){
			//get the event
			talk = eve;
			//tell to wait the conversation
			waitConversation = true;
			conversationFinished = false;
		}
		
		//will load the script wanted
		FactoryManager.getFactoryManager().getScriptManager().getReadScript()
				.loadScript(talk.direction, talk.affected);
		
		//will map things used for the event
		//like the number to indicate if it was a collision or a talk
		//like the String to tell with who collidied with, e.g. Fire ball, character
		
		//temporary objects	
		Object objects[] = null;
		Object pass[] = new Object[5];
				
		//put what is needed in the array
		//the name of the one with who happened
		pass[0] = talk.getExtra();
		//the type of the one
		pass[1] = talk.getExtra2();
		//the type of event, talk or collision
		pass[2] = talk.getPosX();
		//if is talk pass what is needed
		//current talk
		pass[3] = talk.getExtra3();
		//current part
		pass[4] = talk.getExtra4();		
		
		//map the array of objects to the script, always mapping again to change the Feature values according
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().mapToTheScript("GameEvent",pass);
		
		//will tell to execute it
		objects = FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript("main", pass);
				
		//test if received anything
		if(objects != null){
			//will treat what received

			//if battle
			if(objects[0].toString().equals("battle")){

				//will remove the model from the world
				FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(talk.getExtra()).getPhysic().removeFromParent();

				//will create the array to pass the enemies
				String[] enemies = new String[objects.length - 1];

				//will put all the enemies in the array
				for(int ene = 1; ene < objects.length; ene++){
					//get the enemy
					enemies[ene - 1] = objects[ene].toString();				
				}			

				//will create the enemy group
				RPGSystem.getRPGSystem().getBattleSystem().createEnemyGroup(enemies);

				//will begin the battle
				Game.battle = true;

			}else if(objects[0].toString().equals("talk")){
				//temporary object
				int part;
				int talking;
				NPC npc;
				
				//will get the NPC in question
				npc = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getNPC(talk.getExtra());
								
				//get the values
				talking = Integer.parseInt(objects[1].toString());
				part = Integer.parseInt(objects[2].toString());
								
				//test the part, if the part is greater then has another part
				if(part > npc.getTalkPart()){
					npc.setTalkPart(part);
				}else{
					// test the talk, if the talk is greater then move to the
					// next talk
					if(talking > npc.getTalkCurrent()){
						//gets the new talk
						npc.setTalkCurrent(talking);
						//begin all the parts again
						npc.setTalkPart(0);
						//needs to tell this part is over
						talk = null;
						conversationFinished = true;
						
					}else{
						// needs to finish the talk and initialize all the talk
						npc.setTalkPart(0);
						npc.setTalkCurrent(0);
						talk = null;
						conversationFinished = true;
					}
				}		
								
				//change the text inside the conversation box
				RPGSystem.getRPGSystem().getMenu().changeText(objects[3].toString());				
				
				//show the conversation box
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().showConversationBox();

				//refresh the talk
				if(talk != null){
					talk.setExtra3(npc.getTalkCurrent());
					talk.setExtra4(npc.getTalkPart());
				}
								
			}else if(objects[0].toString().equals("shop")){
				//will call a shop								
				RPGSystem.getRPGSystem().getMenu().createAskShop(objects[1].toString(),250, 250, 500, 80);			
				
				//will need to create the conversation to show the options buy and sell
				
				//will create the shop according to the button pressed
				
				//will show the conversation box
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showThirdMenu();
								
			}
			
		}
						
	}

	public void acceptEvent(){

		//test purpose
		//System.out.println("Time: "
		//		+ FactoryManager.getFactoryManager().getResourceManager()
		//				.getObjectManager().getCharacter().getJoint()
		//				.getCurrentTime());
		
		//Game.battle = true;
		
		if (waitConversation && conversationFinished) {

			// if the talk finished then end the conversation
			waitConversation = false;
			// tells to remove the last conversation box
			FactoryManager.getFactoryManager().getGraphicsManager().getGui()
					.closeConversationBox();
		} else {
			//tells to remove the last conversation box
			FactoryManager.getFactoryManager().getGraphicsManager().getGui()
					.closeConversationBox();
			// tells to pass to the next text
			scriptEvent(null);
		}		
		
		//if is in the menu
		if(Game.menu){			
				
		}
		
		//if is in a cut-scene
		if(Game.cutScene && RPGSystem.getRPGSystem().getCutscene().isWaitText()){
			//tells to pass to the next text
			RPGSystem.getRPGSystem().getCutscene().setWaitText(false);
			//tells to remove the last conversation box
			FactoryManager.getFactoryManager().getGraphicsManager().getGui().closeConversationBox();
		}

		//if is talking

		//if is in battle

		//if is free map
		if(RPGSystem.getRPGSystem().getMap().getMapType()){
			//test if there´s anybody nearby or an object with an event

		}//the path map
		else{
			//enter in a map
			RPGSystem.getRPGSystem().getMap().enterMap();
		}

		//must be in the menu or in a choice
		//System.out.println("Accept");
		/*if(Game.menu){


		}else{
			//play a sound effect
			FactoryManager.getFactoryManager().getResourceManager()
					.getAudioManager().playSFX("Wolf");
		}*/
	}

	public void createCollision(){
		physic.createCollisionOnFloor();
	}

	public Physic getPhysic() {
		return physic;
	}

	public void registerEvent(Event event) {
		events.addElement(event);
	}

	public void registerEvents(Vector<Event> eves) {
		// events.addAll(eves);
		Event temp;
		for (int i = 0; i < eves.size(); i++) {
			temp = eves.remove(0);
			events.addElement(temp);
		}

	}

	public boolean isWaitConversation() {
		return waitConversation;
	}

	public void setWaitConversation(boolean waitConversation) {
		this.waitConversation = waitConversation;
	}

	public boolean isConversationFinished() {
		return conversationFinished;
	}

	public void setConversationFinished(boolean conversationFinished) {
		this.conversationFinished = conversationFinished;
	}

}
