package framework.rpgsystem.evolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;
import framework.util.EvolveTogetherFeature;
import framework.util.LearnTogetherFeature;

/**
 * Has all the Features that are used in the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FeatureNetwork {

	//divide for the types of Features that are needed

	/**
	 * An array used to hold the Features of the type status
	 */
	private ArrayList<Feature> status;

	/**
	 * An array used to hold the Features of the type skills
	 */
	private ArrayList<Feature> skills;

	/**
	 * An array used to hold the magics, special attacks
	 */
	private ArrayList<Feature> magics;

	/**
	 * An array used to hold the restrictions
	 */
	private ArrayList<Feature> restrictions;

	/**
	 * Constructor of the class
	 */
	public FeatureNetwork(){
		status = new ArrayList<Feature>();
		skills = new ArrayList<Feature>();		
		magics = new ArrayList<Feature>(); 
		restrictions = new ArrayList<Feature>();
	}

	/**
	 * Method used to load the Features of the type status
	 * 
	 * @param stat
	 *            an Element
	 */
	public void loadStatus(Element stat){
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

	/**
	 * Method used to load the Features of the type skill
	 * 
	 * @param skill
	 *            an Element
	 */
	public void loadSkills(Element skill){
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

	/**
	 * Method used to load the Features of the type restrictions
	 * 
	 * @param rest
	 *            an Element
	 */
	public void loadRestrictions(Element rest){
		// get the children to a list
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

	/**
	 * Method used to learn a new Feature
	 * @param name the identifier of the Feature, a String
	 * @param type the type of the Feature, a String
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

	public Feature getLearnFeature(String name,String type){		
		//according to the type will place it
		if(type.equals("Skill")){
			//load a new skill
			return loadFeat(name,false);
		}else if(type.equals("Status")){
			//load a new status
			return loadFeat(name,true);
		}else if(type.equals("Restriction")){
			//load a new restriction
			return loadFeat(name,true);
		}else if(type.equals("Magic")){
			//load a new magic
			return loadFeat(name,false);
		}

		//not found
		return null;		
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
	public Feature loadFeat(String name, boolean type){

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
		}else if(type.equals("Status")){
			return getStatus(name);
		} else if (type.equals("Restriction")) {
			return getRestriction(name);
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

	public void evolveFeature(String name, int increase){
		//evolve a status of the class
		for(int i = 0; i < status.size(); i++){
			//try if is the status wanted
			if(status.get(i).getName().equals(name)){				
				//found then increase the value
				status.get(i).getSkill().getFeature().increaseValue(increase);
			}else{
				
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
	 * Change the value of a Status, by receiving the name and the new value
	 * 
	 * @param name
	 *            a String
	 * @param value
	 *            an integer
	 */
	public void setStatusCurrent(String name, int value) {
		//evolve a status of the class
		for(int i = 0; i < status.size(); i++){
			//try if is the status wanted
			if(status.get(i).getName().equals(name)){
				//found then increase the value
				status.get(i).getSkill().getFeature().setCurrent(value);
			}
		}
	}
	
	public int getStatusValue(String name){
		//return the value wanted
		return getStatus(name).getSkill().getFeature().getValue();
	}
	
	public int getStatusCurrent(String name){
		//return the wanted value
		return getStatus(name).getSkill().getFeature().getCurrent();
	}
	
	public void refreshStatus(){
		for(int i = 0; i < status.size(); i++){
			if(status.get(i).getName().length() > 2){
				status.get(i).getSkill().getFeature().refreshCurrent();
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
		
		//status
		for(int i = 0; i < status.size(); i++){
			//get the Feature
			status.get(i).gainsExperience(gains,identifier);			
		}		
		//skills
		for(int i = 0; i < skills.size(); i++){
			//get the Feature
			skills.get(i).gainsExperience(gains,identifier);			
		}		
		//restrictions
		for(int i = 0; i < restrictions.size(); i++){
			//get the Feature
			restrictions.get(i).gainsExperience(gains,identifier);			
		}		
		//magics
		for(int i = 0; i < magics.size(); i++){
			//get the Feature
			magics.get(i).gainsExperience(gains,identifier);			
		}			
	}
	
	public void gainsExperienceUsed(Feature feature,String identifier){
		//the Feature is going to gain experience, if is more then 0
		if(feature.getMainFeature().getNeedGains() > 0){
			//gives experience to this feature
			feature.gainsExperienceByUse(feature.getMainFeature().getNeedGains(), identifier);
			//gives experience to those that gain together
			gainsExperienceTogether(feature.getMainFeature().getEvolveTogether(), feature.getMainFeature().getNeedGains(), identifier);
		}
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
	
	public void useFeature(String name,String identifier,boolean userGroup, int userPosition, boolean targetGroup, int targetPosition){
		
		//get the Feature that is going to be used, the only ones
		//that can be used directly are the Magics
		
		//get the Magic
		//use the magic
		getMagic(name).useFeature(userGroup, userPosition, targetGroup, targetPosition);
		
		//use it according to the parameters
		
		//increase the number of times used
		
		//test if learn something
		
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
		
		//gain experience by being used, a fixed value
		gainsExperienceUsed(getMagic(name),identifier);
		
		//test if learn something
		learnsNewFeatureUsed(name);
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

	public ArrayList<Feature> getMagics() {
		return magics;
	}

	public ArrayList<Feature> getRestrictions() {
		return restrictions;
	}

	public ArrayList<Feature> getSkills() {
		return skills;
	}

	public ArrayList<Feature> getStatus() {
		return status;
	}
	
}

