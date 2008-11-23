function main(object){

	// 0 - the name
	// 1 - the type
	// 2 - kind of event - 0 accept button/ 1 collision
	
	//get the kind of event
	var event = Number(GameEvent[2]);
	
	//identify what must be done
	switch (event)
	{		
	case 0:
		// if was accept button
		//println("Trying to talk");
		//println(GameEvent[0]);
		//println(GameEvent[1]);
		//println(GameEvent[2]);
		//println(GameEvent[3]);
		//println(GameEvent[4]);
		//return "talk;" + talk();
		return "shop;ItemShop";
		break;
	case 1:
		//if was collision and has anything to do
		return "battle;Goblin;Goblin;Goblin"	
		//println("Collision happened");
		//println(GameEvent[0]);
		//println(GameEvent[1]);
		break;	
	}

	//go back
	return;
}

// if battle the line will be formed like this - battle;name of the enemy in the enemies list;
// if talk will be like the cut-scene
// if event will be like this - event; type of the event / TODO

function talk(object){

	//get the current talk
	var current = Number(GameEvent[3]);
		
	//test which part must call
	switch(current){
	case 0:
		return 1 + ";" + talk1();
		break;
	case 1:
		return 0 + ";" + talk2();
		break;
	default:
		break;
	}

	//return nothing
	return "not good";
}

function talk1(object){

	//get the part of the talk
	var current = Number(GameEvent[4]);

	//work according to the part of the talk	
	switch(current){
		case 0:
			return 1 + ";" + "Hello my friends";
			break;
		case 1:
			return 2 + ";" + "How are you doing?";
			break;
		case 2:
			return 0 + ";" + "It´s a beatiful day!";
			break;
		default:
			break;	
	}
			
	//to make sure that doesn´t come here
	return 0 + ";" + "Isn´t supposed to be here";
}

function talk2(object){

	//get the part of the talk
	var current = Number(GameEvent[4]);
	
	//work according to the part of the talk	
	switch(current){
		case 0:
			return 1 + ";" + "Goodbye my friends";
			break;
		case 1:
			return 0 + ";" + "Have a safe trip!";
			break;		
		default:
			break;	
	}
	
	//to make sure that doesn´t come here
	return 0 + ";" + "Isn´t supposed to be here";	
}