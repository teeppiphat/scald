package framework.rpgsystem.evolution;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;
import framework.util.LearnFeature;
import framework.util.LearnTogetherFeature;

/**
 * Responsible for having the class of the character
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class RPGClass{
 	 
	/**
	 * Holds the table of experience
	 */
	private RPGClassXPTable classXPTable;
	 
	/**
	 * Holds the Features of this class
	 */
	private FeatureNetwork featureNetwork;

	/**
	 * Holds the level of the class, at the present moment
	 */
	private int level;
	
	/**
	 * Holds the experience of the class, at the moment
	 */
	private int experience;
	
	/**
	 * Holds the name of the class
	 */
	private String name;

	/**
	 * Holds the Features that are learned and must be passed to the Character
	 */
	private ArrayList<Feature> learnsFeatures;
	
	/**
	 * Holds the file to the initial values
	 */
	private String initialFile;
	
	/**
	 * Holds the folder to the initial values
	 */
	private String initialPath;
	
	
	
	/**
	 * Constructor of the class
	 */
	public RPGClass(){
		classXPTable = null;
		featureNetwork = new FeatureNetwork();
		learnsFeatures = new ArrayList<Feature>();
		initialFile = null;
		initialPath = null;
	}	 
	
	/**
	 * Method used to create a class, receive the class that wants to create
	 * 
	 * @param wanted
	 *            the name of the class, a String
	 */
	public void createClass(String wanted){
		//set the name
		name = wanted;
		//set the level, novice
		level = 1;
		//set the experience
		experience = 0;
		//load the Features of the class
		loadFeatureNetwork();
		
	}
	
	/**
	 * Method used to load all the Features of the class by receiving the class
	 * that wants to load
	 * 
	 * @param wanted
	 *            the name of the class, a String
	 */
	public void loadFeatureNetwork(){
		
		//get the element of the class
		Element network = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getRootElement(
						RPGSystem.getRPGSystem().getClassNetwork()
								.getClassPlace(name));		
		
		//load the Features used for status
		loadStatus(network.getChild("Status"));	
		//load the Features used for skills
		loadSkills(network.getChild("Skills"));
		//load the Features used for restrictions
		loadRestrictions(network.getChild("Restrictions"));
		//load the XpTable
		loadXPTable(network.getChild("XPTable"));		
		//load how the class evolves, its status
		loadEvolve(network.getChild("Evolve"));
		//load the Features that are learned
		loadLearned(network.getChild("Learn"));
		//load the initial values
		loadInitialValues(network.getChild("InitialValue"));
		
	}
	
	/**
	 * Method used to load the Features of the type status
	 * 
	 * @param status
	 *            an Element
	 */
	public void loadStatus(Element status){
		if(status != null){
			//load the status
			featureNetwork.loadStatus(status);
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
			featureNetwork.loadSkills(skill);
		}
	}
	
	/**
		 * Method used to load the Features of the type restrictions
		 * 
		 * @param restriction
		 *            an Element
		 */
		public void loadRestrictions(Element restriction){
			if(restriction != null){
				//load the restrictions
				featureNetwork.loadRestrictions(restriction);
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
			//initialize the table, that means that evolve
			classXPTable = new RPGClassXPTable();
			//load the XP table
			classXPTable.getTable(table.getChildText("Path"), table
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
			//get the file
			classXPTable.getClassXPTableEntry().setStatusFile(evolve.getChildText("File"));
			//get the path
			classXPTable.getClassXPTableEntry().setStatusPath(evolve.getChildText("Path"));
		}
	}
	
	/**
	 * Method used to load the Features that are learned
	 * 
	 * @param learn
	 *            an Element
	 */
	public void loadLearned(Element learn){
		if(learn != null){
			// get all the Features that can be learned
			List list = learn.getChildren();
			//temporary object to get one of them
			Element temp;
			
			//load all the Features
			for(int i = 0; i < list.size(); i++){
				//get the one that is learned
				temp = (Element)list.get(i);
				
				//create a temporary object used to load
				LearnFeature learned = new LearnFeature();
				
				//get the name
				learned.setName(temp.getChildText("Name"));
				
				//get the way is learned
				learned.setWay(temp.getChildText("Way"));
				
				//get the one who learns the new Feature
				learned.setWho(Boolean.parseBoolean(temp.getChildText("Who")));
				
				//get the type of the Feature, the general type
				learned.setType(temp.getChildText("Type"));				
				
				// according to the way that is learned load the rest
				if(learned.getWay().equals("Level")){
					//get the level when is learned
					learned.setLevel(Integer.parseInt(temp.getChildText("Level")));
				}else{
					// tell to load the together
					learned.setTogether(loadLearnTogether(temp.getChild("Together")));
				}
				
				//keep the object
				classXPTable.getClassXPTableEntry().addLearnFeature(learned);
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
	 * Method used to set the initial values for the class
	 * 
	 * @param identifier
	 *            the identifier of the character that must set
	 */
	public void setInitialValues(String identifier){
		
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
	
	
	/**
	 * Method used to increase the experience
	 * @param gains the value to increase, an integer
	 */
	public void gainsExperienceClass(int gains,String identifier){
		
		//if the class evolves
		if(classXPTable != null){			
			//gains experience
			experience = experience + gains;

			//if the amount of experience surpass the required for the next level
			//level 2 is position 1 of the array, then not a problem

			//need to know how many levels passed, will make once for
			//each level that passed
			while(experience >= classXPTable.getXP(level)){
				//increase the level
				level++;
				//calls the script responsible for evolving the status
				classXPTable.getClassXPTableEntry().gainLevel(level,identifier);
				//test if a Feature was learned by the evolution of the class
				learnsNewFeaturesLevel();						
			}
		}
	}
	
	/**
	 * Method used to increase the experience, for the Features
	 * 
	 * @param gains
	 *            the value to increase, an integer
	 */
	public void gainsExperienceFeatures(int gains,String identifier){
		//tells the Features that evolve to gain experience
		
		//pass by all the Features
		featureNetwork.gainsExperienceFeatures(gains,identifier);
		
		//test if a Feature was learned by the evolution of other Features
		learnsNewFeatureTogether();
	}
	
	/**
	 * Method used to learn a new Feature by receiving the name, identifier, and the type
	 * @param name a String 
	 * @param type a String 
	 */
	public void learnFeature(String name,String type){
			
		//according to the rules
		//if the holder, then pass the Feature learned to the character
		//if the self, then keep everything learned to itself
		if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getClassLearn().equals("Holder")){
			//put int the array list used to pass to the class
			learnsFeatures.add(featureNetwork.getLearnFeature(name, type));
		}else{
			//ask the FeatureNetwork to learn the Feature
			featureNetwork.learnFeature(name, type);
		}
	}
	
	/**
	 * Method used to ask to learn a new Feature by the level of the class
	 */
	public void learnsNewFeaturesLevel(){
		//temporary object used for test
		LearnFeature temp;
		
		//will test all the Features that can learn
		for(int i = 0; i < classXPTable.getClassXPTableEntry().getLearns().size(); i++){
			//get one of the Feature
			temp = classXPTable.getClassXPTableEntry().getLearns().get(i);
			
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
		for(int i = 0; i < classXPTable.getClassXPTableEntry().getLearns().size(); i++){
			//get one of the Feature
			temp = classXPTable.getClassXPTableEntry().getLearns().get(i);
						
			//test the LearnFeature, first the type it is, if is by Together
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
			test = featureNetwork.searchFeature(temp.getType(), temp.getName());
			
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
	 * Evolve determined Status, by receiving its name and how much earns
	 * 
	 * @param name
	 *            a String
	 * @param increase
	 *            an integer
	 */
	public void evolveFeature(String name, int increase){
		featureNetwork.evolveFeature(name, increase);
	}
	
	public void useFeature(String name,String identifier,boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){		
		featureNetwork.useFeature(name, identifier, userGroup, userPosition, targetGroup, targetPosition);
	}
	
	public Feature getSkillNeedEquip(String specific){
		return featureNetwork.getSkillNeedEquip(specific);
	}	
	
	/**
	 * Change the value of a Status, by receiving the name and the new value
	 * 
	 * @param name
	 *            a String
	 * @param value
	 *            an integer
	 */
	public void setStatusValue(String name, int value){
		featureNetwork.setStatusValue(name,value);
	}
	
	public void setStatusCurrent(String name, int value){
		featureNetwork.setStatusCurrent(name,value);
	}
	
	public int getStatusValue(String name){
		return featureNetwork.getStatusValue(name);
	}
	
	public int getStatusCurrent(String name){
		return featureNetwork.getStatusCurrent(name);
	}
	
	public void refreshStatus(){
		featureNetwork.refreshStatus();
	}
	
	/**
	 * Method used to get the experience
	 * @return an integer
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * Method used to change the experience
	 * @param experience an integer
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * Method used to get the current level
	 * @return an integer
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Method used to change the current level
	 * @param level an integer
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Method used to get the name of the class
	 * @return a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used to change the name of the class
	 * @param name a String
	 */
	public void setName(String name) {
		this.name = name;
	}
		
	public void print(){		
		featureNetwork.printStatusValues();
	}
	
	public void cleanLearnFeatures(){
		learnsFeatures.clear();
	}

	public ArrayList<Feature> getLearnsFeatures() {
		return learnsFeatures;
	}

	public FeatureNetwork getFeatureNetwork() {
		return featureNetwork;
	}

	public RPGClassXPTable getClassXPTable() {
		return classXPTable;
	}		
		
}
 
