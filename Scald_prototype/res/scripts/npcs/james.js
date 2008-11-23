function talk(object){

	//get the current talk
	var current = Number(object[0]);
	
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
	return;
}

function talk1(object){

	//get the part of the talk
	var current = Number(object[1]);

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
	var current = Number(object[1]);
	
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

function move(object){

	//randomize the direction, 5
	
	//these values are used by the system to move
	// 0 - don´t move - 60%
	// 1 - up - 10%
	// 2 - down - 10%
	// 3 - left - 10%
	// 4 - right - 10%
	var direction = Random.nextInt(100) + 1;
	
	//the test to choose the direction, use the directions according to
	//the movement permitted to the npc
	if(direction < 60){
		direction = 0;
	}else if(direction < 70){
		direction = 1;
	}else if(direction < 80){
		direction = 2;
	}else if(direction < 90){
		direction = 3;
	}else if(direction < 100){
		direction = 4;
	}		

	//always walk the same amount
	var distance = 1.0;
	
	//return to the system the values of the npc, 
	//the direction to move, the distance and the animation used
	return direction + ";" + distance + ";" + "Walk";
}