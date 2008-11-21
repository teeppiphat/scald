package framework.rpgsystem.evolution;

import framework.FactoryManager;
import framework.util.LoadTableEntry;

/**
 * Tells everything that evolves when the class evolves,
 * for exemple, atributes and new magics/skills 
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RPGClassXPTableEntry extends LoadTableEntry{
 	
	/**
	 * Constructor of the class
	 */
	public RPGClassXPTableEntry(){
		super();
	}
		
	/**
	 * Method used to evolve the class one level, just calls the script
	 * responsible for the evolution of the status, if has
	 * 
	 * @param level
	 *            the level that was gained, an integer
	 * @param identifier
	 *            the identifier of the character, a String
	 */
	public void gainLevel(int level,String identifier){
				
		if(statusPath != null && statusFile != null){
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
 
