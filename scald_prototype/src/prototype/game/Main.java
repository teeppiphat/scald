package prototype.game;

import framework.Game;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//TODO Colocar o persistente no lugar certo
		//PersitentManager pm = new PersitentManager("/prototeste");
		//pm.addFeature(new Feature());
		
		//motor - adicionar o z-buffer para não explodir a placa de vídeo
		
		//object used to keep the place where the file with the settings is
		//String settings = "res/properties/settings.xml";
		//String firstMap = "res/maps/maps.xml";
		
		String where = "res/properties/where.xml";		
		
		//creates the class
		Game game;
		
		//ask for an instance
		game = new Game(where);
				
		//begins the game
		game.start();
		
	}

}
