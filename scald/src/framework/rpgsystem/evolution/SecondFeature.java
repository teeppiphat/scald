package framework.rpgsystem.evolution;

/**
 * It is used to describe a Feature and by the MainFeature
 * to indicate Features that evolve with the Feature in question
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */

public class SecondFeature<X> {
 
	//private Magic magic;
		 
	//private Skill skill;
	 
	//private Weapon weapon;
			
	/**
	 * Has one of these types, Magic, Skill or Weapon
	 */
	private X feature;
		
	public void setFeature(X f){
		feature = f;
	}
	
	public X getFeature(){
		return feature;
	}

}
 
