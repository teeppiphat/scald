package framework.engine.artificialinteligence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.util.StrategyAction;

/**
 * 
 * Responsible for the actions of the enemys, more like
 * controlling them during the battle, because outside it
 * they are NPCs
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class AgentEnemy {
 
	
	/**
	 * Object used to have the strategies used by the enemies with a easy way to
	 * find
	 */
	HashMap<String, ArrayList<StrategyAction>> strategies;
	 
	/**
	 * Constructor of the class
	 */
	public AgentEnemy(){
		//initialize
		strategies = new HashMap<String, ArrayList<StrategyAction>>();
	}
	
	public ArrayList<StrategyAction> getStrategy(String name, String file){
		//ask for the file		
		ArrayList<StrategyAction> strategy = strategies.get(name);

		//test if the strategy was found
		if(strategy == null){
			//if it wasn´t found then load it
			strategy = loadStrategy(name, file);
			//and put it in the hash
			strategies.put(name, strategy);
		}
		//returns the strategy
		return strategy;
	}
	
	public ArrayList<StrategyAction> loadStrategy(String name,String file){
		
		//will load the strategy needed
		//the loader
		Element loader;
		Element temp;
		ArrayList<StrategyAction> array = new ArrayList<StrategyAction>();
		StrategyAction stra;
		
		// get the root in the xml file, the root of the AI
		String load = FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getFileElement(file, "EnemiesStrategies"); 

		loader = FactoryManager.getFactoryManager().getScriptManager().getReadScript().getElementXML(load, name);
				//getElementXML(file, "EnemiesStrategies");

		//get the strategy wanted
		//temp = loader.getChild(name);
		
		//load the strategy
		//get the children to a list
		List list = loader.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();
			//new strategy
			stra = new StrategyAction();
			
			//get the values used
			stra.setAction(temp.getChildText("Do"));
			stra.setInitial(Integer.parseInt(temp.getChildText("Initial")));
			stra.setEnd(Integer.parseInt(temp.getChildText("Final")));
			stra.setName(temp.getChildText("Name"));
			
			//put the action in the array
			array.add(stra);			
		}
		
		//return the strategy
		return array;
	}
	
	
	public void cleanHash(){
		strategies.clear();
	}
}
 
