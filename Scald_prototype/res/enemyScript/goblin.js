function initialValue(object){

	//get the identifier
	var identifier = object;
	
	//find the position of the character wanted
	var position = RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].findEnemyPosition(identifier);
	
	//if was found the character wanted, then evolve,
	//in this case will evolve the status of the class
	if(position != -1){
				
		//set the Hit Points
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("HP",50);
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusCurrent("HP",50);
		
		//set the Magic Points
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("MP",10);
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusCurrent("MP",10);

		//set the Strenght
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("Str",16);
		//set the Agility
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("Agi",13);
		//set the Intelligence
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("Int",5);
		//set the Constitution
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("Con",14);
		//set the Wisdom
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("Wis",5);
				
		//set the AttackPower 1.6
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("AtkPow",25);
		//set the MagicPower 1.2
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("MagPow",6);
		//set the PhysicalResistence 1.4
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("PhyDef",20);
		//set the MagicalResistence 1.2
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("MagDef",6);
		//set the Speed 1.3
		RPGSystem.getRPGSystem().getBattleSystem().getEnemyGroup()[0].getEnemy(position).setStatusValue("Speed",17);
		
	}
	
	//go back
	return;

}