package framework.rpgsystem.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;
import framework.rpgsystem.evolution.Feature;
import framework.rpgsystem.evolution.FeatureEntry;
import framework.rpgsystem.evolution.Magic;
import framework.rpgsystem.evolution.SecondFeature;
import framework.rpgsystem.evolution.Weapon;
import framework.util.ChangeFeature;
import framework.util.EffectGained;
import framework.util.EvolveTogetherFeature;
import framework.util.LearnFeature;
import framework.util.LearnTogetherFeature;

/**
 * It represents an item, if it evolves and its effects
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Item implements Serializable{
 	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the effect of the item, if is a usable
	 * item
	 */
	private SecondFeature<Magic> usable;
	
	/**
	 * Represents the item, if is an equipament
	 */
	private SecondFeature<Weapon> equipament;
		
	/**
	 * Represents if the item can evolve
	 */
	private ItemXPTable itemXPTable;
	
	/**
	 * Represents the identifier of the item
	 */
	private String identifier;
	
	/**
	 * Represents the name of the item
	 */
	private String name;

	/**
	 * Responsible for saying if a item is unique or not, in this case
	 * can´t have more then one slot with this type of item
	 */
	private boolean unique;
		
	/**
	 * The type of the Item, if is equipament or magic,
	 * more of a general type
	 */
	private String type;
	
	/**
	 * Has the description of the item
	 */
	private String description;
	
	/**
	 * The value to buy this item
	 */
	private int buy;
	
	/**
	 * The value to sell this item
	 */	
	private int sell;
		
	/**
	 * Holds the amount of experience of the item
	 */
	private int experience;
	
	/**
	 * Holds the level of the item
	 */
	private int level;
	
	/**
	 * Holds the maximum quantity can have
	 */
	private int maxQuantity;
	
	/**
	 * Holds the Features that evolve together with this
	 */
	private ArrayList<EvolveTogetherFeature> evolveTogether;
	
	/**
	 * Holds the places that this item is equipped
	 */
	private ArrayList<String> whereIsEquipped;
	
	/**
	 * Holds the type of Feature needed to use this item
	 */
	private String needFeatureType;
	
	/**
	 * If needs to be used to evolve or only hold to it, at least
	 * must be equipped
	 */
	private boolean needUse;
	
	/**
	 * Holds the Features that are learned by the item
	 */
	private ArrayList<Feature> learnsFeatures;
	
	/**
	 * Holds the file of the image that represents the item in the menu
	 */
	private String imageFile;
	
	/**
	 * Holds the path of the image that represents the item in the menu
	 */
	private String imagePath;
	
	/**
	 * Holds the number of times 
	 */
	private int usedTimes;
	
	/**
	 * Holds the effects that the Feature has gained
	 */
	private ArrayList<EffectGained> hasEffects;
	
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
	 * Constructor of the class
	 */
	public Item(){
		usable = null;
		equipament = null;
		evolveTogether = null;		
		itemXPTable = null;
		experience = 0;
		level = 0;
		maxQuantity = 0;
		learnsFeatures = new ArrayList<Feature>();
		whereIsEquipped = null;
		imageFile = null; 
		imagePath = null;
		hasEffects = null;
		usedTimes = 0;
		animations = new HashMap<String, String>();
	}
	
	/**
	 * Method used to search the configuration of the item, by receiving its
	 * name
	 * 
	 * @param item
	 *            a String
	 * @return an Element
	 */
	public Element searchItemConfiguration(String item){
		//get the item configuration on the file
		Element it = FactoryManager
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
								"Itens"));
		
		//return the Element wanted
		return it.getChild(item);
	}
	
	/**
	 * Method used to create an item by receiving its name and calling
	 * everything that is needed
	 * 
	 * @param item
	 *            a String
	 */
	public void create(String item){

		//create itself by using its name
		createItem(searchItemConfiguration(item));
	}
	
	/**
	 * Method used to create an item, receives the Element that represents the
	 * item that must be created 
	 * @param item an Element
	 */
	public void createItem(Element item){
		
		//create according to its type
		identifier = item.getName();
		//load the general information
		loadGeneral(item.getChild("General"));
		
		//load the specific type
		loadSpecificType(item.getChild("SpecificType"));
		
		//load the cost to use
		loadUseCost(item.getChild("ToUse"));
		
		//load the features that change
		loadChangeOthersFeatures(item.getChild("ChangeOtherFeatures"));
		
		//load the need for a feature to equip
		loadNeedOtherFeature(item.getChild("NeedOtherFeature"));
		
		//load the duration
		loadDuration(item.getChild("Duration"));
		
		//load the target
		loadTarget(item.getChild("Target"));
		
		//load the buy specifications
		loadBuySpecifications(item.getChild("BuySpecification"));
		
		//load if the item evolves
		loadEvolution(item.getChild("Evolution"));
		
		//load when is learned
		loadLearn(item.getChild("Learn"));
		
		//load where is equipped
		loadEquippedPlace(item.getChild("Equipped"));
		
		//load the image used for the menu
		loadMenuImage(item.getChild("MenuImage"));
		
		// load the animations used for this feature
		loadAnimation(item.getChild("AnimationUsed"));
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
		//get all the typesOf
		List list = type.getChildren();
		//temporary object
		Element temp;
		
		//load an usable item
		if(this.type.equals("Magic")){
			//create the object
			usable = new SecondFeature<Magic>();
			//create the inside
			usable.setFeature(new Magic());
			
			//set the configurations for it
									
			//put the elements in its place
			for(int i = 0; i < list.size(); i++){
				//get the element
				temp = (Element)list.get(i);
				//store the element
				usable.getFeature().addTypeOf(temp.getText());
			}
			
		}//load an equipament
		else{
			//create the object
			equipament = new SecondFeature<Weapon>();
			//create the inside
			equipament.setFeature(new Weapon());
			
			//set the configurations for it
			
			//put the elements in its place
			for(int i = 0; i < list.size(); i++){
				//get the element
				temp = (Element)list.get(i);
				//store the element
				equipament.getFeature().addTypeOf(temp.getText());
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
				usable.getFeature().setCost(Integer.parseInt(cost.getChildText("Cost")));
				//load who pays
				usable.getFeature().setWhoPays(cost.getChildText("WhoPays"));
			}else{
				//load the cost
				equipament.getFeature().setCost(Integer.parseInt(cost.getChildText("Cost")));
				//load who pays
				equipament.getFeature().setWhoPays(cost.getChildText("WhoPays"));
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
			
			//the usable item
			if(type.equals("Magic")){
				
				//loop
				while(i.hasNext()){
					//get the object
					temp = (Element)i.next();					
					//create the object to store
					ChangeFeature object = new ChangeFeature();
					//set the name
					object.setName(temp.getChildText("Name"));
					//set the value
					object.setValue(Integer
							.parseInt(temp.getChildText("Value")));
					//set the type
					object.setType(temp.getChildText("Type"));
					//set the changes
					object.setChanges(temp.getChildText("Changes"));
					//set the way to calculate
					object.setCalculate(Boolean.parseBoolean(temp
							.getChildText("Calculate")));
					//set the way to compute
					object.setCompute(temp.getChildText("Compute"));
					
					//store the object
					usable.getFeature().addChangeFeature(object);
				}				
			}else //the equipament
			{
				
				//loop
				while(i.hasNext()){
					//get the object
					temp = (Element)i.next();					
					//create the object to store
					ChangeFeature object = new ChangeFeature();
					//set the name
					object.setName(temp.getChildText("Name"));
					//set the value
					object.setValue(Integer
							.parseInt(temp.getChildText("Value")));
					//set the way to calculate
					object.setCalculate(Boolean.parseBoolean(temp
							.getChildText("Calculate")));
					//store the object
					equipament.getFeature().addChangeFeature(object);
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
			//get all the typesOf
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
					usable.getFeature().addNeedFeature(temp.getText());
				}
			}else{
				//put the elements in its place
				for(int i = 0; i < list.size(); i++){
					//get the element
					temp = (Element)list.get(i);
					//store the element
					equipament.getFeature().addNeedFeature(temp.getText());
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
				usable.getFeature().setDurationUse(duration.getChildText("Type"));
				//set the time
				usable.getFeature().setDurationTime(Integer.parseInt(duration.getChildText("Time")));
			}else{
				//set the duration
				equipament.getFeature().setDurationUse(duration.getChildText("Type"));
				//set the time
				equipament.getFeature().setDurationTime(Integer.parseInt(duration.getChildText("Time")));
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
				usable.getFeature().setTarget(target.getText());				
			}else{
				//set the target type
				equipament.getFeature().setTarget(target.getText());
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
			// get the maximum quantity can have
			maxQuantity = Integer.parseInt(specifications.getChildText("MaximumQuantity"));
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
			//initialize what is needed
			evolveTogether = new ArrayList<EvolveTogetherFeature>();
			
			//get all the elements
			List list = together.getChildren();
			//iterator to get the values in the list
			Iterator i = list.iterator();
			//temporary object used to store
			Element temp;
			//loop
			while(i.hasNext()){
				//get the object
				temp = (Element)i.next();					
				//create the object to store
				EvolveTogetherFeature object = new EvolveTogetherFeature();
				//set the name
				object.setName(temp.getChildText("Name"));
				//set the value
				object.setExperience(Integer
						.parseInt(temp.getChildText("Value")));					
				evolveTogether.add(object);
			}
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
			//get the type of Feature needed
			needFeatureType = needTo.getChildText("NeedFeatureType");
			//get if needs to be used to evolve
			needUse = Boolean.parseBoolean(needTo.getChildText("NeedUse"));
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
			// get the file
			itemXPTable.getItemXPTableEntry().setStatusFile(status.getChildText("File"));
			// get the path
			itemXPTable.getItemXPTableEntry().setStatusPath(status.getChildText("Path"));
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
			//get all the elements
			List list = effect.getChildren();
			//iterator to get the values in the list
			Iterator i = list.iterator();
			//temporary object used to store
			Element temp;			
			//loop
			while(i.hasNext()){
				//get the object
				temp = (Element)i.next();					
				//create the object to store
				EffectGained object = new EffectGained();
				//set the name
				object.setType(temp.getChildText("Name"));
				//set the value
				object.setLevel(Integer
						.parseInt(temp.getChildText("Value")));	
				//add the object created
				itemXPTable.getItemXPTableEntry().addEffectGained(object);
			}
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
			//initialize the table
			itemXPTable = new ItemXPTable();			
			//load the table
			itemXPTable.getTable(table.getChildText("Path"), table
					.getChildText("Path"), Integer.parseInt(table
					.getChildText("MaxLevel")));
			}
	}
	
	/**
	 * Method used to load what is learned by or throught this Feature
	 * @param learn an Element
	 */
	public void loadLearn(Element learn){
		if(learn != null){
			
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
				
				//according to the way that is learned load the rest
				if(learned.getWay().equals("Level")){
					//get the level when is learned
					learned.setLevel(Integer.parseInt(temp.getChildText("Level")));
				}else{
					// tell to load the together
					learned.setTogether(loadLearnTogether(temp.getChild("Together")));
				}
				
				//keep the object
				itemXPTable.getItemXPTableEntry().addLearnFeature(learned);
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
	 * Method used to load the place where can be equipped equipped
	 * 
	 * @param eq
	 *            an Element
	 */
	public void loadEquippedPlace(Element eq){
		if(eq != null){
			
			//initialize array
			whereIsEquipped = new ArrayList<String>();
			//get the list
			List list = eq.getChildren();
			//temporary element
			Element temp;
			//get everything
			for(int i = 0; i < list.size(); i++){
				//get the element
				temp = (Element)list.get(i);
				//pass to the array
				whereIsEquipped.add(temp.getText());
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
		
		if(!testExist(name, type)){
			//according to the type will load it
			if(type.equals("Skill")){
				//load a new skill
				learnsFeatures.add(loadFeat(name,true));
			}else if(type.equals("Status")){
				//load a new status
				learnsFeatures.add(loadFeat(name,true));
			}else if(type.equals("Restriction")){
				//load a new restriction
				learnsFeatures.add(loadFeat(name,true));
			}else if(type.equals("Magic")){
				//load a new magic
				learnsFeatures.add(loadFeat(name,false));
			}
		}
	}
	
	public boolean testExist(String name, String type){
		//test if exists
		if(getFeature(name) != null)
			return true;
		
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
		for(int i = 0; i < itemXPTable.getItemXPTableEntry().getLearns().size(); i++){
			//get one of the Feature
			temp = itemXPTable.getItemXPTableEntry().getLearns().get(i);
						
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
		for(int i = 0; i < itemXPTable.getItemXPTableEntry().getLearns().size(); i++){
			//get one of the Feature
			temp = itemXPTable.getItemXPTableEntry().getLearns().get(i);
						
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
		temp = getFeature(name);
		
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
		
	public void gainsExperienceTogether(ArrayList<EvolveTogetherFeature> array,int value,String identifier){
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

	/**
	 * Method used to get a Feature by giving the name and its type
	 * @param type a String
	 * @param name a String
	 * @return the Feature or null, if not found
	 */
	public Feature searchFeature(String type,String name){
		//will search for a Feature that has
		return getFeature(name);
	}
	
	/**
	 * Method used to get a Feature learned by giving the name of the Feature wanted
	 * @param name a String
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
	public void gainsExperience(int gains){
		//if the item evolves
		if(itemXPTable != null){
			//gains experience
			experience = experience + gains;
			//if the amount of experience surpass the required for the next level

			//level 2 is position 1 of the array, then not a problem

			//need to know how many levels passed, will make once for
			//each level that passed
			while(experience > itemXPTable.getXP(level)){
				//increase the level
				level++;
				//calls the script responsible for evolving the item, if evolves status
				itemXPTable.getItemXPTableEntry().gainLevel(level,name);
				//test if a Feature was learned by the evolution of the class
				learnsNewFeaturesLevel();	
			}
		}
	}
	
	public void useFeature(boolean group, int position){
		//get the Feature that is going to be used, the only ones
		//that can be used directly are the Magics
		
		//the item will use itself		
		//already has the target
		
		//if the item evolve
		if(itemXPTable != null){
			//increase the number of times used
			increaseUsedTimes();
			
			//gains effect
			gainsEffectUsed();
			
			//gain experience by being used, a fixed value
			gainsExperience(10);
			
			//test if learn something
			learnsNewFeatureUsed(name);			
		}
		
		//use it according to the parameters, its own parameters
		fireFeature(group, position);
				
	}
	
	public void fireFeature(boolean group, int position){

		//use the item according to its values and the type
		
		if(type.equals("Magic")){
			//will use itself according to the change features
			
			//must go through all the possible changes
			for(int i = 0; i < usable.getFeature().getChangeFeatures().size(); i++){
				//calculate it
				calculate(group, position, usable.getFeature().getChangeFeatures().get(i), usable.getFeature().getTarget());
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
		
	
	
	public Feature getSkillNeedEquip(String specific){
		//look for a skill that lets use an equipament specified
		for(int i = 0; i < learnsFeatures.size(); i++){
			//needs a Feature of the type Skill
			if(learnsFeatures.get(i).getType().equals("Skill")){
				if(learnsFeatures.get(i).getSkill().getFeature().getTypeOf().get(0).equals("Equipament")){			
					//find the type of the item that can use
					if(learnsFeatures.get(i).getSkill().getFeature().getTypeOf().get(1).equals(specific)){
						//found the skill needed to equip
						return learnsFeatures.get(i);
					}				
				}
			}			
		}
		//not found
		return null;
	}
	
	public void gainsEffectUsed(){
		//temporary object
		EffectGained temp;
		
		//test all the effects that can gain
		for(int i = 0; i < itemXPTable.getItemXPTableEntry().getEffects().size(); i++){
			//get one effect
			temp = itemXPTable.getItemXPTableEntry().getEffects().get(i);
			
			//test the way is learned, in this case Used
			if(temp.getWay().equals("Used")){
				//test the number of times that was used
				if(usedTimes == temp.getLevel()){
					//gains the effect
					hasEffects.add(temp);
					//testing
					System.out.println("Effect gained by using");
				}				
			}
		}
	}
	
	public ArrayList<EffectGained> getHasEffects() {
		return hasEffects;
	}
	
	/**
	 * Method used to get the name of the item
	 * @return the name of the item, a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used to set the name of an item
	 * @param name receives the new name, a String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method used to get if the item is unique or not
	 * @return true is unique, false not, a boolean
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * Method used to set if the item is unique or not
	 * @param unique true is unique, false not, a boolean
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public int getBuy() {
		return buy;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNeedFeatureType() {
		return needFeatureType;
	}

	public void setNeedFeatureType(String needFeatureType) {
		this.needFeatureType = needFeatureType;
	}

	public boolean isNeedUse() {
		return needUse;
	}

	public void setNeedUse(boolean needUse) {
		this.needUse = needUse;
	}

	public int getSell() {
		return sell;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
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

	public ArrayList<String> getWhereIsEquipped() {
		return whereIsEquipped;
	}

	public void setWhereIsEquipped(ArrayList<String> whereIsEquipped) {
		this.whereIsEquipped = whereIsEquipped;
	}

	public SecondFeature<Weapon> getEquipament() {
		return equipament;
	}

	public void setEquipament(SecondFeature<Weapon> equipament) {
		this.equipament = equipament;
	}

	public SecondFeature<Magic> getUsable() {
		return usable;
	}

	public void setUsable(SecondFeature<Magic> usable) {
		this.usable = usable;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getImageFile() {
		return imageFile;
	}

	public String getImagePath() {
		return imagePath;
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
 
