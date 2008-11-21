function scene(object){

	var current = Number(Objects[0]);
	//test which part of the cut-scene must execute			
	switch (current)
	{		
		case 0:
			return "effect;sound;Wolf;false;true";
			break;		
		case 1:
			//will move someone
			//format to specify
			// move;direction;distance;speed;animation;character;wait(need to wait the character stop or not)
			// can put many characters moving together doing it this way
			// move;direction;distance;speed;animation;character;move;direction;distance;speed;animation;character;wait
			// keep going
			
			//these values are used by the system to move
			// 0 - don´t moves
			// 1 - up
			// 2 - down
			// 3 - left
			// 4 - right
			// 5 - up left
			// 6 - up-right
			// 7 - down - left
			// 8 - down - right

			return "move;4;1.0;0.05;Block;Character;true";
			break;
		
		case 2:
			//will talk
			//format to specify			
			return "effect;sound;Wolf;false;true";
			break;
		
		case 3:
			//will use an effect
			return "move;4;2.0;0.05;Run;Character;true";
//			return "talk;" + talk();
//			return "effect;particle;Fire;false";
			break;
		
		case 4:
			//will use another effect
			return "effect;sound;Wolf;false;true";
		
		case 5:
			//will use another effect
//			return "effect;remove;Character";
			return "talk;" + talk();
				
		case 6:
			//will use another effect
			//return "effect;sound;Footsteps;false;false";
			return "effect;music;Melody;false;true";
		
		case 7:
			//will use another effect
			//return "effect;add;Character";
			
			//will need the name, model, type, x,y,z,rotx,roty,rotz,scale,material,density,slide,bounce,event,pathEvent
			return "effect;add;Character;Lynder;Object;2.0;-1.0;0.0;0.0;1.0;0.0;0.05;Default;0.0;0.0;0.0;none;none;"
		
		case 8:
			//will use another effect
			return "effect;video;opening";
		
		case 9:
			//will tell the program that is finished
			return "end";
			break;			
		
		default:
			break;	
				
	}

	//must move someone who is in the scene, must specify everything needed	
	//move(direction,distance,animation,character);
	
	//talk, must specify the part of the talk that is
	//talk(part);

	//quest, depends on the talk

	//effects, like particles, music and remove character
	//effects(wanted);
		
	//return the information
	return;
}

function talk(){

	//get the current talk
	var current = Number(Objects[1]);
	
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

function talk1(){
	//get the part of the talk
	var current = Number(Objects[2]);

	//try to validate the quest, using the quest name
	
	
	//try to begin a quest
	if(RPGSystem.getQuests().beginQuest("Another")){
		println("Able");
	}else{
		println("Not able");
	}
	
	RPGSystem.getQuests().validateQuest("Another");
	
	//ask about a quest, using its name
	if(RPGSystem.getQuests().completeQuest("Another") == false){
		println("Not done");
	}else{
		println("Completed");
	}

	//work according to the part of the talk	
	switch(current){
		case 0:
			return 1 + ";" + "Hello my friends";
			break;
		case 1:
			return 2 + ";" + "How are you doing?";
			break;
		case 2:
			return 3 + ";" + "It´s a beatiful day!";
			break;
		case 3:
			return 4 + ";" + "Again here \n still";
			break;			
		case 4:
			return 5 + ";" + "so? what \n do you need?";
			break;
		case 5:
			return 0 + ";" + "Are you sure?";
			break;
		default:
			break;	
	}
			
	//to make sure that doesn´t come here
	return 0 + ";" + "Isn´t supposed to be here";
}

function talk2(){

	//get the part of the talk
	var current = Number(Objects[2]);
		
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