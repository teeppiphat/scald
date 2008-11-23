package framework.util.persistent;

import java.util.Date;

import org.prevayler.Transaction;

import framework.rpgsystem.item.ItemEntry;

public class AddItemEntry implements Transaction{

	private static final long serialVersionUID = 1L;

	private ItemEntry tempItemEntry;
	
	public AddItemEntry(){}
	
	public AddItemEntry(ItemEntry toKeep){
		tempItemEntry = toKeep;
	}
	
	public void executeOn(Object itemEntry, Date arg1) {
		((DataCollection)itemEntry).getItensEntry().addItem(tempItemEntry);
	}

}
