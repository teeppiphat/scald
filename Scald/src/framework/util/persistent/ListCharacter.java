package framework.util.persistent;

import java.io.Serializable;
import java.util.ArrayList;
import framework.rpgsystem.evolution.Character;

public class ListCharacter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Character> characters;
	
	public ListCharacter(){
		characters = new ArrayList<Character>();
	}
	
	public void addCharacter(Character character){
		characters.add(character);
	}
	
	public void removeCharacter(Character character){
		characters.remove(character);
	}
	
	public void removeCharacter(int character){
		characters.remove(character);
	}
	
	public void cleanCharacters(){
		characters.clear();
	}
	
}
