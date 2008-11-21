package framework.rpgsystem.evolution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;
import framework.rpgsystem.item.Bag;
import framework.rpgsystem.item.Item;
import framework.util.ChangeFeature;
import framework.util.EvolveTogetherFeature;
import framework.util.LearnFeature;
import framework.util.LearnTogetherFeature;

/**
 * Has the atributes used in the character and the other
 * necessities for them, like itens and experience
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Character implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the itens of the characters
	 */
	private Bag bag;

	/**
	 * Holds the table experience for the character
	 */
	private CharacterXPTable characterXPTable;

	/**
	 * An array used to hold the Features of the type status
	 */
	private ArrayList<Feature> status;

	/**
	 * An array used to hold the Features of the type skills
	 */
	private ArrayList<Feature> skills;

	/**
	 * An array used to hold the restrictions
	 */
	private ArrayList<Feature> restrictions;

	/**
	 * An array used to hold the magics, special attacks
	 */
	private ArrayList<Feature> magics;

	/**
	 * An array used to hold the equipament that the character is using
	 */
	private ArrayList<Item> equipament;

	/**
	 * Holds the classes that the character has
	 */
	private RPGClass[] classes;

	/**
	 * The complete name of the character
	 */
	private String name;

	/**
	 * The identifier of the character
	 */
	private String identifier;

	/**
	 * The description of the character
	 */
	private String description;

	/**
	 * The class at the moment
	 */
	private int classMoment;

	/**
	 * Holds the level of the class, at the present moment
	 */
	private int level;

	/**
	 * Holds the experience of the class, at the moment
	 */
	private int experience;

	/**
	 * Holds the file to the initial values
	 */
	private String initialFile;

	/**
	 * Holds the folder to the initial values
	 */
	private String initialPath;
	
	/**
	 * Holds the file of the image that represents the item in the menu
	 */
	private String imageFile;
	
	/**
	 * Holds the path of the image that represents the item in the menu
	 */
	private String imagePath;
	
	/**
	 * The model used for the battle
	 */
	private String battleModel;
	
	/**
	 * The scale used for the model during battle
	 */
	private float battleScale;
	
	/**
	 * Constructor, responsible for initialization of its components
	 */
	public Character(){
		characterXPTable = null;		
		bag = null;
		status = new ArrayList<Feature>();
		skills = new ArrayList<Feature>();
		restrictions = new ArrayList<Feature>();
		magics = new ArrayList<Feature>(); 
		equipament = new ArrayList<Item>();
		initialFile = null;
		initialPath = null;
		imageFile = imagePath = null;
		battleModel = null;
		battleScale = 0.0f;
	}

	/**
	 * Method used to create a Character, giving its name to be created, the
	 * name is the identifier
	 * 
	 * @param name
	 *            a String
	 */
	public void createCharacter(Element character){
		//set the level for the character
		level = 1;
		//set the experience at 0, first level
		experience = 0;		
		//load everything that is needed
		loadFeatures(character);		
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
		//load the information of the class
		loadClasses(character.getChild("Classes"));
		//load the Features used for status
		loadStatus(character.getChild("Status"));
		//load the Features used for restrictions
		loadRestrictions(character.getChild("Restrictions"));
		//load the Features used for skills
		loadSkills(character.getChild("Skills"));
		//load the XpTable
		loadXPTable(character.getChild("XPTable"));		
		//load how the class evolves, its status
		loadEvolve(character.getChild("Evolve"));
		//load the Features that are learned
		loadLearned(character.getChild("Learn"));
		//load the Bag characteristics
		loadBag(character.getChild("Bag"));		
		//load the initial values
		loadInitialValues(character.getChild("InitialValue"));
		//load the image used for the menu
		loadMenuImage(character.getChild("MenuImage"));
		//load the model and scale used for the battle
		loadBattleModel(character.getChild("BattleModel"));

	}

	/**
	 * Method used to load the images used to represent the menu,
	 * has the place and the name of the image used
	 * 
	 * @param image an Element
	 */
	private void loadMenuImage(Element image) {
		if(image != null){
			//get the file
			imageFile = image.getChildText("File");
			//get the path
			imagePath = image.getChildText("Path");
		}		
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
			//load the description
			description = inform.getChildText("Description");
		}
	}

	/**
	 * Method used to get the class(es) that the character can have
	 * 
	 * @param cla
	 *            an Element
	 */
	public void loadClasses(Element cla){
		if(cla != null){
			//load the class(es)

			//get the list
			List list = cla.getChildren();
			//Element to get the elements first then pass to String
			Element pass;
			//String to get the classes
			String temp;			
			//allocate the array
			classes = new RPGClass[list.size()];

			//load all the classes
			for(int i = 0; i < list.size(); i++){
				//get the object
				pass = (Element)list.get(i);
				//get the class
				temp = pass.getText();
				//create the class
				classes[i] = new RPGClass();
				//ask to create it
				classes[i].createClass(temp);
			}

			// indicate the first class as the one used in the beggining, the
			// position in the array
			classMoment = 0;

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
	 * Method used to load the Features of the type restrictions
	 * 
	 * @param restriction
	 *            an Element
	 */
	public void loadRestrictions(Element rest){
		if(rest != null){
			//load the restrictions

			//get the children to a list
			List list = rest.getChildren();

			// create the iterator to access the list
			Iterator i = list.iterator();

			// temporary element used to get the elements
			Element temp;

			//loop to get all the elements
			while(i.hasNext()){

				//get the element
				temp = (Element) i.next();

				//load the Feature that is wanted
				restrictions.add(loadFeat(temp.getText(),true));
			}
		}
	}

	/**
	 * Method used to load the Features of the type skills
	 * 
	 * @param skill
	 *            an Element
	 */
	public void loadSkills(Element skill){
		if(skill != null){
			//load the skills

			// get the children to a list
			List list = skill.getChildren();

			// create the iterator to access the list
			Iterator i = list.iterator();

			// temporary element used to get the elements
			Element temp;

			//loop to get all the elements
			while(i.hasNext()){

				//get the element
				temp = (Element) i.next();

				//load the Feature that is wanted
				skills.add(loadFeat(temp.getText(),true));
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
	 * Method used to know from which file to load, if from ability or magic file
	 * @param name the name of the Feature, a String
	 * @param type the type to load, a boolean
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
	 * Method used to load the table of experience
	 * 
	 * @param table
	 *            an Element
	 */
	public void loadXPTable(Element table){
		//if the class evolves
		if(table != null){
			//initializes the table
			characterXPTable = new CharacterXPTable();
			//load the XP table
			characterXPTable.getTable(table.getChildText("Path"), table
					.getChildText("File"), Integer.parseInt(table
							.getChildText("MaxLevel")));
		}
	}

	/**
	 * Method used to keep the script responsible for evolving the status
	 * 
	 * @param evolve
	 *            an Element
	 */
	public void loadEvolve(Element evolve){
		if(evolve != null){
			//initialize the XP Table
			characterXPTable = new CharacterXPTable();
			//get the file
			characterXPTable.getCharacterXPTableEntry().setStatusFile(evolve.getChildText("File"));
			//get the path
			characterXPTable.getCharacterXPTableEntry().setStatusPath(evolve.getChildText("Path"));
		}
	}

	/**
	 * Method used to load the Features that are learned
	 * 
	 * @param learn
	 *            an Element
	 */
	public void loadLearned(Element learn) {
		if (learn != null) {
			// get all the Features that can be learned
			List list = learn.getChildren();
			// temporary object to get one of them
			Element temp;

			// load all the Features
			for (int i = 0; i < list.size(); i++) {
				// get the one that is learned
				temp = (Element) list.get(i);

				// create a temporary object used to load
				LearnFeature learned = new LearnFeature();

				// get the name
				learned.setName(temp.getChildText("Name"));

				// get the way is learned
				learned.setWay(temp.getChildText("Way"));

				// get the one who learns the new Feature
				learned.setWho(Boolean.parseBoolean(temp.getChildText("Who")));

				// get the type of the Feature, the general type
				learned.setType(temp.getChildText("Type"));

				// according to the way that is learned load the rest
				if (learned.getWay().equals("Level")) {
					// get the level when is learned
					learned.setLevel(Integer.parseInt(temp
							.getChildText("Level")));
				} else {
					// tell to load the together
					learned.setTogether(loadLearnTogether(temp
							.getChild("Together")));
				}

				// keep the object
				characterXPTable.getCharacterXPTableEntry().addLearnFeature(
						learned);
			}
		}
	}

	/**
	 * Method used to load the Feature(s) that is(are) needed to learn another
	 * Feature and level
	 * 
	 * @param together
	 *            an Element
	 * @return an ArrayList of the class LearnTogetherFeature
	 */
	public ArrayList<LearnTogetherFeature> loadLearnTogether(Element together){
		//create a temporary object to load
		ArrayList<LearnTogetherFeature> tog = new ArrayList<LearnTogetherFeature>();

		//get all the Features
		List list = together.getChildren();

		//to get one Feature
		Element temp;

		//get all the children that are needed
		for(int i = 0; i < list.size(); i++){
			//get one Feature
			temp = (Element)list.get(i);
			//put it inside the array
			tog.add(new LearnTogetherFeature(temp.getChildText("Name"), Integer
					.parseInt(temp.getChildText("Level")),temp.getChildText("Type")));			
		}

		//return the array loaded
		return tog;
	}

	/**
	 * Method used to load the characteristics of the bag of the character
	 * 
	 * @param bag
	 *            an Element
	 */
	public void loadBag(Element bag){
		if(bag != null){
			//load the characteristics
			//if has bag, only load the maximum size
			this.bag = new Bag();
			//set the maximum size of the bag
			this.bag.setMaxEntrys(Integer.parseInt(bag.getChildText("MaximumSlots")));						
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
		}else{
			//execute it for the class
			classes[classMoment].setInitialValues(identifier);
		}
	}

	/**
	 * Evolve determined Status, by receiving its name and how much earns
	 * 
	 * @param name
	 *            a String
	 * @param increase
	 *            an integer
	 */
	public void evolveFeature(String name, int increase){

		//evolve a status of the class
		for(int i = 0; i < status.size(); i++){
			//try if is the status wanted
			if(status.get(i).getName().equals(name)){
				//found then increase the value
				status.get(i).getSkill().getFeature().increaseValue(increase);
			}
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
	 * Method used to pass the experience to those that evolve, according to the rules that were set
	 * @param xpCharacter the experience for the Character, an integer
	 * @param xpClass the experience for the Class, an integer
	 * @param xpItem the experience for the Item, an integer
	 * @param xpFeature the experience for the Feature, an integer
	 */
	public void gainsExperience(int xpCharacter,int xpClass,int xpItem,int xpFeature){
		//the character is responsible for passing the experince for others
		for(int i = 0; i < FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getWhatGainExperience().size(); i++){
			//test who gains experience
			//test if the character evolves
			if (FactoryManager.getFactoryManager().getRulesManager()
					.getRulesSet().getWhatGainExperience().get(i).equals(
					"Character")) {
				gainsExperienceCharacter(xpCharacter);
			}
			// test if the class evolves
			if (FactoryManager.getFactoryManager().getRulesManager()
					.getRulesSet().getWhatGainExperience().get(i).equals(
					"Class")) {
				gainsExperienceClass(xpClass);
			} // test if the item evolves
			if (FactoryManager.getFactoryManager().getRulesManager()
					.getRulesSet().getWhatGainExperience().get(i)
					.equals("Item")) {
				gainsExperienceItem(xpItem);
			}
			// test if the Feature evolves
			if (FactoryManager.getFactoryManager().getRulesManager()
					.getRulesSet().getWhatGainExperience().get(i).equals(
					"Feature")) {				
				//the characters Features
				gainsExperienceFeatures(xpFeature);
				//the class Features
				classes[classMoment].gainsExperienceFeatures(xpFeature,identifier);
			}
		}
	}

	/**
	 * Method used to increase the experience, for the character
	 * 
	 * @param gains
	 *            the value to increase, an integer
	 */
	public void gainsExperienceCharacter(int gains){

		//if the character can evolve
		if(characterXPTable != null){
			//gains experience
			experience = experience + gains;
			//if the amount of experience surpass the required for the next level
			//level 2 is position 1 of the array, then not a problem

			//need to know how many levels passed, will make once for
			//each level that passed
			while(experience >= characterXPTable.getXP(level)){
				//increase the level
				level++;
				//calls the script responsible for evolving the class
				characterXPTable.getCharacterXPTableEntry().gainLevel(level,identifier);
				//test if a Feature was learned by the evolution of the class
				learnsNewFeaturesLevel();				
			}
		}
	}

	/**
	 * Method used to increase the experience, for the class
	 * 
	 * @param gains
	 *            the value to increase, an integer
	 */
	public void gainsExperienceClass(int gains){
		//tells that the class at the moment gains experience
		classes[classMoment].gainsExperienceClass(gains,identifier);
		//if the Features learned don´t stay with the class
		if(!classes[classMoment].getLearnsFeatures().isEmpty()){
			//get all the Features that were learned and clean it
			for(int i = 0; i < classes[classMoment].getLearnsFeatures().size(); i++){
				//get the Feature and put it in its place
				addFeature(classes[classMoment].getLearnsFeatures().get(i));				
			}			
		}
	}

	public void addFeature(Feature add){		
		//according to the type will place it
		if(add.getType().equals("Skill")){
			if(!skills.contains(add)){
				//get a new skill
				skills.add(add);
			}
		}else if(add.getType().equals("Status")){
			if(!status.contains(add)){
				//get a new status
				status.add(add);
			}
		}else if(add.getType().equals("Restriction")){
			if(restrictions.contains(add)){
				//get a new restriction
				restrictions.add(add);
			}
		}else if(add.getType().equals("Magic")){
			if(magics.contains(add)){
				//get a new magic
				magics.add(add);
			}
		}		
	}

	/**
	 * Method used to increase the experience, for the Features
	 * 
	 * @param gains
	 *            the value to increase, an integer
	 */
	public void gainsExperienceFeatures(int gains){
		//tells the Features that evolve to gain experience

		//pass by all the Features

		if(characterXPTable != null){

			//status
			for(int i = 0; i < status.size(); i++){
				//get the Feature
				status.get(i).gainsExperience(gains,identifier);
				//get the Features that were learned and pass them to the Character
				if(!status.get(i).getLearnsFeatures().isEmpty()){
					//get all the Features that were learned and clean it
					for(int k = 0; k < status.get(i).getLearnsFeatures().size(); k++){
						//get the Feature and put it in its place
						addFeature(status.get(i).getLearnsFeatures().get(k));				
					}
				}
			}		
			//skills
			for(int i = 0; i < skills.size(); i++){
				//get the Feature
				skills.get(i).gainsExperience(gains,identifier);
				//get the Features that were learned and pass them to the Character			
				if(!skills.get(i).getLearnsFeatures().isEmpty()){
					//get all the Features that were learned and clean it
					for(int k = 0; k < skills.get(i).getLearnsFeatures().size(); k++){
						//get the Feature and put it in its place
						addFeature(skills.get(i).getLearnsFeatures().get(k));				
					}
				}
			}		
			//restrictions
			for(int i = 0; i < restrictions.size(); i++){
				//get the Feature
				restrictions.get(i).gainsExperience(gains,identifier);
				//get the Features that were learned and pass them to the Character
				if(!restrictions.get(i).getLearnsFeatures().isEmpty()){
					//get all the Features that were learned and clean it
					for(int k = 0; k < restrictions.get(i).getLearnsFeatures().size(); k++){
						//get the Feature and put it in its place
						addFeature(restrictions.get(i).getLearnsFeatures().get(k));				
					}
				}
			}		
			//magics
			for(int i = 0; i < magics.size(); i++){
				//get the Feature
				magics.get(i).gainsExperience(gains,identifier);
				//get the Features that were learned and pass them to the Character
				if(!magics.get(i).getLearnsFeatures().isEmpty()){
					//get all the Features that were learned and clean it
					for(int k = 0; k < magics.get(i).getLearnsFeatures().size(); k++){
						//get the Feature and put it in its place
						addFeature(magics.get(i).getLearnsFeatures().get(k));				
					}
				}
			}

			//test if a Feature was learned by the evolution of other Features
			learnsNewFeatureTogether();
		}
	}

	/**
	 * Method used to increase the experience, for the equipament
	 * 
	 * @param gains
	 *            the value to increase, an integer
	 */
	public void gainsExperienceItem(int gains){
		//tells the Items that evolve to gain experience

		//pass only by those Items that are equipped

		//equipament
		for(int i = 0; i < equipament.size(); i++){
			//pass experience to the item
			equipament.get(i).gainsExperience(gains);
			//if the item doen´t hold the Features
			if(!equipament.get(i).getLearnsFeatures().isEmpty()){
				//get all the Features that were learned and clean it
				for(int k = 0; k < equipament.get(i).getLearnsFeatures().size(); k++){
					//get the Feature and put it in its place
					addFeature(equipament.get(i).getLearnsFeatures().get(k));				
				}
			}
		}
	}

	/**
	 * Method used to learn a new Feature
	 * 
	 * @param name
	 *            the identifier of the Feature, a String
	 * @param type
	 *            the type of the Feature, a String
	 */	
	public void learnFeature(String name, String type){
		
		if(!testExist(name, type)){
			//according to the type will place it
			if(type.equals("Skill")){			
				//load a new skill
				skills.add(loadFeat(name,false));
			}else if(type.equals("Status")){
				//load a new status
				status.add(loadFeat(name,true));
			}else if(type.equals("Magic")){
				//load a new magic
				magics.add(loadFeat(name,false));
			}else if(type.equals("Restriction")){
				//load a new restriction
				restrictions.add(loadFeat(name,true));
			}
		}
	}
	
	public boolean testExist(String name, String type){
		//according to the type will place it
		if(type.equals("Skill")){			
			//load a new skill
			if(getSkill(name)!= null)
				return true;			
		}else if(type.equals("Status")){
			if(getStatus(name) != null)
				return true;			
		}else if(type.equals("Magic")){
			if(getMagic(name) != null)
				return true;			
		}else if(type.equals("Restriction")){
			if(getRestriction(name) != null)
				return true;
		}
		
		//doesn´t have
		return false;
	}
	
	/**
	 * Method used to ask to learn a new Feature by the level of the class
	 */
	public void learnsNewFeaturesLevel(){
		//temporary object used for test
		LearnFeature temp;

		//will test all the Features that can learn
		for(int i = 0; i < characterXPTable.getCharacterXPTableEntry().getLearns().size(); i++){
			//get one of the Features
			temp = characterXPTable.getCharacterXPTableEntry().getLearns().get(i);

			//test the LearnFeature, first the type it is, if is by Level
			if(temp.getWay().equals("Level")){
				//if by level test the level, if the same must Learn
				if(temp.getLevel() == level){
					//learn the Feature
					learnFeature(temp.getName(),temp.getType());
				}
			}	
		}		
	}

	/**
	 * Method used to test if can learn a new Feature that is learned by the
	 * learn together way
	 */
	public void learnsNewFeatureTogether(){
		// temporary object used for test
		LearnFeature temp;

		//will test all the Features that can learn
		for(int i = 0; i < characterXPTable.getCharacterXPTableEntry().getLearns().size(); i++){
			//get one of the Features
			temp = characterXPTable.getCharacterXPTableEntry().getLearns().get(i);

			//test the LearnFeature, first the type it is, if is by Level
			if(temp.getWay().equals("Together")){
				//if learns together, then must ask for all the ones that must reached a certain level				
				if(testLearnTogether(temp.getTogether())){
					// learn the Feature
					learnFeature(temp.getName(),temp.getType());
				}
			}	
		}	
	}
	
	/**
	 * Method used to learn a Feature that was used, in this case only Features
	 * that can be directly used can be learned this way, in this case of the
	 * type Magic
	 * 
	 * @param name
	 *            the name of the Magic, a String
	 */
	public void learnsNewFeatureUsed(String name){
		
		//temporary object
		Feature temp;
		//temporary object
		FeatureEntry entry;
		
		//get the Feature
		temp = getMagic(name);
		
		//test all the ones that are learned by the use of another
		for(int i = 0; i < temp.getFeatureEntry().size(); i++){
			//get one Entry
			entry = temp.getFeatureEntry().get(i);
			//test the Entry
			if(testLearnUsed(entry.getConstraint().getMustUse(),temp)){
				//learn the Feature
				learnFeature(entry.getName(),entry.getType());
			}
		}		
	}	
	
	/**
	 * Method used to test if can be learned by the used way, receives what
	 * needs to test with and the Feature and return true or false that was used
	 * 
	 * @param used
	 *            an ArrayList
	 * @param compared
	 *            a Feature
	 * @return a boolean
	 */
	public boolean testLearnUsed(ArrayList<LearnTogetherFeature> used, Feature compared){
		//temporary object
		LearnTogetherFeature temp;
		
		//goes throught all the Features that are needed
		for(int i = 0; i < used.size(); i++){
			//get one of the elements
			temp = used.get(i);
			
			//test the number of times that was used
			if(compared.getUsedTimes() == temp.getLevel()){
				//then can learn				
			}else{
				//can´t learn
				return false;
			}			
		}
		
		//can learn
		return true;
	}

	/**
	 * Method used to test the Features needed to learn another feature,
	 * receives the array of Features needed and returns if can learn, true, or
	 * cannot, false
	 * 
	 * @param tog
	 *            an ArrayList of LearnTogetherFeature
	 * @return a boolean
	 */
	public boolean testLearnTogether(ArrayList<LearnTogetherFeature> tog){

		//temporary object to help
		LearnTogetherFeature temp;
		//temporary object to help
		Feature test;

		//test all of them
		for(int i = 0; i < tog.size(); i++){			
			//get one of the elements
			temp = tog.get(i);
			//test the element

			//get the Feature needed
			test = searchFeature(temp.getType(), temp.getName());

			//first test if the Feature was found
			if(test != null){
				//then test the level of the Feature
				if(temp.getLevel() == test.getMainFeature().getLevel()){
					//then can learn
				}else{
					// if isn´t at the necessary level, don´t need to keep
					// looking for
					return false;
				}
			}else{
				// if doens´t have one of the Features needed there´s no need to
				// keep looking for
				return false;
			}
		}

		//can learn
		return true;
	}


	/**
	 * Method used to get a Feature by giving the name and its type
	 * @param type a String
	 * @param name a String
	 * @return the Feature or null, if not found
	 */
	public Feature searchFeature(String type,String name){
		//will search according to the type
		if(type.equals("Magic")){
			return getMagic(name);
		}else if(type.equals("Skill")){
			return getSkill(name);
		}else if(type.equals("Restriction")){
			return getRestriction(name);
		}else if(type.equals("Status")){
			return getStatus(name);
		}		
		//return nothing, not found
		return null;
	}

	/**
	 * Method used to get a skill by giving the name of the skill wanted
	 * @param wanted a String
	 * @return the Skill or null, if not found
	 */
	public Feature getSkill(String wanted){
		for(int i = 0; i < skills.size(); i++){
			if(wanted.equals(skills.get(i).getName())){
				return skills.get(i);
			}
		}		
		//not found
		return null;
	}

	/**
	 * Method used to get a status by giving the name of the status wanted
	 * @param wanted a String
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
	 * Method used to get a restriction by giving the name of the restriction wanted
	 * @param wanted a String
	 * @return the Restriction or null, if not found
	 */
	public Feature getRestriction(String wanted){
		for(int i = 0; i < restrictions.size(); i++){
			if(wanted.equals(restrictions.get(i).getName())){
				return restrictions.get(i);
			}
		}		
		//not found
		return null;
	}

	/**
	 * Method used to get a magic by giving the name of the magic wanted
	 * @param wanted a String
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
	
	public void gainsExperienceUsed(Feature feature){
		//the Feature is going to gain experience, if is more then 0
		if(feature.getMainFeature().getNeedGains() > 0){
			//gives experience to this feature
			feature.gainsExperienceByUse(feature.getMainFeature().getNeedGains(), identifier);
			//gives experience to those that gain together
			gainsExperienceTogether(feature.getMainFeature().getEvolveTogether(), feature.getMainFeature().getNeedGains());
		}
	}
	
	public void gainsExperienceTogether(ArrayList<EvolveTogetherFeature> array,int value){
		//temporary object
		EvolveTogetherFeature temp;
		//temporary object
		Feature feat = null;
		//temporary object
		int gains;
		
		//all the object to give them experience
		for(int i = 0; i < array.size(); i++){
			//get one object
			temp = array.get(i);			
			//get the Feature that gains experience
			feat = searchFeature(temp.getType(), temp.getName());
			
			//test if the feature was found
			if(feat != null){
				//make the calculation for how much gains
				gains = (value*temp.getExperience())/100;				
				//gives experience to the Feature
				feat.increaseExperience(gains, identifier);				
			}
		}		
	}
	
	public void useFeature(String name, boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){
		
		//first test if has the Feature
		if(getMagic(name) != null){
			//use the magic that has
			getMagic(name).useFeature(userGroup, userPosition, targetGroup, targetPosition);
			//gain experience by being used, a fixed value
			gainsExperienceUsed(getMagic(name));			
			//test if learn something
			learnsNewFeatureUsed(name);
		}else{
			//try to use the one that is in the class
			useClassFeature(name,userGroup, userPosition, targetGroup, targetPosition);
		}
		
		//get the Feature that is going to be used, the only ones
		//that can be used directly are the Magics
		
		//get the Magic
		//Feature temp = getMagic(name);
		//in the try phase
		//tryFeature(temp);
		//use it according to the parameters
		//fireFeature(temp);
		//increase the number of times used
		//temp.increaseUsedTimes();
		
		//gains an effect
		//temp.gainsEffectUsed();
		
	}	

	public Item findEquipament(String typeOf){
		//try to find the equipament type wanted
		for(int i = 0; i < equipament.size(); i++){
			//test if has the type wanted
			if(equipament.get(i).getEquipament().getFeature().getTypeOf().get(1).equals(typeOf)){
				//found the type of equipament
				return equipament.get(i);
			}
		}		
		//not found
		return null;
	}
	
	public Item findEquipamentRestriction(String typeOf){
		// try to find the equipament type wanted
		for (int i = 0; i < equipament.size(); i++) {
			// test if has the type wanted
			if (equipament.get(i).getWhereIsEquipped().size() > 2) {
				// test the size 2 to know if is wanted
				if (equipament.get(i).getWhereIsEquipped().get(2)
						.equals(typeOf)) {
					// was found
					return equipament.get(i);
				}
			} else {
				// test if is one of the restrictions asked for
				if (equipament.get(i).getWhereIsEquipped().get(1).equals(typeOf)) {
					return equipament.get(i);
				}
			}
		}		
		//not found
		return null;
	}
	
	public Feature getSkillNeedEquip(String specific){
		//look for a skill that lets use this equipament
		for(int i = 0; i < skills.size(); i++){			
			//to know if is a skill that lets equip
			if(skills.get(i).getSkill().getFeature().getTypeOf().get(0).equals("Equipament")){			
				//find the type of the item that can use
				if(skills.get(i).getSkill().getFeature().getTypeOf().get(1).equals(specific)){
					//found the skill needed to equip
					return skills.get(i);
				}				
			}
		}
		//not found
		return null;
	}
	
	public Feature getSkillInEquip(String specific){
		//look for the skill in an equipped item
		for(int i = 0; i < equipament.size(); i++){
			//try to find the skill
			if(equipament.get(i).getSkillNeedEquip(specific) != null){
				return equipament.get(i).getSkillNeedEquip(specific);
			}
		}
		//not found
		return null;
	}
	
	public boolean removeItem(String item){
		
		//search for the item in the equipament
		for(int i =0; i < equipament.size(); i++){
			if(equipament.get(i).getName().equals(item)){
				removeEquipament(equipament.get(i));
				return true;
			}
		}
		
		//not removed
		return false;
	}
		
	public void equipItem(Item equip){
		//temporary object to get the resctriction
		Feature restriction[] = new Feature[equip.getWhereIsEquipped().size()];		
		//temporary object to get the skill
		Feature skill = null;
		//temporary object to get the general type, like Weapon
		//String general = equip.getEquipament().getFeature().getTypeOf().get(0);
		//temporary object to get the greater specification, like Sword
		String specific = equip.getEquipament().getFeature().getTypeOf().get(1);;
						
		//must find from who to try the skills, from the character or from the class
		
		//look for the skill in the character
		skill = getSkillNeedEquip(specific);
		
		//if didn´t find in the character then try the class
		if(skill == null){
			//look for the skill in the class
			skill = classes[classMoment].getSkillNeedEquip(specific);
		}
		
		//if didn´t find in the character and in the class try the item
		if(skill == null){
			//look for the skill in the equipped items
			skill = getSkillInEquip(specific);
		}
			
		//if was found can equip
		if(skill != null){
						
			//get the resctrictions according
			for(int i = 0; i < restriction.length; i++){
				//get a restriction
				restriction[i] = getRestriction(equip.getWhereIsEquipped().get(i));			
			}
			
			//if has the need for only one restriction
			if(restriction.length == 1){
				
				//try to equip the item, test if has an equip in the same place
				//use the typeOf to find the same type of item
				
				//temporary Item, that will be removed from the equipament
				Item found;
				
				//find if has the item
				found = findEquipament(specific);
				
				//test if will remove an item or not
				if(found != null){
					//remove the item found, decrease the status changed and add it to the bag
					removeEquipament(found);					
				}				
				
				//put the new item in the equipament, increase the status and remove from the bag
				putEquipament(equip);			
			}else if(restriction.length > 1){
				//NOT THE BEST WAY TO SOLVE THIS
				
				//if has the need for more then one restriction 
				Item found[] = new Item[restriction.length];
				
				//get all the necessary items
				for(int i = 0; i < found.length; i++){
					if(restriction[i].getSkill().getFeature().getTypeOf().size() > 2){
						found[i] = findEquipamentRestriction(restriction[i].getSkill().getFeature().getTypeOf().get(2)); 
					}else{
						found[i] = findEquipamentRestriction(restriction[i].getSkill().getFeature().getTypeOf().get(1));
					}					
				}
				
				//remove the itens that were found
				for(int i = 0; i < found.length; i++){
					//if the position isn´t null then remove
					if(found[i] != null){
						removeEquipament(found[i]);
					}
				}

				//equip the item
				putEquipament(equip);
			}
		}
		
		
		//has the place to equip, test the restriction
		
		//has the skill to equip and use
		
		//must know if has the restriction needed to equip it
				
		
		
		//must know if can equip this item, has the skill to use it
		
		
		
		//must find out if has an equipament in that place
		
	}
	
	public void refreshStatus(){
		for(int i = 0; i < status.size(); i++){
			if(status.get(i).getName().length() > 2){
				status.get(i).getSkill().getFeature().refreshCurrent();
			}
		}
		
		classes[classMoment].refreshStatus();
	}
	
	public int getStatusValue(String name){
		//return the value wanted
		return getStatus(name).getSkill().getFeature().getValue();
	}
	
	public int getStatusCurrent(String name){
		//return the wanted value
		return getStatus(name).getSkill().getFeature().getCurrent();
	}
	
	public void putEquipament(Item put){
		//temporary object
		ChangeFeature temp;
		
		//remove the equipament from the bag
		RPGSystem.getRPGSystem().getPlayerGroup().removeItem(put.getName(), 1);
		
		//put the equipament as a part of the equipaments
		equipament.add(put);
		
		//increase the status
		for(int i = 0; i < put.getEquipament().getFeature().getChangeFeatures().size(); i++){
			//get the object
			temp = put.getEquipament().getFeature().getChangeFeatures().get(i);
			//decrease what needs to
			increaseFeature(temp);
		}
	}
	
	public void removeEquipament(Item remove){
		//temporary object
		ChangeFeature temp;
		
		//put the equipament back in the bag
		RPGSystem.getRPGSystem().getPlayerGroup().addItem(remove.getIdentifier(), 1);
		
		//remove the equipament from the items equipped
		equipament.remove(remove);		
		//decrease the status
		for(int i = 0; i < remove.getEquipament().getFeature().getChangeFeatures().size(); i++){
			//get the object
			temp = remove.getEquipament().getFeature().getChangeFeatures().get(i);
			//decrease what needs to
			decreaseFeature(temp);
		}
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
		} else if (increase.equals("Skill")) {
			if (increase.getChanges().equals("Cost")) {
				getSkill(increase.getName()).getSkill().getFeature().changeCost(increase.getValue());
			} else if (increase.getChanges().equals("Time")) {
				getSkill(increase.getName()).getSkill().getFeature().changeDurationTime(increase.getValue());
			}
		} else if (increase.equals("Restriction")) {
			if (increase.getChanges().equals("Cost")) {
				getRestriction(increase.getName()).getSkill().getFeature().changeCost(increase.getValue());
			} else if (increase.getChanges().equals("Time")) {
				getRestriction(increase.getName()).getSkill().getFeature().changeDurationTime(increase.getValue());
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
		} else if (decrease.equals("Skill")) {
			if (decrease.getChanges().equals("Cost")) {
				getSkill(decrease.getName()).getSkill().getFeature().changeCost(-decrease.getValue());
			} else if (decrease.getChanges().equals("Time")) {
				getSkill(decrease.getName()).getSkill().getFeature().changeDurationTime(-decrease.getValue());
			}
		} else if (decrease.equals("Restriction")) {
			if (decrease.getChanges().equals("Cost")) {
				getRestriction(decrease.getName()).getSkill().getFeature().changeCost(-decrease.getValue());
			} else if (decrease.getChanges().equals("Time")) {
				getRestriction(decrease.getName()).getSkill().getFeature().changeDurationTime(-decrease.getValue());
			}
		}
	}

	/**
	 * Method used to get the experience
	 * 
	 * @return an integer
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * Method used to change the experience
	 * 
	 * @param experience
	 *            an integer
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * Method used to get the current level
	 * 
	 * @return an integer
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Method used to change the current level
	 * 
	 * @param level
	 *            an integer
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Method used to get the position of the class in the array
	 * 
	 * @return an integer
	 */
	public int getClassMoment() {
		return classMoment;
	}

	/**
	 * Method used to change the class being used, by changing the positon
	 * indicated in the array
	 * 
	 * @param classMoment
	 *            an integer
	 */
	public void setClassMoment(int classMoment) {
		this.classMoment = classMoment;
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

	/**
	 * Method used to get the bag of the character
	 * 
	 * @return an object of the class Bag
	 */
	public Bag getBag() {
		return bag;
	}

	/**
	 * Method used to get the class at the moment
	 * @return an object of the type Class
	 */
	public RPGClass getCharacterClass(){
		return classes[classMoment];
	}
	
	public void useClassFeature(String name, boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){
		classes[classMoment].useFeature(name, identifier, userGroup, userPosition, targetGroup, targetPosition);
	}
	
	public String getImageFile() {
		return imageFile;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void print(){

		classes[classMoment].print();
		//System.out.println(identifier);
		//System.out.println("Bag:" + bag);		
		//System.out.println("Class P:" + classMoment);
		//System.out.println("Desc:" + description);
	}

	public String getBattleModel() {
		return battleModel;
	}

	public float getBattleScale() {
		return battleScale;
	}

}

