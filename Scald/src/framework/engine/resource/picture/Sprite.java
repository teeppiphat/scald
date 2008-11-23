package framework.engine.resource.picture;

import com.jme.image.Image;

import framework.FactoryManager;

/**
 * A sprite responsible class, it takes care
 * of animations that use images
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Sprite {
 	
	/**
	 * Determine the quantity of frames used
	 */
	int quantity;
	
	/**
	 * The frame that is being painted
	 */
	int moment;
		
	/**
	 * Used to specify the where the images are printed 
	 */
	int x,y;
	
	/**
	 * Used to get the size of the image
	 */
	int height,width;
	
	/**
	 * Array that has the images used by the sprite
	 */
	Image images[];
	
	/**
	 * Keeps the name of the image that is loaded
	 */
	String image;
	 
	/**
	 * Constructor of the class, private so that its not used
	 */
	public Sprite(){
		
	}
	
	public void createSprite(int quantity,int x,int y,String image){
		
		//determine the quantity of frames
		this.quantity = quantity;
		//determine the position of the images		
		this.x = x;
		this.y = y;
		//keeps the name of the image
		this.image = image;
		//used to control the animation of the sprite
		moment = 0;
		//initializes the array
		images = new Image[quantity];
		//load the images, using the Picture class
		for(int i=0; i < images.length; i++){
			images[i]  = FactoryManager.getFactoryManager().
			getResourceManager().getPictureManager().
			getPicture().getPicture(image, image);
		}
		
		//get the size of the image
		height = images[0].getHeight();
		width = images[0].getWidth();
		
	}
	
	public Image getMomentFrame(){
		return images[moment];
	}
	
	public void beginsAnimation(){
		moment = 0;
	}
	
	public boolean isPrintable(){
		if(moment < quantity)
			return true;
		else
			return false;		
	}
	
	public void refresh(){
		if (isPrintable())
			moment++;
		else
			moment = 0;
	}
	
}
 
