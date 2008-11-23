package framework.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.fenggui.Canvas;
import org.fenggui.PixmapDecorator;
import org.fenggui.composites.Window;
import org.fenggui.render.Binding;
import org.fenggui.render.Cursor;
import org.fenggui.render.Pixmap;

import framework.FactoryManager;
import framework.RPGSystem;

/**
 * Responsible for drawing the interface used in the engine, exemple, menus,
 * widgets, etc.
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class GUI {

	/**
	 * Object responsible for the GUI, it uses the FengGUI display
	 */
	org.fenggui.Display disp;

	private Cursor cursor = null;
	
	private Canvas character = null;

	/**
	 * Constructor of the class, it initialize the display
	 */
	public GUI() {

	}

	/**
	 * Method used to set the GUI properties
	 */
	public void setGUIProperties() {

	}

	public void createCursor() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new URL("file:res/images/cursor/cursor1.png"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//create a cursor, must get an image
		cursor = Binding.getInstance().getCursorFactory().createCursor(0,27,
				img);

	}

	public void createDisplay() {
		disp = new org.fenggui.Display(
				new org.fenggui.render.lwjgl.LWJGLBinding());

	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	
	public void createCharacterImage(String file, String path){
		
		//create the container
		Canvas keepImage = new Canvas();
		
		//create the image
		Pixmap image = new Pixmap(FactoryManager.getFactoryManager()
				.getResourceManager().getPictureManager().getPicture()
				.getIPicture(file,path + file));
		
		//just to have the image
		keepImage.getAppearance().removeAll();
		
		//set some properties of the canvas
		keepImage.setExpandable(false);
		keepImage.setShrinkable(false);
			
		//set the position
		//keepImage.setXY(x, y);
		
		//set the size
		keepImage.setSize(image.getWidth(), image.getHeight());
		
		//put it in a container
		keepImage.getAppearance().add(
				new PixmapDecorator(Window.GENERATE_NAME, image));
		
		//get the image
		character = keepImage;
			
	}
	
	public void placeCharacter(int x, int y){
		//set the place to put the character
		character.setXY(x,y);
	}
		
	public void insertCharacter(){
		disp.addWidget(character);		
	}
	
	public void removeCharacter(){
		disp.removeWidget(character);
	}
		
	public void createCityImage(String file, String path, int x, int y) {
		
		//create the container
		Canvas keepImage = new Canvas();
		
		//create the image
		Pixmap image = new Pixmap(FactoryManager.getFactoryManager()
				.getResourceManager().getPictureManager().getPicture()
				.getIPicture(file,path + file));
		
		//just to have the image
		keepImage.getAppearance().removeAll();
		
		//set some properties of the canvas
		keepImage.setExpandable(false);
		keepImage.setShrinkable(false);
			
		//set the position
		keepImage.setXY(x, y);
		
		//set the size
		keepImage.setSize(image.getWidth(), image.getHeight());
		
		//put it in a container
		keepImage.getAppearance().add(
				new PixmapDecorator(Window.GENERATE_NAME, image));
		
		//place the image in the display
		disp.addWidget(keepImage);
		
	}
	
	public void createMapImage(String file, String path, int x, int y, int sizeX,
			int sizeY) {
		
		//create the container
		Canvas keepImage = new Canvas();
		
		//create the image
		Pixmap image = new Pixmap(FactoryManager.getFactoryManager()
				.getResourceManager().getPictureManager().getPicture()
				.getIPicture(file,path + file));
		
		//just to have the image
		keepImage.getAppearance().removeAll();
		
		//set some properties of the canvas
		keepImage.setExpandable(false);
		keepImage.setShrinkable(false);
		
		//set the size
		keepImage.setSize(sizeX, sizeY);
		
		//set the position
		keepImage.setXY(x, y);
		
		//put it in a container
		keepImage.getAppearance().add(
				new PixmapDecorator(Window.GENERATE_NAME, image));
			
		//place the image in the display
		disp.addWidget(keepImage);
		
	}
	
	public void cleanDisplay(){
		disp.removeAllWidgets();
	}	
	
	public void showConversationBox(){
		
		//puts the window in the display
		disp.addWidget(RPGSystem.getRPGSystem().getMenu().getConversationBox());

		//refresh the display
		disp.layout();		
	}
	
	public void showPrimaryMenu(){
		
		// puts the window in the display
		disp.addWidget(RPGSystem.getRPGSystem().getMenu().getPrimaryMenu());

		//refresh the display
		disp.layout();	
	}	
	
	public void showSecondaryMenu(){
		// puts the window in the display
		disp.addWidget(RPGSystem.getRPGSystem().getMenu().getSecondaryMenu());

		//refresh the display
		disp.layout();	
	}
	
	public void showThirdMenu(){
		// puts the window in the display
		disp.addWidget(RPGSystem.getRPGSystem().getMenu().getThirdMenu());

		//refresh the display
		disp.layout();	
	}
	
	public void showBattleMenu(){
		//puts the window in the display
		disp.addWidget(RPGSystem.getRPGSystem().getMenu().getBattleMenu());
		//refresh the display
		disp.layout();
	}
	
	public void showTargetMenu(){
		//puts the window in the display
		disp.addWidget(RPGSystem.getRPGSystem().getMenu().getTargetMenu());
		//refresh the display
		disp.layout();
	}
			
	public void closePrimaryMenu() {
		disp.removeWidget(RPGSystem.getRPGSystem().getMenu().getPrimaryMenu());
	}
	
	public void closeSecondaryMenu() {
		disp.removeWidget(RPGSystem.getRPGSystem().getMenu().getSecondaryMenu());
	}
	
	public void closeThirdMenu() {
		disp.removeWidget(RPGSystem.getRPGSystem().getMenu().getThirdMenu());
	}

	public void closeConversationBox(){
		disp.removeWidget(RPGSystem.getRPGSystem().getMenu().getConversationBox());
	}
	
	public void closeBattleMenu(){
		disp.removeWidget(RPGSystem.getRPGSystem().getMenu().getBattleMenu());
	}
	
	public void closeTargetMenu(){
		disp.removeWidget(RPGSystem.getRPGSystem().getMenu().getTargetMenu());
	}

	public void displayDraw() {
		disp.display();
	}

	public org.fenggui.Display getDisp() {
		return disp;
	}

}
