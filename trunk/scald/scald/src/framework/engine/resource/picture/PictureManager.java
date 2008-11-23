package framework.engine.resource.picture;

/**
 * Manages the images used in the game, being them
 * images used in the menu or for the interface like 
 * showing the map.
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class PictureManager {

	/**
	 * Responsible for the images
	 */
	private Picture picture;

	/**
	 * Responsible for the sprites
	 */
	private Sprite sprite;

	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public PictureManager() {

		//initializing the objects
		picture = new Picture();
		sprite = new Sprite();

	}

	/**
	 * Method used to get the class responsible for the pictures
	 * @return returns an object of the Picture class
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * Method used to get the class responsible for the spirtes
	 * @return returns an object of the Sprite class
	 */
	public Sprite getSprite() {
		return sprite;
	}

}
