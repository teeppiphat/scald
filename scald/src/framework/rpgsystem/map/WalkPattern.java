package framework.rpgsystem.map;

/**
 * Represents the way that is possible to move in the map
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class WalkPattern<X> {
 
	/**
	 * Holds the pattern used to walk in this map, in the
	 * case can be a Free or a Path pattern, according to
	 * the need, thanks to templates
	 */
	private X pattern;
	
	/**
	 * Represents the way to walk freely in the map
	 */
	//private Free free;
	 
	/**
	 * Represents the way to walk only to the places
	 * that are specified
	 */
	//private Path path;
	 
	/**
	 * Represents the events that can be generated 
	 */
	private Trigger[] trigger;
	
	/**
	 * Constructor of the class
	 */
	public WalkPattern(){
		
	}
	
	public X getPattern() {
		return pattern;
	}

	public void setPattern(X pattern) {
		this.pattern = pattern;
	} 
		
}
 
