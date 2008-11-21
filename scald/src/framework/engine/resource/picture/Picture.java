package framework.engine.resource.picture;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.fenggui.render.Binding;
import org.fenggui.render.ITexture;

import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.util.TextureManager;

/**
 * A static image used in the game, in the formats
 * that are comported
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Picture {
 	
	/**
	 * Object used to keep the images that are loaded
	 */
	HashMap<String, Image> pictures;
	/**
	 * Object used to keep the images for display that are loaded
	 */
	HashMap<String, ITexture> pictures2;
	
	/**
	 * Obejct used to keep the textures that are loaded
	 */
	HashMap<String, Texture> textures;
	
	/**
	 * Constructor of the class, it initiates its objects 
	 */
	public Picture(){
		
		//
		pictures = new HashMap<String, Image>();
		pictures2 = new HashMap<String, ITexture>();
		textures = new HashMap<String, Texture>();
	}
	
	/**
	 * Method used to get an image. Using the name of the image
	 * and the place where it is
	 * @param name an object of the class String, represents the name
	 * @param file an object of the class String, represents the place
	 * @return returns an object of the class Image
	 */
	public Image getPicture(String name, String file){
		
		//try to get the image from the hash
		Image picture = (Image)pictures.get(name);
		//if the image isn´t in the hash
		if(picture == null){
			//if it wasn´t in the hash map, then load it
			//maybe file + name
			picture = loadImage(file);
			//and then put it in the hash map
			pictures.put(name, picture);
		}
		
		//returns the image
		return picture;
	}
	
	/**
	 * Method used to load the image from the disk
	 * @param file an object of the class String, where the image is
	 * @return returns the image that was found
	 */
	public Image loadImage(String file){
		//first the place where it is, second if is flipped		
		return TextureManager.loadImage(file, false);
	}
	
	/**
	 * Method used to get an image. Using the name of the image
	 * and the place where it is
	 * @param name an object of the class String, represents the name
	 * @param file an object of the class String, represents the place
	 * @return returns an object of the class Image
	 */
	public ITexture getIPicture(String name, String file){
		
		//try to get the image from the hash
		ITexture picture = (ITexture)pictures2.get(name);
		//if the image isn´t in the hash
		if(picture == null){
			//if it wasn´t in the hash map, then load it
			//maybe file + name
			picture = loadIImage(file);
			//and then put it in the hash map
			pictures2.put(name, picture);
		}
		
		//returns the image
		return picture;
	}
	
	/**
	 * Method used to load the image from the disk
	 * @param file an object of the class String, where the image is
	 * @return returns the image that was found
	 */
	public ITexture loadIImage(String file){
		//first the place where it is, second if is flipped		
		try {
			return Binding.getInstance().getTexture(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method used to get an image. Using the name of the image
	 * and the place where it is
	 * @param name an object of the class String, represents the name
	 * @param file an object of the class String, represents the place
	 * @return returns an object of the class Image
	 */
	public Texture getTexture(String name, String file){		
		Texture texture = new Texture();
		//try to get the image from the hash
		texture = (Texture)textures.get(name);
		//if the image isn´t in the hash
		if(texture == null){
			//if it wasn´t in the hash map, then load it
			//maybe file + name
			texture = loadTexture(file);
			//and then put it in the hash map
			textures.put(name, texture);
		}
		
		//returns the image
		return texture;
	}
	
	/**
	 * Method used to load the image from the disk
	 * @param file an object of the class String, where the image is
	 * @return returns the image that was found
	 */
	public Texture loadTexture(String file){
		
		//create a URL to load
		URL url = null;
		
		//get the path
		try {
			url = new URL("file:" + file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//first the place where it is, second if is flipped		
		return TextureManager.loadTexture(url);
	}
	
	
	
	
	
	
}
 
