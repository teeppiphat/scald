package framework.rpgsystem.battle;

/**
 * The actions that are used during a battle, being them all 
 * the ones that can be used, dependind of the ones that are
 * possible for different types of characters, like one can
 * use magic and the other can use special attacks, and the
 * common actions like using itens, defending, running, and others
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Action {
	
	//atributes of the Action
	
	/**
	 * Identifies which group is the action from,
	 * true = player group
	 * false = enemy group
	 */
	private boolean userGroup;
	
	/**
	 * Identifies which group is the action affecting,
	 * true = player group
	 * false = enemy group
	 */
	private boolean targetGroup;
	
	/**
	 * The position of the user in its group
	 */
	private int userPosition;
	
	/**
	 * The position of the target in its group
	 */
	private int targetPosition;
	/**
	 * The name of the one that is doing this action
	 */
	private String name;
	
	/**
	 * The name of the action that will be used, attack,
	 * magic, etc
	 */
	private String what;
	
	/**
	 * If its an item or magic, then holds the one that
	 * is used
	 */
	private String used;
	
	/**
	 * Indicates the speed of when will be used, the greater the speed the best
	 */
	private int speed;
	
	/**
	 * Constructor of the class
	 */
	public Action(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(boolean targetGroup) {
		this.targetGroup = targetGroup;
	}

	public int getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(int targetPosition) {
		this.targetPosition = targetPosition;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public boolean isUserGroup() {
		return userGroup;
	}

	public void setUserGroup(boolean userGroup) {
		this.userGroup = userGroup;
	}

	public int getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(int userPosition) {
		this.userPosition = userPosition;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}
		
}
 
