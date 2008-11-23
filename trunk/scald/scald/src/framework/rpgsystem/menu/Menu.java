package framework.rpgsystem.menu;

import java.util.Iterator;
import java.util.List;

import org.fenggui.Button;
import org.fenggui.Canvas;
import org.fenggui.Container;
import org.fenggui.GameMenuButton;
import org.fenggui.Label;
import org.fenggui.PixmapDecorator;
import org.fenggui.ScrollContainer;
import org.fenggui.TextEditor;
import org.fenggui.background.PixmapBackground;
import org.fenggui.border.PixmapBorder;
import org.fenggui.composites.Window;
import org.fenggui.event.ButtonPressedEvent;
import org.fenggui.event.IButtonPressedListener;
import org.fenggui.layout.BorderLayout;
import org.fenggui.layout.BorderLayoutData;
import org.fenggui.layout.GridLayout;
import org.fenggui.render.ITexture;
import org.fenggui.render.Pixmap;
import org.fenggui.table.ITableModel;
import org.fenggui.table.PixmapCellRenderer;
import org.fenggui.table.Table;
import org.fenggui.table.TableColumn;
import org.fenggui.util.Alphabet;
import org.fenggui.util.Color;
import org.fenggui.util.Dimension;
import org.fenggui.util.Spacing;
import org.jdom.Element;

import framework.FactoryManager;
import framework.Game;
import framework.RPGSystem;
import framework.rpgsystem.item.Item;
import framework.util.TableModel;

/**
 * The class responsible for putting together the system of the
 * RPG and the system of the menu
 *  *****The system of the menu must be specified, using an UML
 * @author Diego Antônio Tronquini Costi
 *
 */
public class Menu {
 
	/**
	 * Responsible for the menu, the main menu of the game
	 */
	Window primaryMenu;
	
	/**
	 * Responsible for the menu, the battle menu of the game
	 */
	Window battleMenu;
	
	/**
	 * Responsible for the menu, the secondary menus used
	 */
	Window secondaryMenu;
	
	/**
	 * Responsible for the menu, the third menus used, like after selecting the character,
	 * then show something from that character, will be used to show magics, status, equips
	 */
	Window thirdMenu;
	
	/**
	 * Responsible for the targetting of any action
	 */
	Window targetMenu;
	
	/**
	 * Responsible for the chat box
	 */
	Window conversationBox;
	
	/**
	 * Responsible for changing the conversation
	 */
	TextEditor conversation;
	
	/**
	 * Constructor of the class
	 */
	public Menu(){
		
	}
	
	public GameMenuButton createGameMenuButton(int sizeX, int sizeY, int posX,
			int posY, String notSelected, String selected) {
		
		//create the button, first is not selected, second is selected
		GameMenuButton menuButton = new GameMenuButton(notSelected, selected);
		
		//set the size
		menuButton.setSize(sizeX,sizeY);
		
		//set the position
		menuButton.setXY(posX, posY);
				
		//return the button created
		return menuButton;
		
	}
	
	public Button createButton(int sizeX, int sizeY, int posX, int posY,
			String pressedName, String pressedFile, String defaultName,
			String defaultFile, String highlightName, String highlightFile) {
			
		//create the button
		Button button = new Button();
				
		//set the size
		button.setSize(sizeX, sizeY);
		
		//adjust the appearance
		button.getAppearance().setPadding(Spacing.ZERO_SPACING);
		button.getAppearance().setMargin(Spacing.ZERO_SPACING);
		
		//set the position
		button.setXY(posX, posY);
		
		//remove the appearance that we don´t want
		button.getAppearance().removeAll();
		
		// set the images used for the button
		//default
		button.getAppearance().add(new PixmapDecorator(
			Button.LABEL_DEFAULT,new Pixmap(FactoryManager
			.getFactoryManager().getResourceManager().getPictureManager()
				.getPicture().getIPicture(defaultName,defaultFile))));

		//mouse over
		button.getAppearance().add(new PixmapDecorator(
				Button.LABEL_MOUSEHOVER,new Pixmap(FactoryManager
				.getFactoryManager().getResourceManager().getPictureManager()
					.getPicture().getIPicture(pressedName,pressedFile))));

		//pressed
		button.getAppearance().add(new PixmapDecorator(
				Button.LABEL_PRESSED,new Pixmap(FactoryManager
				.getFactoryManager().getResourceManager().getPictureManager()
					.getPicture().getIPicture(highlightName,highlightFile))));
		
		//return the button that was created
		return button;
	}
	
	public void changeText(String string){
		conversation.setText(string);
	}

	public void createConvesationBox(String file, String path, String want) {

		Element properties = FactoryManager.getFactoryManager()
				.getScriptManager().getReadScript().getElementXML(path + file,
						want);

		// create the menu, without the minimize,maximize,close
		conversationBox = new Window(false, false, false);

		// define the size
		conversationBox.setSize(Integer.parseInt(properties.getChild("Where")
				.getChildText("SizeX")), Integer.parseInt(properties.getChild(
				"Where").getChildText("SizeY")));

		// define the position
		conversationBox.setXY(Integer.parseInt(properties.getChild("Where")
				.getChildText("PosX")), Integer.parseInt(properties.getChild(
				"Where").getChildText("PosY")));

		// define the title
		conversationBox.setTitle(null);

		// remove the border
		conversationBox.getAppearance().removeAll();

		// create the new border
		conversationBox.getAppearance().add(
				createBorder(properties.getChild("ImagesBB").getChildText(
						"WindowBorder"), properties.getChild("ImagesBB")
						.getChildText("WindowBorderPath")));

		// creates a text area
		conversation = new TextEditor();
		// set the position of the text box,begins in the left inferior
		conversation.setX(0);
		conversation.setY(0);
		// set the area of the text area
		conversation.setWidth(conversationBox.getSize().getWidth() - 25);
		conversation.setHeight(conversationBox.getSize().getHeight() - 25);
		// test
		conversation.setMultiline(true);

		// disable to write in it
		conversation.setEnabled(false);

		// set the appearance
		conversation.getAppearance().add(
				createBackground(properties.getChild("ImagesBB").getChildText(
						"WindowBackground"), properties.getChild("ImagesBB")
						.getChildText("WindowBackgroundPath")));

		// set the color used for the text
		conversation.getAppearance().setTextColor(
				new Color(Float.parseFloat(properties.getChild("Font")
						.getChildText("TextColorRed")), Float
						.parseFloat(properties.getChild("Font").getChildText(
								"TextColorGreen")), Float.parseFloat(properties
						.getChild("Font").getChildText("TextColorBlue")), Float
						.parseFloat(properties.getChild("Font").getChildText(
								"TextColorAlpha"))));

		// set the font that will be used
		conversation.getAppearance().setFont(
				FactoryManager.getFactoryManager().getResourceManager()
						.getFontManager().getFont(
								properties.getChild("Font")
										.getChildText("Font")));

		// puts the text area in the window
		conversationBox.addWidget(conversation);

	}
		
	public void createSellMenu(int x, int y, int sizeX, int sizeY, int height){
		//object to create the window
		Window window;
		//create
		window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
						
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);

		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		window.getContentContainer().addWidget(scroll);	
		
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		scroll.setInnerWidget(table);	
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();

		
		//gives the number of itens, and 3 columns to show image, name and value
		model.initMatrix(RPGSystem.getRPGSystem().getPlayerGroup().getBag().getBagSize(), 3);
		
