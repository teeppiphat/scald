package framework.engine.graphics;

import com.jme.scene.Node;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.ZBufferState;


/**
 * Class responsible for the management of the graphics, taking care
 * of the interface(GUI), drawing the objects of the scene(Render) and
 * showing them adjusting for the display used(Output)
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class GraphicsManager {
 
	/**
	 * Responsible for the interface presented to the
	 * user, the menus and informations in general
	 */
	private GUI gui;
	 
	/**
	 * Responsible for drawing the objects of the scene, depending
	 * of the Output
	 */
	private Render render;
	 
	/**
	 * Represents the display used by the platform
	 */
	private Output output;
	 
	/**
	 * Represents the tree that has all the nodes that are going
	 * to be draw, that are a part of the map that was loaded
	 * It´s used only to attach all the elements of the map inside
	 * it and to pass it to be draw
	 */
	private Node rootNode;
	
	/**
	 * Represents all the objects, it is attached to the rootNode
	 * to draw everything
	 */
	//private Node objectNode;
	
	/**
	 * Represents the interface, it is attached to the rootNode
	 * to draw everything
	 */
	//private Node interfaceNode;
	
	
	/**
	 * Constructor of the class, it initialize the
	 * objects of the class
	 */
	public GraphicsManager(){
		
		//initialize the objects
		gui = new GUI();
		render = new Render();
		output = new Output();
		rootNode = new Node("My Node");	
	}

	/**
	 * Method used to create the display used to draw
	 */
	public void createDisplay(String file){
				
		//first load the configuration
		output.loadDisplayConfiguration(file);				
		//and then create the display
		render.createDisplay(output.getElement());
		
	}
	
	public void createZBuffer(){
		/** Create a ZBuffer to display pixels closest to the camera above farther ones.  */
	      ZBufferState buf = getRender().display.getRenderer().createZBufferState();
	      //buf.setWritable(false);
	      buf.setEnabled(true);
	      buf.setFunction(ZBufferState.CF_LEQUAL);

	      rootNode.setRenderState(buf);
	}
	
	public void createAlphaState(){
		 AlphaState as1 = getRender().display.getRenderer().createAlphaState();
		    as1.setBlendEnabled(true);
		    as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		    as1.setDstFunction(AlphaState.DB_ONE);
		    as1.setTestEnabled(true);
		    as1.setTestFunction(AlphaState.TF_GREATER);
		    as1.setEnabled(true);
	}
	
	/**
	 * Method used to return the rootNode
	 * @return return an object of the class Node
	 */
	public Node getRootNode() {
		return rootNode;
	}

	/**
	 * Is used to return the GUI
	 * @return return an object of the class GUI
	 */
	public GUI getGui() {
		return gui;
	}

	/**
	 * Is used to return the output
	 * @return return an object of the class Output
	 */
	public Output getOutput() {
		return output;
	}

	/**
	 * Is used to return the render 
	 * @return return an object of the class Render
	 */
	public Render getRender() {
		return render;
	}
	 
	
}
 
