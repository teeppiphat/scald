package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.Item;

public class AddItem implements Transaction{
	
	private static final long serialVersionUID = 1L;

	private Item tempItem;
	
	public AddItem(){}
	
	public AddItem(Item toKeep){
		tempItem = toKeep;
	}
	
	public void executeOn(Object item, Date arg1) {
		((DataCollection)item).getItens().addItem(tempItem);		
	}
	
}
