package framework.util;

public class EffectGained {

	/**
	 * The type of the effect gained
	 */
	private String type;
	
	/**
	 * The level when is gained
	 */
	private int level;
	
	/**
	 * The way that is gained, by level or by using
	 */
	private String way;
	
	/**
	 * If heals a status, then the name of it
	 */
	private String status;
	
	/**
	 * If causes more damage or increase healing, then how much
	 */
	private int value;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	
	
}
