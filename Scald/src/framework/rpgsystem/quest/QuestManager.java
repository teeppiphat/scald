package framework.rpgsystem.quest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import framework.FactoryManager;
import framework.RPGSystem;


/**
 * Manager of the quests that happen at the game, being
 * responsible for having all of them and managing them,
 * if they are secondary quests or not it doesn´t matter
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class QuestManager {
 	
	/**
	 * A hash that keeps all the quests using the identifier for each quest
	 */
	private HashMap<String, Quest> quests;
	
	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public QuestManager(){
		
		//initializing the object	
		quests = new HashMap<String, Quest>();
	}
	
	public void createInformation(Element inf, Quest quest){
		if(inf != null){			
			quest.setName(inf.getChildText("Name"));
			quest.setDescription(inf.getChildText("Description"));
			quest.setObjective(inf.getChildText("Objective"));
		}
	}
	
	public String[] loadOneDependencie(Element element){
		
		//get the children to a list
		List list = element.getChildren();

		//allocate the array according to the size				
		String[] names = new String[list.size()];
		
		// create the iterator to access the list
		Iterator i = list.iterator();
		int cont = 0;

		// temporary element used to get the elements
		Element temp;
		
		//get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			//put the object in the array
			names[cont] = temp.getText();
			//increase
			cont++;
		}
		
		//return the array
		return names;
	}
	
	public Object[] loadTwoDependencies(Element element){
		
		//get the children to a list
		List list = element.getChildren();

		//greater array
		Object[] objects = new Object[2];
		
		//allocate the array according to the size				
		String[] names = new String[list.size()];
		int[] values = new int[list.size()];
		
		// create the iterator to access the list
		Iterator i = list.iterator();
		int cont = 0;

		// temporary element used to get the elements
		Element temp;
		
		//get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			//put the object in the array of names
			names[cont] = temp.getChildText("Name");
			//put the object in the array of values
			values[cont] = Integer.parseInt(temp.getChildText("Quantity"));
			//increase
			cont++;
		}
		
		//set the arrays in their places
		objects[0] = names;
		objects[1] = values;
		
		//return
		return objects;
	}
	
	public Object[] loadThreeDependencies(Element element){
		
		// get the children to a list
		List list = element.getChildren();

		//greater array
		Object[] objects = new Object[3];
		
		//allocate the array according to the size				
		String[] names = new String[list.size()];
		String[] types = new String[list.size()];
		int[] values = new int[list.size()];
		
		// create the iterator to access the list
		Iterator i = list.iterator();
		int cont = 0;

		// temporary element used to get the elements
		Element temp;
		
		//get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			//put the object in the array of names
			names[cont] = temp.getChildText("Name");
			//put the object in the array of types
			types[cont] = temp.getChildText("Type");			
			//put the object in the array of values
			values[cont] = Integer.parseInt(temp.getChildText("Value"));
			//increase
			cont++;
		}
		
		//set the arrays in their places
		objects[0] = names;
		objects[1] = types;
		objects[2] = values;
		
		//return
		return objects;
	}
		
	public void createDependencies(Element dep, Quest quest){
		if(dep != null){
			
			//if there are quests
			if(dep.getChild("Quest") != null){
				//get the quest dependencies
				quest.setQuestDependencies(loadOneDependencie(dep.getChild("Quest")));				
			}
			
			//if there are itens needed
			if(dep.getChild("Itens") != null){
				//get the Itens
				Object[] objects;
				objects = loadTwoDependencies(dep.getChild("Itens"));				
				//set their places
				quest.setItemDependencies((String[]) objects[0]);
				quest.setItemDependenciesQuantity((int[]) objects[1]);								
			}		
			
			//if exists the need for levels
			if(dep.getChild("Level") != null){
				//get the Level	
				quest.setLevelDependencie(Integer.parseInt(dep.getChildText("Level")));
			}		
			
		}
	}
	
	public void createObjective(Element obj, Quest quest){
		if(obj != null){
			
			//if needs to get money
			if(obj.getChild("Money") != null){
				quest.setMoneyNeeded(Integer.parseInt(obj.getChildText("Money")));
			}
			
			//if needs itens
			if(obj.getChild("Itens") != null){
				// get the Itens
				Object[] objects;
				objects = loadTwoDependencies(obj.getChild("Itens"));				
				//set their places
				quest.setGetItem((String[]) objects[0]);
				quest.setNumberItemGot((int[]) objects[1]);	
			}
			
			//if needs monsters kills
			if(obj.getChild("Monsters") != null){
				// get the Itens
				Object[] objects;
				objects = loadTwoDependencies(obj.getChild("Monsters"));				
				//set their places
				quest.setMonsterKill((String[]) objects[0]);
				quest.setMonsterKilled((int[]) objects[1]);	
			}
			
			//if needs talk to
			if(obj.getChild("Talk") != null){			
				//get the order
				quest.setOrder(Boolean.parseBoolean(obj.getChildText("Order")));				
				//get the NPCs
				quest.setNPCTalk(loadOneDependencie(obj.getChild("Talk").getChild("NPC")));
				//create the controller for this
				quest.createNPCTalkControl();
			}
			
		}		
	}
	
	public void createReward(Element rew, Quest quest){
		if(rew != null){
			
			//if there´s money to be rewarded
			if(rew.getChild("Money") != null){
				quest.setMoneyRewarded(Integer.parseInt(rew.getChildText("Money")));
			}else{
				//if there´s no money reward
				quest.setMoneyRewarded(-1);
			}
			
			//if there´s one or more itens to be rewarded
			if(rew.getChild("Itens") != null){
				//get the Itens
				Object[] objects;
				objects = loadTwoDependencies(rew.getChild("Itens"));				
				//set their places
				quest.setRewardItem((String[]) objects[0]);
				quest.setRewardedItem((int[]) objects[1]);	
			}
			
			//if there´s something of the character that increases
			if(rew.getChild("Features") != null){
				//get the Itens
				Object[] objects;
				objects = loadThreeDependencies(rew.getChild("Features"));				
				//set their places
				quest.setFeatureName((String[]) objects[0]);
				quest.setFeatureType((String[]) objects[1]);
				quest.setFeatureValue((int[]) objects[2]);
			}
		}
	}
	
	/**
	 * Method used to create one quest by receiving its information from the XML
	 * 
	 * @param child an object of the class Element
	 */
	public void createQuest(Element child){
		
		// put the new quest in the hash, need to test if already has a quest
		// with this identifier, if already exists then don´t create the last
		
		//create the quest
		Quest quest = quests.get(child.getName());
		
		//test if exists, if doesn´t exists then create it
		if(quest == null){
			//pass the element of the quest for it to be created
			
			//get the data for the quest
			quest = new Quest();
			
			//get the information
			createInformation(child.getChild("Information"), quest);
			
			//get the dependencies
			createDependencies(child.getChild("Dependencies"), quest);
			
			//get the objective
			createObjective(child.getChild("Objective"), quest);
			
			//get the rewards
			createReward(child.getChild("Reward"), quest);	
			
			//put the quest in the hash
			quests.put(quest.getName(), quest);
		}				
	}	 
	
	/**
	 * Method used to load all the quests
	 * 
	 * @param file the path to the file that has all the quests
	 */
	public void loadQuest(String file){
		
		//load the file
		Element loader = FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getRootElement(file);
		
		//create all the quests

		//get the children to a list
		List list = loader.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;
		
		//get the elements from the list
		while (i.hasNext()) {
			// get the first object
			temp = (Element) i.next();
			//create a quest
			createQuest(temp);			
		}
		
	}
	
	/**
	 * Method used to remove what was asked at a quest
	 * 
	 * @param quest an object of the class Quest
	 */
	public void removeQuestNeeds(Quest quest){

		//remove the money from the group, if has money to remove
		if(quest.getMoneyNeeded() != -1){
			//remove it
			RPGSystem.getRPGSystem().getPlayerGroup().setMoneyValue(-quest.getMoneyNeeded());
		}
		
		//if has itens to remove
		if(quest.getGetItem() != null){
			
			//the quantity to remove
			int sum;
							
			//must remove all the itens needed
			for(int i = 0; i < quest.getGetItem().length; i++){
				
				//receive the quantity that needs to remove
				sum = quest.getNumberItemGot()[i];
									
				//remove the itens, first try to remove from the bag and then from the characters
				//if exists a bag for the group
				if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isGroupBag()){					
					//there is a bag for the group
					
					//test the quantity
					if (RPGSystem.getRPGSystem().getPlayerGroup().getBag()
							.getItemEntry(quest.getGetItem()[i]) != null) {
						
						//remove the quantity
						if(RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItemEntry(quest.getGetItem()[i]).getQtd() >= sum){
							//remove the quantity needed
							RPGSystem.getRPGSystem().getPlayerGroup().getBag().removeItem(quest.getGetItem()[i], sum);
							//removed all the quantity needed
							sum = 0;								
						}else{
							//remove from the sum the quantity
							sum = sum
									- RPGSystem.getRPGSystem()
											.getPlayerGroup().getBag()
											.getItemEntry(
													quest.getGetItem()[i])
											.getQtd();								
							//remove the quantity that has
							RPGSystem
									.getRPGSystem()
									.getPlayerGroup()
									.getBag()
									.removeItem(
											quest.getGetItem()[i],
											RPGSystem
													.getRPGSystem()
													.getPlayerGroup()
													.getBag()
													.getItemEntry(
															quest
																	.getGetItem()[i])
													.getQtd());								
						}												
					}						
				}
									
				//if there´s the possibility of finding in the characters belongings				
				if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isCharacterBag() && sum > 0){					
					//there are characters bags
											
					//must search in all of the characters bags and sum up the amount					
					for(int p = 0; p < RPGSystem.getRPGSystem().getPlayerGroup().getCharacter().length; p++){
						
						//must test all the characters 
						if (RPGSystem.getRPGSystem().getPlayerGroup()
								.getCharacter(p).getBag().getItemEntry(
										quest.getGetItem()[i]) != null) {

							//remove the quantity according to how much can remove
							if(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(p).getBag().getItemEntry(quest.getGetItem()[i]).getQtd() >= sum){
								//remove the quantity needed
								RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(p).getBag().removeItem(quest.getGetItem()[i], sum);
								//removed all the quantity needed
								sum = 0;								
							}else{
								//remove from the sum the quantity
								sum = sum
								- RPGSystem
								.getRPGSystem()
								.getPlayerGroup()
								.getCharacter(p)
								.getBag()
								.getItemEntry(
										quest.getGetItem()[i])
										.getQtd();								
								//remove the quantity that has
								RPGSystem
								.getRPGSystem()
								.getPlayerGroup()
								.getCharacter(p)
								.getBag()
								.removeItem(
										quest.getGetItem()[i],
										RPGSystem
										.getRPGSystem()
										.getPlayerGroup()
										.getCharacter(p)
										.getBag()
										.getItemEntry(
												quest
												.getGetItem()[i])
												.getQtd());								
							}							
						}
					}
				}					
			}
		}				
	}
	
	/**
	 * Method used to check if a quest was completed
	 * 
	 * @param quest the quest in question
	 */
	public void checkQuest(Quest quest){
				
		//to test if the quest was completed
		boolean complete = true;
		
		//test if the quest exists and is active		
		if(quest != null && quest.getState() == 'A'){		
						
			// if has monster to kill
			if(quest.getMonsterKill() != null){
				//if the quantity is equal to zero then don´t need to kill anymore
				
				//temporary
				int i = 0;
				
				//loop to know if every monster needed to kill was killed
				while(complete == true && i < quest.getMonsterKilled().length){
					//test the quantity
					if(quest.getMonsterKilled()[i] == 0){
						//was killed goes to next
						i++;
					}else{
						//there´s some left
						complete = false;
					}									
				}				
			}
			
			//if an item has been found
			if(quest.getGetItem() != null){
				//test if the quantity that the player has in the bag is the quantity needed
				
				//must find where to search, characters and/or group
				
				// one search for the characters belongings and another for the group
								
				//temporary
				int i = 0;
				
				//used to put together the quantity between characters and the bag
				int sum = 0;
				
				//loop to test the quantity of all itens needed
				while(complete == true && i < quest.getGetItem().length){
					//search the player or the group bag, if player must search all bags
					//System.out.println(quest.getGetItem()[i]);
					//test the quantity
					sum = 0;
					
					// if there´s the possibility of finding in the characters belongings				
					if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isCharacterBag()){					
						//there are characters bags
												
						//must search in all of the characters bags and sum up the amount					
						for(int p = 0; p < RPGSystem.getRPGSystem().getPlayerGroup().getCharacter().length; p++){
							
							//must test all the characters 
							if (RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(p).getBag().getItemEntry(
											quest.getGetItem()[i]) != null) {

								// has the item, then put together to make the test
								sum = sum
								+ RPGSystem.getRPGSystem()
								.getPlayerGroup().getCharacter(
										p).getBag()
										.getItemEntry(
												quest.getGetItem()[i])
												.getQtd();
							}
						}
					}	
					
					// if exists a bag for the group
					if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isGroupBag()){					
						//there is a bag for the group
						
						//test the quantity
						if (RPGSystem.getRPGSystem().getPlayerGroup().getBag()
								.getItemEntry(quest.getGetItem()[i]) != null) {
							
							//put together the item
							sum = sum +
							RPGSystem.getRPGSystem().getPlayerGroup()
							.getBag().getItemEntry(
									quest.getGetItem()[i]).getQtd();							
						}						
					}
					
					//test the quantity
					if(sum <= quest.getNumberItemGot()[i]){
						complete = false;
						
					}else{
						i++;
					}
				}				
			}
			
			//if has money to get
			if(quest.getMoneyNeeded() != -1){
				//the money is with the player group
				
				//test the quantity needed
				if(RPGSystem.getRPGSystem().getPlayerGroup().getMoneyValue() < quest.getMoneyNeeded()){
					complete = false;					
				}
			}
			
			//if talked with everyone needed to
			if(quest.getNPCTalk() != null){
				
				//test if all the NPC needed to talk were found
				
				//temporary
				int i = 0;
				
				//loop to test if they were all found
				while(complete == true && i < quest.getNPCTalk().length){
					//test
					if(quest.getNPCTalked()[i]){
						//was talked
						i++;
					}else{
						//one is missing
						complete = false;
					}					
				}				
			}			
		}else{
			complete = false;
		}
		
		//teste if the quest has been completed
		if(quest != null && complete){
			//was completed
			quest.setState('C');		
		}
	}
	
	/**
	 * Method used to count a monster that was killed
	 * 
	 * @param monster the name of the monster
	 */
	public void monsterKilled(String monster){
		//temporary object
		Quest temp;
		
		//must test in all the active quests if the monster killed has to be counted
		for(int i = 0; i <quests.size(); i++){
			//get the quest
			temp = quests.get(i);
			
			//test if is active and has monster to kill
			if(temp.getState() == 'A' && temp.getMonsterKill() != null){
				//test if has the monster to decrease the needed amount
				for(int l = 0; l < temp.getMonsterKill().length; i++){
					//test if has the monster
					if(temp.getMonsterKill()[i].equals(monster)){						
						//decrease only if can
						if(temp.getMonsterKilled()[i] > 0){
							temp.getMonsterKilled()[i]--;
						}
					}
				}
			}
			
			//test if the quest was completed
			checkQuest(temp);
		}	
		
	}
	
	/**
	 * Method used to set one NPC as talked to, giving the name of the NPC and the quest identifier
	 * 
	 * @param name the name of the NPC
	 * @param questName the identifier of the quest
	 */
	public void NPCTalkedTo(String name, String questName){		
		//temporary object
		Quest quest;
		
		//get the quest
		quest = quests.get(questName);
		
		//test the quest
		if(quest != null && quest.getState() =='A'){
			
			//temporary object
			boolean found = false;
			int i = 0;
			
			//loop to search for the NPC wanted
			while(i < quest.getNPCTalk().length && found == false){
				//make the test
				if(quest.getNPCTalk()[i].equals(name)){
					//the NPC that was looking for
					quest.getNPCTalked()[i] = true;
				}else{
					//keep looking for
					i++;
				}
			}			
		}		
	}
		
	/**
	 * Method used to test if the quest was completed, by receiving the identifier of the quest
	 * 
	 * @param name the identifier of the quest
	 * @return true or false according to if the quest was completed or not
	 */
	public boolean completeQuest(String name){
		//temporary object
		Quest quest;
		
		//get the quest in question
		quest = quests.get(name);			
		//test the quest, if exist and if its complete
		if(quest != null && quest.getState() == 'C'){			
			//validate the quest by removing what needs to be removed
			removeQuestNeeds(quest);			
			//receive the reward
			receiveReward(quest);
			//set the quest as finished
			quest.setState('F');			
			//the quest was completed
			return true;
		}else{			
			//the quest wasn´t completed
			return false;
		}
		
		//the quest wasn´t completed
		//return false;
	}
	
	/**
	 * Method used to give the player group the reward for the quest that was completed
	 * 
	 * @param quest an object of the class Quest
	 */
	public void receiveReward(Quest quest){
		
		//test if the reward has money
		if(quest.getMoneyRewarded() != -1){
			RPGSystem.getRPGSystem().getPlayerGroup().setMoneyValue(quest.getMoneyRewarded());
		}
		
		//test if has one or more itens to receive
		if(quest.getRewardItem() != null){			
			//will pass throught all itens and say to give them to the player
			for(int i = 0; i < quest.getRewardedItem().length; i++){
				//say to put the item in the player inventory
				RPGSystem.getRPGSystem().getPlayerGroup().addItem(quest.getRewardItem()[i], quest.getRewardedItem()[i]);				
			}			
		}
		
		//test if has one or more features to increase
		if(quest.getFeatureName() != null){			
			//will pass throught all the features and increase all the groups features
			for(int i = 0; i < quest.getFeatureName().length; i++){
				//needs to increase for the entire group
				RPGSystem.getRPGSystem().getPlayerGroup().increaseGroupFeature(
						quest.getFeatureName()[i], quest.getFeatureType()[i],
						quest.getFeatureValue()[i]);
			}
		}
	}
	
	/**
	 * Ask to validate a quest by receiving its name
	 * 
	 * @param name the identifier of the quest
	 */
	public void validateQuest(String name){
		//temporary object
		Quest quest;
		
		//get the quest
		quest = quests.get(name);		
		
		//check the quest
		checkQuest(quest);		
	}
	
	/**
	 * Method used to return the state of a quest that is ask
	 * 
	 * @param name the identifier of the quest
	 * @return the state of the quest, represented by a char
	 */
	public char getQuestState(String name){
		return quests.get(name).getState();
	}
	
	/**
	 * Test if the quest can be checked or not
	 * 
	 * @param quest an object of the class Quest
	 */
	public void testQuest(Quest quest){
		//test if the quest is active or not
		if(quest != null && quest.getState() == 'A'){
			//check the quest
			checkQuest(quest);
		}		
	}
			
	/**
	 * Method used to validate all the quests that are active
	 */
	public void validateQuests(){				
		//check all the quests if they have been completed
		for(int i = 0; i < quests.size(); i++){
			//test the quest if can be checked
			testQuest(quests.get(i));
		}		
	}
	
	public boolean hasRequisites(Quest quest){
				
		//test if has the necessary level
		if(quest.getLevelDependencie() != -1){
			if(!RPGSystem.getRPGSystem().getPlayerGroup().hasLevelNeeded(quest.getLevelDependencie())){
				return false;
			}
		}
				
		//test if has the necessary quest needed
		if(quest.getQuestDependencies() != null){
			for(int i = 0; i < quest.getQuestDependencies().length; i++){
				//test if all quests are completed
				if (quests.get(quest.getQuestDependencies()[i]) != null
						&& quests.get(quest.getQuestDependencies()[i])
								.getState() != 'F') {
					return false;
				}				
			}		
		}
			
		//test if has the necessary item needed
		if(quest.getItemDependencies() != null){
			//temporary objects
			int i = 0;
			int sum = 0;			
			
			while(i < quest.getItemDependencies().length){
				System.out.println(quest.getItemDependencies()[i]);
				//if there´s the possibility of finding in the characters belongings				
				if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isCharacterBag()){					
					//there are characters bags
											
					//must search in all of the characters bags and sum up the amount					
					for(int p = 0; p < RPGSystem.getRPGSystem().getPlayerGroup().getCharacter().length; p++){
						
						//must test all the characters 
						if (RPGSystem.getRPGSystem().getPlayerGroup()
								.getCharacter(p).getBag().getItemEntry(
										quest.getItemDependencies()[i]) != null) {

							
							// has the item, then put together to make the test
							sum = sum
							+ RPGSystem.getRPGSystem()
							.getPlayerGroup().getCharacter(
									p).getBag()
									.getItemEntry(
											quest.getItemDependencies()[i])
											.getQtd();
						}
					}
				}	
				
				// if exists a bag for the group
				if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isGroupBag()){					
					//there is a bag for the group
					
					//test the quantity
					if (RPGSystem.getRPGSystem().getPlayerGroup().getBag()
							.getItemEntry(quest.getItemDependencies()[i]) != null) {
						System.out.println("Found the item");
						//put together the item
						sum = sum +
						RPGSystem.getRPGSystem().getPlayerGroup()
						.getBag().getItemEntry(
								quest.getItemDependencies()[i]).getQtd();							
					}						
				}
				
				//test the quantity
				if(sum < quest.getItemDependenciesQuantity()[i]){
					return false;
				}else{
					i++;
				}
			}		
		}	
		
		//all the requisites are OK
		return true;
	}
	
	public boolean beginQuest(String name){
		//temporary object
		Quest quest;
		
		//gets the quest
		quest = quests.get(name);
		
	
		//test if exists and is inactive
		if(quest != null && quest.getState() == 'I'){			
			//test the dependencies, if can apply then begin the quest
			if(hasRequisites(quest)){
				//begin the quest
				quest.setState('A');
				//the quest is active
				return true;
			}
		}
		
		//not able to begin the quest
		return false;
	}
}
 
