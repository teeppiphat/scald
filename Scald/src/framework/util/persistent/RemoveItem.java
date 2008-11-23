package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.Item;

public class RemoveItem implements Transaction{

	private static final long serialVersionUID = 1L;

	private Item removeItem;
	
	public RemoveItem(){}
	
	public RemoveItem(Item toRemove){
		removeItem = toRemove;
	}
	
	public void executeOn(Object item, Date arg1) {
		((DataCollection)item).getItens().removeItem(removeItem);
	}
	
}
