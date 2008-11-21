package framework.rpgsystem.evolution;

import framework.FactoryManager;
import framework.util.LoadTableEntry;

/**
 * Represents the changes when a feature evolves
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FeatureXPTableEntry extends LoadTableEntry{
 
	//must have the changes for the status, the script
	
	//must have the changes for the Feature, gains another Feature
	
	//must have the changes for the Feature, gains effects
	
	/**
	 * Constructor of the class
	 */
	public FeatureXPTableEntry(){
		super();
		
	}
	
	/**
	 * Method used to evolve the feature one level, just calls the script
	 * responsible for the evolution of the status, if has
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
 
