package framework.engine.event;

/**
 * Class responsible for the events creating an event and treating them,
 * are used by the EventManager to keep the game running, the engine is
 * oriented by events.
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Event {
 	
	/**
	 * Responsible for saying the kind of event that
	 * was used
	 */
	String event;
	
	
	/**
	 * Responsible for saying what is affected
	 */
	String affected;

	/**
	 * Responsible for saying the direction
	 */
	String direction;

	/**
	 * Used by the mouse, to know the position
	 */
	int posX,posY;
	
	//maybe use the position of the mouse to use it in the world 3D, later
	//it´s something to think
	
	/**
	 * Extra space to be used to put in the event, for text
	 */
	String extra;
	
	/**
	 * Extra space to be used to put in the event, for text
	 */
	String extra2;
	
	/**
	 * Extra space to be used to put in the event, for integer
	 */
	int extra3;
	
	/**
	 * Extra space to be used to put in the event, for integer
	 */
	int extra4;
	
	/**
	 * Constructor of the class, it initializes its
	 * objects
	 */
	public Event(){
		
		//responsible for the type of the event
		event = "";		
		//responsible for who is affected
		affected = "";
		//responsible for the direction
		direction = "";
		
		//gets the position of the mouse in the x axis
		posX = 0;
		//gets the position of the mouse in the y axis
		posY = 0;
		
		extra = null;
		extra2 = null;
		
	}
	
	/**
	 * Constructor of the class that receives all the parameters
	 * needed to create an event
	 * @param event represents the type of the event, mouse or keyboard
	 * @param affected represents who is affected, Character, NPC
	 * @param direction represents to where, move up,down
	 * @param posX represents the position of the mouse, axis X
	 * @param posY represents the position of the mouse, axis Y
	 */
	public Event(String event, String affected, 
			String direction, int posX, int posY){
		
		//responsible for the type of the event
		this.event = event;		
		//responsible for who is affected
		this.affected = affected;
		//responsible for the direction
		this.direction = direction;
		
		//gets the position of the mouse in the x axis
		this.posX = posX;
		//gets the position of the mouse in the y axis
		this.posY = posY;
		
		this.extra = null;		
		this.extra2 = null;
	}

	public String getAffected() {
		return affected;
	}

	public void setAffected(String affected) {
		this.affected = affected;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getExtra3() {
		return extra3;
	}

	public void setExtra3(int extra3) {
		this.extra3 = extra3;
	}

	public int getExtra4() {
		return extra4;
	}

	public void setExtra4(int extra4) {
		this.extra4 = extra4;
	}	
	
	
	
}
 
