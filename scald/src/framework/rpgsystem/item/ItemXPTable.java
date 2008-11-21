package framework.rpgsystem.item;

import framework.util.LoadTable;

/**
 * If the item evolves, this is the table that determines
 * when it will evolve
 * 
 * @author Diego AnTõnio Tronquini Costi
 *
 */
public class ItemXPTable extends LoadTable{
 	 
	/**
	 * Represents the abilitys of the item, determining the
	 * ones that evolve
	 */
	private ItemXPTableEntry itemXPTableEntry;
	//private ItemXPTableEntry[] itemXPTableEntry;
	
	/**
	 * Constructor of the class
	 */
	public ItemXPTable(){
		
	}
	
	/**
	 * Method used to load the table that will have all the 
	 * levels and their respective amount of experience needed
	 * @param path the folder where the file is, a String
	 * @param file the name of the file, a String
	 * @param maxLevel the maximum level can reach, an integer
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
	 * @param level the level that wants
	 * @return how much is needed
	 */
	public int getXP(int level){
		return levels[level];
	}

	public ItemXPTableEntry getItemXPTableEntry() {
		return itemXPTableEntry;
	}
	 
}
 
