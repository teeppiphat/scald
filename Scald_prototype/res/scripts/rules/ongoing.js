function calculatePercentage(object){

	//used to get the value to calculate
	var temp;

	//must identify from where
	if(FeatureAttribute[4].getCompute().equals("Maximum")){
		//get the values necessary
		if(FeatureAttribute[2]){
			//player
			
			
		}else{
			//enemy
		}
		
		
	}else if(FeatureAttribute[4].getCompute().equals("Current")){
		//calculate

	}else if(FeatureAttribute[4].getCompute().equals("Value")){
		//calculate

	}else if(FeatureAttribute[4].getCompute().equals("Cost")){
		//calculate

	}else if(FeatureAttribute[4].getCompute().equals("Time")){
		//calculate

	}
	
	
	
	//

}

function percentage(object){
	var a = object[0];
	var b = object[1];

	return ((a * b) / 100);
}

function physicalDamage(object){

// needs - user
// user strenght
// user attack power
// needs - target
// target constitution
// target defense power
// Feature has a value to add to the damage

	var strenght;
	var attack;
	var constitution;
	var defense;
	
	//the attack	
	if(FeatureAttribute[0] == true){		
		//player
		strenght = PlayerGroup.getCharacter(FeatureAttribute[1]).getCharacterClass().getStatusValue("Str");
		attack = PlayerGroup.getCharacter(FeatureAttribute[1]).getCharacterClass().getStatusValue("AtkPow");
	}else{		
		//enemy
		strenght = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[1]).getStatusValue("Str");
		attack = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[1]).getStatusValue("AtkPow");
	}
	
	//the defense
	if(FeatureAttribute[2] == true){	
		//player		
		constitution = PlayerGroup.getCharacter(FeatureAttribute[3]).getCharacterClass().getStatusValue("Con");
		defense = PlayerGroup.getCharacter(FeatureAttribute[3]).getCharacterClass().getStatusValue("PhyDef");
	}else{	
		//enemy
		constitution = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[3]).getStatusValue("Con");
		defense = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[3]).getStatusValue("PhyDef");
	}
		
	//calculate, use the additional value if exists
	if(FeatureAttribute[4] != null){
		//test if the additional value has influence of percentage
		if(FeatureAttribute[4].isCalculate() == true){
			//find the attributes interested in and return the percentage calculated
			// TO BE IMPLEMENTED						
		}else{
			//just use the value
			return ((strenght + attack) * 1.75 - FeatureAttribute[4].getValue()) - ((constitution + defense) * 1.25);			
		}
	}else{
		//its probably a normal attack	
		return ((strenght + attack) * 1.75) - ((constitution + defense) * 1.25);		
	}
}

function physicalSupport(object){

// if its healing or treating a status there´s no resistence
if (object.get(2) != null && object.get(2).equals("Heal")){
	
	//what status will be used (?)
	
}

// if its making a status go down then is there a resistence (?)

	return;

}

function magicalDamage(object){

// needs - user
// user inteligence (?)
// user magic power (?)
// needs - target
// target wisdom
// target magical defense
// Feature has a value to add to the damage

	var intelligence;
	var magic;
	var wisdom;
	var resistence;

	//the attack	
	if(FeatureAttribute[0] == true){
		//player
		intelligence = PlayerGroup.getCharacter(FeatureAttribute[1]).getCharacterClass().getStatusValue("Int");
		magic = PlayerGroup.getCharacter(FeatureAttribute[1]).getCharacterClass().getStatusValue("MagPow");
	}else{
		//enemy
		intelligence = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[1]).getStatusValue("Int");
		magic = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[1]).getStatusValue("MagPow");
	}

	//the defense
	if(FeatureAttribute[2] == true){
		//player
		wisdom = PlayerGroup.getCharacter(FeatureAttribute[3]).getCharacterClass().getStatusValue("Wis");
		resistence = PlayerGroup.getCharacter(FeatureAttribute[3]).getCharacterClass().getStatusValue("MagDef");
	}else{
		//enemy
		wisdom = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[3]).getStatusValue("Wis");
		resistence = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[3]).getStatusValue("MagDef");
	}
		
	//calculate, use the additional value if exists
	if(FeatureAttribute[4] != null){
		//test if the additional value has influence of percentage
		if(FeatureAttribute[4].isCalculate() == true){
			//find the attributes interested in and return the percentage calculated
			// TO BE IMPLEMENTED						
		}else{
			//just use the value
			return ((intelligence + magic) * 1.75 - FeatureAttribute[4].getValue()) - ((wisdom + resistence) * 1.25);			
		}
	}else{
		//its probably a normal attack	
		return ((intelligence + magic) * 1.75) - ((wisdom + resistence) * 1.25);		
	}

	return;
}

