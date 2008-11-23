package framework.util;

public class LearnTogetherFeature {

	/**
	 * The name of the Feature that is being depended upon
	 */
	public String name;
	
	/**
	 * The level when is learned, can be number of times also
	 */
	public int level;
	
	/**
	 * Holds the type of the Feature that is needed
	 */
	public String type;

	/**
	 * Constructor of the class
	 */
	public LearnTogetherFeature(){
		
	}

	/**
	 * Constructor of the class, receives the values
	 * @param name the name of the Feature, a String
	 * @param level the level when is learned, an integer
	 */
	public LearnTogetherFeature(String name,int level,String type){
		this.name = name;
		this.level = level;
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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
