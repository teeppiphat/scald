package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.Bag;

public class AddBagPlayerGroup implements Transaction{

	private static final long serialVersionUID = 1L;

	private Bag tempBagPlayerGroup;
	
	public AddBagPlayerGroup(){}
	
	public AddBagPlayerGroup(Bag toKeep){
		tempBagPlayerGroup = toKeep;
	}
	
	public void executeOn(Object bagGroup, Date arg1) {
		((DataCollection)bagGroup).getBagGroup().addBagPlayerGroup(tempBagPlayerGroup);
	}

	
}
