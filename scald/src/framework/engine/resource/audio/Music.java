package framework.engine.resource.audio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;

/**
 * Responsible for playing the musics that are used
 * in the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Music {
 	
	/**
	 * Object used to keep the musics and to have them in an easy way
	 * to find them
	 */
	HashMap<String, AudioTrack> musics;
	
	/**
	 * Constructor of the class, it initializes its objects
	 */
	public Music(){
		//the HashMap
		musics = new HashMap<String, AudioTrack>();
	}
	
	/**
	 * Method used to ask for a music used in the game, if the music is already
	 * loaded it returns it, if its not in the memory than load it
	 * @param name the key that represents the music, of the class String
	 * @param file the object used to load the music, of the class String
	 * @param distance determines the distance the music can be heard, uses a float
	 * @param volume determines the volume of the music can be heard, uses a float
	 * @return an object of the class AudioTrack
	 */
	public AudioTrack getMusic(String name, String file, float distance, float volume){
		
		//ask for the file
		AudioTrack music = musics.get(name);
		
		//test if the music was in the hash map
		if( music == null){
			//if it wasn´t in the hash map, then load it
			music = loadMusic(file);
			//and then put it in the hash map
			musics.put(name, music);			
		}
		
		//set the configurations used in the music
		//set the distance that can be heard the music
		music.setMaxAudibleDistance(distance);
		//set the volume of the music
		music.setVolume(volume);
		//returns the music		
		return music;		
	}
	
	public AudioTrack getMusic(String name){
		//ask the hash for the sound effect
		AudioTrack music = (AudioTrack)musics.get(name);
		//if the sound doesn´t exists, then load it
		if(music == null){
			return null;
		}		
		
		//returns the sound
		return music;	
	}

	/**
	 * Method used to load a music
	 * 
	 * @param file the object that has where the music needed is, of the class String
	 * @return returns the music that was loaded, an object of the class AudioTrack
	 */
	private AudioTrack loadMusic(String file) {
		
		
		URL url = null;
		try {
			url = new URL("file:" + file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//load the music and returns it
		//return AudioSystem.getSystem().createAudioTrack(getClass().
				//getResource( file ), false);
		
		//load the music and returns it		
		return AudioSystem.getSystem().createAudioTrack(url, false);		
	}
	
	/**
	 * Method used to remove all the musics from the memory
	 */
	public void cleanHash(){
		musics.clear();
	}
	
}
 
