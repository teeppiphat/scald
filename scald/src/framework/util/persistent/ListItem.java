package framework.util.persistent;

import java.io.Serializable;
import java.util.ArrayList;

import framework.rpgsystem.item.Item;

public class ListItem implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Item> itens;
	
	public ListItem(){
		itens = new ArrayList<Item>();
	}
	
	public void addItem(Item item){
		itens.add(item);
	}
	
	public void removeItem(Item item){
		itens.remove(item);
	}
	
	public void removeItem(int item){
		itens.remove(item);
	}
	
	public void cleanItens(){
		itens.clear();
	}
}
