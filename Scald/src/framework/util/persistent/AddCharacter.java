package framework.util.persistent;

import java.util.Date;
import framework.rpgsystem.evolution.Character;

import org.prevayler.Transaction;

public class AddCharacter implements Transaction{
	
	private static final long serialVersionUID = 1L;

	private Character tempCharacter;
	
	public AddCharacter(){}
	
	public AddCharacter(Character toKeep){
		tempCharacter = toKeep;
	}
	
	public void executeOn(Object character, Date arg1) {
		((DataCollection)character).getCharacters().addCharacter(tempCharacter);
		
	}

}
