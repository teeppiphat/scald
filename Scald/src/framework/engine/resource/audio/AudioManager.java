package framework.engine.resource.audio;

import org.jdom.Element;

import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;
import com.jmex.audio.MusicTrackQueue.RepeatType;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;

/**
 * It manages directly the system of the audio, responsible
 * for both the background music and the sound effects
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class AudioManager {
 	 
	/**
	 * Responsible for the sound effects
	 */
	private SoundEffects soundEffects;
	 
	/**
	 * Responsible for the background music
	 */
	private Music music;
	
	/**
	 * Object responsible for the system used to play the musics and
	 * the sound effects used
	 */
	private AudioSystem system;
	
	/**
	 * Responsible for one effect that must wait to stop playing
	 */
	private AudioTrack effect;
	
	/**
	 * Responsible for one music that must wait to stop playing
	 */
	private AudioTrack CutSceneMusic;
	
	private AudioTrack musicPlaying;
	
	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public AudioManager(){				
		//initializing the objects
		soundEffects = new SoundEffects();
		music = new Music();
		effect = null;
		CutSceneMusic = null;
		musicPlaying = null;
	}
	
	/**
	 * Method used to create the audio system
	 */
	public void createAudioSystem(){
		//create the system used in the audio
		system = AudioSystem.getSystem();		
	}	

	/**
	 * Method used to destroy the audio system
	 */
	public void destroyAudioSystem(){		
		//if the audio system was created
		if (system != null){
			//then destroy it
			system.cleanup();
		}
	}
	
	public void playMusic(String name){
		//test if the music is playing
		if(musicPlaying != null && musicPlaying.isPlaying()){
			//stop the music
			musicPlaying.stop();
		}
			
		//will get the new music
		musicPlaying = music.getMusic(name);
		//will tell the music to play
		musicPlaying.play();
				
		/*if(system.getMusicQueue().isPlaying()){	
			//will stop the music at the moment
			system.getMusicQueue().stop();			
			//will clear the music track
			system.getMusicQueue().clearTracks();			
		}		
		//will put the new music in it
		system.getMusicQueue().addTrack(music.getMusic(name));	
		//put the music to play
		//system.getMusicQueue().play();
		
		System.out.println("Size: " + system.getMusicQueue().getTrackList().size());
		
		*/
	}

	/**
	 * Method responsible for playing a music
	 */
	public void playMusic(){
		//if it´s not already playing a music
		if (!system.getMusicQueue().isPlaying()){
			//then play one
			system.getMusicQueue().play();
		}
	}
	
