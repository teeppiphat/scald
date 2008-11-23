package framework.rpgsystem.item;

import framework.FactoryManager;
import framework.util.LoadTableEntry;

/**
 * If the item evolves, this one determines what new
 * abilities the item will gain
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class ItemXPTableEntry extends LoadTableEntry{
  	
	public ItemXPTableEntry(){
		super();
	}
	
	/**
	 * Method used to evolve the class one level, just calls the script
	 * responsible for the evolution of the status, if has
	 */
	public void gainLevel(int level, String identifier){
		
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
 
