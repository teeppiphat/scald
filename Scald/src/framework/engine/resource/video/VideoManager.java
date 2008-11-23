package framework.engine.resource.video;

import framework.engine.resource.ResourceManager;

/**
 * Responsible for having the way to play a video,
 * and having all the videos that should be played
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class VideoManager {
 
	//private ResourceManager resourceManager;
	 
	/**
	 * Responsible for the video
	 */
	private Video video;
	 
	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public VideoManager(){
		
		//initializing the object
		video = new Video();
		
	}
}
 
