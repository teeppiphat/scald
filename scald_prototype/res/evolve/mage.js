function evolve(object){

	//var group = PlayerGroup;
	
	//get the identifier
	var identifier = object;
	
	//find the position of the character wanted
	var position = PlayerGroup.findCharacterPosition(identifier);
	
	//if was found the character wanted, then evolve,
	//in this case will evolve the status of the class
	if(position != -1){
	
		//the rule used to gain Hit Points
		var mult = (Random.nextInt(3) + 1);
		var hp = 5 * mult;
	
		//evolve the HitPoints
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("HP",hp);
		
		//the rule used to gain Magic Points
		mult = (Random.nextInt(4) + 1);
		var mp = 10 * mult;
		
		//evolve the MagicPoints
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("MP",mp);
		
		// basical attributes
		//the rule used to gain Strenght		
		var str = (Random.nextInt(3) + 1) + 1;
		//the rule used to gain Agility
		var agi = (Random.nextInt(4) + 1) + 2;		
		//the rule used to gain Intelligence
		var intel = (Random.nextInt(5) + 1) + 3;		
		//the rule used to gain Constitution
		var con = (Random.nextInt(3) + 1) + 1;	
		//the rule used to gain Wisdom
		var wis = (Random.nextInt(5) + 1) + 3;	
		
		// depend of the basics
		//the rule used to gain AttackPower
		var atk = str * 1.25;
		//the rule used to gain MagicPower
		var mag = intel * 1.75;		
		//the rule used to gain PhysicalResistence
		var phy = con * 1.25;		
		//the rule used to gain MagicalResistence				
		var magr = wis * 1.75;		
		//the rule used to gain Speed
		var spe = agi * 1.5;		
				
		//evolve the HitPoints
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("HP",hp);		
		//evolve the MagicPoints
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("MP",mp);
		//evolve the Strenght
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("Str",str);
		//evolve the Agility
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("Agi",agi);
		//evolve the Intelligence
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("Int",intel);		
		//evolve the Constitution
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("Con",con);
		//evolve the Wisdom
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("Wis",wis);		
		
		//evolve the AttackPower
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("AtkPow",atk);		
		//evolve the MagicPower
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("MagPow",mag);		
		//evolve the PhysicalResistence
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("PhyDef",phy);		
		//evolve the MagicalResistence
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("MagDef",magr);		
		//evolve the Speed
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("Speed",spe);		
				
		
	}
	
	//go back
	return;
}

function initialValue(object){

	//get the identifier
	var identifier = object;
	
	//find the position of the character wanted
	var position = PlayerGroup.findCharacterPosition(identifier);
	
	//if was found the character wanted, then evolve,
	//in this case will evolve the status of the class
	if(position != -1){
				
		//set the Hit Points
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("HP",50);
		//set the current Hit Points
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusCurrent("HP",50);
		
		//set the MagicPoints
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("MP",100);
		//set the current Magic Points
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusCurrent("MP",100);

		//set the Strenght
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("Str",10);
		//set the Agility
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("Agi",15);
		//set the Intelligence
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("Int",20);
		//set the Constitution
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("Con",10);
		//set the Wisdom
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("Wis",20);
		//set the AttackPower
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("AtkPow",12);
		//set the MagicPower
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("MagPow",35);
		//set the PhysicalResistence
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("PhyDef",12);
		//set the MagicalResistence
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("MagPow",35);
		//set the Speed
		PlayerGroup.getCharacter(position).getCharacterClass().setStatusValue("Speed",22);
	}
	
	//go back
	return;

}