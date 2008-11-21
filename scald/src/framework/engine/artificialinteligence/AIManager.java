package framework.engine.artificialinteligence;

import java.util.ArrayList;

import framework.util.StrategyAction;

/**
 * Class responsible for having all the types of Artificial Inteligence,
 * the kind that controls those who exists in the world, except for
 * the characters used by the player
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class AIManager {
 
	/**
	 * Atribute responsible for the actions of the characters
	 */
	private AgentCharacter agentCharacter;
	 
	/**
	 * Atribute responsible for the actions of the NPCs
	 */
	private AgentNPC agentNPC;
	 
	/**
	 * Atribute responsible for the actions of the Enemys
	 */
	private AgentEnemy agentEnemy;
	
	/**
	 * The file that has where everything is
	 */
	private String where;
	
	/**
	 * Constructor of the class, it initializes
	 * the objects
	 */
	public AIManager(){
		
		//initialize the objects
		agentCharacter = new AgentCharacter();
		agentEnemy = new AgentEnemy();
		agentNPC = new AgentNPC();		
	}
	
	public StrategyAction searchEnemyAction(int number, ArrayList<StrategyAction> array){
		
		//look for the number used
		for(int i = 0; i < array.size(); i++){
			//test if is the one
			if((number >= array.get(i).getInitial()) && (number <= array.get(i).getEnd())){
				//return what was found
				return array.get(i);
			}
		}
		
		//return nothing, to say not found
		return null;
		
	}

	public StrategyAction getEnemyAction(int number, String strategy){
		
		//get the stratey used
		ArrayList<StrategyAction> temp;
				
		//get the strategy used
		temp = agentEnemy.getStrategy(strategy, where);
		
		//search for the action
		return searchEnemyAction(number, temp);
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
		
}
 
