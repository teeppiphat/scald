package framework.util.persistent;

import java.io.Serializable;
import java.util.ArrayList;

import framework.rpgsystem.item.ItemEntry;

public class ListItemEntry implements Serializable{

	private static final long serialVersionUID = 1L;

	private ArrayList<ItemEntry> itensEntry;
	
	public ListItemEntry(){
		itensEntry = new ArrayList<ItemEntry>();
	}
	
	public void addItem(ItemEntry item){
		itensEntry.add(item);
	}
	
	public void removeItem(ItemEntry item){
		itensEntry.remove(item);
	}
	
	public void removeItem(int item){
		itensEntry.remove(item);
	}
	
	public void cleanitensEntry(){
		itensEntry.clear();
	}
}
