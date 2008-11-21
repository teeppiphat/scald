package framework.engine.physic;

import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.util.SyntheticButton;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.contact.ContactInfo;

import framework.FactoryManager;

/**
 * Take care of the physic of the game, it can be
 * said that it takes care of the colisions that 
 * happens at the game
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Physic {

	private PhysicsSpace physicsSpace;

	private float physicsSpeed = 0.5f;

	//protected boolean showPhysics;

	/**
	 * Responsible for the particles
	 */
	private ParticleSystem particleSystem;

	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public Physic() {

		//initializing the object
		particleSystem = new ParticleSystem();
	}
	
	public ParticleSystem getParticleSystem() {
		return particleSystem;
	}

	/**
	 * @return speed set by {@link #setPhysicsSpeed(float)}
	 */
	public float getPhysicsSpeed() {
		return physicsSpeed;
	}

	/**
	 * The multiplier for the physics time. Default is 1, which means normal
	 * speed. 0 means no physics processing.
	 * 
	 * @param physicsSpeed
	 *            new speed
	 */
	public void setPhysicsSpeed(float physicsSpeed) {
		this.physicsSpeed = physicsSpeed;
	}

	/**
	 * @return the physics space for this simple game
	 */
	public PhysicsSpace getPhysicsSpace() {
		return physicsSpace;
	}

	/**
	 * @param physicsSpace The physics space for this simple game
	 */
	protected void setPhysicsSpace(PhysicsSpace physicsSpace) {
		if (physicsSpace != this.physicsSpace) {
			if (this.physicsSpace != null)
				this.physicsSpace.delete();
			this.physicsSpace = physicsSpace;
		}
	}

	public void createPhysic() {
		setPhysicsSpace(PhysicsSpace.create());
	}
	
	public void createCollisionOnFloor(){
		
		 // collision events analoguous to Lesson8
        SyntheticButton CollisionEventHandler = physicsSpace.getCollisionEventHandler();
        
        FactoryManager.getFactoryManager().getInputManager().getInput().addAction( new InputAction()
        {
            public void performAction( InputActionEvent evt )
            {
                ContactInfo contactInfo = (ContactInfo) evt.getTriggerData();
                
                if(contactInfo.getNode1().getName().equals("Terrain")){
                
                	//get the physic node
                	DynamicPhysicsNode node = (DynamicPhysicsNode)contactInfo.getNode2();
                	
                	//clear the forces upon it
                	node.clearDynamics();

                	//node.rest();
                	//if(!node.isResting())
                	//	node.rest();
                	
                }else if(contactInfo.getNode2().getName().equals("Terrain")){
                	
                	//get the physic node
                	DynamicPhysicsNode node = (DynamicPhysicsNode)contactInfo.getNode1();
                	
                	//clear the forces upon it
                	node.clearDynamics();

                	//node.rest();
                	//if(!node.isResting())
                		//node.rest();
                	
                }else{
                    //test for different objects
                	
                	// get the physic node
                	DynamicPhysicsNode node = (DynamicPhysicsNode)contactInfo.getNode1();
                	//get the physic node
                	DynamicPhysicsNode node2 = (DynamicPhysicsNode)contactInfo.getNode2();
                	
                	//change position, put it back, to avoide collision
                	//node.getLocalTranslation().setX(node.getLocalTranslation().x + 0.01f);
                	//node2.getLocalTranslation().setX(node2.getLocalTranslation().x - 0.01f);
                	
                	//clear the forces upon it, not enough
                	node.clearDynamics();                	
                	node2.clearDynamics();
                	
                	//System.out.println(node.getName());
                	//System.out.println(node2.getName());
                	//just stop both from moving
                	//node.rest();
                	//node2.rest();
                	
                	//node.setLocalTranslation(node.getLocalTranslation());
                	//node2.setLocalTranslation(node2.getLocalTranslation());
                	
                	//if knows where is walking, then make both stand back a bit
                	//enough to stop the collision
                	
                }
                

               
            }
        }, CollisionEventHandler.getDeviceName(), CollisionEventHandler.getIndex(),
                InputHandler.AXIS_NONE, false ); //TODO: this should be true when physics supports release events
        
	}
	
	public void updatePhysic(float tpf) {
		//for things to don´t happen to fast
		if ( tpf > 0.2 || Float.isNaN( tpf ) ) {            
            tpf = 0.2f;
        }
		//update the physic
		getPhysicsSpace().update(tpf * physicsSpeed);
	}

}
