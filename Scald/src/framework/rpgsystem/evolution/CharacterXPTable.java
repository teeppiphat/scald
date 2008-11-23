package framework.rpgsystem.evolution;

import framework.util.LoadTable;

/**
 * Responsible for the table that determines when the character will
 * evolve, or will pass to a new level
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class CharacterXPTable extends LoadTable{
	 
	//private CharacterXPTableEntry[] characterXPTableEntry;
	private CharacterXPTableEntry characterXPTableEntry;
	
	public CharacterXPTable(){
		characterXPTableEntry = new CharacterXPTableEntry();
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

	public CharacterXPTableEntry getCharacterXPTableEntry() {
		return characterXPTableEntry;
	}
	 
}
 
