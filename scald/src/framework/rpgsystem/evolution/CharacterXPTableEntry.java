package framework.rpgsystem.evolution;

import framework.FactoryManager;
import framework.util.LoadTableEntry;

/**
 * Responsible for changing everything that needs to be
 * changed when the character will evolve
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class CharacterXPTableEntry extends LoadTableEntry{
 	
	/**
	 * Constructor of the class
	 */
	public CharacterXPTableEntry(){
		super();
	}
	
	/**
	 * Method used to evolve the character one level, just calls the script
	 * responsible for the evolution of the status, if has
	 */
	public void gainLevel(int level, String identifier){

		if(statusFile != null && statusPath != null){
			//load the script wanted
			FactoryManager.getFactoryManager().getScriptManager().getReadScript()
			.loadScript(statusPath, statusFile);

			//calls the script to evolve the class
			//System.out.println("Class gained level");
			FactoryManager.getFactoryManager().getScriptManager().getReadScript()
			.executeScript("evolve", identifier);

		}
	}
	 
}
 