function magicalSupport(object){

// if its healing or treating a status there´s no resistance
if (object.get(2) != null && object.get(2).equals("Heal")){
	
	//get the intelligence and the magic power of the user
	var intelligence;
	var magic;
	
	//the user
	if(FeatureAttribute[0] == true){
		//player
		intelligence = PlayerGroup.getCharacter(FeatureAttribute[1]).getCharacterClass().getStatusValue("Int");
		magic = PlayerGroup.getCharacter(FeatureAttribute[1]).getCharacterClass().getStatusValue("MagPow");
	}else{
		//enemy
		intelligence = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[1]).getStatusValue("Int");
		magic = BattleSystem.getEnemyGroup()[0].getEnemy(FeatureAttribute[1]).getStatusValue("MagPow");
	}
	
	//test if there´s a value
	if(FeatureAttribute[4] != null){
		//test if use percentage
		if(FeatureAttribute[4].isCalculate() == true){
			// if use percentage
			// TO BE IMPLEMENTED
			
		}else{
			return ((intelligence + magic) * 1.75 + FeatureAttribute[4].getValue());
		}			
	}else{
		return ((intelligence + magic) * 1.75);	
	}
	
}

// if its making a status go down then is there a resistance (?)

	return;
}

function itemDamage(object){
//TO BE IMPLEMENTED
return;
}

function itemSupport(object){
// TO BE IMPLEMENTED

return;
}

function calculateFeatureUse(object){

	//test what needs to calculate, according to the  specific type of the Feature

	//receive the user, target and what will be used 
	
	//get the first type for the way used to calculate, physical, magical or item
	
	//them the second will be directly for saying if is damage or support, healing or treating(cure one status) or
	// the type of the magic or attack, like fire, water, wind, element, if has one
	
	//and the third if something was left out
	
	//them will resolve what will use to calculate what needs
	
	//the attributes received are always in this order:
	// 0 - boolean userGroup
	// 1 - integer userPosition
	// 2 - boolean targetGroup
	// 3 - integer targetPosition
	// 4 - ChangeFeature change
	// 5 - Feature feature
		
//	println("Testing the mapping");
//	println(FeatureAttribute[0]);
//	println(FeatureAttribute[1]);
//	println(FeatureAttribute[2]);
//	println(FeatureAttribute[3]);
//	println(FeatureAttribute[4]);
//	println(FeatureAttribute[5].getName());
	
	//an array used to get the specific type of the Feature
	var array;
	
	//first get the type of the feature, skill, magic or equipament
	if(FeatureAttribute[5].getType().equals("Skill")){
		//get the specific type according to its type
		array = FeatureAttribute[5].getSkill().getFeature().getTypeOf();		
	}else if(FeatureAttribute[5].getType().equals("Magic")){
		//get the specific type according to its type
		array = FeatureAttribute[5].getMagic().getFeature().getTypeOf();	
	}else if(FeatureAttribute[5].getType().equals("Equipament")){
		//get the specific type according to its type
		array = FeatureAttribute[5].getWeapon().getFeature().getTypeOf();	
	}
	
	//test the size and treat according to it
		
	//the order will always be:
	// 0 - Physical/Magical/Item
	// 1 - Damage/Support
	// 2 - Type of Damage/Support (The only one for now is Life)(if has, not complete implemented)
		
	//first part is the type
	if(array.get(0).equals("Physical")){
		//treat according to what does
		if(array.get(1).equals("Damage")){
		//must return the value that will be calculated
		return -physicalDamage(array);
		}else if(array.get(1).equals("Support")){
		//must return the value of the help
		return physicalSupport(array);
		}		
	}else if(array.get(0).equals("Magical")){
		//treat according to what does
		if(array.get(1).equals("Damage")){
		//must return the value that will be calculated
		return -magicalDamage(array);
		}else if(array.get(1).equals("Support")){
		//must return the value that will be calculated
		return magicalSupport(array);
		}
	}else if(array.get(0).equals("Item")){
		//treat according to what does
		if(array.get(1).equals("Damage")){
		//must return the value that will be calculated
		return -itemDamage(array);
		}else if(array.get(1).equals("Support")){
		//must return the value that will calculated
		return itemSupport(array);
		}
	}
	
	return;	
}

function battleOrder(object){

	return;
}


function testBattle(object){
	//tells to test both conditions
	var win = victory();
	var lose = defeat();
	//return the conditions
	return win + ";" + lose;
}

function victory(){

	//test if the enemy group is dead

	//will test all the enemy group
	for(var i = 0; i < BattleSystem.getEnemyGroup()[0].getEnemy().length; i++){	
		if(BattleSystem.getEnemyGroup()[0].getEnemy(i).getStatusCurrent("HP") != 0){
			//one enemy is still alive			
			return false;
		}	
	}
	
	//no enemy is alive
	return true;
}

function defeat(){

	//temporary
	var size = PlayerGroup.getQuantity() + 1;

	//test if the player group is dead, the group in the battle
	
	//will test according to the quantity of players in the battle, first must test the quantity
	if(size > FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle()){
		size = FactoryManager.getFactoryManager().getRulesManager().getRulesSet().getPlayersInBattle();
	}
		
	//will test all the player group
	for(var i = 0; i < size; i++){
		if(PlayerGroup.getCharacter(i).getCharacterClass().getStatusCurrent("HP") != 0){
			//one is still alive
			return false;
		}		
	}
	
	// everyone is dead
	return true;
}
