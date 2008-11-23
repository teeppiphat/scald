package framework.rpgsystem.evolution;

import java.io.Serializable;

import org.jdom.Element;

import framework.FactoryManager;
import framework.rpgsystem.item.Bag;

/**
 * Has all the characters that can be used by the
 * player, telling the formation, who battles, etc
 * @author Diego Antônio Tronquini Costi
 *
 */
public class PlayerGroup implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Array that holds all the characters
	 */
	private Character[] character;
	
	/**
	 * Responsible for saying how many characters are in the group, for control
	 */
	private int quantity;
	
	/**
	 * Holds the itens of the group
	 */
	private Bag bag;
	 
	/**
	 * The magic used by the group, in this case magic that is used by more than
	 * one person
	 */
	private Magic[] magic;
	
	/**
	 * Responsible for holding the indication to the file that has where all the
	 * characters are
	 */
	private String where;
		
	/**
	 * Responsible for holding the amount that has in the moment
	 */
	private Feature money;

	/**
	 * Constructor of the class
	 */
	public PlayerGroup(){
		bag = null;
		character = null;
		magic = null;
		quantity = -1;
		money = null;
	}
	
	/**
	 * Method used to get the place where the file that has the indication of
	 * all characters is
	 * 
	 * @return an Element
	 */
	public Element getPlaceCharacters(){
		return FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getElementXML(where, "Characters");
	}
	
	/**
	 * Used to get an element that is wanted in a File
	 * @param whe the file, a String
	 * @param what the element wanted, a String
	 * @return an Element
	 */
	public Element getWanted(String whe, String what){
		return FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getElementXML(whe, what);
	}
	
	/**
	 * Method used to get where the file that has all characters is
	 * 
	 * @return a String
	 */
	public String getFile(Element temp){
		//Element temp = getPlace(where,"Characters");
		return temp.getChildText("Path") + temp.getChildText("File");
	}
	
	/**
	 * Method used to get the file that has where all the characters are
	 * 
	 * @param place
	 *            where the file is
	 * @return an Element
	 */
	public Element getCharacters(String place) {
		return FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getRootElement(place);		
	}
	
	/**
	 * Method used to get the a xml file, returns its root
	 * @param place the file, a String
	 * @return an Element
	 */
	public Element loadFile(String place){
		return FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getRootElement(place);	
	}
	
	/**
	 * Method used to get a character in the file that has all of them, the name
	 * is the identifier and the all is the Element that is the file of all
	 * characters, returning the Element that represents the character
	 * 
	 * @param name
	 *            a String
	 * @param all
	 *            an Element
	 * @return an Element
	 */
	public Element getChar(String name, Element all){
		return all.getChild(name);		
	}
	
	/**
	 * Method used to get one character from the file that has
	 * all characters
	 * @param name the identifier, a String
	 * @return an Element
	 */
	public Element getOneCharacter(String name){
		
		//get the place where the characters are indicated
		//Element all = getWanted(where, "Characters");
		
		//get the place where the all the characters are indicated
		//String whereCharacters = getFile(all);
		
		//get the character wanted
		//Element wanted = getWanted(getFile(getWanted(where, "Characters")), name);
		
		//get the place where the character is
		//String file = getFile(getWanted(getFile(getWanted(where, "Characters")), name));
		
		//load the character
		
		//return the character wanted		
		return loadFile(getFile(getWanted(getFile(getWanted(where, "Characters")), name)));
	}
	
	/**
	 * Method used to load the money specifications
	 */
	public void createMoney(){
		//load the values for the money
		
		//get the name of the money
		String name = FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getMoney();
				
		//create the money as a Feature
		money = new Feature();
		
		//load the Feature that has its properties
		
		//get the item configuration on the file
		Element it = FactoryManager
		.getFactoryManager()
		.getScriptManager()
		.getReadScript()
		.getRootElement(
				FactoryManager
						.getFactoryManager()
						.getScriptManager()
						.getReadScript()
						.getFileElement(
								where,
								"Itens"));

		//get the item wanted
		Element element = it.getChild(name);
		
		//load the Feature
		money.load(element);	
		
	}
	
	/**
	 * Method used to create the bag of the group
	 */
	public void createGroupBag(){	
		
		//get the group bag
		Element b = getWanted(getFile(getWanted(where,"Characters")),"GroupBag");
		
		//create the bag
		if(b != null){
			//create the bag
			bag = new Bag();
			//set the size
			bag.setMaxEntrys(Integer.parseInt(b.getChildText("MaximumSlots")));
		}		
	}
		
	/**
	 * Method used to create a character and put in the group, by giving the
	 * identifier to that character
	 * 
	 * @param name
	 *            a String
	 */
	public void createCharacter(String name){
		if(character != null){
			//will create in a new position
			if(quantity < character.length){
				//will update the quantity
				quantity++;
				//will create a space in the array
				character[quantity] = new Character();
				//ark the to create itself, passing the character to create
				character[quantity].createCharacter(getOneCharacter(name));
				//needs to set the initial valus of the status of the character
				character[quantity].setInitialValues();				
			}
		}
	}
	
	public void printGroup(){
		for(int i = 0; i < 1; i++){
			character[i].print();
		}
	}
	
	/**
	 * Method used to create the group of the playear
	 * 
	 * @param name
	 *            the first character´s identifier, a String
	 * @param size
	 *            the maximum size of the group, an integer
	 */
	public void createPlayerGroup(String name, int size){
		//create the array
		allocateCharacters(size);
		//ask to create the first character
		createCharacter(name);
	}
	
	/**
	 * Gives experience to the group
	 * 
	 * @param xpCharacter
	 *            for the character, an integer
	 * @param xpClass
	 *            for the class, an integer
	 * @param xpItem
	 *            for the item, an integer
	 * @param xpFeature
	 *            for the feature, an integer
	 */
	public void groupExperience(int xpCharacter,int xpClass,int xpItem,int xpFeature){		
		//gives experience for each character in the group
		for(int i = 0; i <= quantity; i++){
			//gives the experience to the character
			character[i].gainsExperience(xpCharacter, xpClass, xpItem, xpFeature);
		}
	}
	
	/**
	 * Method used to find the position of a character in the group by giving its identifier 
	 * @param identifier a String
	 * @return an integer that is the character position in the array
	 */
	public int findCharacterPosition(String identifier){			
		//loop
		for(int i = 0; i <= quantity; i++){
			if(character[i].getIdentifier().equals(identifier)){
				return i;
			}
		}		
		//not found
		return -1;
	}
	
	public int findCharacterNamePosition(String name){
		//loop
		for(int i = 0; i <= quantity; i++){
			if(character[i].getName().equals(name)){
				return i;
			}
		}		
		//not found
		return -1;
	}
	
	public void removeItem(String itemName, int quantity){
		//where will be added
		
		// first try at the group bag, if can´t then try the character bag,
		// asking for the rules
		
		//always put in the group bag, only don´t put there if doesn´t exists
		
		//not from a bag of a character
		if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isGroupBag()){
			//remove from the group bag
			bag.removeItem(itemName,quantity);						
		}else if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isCharacterBag()){
			//remove from the bag of a character
		}
	}
		
	public void addItem(String itemName,int quantity){
		//where will be added
		
		// first try at the group bag, if can´t then try the character bag,
		// asking for the rules
		
		//always put in the group bag, only don´t put there if doesn´t exists
		
		if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isGroupBag()){
			//add in the group bag
			bag.addItem(itemName,quantity);
			
		}else if(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().isCharacterBag()){
			//add in the character bag
			boolean added = false;
			boolean out = false;
			int i = 0;
			
			//loop to put the item in a character bag
			while(!out){				
				if(character[i].getBag().addItem(itemName, quantity)){
					out = true;
					added = true;
				}else{
					i++;
					if(i > quantity){
						out = true;
					}
				}
			}
			
			//tells what happened
			if(added){
				System.out.println("Found " + quantity +" " + itemName);
			}
		}		
	}
	
	public void refreashCharactersValues(){
		//for all the characters
		for(int i = 0; i < getQuantity() + 1; i++){
			getCharacter(i).refreshStatus();
		}
	}
	
	public boolean hasLevelNeeded(int value){
		//test all characters
		for(int i = 0; i < quantity + 1; i++){
			if(getCharacter(i).getLevel() < value){
				return false;
			}
		}
		
		//if all passed the test
		return true;
	}
		
	public void increaseGroupFeature(String name, String type, int value){
		for(int i = 0; i < quantity + 1; i++){
			//will increase the value of the feature
			if(type.equals("Status")){
				if(getCharacter(i).getStatus(name) != null){
					getCharacter(i).getStatus(name).getSkill().getFeature().increaseValue(value);
				}else if (getCharacter(i).getCharacterClass().getFeatureNetwork().getStatus(name) != null){
					getCharacter(i).getCharacterClass().getFeatureNetwork().getStatus(name).getSkill().getFeature().increaseValue(value);
				}				
			}else if(type.equals("Skill")){				
				if(getCharacter(i).getSkill(name) != null){
					getCharacter(i).getSkill(name).getSkill().getFeature().increaseValue(value);
				}else if (getCharacter(i).getCharacterClass().getFeatureNetwork().getSkill(name) != null){
					getCharacter(i).getCharacterClass().getFeatureNetwork().getSkill(name).getSkill().getFeature().increaseValue(value);
				}	
			}		
		}		
	}
	
	public void gainMoney(int gain){
		money.getSkill().getFeature().changeValue(gain);		
	}
	
	public void printItems(){
		bag.printItems();
	}
	
	public Bag getBag() {
		return bag;
	}
	
	public void allocateCharacters(int size){
		character = new Character[size];		
	}
	
	public Character[] getCharacter() {
		return character;
	}

	public Magic[] getMagic() {
		return magic;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
	
	public Character getCharacter(int pos){
		return character[pos];
	}
	
	public int getGroupCharacterQuantity(){
		return quantity;
	}
	
	public int getMoneyValue(){
		return money.getSkill().getFeature().getValue();
	}
	
	public void setMoneyValue(int value){
		money.getSkill().getFeature().changeValue(value);
	}
	
	public Feature getMoney() {
		return money;
	}

	public void setMoney(Feature money) {
		this.money = money;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
			
}
 
