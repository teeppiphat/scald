package framework.util.persistent;

import java.io.Serializable;
import java.util.ArrayList;

import framework.rpgsystem.item.Bag;

public class ListBagPlayerGroup implements Serializable{

	private static final long serialVersionUID = 1L;

	private ArrayList<Bag> bagPlayerGroup;
	
	public ListBagPlayerGroup(){
		bagPlayerGroup = new ArrayList<Bag>();
	}
	
	public void addBagPlayerGroup(Bag bag){
		bagPlayerGroup.add(bag);
	}
	
	public void removeBagPlayerGroup(Bag bag){
		bagPlayerGroup.remove(bag);
	}
	
	public void removeBagPLayerGroup(int bag){
		bagPlayerGroup.remove(bag);
	}
	
	public void cleanBagPlayerGroup(){
		bagPlayerGroup.clear();
	}
}
