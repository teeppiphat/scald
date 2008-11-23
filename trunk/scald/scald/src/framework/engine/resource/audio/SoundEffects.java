package framework.engine.resource.audio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;

/**
 * Responsible for playing the sound effects
 * that are used in the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class SoundEffects {
	 
	/**
	 * Object used to keep the sound effects
	 */
	HashMap<String, AudioTrack> sfxs;

	/**
	 * Constructor of the class, it initializes its objects
	 */
	public SoundEffects(){
		//the HashMap
		sfxs = new HashMap<String, AudioTrack>();
		
	}
	
	/**
	 * Method used to get a soud effect, if it is already in the memory
	 * the sound effect is returned, if not then load it and return it
	 * @param name the key that represents the music, of the class String
	 * @param file the object used to load the music, of the class String
	 * @param distance determines the distance the music can be heard, uses a float
	 * @param volume determines the volume of the music can be heard, uses a float
	 * @return an object of the class AudioTrack
	 */
	public AudioTrack getSFX(String name,String file, float distance, float volume){
		
		//ask the hash for the sound effect
		AudioTrack sound = (AudioTrack)sfxs.get(name);
		//if the sound doesn´t exists, then load it
		if(sound == null){
			//load the sound
			sound = loadSFX(file);
			//set the configurations used for the sound
			//set the distance the sound can be heard
			sound.setMaxAudibleDistance(distance);		
			//set the volume used for this sound
			sound.setVolume(volume);
			//put the sound in the hash
			sfxs.put(name, sound);
		}		
		
		//returns the sound
		return sound;	
	}
	
	public AudioTrack getSFX(String name){
		//ask the hash for the sound effect
		AudioTrack sound = (AudioTrack)sfxs.get(name);
		//if the sound doesn´t exists, then load it
		if(sound == null){
			return null;
		}		
		
		//returns the sound
		return sound;	
	}

	/**
	 * Method used to load the sound effect
	 * @param file represents where the sound effect is, an object of the class
	 * String
	 * @return returns the sound effect, an object of the class AudioTrack
	 */
	private AudioTrack loadSFX(String file) {
		
		URL url = null;
		try {
			url = new URL("file:" + file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//load the sound effect needed
		return AudioSystem.getSystem().createAudioTrack(url, false);
	}
	
	/**
	 * Method used to clean the sound effects that were loaded
	 */
	public void cleanHash(){
		sfxs.clear();		
	}
	
}
 
