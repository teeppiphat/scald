package framework.engine.resource.font;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.fenggui.render.Font;

/**
 * Responsible for holding the fonts that are used
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Fonts {

	/**
	 * Object responsible for loading the fonts needed
	 */
	HashMap<String, Font> fonts;

	/**
	 * Constructor of the class
	 */
	public Fonts() {
		fonts = new HashMap<String, Font>();

	}
	
	public Font getFont(String name){
		//get the font from the hashmap
		Font font = fonts.get(name);
		//return the font
		return font;
	}

	public Font getFont(String name, String file1, String file2) {

		//get the font from the hashmap
		Font font = fonts.get(name);
		//test if the font was found
		if (font == null) {
			//if it wasn´t in the hashmap then load it
			font = loadFont(file1, file2);
			//put it in the hashmap
			fonts.put(name, font);
		}
		return font;
	}

	public Font loadFont(String file1, String file2) {
		URL url1, url2;
		Font font = null;
		try {
			url1 = new URL("file:" + file1);
			url2 = new URL("file:" + file2);
			try {
				font = new Font(url1.getFile(), url2.getFile());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return font;
	}

}
