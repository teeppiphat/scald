package framework.rpgsystem.evolution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;
import framework.util.ChangeFeature;
import framework.util.EffectGained;
import framework.util.LearnFeature;
import framework.util.LearnTogetherFeature;

/**
 * Used to describe all the atributes, magics/skills, classes,
 * everything that is needed as a part of the characters or
 * enemys
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Feature implements Serializable{
 
	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the new abilities that can be gained
	 * with the use of this Feature
	 */
	private ArrayList<FeatureEntry> featureEntry;
			 
	/**
	 * Responsible for the evolution of the Feature
	 */
	private MainFeature mainFeature;
	 
	/**
	 * Describe the Feature if is a magic
	 */
	private SecondFeature<Magic> magic;
	
	/**
	 * Describe the Feature if is weapon
	 */
	private SecondFeature<Weapon> weapon;
	
	/**
	 * Describe the Feature if is weapon
	 */
	private SecondFeature<Skill> skill;
	
	/**
	 * The name of the Feature
	 */
	private String name;
	
	/**
	 * The type of the Feature, if is equipament, magic or skill,
	 * more of a general type
	 */
	private String type;
	
	/**
	 * Has the description of the item
	 */
	private String description;	
	
	/**
	 * The value to buy this Feature
	 */
	private int buy;
	
	/**
	 * The value to sell this Feature
	 */	
	private int sell;
		
	/**
	 * Responsible for saying if a item is unique or not, in this case
	 * can´t have more then one slot with this type of Feature
	 */
	private boolean unique;
	
	/**
	 * Holds the Features that are learned by the Feature
	 */
	private ArrayList<Feature> learnsFeatures;
	
	/**
	 * Holds the number of times the Feature was used
	 */
	private int usedTimes;
	
	/**
	 * Holds the effects that the Feature has gained
	 */
	private ArrayList<EffectGained> hasEffects;
	
	/**
	 * Holds the file of the image that represents the item in the menu
	 */
	private String imageFile;
	
	/**
	 * Holds the path of the image that represents the item in the menu
	 */
	private String imagePath;
	
	/**
	 * Holds the animations of each model for this feature
	 */
	private HashMap<String,String> animations;
	
	/**
	 * Has the type of the effect used for the animation
	 */
	private String effectType;

	/**
	 * Has the name of the effect used for the animation
	 */
	private String effectName;
	
	/**
	 * Constructor of the class, don´t do anything
	 */
	public Feature(){
		learnsFeatures = new ArrayList<Feature>();
		mainFeature = null;
		featureEntry = null;
		usedTimes = 0;
		hasEffects = null;
		imageFile = null;
		imagePath = null;
		animations = new HashMap<String, String>();
	}
	
	/**
	 * Constructor of the class, receives the values
	 */
	public Feature(String name, String type){
		//get the values
		this.name = name;
		this.type = type;
	}
	
	public void load(Element feat){
		
		//create according to its type
		
		//load the general information
		loadGeneral(feat.getChild("General"));
		
		//load the specific type
		loadSpecificType(feat.getChild("SpecificType"));
		
		//load the cost to use
		loadUseCost(feat.getChild("ToUse"));
		
		//load the features that change
		loadChangeOthersFeatures(feat.getChild("ChangeOtherFeatures"));
		
		//load the need for a feature to equip
		loadNeedOtherFeature(feat.getChild("NeedOtherFeature"));
		
		//load the duration
		loadDuration(feat.getChild("Duration"));
		
		//load the target
		loadTarget(feat.getChild("Target"));
		
		//load the buy specifications
		loadBuySpecifications(feat.getChild("BuySpecification"));
		
		//load if the feat evolves
		loadEvolution(feat.getChild("Evolution"));
		
		//load when is learned
		loadLearn(feat.getChild("Learn"));
		
		//load the max value
		loadMaxValue(feat.getChild("Values"));
		
		//load the image used for the menu
		loadMenuImage(feat.getChild("MenuImage"));
		
		//load the animations used for this feature
		loadAnimation(feat.getChild("AnimationUsed"));
		
	}
	
	private void loadAnimation(Element ani){
		if(ani != null){
			//temporary object
			Element temp;
			
			//get the elements
			List list = ani.getChild("Animations").getChildren();
			
			//get the iterator
			Iterator i = list.iterator();
			
			while(i.hasNext()){
				//get the element
				temp = (Element)i.next();
				//will put the element in the hash
				animations.put(temp.getName(), temp.getText());
				
			}
			
			//get the effect type
			effectType = ani.getChild("Effect").getChildText("Type");
						
			//get the effect name
			effectName = ani.getChild("Effect").getChildText("Name");
			
		}
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
	 * Method used to load the general information, the name, the type and the
	 * description
	 * 
	 * @param general an Element
	 */
	public void loadGeneral(Element general){
		name = general.getChildText("Name");
		type = general.getChildText("Type");
		description = general.getChildText("Description");
	}
	
	/**
	 * Method used to load the specific type of the item
	 * 
	 * @param type
	 *            an Element
	 */
	public void loadSpecificType(Element type){
		
		//if there´s an specific type
		if(type != null){

			//get all the typesOf
			List list = type.getChildren();	
			//temporary element
			Element temp;

			//load an usable item
			if(this.type.equals("Magic")){
				//create the object
				magic = new SecondFeature<Magic>();
				//create the inside
				magic.setFeature(new Magic());

				//set the configurations for it

				//put the elements in its place
				for(int i = 0; i < list.size(); i++){
					//get the object
					temp = (Element)list.get(i);
					//store the element
					magic.getFeature().addTypeOf(temp.getText());
				}

			}else if (this.type.equals("Skill")){

				//create the object
				skill = new SecondFeature<Skill>();
				//create the inside
				skill.setFeature(new Skill());

				//set the configurations for it

				//put the elements in its place
				for(int i = 0; i <list.size(); i++){
					//get the object
					temp = (Element)list.get(i);
					//store the element
					skill.getFeature().addTypeOf(temp.getText());
				}

			}else if (this.type.equals("Equipament")){
				//create the object
				weapon = new SecondFeature<Weapon>();
				//create the inside
				weapon.setFeature(new Weapon());

				//set the configurations for it

				//put the elements in its place
				for(int i = 0; i <list.size(); i++){
					//get the object
					temp = (Element)list.get(i);
					//store the element
					weapon.getFeature().addTypeOf(temp.getText());
				}			
			}		
		}
	}
	
	/**
	 * Method used to load the cost that is needed to use this
	 * 
	 * @param cost
	 *            an Element
	 */
	public void loadUseCost(Element cost){
		if(cost != null){
			if(type.equals("Magic")){
				//load the cost
				magic.getFeature().setCost(Integer.parseInt(cost.getChildText("Cost")));
				//load who pays
				magic.getFeature().setWhoPays(cost.getChildText("WhoPays"));
			}else if (this.type.equals("Skill")){
				//load the cost
				//skill.getFeature().setCost(Integer.parseInt(cost.getChildText("Cost")));
				//load who pays
				//skill.getFeature().setWhoPays(cost.getChildText("WhoPays"));				
			}else if (this.type.equals("Weapon")){				
				//load the cost
				weapon.getFeature().setCost(Integer.parseInt(cost.getChildText("Cost")));
				//load who pays
				weapon.getFeature().setWhoPays(cost.getChildText("WhoPays"));
			}
		}
	}
	
	/**
	 * Method used to load the changes that happen to other Features
	 * 
	 * @param change
	 *            an Element
	 */
	public void loadChangeOthersFeatures(Element change){
		if(change != null){
			
			//get all the elements
			List list = change.getChildren();
			//iterator to get the values in the list
			Iterator i = list.iterator();
			//temporary object used to store
			Element temp;
			
			//loop
			while(i.hasNext()){
				//get the object
				temp = (Element)i.next();	
				
				//create the object to store
				ChangeFeature object = new ChangeFeature();
				//set the name
				object.setName(temp.getChildText("Name"));
				//set the type
				object.setType(temp.getChildText("Type"));
				//set the changes
				object.setChanges(temp.getChildText("Changes"));
				//set the value
				object.setValue(Integer
						.parseInt(temp.getChildText("Value")));
				//set the way to calculate
				object.setCalculate(Boolean.parseBoolean(temp
						.getChildText("Calculate")));	
				//set the one used to compute
				object.setCompute(temp.getChildText("Compute"));
				
				//store the object				
				if(type.equals("Magic")){
					magic.getFeature().addChangeFeature(object);
								
				}else if (this.type.equals("Skill")){
					skill.getFeature().addChangeFeature(object);		
					
				}else if (this.type.equals("Weapon")){
					weapon.getFeature().addChangeFeature(object);			
					
				}				
			}
		}
	}
	
	/**
	 * Method used load the Features that are needed
	 * 
	 * @param need
	 *            an Element
	 */
	public void loadNeedOtherFeature(Element need){
		if(need != null){
			
			//get all the need
			List list = need.getChildren();
			//temporary object
			Element temp;
			
			//the usable item
			if(type.equals("Magic")){
				
				//put the elements in its place
				for(int i = 0; i < list.size(); i++){
					//get the element
					temp = (Element)list.get(i);
					//store the element
					magic.getFeature().addNeedFeature(temp.getText());
				}
			}else if (this.type.equals("Skill")){
				//put the elements in its place
				for(int i = 0; i < list.size(); i++){
					//get the element
					temp = (Element)list.get(i);
					//store the element
					skill.getFeature().addNeedFeature(temp.getText());
				}
			}else if (this.type.equals("Weapon")){
				//put the elements in its place
				for(int i = 0; i < list.size(); i++){
					//get the element
					temp = (Element)list.get(i);
					//store the element
					weapon.getFeature().addNeedFeature(temp.getText());
				}
			}
		}
	}
	
	/**
	 * Method used to load the Duration of the Feature
	 * 
	 * @param duration
	 *            an Element
	 */
	public void loadDuration(Element duration){
		//if has a duration
		if(duration != null){
			//load it
			if(type.equals("Magic")){
				//set the duration
				magic.getFeature().setDurationUse(duration.getChildText("Type"));
				//set the time
				magic.getFeature().setDurationTime(Integer.parseInt(duration.getChildText("Time")));
			}else if (this.type.equals("Skill")){
				//set the duration
				//skill.getFeature().setDurationUse(duration.getChildText("Type"));
				//set the time
				//skill.getFeature().setDurationTime(Integer.parseInt(duration.getChildText("Time")));
			}else if (this.type.equals("Weapon")){
				//set the duration
				//weapon.getFeature().setDurationUse(duration.getChildText("Type"));
				//set the time
				//weapon.getFeature().setDurationTime(Integer.parseInt(duration.getChildText("Time")));
			}			
		}
	}
	
	/**
	 * Method used to load the Target of the Feature
	 * 
	 * @param target
	 *            an Element
	 */
	public void loadTarget(Element target){
		//load the target
		if (target != null){			
			//according to the type
			if(type.equals("Magic")){
				//set the target type
				magic.getFeature().setTarget(target.getText());				
			}else if (this.type.equals("Skill")){
				//set the target type
				//skill.getFeature().setTarget(target.getText());
			}else if (this.type.equals("Weapon")){
				//set the target type
				weapon.getFeature().setTarget(target.getText());
			}			
		}
	}
	
	/**
	 * Method used to load the specifications of the Feature to bought or sold
	 * 
	 * @param specifications
	 *            an Element
	 */
	public void loadBuySpecifications(Element specifications){
		//if it has
		if(specifications != null){			
			//get the buy value
			buy = Integer.parseInt(specifications.getChildText("Buy"));			
			//get the sell value
			sell = Integer.parseInt(specifications.getChildText("Sell"));			
			//get if is unique
			unique = Boolean.parseBoolean(specifications.getChildText("Unique"));
		}
	}
	
	/**
	 * Method used to load the Evolution of the Feature
	 * 
	 * @param evolution
	 *            an Element
	 */
	public void loadEvolution(Element evolution){
		//if evolves
		if(evolution != null){
			//evolves, then need this
			mainFeature = new MainFeature();
			//load who evolves together
			loadEvolutionEvolveTogether(evolution.getChild("EvolveTogether"));			
			//load what needs to evolve
			loadEvolutionNeedToEvolve(evolution.getChild("ToEvolve"));			
			//load the script to evolve status
			loadEvolutionEvolveStatus(evolution.getChild("Status"));			
			//load the effects that are gained
			loadEvolutionEffectGained(evolution.getChild("EffectGained"));		
			//load the table used to evolve
			loadEvolutionTable(evolution.getChild("Table"));
		}
	}
		
	/**
	 * Method used to load if another Feature evolve with this
	 * 
	 * @param together
	 *            an Element
	 */
	public void loadEvolutionEvolveTogether(Element together){
		if(together != null){			
			//pass to the one responsible for evolving		
			mainFeature.loadEvolveTogether(together);			
		}
	}
	
	/**
	 * Method used to load if the Feature need to be used or a type to evolve
	 * 
	 * @param needTo
	 *            an Element
	 */
	public void loadEvolutionNeedToEvolve(Element needTo){
		if(needTo != null){
			mainFeature.loadNeedToEvolve(needTo);
		}
	}
	
	/**
	 * Method used to get the script responsible for evolving the status of the
	 * Character if evolves
	 * 
	 * @param status
	 *            an Element
	 */
	public void loadEvolutionEvolveStatus(Element status){
		if(status != null){
			mainFeature.loadEvolveStatus(status);
		}
	}
	
	/**
	 * Method used to load an effect that is gained when evolves
	 * 
	 * @param effect
	 *            an Element
	 */
	public void loadEvolutionEffectGained(Element effect){
		if(effect != null){
			//initialize it
			hasEffects = new ArrayList<EffectGained>();		 
			//load them
			mainFeature.loadEffectGained(effect);
		}
	}
	
	/**
	 * Method used to load the table of experience
	 * 
	 * @param table
	 *            an Element
	 */
	public void loadEvolutionTable(Element table){
		if(table != null){
			//load the table
			mainFeature.getFeatureXPTable().getTable(
					table.getChildText("Path"), table.getChildText("File"),
					Integer.parseInt(table.getChildText("MaxLevel")));
			}
	}
	
	/**
	 * Method used to load what is learned by or throught this Feature
	 * 
	 * @param learn
	 *            an Element
	 */
	public void loadLearn(Element learn){
		if(learn != null){
			
			//initialize it for the used type
			featureEntry = new ArrayList<FeatureEntry>();
			
			//get all the Features that can be learned
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
				
				// get the type of the Feature, the general type
				learned.setType(temp.getChildText("Type"));				
				
				//according to the way that is learned load the rest
				if(learned.getWay().equals("Level")){
					//get the level when is learned
					learned.setLevel(Integer.parseInt(temp.getChildText("Level")));
				}else{
					// tell to load the together
					learned.setTogether(loadLearnTogether(temp.getChild("Together")));
				}
							
				//keep the object, according to the way is learned
				if(learned.getWay().equals("Used")){
					//if used then is FeatureEntry
										
					//create a FeatureEntry
					FeatureEntry newFeatureEntry = new FeatureEntry();					
					//create the constraints
					newFeatureEntry.createConstraint(learned.getTogether());
					//set the name
					newFeatureEntry.setName(learned.getName());
					//set the type
					newFeatureEntry.setType(learned.getType());
					//put the new Entry in the array
					featureEntry.add(newFeatureEntry);
				}else{
					//else is level or together and is by evolving
					mainFeature.getFeatureXPTable().getFeatureXPTableEntry().addLearnFeature(learned);
				}
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
	 * Method used to load the maximum value that can achieve
	 * 
	 * @param max
	 *            an Element
	 */
	public void loadMaxValue(Element max){
				
		if(max != null){
					
			if(this.type.equals("Magic")){
				//load the maximum value that can have
				
			}else if(this.type.equals("Skill")){
				//load the initial value
				skill.getFeature().setValue(Integer.parseInt(max.getChildText("Initial")));
				//set the current value as the same as the initial value
				skill.getFeature().setCurrent(Integer.parseInt(max.getChildText("Initial")));
				//load the maximum value that can have				
				skill.getFeature().setMaxValue(Integer.parseInt(max.getChildText("Maximum")));			
			}else if(this.type.equals("Equipament")){
				//load the maximum value that can have
				
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
	 * Method used to learn a new Feature
	 * @param name the identifier of the Feature, a String
	 * @param type the type of the Feature, a String
	 */
	public void learnFeature(String name, String type){
		// according to the type will load it
		if (type.equals("Skill")) {
			if (!learnsFeatures.contains(loadFeat(name, true))) {
				// load a new skill
				learnsFeatures.add(loadFeat(name, true));
			}
		} else if (type.equals("Status")) {
			if (!learnsFeatures.contains(loadFeat(name, true))) {
				// load a new skill
				learnsFeatures.add(loadFeat(name, true));
			}
		} else if (type.equals("Restriction")) {
			if (!learnsFeatures.contains(loadFeat(name, true))) {
				// load a new skill
				learnsFeatures.add(loadFeat(name, true));
			}
		} else if (type.equals("Magic")) {
			if (!learnsFeatures.contains(loadFeat(name, false))) {
				// load a new skill
				learnsFeatures.add(loadFeat(name, false));
			}
		}
	}
	
	/**
	 * Method used to ask to learn a new Feature by the level of the class
	 */
	public void learnsNewFeaturesLevel(){
		//temporary object used for test
		LearnFeature temp;
		
		//will test all the Features that can learn
		for(int i = 0; i < mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getLearns().size(); i++){
			//get one of the Feature
			temp = mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getLearns().get(i);
						
			//test the LearnFeature, first the type it is, if is by Level
			if(temp.getWay().equals("Level")){
				//if by level test the level, if the same must Learn
				if(temp.getLevel() == mainFeature.getLevel()){
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
		for(int i = 0; i < mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getLearns().size(); i++){
			//get one of the Feature
			temp = mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getLearns().get(i);
						
			//test the LearnFeature, first the type it is, if is by Level
			if(temp.getType().equals("Together")){
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
	 * 
	 * @param type
	 *            a String
	 * @param name
	 *            a String
	 * @return the Feature or null, if not found
	 */
	public Feature searchFeature(String type,String name){
		//will search for a Feature that has
		return getFeature(name);
	}
	
	/**
	 * Method used to get a Feature learned by giving the name of the Feature
	 * wanted
	 * 
	 * @param name
	 *            a String
	 * @return the Feature or null, if not found
	 */
	public Feature getFeature(String name){
		for(int i = 0; i < learnsFeatures.size(); i++){
			if(name.equals(learnsFeatures.get(i).getName())){
				return learnsFeatures.get(i);
			}
		}
		//not found		
		return null;
	}
		
	/**
	 * Method used to increase the experience
	 * 
	 * @param gains
	 *            the value to increase, an integer
	 */
	public void gainsExperience(int gains,String identifier){
		
		//if the Feature evolve
		if(mainFeature != null){
			
			//test if gains experience when used
			if(mainFeature.isNeedUse()){
				//needs to be used
				//TODO
			}else{							
				//increase the experience
				increaseExperience(gains, identifier);
			}
		}
	}
	
	public void increaseExperience(int gains, String identifier){
		
		if(mainFeature != null){
			//gains experience
			mainFeature.increaseExperience(gains);

			//if the amount of experience surpass the required for the next level
			//level 2 is position 1 of the array, then not a problem
			
			// need to know how many levels passed, will make once for
			// each level that passed
			
			if (mainFeature.getLevel() < mainFeature.getFeatureXPTable()
					.getLevelsSize() - 1) {
				
				while (mainFeature.getExperience() >= mainFeature
						.getFeatureXPTable().getXP(mainFeature.getLevel())) {
					// increase the level
					mainFeature.increaseLevel();
					// evolve the status
					mainFeature.evolveStatus(identifier);
					// gains an effect, in this case by gaining a level
					gainsEffectLevel();
					// calls the script responsible for evolving the Feature, if
					// evolves status
					mainFeature.getFeatureXPTable().getFeatureXPTableEntry()
							.gainLevel(mainFeature.getLevel(), name);
					// test if a Feature was learned by the evolution of the
					// Feature
					learnsNewFeaturesLevel();
					// test if a Feature was learned by the learn together
					learnsNewFeatureTogether();
				}
			}
		}
	}
	
	public void gainsExperienceByUse(int gains, String identifier){
		//gives the experience
		increaseExperience(gains, identifier);
	}
	
	public void gainsEffectUsed(){
		//temporary object
		EffectGained temp;
		
		//test all the effects that can gain
		for(int i = 0; i < mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getEffects().size(); i++){
			//get one effect
			temp = mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getEffects().get(i);
			
			//test the way is learned, in this case Used
			if(temp.getWay().equals("Used")){
				//test the number of times that was used
				if(usedTimes == temp.getLevel()){
					//gains the effect
					hasEffects.add(temp);					
				}				
			}
		}
	}
	
	public void gainsEffectLevel(){
		//temporary object
		EffectGained temp;
		
		//test all the effects that can gain
		for(int i = 0; i < mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getEffects().size(); i++){
			//get one effect
			temp = mainFeature.getFeatureXPTable().getFeatureXPTableEntry().getEffects().get(i);
			
			//test the way is learned
			if(temp.getWay().equals("Level")){
				//test the level of the Feature
				if(mainFeature.getLevel() == temp.getLevel()){
					//gains the effect
					hasEffects.add(temp);					
				}				
			}
		}
	}
	
	public void useFeature(String target, boolean group, int position){
		//get the Feature that is going to be used, the only ones
		//that can be used directly are the Magics
		
		//if the magic evolve
		if(mainFeature != null){
			//increase the number of times used
			increaseUsedTimes();
			
			//gains effect
			gainsEffectUsed();

			//evolve the character
			if(group){
				//gain experience by being used, a fixed value
				gainsExperience(10,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getIdentifier());
			}		
		}
		
		//use it according to the parameters, its own parameters
		fireFeature(target, group, position);
	}
	
	public void useFeature(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){
		//if the magic evolve
		if(mainFeature != null){
			//increase the number of times used
			increaseUsedTimes();
			
			//gains effect
			gainsEffectUsed();

			//evolve the character
			if(userGroup){
				//gain experience by being used, a fixed value
				gainsExperience(10,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getIdentifier());
			}		
		}
		
		//use it according to the parameters, its own parameters
		fireFeature(userGroup, userPosition, targetGroup, targetPosition);
	}
	
	public void useMagicCost(boolean userGroup, int userPosition){		
		//identify if has cost
		if(magic.getFeature().getWhoPays() != null){
			//identify the user group
			if(userGroup){
				//player
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatus(magic.getFeature().getWhoPays()) != null){
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getStatus(magic.getFeature().getWhoPays()).getSkill().getFeature().changeCurrent(-magic.getFeature().getCost());
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatus(magic.getFeature().getWhoPays()) != null){
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getStatus(magic.getFeature().getWhoPays()).getSkill().getFeature().changeCurrent(-magic.getFeature().getCost());
				}
			}else{
				//enemy
				RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0]
						.getEnemy(userPosition).getStatus(
								magic.getFeature().getWhoPays()).getSkill()
						.getFeature().changeCurrent(
								-magic.getFeature().getCost());
			}
		}
	}
	
	public void fireFeature(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){
		//use the feature according to its values and the type		
		if(type.equals("Magic")){
			//will use itself according to the change features
			
			//must remove its cost to use
			useMagicCost(userGroup,userPosition);
			
			//must go through all the possible changes
			for(int i = 0; i < magic.getFeature().getChangeFeatures().size(); i++){
				//calculate it, depends on the user and the target
				calculate(userGroup, userPosition, targetGroup, targetPosition, magic.getFeature().getChangeFeatures().get(i), magic.getFeature().getTarget());
			}
		}else if(type.equals("Equipament")){			
			//TODO an equipament that can be used to execute a magic
		}
	}
	
	public void calculate(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition, ChangeFeature change, String target){

		// set the value
		int value = change.getValue();

		//just to make a test
		callScript(userGroup, userPosition, targetGroup, targetPosition, change);
		
		//player group
		if(change.getType().equals("Skill")){

			//single execute to only one character
			if(target.equals("Single")){

				//calculate and change the value to the character
				computeChanges(userGroup, userPosition, targetGroup, targetPosition, change, value);

			}if(target.equals("Group")){
				//creates the size
				int size = RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1;

				//test where is being used
				if(Game.battle){
					//if is in battle

					//test from which group it comes from
					if(targetGroup){
						//characters
						if(size > FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle()){
							size = FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle();
						}
					}else{
						//enemy
						size = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length;
					}

					for(int i = 0; i < size; i++){
						// for each character use the item
						computeChanges(userGroup, userPosition, targetGroup, i, change, value);						
					}

				}else{
					//if is outside battle

					//must use for each character that is in the group
					for(int i = 0; i < size; i++){
						//for each character use the item
						computeChanges(userGroup, userPosition, targetGroup, i, change, value);	
					}							
				}
			}				

		}else if(change.getType().equals("Magic")){
			// TODO
		}else if(change.getType().equals("Equipament")){
			// TODO
		}

	}
	
	//use this to calculate the value, them return a value
	public int callScript(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition, ChangeFeature change){

		//create the array used for the test
		Object[] objs = new Object[6];
		objs[0] = userGroup;
		objs[1] = userPosition;
		objs[2] = targetGroup;
		objs[3] = targetPosition;
		objs[4] = change;
		objs[5] = this;
		
		//temporary object, to receive a value
		Object[] receive = null;
		//map the array of objects to the script, always mapping again to change the Feature values according
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().mapToTheScript("FeatureAttribute",objs);
		//test purpose
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().loadScript("res/scripts/rules/", "ongoing.js");
		//test send the values to the script
		receive = FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript("calculateFeatureUse", objs);
		
		//return the value found		
		return (int)Float.parseFloat(receive[0].toString());						
	}
		
	public void computeChanges(boolean userGroup, int userPosition, boolean targetGroup, int targetPosition, ChangeFeature change, int value){		
		//calls the script to execute the rule for the use, will receive a new value, if has a rule for it
		value = callScript(userGroup, userPosition, targetGroup, targetPosition, change);		
		//tells to change
		choseWhatChange(targetGroup, targetPosition, change, value);
	}
	
	public void fireFeature(String target, boolean group, int position){

		//use the item according to its values and the type
		
		if(type.equals("Magic")){
			//will use itself according to the change features
			
			//must go through all the possible changes
			for(int i = 0; i < magic.getFeature().getChangeFeatures().size(); i++){
				//calculate it
				calculate(group, position, magic.getFeature().getChangeFeatures().get(i), magic.getFeature().getTarget());
			}
		}else if(type.equals("Equipament")){			
			//TODO an equipament that can be used to execute a magic
		}
		
		//will use itself
		//needs the target only
		
		//get the attributes of the item
		
	}
	
	public int calculatePercentage(int A, int B){		
		return (A * B)/100;
	}
	
	public int calculateValueChange(boolean group, int position, ChangeFeature change,int value){
		//change using value					
		if(change.isCalculate()){
			
			//test where to calculate
			if(group){
				//player
				//calculate the percentage
				//test if the status is in the character or in the class of the character
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().getValue());	
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().getValue());
				}
			}else{
				//enemy
				return calculatePercentage(value,RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().getValue());
			}
		}
		
		return value;
	}
	
	public int calculateMaximumChange(boolean group, int position, ChangeFeature change,int value){
		//change using value					
		if(change.isCalculate()){
			// test where to calculate
			if(group){
				//player
				// calculate the percentage
				//test if the status is in the character or in the class of the character
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().getMaxValue());	
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().getMaxValue());
				}	
			}else{
				//enemy
				return calculatePercentage(value,RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().getMaxValue());
			}	
		}
		
		return value;
	}
		
	public int calculateCurrentChange(boolean group, int position, ChangeFeature change,int value){
		//change using value					
		if(change.isCalculate()){
			// test where to calculate
			if(group){
				//player
				// calculate the percentage
				//test if the status is in the character or in the class of the character
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().getCurrent());	
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().getCurrent());
				}	
			}else{
				//enemy
				return calculatePercentage(value,RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().getCurrent());
			}	
		}
		
		return value;
	}
	
	public int calculateCostChange(boolean group, int position, ChangeFeature change,int value){
		//change using value					
		if(change.isCalculate()){
			// test where to calculate
			if(group){
				//player
				// calculate the percentage
				//test if the status is in the character or in the class of the character
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().getCost());	
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().getCost());
				}	
			}else{
				//enemy
				return calculatePercentage(value,RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().getCost());
			}	
		}
		
		return value;
	}
	
	public int calculateTimeChange(boolean group, int position, ChangeFeature change,int value){
		//change using value					
		if(change.isCalculate()){
			// test where to calculate
			if(group){
				//player
				// calculate the percentage
				//test if the status is in the character or in the class of the character
				if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().getDurationTime());	
				}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
					return calculatePercentage(value,RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().getDurationTime());
				}	
			}else{
				//enemy
				return calculatePercentage(value,RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().getDurationTime());
			}
		}
		
		return value;
	}
	
	public void changeCurrentValue(boolean group, int position, ChangeFeature change, int value){
		// test where to calculate
		if(group){
			//player
			// test where the status is
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().changeCurrent(value);			
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().changeCurrent(value);
			}
		}else{			
			//enemy			
			RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().changeCurrent(value);
		}
		
	}
	
	public void changeMaximumValue(boolean group, int position, ChangeFeature change, int value){
		// test where to calculate
		if(group){
			//player
			// test where the status is
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().increaseMaxValue(value);			
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().increaseMaxValue(value);
			}
		}else{
			//enemy
			RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().increaseMaxValue(value);
		}
		
	}
	
	public void changeValueValue(boolean group, int position, ChangeFeature change, int value){
		// test where to calculate
		if(group){
			//player
			// test where the status is
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().increaseValue(value);			
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().increaseValue(value);
			}
		}else{
			//enemy
			RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().increaseValue(value);
		}
		
	}
	
	public void changeCostValue(boolean group, int position, ChangeFeature change, int value){
		// test where to calculate
		if(group){
			//player
			// test where the status is
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().changeCost(value);			
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().changeCost(value);
			}
		}else{
			//enemy
			RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().changeCost(value);
		}
		
	}
	
	public void changeTimeValue(boolean group, int position, ChangeFeature change, int value){
		// test where to calculate
		if(group){
			//player
			// test where the status is
			if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getStatus(change.getName()).getSkill().getFeature().changeDurationTime(value);			
			}else if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()) != null){
				RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getStatus(change.getName()).getSkill().getFeature().changeDurationTime(value);
			}
		}else{
			//enemy
			RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).getStatus(change.getName()).getSkill().getFeature().changeDurationTime(value);
		}
		
	}
	
	public int calculateWhatChange(boolean group,int position, ChangeFeature change, int value){		
		//test what calculate
		if(change.getCompute().equals("Maximum")){
			//calculate
			return calculateMaximumChange(group, position, change, value);			
		}else if(change.getCompute().equals("Current")){
			//calculate
			return calculateCurrentChange(group, position, change, value);
		}else if(change.getCompute().equals("Value")){
			//calculate
			return calculateValueChange(group, position, change, value);
		}else if(change.getCompute().equals("Cost")){
			//calculate
			return calculateCostChange(group, position, change, value);
		}else if(change.getCompute().equals("Time")){
			//calculate
			return calculateCostChange(group, position, change, value);
		}
		
		return value;
	}
	
	public void choseWhatChange(boolean group, int position, ChangeFeature change, int value){
		//test what changes
		if(change.getChanges().equals("Maximum")){
			//change
			changeMaximumValue(group, position, change, value);
		}else if(change.getChanges().equals("Current")){
			//change
			changeCurrentValue(group, position, change, value);
		}else if(change.getChanges().equals("Value")){
			//change
			changeValueValue(group, position, change, value);
		}else if(change.getChanges().equals("Cost")){
			//change
			changeCostValue(group, position, change, value);
		}else if(change.getChanges().equals("Time")){
			//change
			changeTimeValue(group, position, change, value);
		}
	}
		
	public void computeChanges(boolean group, int position, ChangeFeature change, int value){
		//tells to calculate
		value = calculateWhatChange(group, position, change, value);
		//calls the script to execute the rule for magic damage, will receive a new value
		
		//tells to change
		choseWhatChange(group, position, change, value);
	}
	
	public void calculate(boolean group, int position, ChangeFeature change, String target){

		// set the value
		int value = change.getValue();

		//player group
		if(change.getType().equals("Skill")){

			//single execute to only one character
			if(target.equals("Single")){

				//calculate and change the value to the character
				computeChanges(group, position, change, value);

			}if(target.equals("Group")){
				//creates the size
				int size = RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1;

				//test where is being used
				if(Game.battle){
					//if is in battle

					//test from which group it comes from
					if(group){
						//characters
						if(size > FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle()){
							size = FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle();
						}
					}else{
						//enemy
						size = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length;
					}

					for(int i = 0; i < size; i++){
						// for each character use the item
						computeChanges(group, i, change, value);
					}

				}else{
					//if is outside battle

					//must use for each character that is in the group
					for(int i = 0; i < size; i++){
						//for each character use the item
						computeChanges(group, i, change, value);
					}							
				}
			}				

		}else if(change.getType().equals("Magic")){
			// TODO
		}else if(change.getType().equals("Equipament")){
			// TODO
		}

	}
		
	public ArrayList<EffectGained> getHasEffects() {
		return hasEffects;
	}

	public void setHasEffects(ArrayList<EffectGained> hasEffects) {
		this.hasEffects = hasEffects;
	}

	public int getBuy() {
		return buy;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public int getSell() {
		return sell;
	}

	public String getType() {
		return type;
	}

	public boolean isUnique() {
		return unique;
	}

	public ArrayList<FeatureEntry> getFeatureEntry() {
		return featureEntry;
	}

	public MainFeature getMainFeature() {
		return mainFeature;
	}

	public SecondFeature<Magic> getMagic() {
		return magic;
	}

	public void setMagic(SecondFeature<Magic> magic) {
		this.magic = magic;
	}

	public SecondFeature<Skill> getSkill() {
		return skill;
	}

	public void setSkill(SecondFeature<Skill> skill) {
		this.skill = skill;
	}

	public SecondFeature<Weapon> getWeapon() {
		return weapon;
	}

	public void setWeapon(SecondFeature<Weapon> weapon) {
		this.weapon = weapon;
	}
	
	public void cleanLearnFeatures(){
		learnsFeatures.clear();
	}

	public ArrayList<Feature> getLearnsFeatures() {
		return learnsFeatures;
	}

	public void setLearnsFeatures(ArrayList<Feature> learnsFeatures) {
		this.learnsFeatures = learnsFeatures;
	}

	public int getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(int usedTimes) {
		this.usedTimes = usedTimes;
	}
	
	public void increaseUsedTimes(){
		this.usedTimes++;
	}
				
	public String getImageFile() {
		return imageFile;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getEffectName() {
		return effectName;
	}

	public String getEffectType() {
		return effectType;
	}

	public HashMap<String, String> getAnimations() {
		return animations;
	}
}
 
