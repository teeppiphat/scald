package framework.rpgsystem.battle;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;

/**
 * Responsible for having all the enemys that appear during
 * a battle, the group that attacks the player
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class EnemyGroup {
 
	/**
	 * Responsible for the enemys
	 */
	private Enemy[] enemy;
	
	/**
	 * Responsible for the size of the array of enemies
	 */
	private int size;
	
	/**
	 * Constructor of the class
	 */
	public EnemyGroup(){
		
	}
	
	/**
	 * Used to get an element that is wanted in a File
	 * @param whe the file, a String
	 * @param what the element wanted, a String
	 * @return an Element
	 */
	public Element getWanted(String whe, String what){
		return FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getElementXML(whe, what);
	}
	
	/**
	 * Method used to get where the file that has all characters is
	 * 
	 * @return a String
	 */
	public String getFile(Element temp){
		//Element temp = getPlace(where,"Characters");
		return temp.getChildText("Path") + temp.getChildText("File");
	}
	
	/**
	 * Method used to get the a xml file, returns its root
	 * @param place the file, a String
	 * @return an Element
	 */
	public Element loadFile(String place){
		return FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getRootElement(place);	
	}
	
	/**
	 * Method used to get one enemy from the file that has
	 * all enemys
	 * @param name the identifier, a String
	 * @return an Element
	 */
	public Element getOneEnemy(String name){
		//follow the commentarys of the player group, getOneCharacter, just doesn´t have its own where
		return loadFile(getFile(getWanted(getFile(getWanted(RPGSystem.getRPGSystem().getWhere(), "Enemys")), name)));
	}
	
	/**
	 * Method used to create a group of enemys 
	 * @param enemys an array with the identifier to load each enemy
	 */
	public void createGroup(String[] enemys){
		
		//get the size
		size = enemys.length;
		
		//allocate the number of spaces necessary
		enemy = new Enemy[size];
		
		//loop to create each enemy
		for(int i = 0; i < size; i++){
			//create the position
			enemy[i] = new Enemy();			
			//ask to create an enemy
			enemy[i].createEnemy(getOneEnemy(enemys[i]));
		}
		
		//will test if there´s enemies of the same name
		testName();
	}
	
	public void testName(){
		//temporary objects
		int number;
		boolean changed;
		
		//first used 
		for (int initial = 0; initial < enemy.length - 1; initial++) {
			// reset the values
			number = 2;
			changed = false;
			
			//second used
			for(int search = 0; search < enemy.length; search++){
				//can´t test with the same position
				if(initial != search){
					
					//test if the name is the same
					if(enemy[initial].getName().equals(enemy[search].getName())){
						//the name is the same, puts the name at the end
						enemy[search].setName(enemy[search].getName() + number);
						//tells that changed
						changed = true;
						//increase the value
						number++;
					}					
				}				
			}			
			//test if there´s another with the same name
			if(changed){
				enemy[initial].setName(enemy[initial].getName() + 1);
			}			
		}		
	}
	
	public Enemy getEnemy(int pos){
		return enemy[pos];
	}

	public Enemy[] getEnemy() {
		return enemy;
	}
	
	public int findEnemyPosition(String identifier){			
		//loop
		for(int i = 0; i <= size; i++){
			if(enemy[i].getIdentifier().equals(identifier)){
				return i;
			}
		}		
		//not found
		return -1;
	}
	
	public int findEnemyNamePosition(String name){
		//loop
		for(int i = 0; i <= size; i++){
			if(enemy[i].getName().equals(name)){
				return i;
			}
		}		
		//not found
		return -1;
	}
	
	public void removeEnemy(String name){		
		//look for the element that wants to remove
		int remove = findEnemyNamePosition(name);
		
		//subtract in one the size of the array
		size--;
		
		//make all the values in the array down in one position, in the position to remove
		for(int i = remove + 1; i < size; i++){
			enemy[i - 1] = enemy[i];
		}
	}

	public int getSize() {
		return size;
	}
		 
}
 
