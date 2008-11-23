package framework.util;

import java.util.ArrayList;

public class LearnFeature {

	/**
	 * The Feature that is learned
	 */
	private String name;
	
	/**
	 * The way that is learned
	 */
	private String way;
	
	/**
	 * The one that learns the new Feature
	 */
	private boolean who;
	
	/**
	 * The type of the Feature, to know where to look for
	 */
	private String type;
	
	/**
	 * The level when is learned
	 */
	private int level;

	/**
	 * When depends on other Features
	 */
	private ArrayList<LearnTogetherFeature> together;
	
	public LearnFeature(){
		together = null;
		level = -1;
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

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	public boolean isWho() {
		return who;
	}

	public void setWho(boolean who) {
		this.who = who;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void createTogether(){
		//initialize
		together = new ArrayList<LearnTogetherFeature>();	
	}
	
	public void addTogether(LearnTogetherFeature learnTogether){
		together.add(learnTogether);		
	}
	
	public LearnTogetherFeature getTogether(int pos){
		return together.get(pos);
	}

	public ArrayList<LearnTogetherFeature> getTogether() {
		return together;
	}

	public void setTogether(ArrayList<LearnTogetherFeature> together) {
		this.together = together;
	}
	
	
}
