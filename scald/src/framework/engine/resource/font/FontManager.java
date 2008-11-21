package framework.engine.resource.font;

import org.fenggui.render.Font;


/**
 * Responsible for managing the types of fonts that
 * are used and disponible in the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FontManager {
 	 
	/**
	 * Responsible for a font
	 */
	private Fonts font;
	
	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public FontManager(){
		
		//initializing the object
		font = new Fonts();
		
	}	

	public Font getFont(String name) {		
		return font.getFont(name);
	}
	
	public void createFont(String name, String file1, String file2, String path) {
		font.getFont(name, path + file1, path + file2);
	}
		 
}
 
