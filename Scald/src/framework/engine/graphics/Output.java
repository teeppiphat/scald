package framework.engine.graphics;

import org.jdom.Element;

import framework.FactoryManager;

/**
 * Responsible for adjusting everything that is printed
 * in the display, adjusting for the determined display
 * properties
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Output {
 
	//private GraphicsManager graphicsManager;
	
	private Element element;
		
	/**
	 * Constructor of the class, it initalize the object
	 */
	public Output(){		
		element = null;		
	}
	
	/**
	 * Method used to load the display settings
	 */
	public void loadDisplayConfiguration(String file){		
		
		element = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getElementXML(file, "DisplaySettings");
				
	}

	public Element getElement() {
		return element;
	}

	
	
	
}
 
