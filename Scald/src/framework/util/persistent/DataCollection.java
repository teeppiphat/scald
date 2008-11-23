package framework.util.persistent;

import java.io.Serializable;

public class DataCollection implements Serializable{

	private static final long serialVersionUID = 1L;

	private ListFeature features;
	
	private ListCharacter characters;
	
	private ListBagCharacter bagCharacters;
	
	private ListBagPlayerGroup bagGroup;
	
	private ListItem itens;
	
	private ListItemEntry itensEntry;
	
	private static DataCollection instance;
	
	private DataCollection(){
		features = new ListFeature();
		characters = new ListCharacter();
		bagCharacters = new ListBagCharacter();
		bagGroup = new ListBagPlayerGroup();
	}
	
	public static DataCollection getInstance(){
		if(instance==null) instance = new DataCollection();
		return instance;
	}

	public ListFeature getFeatures() {
		return features;
	}
	
	public ListCharacter getCharacters(){
		return characters;
	}
	
	public ListBagCharacter getBagCharacters(){
		return bagCharacters;
	}
	
	public ListBagPlayerGroup getBagGroup(){
		return bagGroup;
	}

	public ListItem getItens() {
		return itens;
	}

	public ListItemEntry getItensEntry() {
		return itensEntry;
	}
	
}
