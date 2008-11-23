package framework.engine.physic;

import com.jme.math.Vector3f;
import com.jme.scene.Controller;
import com.jmex.effects.particles.ParticleGeometry;

import framework.RPGSystem;

/**
 * Class responsible for taking care to update the particle that was
 * created
 * @author Diego Antônio Tronquini Costi
 *
 */
public class ParticleController extends Controller {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//the time to live
	private float life;

	//the object
	private ParticleGeometry object;

	//the destination, if used
	private Vector3f destination[];
	
	//change the direction of the emission
	private Vector3f emission[];

	//change destination to move
	private int moveTo;
	
	//change to where emit
	private int emissionTo;
	
	//if it must go back from the last path to the first
	private boolean circle;
	//if it must go back from the last emission to the first
	private boolean circleEmission;
	
	//uses the destination
	private boolean move;
	//change the emission
	private boolean changeDirection;

	//used to move
	private float increase;
	
	//used to emission
	private float moveEmission;

	//used to test the values of position, compare up to 2 houses
	private int x,y,z;
	
	public ParticleController() {

	}

	/**
	 * Constructor of the class
	 * @param life the time the particle will live
	 * @param object the particle
	 */
	public ParticleController(float life, ParticleGeometry object) {
		//get the time to live
		this.life = life;
		//get the object
		this.object = object;
		//if the particle source move
		this.move = false;
		//get the destination position
		this.destination = null;
		//get the emission position
		this.emission = null;
		//get how much increase at a time
		this.increase = 0;
		//get how much change emission
		this.moveEmission = 0;		
		//don´t have a destination
		this.moveTo = 0;
		//don´t move back
		this.circle = false;
		//don´t emission back
		this.circleEmission = false;		
		//don´t change emission
		this.emissionTo = 0;

	}
	
	/**
	 * Constructor of the class
	 * @param life the time the particle will live
	 * @param object the particle
	 * @param move if the particle will move
	 * @param destination to where the particle will move
	 * @param increase how much will move each time
	 */
	public ParticleController(float life, ParticleGeometry object, boolean move,
			Vector3f destination[], float increase, boolean circle,float moveEmission,Vector3f emission[],boolean circleEmission,boolean changeDirection) {
		//get the time to live
		this.life = life;
		//get the object
		this.object = object;
		//if the particle source move
		this.move = move;
		//if the direction to emit change
		this.changeDirection = changeDirection;
		//get the destination position
		this.destination = destination;
		//get the emission position
		this.emission = emission;
		//get how much increase at a time
		this.increase = increase;
		// get how much change emission
		this.moveEmission = moveEmission;
		//begins at the first position in the array
		this.moveTo = 0;		
		//begins at first position of the emission
		this.emissionTo = 0;
		//move back or not
		this.circle = circle;
		//emission back or not
		this.circleEmission = circleEmission;
		
		if(move){
			//set the position of the object
			this.object.setLocalTranslation(this.destination[0].clone());
		}
		
		if(changeDirection){
			//set the emission of the object
			this.object.setEmissionDirection(this.emission[0].clone());
		}
		
	}

	/**
	 * Move the source in the x axis, increasing or decreasing position
	 */
	public void moveX() {
		// move in x
		if (object.getLocalTranslation().x > destination[moveTo].x) {
			// increase x
			object.getLocalTranslation().setX(
					object.getLocalTranslation().x - increase);
		} else if (object.getLocalTranslation().x < destination[moveTo].x) {
			// decrease x
			object.getLocalTranslation().setX(
					object.getLocalTranslation().x + increase);
		}
	}
	
	public void emissionX(){
		//change the emission in x
		if(object.getEmissionDirection().x > emission[emissionTo].x){
			//increase x
			object.getEmissionDirection().setX(object.getEmissionDirection().x - moveEmission);			
		}else if (object.getEmissionDirection().x < emission[emissionTo].x){
			//increase x
			object.getEmissionDirection().setX(object.getEmissionDirection().x + moveEmission);			
		}
	}
	
	public void emissionY(){
		//change the emission in x
		if(object.getEmissionDirection().y > emission[emissionTo].y){
			//increase y
			object.getEmissionDirection().setY(object.getEmissionDirection().y - moveEmission);			
		}else if (object.getEmissionDirection().y < emission[emissionTo].y){
			//decrease y
			object.getEmissionDirection().setY(object.getEmissionDirection().y + moveEmission);			
		}
	}
	
