package framework.rpgsystem.cutscene;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;
import framework.engine.resource.object.Agent;

public class CutScene {

	/**
	 * Used to tell when to begin executing the cut-scene
	 */
	private boolean control;
	
	/**
	 * Used to keep continuing executing the scene without waiting for the
	 * current part to end
	 */
	private boolean waitEffect,waitMusic,waitMove,waitSound,waitText,wait,moving;
		
	/**
	 * used to tell if the music must keep playing, in the case of music of the ambient
	 * during the cut-scene
	 */
	private boolean repeatMusic,repeatSound;
		
	/**
	 * To control in which event is the cut-scene
	 */
	private int event;
	
	/**
	 * To control the division of the current talk
	 */
	private int talkPart;
	
	/**
	 * To control in which part of talking is
	 */
	private int talkCurrent;
	
	/**
	 * Array used to move characters
	 */
	private Object[] move;
	
	/**
	 * Constructor of the class
	 */
	public CutScene(){
		event = 0;
		control = false;
		waitEffect = false;
		waitSound = false;
		waitMusic = false;
		waitMove = false;
		waitText = false;
		repeatMusic = false;
		repeatSound = false;
		moving = false;
		
	}
	
	public void loadCutScene(String path,String file){	
		//will load the script of the scene
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().loadScript(path, file);
	}
	
	public void moveAll(){
		//temporary object
		int stop = 0;
		//to find the number of characters moving need to divide the size
		// of the array by the number of parameters used, that is 6
		int num = move.length / 6;
		String name;
		String animation;
		float speed;
		float distance;
		//int dis;
		int direction;
		Agent character = null;
		
		//will execute to all characters
		for(int i = 0; i < num; i++){
			//set the necessary value
			stop = 0;
			//receive all the values
			name = move[5].toString();
			animation = move[4].toString();
			speed = Float.parseFloat(move[3].toString());
			distance = Float.parseFloat(move[2].toString());
			//dis = (int)distance*100;
			direction = Integer.parseInt(move[1].toString());
			
			//need to get the character to move
			character = FactoryManager.getFactoryManager().getResourceManager().getObjectManager().getAll().get(name);
			
			//if the character exists, them will try to move
			if(character != null){
				
				System.out.println("Animation: " + animation);
				
				//if already walked all that could
				if((int)distance*100 == 0){
					//say that this character is already at the place
					stop++;					
				}else{
					//will move the character
					character.move(direction, speed);
					//tells the character to animate
					character.animate(animation);
					//subtract the value
					distance = distance - speed;	
					//pass the distance back to the array
					move[2] = distance;
					
				}
			}
			
		}
		
		if(stop == num){
			//can continue the execution
			waitMove = false;
			//don´t need to try moving again
			moving = false;
		}
	}
	
