package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.Bag;

public class RemoveBagCharacter implements Transaction{

	private static final long serialVersionUID = 1L;

	private Bag removeBag;
	
	public RemoveBagCharacter(){}
	
	public RemoveBagCharacter(Bag toRemove){
		removeBag = toRemove;
	}
	
	public void executeOn(Object bagCharacter, Date arg1) {
		((DataCollection)bagCharacter).getBagCharacters().removeBagCharacter(removeBag);
	}

}
