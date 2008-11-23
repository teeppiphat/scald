package framework.util;

import java.util.ArrayList;

/**
 * Class used to load the table entry
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class LoadTableEntry {

	/**
	 * The file used to evolve the status when this Feature evolves
	 */
	protected String statusFile;
	
	/**
	 * The place where the file is stored
	 */
	protected String statusPath;
		
	/**
	 * The new effects the Feature gains when evolve
	 */
	protected ArrayList<EffectGained> effects;
	
	/**
	 * The Features that are learned
	 */
	protected ArrayList<LearnFeature> learns;
	
	/**
	 * Constructor of the class
	 */
	public LoadTableEntry(){
		learns = new ArrayList<LearnFeature>();
		effects = new ArrayList<EffectGained>();
		statusFile = null;
		statusPath = null;
	}

	public String getStatusFile() {
		return statusFile;
	}

	public void setStatusFile(String statusFile) {
		this.statusFile = statusFile;
	}

	public String getStatusPath() {
		return statusPath;
	}

	public void setStatusPath(String statusPath) {
		this.statusPath = statusPath;
	}
	
	public void addLearnFeature(LearnFeature learn){
		learns.add(learn);
	}
	
	public void addEffectGained(EffectGained effect){
		effects.add(effect);
	}

	public ArrayList<LearnFeature> getLearns() {
		return learns;
	}

	public ArrayList<EffectGained> getEffects() {
		return effects;
	}

	public void setEffects(ArrayList<EffectGained> effects) {
		this.effects = effects;
	}

	public void setLearns(ArrayList<LearnFeature> learns) {
		this.learns = learns;
	}
				
}