	public void executeCutScene(String method){		
		//temporary object
		Object objects[] = null;
		Object pass[] = new Object[3];
				
		//put what is needed in the array
		pass[0] = event;
		pass[1] = talkCurrent;
		pass[2] = talkPart;
		
		//map the array of objects to the script, always mapping again to change the Feature values according
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().mapToTheScript("Objects",pass);
		
		//will tell to execute it
		objects = FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript(method, pass);
						
		//will treat what is received
		if(objects != null){						

			if(objects[0].toString().equals("move")){
				// needs to move the character, needs to find out the number of
				// characters moving
				move = objects;				
				
				//tell that will move something
				moving = true;
				
				//try to know if its to wait
				waitMove = Boolean.parseBoolean(objects[objects.length-1].toString());
				
			}else if(objects[0].toString().equals("talk")){
				//will use the conversation box
				
				//RPGSystem.getRPGSystem().getQuests().completeQuest("Test");
				
				//needs to treat for the conversation
				int part;
				int talk;
				
				talk = Integer.parseInt(objects[1].toString());
				part = Integer.parseInt(objects[2].toString());
								
				//test the part, if the part is greater then has another part
				if(part > talkPart){
					talkPart = part;
				}else{
					//test the talk, if the talk is greater then move to the next talk
					if(talk > talkCurrent){
						//gets the new talk
						talkCurrent = talk;
						//begin all the parts again
						talkPart = 0;
					}else{
						//needs to finish the talk and initialize all the talk
						talkPart = 0;
						talkCurrent = 0;
						waitText = false;
					}
				}				
				
				//change the text inside the conversation box
				RPGSystem.getRPGSystem().getMenu().changeText(objects[3].toString());				
				
				//show the conversation box
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().showConversationBox();

				
				//will wait for the text to be read
				waitText = true;
				
			}else if(objects[0].toString().equals("effect")){
				//will play one of the effects below
				
				if(objects[1].toString().equals("particle")){
					//particle, will load and execute it, will have to get the place to the particles in the begging
					
					//MODIFY THE PARTICLES SO THAT ASK ONLY FOR THE NAME(Identifier) OF THE PARTICLE
					FactoryManager.getFactoryManager().getEventManager()
					.getPhysic().getParticleSystem().createParticle(
							"res/particles/", "particles.xml", objects[2].toString());					
					
					//get from what was received from the script if waits or not
					waitEffect = Boolean.parseBoolean(objects[3].toString());

				}else if(objects[1].toString().equals("sound")){
					//if wait true must wait for the effect to finish
					waitSound = Boolean.parseBoolean(objects[4].toString());	
					//if can repeat the sound
					repeatSound = Boolean.parseBoolean(objects[3].toString());					
					//sound effect, will load and execute it, don´t need to wait
					FactoryManager.getFactoryManager().getResourceManager().getAudioManager().playSFX(objects[2].toString());
					
				}else if(objects[1].toString().equals("music")){
					//if wait true must wait for the effect to finish
					waitMusic = Boolean.parseBoolean(objects[4].toString());					
					//tells the music to not repeat
					repeatMusic = Boolean.parseBoolean(objects[3].toString());						
					//music, will load and execute it, don´t need to wait to execute
					FactoryManager.getFactoryManager().getResourceManager().getAudioManager().playMusic(objects[2].toString());
					
				}else if(objects[1].toString().equals("remove")){
					//remove a character, will find the character and remove, don´t need to wait					
					//will have to find the model in the rootNode					
					FactoryManager.getFactoryManager().getGraphicsManager().getRootNode().getChild("Character").removeFromParent();				
					
				}else if(objects[1].toString().equals("add")){
					//will add a character, load and execute it, need to wait the loading
/*					String name = objects[2].toString();
					String identifier = objects[3].toString();
					String type = objects[4].toString();				
					float posX =  Float.parseFloat(objects[5].toString());
					float posY =  Float.parseFloat(objects[6].toString());
					float posZ =  Float.parseFloat(objects[7].toString());
					float angleX =  Float.parseFloat(objects[8].toString());
					
					float angleY =  Float.parseFloat(objects[9].toString());
					float angleZ =  Float.parseFloat(objects[10].toString());
					float scale =  Float.parseFloat(objects[11].toString());					
					String material = objects[12].toString();
					float density =  Float.parseFloat(objects[13].toString());
					float slide =  Float.parseFloat(objects[14].toString());
					float bounce =  Float.parseFloat(objects[15].toString());
*/					//will need the 2name, 3model, 4type, 5x,6y,7z,8rotx,9roty,10rotz,11scale,12material,13density,14slide,15bounce,16event,17pathEvent
					//"0effect;1add;2Character;3Lynder;4Object;5 1.0;6 0.0;7 0.0;8 0.0;9 0.0;0.0;0.0025;Default;0.0;0.0;0.0;none;none;"
					
					//will the the parameters to where they are needed
					FactoryManager.getFactoryManager().getResourceManager().loadModel(objects[4].toString(), objects[2].toString(), 
							objects[3].toString(), Float.parseFloat(objects[11].toString()), Float.parseFloat(objects[5].toString()),
									Float.parseFloat(objects[6].toString()), Float.parseFloat(objects[7].toString()), 
									Float.parseFloat(objects[8].toString()), Float.parseFloat(objects[9].toString()), 
									Float.parseFloat(objects[10].toString()), objects[12].toString(), Float.parseFloat(objects[13].toString()),
									Float.parseFloat(objects[14].toString()), Float.parseFloat(objects[15].toString()),"none","none");
					
				}else if(objects[1].toString().equals("video")){
					//video, will load and execute it, putting the system in a hold
					//TODO First need to create the VideoManager and Video to be able to play one
				}
			}else if(objects[0].toString().equals("end")){
				//will finish the cut-scene
				Game.cutScene = false;
			}
		}		
		
		//if its in the talk then wait for it to end
		if(talkPart == 0){
			//needs to increase to the next
			event++;
		}
	}
	
	public void execute(String path, String file, String method){
		//will control the execution
		if(control){			
			//begin and keep executing, also depends if must wait for one
			//of the events to end
			if(testWait()){
				executeCutScene(method);
			}
		}else{			
			//load the data necessary
			loadCutScene(path, file);
			//tell that can begin the execution of the cut-scene
			control = true;
		}
	}
	
	public boolean testWait(){
		if(waitEffect || waitMusic || waitMove || waitSound || waitText){
			return false;
		}else{
			return true;
		}
	}

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public boolean isWaitEffect() {
		return waitEffect;
	}

	public void setWaitEffect(boolean waitEffect) {
		this.waitEffect = waitEffect;
	}

	public boolean isWaitMove() {
		return waitMove;
	}

	public void setWaitMove(boolean waitMove) {
		this.waitMove = waitMove;
	}

	public boolean isWaitMusic() {
		return waitMusic;
	}

	public void setWaitMusic(boolean waitMusic) {
		this.waitMusic = waitMusic;
	}

	public boolean isWaitSound() {
		return waitSound;
	}

	public void setWaitSound(boolean waitSound) {
		this.waitSound = waitSound;
	}

	public boolean isRepeatMusic() {
		return repeatMusic;
	}

	public void setRepeatMusic(boolean repeatMusic) {
		this.repeatMusic = repeatMusic;
	}

	public boolean isRepeatSound() {
		return repeatSound;
	}

	public void setRepeatSound(boolean repeatSound) {
		this.repeatSound = repeatSound;
	}

	public boolean isWaitText() {
		return waitText;
	}

	public void setWaitText(boolean waitText) {
		this.waitText = waitText;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
		
}
