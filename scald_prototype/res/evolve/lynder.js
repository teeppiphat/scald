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
		var hp = 10 * mult;
	
		//evolve the HitPoints
		PlayerGroup.getCharacter(position).evolveFeature("HP",hp);
		
		//the rule used to gain Magic Points
		mult = (Random.nextInt(2) + 1);
		var mp = 5 * mult;
		
		//evolve the MagicPoints
		PlayerGroup.getCharacter(position).evolveFeature("MP",mp);
		println("aqui");
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
		PlayerGroup.getCharacter(position).setStatusValue("HP",100);		
		//set the MagicPoints
		PlayerGroup.getCharacter(position).setStatusValue("MP",20);
	}
	
	//go back
	return;

}