package framework.util;

/**
 * Utility class to help loading Features, used for the ChangeFeatures
 * tag
 * 
 * @author Diego Antônio Tronquini Cost
 *
 */

public class ChangeFeature {

	/**
	 * Holds a name
	 */
	private String name;

	/**
	 * Holds the type
	 */
	private String type;
	
	/**
	 * Holds what changes
	 */
	private String changes;
	
	/**
	 * Holds a value
	 */
	private int value;
	
	/**
	 * Determine the value used to compute
	 */
	private String compute;
	
	/**
	 * Holds the way to calculate, true percentagem, false
	 * value
	 */
	private boolean calculate;

	public boolean isCalculate() {
		return calculate;
	}

	public void setCalculate(boolean calculate) {
		this.calculate = calculate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getCompute() {
		return compute;
	}

	public void setCompute(String compute) {
		this.compute = compute;
	}
			
}