//	write the current track
	//if the music stopped
	/*if (!system.getMusicQueue().isPlaying()){			
		//System.out.println("not playing");
		//if is already at the end
		if (system.getMusicQueue().getTrackList().isEmpty()){
			//next track
			system.getMusicQueue().setCurrentTrack(0);					
		}
		
		//if is in the cut-scene when stopped playing and is waiting for something
		if(Game.cutScene && RPGSystem.getRPGSystem().getCutscene().isWaitMusic()){					
			RPGSystem.getRPGSystem().getCutscene().setWaitMusic(false);
		}else{		
			//System.out.println("Keep playing");
			//System.out.println(Game.cutScene);
			//System.out.println(RPGSystem.getRPGSystem().getCutscene().isWaitMusic());
			//stopped
			system.getMusicQueue().stop();
			//play
			system.getMusicQueue().play();
		}
	}*/
	
	/**
	 * Method is used to update the audio system
	 */
	public void updateAudioSytem(){		
		//if the system is created
		if (system != null){
			//then update the sound system
			system.update();			
			
			//must test if there´s a cut-scene in play
			if(Game.cutScene){
				//will test if waits for anything
				if(RPGSystem.getRPGSystem().getCutscene().isWaitSound() || RPGSystem.getRPGSystem().getCutscene().isRepeatSound()){
					//if there´s an effect, then is waiting for it
					if(effect != null && !effect.isPlaying()){							
						
						//can go on
						if(Game.cutScene && RPGSystem.getRPGSystem().getCutscene().isWaitSound()){
							RPGSystem.getRPGSystem().getCutscene().setWaitSound(false);
						}
						
						//must clean the effect	or keep playing it		
						if(!RPGSystem.getRPGSystem().getCutscene().isRepeatSound()){								
							effect = null;	
						}else{								
							effect.play();
						}
					}				
				}			
			}			
			
			//test if the music isn´t playing
			if(musicPlaying != null && !musicPlaying.isPlaying()){
				//not playing
				
				//if is in the cut-scene when stopped playing and is waiting for something
				if(Game.cutScene && RPGSystem.getRPGSystem().getCutscene().isWaitMusic() && !musicPlaying.isActive()){					
					RPGSystem.getRPGSystem().getCutscene().setWaitMusic(false);
					
					//try to know if will keep playing the music
					if(!RPGSystem.getRPGSystem().getCutscene().isRepeatMusic()){
						//stop playing
						musicPlaying = null;
					}
				}else{	
					
					//must know if keep playing the music
					
					//the music stopped
					musicPlaying.stop();
					//put to play again
					musicPlaying.play();	
				}
				
				//the music stopped
				//musicPlaying.stop();
				//put to play again
				//musicPlaying.play();				
			}			
		}
	}
	
	public void updateAudioSytem2(){		
		//if the system is created
		if (system != null){
			//then update the sound system
			system.update();			
			
			//test if the music is playing
			if(musicPlaying != null && !musicPlaying.isPlaying()){
				//the music stopped
				musicPlaying.stop();
				//put to play again
				musicPlaying.play();				
			}
			
			//write the current track
			//if the music stopped
			/*if (!system.getMusicQueue().isPlaying()){
				
				//se chegou já no final
				if (system.getMusicQueue().getTrackList().isEmpty()){
					//next track
					system.getMusicQueue().setCurrentTrack(0);					
				}
				//stopped
				system.getMusicQueue().stop();
				//play
				system.getMusicQueue().play();	
			}	*/					
		}
	}
			
	/**
	 * Method used to set the configurations used for the music queue
	 * @param fadeIn time between musics, the fade in, of the class Float
	 * @param fadeOut time between musics, the fade out, of the class Float
	 * @param repeat represents the times the music is repeated, is
	 * an object of the MusicQueue, a RepeatType
	 */
	public void setMusicQueue(float fadeIn, float fadeOut,int repeat){

		switch(repeat){
		case 0:{
			//always repeat
			//used to say the number of times that a music is repeated
			system.getMusicQueue().setRepeatType(RepeatType.ALL);
			break;
		}
		case 1:{
			//repeat only the music playing
			//used to say the number of times that a music is repeated
			system.getMusicQueue().setRepeatType(RepeatType.ONE);
			break;			
		}
		case -1:{
			//don´t repeat, play only once
			//used to say the number of times that a music is repeated
			system.getMusicQueue().setRepeatType(RepeatType.NONE);
			break;
		}
		}
	
		//used to set the time between musics, fade in
		system.getMusicQueue().setCrossfadeinTime(fadeIn);
		//used to set the time between musics, fade out
		system.getMusicQueue().setCrossfadeoutTime(fadeOut);		
	}
	//I think
	//NONE play the music once
	//ONE plays only that music
	//ALL repeat all the musics after playing then once
	
	//not tested
	public void createMusic(String name,String file,float distance,float volume){		
		//calls the class responsible for the musics
		music.getMusic(name,file,distance,volume);
		//system.getMusicQueue().addTrack(music.getMusic(name,file,distance,volume));				
	}

	//not tested
	public void createSFX(String name,String file,float distance,float volume){
		//calls the class responsible for the sound effects
		//system.getMusicQueue().addTrack(soundEffects.getSFX(name,file,
		//		distance,volume));
		
		//in the case of a sound effect, it only is played when is needed,
		//so it doesn´t go into the MusicQueue
		soundEffects.getSFX(name,file,distance,volume);		
	}
	
	public void playSFX(String name){
		//depends on the cut-scene
		if(RPGSystem.getRPGSystem().getCutscene().isWaitSound()){			
			//the effect must receive
			effect = soundEffects.getSFX(name);
			//then will put to play
			effect.play();
		}else{			
			//only play
			soundEffects.getSFX(name).play();
		}				
	}
	
	//not tested
	public void playSFX(String name,String file,float distance,float volume){
		//get the sound effect and already plays it
		soundEffects.getSFX(name,file,distance,volume).play();
	}
	
	/**
	 * Method used to clean the musics that were loaded
	 */
	public void cleanMusic(){
		//remove the musics from the list
		system.getMusicQueue().clearTracks();
		//remove the musics from the hash
		//music.cleanHash();
		//remove the sound effects from the hash
		//soundEffects.cleanHash();
		//initialize the object
		effect = null;
	}

	public void setConfigurations(String settings) {
		
		//get the element that has the properties
		Element properties = FactoryManager.getFactoryManager()
		.getScriptManager().getReadScript().getElementXML(settings,
				"AudioSettings");
		
		//set the properties
		setMusicQueue(Float.parseFloat(properties.getChildText("FadeIn")),
				Float.parseFloat(properties.getChildText("FadeOut")),
				Integer.parseInt(properties.getChildText("RepeatMusic")));
		
	}

	
	 
}
 
