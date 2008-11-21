package framework.engine.script;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.jme.math.Vector3f;

import framework.FactoryManager;

/**
 * Responsible for being able of writing a script
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class WriteScript {

	/**
	 * Constructor of the class
	 */
	public WriteScript() {

	}

	public Element writeSlotXML() {

		//create the elements needed to save the information
		Element slot = new Element("Slot");
		Element map = new Element("Map");
		Element character = new Element("Character");
		Element money = new Element("Money");
		Element posX = new Element("PosX");
		Element posY = new Element("PosY");
		Element posZ = new Element("PosZ");

		//get the position of the player
		Vector3f position = FactoryManager.getFactoryManager()
				.getResourceManager().getObjectManager().getCharacter()
				.getModelPosition();

		//add the content to the elements
		money.addContent(String.valueOf(300));
		posX.addContent(String.valueOf(position.x));
		posY.addContent(String.valueOf(position.y));
		posZ.addContent(String.valueOf(position.z));

		//put the information of the character inside of him
		character.addContent(money);
		character.addContent(posX);
		character.addContent(posY);
		character.addContent(posZ);

		//put other information directly outside, in the root
		slot.addContent(map);

		//put the character at the root
		slot.addContent(character);

		//return the slot created
		return slot;

	}

	public void createSlotXML(String path, int number) {

		//calls for the method to create the slot
		Element slot = writeSlotXML();

		//Creating the document  
		Document doc = new Document();

		//setting the root element
		doc.setRootElement(slot);

		//saving the slot
		URL url = null;
		try {
			//where it will be saved, with the number of the slot
			url = new URL("file:" + path + "slot" + number + ".xml");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//create the stream to save the information in a file
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(url.getPath()), "UTF8"));

			//create the object that will be responsible for the format
			//of the file
			Format test;
			//create the object used to write
			XMLOutputter xout = new XMLOutputter();
			//get the format
			test = xout.getFormat();
			//set to "jump" a line
			test.setIndent(" ");
			//make even the empty elements to have the tag to close them
			test.setExpandEmptyElements(true);
			//set the codification used
			test.setEncoding("ISO-8859-1");
			//set the format back
			xout.setFormat(test);
			//save the file
			xout.output(doc, out);

			//xout.output(doc, System.out);
			//System.out.println("XML criado com sucesso!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Element writeSlotSave(int number) {

		//create the root node
		Element saveSlot = new Element("Slot " + number);

		//create the elements that it has
		Element path = new Element("Path");
		Element file = new Element("File");

		//put the information on them
		path.addContent("path");
		file.addContent("file");

		//put the information on the slot
		saveSlot.addContent(path);
		saveSlot.addContent(file);

		//return the slot that was created
		return saveSlot;

	}

	public void refreshSave(Document doc, int number, String path, String file) {

		//must create the slot
		Element saveSlot = writeSlotSave(number);

		//get the root node
		Element root = doc.getRootElement();

		//must put the new slot in the save.xml
		root.addContent(saveSlot);

		//save the file save.xml, overwrite it, must get the place
		createSave(root, path, file);

		//create the file slotN.xml
		createSlotXML(path, number);

	}

	// method used to write the save file
	public Document createSave(Element root, String path, String file) {

		// Creating the document
		Document doc = new Document();

		if (root == null) {
			// create the root element
			Element save = new Element("Save");
			// setting the root element
			doc.setRootElement(save);
		} else {
			doc.setRootElement(root);
		}

		// saving the file
		URL url = null;
		try {
			// where it will be saved
			url = new URL("file:" + path + file);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// create the stream to save the information in a file
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(url.getPath()), "UTF8"));

			// create the object that will be responsible for the format
			// of the file
			Format test;
			// create the object used to write
			XMLOutputter xout = new XMLOutputter();
			// get the format
			test = xout.getFormat();
			// set to "jump" a line
			test.setIndent(" ");
			// make even the empty elements to have the tag to close them
			test.setExpandEmptyElements(true);
			// set the codification used
			test.setEncoding("ISO-8859-1");
			// set the format back
			xout.setFormat(test);
			// save the file
			xout.output(doc, out);

			// xout.output(doc, System.out);
			// System.out.println("XML criado com sucesso!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;

	}

}
