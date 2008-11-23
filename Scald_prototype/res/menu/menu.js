function menu(object){

	//get the one to be used
	var used = object;
	
	//identify using the state
	
	//Battle menu
	if(Game.battle){
		//find the button that was pressed
		//Attack button
		if(used.equals("Attack"))
			battleAttack();
		//Defend button
		if(used.equals("Defend"))
			battleDefend();
		//Magic button
		if(used.equals("Magic"))
			battleMagic();
		//Item button
		if(used.equals("Item"))
			battleItem();
		//Run button
		if(used.equals("Run"))
			battleRun();
	}
	
	//Main menu	
	if(Game.menu){
		if(used.equals("Inventory"))
			mainInventory();
		if(used.equals("Magic"))
			mainMagic();
		if(used.equals("Status"))
			mainStatus();
		if(used.equals("Skill"))
			mainSkill();
		if(used.equals("Equip"))
			mainEquip();
		if(used.equals("Order"))
			mainOrder();
		if(used.equals("Save"))
			mainSave();
		if(used.equals("Load"))
			mainLoad();
	}
		
	return;
}

function mainInventory(){
	
	//create the menu used for the inventory
	RPGSystem.getMenu().createInventoryMenu(250,250,300,500,30);
	
	//show that menu
	FactoryManager.getGraphicsManager().getGui().showSecondaryMenu();
}

function mainMagic(){
	
	//create the menu to select a character
	RPGSystem.getMenu().createStatusGroupSelect(250,250,300,500,100,"Magic");
	
	//show that menu
	FactoryManager.getGraphicsManager().getGui().showSecondaryMenu();

}

function mainStatus(){
	
	//create the menu to select a character
	RPGSystem.getMenu().createStatusGroupSelect(250,250,300,500,100,"Status");
	
	//show that menu
	FactoryManager.getGraphicsManager().getGui().showSecondaryMenu();
}

function mainSkill(){
	
	//create the menu to select a character
	RPGSystem.getMenu().createStatusGroupSelect(250,250,300,500,100,"Skill");
	
	//show that menu
	FactoryManager.getGraphicsManager().getGui().showSecondaryMenu();
}

function mainEquip(){
	
	//create the menu to select a character
	RPGSystem.getMenu().createStatusGroupSelect(250,250,300,500,100,"Equip");
	
	//show that menu
	FactoryManager.getGraphicsManager().getGui().showSecondaryMenu();
}

function mainOrder(){
//formation
}

function mainSave(){
//save game

//show the menu with the slots

}

function mainLoad(){
//load game

//show the menu with the slots
}

function battleAttack(){	
	//needs to get the user, first the position
	var temp = RPGSystem.getBattleSystem().getActionStrategy().getCharacterActs();
	//gets the name
	var name = PlayerGroup.getCharacter(temp).getName();

	//hide the battle menu
	FactoryManager.getGraphicsManager().getGui().closeBattleMenu();

	//create the target menu
	RPGSystem.getMenu().createTargetMenu(250,250,300,500,30,
						"Attack","Single","none",name);
						
	//show the target menu
	FactoryManager.getGraphicsManager().getGui().showTargetMenu();
	
}

function battleDefend(){
	//needs to get the user, first the position
	var temp = RPGSystem.getBattleSystem().getActionStrategy().getCharacterActs();
	//gets the name
	var name = PlayerGroup.getCharacter(temp).getName();

	//hide the battle menu
	FactoryManager.getGraphicsManager().getGui().closeBattleMenu();
	
	//create the action
	BattleSystem.createAction("Defend",true,temp,true,temp,"none",0);
						
	//say that the action was created
	BattleSystem.getActionStrategy().setOption(3);	
	
}

function battleMagic(){
	//needs to get the user, first the position
	var temp = RPGSystem.getBattleSystem().getActionStrategy().getCharacterActs();
	//gets the name
	var name = PlayerGroup.getCharacter(temp).getName();

	//hide the battle menu
	FactoryManager.getGraphicsManager().getGui().closeBattleMenu();
	
	//create the target menu
	RPGSystem.getMenu().createMagicMenu(250,250,300,500,30,name);
						
	//show the target menu
	FactoryManager.getGraphicsManager().getGui().showThirdMenu();
	
}

function battleItem(){
	//needs to get the user, first the position
	var temp = RPGSystem.getBattleSystem().getActionStrategy().getCharacterActs();
	//gets the name
	var name = PlayerGroup.getCharacter(temp).getName();

	//hide the battle menu
	FactoryManager.getGraphicsManager().getGui().closeBattleMenu();

	//create the target menu
	RPGSystem.getMenu().createInventoryMenu(250,250,300,500,30);
	
	//show that menu
	FactoryManager.getGraphicsManager().getGui().showSecondaryMenu();
	
}

function battleRun(){
	//needs to get the user, first the position
	var temp = RPGSystem.getBattleSystem().getActionStrategy().getCharacterActs();
	//gets the name
	var name = PlayerGroup.getCharacter(temp).getName();

	//hide the battle menu
	FactoryManager.getGraphicsManager().getGui().closeBattleMenu();
	
	//create the action
	BattleSystem.createAction("Run",true,temp,true,temp,"none",0);
						
	//say that the action was created
	BattleSystem.getActionStrategy().setOption(3);	
	
}

