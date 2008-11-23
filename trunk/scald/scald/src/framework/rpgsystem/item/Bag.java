package framework.rpgsystem.item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * It has the possessions of the characters or the
 * group
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Bag implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	/**
	 * Has the ItemEntry for all the itens in the bag
	 * and the ItemEntry is responsible for having the quantity
	 * of an item, maximum quantity and the item itself
	 */	
	private ArrayList<ItemEntry> itens;
	
	/**
	 * Responsible for the maximum quantity of ItemEntrys that the bag
	 * can have, in a way, responsible for the maxmimum quantity of itens 
	 * that the bag can have
	 */
	private int maxEntrys;	
		
	/**
	 * Constructor of the class, it initializes its objects
	 */
	public Bag(){
		itens = new ArrayList<ItemEntry>();
		maxEntrys = 0;			
	}
		
	public boolean createEntry(String entry,int quantity){
		
		//test if can put a new entry		
		//maxEntrys == 0 means can put as many as want		
		if((maxEntrys == 0) || (maxEntrys < itens.size())){		
			//create a temporary ItemEntry
			ItemEntry temp = new ItemEntry();
			//create a temporary Item
			Item item = new Item();

			//create the item, method to look for it and create it
			item.create(entry);

			//set the item, same as before
			temp.setItem(item);
			//set the quantity, created now so one
			temp.setQtd(quantity);
			//set the maximum quantity
			temp.setMaxQtd(item.getMaxQuantity());

			//put the ItemEntry in the arraylist
			itens.add(temp);
			
			//was add
			return true;
			
		}
		
		//not add, without space
		return false;
	}
		
	public boolean addItem(String itemName,int quantity){		
		//temporary object
		boolean look = false;
		boolean temp = false;		
		int find;
		int num = 0;
		
		//only when the item is put inside or can´t
		while(!look){
			//search if already exist
			find = searchItem(itemName,num);
			
			//if already exist must know if can add here
			//if can´t add here then search again
			if(find != -1){
				//try to know if can increase or not
				if(itens.get(find).getQtd() < itens.get(find).getMaxQtd()){
					//increase the count
					itens.get(find).increaseQuantityValue(quantity);
					//get out
					look = true;	
					//says that added it
					temp = true;
				}else{
					//test if the item must be unique
					if(itens.get(find).getItem().isUnique()){
						//get out without creating
						look = true;
					}else{
						num++;
					}
				}					
			}else{
				// create a new entry
				temp = createEntry(itemName,quantity);				
				//not found will create
				look = true;				
			}						
		}
				
		//says if was created of not
		if(temp){			
			System.out.println("picked up");
			return true;
		}else{
			System.out.println("full");
			return false;
		}				
	}
	
	public void printItems(){
		for(int i = 0; i < itens.size(); i++){
			//print the item name
			System.out.println("Name: " + itens.get(i).getItem().getName());
			//print the item description
			System.out.println("Description: " + itens.get(i).getItem().getDescription());
			//print the item quantity
			System.out.println("Quantity: " + itens.get(i).getQtd());
		}
	}
	
	public void removeItem(int slot){
		//will remove the item that has been used
		itens.get(slot).deacreseQuantity();
		
		//test if must remove the item
		if(itens.get(slot).getQtd() == 0){
			//remove the item
			itens.remove(slot);
		}		
	}
	
	public void removeItem(String itemName,int quantity){
		//find the item in the bag
		int pos = searchItem(itemName,0);

		//found the item
		if(pos != -1){
			itens.get(pos).decreaseQuantityValue(quantity);
			
			//test if must remove
			if(itens.get(pos).getQtd() <= 0){
				//remove the item
				itens.remove(pos);
			}
		}		
	}
	
	public int searchItem(String item,int begin){
		//temporary object
		ItemEntry temp;
		
		// search for the item using its identifier
		for (int i = begin; i < itens.size(); i++) {
			// get the ItemEntry
			temp = itens.get(i);
			// test if is the Item wanted
			if (temp.getItem().getName().equals(item)) {
				// get out of the loop
				return i;
			}
		}
		//return not found
		return -1;
	}
	
	public ItemEntry getItemEntry(String name){
		
		//get the position of the item
		int pos = searchItem(name,0);
		//if was found
		if(pos != -1)
			//return the ItemEntry
			return itens.get(pos);
		//else doesn´t exist
		return null;
	}
	
	public void useItem(String item, boolean group, int position){
		
		//get the item to be used
		ItemEntry temp = getItemEntry(item);
		
		if(group){
			//character party
			
		}else {
			//enemy party
		}
		
		//use the item according to who
		temp.getItem().useFeature(group,position);			
			
		//remove the quantity and test if must remove the item
		removeItem(item, 1);
		
	}
	
	public void getItens(){
		
	}
	
	public Item getItem(String itemName){
		
		//get the item wanted
		for(int i =0; i <itens.size(); i++){
			if(itens.get(i).getItem().getName().equals(itemName)){
				return itens.get(i).getItem();
			}
		}	
		
		//not found
		return null;
	}

	public int getMaxEntrys() {
		return maxEntrys;
	}

	public void setMaxEntrys(int maxEntrys) {
		this.maxEntrys = maxEntrys;
	}
	
	public int getBagSize(){
		return itens.size();
	}
	
	public String getItemEntryImageFile(int pos){
		return itens.get(pos).getItem().getImageFile();
	}
	
	public String getItemEntryImagePath(int pos){
		return itens.get(pos).getItem().getImagePath();
	}
	
	public String getItemEntryName(int pos){
		return itens.get(pos).getItem().getName();
	}
	
	public int getItemEntryQuantity(int pos){
		return itens.get(pos).getQtd();
	}
	
	public ItemEntry getItem(int pos){
		return itens.get(pos);
	}
	
}
 