	public void emissionZ(){
		//change the emission in x
		if(object.getEmissionDirection().z > emission[emissionTo].z){
			//increase z
			object.getEmissionDirection().setZ(object.getEmissionDirection().z - moveEmission);			
		}else if (object.getEmissionDirection().z < emission[emissionTo].z){
			//decrease z
			object.getEmissionDirection().setZ(object.getEmissionDirection().z + moveEmission);			
		}
	}

	/**
	 * Move the source in the y axis, increasing or decreasing position
	 */
	public void moveY() {
		// move in y
		if (object.getLocalTranslation().y > destination[moveTo].y) {
			// increase y
			object.getLocalTranslation().setY(
					object.getLocalTranslation().y - increase);
		} else if (object.getLocalTranslation().y < destination[moveTo].y) {
			// decrease y
			object.getLocalTranslation().setY(
					object.getLocalTranslation().y + increase);
		}
	}
	
	/**
	 * Move the source in the z axis, increasing or decreasing position
	 */
	public void moveZ() {
		// move in z
		if (object.getLocalTranslation().z > destination[moveTo].z) {
			// increase z
			object.getLocalTranslation().setZ(
					object.getLocalTranslation().z - increase);
		} else if (object.getLocalTranslation().z < destination[moveTo].z) {
			// decrease z
			object.getLocalTranslation().setZ(
					object.getLocalTranslation().z + increase);
		}
	}
	
	public void moveObject(){
		if (object.getLocalTranslation().x != destination[moveTo].x)
			// move in x
			moveX();
		if (object.getLocalTranslation().y != destination[moveTo].y)
			// move in y
			moveY();
		if (object.getLocalTranslation().z != destination[moveTo].z)
			// move in z
			moveZ();
	}
	
	public void moveEmission() {
		if (object.getEmissionDirection().x != emission[emissionTo].x)
			// change x
			emissionX();
		if (object.getEmissionDirection().y != emission[emissionTo].y)
			// change y
			emissionY();
		if (object.getEmissionDirection().z != emission[emissionTo].z)
			// change z
			emissionZ();
	}
	
	public void changeDestination(){
		//convert to stop errors from precision
		x = (int)object.getLocalTranslation().x*100;
		y = (int)object.getLocalTranslation().y*100;
		z = (int)object.getLocalTranslation().z*100;
		
		// test, if all the position in x,y and z have matched the
		// destination, then go to another
		if((x == destination[moveTo].x*100) && (y == destination[moveTo].y*100)
				&& (z == destination[moveTo].z*100)){
			
			//increase moveTo to go to a new path
			moveTo++;
			//test if passed the last destination
			if(moveTo > destination.length - 1){	
				//test if must beggin again or not
				if(circle){						
					//put the destination to the beggining
					moveTo = 0;						
				}else{
					//don´t move anymore
					move = false;
				}
			}
		}
	}
	
	public void changeEmission(){
		// convert to stop errors from precision
		x = (int)object.getEmissionDirection().x*100;
		y = (int)object.getEmissionDirection().y*100;
		z = (int)object.getEmissionDirection().z*100;
		
		//test, if the direction has already been reached
		if((x == emission[emissionTo].x*100) && (y == emission[emissionTo].y*100)
				&& (z == emission[emissionTo].z*100)){
			
			//increase emissionTo to go to a new path
			emissionTo++;
			
			//test if passed the last destination
			if(emissionTo > emission.length - 1){			
				//test if must beggin again or not
				if(circleEmission){						
					//put the destination to the beggining
					emissionTo = 0;						
				}
			}
		}
		
	}

	@Override
	public void update(float time) {

		//reduce the life of the object
		life -= time;

		// if is supposed to move
		if (move) {			
			//move the emitter
			moveObject();
			//set a new destination to move
			changeDestination();
		}
		
		if (changeDirection){
			// move the emission
			moveEmission();
			// set a new direction to emit
			changeEmission();	
		}
		
		//test if the time has ended
		if (life <= 0) {
			//remove the controller
			object.removeController(this);
			//remove the particle
			object.removeFromParent();
			//test if there´s a cut-scene waiting for this particle to finish
			//execution
			if(RPGSystem.getRPGSystem().getCutscene().isWaitEffect()){
				//if it´s waiting then tell to proceed
				RPGSystem.getRPGSystem().getCutscene().setWaitEffect(false);
			}
			
			//tell the execution of the effect during the battle is over
			if(RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().isShowEffect()){
				//if its showing them tell that its over
				RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().setShowEffect(false);
			}
		}
	}

}
