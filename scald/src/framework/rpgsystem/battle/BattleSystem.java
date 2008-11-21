package framework.rpgsystem.battle;

import framework.FactoryManager;

/**
 * The battle system used by the game, having the rules
 * used in the battle, the group of enemys and the group of players,
 * also having the actions that can be used and the way they will
 * be used 
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class BattleSystem {
 
	/**
	 * Responsible for the type of attacks and for the way the actions happens
	 */
	private ActionStrategy actionStrategy;
		
	/**
	 * Responsible for having the enemys that appear
	 * in battle
	 */
	private EnemyGroup[] enemyGroup;
		
	/**
	 * Constructor of the class
	 */
	public BattleSystem(){
		
	}
	
	public void createEnemyGroup(String[] enemys){
		
		//the enemy group will be created according to the rules
		if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isBattleGroup()){
			//create the array with only one position
			enemyGroup = new EnemyGroup[1];
			//create the position
			enemyGroup[0] = new EnemyGroup();
			//create the group of enemys
			enemyGroup[0].createGroup(enemys);
		}else{
			//create more than one group of enemys			
		}		
	}
	
	public void init(){
		//initializes the battle system according to the rules
		
		//create the ActionStrategy according to the rules
		
		if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getBattleType().equals("Queue")){
			//create the action strategy for the battle of the type QueueTurnbased
			actionStrategy = new QueueTurnbased();
			
		}else if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getBattleType().equals("Real-Time")){
			//create the action strategy for the battle of the type Real-Time
			actionStrategy = new RealTime();
			
		}else if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getBattleType().equals("Progressive")){
			//create the action strategy for the battle of the type ProgressTurnbased
			actionStrategy = new ProgressTurnbased();
		}
	}
	
	public void createBattle(String[] enemys){
		
		//get everything ready for a battle
		
		//create the enemy group
		createEnemyGroup(enemys);
		
		
		//begin the battle
		//actionStrategy.start();		
	}
	
	public void battle(){		
		//keeps the battle running
		actionStrategy.start();
	}
	
	public void createAction(String type, boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, String used, int where) {
		//tell the ActionStrategy to create an Action
		actionStrategy.createAction(type, userGroup, userPosition, targetGroup,
				targetPosition, used, where);		
	}

	public ActionStrategy getActionStrategy() {
		return actionStrategy;
	}

	public EnemyGroup[] getEnemyGroup() {
		return enemyGroup;
	}
		
}
 
