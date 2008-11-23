package framework.engine.rules;

import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;


/**
 * It keeps all the rules that are used in the game
 * and pass them to where they are needed, only the rules
 * that are necessary for that part, like only the rules 
 * used in battle
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RulesManager {
 
	/**
	 * Responsible for a set of rules
	 */
	private RulesSet rulesSet;
		
	/**
	 * Constructor of the class, it initializes it´s objects
	 */
	public RulesManager(){
		
		//initializing the object
		rulesSet = new RulesSet();
	}
	
	/**
	 * Receive the file from where must load the rules
	 * @param file a String
	 */
	public void loadFile(String file){
		//load the element needed
		Element rules = FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getRootElement(file);
		
		//load the rules set
		loadRulesSet(rules);
	}
	
	/**
	 * Method used to load the rules set
	 * 
	 * @param rules
	 *            an Element
	 */
	public void loadRulesSet(Element rules){
		
		//load the storage rules for itens
		loadStorageRules(rules.getChild("Storage"));
		
		//load the money
		rulesSet.setMoney(rules.getChildText("Money"));
		
		//load the battle rules
		loadBattleRules(rules.getChild("Battle"));
		
		//load the evolution rules
		loadEvolutionRules(rules.getChild("Evolution"));
		
	}
	
	/**
	 * Load to know who can have a bag
	 * @param storage an Element
	 */
	public void loadStorageRules(Element storage){
		//get if the group has a bag
		rulesSet.setGroupBag(Boolean.parseBoolean(storage.getChildText("Group")));
		//get if the character has a bag
		rulesSet.setCharacterBag(Boolean.parseBoolean(storage.getChildText("Character")));
	}
	
	/**
	 * Called to load the battle rules in a rules set
	 * 
	 * @param battle
	 *            an Element
	 */
	public void loadBattleRules(Element battle){
		
		//get the type
		rulesSet.setBattleType(battle.getChildText("Type"));
		
		//get the encounter
		loadBattleEncounter(battle.getChild("Encounter"));
		
		//get the condition for victory and defeat
		//loadBattleConditionVD(battle.getChild("ContidionVD"));
		
		//get the contabilization of the battle
		loadBattleOnGoing(battle.getChild("OnGoing"));
		
		//get the quantity of players in battle
		loadBattleQuantity(battle.getChild("Quantity"));
		
		//get the possible actions
		//loadBattleActions(battle.getChild("Actions"));
		
		//get if can change characters during a battle
		rulesSet.setBattleChange(Boolean.parseBoolean(battle.getChildText("Change")));
		
		//get the type of physical and magical attacks
		loadAttackType(battle.getChild("Attack"));
		
		//get if create more than one group of enemys for battle
		rulesSet.setBattleGroup(Boolean.parseBoolean(battle.getChildText("Group")));
		
		//get the order factor of the battle
		rulesSet.setOrderFactor(battle.getChildText("OrderFactor"));
	}
	
	/**
	 * Load the way encounters work
	 * 
	 * @param encounter
	 *            an Element
	 */
	public void loadBattleEncounter(Element encounter){		
		//get if the battle is random
		rulesSet.setEncounterRandom(Boolean.parseBoolean(encounter.getChildText("Random")));		
		//get if change the screen
		rulesSet.setEncounterChangeScreen(Boolean.parseBoolean(encounter.getChildText("ChangeScreen")));		
	}
	
	/**
	 * Load the condition for victory and defeat, script responsible
	 * 
	 * @param condition
	 *            an Element
	 */
	public void loadBattleConditionVD(Element condition){		
		//get the file
		rulesSet.setConditionVDFile(condition.getChildText("File"));
		//get the path
		rulesSet.setConditionVDPath(condition.getChildText("Path"));		
	}
	
	/**
	 * Load the condition for how the battle happens, how to calculate things,
	 * script responsible
	 * 
	 * @param condition
	 *            an Element
	 */	
	public void loadBattleOnGoing(Element onGoing){
		//get the file
		rulesSet.setOnGoingFile(onGoing.getChildText("File"));
		//get the path
		rulesSet.setOnGoingPath(onGoing.getChildText("Path"));
	}
	
	/**
	 * How many characters go in a battle, like 3 battle together
	 * 
	 * @param quantity
	 *            an Element
	 */
	public void loadBattleQuantity(Element quantity){
		//get the quantity in battle
		rulesSet.setPlayersInBattle(Integer.parseInt(quantity.getChildText("Battle")));
		//get the quantity in the group
		rulesSet.setPlayersInGroup(Integer.parseInt(quantity.getChildText("PlayerGroup")));
	}
	
	/**
	 * Load all the possible actions in a battle, of the type attack, defend,
	 * run, others
	 * 
	 * @param act
	 *            an Element
	 */
	/*public void loadBattleActions(Element act){
		//get all the actions
		List list = act.getChildren();
		//temporary used to help
		Element temp;
		
		//get all the actions
		for(int i = 0; i < list.size(); i++){
			temp = (Element)list.get(i);
			//get one action
			rulesSet.getActions().add(temp.getText());
		}		
	}*/
	
	/**
	 * Get the type of the physical,simple or combo, and magical,simple or
	 * combo1(same time) or combo2(sequence), attack
	 * 
	 * @param attack
	 *            an Element
	 */
	public void loadAttackType(Element attack){
		//get the physical attack
		rulesSet.setAttackPhysical(attack.getChildText("Physical"));
		//get the magical attack
		rulesSet.setAttackMagical(attack.getChildText("Magical"));		
	}
	
	
	/**
	 * Method used to load the rules used for evolution
	 * 
	 * @param evolution
	 *            an Element
	 */
	public void loadEvolutionRules(Element evolution){
		
		//get the ways to evolve
		loadWaysEvolve(evolution.getChild("Way"));		
		//get who can evolve, gain experience
		loadWhatGainsExperience(evolution.getChild("What"));
		//get who learns
		loadWhoLearns(evolution.getChild("Who"));
		
		
		//get if can absorb
		rulesSet.setAbsorbFeature(Boolean.parseBoolean(evolution.getChildText("Absorb")));		
		//get if can create
		rulesSet.setCreationOfItem(Boolean.parseBoolean(evolution.getChildText("Creation")));
		//get if can equip magic
		rulesSet.setEquipMagic(Boolean.parseBoolean(evolution.getChildText("EquipMagic")));
	}
	
	/**
	 * Load the possible ways to evolve, by gainning experience and/or using a
	 * Feature
	 * 
	 * @param ways
	 *            an Element
	 */
	public void loadWaysEvolve(Element ways){
		//get all the actions
		List list = ways.getChildren();
		//temporary object
		Element temp;
		
		//get all the actions
		for(int i = 0; i < list.size(); i++){
			//get the element
			temp = (Element)list.get(i);
			//get one action
			rulesSet.getPossibleWayEvolve().add(temp.getText());
		}	
	}
	
	/**
	 * Load all that can gain experience, Character, Class, Item, Feature(Magic
	 * and Skill)
	 * 
	 * @param who
	 *            an Element
	 */
	public void loadWhatGainsExperience(Element who){
		// get all the actions
		List list = who.getChildren();
		// temporary object
		Element temp;
		
		//get all the actions
		for(int i = 0; i < list.size(); i++){
			// get the element
			temp = (Element)list.get(i);
			//get one action
			rulesSet.getWhatGainExperience().add(temp.getText());
		}	
	}
	
	/**
	 * Load who learns things from a class or an item, for those who can hold
	 * Feature
	 * 
	 * @param who
	 *            an Element
	 */
	public void loadWhoLearns(Element who){
		if(who != null){
			// get the class
			rulesSet.setClassLearn(who.getChildText("Class"));
			// get the item
			rulesSet.setItemLearn(who.getChildText("Item"));
		}
	}

	/**
	 * Method used to be able to get one of the rules from the RulesSet
	 * @return an object of the class RulesSet
	 */
	public RulesSet getRulesSet() {
		return rulesSet;
	}
	
	
	 
}
 
