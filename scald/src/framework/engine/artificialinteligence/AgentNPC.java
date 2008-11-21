package framework.engine.artificialinteligence;

import framework.FactoryManager;

/**
 * Class responsible for the actions of the NPCs(Non-Playable Characters),
 * they have actions like moving, conversation, and these kind of things and
 * they have no action of the type of combat, in combat they are treated as
 * characters.
 * 
 * @author Admin
 *
 */
public class AgentNPC {
 	 
	/**
	 * Constructor of the class
	 */
	public AgentNPC(){
		
	}
	
	public Object[] callTalkScript(String path, String file, int part){
		
		//call the method to load the script
		FactoryManager.getFactoryManager().getScriptManager().getReadScript().loadScript(path, file);
		
		//create the temporary array of objects		
		Object[] send = new Object[1];
		send[0] = part;
		
		//call the method to execute the script
		return FactoryManager.getFactoryManager().getScriptManager().getReadScript().executingScript("talk", send);		
	}
}
 
