package framework.rpgsystem.battle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;
import framework.rpgsystem.evolution.Feature;
import framework.util.ChangeFeature;

/**
 * The enemy that appers during the battle, having their
 * description and the magics/special attacks that the
 * one in question can use
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Enemy {
 	
	/**
	 * An array used to hold the Features of the type status
	 */
	private ArrayList<Feature> status;
	
	/**
	 * An array used to hold the magics, special attacks
	 */
	private ArrayList<Feature> magics;
		
	/**
	 * The complete name of the character
	 */
	private String name;

	/**
	 * The identifier of the character
	 */
	private String identifier;
	
	/**
	 * The type of the enemy
	 */
	private String enemyClass;

	/**
	 * The description of the character
	 */
	private String description;
	
	/**
	 * Holds the file to the initial values
	 */
	private String initialFile;

	/**
	 * Holds the folder to the initial values
	 */
	private String initialPath;
	
	/**
	 * The script used if has something different to execute, like
	 * how walks, the folder
	 */
	private String scriptPath;
	
	/**
	 * The script used if has something different to execute, like
	 * how walks, the file
	 */
	private String scriptFile;
	
	/**
	 * The strategy of the enemy during battle
	 */
	private String strategy;
	
	/**
	 * Holds how much experience the class gains 
	 */
	private int experienceClassGained;
	
	/**
	 * Holds how much experience the character gains
	 */
	private int experienceCharacterGained;
	
	/**
	 * Holds how much experience the item gains
	 */
	private int experienceItemGained;
	
	/**
	 * Holds how much experience the feature gains
	 */
	private int experienceFeatureGained;
	
	/**
	 * Holds how much money the player gains
	 */
	private int moneyGained;
	
	/**
	 * The model used for the battle
	 */
	private String battleModel;
	
	/**
	 * The scale used for the model during battle
	 */
	private float battleScale;
	
	/**
	 * Constructor of the class
	 */
	public Enemy(){
		status = new ArrayList<Feature>();			
		magics = new ArrayList<Feature>(); 		
		initialFile = null;
		initialPath = null;	
		scriptFile = null;
		scriptPath = null;
	}
	
	/**
	 * Method used to create a Character, giving its name to be created, the
	 * name is the identifier
	 * 
	 * @param name
	 *            a String
	 */
	public void createEnemy(Element character){		
		//load everything that is needed
		loadFeatures(character);		
		//set the initial value
		setInitialValues();		
	}

	/**
	 * Method used to load the Features of a character passing its identifier
	 * 
	 * @param name
	 *            a String
	 */
	public void loadFeatures(Element character){

		// load the identifier
		identifier = character.getName();
		//load the information of the character
		loadInformation(character.getChild("Information"));
		//load the Features used for status
		loadStatus(character.getChild("Status"));
		//load the Features used for magics
		loadMagics(character.getChild("Magics"));
		//load the initial values
		loadInitialValues(character.getChild("InitialValue"));
		//load the actions
		loadActions(character.getChild("Actions"));
		//load what is gained
		loadGained(character.getChild("Gained"));
		//load the model and scale used for the battle
		loadBattleModel(character.getChild("BattleModel"));
	}
	
	/**
	 * Method used to load the model and the scale used for this model
	 * during the battle
	 * 
	 * @param bat an Element
	 */
	private void loadBattleModel(Element bat){
		if(bat != null){
			//get the model
			battleModel = bat.getChildText("Model");
			//get the scale
			battleScale = Float.parseFloat(bat.getChildText("Scale"));
		}
	}

	/**
	 * Method used to get the general information
	 * 
	 * @param inform
	 *            an Element
	 */
	public void loadInformation(Element inform){
		if(inform != null){
			//load the information

			//load the name
			name = inform.getChildText("Name");
			//load the type
			enemyClass = inform.getChildText("Class");
			//load the description
			description = inform.getChildText("Description");
		}
	}

	/**
	 * Method used to load the Features of the type status
	 * 
	 * @param status
	 *            an Element
	 */
	public void loadStatus(Element stat){
		if(stat != null){
			//load the status

			//get the children to a list
			List list = stat.getChildren();

			// create the iterator to access the list
			Iterator i = list.iterator();

			// temporary element used to get the elements
			Element temp;

			//loop to get all the elements
			while(i.hasNext()){

				//get the element
				temp = (Element) i.next();

				//load the Feature that is wanted
				status.add(loadFeat(temp.getText(),true));
			}			
		}
	}
	
	/**
	 * Method used to load the Features of the type magic
	 * 
	 * @param magic
	 *            an Element
	 */
	public void loadMagics(Element magic){
		if(magic != null){
			//load the magics

			//get the children to a list
			List list = magic.getChildren();

			// create the iterator to access the list
			Iterator i = list.iterator();

			// temporary element used to get the elements
			Element temp;

			//loop to get all the elements
			while(i.hasNext()){

				//get the element
				temp = (Element) i.next();

				//load the Feature that is wanted
				magics.add(loadFeat(temp.getText(),false));
			}			
			
		}
	}

	/**
	 * Method used to load a Feature, receive what to load, an Element with the
	 * information
	 * 
	 * @param feat
	 *            an Element
	 * @return the Feature loaded
	 */
	public Feature loadFeature(Element feat){
		// create a new Feature and load everything that can have
		Feature newFeature = new Feature();

		//load everything
		newFeature.load(feat);

		//return
		return newFeature;
	}

	/**
	 * Method used to know from which file to load, if from ability or magic
	 * file
	 * 
	 * @param name
	 *            the name of the Feature, a String
	 * @param type
	 *            the type to load, a boolean
	 * @return the Feature wanted
	 */
	public Feature loadFeat(String name, boolean type) {

		// temporary objects
		Element has;

		// will load according to the type, true for ability, false for magic
		if(type){
			// load an ability

			// get the file that has all the abilitys
			has = FactoryManager
			.getFactoryManager()
			.getScriptManager()
			.getReadScript()
			.getRootElement(
					FactoryManager
					.getFactoryManager()
					.getScriptManager()
					.getReadScript()
					.getFileElement(
							RPGSystem.getRPGSystem().getWhere(),
							"Abilitys"));

			// create the ability and return it
			return loadFeature(has.getChild(name));

		}else{
			// load a magic

			//get the file that has all the magics
			has = FactoryManager
			.getFactoryManager()
			.getScriptManager()
			.getReadScript()
			.getRootElement(
					FactoryManager
					.getFactoryManager()
					.getScriptManager()
					.getReadScript()
					.getFileElement(
							RPGSystem.getRPGSystem().getWhere(),
							"Magics"));

			//create the magic and return it
			return loadFeature(has.getChild(name));
		}
	}
	
	/**
	 * Load the script that set the initial values for the character
	 * 
	 * @param initial
	 *            an Element
	 */
	public void loadInitialValues(Element initial){
		//if has
		if(initial != null){
			//get the file
			initialFile = initial.getChildText("File");
			//get the folder
			initialPath = initial.getChildText("Path");			
		}
	}
	
	/**
	 * Load the actions possible, the strategy for battle and a script if has
	 * outside actions
	 * 
	 * @param act
	 *            an Element
	 */
	public void loadActions(Element act){
		//if has
		if(act != null){
			//test if has a script
			if(act.getChild("Script") != null){
				//get the file
				scriptFile = act.getChild("Script").getChildText("File");
				//get the path
				scriptPath = act.getChild("Script").getChildText("Path");
			}
			
			//get the strategy
			strategy = act.getChildText("Strategy");
		}
	}
	
	/**
	 * Load the values that are gained by defeating this enemy,
	 * the values of experience and money
	 * 
	 * @param gained an Element
	 */
	public void loadGained(Element gained){
		
		if(gained != null){
			//get the money
			moneyGained = Integer.parseInt(gained.getChildText("Money"));			
			//get the experience - class
			experienceClassGained = Integer.parseInt(gained.getChildText("ExperienceClass"));
			//get the experience - character			
			experienceCharacterGained = Integer.parseInt(gained.getChildText("ExperienceCharacter"));
			//get the experience - item			
			experienceItemGained = Integer.parseInt(gained.getChildText("ExperienceItem"));
			//get the experience - feature			
			experienceFeatureGained = Integer.parseInt(gained.getChildText("ExperienceFeature"));
		}		
	}

	/**
	 * Used to set the initial values for the character
	 */
	public void setInitialValues(){
		if(initialFile != null && initialPath != null){
			//execute it
			//load the script wanted
			FactoryManager.getFactoryManager().getScriptManager().getReadScript()
			.loadScript(initialPath, initialFile);

			//calls the script to evolve the class
			//System.out.println("Class gained level");
			FactoryManager.getFactoryManager().getScriptManager().getReadScript()
			.executeScript("initialValue", identifier);
		}
	}

	public void printStatusValues(){
		for(int i = 0; i < status.size(); i++){
			System.out.println(status.get(i).getName());
			System.out.println(status.get(i).getSkill().getFeature().getValue());
			System.out.println(status.get(i).getSkill().getFeature().getMaxValue());
		}
	}

	/**
	 * Change the value of a Status, by receiving the name and the new value
	 * 
	 * @param name
	 *            a String
	 * @param value
	 *            an integer
	 */
	public void setStatusValue(String name, int value) {
		//evolve a status of the class
		for(int i = 0; i < status.size(); i++){
			//try if is the status wanted
			if(status.get(i).getName().equals(name)){
				//found then increase the value
				status.get(i).getSkill().getFeature().setValue(value);
			}
		}
	}
	
	/**
	 * Change the current of a Status, by receiving the name and the new value
	 * 
	 * @param name
	 *            a String
	 * @param value
	 *            an integer
	 */
	public void setStatusCurrent(String name, int value){
		//also increase the current value
		for(int i = 0; i < status.size(); i++){
			//try if is the status wanted
			if(status.get(i).getName().equals(name)){
				//found then increase the value
				status.get(i).getSkill().getFeature().setCurrent(value);
			}
		}
	}
		
	/**
	 * Method used to get a Feature by giving the name and its type
	 * 
	 * @param type
	 *            a String
	 * @param name
	 *            a String
	 * @return the Feature or null, if not found
	 */
	public Feature searchFeature(String type,String name){
		//will search according to the type
		if(type.equals("Magic")){
			return getMagic(name);
		}else if(type.equals("Status")){
			return getStatus(name);
		}		
		//return nothing, not found
		return null;
	}

	/**
	 * Method used to get a status by giving the name of the status wanted
	 * 
	 * @param wanted
	 *            a String
	 * @return the Status or null, if not found
	 */
	public Feature getStatus(String wanted){
		for(int i = 0; i < status.size(); i++){
			if(wanted.equals(status.get(i).getName())){
				return status.get(i);
			}
		}		
		//not found
		return null;
	}

	/**
	 * Method used to get a magic by giving the name of the magic wanted
	 * 
	 * @param wanted
	 *            a String
	 * @return the Magic or null, if not found
	 */
	public Feature getMagic(String wanted){
		for(int i = 0; i < magics.size(); i++){
			if(wanted.equals(magics.get(i).getName())){
				return magics.get(i);
			}
		}		
		//not found
		return null;
	}	
	
	public int getStatusValue(String name){
		return getStatus(name).getSkill().getFeature().getValue();
	}
	
	public int getStatusCurrent(String name){
		return getStatus(name).getSkill().getFeature().getCurrent();
	}
	
	public void useFeature(String name, boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){
		
		//get the Feature that is going to be used, the only ones
		//that can be used directly are the Magics
		
		//get the Magic
		getMagic(name).useFeature(userGroup, userPosition, targetGroup, targetPosition);
		
		//in the try phase
		//tryFeature(temp);
		//use it according to the parameters
		//fireFeature(temp);
		//increase the number of times used
		//temp.increaseUsedTimes();
		
		//gains an effect
		//temp.gainsEffectUsed();
							
	}	

	public void fireFeature(Feature fire){
		
	}
	
	public void tryFeature(Feature trying){
		
	}	
				
	public void increaseFeature(ChangeFeature increase){
		//according to the type of the Feature
		if (increase.equals("Magic")) {
			if (increase.getChanges().equals("Cost")) {
				getMagic(increase.getName()).getMagic().getFeature().changeCost(increase.getValue());
			} else if (increase.getChanges().equals("Time")) {
				getMagic(increase.getName()).getMagic().getFeature().changeDurationTime(increase.getValue());
			}
		} else if (increase.equals("Status")) {
			if (increase.getChanges().equals("Cost")) {
				getStatus(increase.getName()).getSkill().getFeature().changeCost(increase.getValue());
			} else if (increase.getChanges().equals("Time")) {
				getStatus(increase.getName()).getSkill().getFeature().changeDurationTime(increase.getValue());
			}else if (increase.getChanges().equals("Value")) {
				getStatus(increase.getName()).getSkill().getFeature().changeValue(increase.getValue());
			}
		}
	}
	
	public void decreaseFeature(ChangeFeature decrease){
		// according to the type of the Feature
		if (decrease.equals("Magic")) {
			if (decrease.getChanges().equals("Cost")) {
				getMagic(decrease.getName()).getMagic().getFeature().changeCost(-decrease.getValue());
			} else if (decrease.getChanges().equals("Time")) {
				getMagic(decrease.getName()).getMagic().getFeature().changeDurationTime(-decrease.getValue());
			}
		} else if (decrease.equals("Status")) {
			if (decrease.getChanges().equals("Cost")) {
				getStatus(decrease.getName()).getSkill().getFeature().changeCost(-decrease.getValue());
			} else if (decrease.getChanges().equals("Time")) {
				getStatus(decrease.getName()).getSkill().getFeature().changeDurationTime(-decrease.getValue());
			}else if (decrease.getChanges().equals("Value")) {
				getStatus(decrease.getName()).getSkill().getFeature().changeValue(-decrease.getValue());
			}
		}
	}

	/**
	 * Method used to get the description of the character
	 * 
	 * @return a String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method used to change the description of the character
	 * 
	 * @param description
	 *            a String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method used to get the identifier of the character
	 * 
	 * @return a String
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Method used to change the identifier of the character
	 * 
	 * @param identifier
	 *            a String
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Method used to get the name of the character
	 * 
	 * @return a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used to change the name of the character
	 * 
	 * @param name
	 *            a String
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getEnemyClass() {
		return enemyClass;
	}

	public void setEnemyClass(String enemyClass) {
		this.enemyClass = enemyClass;
	}

	public String getScriptFile() {
		return scriptFile;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public String getStrategy() {
		return strategy;
	}

	public int getExperienceCharacterGained() {
		return experienceCharacterGained;
	}

	public void setExperienceCharacterGained(int experienceCharacterGained) {
		this.experienceCharacterGained = experienceCharacterGained;
	}

	public int getExperienceClassGained() {
		return experienceClassGained;
	}

	public void setExperienceClassGained(int experienceClassGained) {
		this.experienceClassGained = experienceClassGained;
	}

	public int getExperienceFeatureGained() {
		return experienceFeatureGained;
	}

	public void setExperienceFeatureGained(int experienceFeatureGained) {
		this.experienceFeatureGained = experienceFeatureGained;
	}

	public int getExperienceItemGained() {
		return experienceItemGained;
	}

	public void setExperienceItemGained(int experienceItemGained) {
		this.experienceItemGained = experienceItemGained;
	}

	public int getMoneyGained() {
		return moneyGained;
	}

	public void setMoneyGained(int moneyGained) {
		this.moneyGained = moneyGained;
	}
	
	public String getBattleModel() {
		return battleModel;
	}

	public float getBattleScale() {
		return battleScale;
	}
	
}
 
