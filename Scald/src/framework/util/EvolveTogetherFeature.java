package framework.util;

public class EvolveTogetherFeature {

	/**
	 * The name of the Feature that evolves together
	 */
	private String name;

	/**
	 * The type of the Feature that evolves together
	 */
	private String type;
	
	/**
	 * The percentage that the Feature gains of the total experience
	 */
	private int experience;

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
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
