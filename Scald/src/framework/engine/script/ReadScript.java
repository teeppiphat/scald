package framework.engine.script;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Responsible for being able of reading a script
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class ReadScript {
 	
	ScriptEngineManager manager;
	
	ScriptEngine engine;
	
	/**
	 * Constructor of the class
	 */
	public ReadScript(){
		manager = new ScriptEngineManager();
	}
	
	
	//Methods used to read a JavaScript
		
	public void createJavaScriptEngine(){
		engine = manager.getEngineByExtension("js");		
	}
	
	public void loadScript(String path,String file){
		// used to read the script
		FileReader reader;
		
		//create an URL to get the script
		URL url;
		
		//used to load and put the script in the engine
		try {
			//create the url
			url = new URL("file:" + path + file);			
			//put the file in a stream
			reader = new FileReader(url.getPath());			
			//put the file in the engine
			engine.eval(reader);			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//System.out.println("Not found");
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			//System.out.println("Script");
			e.printStackTrace();
		}
	}
	
	public String getChat(String function){
		//used to call the function needed
		Invocable invocable = (Invocable)engine;
		
		//create an object to pass
		Object object = null;
		
		try {
			//use the function
			object = invocable.invokeFunction(function, object);			
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return the object
		return (String)object;
	}
	
	public String getChat(String function,int test){
		//used to call the function needed
		Invocable invocable = (Invocable)engine;
		
		//create an object to pass
		Object object = null;
		
		try {
			//use the function
			object = invocable.invokeFunction(function, test);			
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return the object
		return (String)object;
	}
	
	
	public void executeScript(String function){
		//used to call the function needed
		Invocable invocable = (Invocable)engine;
		
		//create an object to pass
		Object object = null;
		
		try {
			//use the function
			object = invocable.invokeFunction(function, object);
			System.out.println(object);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void executeScript(String function, String identifier){
		//used to call the function needed
		Invocable invocable = (Invocable)engine;
				
		try {
			//use the function
			invocable.invokeFunction(function, identifier);			
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Object[] executingScript(String function, Object[] objects){
		//used to call the function needed
		Invocable invocable = (Invocable)engine;
		
		//create the object used to pass
		Object obj = null;
		
		try {
			//use the function
			obj = invocable.invokeFunction(function, objects);
			
			//System.out.println(object);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//transform the object received to the desired output
		
		//temporary object
		Object objs[] = null;
		
		//not null
		if(obj != null){
			int size = 0;
			//will divide the string
			StringTokenizer temp = new StringTokenizer(obj.toString(),";",false);
			//dividing the string
			
			//must find the number of divisions
			while(temp.hasMoreElements()){
				temp.nextToken();
				size++;
			}
			
			//create the size of the array
			objs = new Object[size];
			
			//put the values in the array
			temp = new StringTokenizer(obj.toString(),";",false);
			int i = 0;
			
			while(temp.hasMoreElements()){
				objs[i] = temp.nextElement();
				i++;
			}
		}
		
		return objs;
	}
	
	public void mapToTheScript(String key, Object object){
		//put the object in the script also, permitting changes in
		//the object using the script
		engine.put(key, object);
	}
	
	public void testing(){
		
	}
	
	
	
	
	
	//Methods used to read a XML File
	
	
	
	public Document getDocument(String file){
			
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new URL("file:" + file));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;  
	}
	
	public String getFileElement(String file, String element){
				
		//first get the document
		Document doc = getDocument(file);
		
		//get the root element
		Element root = doc.getRootElement();
		
		//get the element that is wanted
		Element temp =  getElement(root,element);
		
		//return the values that are wanted		
		return temp.getChildText("Path") + temp.getChildText("File");
	}
	
	public Element getElementXML(String file, String element){
		
		//System.out.println(file);
		//first get the document
		Document doc = getDocument(file);
		
		//get the root element
		Element root = doc.getRootElement();
		
		//returns the element that is needed
		return getElement(root,element);		
	}
	
	private Element getElement(Element root, String want){

		//get the element that need, like display, keyboard
		Element element = root.getChild(want);
		
		//returns the element that is needed
		return element;
	}
	
	public Element getRootElement(String file){

		//System.out.println(file);
		//first get the document
		Document doc = getDocument(file);
		
		//get the root element
		return doc.getRootElement();
	}
	
}
 