		for(int i = 0; i < RPGSystem.getRPGSystem().getPlayerGroup().getBag().getBagSize(); i++){
			
			//value
			//RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItem(i).getItem().getSell()
			//quantity
			//RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItem(i).getQtd();
			//get the image file
			//RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItemEntryImageFile(i);
			//RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItemEntryImagePath(i);
			
			model.addRowSell(
					FactoryManager.getFactoryManager().getResourceManager()
							.getPictureManager().getPicture().getIPicture(
									RPGSystem.getRPGSystem().getPlayerGroup()
											.getBag().getItemEntryImageFile(i),
									RPGSystem.getRPGSystem().getPlayerGroup()
											.getBag().getItemEntryImagePath(i)
											+ RPGSystem.getRPGSystem()
													.getPlayerGroup().getBag()
													.getItemEntryImageFile(i)),

					RPGSystem.getRPGSystem().getPlayerGroup().getBag()
							.getItemEntryName(i), RPGSystem.getRPGSystem()
							.getPlayerGroup().getBag().getItem(i).getQtd(),
					RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItem(
							i).getItem().getSell());
			
		}
				
		//set the model of the table
		table.setModel(model);
				
		//set the height of the rows
		table.getAppearance().setCellHeight(height);
				
		// change the cell renderer for the first column
		TableColumn column0 = table.getColumn(0);
		column0.setCellRenderer(new PixmapCellRenderer());
				
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(2, 2));	
		
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
			
		
		//put a button responsible for saying that the selected item will be used
		Button sell = new Button();
		sell = createButton(100, 50, 0, 0, "BotãoMenuSellPressionado.png",
				"res/images/buttons/menu/BotãoMenuSellPressionado.png",
				"BotãoMenuSellNormal.png",
				"res/images/buttons/menu/BotãoMenuSellNormal.png",
				"BotãoMenuSellSobre.png",
				"res/images/buttons/menu/BotãoMenuSellSobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		
		//set the margin to go up
		sell.getAppearance().setMargin(new Spacing(height, height));
		back.getAppearance().setMargin(new Spacing(height, height));

		//add the buttons 
		buttons.addWidget(sell);
		buttons.addWidget(back);
		
		//buttons.getAppearance().setMargin(new Spacing(20,20));
		
		//listeners of the buttons
		
		//get selected character menu
		sell.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {							
				
				//treat according to the place				
				//in this case the menu that will be opened
				
				if(table.getSelection() != -1){
					
					//will get the item that was sould
					String name = (String)table.getModel().getValue(table.getSelection(), 1);				
					
					//will get the value of the item
					int value = Integer.parseInt(table.getModel().getValue(table.getSelection(), 3).toString());
					
					//will remove the item
					RPGSystem.getRPGSystem().getPlayerGroup().removeItem(name, 1);

					// will put the value obtained in the players money
					RPGSystem.getRPGSystem().getPlayerGroup().setMoneyValue(value);
					
					//close
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().closeSecondaryMenu();
					
					//create again to refresh
					RPGSystem.getRPGSystem().getMenu().createSellMenu(250, 250, 300, 500, 30);
										
					//will show that menu
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showSecondaryMenu();
										
				}				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeSecondaryMenu();	
				//open again the initial menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showThirdMenu();
			}			
		});
		
		secondaryMenu = window;
	}
		
	public void createShopMenu(String identifier,int x, int y, int sizeX, int sizeY, int height){

		//object to create the window
		Window window;
		//create
		window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
		
		
				
		//System.out.println();
		
		//Element main = FactoryManager.getFactoryManager().getScriptManager().getReadScript().getElementXML(RPGSystem.getRPGSystem().getWhere(), "Shop");
		// Element temp =
		// FactoryManager.getFactoryManager().getScriptManager().getReadScript().getElementXML(RPGSystem.getRPGSystem().getWhere(),
		// identifier);

		// must get the elements in the file
		// first will get the file in question from the main file
		String file = FactoryManager.getFactoryManager().getScriptManager()
				.getReadScript().getFileElement(
						RPGSystem.getRPGSystem().getWhere(), "Shop"); 

		Element temp = FactoryManager.getFactoryManager().getScriptManager().getReadScript().getElementXML(file, identifier);
		Element temp2;
		
		//create a list
		final List list = temp.getChildren();
		
		//create an iterator
		Iterator i = list.iterator();
		
		//temporary item used to get the itens configuration
		final Item item = new Item();
				
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);

		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		window.getContentContainer().addWidget(scroll);	
		
		//scroll.setLayoutData(null);
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		scroll.setInnerWidget(table);	
		//scroll.addWidget(table);
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();

		//gives the number of itens, and 3 columns to show image, name and value
		model.initMatrix(list.size(), 3);
		
		//get all the itens
		while(i.hasNext()){			
			//get the item
			temp2 = (Element) i.next();
			
			//create the item			
			item.create(temp2.getText());
			
			//put the item data in the table
			model.addRowMagic(FactoryManager.getFactoryManager()
					.getResourceManager().getPictureManager().getPicture()
					.getIPicture(item.getImageFile(),
							item.getImagePath() + item.getImageFile()), item
					.getName(), item.getBuy());			
		}
		
		//set the model of the table
		table.setModel(model);
				
		//set the height of the rows
		table.getAppearance().setCellHeight(height);
				
		// change the cell renderer for the first column
		TableColumn column0 = table.getColumn(0);
		column0.setCellRenderer(new PixmapCellRenderer());
				
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(2, 2));	
		
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
			
		
		//put a button responsible for saying that the selected item will be used
		Button buy = new Button();
		buy = createButton(100, 50, 0, 0, "BotãoMenuBuyPressionado.png",
				"res/images/buttons/menu/BotãoMenuBuyPressionado.png",
				"BotãoMenuBuyNormal.png",
				"res/images/buttons/menu/BotãoMenuBuyNormal.png",
				"BotãoMenuBuySobre.png",
				"res/images/buttons/menu/BotãoMenuBuySobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		
		//set the margin to go up
		buy.getAppearance().setMargin(new Spacing(height, height));
		back.getAppearance().setMargin(new Spacing(height, height));

		//add the buttons 
		buttons.addWidget(buy);
		buttons.addWidget(back);
		
		//buttons.getAppearance().setMargin(new Spacing(20,20));
		
		//listeners of the buttons
		
		//get selected character menu
		buy.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {							
				
				//treat according to the place				
				//in this case the menu that will be opened
				
				if(table.getSelection() != -1){
					
					//will get the item from the list
					Element temp = (Element)list.get(table.getSelection());
								
					//will create the item
					item.create(temp.getText());
									
					//will try to buy
					//first test if the player has money
					if(RPGSystem.getRPGSystem().getPlayerGroup().getMoneyValue() >= item.getBuy()){
											
						//if has money will put one unit of the item in the bag and reduce the value
						RPGSystem.getRPGSystem().getPlayerGroup().addItem(item.getIdentifier(), 1);
						
						//will remove the amout that was used
						RPGSystem.getRPGSystem().getPlayerGroup().setMoneyValue(-item.getBuy());
						
					}													
				}				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeSecondaryMenu();	
				//open again the initial menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showThirdMenu();
			}			
		});
		
		secondaryMenu = window;
	}
	
	public void createAskShop(final String name, int x, int y, int sizeX, int sizeY){

		//object to create the window
		Window window;
		//create
		window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
		
		//create the buttons
		
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.CENTER);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 3));
		
		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
				
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
				
		//put a button responsible for saying that the selected item will be used
		Button buy = new Button();
		buy = createButton(100, 50, 0, 0, "BotãoMenuBuyPressionado.png",
				"res/images/buttons/menu/BotãoMenuBuyPressionado.png",
				"BotãoMenuBuyNormal.png",
				"res/images/buttons/menu/BotãoMenuBuyNormal.png",
				"BotãoMenuBuySobre.png",
				"res/images/buttons/menu/BotãoMenuBuySobre.png");
	
		Button sell = new Button();
		sell = createButton(100, 50, 0, 0, "BotãoMenuSellPressionado.png",
				"res/images/buttons/menu/BotãoMenuSellPressionado.png",
				"BotãoMenuSellNormal.png",
				"res/images/buttons/menu/BotãoMenuSellNormal.png",
				"BotãoMenuSellSobre.png",
				"res/images/buttons/menu/BotãoMenuSellSobre.png");

		Button exit = new Button();
		exit = createButton(100, 50, 0, 0, "BotãoMenuExitPressionado.png",
				"res/images/buttons/menu/BotãoMenuExitPressionado.png",
				"BotãoMenuExitNormal.png",
				"res/images/buttons/menu/BotãoMenuExitNormal.png",
				"BotãoMenuExitSobre.png",
				"res/images/buttons/menu/BotãoMenuExitSobre.png");

		//set the margin to go up without this the button is okay to select
		//buy.getAppearance().setMargin(new Spacing(20, 0));

		//add the buttons 
		buttons.addWidget(buy);
		buttons.addWidget(sell);
		buttons.addWidget(exit);
		
		//listeners of the buttons
		
		//get selected character menu
		buy.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//treat according to the place				
				//in this case the menu that will be opened
								
				//will create the menu of the shop
				RPGSystem.getRPGSystem().getMenu().createShopMenu(name,250, 250, 300, 500, 30);
								
				//will show that menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showSecondaryMenu();
				
				//will hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().closeThirdMenu();
				
			}			
		});
		
		//go back
		sell.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				// treat according to the place
				//in this case the menu that will be opened
				RPGSystem.getRPGSystem().getMenu().createSellMenu(250, 250, 300, 500, 30);
				
				//will create the menu of the sell
				FactoryManager.getFactoryManager().getGraphicsManager()
					.getGui().closeThirdMenu();	
				
				//will show that menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showSecondaryMenu();
			}			
		});			
		
		//go back
		exit.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeThirdMenu();		
				//to make certain that isn´t created more than one
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().cleanDisplay();
				
				FactoryManager.getFactoryManager().getEventManager().setWaitConversation(false);
				FactoryManager.getFactoryManager().getEventManager().setConversationFinished(true);
				
			}			
		});	
		
		thirdMenu = window;
		
	}
	
	public PixmapBackground createBackground(String file, String path) {

		// create the background
		PixmapBackground background = new PixmapBackground(new Pixmap(
				FactoryManager.getFactoryManager().getResourceManager()
						.getPictureManager().getPicture().getIPicture(file,
								path + file)));

		// return the background created
		return background;

	}
	
	public PixmapBorder createBorder(String file, String path) {
		// create a texture, that will be used for the border
		ITexture menuBackground = FactoryManager.getFactoryManager()
				.getResourceManager().getPictureManager().getPicture()
				.getIPicture(file, path + file);

		// mapping pixmaps on the texture. Note that the origin for pixmaps is
		// in the upper left corner!!!
		Pixmap upperLeftCorner = new Pixmap(menuBackground, 0, 0, 13, 13);
		Pixmap upperRightCorner = new Pixmap(menuBackground, 12, 0, 13, 13);
		Pixmap lowerLeftCorner = new Pixmap(menuBackground, 0, 12, 13, 13);
		Pixmap lowerRightCorner = new Pixmap(menuBackground, 12, 12, 13, 13);

		Pixmap leftEdge = new Pixmap(menuBackground, 0, 12, 13, 1);
		Pixmap rightEdge = new Pixmap(menuBackground, 12, 12, 13, 1);
		Pixmap topEdge = new Pixmap(menuBackground, 12, 0, 1, 13);
		Pixmap bottomEdge = new Pixmap(menuBackground, 12, 12, 1, 13);

		// create the border
		PixmapBorder border = new PixmapBorder(leftEdge, rightEdge, topEdge,
				bottomEdge, upperLeftCorner, upperRightCorner, lowerLeftCorner,
				lowerRightCorner);

		// return the border
		return border;

	}
	
	public void createPrimaryMenu(String file, String path, String want){
		//primaryMenu = createMenu(file,path,want);
		createMenu2(file, path, want);
	}
	
	public void createBattleMenu(String file, String path, String want){
			
		//get the menu to create, main, status, equip, etc.
		Element load = FactoryManager.getFactoryManager()
		.getScriptManager().getReadScript().getElementXML(path + file,
				want);
		
		//get all the children and test their names that are a part of the menu
		
		// get the children to a list
		List list = load.getChildren();
		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;
	
		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();
			
			//if is the main panel
			if(temp.getName().equals("MainPanel")){
				//create the main panel, its the one that configure the window
				battleMenu = createPanel(temp);					
			}//or another panel
			else{
				//create a panel that is part of the window
				battleMenu.addWidget(createPanel(temp));
			}
			
		}
		
	}
			
	public void createSecondaryMenu(String file,String path, String want){
		secondaryMenu = createMenu(file, path, want);		
	}
	
	public void createStatusGroupSelect(int x, int y, int sizeX, int sizeY, int height,final String type){
		
		Window window;
		//create
		window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
				
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		//scroll.setInnerWidget(table);	
		scroll.addWidget(table);
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();
				
		//depend on the quantity of items, the number of rows,
		//the number of columns is always 3, 0 is image, 1 is name and 2 is quantity
		model.initMatrix(RPGSystem.getRPGSystem().getPlayerGroup().getGroupCharacterQuantity() + 1, 3);
				
		//put the items in the table		
		for(int i = 0; i < model.getRowCount(); i++){
						
			//put a character in the table, first the image, second the name
			model.addRowCharacter(FactoryManager.getFactoryManager()
					.getResourceManager().getPictureManager().getPicture()
					.getIPicture(
							RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(i).getImageFile(),
							RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(i).getImagePath()
									+ RPGSystem.getRPGSystem().getPlayerGroup()
											.getCharacter(i).getImageFile()),
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(i)
							.getName());
					
		}	
		
		//set the model of the table
		table.setModel(model);
				
		//set the height of the rows
		table.getAppearance().setCellHeight(height);
				
		// change the cell renderer for the first column
		TableColumn column0 = table.getColumn(0);
		column0.setCellRenderer(new PixmapCellRenderer());
				
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 2));
		
		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		window.getContentContainer().addWidget(scroll);		
		
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
				
		//put a button responsible for saying that the selected item will be used
		Button select = new Button();
		select = createButton(100, 50, 0, 0, "BotãoMenuSelectPressionado.png",
				"res/images/buttons/menu/BotãoMenuSelectPressionado.png",
				"BotãoMenuSelectNormal.png",
				"res/images/buttons/menu/BotãoMenuSelectNormal.png",
				"BotãoMenuSelectSobre.png",
				"res/images/buttons/menu/BotãoMenuSelectSobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		//set the margin to go up
		select.getAppearance().setMargin(new Spacing(20, 0));

		//add the buttons 
		buttons.addWidget(select);
		buttons.addWidget(back);
		
		//listeners of the buttons
		
		//get selected character menu
		select.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {							
				
				//treat according to the place				
				//in this case the menu that will be opened
				
				if(table.getSelection() != -1){
					
					// get the one that was selected, the first is the row the
					// second is the column, row = selected, column = the name
					String temp = (String)table.getModel().getValue(table.getSelection(), 1);
					
					//first hide this menu
					FactoryManager.getFactoryManager().getGraphicsManager()
							.getGui().closeSecondaryMenu();	
										
					//second create the new menu
					//the type indicate the menu that must be created
					if(type.equals("Magic")){
						createMagicMenu(250, 250, 300, 500, 30, temp);
					}
					
					if(type.equals("Status")){
						createStatusMenu(250, 50, 500, 600, 30, temp);
					}
					
					if(type.equals("Skill")){
						createSkillMenu(250, 250, 300, 500, 30, temp);
					}
					
					if(type.equals("Equip")){
						
					}
					
					//then show the new menu
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showThirdMenu();					
				}
				
				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeSecondaryMenu();				
			}			
		});
		
		secondaryMenu = window;
	}
		
	public void createStatusMenu(int x, int y, int sizeX, int sizeY, int height, String who){
		
		//create
		Window window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
		
		//put the information in its place
		
		//find the character that was chosen
		int position = RPGSystem.getRPGSystem().getPlayerGroup().findCharacterNamePosition(who);
		
		// show the name		
		window.addWidget(createText(10, 530, 350, 30, "Sttng", "Name", RPGSystem
				.getRPGSystem().getPlayerGroup().getCharacter(position)
				.getName()));
		
		// show the class
		window.addWidget(createText(10, 500, 300, 30, "Sttng", "Class", RPGSystem
				.getRPGSystem().getPlayerGroup().getCharacter(position)
				.getCharacterClass().getName()));
		
		//image
				
		//must get the image of the character
		window.addWidget(createImage(RPGSystem.getRPGSystem().getPlayerGroup()
				.getCharacter(position).getImageFile(), RPGSystem
				.getRPGSystem().getPlayerGroup().getCharacter(position)
				.getImagePath(), 100, 100, 10, 400));
		
		//status - Atributes
		
		//temporary 
		String temp;
		
		for (int i = 0; i < RPGSystem.getRPGSystem().getPlayerGroup()
				.getCharacter(position).getCharacterClass().getFeatureNetwork()
				.getStatus().size(); i++) {
			
			//get one status

			//has different ways to treat the status
			
			//test status
			if (RPGSystem.getRPGSystem().getPlayerGroup()
					.getCharacter(position).getCharacterClass()
					.getFeatureNetwork().getStatus().get(i).getName().length() > 2) {
				
				//get the values
				temp = " " + RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(
								position).getCharacterClass().getFeatureNetwork()
								.getStatus().get(i).getSkill().getFeature()
								.getValue();
				
			}else{
				//get the values
				temp = " "
						+ RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(
								position).getCharacterClass().getFeatureNetwork()
								.getStatus().get(i).getSkill().getFeature()
								.getCurrent()
						+ " / " + RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(
								position).getCharacterClass().getFeatureNetwork()
								.getStatus().get(i).getSkill().getFeature()
								.getValue();
			}
			
			//show the status
			window.addWidget(createText(250, 450 - (i*30), 300, 50, "Sttng",
					RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(
							position).getCharacterClass().getFeatureNetwork()
							.getStatus().get(i).getName(), temp));

		}

		temp = " " + RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getExperience();
		
		//show the experience
		window.addWidget(createText(20, 300, 250, 30, "Sttng", "Experience",temp ));
		
		temp = " "
				+ RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(
						position).getCharacterClass().getClassXPTable().getXP(
						RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(
								position).getCharacterClass().getLevel());
		//show next level
		window.addWidget(createText(20,270,250,30,"Sttng","Next Level",temp));
		
		//get the level
		temp = " " + RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getLevel();

		//show the level
		window.addWidget(createText(20, 240, 250, 30, "Sttng", "Level", temp));
		
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 2));
		
		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
				
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
						
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
	
		//to make the button appear
		back.getAppearance().setMargin(new Spacing(20,0));
		
		//add the buttons 		
		buttons.addWidget(back);
		
		//listeners of the buttons
			
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeThirdMenu();
				//show the last menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().showSecondaryMenu();
			}			
		});
		
		thirdMenu = window;
	}
	
	public void createSkillMenu(int x, int y, int sizeX, int sizeY, int height,String who){
		
		//create
		Window window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
				
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		//scroll.setInnerWidget(table);	
		scroll.addWidget(table);
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();
		
		//the position of the character
		int position = RPGSystem.getRPGSystem().getPlayerGroup()
		.findCharacterNamePosition(who);
		
		//depend on the quantity of magics, the number of rows,
		//the number of columns is always 3, 0 is image, 1 is name and 2 is quantity
		model.initMatrix(RPGSystem.getRPGSystem().getPlayerGroup()
				.getCharacter(position)
				.getCharacterClass().getFeatureNetwork().getSkills().size(), 3);
		
		//put the items in the table		
		for(int i = 0; i < model.getRowCount(); i++){
						
			//put a magic in the table
			//model.addRow(image, name, cost);
			
			//add everything
			//model.addRow(image, name, quantity);
			
			//must get the magics that are in the class
						
			//add everything
			model.addRowCharacter(FactoryManager.getFactoryManager()
					.getResourceManager().getPictureManager().getPicture()
					.getIPicture(
							RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(position).getCharacterClass()
									.getFeatureNetwork().getMagics().get(i)
									.getImageFile(),
							RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(position).getCharacterClass()
									.getFeatureNetwork().getMagics().get(0)
									.getImagePath()
									+ RPGSystem.getRPGSystem().getPlayerGroup()
											.getCharacter(position)
											.getCharacterClass()
											.getFeatureNetwork().getMagics()
											.get(0).getImageFile()),

			RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position)
					.getCharacterClass().getFeatureNetwork().getSkills().get(i)
					.getName());
			
		}	
		
		//set the model of the table
		table.setModel(model);
				
		//set the height of the rows
		table.getAppearance().setCellHeight(height);
				
		// change the cell renderer for the first column
		TableColumn column0 = table.getColumn(0);
		column0.setCellRenderer(new PixmapCellRenderer());
				
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 2));
		
		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		window.getContentContainer().addWidget(scroll);		
		
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
				
		//put a button responsible for saying that the selected item will be used
		Button use = new Button();
		use = createButton(100, 50, 0, 0, "BotãoMenuUsePressionado.png",
				"res/images/buttons/menu/BotãoMenuUsePressionado.png",
				"BotãoMenuUseNormal.png",
				"res/images/buttons/menu/BotãoMenuUseNormal.png",
				"BotãoMenuUseSobre.png",
				"res/images/buttons/menu/BotãoMenuUseSobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		//set the margin to go up
		use.getAppearance().setMargin(new Spacing(20, 0));
		
		//add the buttons 
		buttons.addWidget(use);
		buttons.addWidget(back);
		
		//not able to use
		use.setEnabled(false);
		
		//listeners of the buttons
		
		//use selected item
		use.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				System.out.println("Selected: " + table.getSelection());	
				
				//treat according to the place
				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeThirdMenu();	
				//open the last menu
				if(Game.battle){
					//open the battle menu
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showBattleMenu();
					
				}else{
					//open the select menu
					FactoryManager.getFactoryManager().getGraphicsManager()
							.getGui().showSecondaryMenu();
				}				
			}			
		});
		
		thirdMenu = window;
		
	}
	
	public void createMagicMenu(int x, int y, int sizeX, int sizeY, int height, final String who){
				
		//create
		Window window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
				
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		//scroll.setInnerWidget(table);	
		scroll.addWidget(table);
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();
		
		//the position of the character
		final int position = RPGSystem.getRPGSystem().getPlayerGroup()
		.findCharacterNamePosition(who);
		
		//depend on the quantity of magics, the number of rows,
		//the number of columns is always 3, 0 is image, 1 is name and 2 is quantity
		model.initMatrix(RPGSystem.getRPGSystem().getPlayerGroup()
				.getCharacter(position)
				.getCharacterClass().getFeatureNetwork().getMagics().size(), 3);
		
		//put the items in the table		
		for(int i = 0; i < model.getRowCount(); i++){
						
			//put a magic in the table
			//model.addRow(image, name, cost);
			
			//add everything
			//model.addRow(image, name, quantity);
			
			//must get the magics that are in the class
						
			//add everything
			model.addRowMagic(FactoryManager.getFactoryManager()
					.getResourceManager().getPictureManager().getPicture()
					.getIPicture(
							RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(position).getCharacterClass()
									.getFeatureNetwork().getMagics().get(i)
									.getImageFile(),
							RPGSystem.getRPGSystem().getPlayerGroup()
									.getCharacter(position).getCharacterClass()
									.getFeatureNetwork().getMagics().get(i)
									.getImagePath()
									+ RPGSystem.getRPGSystem().getPlayerGroup()
											.getCharacter(position)
											.getCharacterClass()
											.getFeatureNetwork().getMagics()
											.get(i).getImageFile()),

			RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position)
					.getCharacterClass().getFeatureNetwork().getMagics().get(i)
					.getName(), RPGSystem.getRPGSystem().getPlayerGroup()
					.getCharacter(position).getCharacterClass()
					.getFeatureNetwork().getMagics().get(i).getMagic()
					.getFeature().getCost());
			
		}	
		
				
		//set the model of the table
		table.setModel(model);
				
		//set the height of the rows
		table.getAppearance().setCellHeight(height);
				
		// change the cell renderer for the first column
		TableColumn column0 = table.getColumn(0);
		column0.setCellRenderer(new PixmapCellRenderer());
				
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 2));
		
		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		window.getContentContainer().addWidget(scroll);		
		
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
				
		//put a button responsible for saying that the selected item will be used
		Button use = new Button();
		use = createButton(100, 50, 0, 0, "BotãoMenuUsePressionado.png",
				"res/images/buttons/menu/BotãoMenuUsePressionado.png",
				"BotãoMenuUseNormal.png",
				"res/images/buttons/menu/BotãoMenuUseNormal.png",
				"BotãoMenuUseSobre.png",
				"res/images/buttons/menu/BotãoMenuUseSobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		//set the margin to go up
		use.getAppearance().setMargin(new Spacing(20, 0));

		//add the buttons 
		buttons.addWidget(use);
		buttons.addWidget(back);
		
		//listeners of the buttons
		
		//use selected item
		use.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				
				//treat according to the place
				//get the name of the magic
				String name = (String) table.getModel().getValue(
						table.getSelection(), 1);
				//get the target
				String target = RPGSystem.getRPGSystem().getPlayerGroup()
						.getCharacter(position).getCharacterClass()
						.getFeatureNetwork().getMagic(name).getMagic()
						.getFeature().getTarget();
				
				//hide the menu used to select the magic
				FactoryManager.getFactoryManager().getGraphicsManager()
					.getGui().closeThirdMenu();
				
				//create the target menu
				RPGSystem.getRPGSystem().getMenu().createTargetMenu(250, 250,
						300, 500, 30, "Magic", target, name, who);
				
				// show the target menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showTargetMenu();
				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeThirdMenu();	
				//open the last menu
				if(Game.battle){
					//open the battle menu
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showBattleMenu();
				}else{
					//open the select menu
					FactoryManager.getFactoryManager().getGraphicsManager()
							.getGui().showSecondaryMenu();
				}				
			}			
		});
		
		thirdMenu = window;
		
	}
	
	public void createTargetMenu(int x, int y, int sizeX, int sizeY, int height, final String type, final String simple, final String used, final String user){
		
		//create
		Window window = new Window(false,false,false);
		//remove the appearance
		window.getAppearance().removeAll();
		//set the name
		window.setTitle(null);
		//set the position
		window.setXY(x, y);
		//set the size
		window.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		window.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		window.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
				
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		//scroll.setInnerWidget(table);	
		scroll.addWidget(table);
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();

		//test if is only one that is affected or a group
		if(simple.equals("Single")){
			//if is in a battle
			if(Game.battle){
				//test the quantity of characters in the battle
				if ((RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1) < FactoryManager
						.getFactoryManager().getRulesManager().getRulesSet()
						.getPlayersInBattle()) {
					model.initMatrix((RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1 + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length), 1);	
				}else{
					model.initMatrix(FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle() + RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy().length, 1);
				}				
			}else{
				model.initMatrix((RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1), 1);	
			}

			//put the characters as targets
			for(int i = 0; i < RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1; i++){
				model.addRow(RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(i).getName());
			}
			
			//if is in battle then has enemies as targets
			if(Game.battle){		
				//put the enemies as targets
				for(int i = 0; (i + RPGSystem.getRPGSystem().getPlayerGroup().getQuantity() + 1) < model.getRowCount(); i++){					
					// only show the enemy that has HP
					if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getStatusCurrent("HP") > 0){
						model.addRow(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getName());	
					}else{
						model.addRow(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(i).getName() + "(Death)");
					}
				}
			}
			
		}else if(simple.equals("Group")){
			//if a group is affected
			
			//if is in a battle
			if(Game.battle){
				//just create two possitions
				model.initMatrix(2, 1);
				
				//add the rows
				model.addRow("Player group");
				model.addRow("Enemy group");				
			}else{
				//create one
				model.initMatrix(1, 1);
				
				//add the rows
				model.addRow("Player group");
			}		
		}		
		
		//set the model of the table
		table.setModel(model);
		
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 2));
		
		//set the layout of the menu				
		window.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		window.getContentContainer().addWidget(scroll);		
		
		//put the container in the window
		window.getContentContainer().addWidget(buttons);
				
		//put a button responsible for saying that the selected item will be used
		Button use = new Button();
		use = createButton(100, 50, 0, 0, "BotãoMenuUsePressionado.png",
				"res/images/buttons/menu/BotãoMenuUsePressionado.png",
				"BotãoMenuUseNormal.png",
				"res/images/buttons/menu/BotãoMenuUseNormal.png",
				"BotãoMenuUseSobre.png",
				"res/images/buttons/menu/BotãoMenuUseSobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		//set the margin to go up
		use.getAppearance().setMargin(new Spacing(20, 0));

		//add the buttons 
		buttons.addWidget(use);
		buttons.addWidget(back);
		
		//listeners of the buttons
		
		//use selected item
		use.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
												
				//treat according to the place
				if(table.getSelection() != -1){
					
					// get the one that was selected, the first is the row the
					// second is the column, row = selected, column 0 = the name, column 1 = the group
					String temp = (String)table.getModel().getValue(table.getSelection(), 0);
					
					// for the group of the target
					boolean targetGroup = false; 
					// for the group of the user
					boolean userGroup = false;

					//first hide this menu
					FactoryManager.getFactoryManager().getGraphicsManager()
							.getGui().closeTargetMenu();
					
					// Find the user and the target
					
					//find the character in question
					int targetPosition = 0;
					//find the character that is the user
					int userPosition = 0;
										
					//test the type of target
					if(simple.equals("Single")){
						// must find the character that is the user
						if(RPGSystem.getRPGSystem().getPlayerGroup().findCharacterNamePosition(user) != -1){
							userGroup = true;
						}else if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].findEnemyNamePosition(user) != -1){
							userGroup = false;
						}
						
						// must find the characters group, find the target
						if(RPGSystem.getRPGSystem().getPlayerGroup().findCharacterNamePosition(temp) != -1){
							targetGroup = true;
						}else if(RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].findEnemyNamePosition(temp) != -1){
							targetGroup = false;
						}
						
						//according to the group, target
						if(targetGroup){
							targetPosition = RPGSystem.getRPGSystem().getPlayerGroup().findCharacterNamePosition(temp);
						}else{
							targetPosition = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].findEnemyNamePosition(temp);
						}
						
						//according to the group, user
						if(userGroup){
							userPosition = RPGSystem.getRPGSystem().getPlayerGroup().findCharacterNamePosition(user);
						}else{
							userPosition = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].findEnemyNamePosition(user);
						}
						
					}else if(simple.equals("Group")){
						//test which group is from
						if(temp.charAt(0) == 'P'){
							targetGroup = true;
						}else if(temp.charAt(0) == 'E'){
							targetGroup = false;
						}
					}					
										
					//execute the action according to where it is being used
					if(Game.battle){
						//inside the battle
												
						//create the action
						RPGSystem.getRPGSystem().getBattleSystem()
								.createAction(type, userGroup, userPosition,
										targetGroup, targetPosition, used, 0);
						
						//say that the action was created
						RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().setOption((short)3);
									
					}else{
						//outside the battle
						//just use it
						
						//for test purpose
						if(type.equals("Item")){
													
							//use the item
							RPGSystem.getRPGSystem().getPlayerGroup().getBag().useItem(used,targetGroup, targetPosition);
							
							//refresh the menu
							FactoryManager.getFactoryManager().getGraphicsManager().getGui().closeSecondaryMenu();
														
							RPGSystem.getRPGSystem().getMenu()
									.createInventoryMenu(250, 250, 300, 500, 30);
							
							FactoryManager.getFactoryManager().getGraphicsManager().getGui().showSecondaryMenu();
							
						}else if(type.equals("Magic")){
							
							//need to indicate the user and the target
							
							//use according to the group of the user
							if(userGroup){
								//use the magic
								RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(userPosition).getCharacterClass().getFeatureNetwork().getMagic(used).useFeature(userGroup,userPosition,targetGroup,targetPosition);
								
							}else{
								//use the magic
								RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(userPosition).getMagic(used).useFeature(userGroup, userPosition, targetGroup, targetPosition);
							}
							
							
							//must find the target group and position
							
							
							
							//RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(position).getCharacterClass().getFeatureNetwork().getMagic(used).useFeature(simple, group, position);
							//ADD THE COST
							
							// TODO
						}
					
					}
					
				}
				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeTargetMenu();	
				//open the last menu
				if(Game.battle){
					//open the battle menu
					FactoryManager.getFactoryManager().getGraphicsManager().getGui().showBattleMenu();
				}else{
					//open the select menu
					FactoryManager.getFactoryManager().getGraphicsManager()
							.getGui().showSecondaryMenu();
				}				
			}			
		});
		
		//receive the window that was created
		targetMenu = window;
		
	}

	public void createInventoryMenu(int x, int y, int sizeX,int sizeY,int height){
		
		//create
		secondaryMenu = new Window(false,false,false);
		//remove the appearance
		secondaryMenu.getAppearance().removeAll();
		//set the name
		secondaryMenu.setTitle(null);
		//set the position
		secondaryMenu.setXY(x, y);
		//set the size
		secondaryMenu.setSize(sizeX, sizeY);
		
		//set the properties of the window
		
		//put the background in the menu, use an image
		secondaryMenu.getAppearance().add(createBackground("BackgroundInventory2.png","res/images/"));

		//put the border in the menu
		secondaryMenu.getAppearance().add(createBorder("BorderMenu.png","res/images/"));
				
		//create the scroll
		final ScrollContainer scroll = new ScrollContainer();			
		//set the place the scroll will be
		scroll.setLayoutData(BorderLayoutData.CENTER);
								
		//create the table
		final Table table = new Table();
		//put the table inside the scroll		
		//scroll.setInnerWidget(table);	
		scroll.addWidget(table);
			
		//remove the grid
		table.getAppearance().setGridVisible(false);
		//remove the header
		table.getAppearance().setHeaderVisible(false);
		//change the color for the selection
		table.getAppearance().setSelectionColor(Color.DARK_BLUE);		
		//create the model
		TableModel model = new TableModel();
		//depend on the quantity of items, the number of rows,
		//the number of columns is always 3, 0 is image, 1 is name and 2 is quantity
		model.initMatrix(RPGSystem.getRPGSystem().getPlayerGroup().getBag().getBagSize(), 3);
		
		//put the items in the table		
		for(int i = 0; i < model.getRowCount(); i++){
					
			//put an item in the table
			//model.addRow(image, name, quantity);
			
			//add everything
			model.addRow(
					FactoryManager.getFactoryManager().getResourceManager()
							.getPictureManager().getPicture().getIPicture(
									RPGSystem.getRPGSystem().getPlayerGroup()
											.getBag().getItemEntryImageFile(i),
									RPGSystem.getRPGSystem().getPlayerGroup()
											.getBag().getItemEntryImagePath(i)
											+ RPGSystem.getRPGSystem()
													.getPlayerGroup().getBag()
													.getItemEntryImageFile(i)),
					RPGSystem.getRPGSystem().getPlayerGroup().getBag()
							.getItemEntryName(i), RPGSystem.getRPGSystem()
							.getPlayerGroup().getBag().getItemEntryQuantity(i));
			
		}	
		
		//set the model of the table
		table.setModel(model);
				
		//set the height of the rows
		table.getAppearance().setCellHeight(height);
		//table.getAppearance().setCellSpacing(space);
				
		// change the cell renderer for the first column
		TableColumn column0 = table.getColumn(0);
		column0.setCellRenderer(new PixmapCellRenderer());
				
		//container used to hold both buttons
		Container buttons = new Container();
		//set the place that will take in the layout
		buttons.setLayoutData(BorderLayoutData.SOUTH);
		//set the layout used
		buttons.setLayoutManager(new GridLayout(1, 2));
		
		//set the layout of the menu				
		secondaryMenu.getContentContainer().setLayoutManager(new BorderLayout());
		//put the scroll
		secondaryMenu.getContentContainer().addWidget(scroll);		
		
		//put the container in the window
		secondaryMenu.getContentContainer().addWidget(buttons);
				
		//put a button responsible for saying that the selected item will be used
		Button use = new Button();
		use = createButton(100, 50, 0, 0, "BotãoMenuUsePressionado.png",
				"res/images/buttons/menu/BotãoMenuUsePressionado.png",
				"BotãoMenuUseNormal.png",
				"res/images/buttons/menu/BotãoMenuUseNormal.png",
				"BotãoMenuUseSobre.png",
				"res/images/buttons/menu/BotãoMenuUseSobre.png");
				
		//button responsible for coming back
		Button back = new Button();
		back = createButton(100, 50, 0, 0, "BotãoMenuBackPressionado.png",
				"res/images/buttons/menu/BotãoMenuBackPressionado.png",
				"BotãoMenuBackNormal.png",
				"res/images/buttons/menu/BotãoMenuBackNormal.png",
				"BotãoMenuBackSobre.png",
				"res/images/buttons/menu/BotãoMenuBackSobre.png");
		
		//set the margin to go up
		use.getAppearance().setMargin(new Spacing(20, 0));

		//add the buttons 
		buttons.addWidget(use);
		buttons.addWidget(back);
		
		//listeners of the buttons
		
		//use selected item
		use.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
			
				//get the name of the item
				String temp = RPGSystem.getRPGSystem().getPlayerGroup().getBag().getItemEntryName(table.getSelection());
				
				//for the user
				String user = "not used";
				
				//test if is in battle
				if(Game.battle){
					//get the position
					int pos = (int)RPGSystem.getRPGSystem().getBattleSystem().getActionStrategy().getCharacterActs();
					//get the name of the user
					user = RPGSystem.getRPGSystem().getPlayerGroup().getCharacter(pos).getName();
				}
				
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
					.getGui().closeSecondaryMenu();
				
				//create the target menu
				RPGSystem.getRPGSystem().getMenu().createTargetMenu(
						250,
						250,
						300,
						500,
						30,						
						"Item",
						RPGSystem.getRPGSystem().getPlayerGroup().getBag()
								.getItemEntry(temp).getItem().getUsable()
								.getFeature().getTarget(), temp, user);
				
				//show the target menu
				FactoryManager.getFactoryManager().getGraphicsManager().getGui().showTargetMenu();
				
			}			
		});
		
		//go back
		back.addButtonPressedListener(new IButtonPressedListener(){

			public void buttonPressed(ButtonPressedEvent arg0) {
				//hide this menu
				FactoryManager.getFactoryManager().getGraphicsManager()
						.getGui().closeSecondaryMenu();
				
				//call the menu according to where was in the game
				if(Game.battle){
					FactoryManager.getFactoryManager().getGraphicsManager()
							.getGui().showBattleMenu();
				}
				
			}			
		});
			
	}
	
	public Dimension getSize(Element size){		
		return new Dimension(Integer.parseInt(size.getChildText("SizeX")),
				Integer.parseInt(size.getChildText("SizeY")));
	}
	
	public int getPosX(Element pos){
		return Integer.parseInt(pos.getChildText("PosX"));
	}
	
	public int getPosY(Element pos){
		return Integer.parseInt(pos.getChildText("PosY"));
	}
		
	public Label createText(int x,int y, int sizeX, int sizeY, String font, String first, String second){
		
		//create the label
		Label label = new Label();
				
		//set what must be written
		label.setText(first + ": " + second);		
		//set the size
		label.setSize(sizeX, sizeY);
		//set the position
		label.setXY(x, y);
				
		//set the text color as white
		label.getAppearance().setTextColor(Color.WHITE);
		//set to the minimum size necessary
		//label.setSizeToMinSize();
		
		//get the font used for the Text
		label.getAppearance().setFont(
				FactoryManager.getFactoryManager().getResourceManager()
						.getFontManager().getFont(font));
		
		//return the label
		return label;
	}
	
	public Label createText(Element element){
		
		//create a label to put in the menu
		Label text = new Label();
		
		//set the text of the label
		text.setText(element.getChildText("Write"));
		
		//set the size
		text.setSize(getSize(element.getChild("Where")));
		
		//set the position
		text.setXY(getPosX(element.getChild("Where")), getPosY(element.getChild("Where")));
		
		//set to the minimum size necessary
		text.setSizeToMinSize();
		
		//get the font used for the Text
		text.getAppearance()
				.setFont(
						FactoryManager.getFactoryManager().getResourceManager()
								.getFontManager().getFont(
										element.getChildText("Font")));
		
		
		
		//put the label in the panel, return it
		return text;
	}
	
	public Canvas createImage(String file, String path, int sizeX,int sizeY, int x, int y){
		//create the image
		Pixmap image = new Pixmap(FactoryManager.getFactoryManager()
				.getResourceManager().getPictureManager().getPicture()
				.getIPicture(file,path + file));

		//create the container for the image
		Canvas keepImage = new Canvas();
		
		//just to have the image
		keepImage.getAppearance().removeAll();
		
		//set some properties of the canvas
		keepImage.setExpandable(false);
		keepImage.setShrinkable(false);
				
		// set the size
		keepImage.setSize(sizeX,sizeY);

		// set the position
		keepImage.setXY(x,y);
		
		//set the image as a background
		keepImage.getAppearance().add(
				new PixmapDecorator(Window.GENERATE_NAME, image));
			
		//return it, to put in the panel
		return keepImage;		
	}
	
	public Canvas createImage(Element element){

		//create the image
		Pixmap image = new Pixmap(FactoryManager.getFactoryManager()
				.getResourceManager().getPictureManager().getPicture()
				.getIPicture(
						element.getChildText("File"),
						element.getChildText("Path")
						+ element.getChildText("File")));

		//create the container for the image
		Canvas keepImage = new Canvas();
		
		//just to have the image
		keepImage.getAppearance().removeAll();
		
		//set some properties of the canvas
		keepImage.setExpandable(false);
		keepImage.setShrinkable(false);
				
		// set the size
		keepImage.setSize(getSize(element.getChild("Where")));

		// set the position
		keepImage.setXY(getPosX(element.getChild("Where")), getPosY(element
				.getChild("Where")));
		
		//set the image as a background
		keepImage.getAppearance().add(
				new PixmapDecorator(Window.GENERATE_NAME, image));
			
		//return it, to put in the panel
		return keepImage;
	}
	
	public Label createFeature(Element element){
		
		//create the label used to show
		Label feature = new Label();
		
		// set the size
		feature.setSize(getSize(element.getChild("Where")));

		// set the position
		feature.setXY(getPosX(element.getChild("Where")), getPosY(element
				.getChild("Where")));
		
		
		//set the font
		feature.getAppearance()
				.setFont(
						FactoryManager.getFactoryManager().getResourceManager()
								.getFontManager().getFont(
										element.getChildText("Font")));
		
		//get if must show only the name or the name and the value
		
		//just to write the name, for now
		feature.setText(element.getChildText("Name"));
		
		//set size to the minimum
		feature.setSizeToMinSize();
		
		
		//returns it
		return feature;
		
	}
	
	public Window createPanel(Element panel){

		//create the panel
		Window window = new Window(false,false,false);
		
		//set the title null
		window.setTitle(null);
		
		//remove the appearance
		window.getAppearance().removeAll();
		
		//get all the children and test their names
		// get the children to a list
		List list = panel.getChildren();
		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;
	
		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();
		
			//the tag Where
			if(temp.getName().equals("Where")){
				//get the size,need to pass the size to the panel
				window.setSize(getSize(temp));
				
				//get the position, need to pass the position to the panel
				window.setX(getPosX(temp));
				window.setY(getPosY(temp));
				
			}
			//the tag ImagesBB
			if(temp.getName().equals("ImagesBB")){
				//create the Background, need to pass to the panel
				window.getAppearance().add(createBackground(temp.getChildText("BackgroundImage"), temp
						.getChildText("BackgroundImagePath")));
				// create the Border, need to pass to the panel
				window.getAppearance().add(createBorder(temp.getChildText("BorderImage"), temp
						.getChildText("BorderImagePath")));
			}
			//the tag Option
			if(temp.getName().equals("Option")){
												
				//object to manipulate and to create the button
				Button button = createButton(getSize(temp.getChild("Where"))
						.getWidth(), getSize(temp.getChild("Where"))
						.getHeight(), getPosX(temp.getChild("Where")),
						getPosY(temp.getChild("Where")), temp
								.getChildText("PressedImage"), temp
								.getChildText("PressedPath")
								+ temp.getChildText("PressedImage"), temp
								.getChildText("DefaultImage"), temp
								.getChildText("DefaultPath")
								+ temp.getChildText("DefaultImage"), temp
								.getChildText("HighLightImage"), temp
								.getChildText("HighLightPath")
								+ temp.getChildText("HighLightImage"));
				
				//set the name of the button, uses the Text
				button.setText(temp.getChildText("Event"));
				
				//don´t show the text
				button.getAppearance().setTextColor(new Color(0.0f,0.0f,0.0f,0.0f));
				
				//System.out.println(button.getText());
				
				//puts an event in the button when pressed
				button.addButtonPressedListener(new IButtonPressedListener(){

					public void buttonPressed(ButtonPressedEvent arg0) {
										
						//load the script
						FactoryManager.getFactoryManager().getScriptManager().getReadScript().loadScript("res/menu/", "menu.js");
						
						//pass to be used
						FactoryManager.getFactoryManager().getScriptManager().getReadScript().executeScript("menu", arg0.getTrigger().getText());
						
						//look at the script
						
						//
						//test to print the name of the button
						//System.out.println(arg0.getTrigger().getText());
						
						//works fine

						//gets from the menu the action that is going to use
						
					}
					
				});
				
				//put the button in the panel
				window.addWidget(button);
				
			}
			
			//the tag Text
			if(temp.getName().equals("Text")){
				//create a Text, need to put it in the panel
				window.addWidget(createText(temp));
				
			}
			//the tag Image
			if(temp.getName().equals("Image")){
				//create a Image
				window.addWidget(createImage(temp));
				
			}
			//the tag Feature
			if(temp.getName().equals("Feature")){
				//create a Feature to show(in the menu)
				window.addWidget(createFeature(temp));
			}

		}
		
		//return the panel
		return window;
	}
	
	public void createMenu2(String file, String path, String want){
		
		//get the menu to create, main, status, equip, etc.
		Element load = FactoryManager.getFactoryManager()
		.getScriptManager().getReadScript().getElementXML(path + file,
				want);
		
		//get all the children and test their names that are a part of the menu
		
		// get the children to a list
		List list = load.getChildren();
		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;
	
		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();
			
			//if is the main panel
			if(temp.getName().equals("MainPanel")){
				//create the main panel, its the one that configure the window
				primaryMenu = createPanel(temp);					
			}//or another panel
			else{
				//create a panel that is part of the window
				primaryMenu.addWidget(createPanel(temp));
			}
			
		}
		
	}
		
	public Window createMenu(String file,String path,String want){
		
		Window menu;
		
		Element properties = FactoryManager.getFactoryManager()
				.getScriptManager().getReadScript().getElementXML(path + file,
						want);
		
		//create the menu, without the minimize,maximize,close
		menu = new Window(false,false,false);
		
		//define the size
		menu.setSize(Integer.parseInt(properties.getChildText("SizeX")),
				Integer.parseInt(properties.getChildText("SizeY")));
		
		//define the position
		menu.setXY(Integer.parseInt(properties.getChildText("PosX")), Integer
				.parseInt(properties.getChildText("PosY")));
		//define the title
		menu.setTitle(properties.getChildText("MenuTitle"));
		
		//remove the appearance that already has
		//so that the new appearance is put
		menu.getAppearance().removeAll();
		
		// put the background in the menu, use an image
		menu.getAppearance().add(
				createBackground(properties.getChildText("BackgroundImage"),
						properties.getChildText("BackgroundImagePath")));

		//put the border in the menu
		menu.getAppearance().add(
				createBorder(properties.getChildText("BorderImage"), properties
						.getChildText("BorderImagePath")));
				
		//create the objects that are a part of the menu, in the case
		//the buttons that are needed
		
		
		//get the number of options to insert in the menu
		int options = Integer.parseInt(properties.getChildText("QuantityOption"));
		
		// loop that will keep loading the buttons
		for (int i = 1; i<=options;i++){
			
			//createButton(sizeX, sizeY, posX, posY, pressedName, pressedFile, defaultName, defaultFile, highlightName, highlightFile)
			Button button = createButton(Integer.parseInt(properties
					.getChildText("Option" + i + "SizeX")), Integer
					.parseInt(properties.getChildText("Option" + i + "SizeY")),
					Integer.parseInt(properties.getChildText("Option" + i
							+ "PosX")), Integer.parseInt(properties
							.getChildText("Option" + i + "PosY")), properties
							.getChildText("Option" + i + "PressedImage"),
					properties.getChildText("Option" + i + "PressedPath")
							+ properties.getChildText("Option" + i
									+ "PressedImage"), properties
							.getChildText("Option" + i + "DefaultImage"),
					properties.getChildText("Option" + i + "DefaultPath")
							+ properties.getChildText("Option" + i
									+ "DefaultImage"), properties
							.getChildText("Option" + i + "HighLightImage"),
					properties.getChildText("Option" + i + "HighLightPath")
							+ properties.getChildText("Option" + i
									+ "HighLightImage"));

			// add the action of the button, when pressed
			button.addButtonPressedListener(new IButtonPressedListener(){

				public void buttonPressed(ButtonPressedEvent arg0) {
					// TODO Auto-generated method stub

					//System.out.println("Menu button");
					//works fine

					//gets from the menu the action that is going to use
					
				}
				
			});
		
			//add the button created to the menu
			menu.addWidget(button);
		}
	
		return menu;
	}

	public Window getConversationBox() {
		return conversationBox;
	}

	public Window getPrimaryMenu() {
		return primaryMenu;
	}

	public Window getSecondaryMenu() {
		return secondaryMenu;
	}
	
	public Window getThirdMenu() {
		return thirdMenu;
	}

	public Window getBattleMenu() {
		return battleMenu;
	}

	public Window getTargetMenu() {
		return targetMenu;
	}	
	
	public static String generateRandomString()
	{
		int length = (int) (Math.random() * 10) + 5;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++)
			sb.append(Alphabet.getDefaultAlphabet().getAlphabet()[(int) (Math.random() * Alphabet.getDefaultAlphabet()
					.getAlphabet().length)]);

		return sb.toString();
	}
	
	class MyTableModel implements ITableModel
	{
		String[][] matrix = null;

		public MyTableModel()
		{
			matrix = new String[10 + (int) (Math.random() * 20d)][4];
		}

		public void update()
		{
			matrix = new String[10 + (int) (Math.random() * 20d)][4];
		}

		public String getColumnName(int columnIndex)
		{
			return "column" + columnIndex;
		}

		public int getColumnCount()
		{
			return matrix[0].length;
		}

		public Object getValue(int row, int column)
		{
			if (matrix[row][column] == null)
			{
				if (column == 0) matrix[row][column] = "" + row;
				else if (column == 2) matrix[row][column] = generateRandomString();
				else matrix[row][column] = "" + (int) (Math.random() * 100);
				;
			}

			return matrix[row][column];
		}

		public int getRowCount()
		{
			return matrix.length;
		}

		public void clear()
		{
			// TODO implement
		}

		public Object getValue(int row)
		{
			// not used in this example
			return null;
		}
	}
		
}
 
