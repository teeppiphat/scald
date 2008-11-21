package framework.util;

public class StrategyAction {

	/**
	 * Holds the type of the action
	 */
	private String action;
	
	/**
	 * Holds the initial chance
	 */
	private int initial;
	
	/**
	 * Holds the final chance
	 */
	private int end;
		
	/**
	 * Holds the name of what is used, for item and magic
	 */
	private String name;
	
	/**
	 * Constructor of the class
	 */
	public StrategyAction(){
		
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getInitial() {
		return initial;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
