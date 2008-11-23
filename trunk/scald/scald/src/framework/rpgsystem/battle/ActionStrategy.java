package framework.rpgsystem.battle;

/**
 * Represents how the ations will be used during the battles,
 * if it will be turns, progressive or queue, or  real time, and
 * the type of attacks possibles if it is one simple attack, a
 * sequence of attacks or both of them
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public abstract class ActionStrategy {
 	
	/**
	 * Used to pass the action of the moment, for the animation use
	 */
	protected Action moment;
	
	/**
	 * Attribute used for the option of the character at the battle
	 */
	protected short option;
	
	/**
	 * Attribute used to say what must be done next
	 */
	protected short whoActs;
	
	/**
	 * Attribute used to say which character must act
	 */
	protected short characterActs;
	
	/**
	 * Used to wait until the animation is finished
	 */
	protected boolean showAnimation;
	
	/**
	 * Used to tell if the animation is in loop or not
	 */
	protected boolean continuos;
	
	/**
	 * Used for the effect of a magic or item
	 */
	protected boolean showEffect;
	
	public boolean isContinuos() {
		return continuos;
	}

	public void setContinuos(boolean continuos) {
		this.continuos = continuos;
	}

	/**
	 * Constructor of the class
	 */
	public ActionStrategy(){
		
	}
	 
	/**
	 * Responsible for a simple attack
	 */
	public void simpleAttack(Action action){
		
	}
	 
	/**
	 * Responsible for a sequence of attacks by the same
	 * character
	 */
	public void comboAttack(Action action){
		
	}
	
	/**
	 * Method used to create an Action, must specify the action and uses others parameters
	 * to indicate who is using it, the group is true for player and false for enemies, the
	 * position is used for the position of the character in the array, and where is only if
	 * there´s more than one group of monsters 
	 */
	public void createAction(String type, boolean userGroup, int userPosition,
			boolean targetGroup, int targetPosition, String used, int where) {
		
	}
	 
	/**
	 * Method used to begin a battle, its child must
	 * implement it
	 */
	public void start(){
		
	}
	
	/**
	 * Method used to keep the fight going
	 */
	public void keepFighting(){
		
	}

	public short getOption() {
		return option;
	}

	public void setOption(short option) {
		this.option = option;
	}

	public short getWhoActs() {
		return whoActs;
	}

	public void setWhoActs(short whoActs) {
		this.whoActs = whoActs;
	}

	public short getCharacterActs() {
		return characterActs;
	}

	public void setCharacterActs(short characterActs) {
		this.characterActs = characterActs;
	}

	public boolean isShowAnimation() {
		return showAnimation;
	}

	public void setShowAnimation(boolean showAnimation) {
		this.showAnimation = showAnimation;
	}

	public Action getMoment() {
		return moment;
	}

	public void setMoment(Action moment) {
		this.moment = moment;
	}

	public boolean isShowEffect() {
		return showEffect;
	}

	public void setShowEffect(boolean showEffect) {
		this.showEffect = showEffect;
	}
	
}
 
