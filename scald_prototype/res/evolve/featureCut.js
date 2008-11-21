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
		var mult = (Random.nextInt(10) + 1);
		var hp = 10 * mult;
	
		//evolve the HitPoints
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("HP",hp);
		
		//the rule used to gain Magic Points
		mult = (Random.nextInt(10) + 1);
		var mp = 5 * mult;
		
		//evolve the MagicPoints
		PlayerGroup.getCharacter(position).getCharacterClass().evolveFeature("MP",mp);
	
	}
	
	//go back
	return;
}