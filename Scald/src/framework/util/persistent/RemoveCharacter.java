package framework.util.persistent;

import java.util.Date;

import framework.rpgsystem.evolution.Character;

import org.prevayler.Transaction;

public class RemoveCharacter implements Transaction{

	private static final long serialVersionUID = 1L;

	private Character removeCharacter;
	
	public RemoveCharacter(){}
	
	public RemoveCharacter(Character toRemove){
		removeCharacter = toRemove;
	}
	
	public void executeOn(Object character, Date arg1) {
		((DataCollection)character).getCharacters().removeCharacter(removeCharacter);
	}

}
