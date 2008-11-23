package framework.rpgsystem.evolution;

import java.util.ArrayList;

import framework.util.ChangeFeature;


/**
 * Describes a magic, effects, damage, etc
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Magic {
 
	//private MagicAction magicAction;
	 
	//private MagicLeaf magicLeaf;
	 
	//private PlayerGroup playerGroup;
	 
	//private SecondFeature secondFeature;
	 
	//private Enemy enemy;
		
	/**
	 * If the Feature has a cost to use, more to magic,special attack
	 *  and skill
	 */
	private int cost;
	
	/**
	 * Indicate the Feature who pays the cost 
	 */
	private String whoPays;
		
	/**
	 * The target of the Feature, Single or Group
	 */
	private String target;
	
	/**
	 * Specifies the time of the duration, can be turns or seconds,
	 * according to the durationUse
	 */
	private int durationTime;
	
	/**
	 * It can be turns of seconds, according to the type, if
	 * Use is turn, if Time its seconds 
	 */
	private String durationUse;
	
	/**
	 * An array that has the more specifics type of the Feature,
	 * like fire, weapon, status, etc.
	 */
	private ArrayList<String> typeOf;
	
	/**
	 * Array that has the Features that are affected
	 */
	private ArrayList<ChangeFeature> changeFeatures;
	
	/**
	 * Array that has the Features that are needed to use this, the type
	 */
	private ArrayList<String> needFeature;
		
	public Magic(){
		typeOf = new ArrayList<String>();
		changeFeatures = new ArrayList<ChangeFeature>();
		needFeature = new ArrayList<String>();
		cost = 0;		
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getWhoPays() {
		return whoPays;
	}

	public void setWhoPays(String whoPays) {
		this.whoPays = whoPays;
	}	

	public int getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

	public String getDurationUse() {
		return durationUse;
	}

	public void setDurationUse(String durationUse) {
		this.durationUse = durationUse;
	}
	
	public void addTypeOf(String type){
		typeOf.add(type);
	}
	
	public void addNeedFeature(String need){
		needFeature.add(need);
	}
	
	public void addChangeFeature(ChangeFeature change){
		changeFeatures.add(change);
	}

	public ArrayList<ChangeFeature> getChangeFeatures() {
		return changeFeatures;
	}

	public void setChangeFeatures(ArrayList<ChangeFeature> changeFeatures) {
		this.changeFeatures = changeFeatures;
	}

	public ArrayList<String> getNeedFeature() {
		return needFeature;
	}

	public void setNeedFeature(ArrayList<String> needFeature) {
		this.needFeature = needFeature;
	}

	public ArrayList<String> getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(ArrayList<String> typeOf) {
		this.typeOf = typeOf;
	}
	
	public void changeCost(int change){
		cost = cost + change;
	}
	
	public void changeDurationTime(int changes){
		durationTime = durationTime + changes;
	}
	
	public void changeDurationUse(String changes){
		durationUse = changes;
	}
		 
}
 
