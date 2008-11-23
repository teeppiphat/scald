package framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * Class used by those that need to load a table
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class LoadTable {

	//used to store the levels, experience needed
	protected int[] levels;
	
	//used to have the max level
	protected int maxLevel;
	
	public LoadTable(){
	}
	
	public void setMaxLevel(int maxLevel){
		//get the max level
		this.maxLevel = maxLevel;		
	}
	
	public void setLevels(){
		//set the size of the array of levels
		levels = new int[this.maxLevel];
	}

	public void loadTable(String path,String file){
		
		//initialize the array responsible for the levels
		setLevels();
		
		//used to load the file
		URL url = null;
		
		//get the url
		try {
			url = new URL("file:" + path + file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//load the file
		File archive = new File(url.getPath());
		
		//create the buffer used for reading the file
		BufferedReader read = null;
		try {
			read = new BufferedReader(new FileReader(archive));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//String used to receive what is read in the archive
		String text = "";

		try {

			//used to load the levels and put them in the array
			int level = 0;
			
			//while the archive has something it will keep reading or
			//until it arrives at the max level
			while (((text = read.readLine()) != null) && (level <= maxLevel)) {
				//pass the String to the class StringTokenizer to get the values
				//in an easier way, the string that will be divided, the delimiter
				//and if counts the delimiter as part of the string
				StringTokenizer dividing = new StringTokenizer(text,",",false);

				//used to read an entire line
				while(dividing.hasMoreTokens()){
					//now use the string tokenizer to get the values and put them
					//in the array
					levels[level] = Integer.parseInt(dividing.nextToken());

					//atualiza para a próxima posição do vetor
					level++;
				}
			}

			//close the read
			read.close();

		} catch (IOException e) {
			System.out.println("Error in the input/output of data");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//test if the file was read
		//printLevels();
	}
	
	public void printLevels(){
		for(int i = 0; i < levels.length; i++){
			System.out.println("Level " + (i+1) + " : " + levels[i]);
		}
	}

	public int[] getLevels() {
		return levels;
	}

	public void setLevels(int[] levels) {
		this.levels = levels;
	}

	public int getMaxLevel() {
		return maxLevel;
	}
		
}
