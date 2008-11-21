package framework.rpgsystem.evolution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.util.EffectGained;
import framework.util.EvolveTogetherFeature;

/**
 * Represents the Feature in question, by having the description
 * saying if it evolves, if it depends of others Features and if
 * others Features evolve together
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class MainFeature implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	/**
	 * Responsible for the evolution of the Feature
	 */
	private FeatureXPTable featureXPTable;
	
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
	 * Holds how much the Feature gains of experience by being used
	 */
	private int needGains;
	
	/**
	 * Holds the Features that evolve together with this
	 */
	private ArrayList<EvolveTogetherFeature> evolveTogether;
			
	/**
	 * Represents the experience of the Feature, if it can gain is used
	 * else isn´t used
	 */
	private int experience;
	
	/**
	 * Holds the level of the Feature
	 */
	private int level;
		
	/**
	 * Constructor of the class
	 */
	public MainFeature(){
		//initialize the experience
		experience = 0;
		//initialize the level
		level = 1;
		//initialize the table
		featureXPTable = new FeatureXPTable();
	}	
	
	public void loadEvolveTogether(Element together){
		// initialize what is needed
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
			//set the type
			object.setType(temp.getChildText("Type"));
			//set the value
			object.setExperience(Integer
					.parseInt(temp.getChildText("Experience")));					
			evolveTogether.add(object);
		}
	}
	
	public void loadNeedToEvolve(Element needTo){		
		// get the type of Feature needed
		needFeatureType = needTo.getChildText("NeedFeatureType");
		// get if needs to be used to evolve
		needUse = Boolean.parseBoolean(needTo.getChildText("NeedUse"));		
		// get the quantity of experience needed
		needGains = Integer.parseInt(needTo.getChildText("Gained"));		
	}
	
	public void loadEvolveStatus(Element status){		
		//pass to the one responsible
		//get the file
		featureXPTable.getFeatureXPTableEntry().setStatusFile(status.getChildText("File"));
		// get the path
		featureXPTable.getFeatureXPTableEntry().setStatusPath(status.getChildText("Path"));
	}
	
	public void loadEffectGained(Element effect){		
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
			object.setType(temp.getChildText("Type"));
			//set the way is learned
			object.setWay(temp.getChildText("Way"));
			//set when is learned
			object.setLevel(Integer
					.parseInt(temp.getChildText("Gained")));	
			//set the value
			object.setValue(Integer.parseInt(temp.getChildText("Value")));
			//set the status
			object.setStatus(temp.getChildText("Status"));				

			//add the object created
			featureXPTable.getFeatureXPTableEntry().addEffectGained(object);			
		}
	}
	
	public void increaseExperience(int gains){
		experience = experience + gains;
	}
	
	public void increaseLevel(){
		//if hasn´t achieved the maximum level
		if(level < featureXPTable.getMaxLevel()){
			//increase the level of the Feature
			level++;
		}
	}
	
	public void evolveStatus(String identifier){
		featureXPTable.getFeatureXPTableEntry().gainLevel(level, identifier);
	}
	
	public FeatureXPTable getFeatureXPTable() {
		return featureXPTable;
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

	public int getNeedGains() {
		return needGains;
	}

	public void setNeedGains(int needGains) {
		this.needGains = needGains;
	}

	public ArrayList<EvolveTogetherFeature> getEvolveTogether() {
		return evolveTogether;
	}

	public void setEvolveTogether(ArrayList<EvolveTogetherFeature> evolveTogether) {
		this.evolveTogether = evolveTogether;
	}
	
	
	
	
}
 
