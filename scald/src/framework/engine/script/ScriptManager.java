package framework.engine.script;

import org.jdom.Document;

/**
 * The manager of the scripts, that can read or write
 * them when needed
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class ScriptManager {

	/**
	 * Responsible for reading the script
	 */
	private ReadScript readScript;

	/**
	 * Responsible for writing the script
	 */
	private WriteScript writeScript;

	/**
	 * Constructor of the class, it initializes
	 * it´s objects
	 */
	public ScriptManager() {

		//initializing the objects
		readScript = new ReadScript();
		writeScript = new WriteScript();
	}

	public ReadScript getReadScript() {
		return readScript;
	}

	public WriteScript getWriteScript() {
		return writeScript;
	}
	
	public boolean existElement(String file, String element){
		//look for the element in the save file	
		if (readScript.getElementXML(file, element) != null)
			//exist
			return true;
		else
			//doesn´t exist
			return false;			
	}
		
	public void writeSave(String path, String file, int number) {

		//get the file
		Document doc = readScript.getDocument(path + file);

		//test if the file exists
		if (doc != null) {
			//the file exists, pass the doc, the number used for the file
			
			//test if the slot already exist
			if (existElement(path + file, "slot" + number)){
				//element exist

				// remove the content
				doc.getRootElement().removeChild("slot"+number);
				
				// put the content on the doc
				writeScript.refreshSave(doc, number, path, file);
				
			}else{
				//element doesn´t exist
				writeScript.refreshSave(doc, number, path, file);
			}
			
		} else {
			//the file doesn´t exist then create it and get it
			doc = writeScript.createSave(null, path, file);
			//after creating it, create the slot and add it to the file, first slot
			writeScript.refreshSave(doc, 1, path, file);

		}		

	}

}
