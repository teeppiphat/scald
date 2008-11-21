package framework.engine.rules;

import java.util.ArrayList;

/**
 * It is where the rules are stored, stored in a set
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RulesSet {
 
	//FOR ITENS
	
	/**
	 * If the group has a bag
	 */
	private boolean groupBag;
	
	/**
	 * If the character has a bag
	 */
	private boolean characterBag;
	
	//FOR MONEY
	
	/**
	 * The name of the currency used
	 */
	private String money;
	
	//FOR BATTLE
	
	/**
	 * Holds the type of the battle, real-time, progressive or queue
	 */
	private String battleType;
	
	/**
	 * Holds if has random encounters
	 */
	private boolean encounterRandom;
	
	/**
	 * Holds if change the screen for battle
	 */
	private boolean encounterChangeScreen;
	
	/**
	 * Holds the identifier for the status used to dictate the order of the battle
	 */
	private String orderFactor;
	
	/**
	 * Holds the file that takes care of the condition for victory
	 */
	private String conditionVDFile;
	
	/**
	 * Holds the path that takes care of the condition for victory
	 */
	private String conditionVDPath;
	
	/**
	 * Holds the file that takes care of the battle contabilization
	 */
	private String onGoingFile;

	/**
	 * Holds the path that takes care of the battle contabilization
	 */
	private String onGoingPath;
	
	/**
	 * Holds the quantity of players that can participate in a battle at max
	 */
	private int playersInBattle;
	
	/**
	 * Holds the quantity of players that can have in the group, total
	 */
	private int playersInGroup;
	
	/**
	 * Holds the type of actions that can be performed in a battle
	 */
	//private ArrayList<String> actions;
	
	/**
	 * Can change characters during a battle
	 */
	private boolean battleChange;
	
	/**
	 * Holds the type of the physical attack, simple or combo
	 */
	private String attackPhysical;
	
	/**
	 * Holds the type of the magic attack, simple, combo1(same time) or comb02(sequence)
	 */
	private String attackMagical;
	
	/**
	 * Holds if create more than one enemy group in a battle
	 */
	private boolean battleGroup;
	
	//FOR EVOLUTION
	
	/**
	 * The way that can evolve, by gainning experience and/or by using a magic/skill
	 */
	private ArrayList<String> possibleWayEvolve;
	
	/**
	 * Those that can gain experience, Character, Class, Item, Magic
	 */
	private ArrayList<String> whatGainExperience;
	
	/**
	 * Who will learn the skill/magic learned by a class, the class or the user
	 */
	private String classLearn;
	
	/**
	 * Who will learn the skill/magic learned by an item, the item or the user
	 */
	private String itemLearn;
	
	/**
	 * Specify if a Feature can be absorbed, learned by this way
	 */
	private boolean absorbFeature;
	
	/**
	 * Specify if can create itens that have Features
	 */
	private boolean creationOfItem;
	
	/**
	 * Specify if magics can be equipped, like an equippament, but with it own way
	 */
	private boolean equipMagic;
	
	/**
	 * Constructor of the class
	 */
	public RulesSet(){
		//actions = new ArrayList<String>();
		possibleWayEvolve = new ArrayList<String>();
		whatGainExperience = new ArrayList<String>();
	}
	
	public boolean isCharacterBag() {
		return characterBag;
	}

	public void setCharacterBag(boolean characterBag) {
		this.characterBag = characterBag;
	}

	public boolean isGroupBag() {
		return groupBag;
	}

	public void setGroupBag(boolean groupBag) {
		this.groupBag = groupBag;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public boolean isAbsorbFeature() {
		return absorbFeature;
	}

	public void setAbsorbFeature(boolean absorbFeature) {
		this.absorbFeature = absorbFeature;
	}

	/*public ArrayList<String> getActions() {
		return actions;
	}

	public void setActions(ArrayList<String> actions) {
		this.actions = actions;
	}*/

	public String getAttackMagical() {
		return attackMagical;
	}

	public void setAttackMagical(String attackMagical) {
		this.attackMagical = attackMagical;
	}

	public String getAttackPhysical() {
		return attackPhysical;
	}

	public void setAttackPhysical(String attackPhysical) {
		this.attackPhysical = attackPhysical;
	}	

	public boolean isBattleChange() {
		return battleChange;
	}

	public void setBattleChange(boolean battleChange) {
		this.battleChange = battleChange;
	}

	public String getBattleType() {
		return battleType;
	}

	public void setBattleType(String battleType) {
		this.battleType = battleType;
	}

	public String getClassLearn() {
		return classLearn;
	}

	public void setClassLearn(String classLearn) {
		this.classLearn = classLearn;
	}

	public String getConditionVDFile() {
		return conditionVDFile;
	}

	public void setConditionVDFile(String conditionVDFile) {
		this.conditionVDFile = conditionVDFile;
	}

	public String getConditionVDPath() {
		return conditionVDPath;
	}

	public void setConditionVDPath(String conditionVDPath) {
		this.conditionVDPath = conditionVDPath;
	}

	public boolean isCreationOfItem() {
		return creationOfItem;
	}

	public void setCreationOfItem(boolean creationOfItem) {
		this.creationOfItem = creationOfItem;
	}

	public boolean isEncounterChangeScreen() {
		return encounterChangeScreen;
	}

	public void setEncounterChangeScreen(boolean encounterChangeScreen) {
		this.encounterChangeScreen = encounterChangeScreen;
	}

	public boolean isEncounterRandom() {
		return encounterRandom;
	}

	public void setEncounterRandom(boolean encounterRandom) {
		this.encounterRandom = encounterRandom;
	}

	public boolean isEquipMagic() {
		return equipMagic;
	}

	public void setEquipMagic(boolean equipMagic) {
		this.equipMagic = equipMagic;
	}

	public String getItemLearn() {
		return itemLearn;
	}

	public void setItemLearn(String itemLearn) {
		this.itemLearn = itemLearn;
	}

	public String getOnGoingFile() {
		return onGoingFile;
	}

	public void setOnGoingFile(String onGoingFile) {
		this.onGoingFile = onGoingFile;
	}

	public String getOnGoingPath() {
		return onGoingPath;
	}

	public void setOnGoingPath(String onGoingPath) {
		this.onGoingPath = onGoingPath;
	}

	public int getPlayersInBattle() {
		return playersInBattle;
	}

	public void setPlayersInBattle(int playersInBattle) {
		this.playersInBattle = playersInBattle;
	}
	
	public int getPlayersInGroup() {
		return playersInGroup;
	}

	public void setPlayersInGroup(int playersInGroup) {
		this.playersInGroup = playersInGroup;
	}

	public ArrayList<String> getPossibleWayEvolve() {
		return possibleWayEvolve;
	}

	public void setPossibleWayEvolve(ArrayList<String> possibleWayEvolve) {
		this.possibleWayEvolve = possibleWayEvolve;
	}

	public ArrayList<String> getWhatGainExperience() {
		return whatGainExperience;
	}

	public void setWhatGainExperience(ArrayList<String> whatGainExperience) {
		this.whatGainExperience = whatGainExperience;
	}

	public boolean isBattleGroup() {
		return battleGroup;
	}

	public void setBattleGroup(boolean battleGroup) {
		this.battleGroup = battleGroup;
	}

	public String getOrderFactor() {
		return orderFactor;
	}

	public void setOrderFactor(String orderFactor) {
		this.orderFactor = orderFactor;
	}
	
}
 
