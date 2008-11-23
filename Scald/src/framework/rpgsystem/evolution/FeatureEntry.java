package framework.rpgsystem.evolution;

import java.util.ArrayList;

import framework.util.LearnTogetherFeature;

/**
 * Represents the new abilities that can be gained
 * by using that that already is avainable
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class FeatureEntry {
 
	/**
	 * Feature that is learned
	 */
	private String name;
	
	/**
	 * The type of the Feature that is learned
	 */
	private String type;
	
	/**
	 * Holds what is needed to learn the Feature
	 */
	private Constraint constraint;

	/**
	 * Constructor of the class
	 */
	public FeatureEntry(){
		constraint = new Constraint();
	}
	
	public void createConstraint(ArrayList<LearnTogetherFeature> cons){
		constraint.setMustUse(cons);
	}
	
	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
}
 
