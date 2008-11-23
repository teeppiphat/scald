package framework.rpgsystem.map;


/**
 * Represents the points that can be accessed
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Point {
 
	/**
	 * Its the identifier of the point, its name
	 */
	private String name;
	
	/**
	 * Identifies the path that has up
	 */
	private String up;

	/**
	 * Identifies the path that has down
	 */
	private String down;
	
	/**
	 * Identifies the path that has left
	 */
	private String left;
	
	/**
	 * Identifies the path that has right
	 */
	private String right;
	
	/**
	 * Holds the position of the point in the map
	 */
	private int positionX;
	
	private int positionY;
	
	/**
	 * Constructor of the class
	 */
	public Point(){
		
	}
	
	/**
	 * Constructor of the class, receives the values and
	 * just set them in their rescpective places
	 * @param name the identified of the point, a String
	 * @param position the position of the point, a Vector3f
	 */
	public Point(String name, int positionX,int positionY, String up, String down,
			String left, String right) {
		this.name = name;
		this.positionX = positionX;
		this.positionY = positionY;
		this.up = up;
		this.down = down;
		this.right = right;
		this.left = left;
	}

	/**
	 * Method used to return the identifier
	 * @return the identifier of the point, a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used to change the identifier of the map
	 * @param name receives the new identifier, a String
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getDown() {
		return down;
	}

	public String getLeft() {
		return left;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public String getRight() {
		return right;
	}

	public String getUp() {
		return up;
	}
	
	
	
}
 
