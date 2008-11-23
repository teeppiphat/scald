package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.Bag;

public class RemoveBagPlayerGroup implements Transaction{
	
	private static final long serialVersionUID = 1L;

	private Bag removeBag;
	
	public RemoveBagPlayerGroup(){}
	
	public RemoveBagPlayerGroup(Bag toRemove){
		removeBag = toRemove;
	}
	
	public void executeOn(Object bagGroup, Date arg1) {
		((DataCollection)bagGroup).getBagGroup().removeBagPlayerGroup(removeBag);
	}

}
