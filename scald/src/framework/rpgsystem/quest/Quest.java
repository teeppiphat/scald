package framework.rpgsystem.quest;

/**
 * Responsible for having the data that is needed for
 * a quest that is used in the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Quest {

	/**
	 * The state of the quest, I - inactive, A - active, C - completed and F - finished
	 * inactive means that hasn´t been done yet
	 * active means that is happening
	 * complete means that got everything needed
	 * finished means that has given the itens and money to whoever asked for it
	 */
	private char state;

	/**
	 * The name of the Quest
	 */
	private String name;

	/**
	 * The description used for the quest
	 */
	private String description;
	
	/**
	 * The description of the objective
	 */
	private String objective;

	/**
	 * The identifier of each quest that depends
	 */
	private String[] questDependencies;

	/**
	 * The dependencies of determined itens to the quest
	 */
	private String[] itemDependencies;
	
	/**
	 * The quantity of the itens needed
	 */
	private int[] itemDependenciesQuantity;

	/**
	 * The level needed for the quest, if not used equals -1
	 */
	private int levelDependencie;
	
	/**
	 * The NPCs that must talk to
	 */
	private String[] NPCTalk;
	
	/**
	 * Keep with which NPC already talked
	 */
	private boolean[] NPCTalked;
	
	/**
	 * If must talk in the order of NPCs loaded
	 */
	private boolean order;
	
	/**
	 * The name of the Items that are needed
	 */
	private String[] getItem;
	
	/**
	 * The number of the items that were gotten
	 */
	private int[] numberItemGot;
	
	/**
	 * The name of the monsters that must kill
	 */
	private String[] monsterKill;
	
	/**
	 * The quantity of the monsters killed
	 */
	private int[] monsterKilled;

	/**
	 * The money needed for the quest
	 */
	private int moneyNeeded;
	
	/**
	 * The name of the item rewarded
	 */
	private String[] rewardItem;
	
	/**
	 * The quantity rewarded
	 */
	private int[] rewardedItem;
	
	/**
	 * The money rewarded
	 */
	private int moneyRewarded;
	
	/**
	 * The name of the feature that increases
	 */
	private String[] featureName;
	
	/**
	 * The type of the feature
	 */
	private String[] featureType;
	
	/**
	 * The value that increases
	 */
	private int[] featureValue;
	
	/**
	 * Constructor of the class
	 */
	public Quest() {
		state = 'I';
	}
	
	public void createNPCTalkControl(){
		//create the size of the array
		NPCTalked = new boolean[NPCTalk.length];
		
		//all of them must be false
		for(int i = 0; i < NPCTalked.length; i++){
			NPCTalked[i] = false;
		}
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getItemDependencies() {
		return itemDependencies;
	}

	public void setItemDependencies(String[] itemDependencies) {
		this.itemDependencies = itemDependencies;
	}
	
	public int[] getItemDependenciesQuantity() {
		return itemDependenciesQuantity;
	}

	public void setItemDependenciesQuantity(int[] itemDependenciesQuantity) {
		this.itemDependenciesQuantity = itemDependenciesQuantity;
	}

	public int getLevelDependencie() {
		return levelDependencie;
	}

	public void setLevelDependencie(int levelDependencie) {
		this.levelDependencie = levelDependencie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String[] getQuestDependencies() {
		return questDependencies;
	}

	public void setQuestDependencies(String[] questDependencies) {
		this.questDependencies = questDependencies;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}

	public String[] getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String[] featureName) {
		this.featureName = featureName;
	}

	public String[] getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String[] featureType) {
		this.featureType = featureType;
	}

	public int[] getFeatureValue() {
		return featureValue;
	}

	public void setFeatureValue(int[] featureValue) {
		this.featureValue = featureValue;
	}

	public String[] getGetItem() {
		return getItem;
	}

	public void setGetItem(String[] getItem) {
		this.getItem = getItem;
	}

	public int getMoneyNeeded() {
		return moneyNeeded;
	}

	public void setMoneyNeeded(int moneyNeeded) {
		this.moneyNeeded = moneyNeeded;
	}

	public int getMoneyRewarded() {
		return moneyRewarded;
	}

	public void setMoneyRewarded(int moneyRewarded) {
		this.moneyRewarded = moneyRewarded;
	}

	public String[] getMonsterKill() {
		return monsterKill;
	}

	public void setMonsterKill(String[] monsterKill) {
		this.monsterKill = monsterKill;
	}

	public int[] getMonsterKilled() {
		return monsterKilled;
	}

	public void setMonsterKilled(int[] monsterKilled) {
		this.monsterKilled = monsterKilled;
	}

	public String[] getNPCTalk() {
		return NPCTalk;
	}

	public void setNPCTalk(String[] talk) {
		NPCTalk = talk;
	}

	public boolean[] getNPCTalked() {
		return NPCTalked;
	}

	public void setNPCTalked(boolean[] talked) {
		NPCTalked = talked;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public int[] getNumberItemGot() {
		return numberItemGot;
	}

	public void setNumberItemGot(int[] numberItemGot) {
		this.numberItemGot = numberItemGot;
	}

	public int[] getRewardedItem() {
		return rewardedItem;
	}

	public void setRewardedItem(int[] rewardedItem) {
		this.rewardedItem = rewardedItem;
	}

	public String[] getRewardItem() {
		return rewardItem;
	}

	public void setRewardItem(String[] rewardItem) {
		this.rewardItem = rewardItem;
	}
	
	
}
