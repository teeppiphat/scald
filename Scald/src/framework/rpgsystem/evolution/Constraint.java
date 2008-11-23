package framework.rpgsystem.evolution;

import java.util.ArrayList;

import framework.util.LearnTogetherFeature;

/**
 * It tells what is needed to be able to gain a new
 * magic/skill that depends on a lower one
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Constraint {
	
	/**
	 * Holds the Feature that must be used and the number of times
	 * that must be used
	 */
	private ArrayList<LearnTogetherFeature> mustUse;
	
	/**
	 * Constructor of the class
	 */
	public Constraint(){
		mustUse = new ArrayList<LearnTogetherFeature>();
	}
	
	public void addMustUse(LearnTogetherFeature use){
		mustUse.add(use);
	}	

	public ArrayList<LearnTogetherFeature> getMustUse() {
		return mustUse;
	}

	public void setMustUse(ArrayList<LearnTogetherFeature> mustUse) {
		this.mustUse = mustUse;
	}
	
}
 
