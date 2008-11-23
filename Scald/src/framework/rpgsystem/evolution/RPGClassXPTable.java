package framework.rpgsystem.evolution;

import framework.util.LoadTable;

/**
 * Has the table that represents when determined
 * class evolves or pass to a new level
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RPGClassXPTable extends LoadTable{
	 
	//private ClassXPTableEntry[] classXPTableEntry;
	private RPGClassXPTableEntry classXPTableEntry;
	
	public RPGClassXPTable(){
		classXPTableEntry = new RPGClassXPTableEntry();
	}
	
	/**
	 * Method used to load the table that will have all the 
	 * levels and their respective amount of experience needed
	 * @param path
	 * @param file
	 * @param maxLevel
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
	 * @param level
	 * @return
	 */
	public int getXP(int level){
		return levels[level];
	}

	public RPGClassXPTableEntry getClassXPTableEntry() {
		return classXPTableEntry;
	}
	
	
}
 
