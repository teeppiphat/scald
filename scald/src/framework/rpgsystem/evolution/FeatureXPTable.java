package framework.rpgsystem.evolution;

import framework.util.LoadTable;

/**
 * If the Feature evolves like the characters then this is where
 * its table tells when it evolves
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FeatureXPTable extends LoadTable{
 		
	/**
	 * Holds the way this Feature evolves the status, the new effects that are
	 * gained and the Features that are learned by this
	 */
	private FeatureXPTableEntry featureXPTableEntry;
	//private ArrayList<FeatureXPTableEntry> featureXPTableEntry;
	//private FeatureXPTableEntry[] featureXPTableEntry;
	
	/**
	 * Constructor of the class
	 */
	public FeatureXPTable(){		
		featureXPTableEntry = new FeatureXPTableEntry();
	}
	
	/**
	 * Method used to load the table that will have all the 
	 * levels and their respective amount of experience needed
	 * @param path a String
	 * @param file a String
	 * @param maxLevel an integer
	 */
	public void getTable(String path,String file,int maxLevel){
		//set the max level
		setMaxLevel(maxLevel);
		//load the table
		loadTable(path, file);
	}
	
	/**
	 * Method used to get the experience needed for the
	 * level in question
	 * @param level an integer
	 * @return an integer
	 */
	public int getXP(int level){		
		return levels[level];
	}

	public FeatureXPTableEntry getFeatureXPTableEntry() {
		return featureXPTableEntry;
	}
	
	public int getLevelsSize(){
		return levels.length;
	}
	 
}
 