/*
<!--
<!-- A quest -->
<Identifier>

	<!-- Written information to show the player, if needed -->
	<Information>
		<Name></Name>
		<Description></Description>
		<Objective></Objective>
	</Information>
	
	<!-- What is needed to begin the quest -->
	<Dependencies>
		<!-- As many as wanted -->
		<Quest>
			<!-- The name -->
			<Name></Name>
		</Quest>			
		
		<!-- Itens -->
		<Itens>
			<!-- An Item -->
			<Item>
				<!-- The name-->
				<Name></Name>
				<!-- The quantity-->
				<Quantity></Quantity>
			</Item>			
		</Itens>
		
		<!-- Only one, the last one will count -->
		<Level></Level>
	</Dependencies>	

	<!-- All the objectives of the quest, has only the ones used -->
	<Objective>
	
		<!-- Money must get -->
		<Money></Money>
		
		<!-- Itens -->
		<Itens>
			<!-- Item must get, as many as needed -->
			<Item>
				<!-- Name of the item -->
				<Name></Name>
				<!-- The quantity needed -->
				<Quantity></Quantity>
			</Item>
		</Itens>
	
		<!-- Monsters -->
		<Monsters>
			<!-- Kill monster -->
			<Monster>
				<!-- Name of the monster -->
				<Name></Name>
				<!-- The quantity needed -->
				<Quantity></Quantity>			
			</Monster>
		</Monsters>
		
		<!-- Talk to a NPC -->
		<Talk>				
			<!-- Has order? true or false -->
			<Order></Order>				
			<!-- all the NPCs, as many as needed -->
			<NPC>
				<!-- The name -->
				<Name></Name>				
			</NPC>
		</Talk>
		
	</Objective>
	
	<!-- All the rewards possible -->
	<Reward>
	
		<!-- The money gained -->
		<Money></Money>
	
		<!-- Itens -->
		<Itens>
			<!-- Item must get, as many as needed -->
			<Item>
				<!-- Name of the item -->
				<Name></Name>
				<!-- The quantity needed -->
				<Quantity></Quantity>
			</Item>
		</Itens>
		
		<!-- All the status or something like that -->
		<Features>			
			<!-- If a status or something like that increases -->
			<Feature>
				<!-- The name -->
				<Name></Name>
				<!-- The type of the feature -->
				<Type></Type>
				<!-- The value that will increase, if the feature can increase -->
				<Value></Value>				
			</Feature>			
		</Features>
	
	</Reward>
		
</Identifier>
-->
*/
