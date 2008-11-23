package framework.util.persistent;

import java.io.Serializable;
import java.util.ArrayList;

import framework.rpgsystem.item.Bag;

public class ListBagCharacter implements Serializable{

	private static final long serialVersionUID = 1L;

	private ArrayList<Bag> bagCharacter;
	
	public ListBagCharacter(){
		bagCharacter = new ArrayList<Bag>();
	}
	
	public void addBagCharacter(Bag bag){
		bagCharacter.add(bag);
	}
	
	public void removeBagCharacter(Bag bag){
		bagCharacter.remove(bag);
	}
	
	public void removeBagCharacter(int bag){
		bagCharacter.remove(bag);
	}
	
	public void cleanBagCharacter(){
		bagCharacter.clear();
	}
}
