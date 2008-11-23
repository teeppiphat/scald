package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.Bag;

public class AddBagCharacter implements Transaction{
	
	private static final long serialVersionUID = 1L;

	private Bag tempBag;
	
	public AddBagCharacter(){}
	
	public AddBagCharacter(Bag toKeep){
		tempBag = toKeep;
	}
	public void executeOn(Object bagCharacter, Date arg1) {
		((DataCollection)bagCharacter).getBagCharacters().addBagCharacter(tempBag);		
	}

}
