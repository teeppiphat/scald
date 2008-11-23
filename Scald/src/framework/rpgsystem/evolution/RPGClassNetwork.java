package framework.rpgsystem.evolution;

import org.jdom.Element;

import framework.FactoryManager;

/**
 * It has all the classes that can be used by the characters
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RPGClassNetwork {
 
	/**
	 * Used to hold the file that has all the classes indications
	 */
	private String file;
		
	/**
	 * Used to get information from the file
	 */
	private Element loadClasses;
	
	/**
	 * Constructor of the class
	 */
	public RPGClassNetwork(){		
	}
	
	/**
	 * Method used to specify the file that has information on all classes that
	 * exists, and ask that file to be loaded
	 * 
	 * @param file
	 *            the file, a String
	 * @param path
	 *            the place where the file is, a String
	 */
	public void setClassNetwork(String file){
		//get the file
		this.file = file;
				
		//with this will load all the classes
		loadClasses();		
	}
	
	/**
	 * Method used to get the root element to load a class
	 */
	public void loadClasses(){
		//get the root element
		loadClasses = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getRootElement(file);
	}
	
	/**
	 * Method used to get the place where the class wanted is
	 * 
	 * @param wanted
	 *            the class that wants, a String
	 * @return an Element
	 */
	public Element getClassInformation(String wanted){
		return loadClasses.getChild(wanted);
	}
	
	/**
	 * Method used to get the place where the class wanted is
	 * @param wanted the class that is wanted
	 * @return where the class is
	 */
	public String getClassPlace(String wanted){
		//get the class wanted
		Element temp = getClassInformation(wanted);
		//return where it is
		return temp.getChildText("Path") + temp.getChildText("File");		
	}
}
 
