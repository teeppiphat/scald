package framework.engine.physic;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.math.Line;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Controller;
//import com.jme.scene.Line;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.util.TextureManager;
import com.jmex.effects.particles.ParticleFactory;
import com.jmex.effects.particles.ParticleGeometry;
import com.jmex.effects.particles.ParticleLines;
import com.jmex.effects.particles.ParticlePoints;
import com.jmex.effects.particles.SimpleParticleInfluenceFactory;
import com.jmex.effects.particles.SwarmInfluence;

import framework.FactoryManager;

/**
 * This one take care of special effects, directed for the particles that are
 * created and used in the game
 * 
 * @author Diego Antônio Tronquini Costi
 * 
 */
public class ParticleSystem {

	//atributes of the class
	
	ParticleGeometry geometry = null;
	
	ParticleLines lines = null;
	
	ParticlePoints points = null;
	
	Vector3f position = null;
	
	Vector3f emission = null;
	
	Vector3f paths[] = null;
	
	Vector3f emissions[] = null;
	
	int particleType;
	
	
	/**
	 * Constructor of the class,initialize its object
	 */
	public ParticleSystem() {

	}

	public void createParticle(String path, String file, String want) {

		// to create the particle ask for the particle needed
		Element element = FactoryManager.getFactoryManager().getScriptManager()
		.getReadScript().getElementXML(path + file, want);

		// get the type of particle created
		loadInformation(element.getChild("Information"));
		
		switch(particleType){
		case 0:			
			createCustom2(element);
			break;
		case 1:
			createLines2(element);
			break;
		case 2:
			createPoints2(element);
			break;
		}
	}
	
	private void createPoints2(Element element){
		
		//will load particularities of the point
		loadPoint(element.getChild("Point"));
		
		//will load the texture
		loadTexture(element.getChild("Texture"));
		
		//will load the Position and Emission initial values
		loadPositionEmission(element.getChild("PositionAndEmission"));
		
		//will load different values that were put together
		loadOthers(element.getChild("Others"));
		
		//will load the values for release
		loadRelease(element.getChild("Release"));
		
		//will load the values for the colors
		loadColor(element.getChild("Color"));
		
		//will load if has paths to move
		loadPaths(element.getChild("Paths"));

		//will load if has emissions to change
		loadEmissions(element.getChild("Emissions"));
		
		//will load the values used for the controller
		loadController(element.getChild("Controller"));
		
		//will load if has gravity influence
		loadGravity(element.getChild("Gravity"));

		//will load if has wind influence
		loadWind(element.getChild("Wind"));
		
		//will load if has drag influence
		loadDrag(element.getChild("Drag"));
		
		//will load if has vortex influence
		loadVortex(element.getChild("Vortex"));
		
		//will load if has swarn influence
		loadSwarn(element.getChild("Swarn"));
				
		points.setAntialiased(true);
		
		//alpha
		AlphaState as1 = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);

		// z-buffer
		ZBufferState zs = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createZBufferState();
		zs.setWritable(false);
		zs.setEnabled(true);

		// put the render states necessary
		points.setRenderState(as1);
		points.setRenderState(zs);
		
		// attach node
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
		.attachChild(points);
	}
	
	private void createLines2(Element element){
		
		//will load partiularities of the line type
		loadLine(element.getChild("Line"));
		
		//will load the texture
		loadTexture(element.getChild("Texture"));
		
		//will load the Position and Emission initial values
		loadPositionEmission(element.getChild("PositionAndEmission"));
		
		//will load different values that were put together
		loadOthers(element.getChild("Others"));
		
		//will load the values for release
		loadRelease(element.getChild("Release"));
		
		//will load the values for the colors
		loadColor(element.getChild("Color"));
		
		//will load if has paths to move
		loadPaths(element.getChild("Paths"));

		//will load if has emissions to change
		loadEmissions(element.getChild("Emissions"));
		
		//will load the values used for the controller
		loadController(element.getChild("Controller"));
		
		//will load if has gravity influence
		loadGravity(element.getChild("Gravity"));

		//will load if has wind influence
		loadWind(element.getChild("Wind"));
		
		//will load if has drag influence
		loadDrag(element.getChild("Drag"));
		
		//will load if has vortex influence
		loadVortex(element.getChild("Vortex"));
		
		//will load if has swarn influence
		loadSwarn(element.getChild("Swarn"));
				
		lines.setAntialiased(true);
		// tests
		lines.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		lines.setLightCombineMode(LightState.OFF);
		lines.setTextureCombineMode(TextureState.REPLACE);

		// alpha
		AlphaState as1 = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);

		// z-buffer
		ZBufferState zs = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createZBufferState();
		zs.setWritable(false);
		zs.setEnabled(true);

		// put the render states necessary
		lines.setRenderState(as1);
		lines.setRenderState(zs);

		//will put the new particle in the system
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
		.attachChild(lines);		
		
	}
	
	private void createCustom2(Element element){
		
		//will load the information
		//loadInformation(element.getChild("Information"));
		
		//will load the texture
		loadTexture(element.getChild("Texture"));
		
		//will load the Position and Emission initial values
		loadPositionEmission(element.getChild("PositionAndEmission"));
		
		//will load different values that were put together
		loadOthers(element.getChild("Others"));
		
		//will load the values for release
		loadRelease(element.getChild("Release"));
		
		//will load the values for the colors
		loadColor(element.getChild("Color"));
		
		//will load if has paths to move
		loadPaths(element.getChild("Paths"));

		//will load if has emissions to change
		loadEmissions(element.getChild("Emissions"));
		
		//will load the values used for the controller
		loadController(element.getChild("Controller"));
		
		//will load if has gravity influence
		loadGravity(element.getChild("Gravity"));

		//will load if has wind influence
		loadWind(element.getChild("Wind"));
		
		//will load if has drag influence
		loadDrag(element.getChild("Drag"));
		
		//will load if has vortex influence
		loadVortex(element.getChild("Vortex"));
		
		//will load if has swarn influence
		loadSwarn(element.getChild("Swarn"));
		
		//tests
		geometry.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		geometry.setLightCombineMode(LightState.OFF);
		geometry.setTextureCombineMode(TextureState.REPLACE);

		// alpha
		AlphaState as1 = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);

		// z-buffer
		ZBufferState zs = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createZBufferState();
		zs.setWritable(false);
		zs.setEnabled(true);

		// put the render states necessary
		geometry.setRenderState(as1);
		geometry.setRenderState(zs);

		//will put the new particle in the system
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
		.attachChild(geometry);
		
	}
	
	private void loadInformation(Element inf){
		if(inf != null){
			
			String type = inf.getChildText("Type");
			
			if (type.equals("Custom")) {
				particleType = 0;				
			} else if (type.equals("Lines")) {
				particleType = 1;				
			} else if (type.equals("Point")) {
				particleType = 2;				
			}
			
			switch(particleType){
			case 0:
				geometry = ParticleFactory.buildParticles(inf
						.getChildText("Name"), Integer.parseInt(inf
						.getChildText("Number")));
				break;
			case 1:
				lines = ParticleFactory.buildLineParticles(inf
						.getChildText("Name"), Integer.parseInt(inf
						.getChildText("Number")));
				break;
			case 2:
				points = ParticleFactory.buildPointParticles(inf
						.getChildText("Name"), Integer.parseInt(inf
						.getChildText("Number")));
				break;			
			}
		}
	}

	private void loadTexture(Element tex){
		if(tex != null){
			//texture
			TextureState ts = FactoryManager.getFactoryManager()
			.getGraphicsManager().getRender().getDisplay()
			.getRenderer().createTextureState();
			ts.setTexture(TextureManager.loadTexture(ParticleSystem.class
					.getClassLoader().getResource(
							tex.getChildText("Path")
							+ tex.getChildText("File")),
							Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR));
			ts.setEnabled(true);
			
			switch(particleType){
			case 0:						
				geometry.setRenderState(ts);
				break;
			case 1:
				lines.setRenderState(ts);
				break;
			case 2:
				points.setRenderState(ts);
				break;			
			}

		}		
	}
	
	private void loadPositionEmission(Element pe){
		if(pe != null){
			//get the initial position
			position = new Vector3f(Float.parseFloat(pe
					.getChildText("PositionX")), Float.parseFloat(pe
							.getChildText("PositionY")), Float.parseFloat(pe
									.getChildText("PositionZ")));
			
			//get the initial emission
			emission = new Vector3f(Float.parseFloat(pe
					.getChildText("EmissionX")), Float.parseFloat(pe
							.getChildText("EmissionY")), Float.parseFloat(pe
									.getChildText("EmissionZ")));
			
		}
	}
	
	private void loadOthers(Element oth){
		if(oth != null){
			
			switch(particleType){
			case 0:				
				//set velocity
				geometry.setInitialVelocity(Float.parseFloat(oth
						.getChildText("InitialVelocity")));
				// set size
				//initial
				geometry.setStartSize(Float.parseFloat(oth
						.getChildText("InitialSize")));
				//end				
				geometry.setEndSize(Float.parseFloat(oth.getChildText("FinalSize")));
				
				// set the speed of the spin
				geometry.setParticleSpinSpeed(Float.parseFloat(oth
						.getChildText("SpinSpeed")));
				
				// set angle
				geometry.setMinimumAngle(Float.parseFloat(oth
						.getChildText("MinimumAngle")));
				geometry.setMaximumAngle(Float.parseFloat(oth
						.getChildText("MaximumAngle")));
				
				break;
			case 1:
				//set velocity
				lines.setInitialVelocity(Float.parseFloat(oth
						.getChildText("InitialVelocity")));
				// set size
				//initial
				lines.setStartSize(Float.parseFloat(oth
						.getChildText("InitialSize")));
				//end				
				lines.setEndSize(Float.parseFloat(oth.getChildText("FinalSize")));
				
				// set the speed of the spin
				lines.setParticleSpinSpeed(Float.parseFloat(oth
						.getChildText("SpinSpeed")));
				
				// set angle
				lines.setMinimumAngle(Float.parseFloat(oth
						.getChildText("MinimumAngle")));
				lines.setMaximumAngle(Float.parseFloat(oth
						.getChildText("MaximumAngle")));

				break;
			case 2:
				//set velocity
				points.setInitialVelocity(Float.parseFloat(oth
						.getChildText("InitialVelocity")));
				// set size
				//initial
				points.setStartSize(Float.parseFloat(oth
						.getChildText("InitialSize")));
				//end				
				points.setEndSize(Float.parseFloat(oth.getChildText("FinalSize")));
				
				// set the speed of the spin
				points.setParticleSpinSpeed(Float.parseFloat(oth
						.getChildText("SpinSpeed")));
				
				// set angle
				points.setMinimumAngle(Float.parseFloat(oth
						.getChildText("MinimumAngle")));
				points.setMaximumAngle(Float.parseFloat(oth
						.getChildText("MaximumAngle")));

				break;			
			}

		}
	}
	
	private void loadRelease(Element rel){
		if(rel != null){
			switch(particleType){
			case 0:
				// set life time
				geometry.setMinimumLifeTime(Float.parseFloat(rel
						.getChildText("MinimumLifeTime")));
				geometry.setMaximumLifeTime(Float.parseFloat(rel
						.getChildText("MaximumLifeTime")));				
				// set the release rate of particles
				geometry.setReleaseRate(Integer.parseInt(rel
						.getChildText("ReleaseRate")));
				//set the variance in the release
				geometry.setReleaseVariance(Float.parseFloat(rel
						.getChildText("ReleaseVariance")));
				
				break;
			case 1:
				// set life time
				lines.setMinimumLifeTime(Float.parseFloat(rel
						.getChildText("MinimumLifeTime")));
				lines.setMaximumLifeTime(Float.parseFloat(rel
						.getChildText("MaximumLifeTime")));				
				// set the release rate of particles
				lines.setReleaseRate(Integer.parseInt(rel
						.getChildText("ReleaseRate")));
				//set the variance in the release
				lines.setReleaseVariance(Float.parseFloat(rel
						.getChildText("ReleaseVariance")));
				break;
			case 2:
				// set life time
				points.setMinimumLifeTime(Float.parseFloat(rel
						.getChildText("MinimumLifeTime")));
				points.setMaximumLifeTime(Float.parseFloat(rel
						.getChildText("MaximumLifeTime")));				
				// set the release rate of particles
				points.setReleaseRate(Integer.parseInt(rel
						.getChildText("ReleaseRate")));
				//set the variance in the release
				points.setReleaseVariance(Float.parseFloat(rel
						.getChildText("ReleaseVariance")));
				break;			
			}
		}	
	}
	
	private void loadColor(Element col){
		if(col != null){
			switch(particleType){
			case 0:
				//set color
				geometry.setStartColor(new ColorRGBA(Float.parseFloat(col
						.getChildText("StartColorR")), Float.parseFloat(col
								.getChildText("StartColorG")), Float.parseFloat(col
										.getChildText("StartColorB")), Float.parseFloat(col
												.getChildText("StartColorA"))));
				geometry.setEndColor(new ColorRGBA(Float.parseFloat(col
						.getChildText("EndColorR")), Float.parseFloat(col
								.getChildText("EndColorG")), Float.parseFloat(col
										.getChildText("EndColorB")), Float.parseFloat(col
												.getChildText("EndColorA"))));
				break;
			case 1:
				//set color
				lines.setStartColor(new ColorRGBA(Float.parseFloat(col
						.getChildText("StartColorR")), Float.parseFloat(col
								.getChildText("StartColorG")), Float.parseFloat(col
										.getChildText("StartColorB")), Float.parseFloat(col
												.getChildText("StartColorA"))));
				lines.setEndColor(new ColorRGBA(Float.parseFloat(col
						.getChildText("EndColorR")), Float.parseFloat(col
								.getChildText("EndColorG")), Float.parseFloat(col
										.getChildText("EndColorB")), Float.parseFloat(col
												.getChildText("EndColorA"))));
				break;
			case 2:
				//set color
				points.setStartColor(new ColorRGBA(Float.parseFloat(col
						.getChildText("StartColorR")), Float.parseFloat(col
								.getChildText("StartColorG")), Float.parseFloat(col
										.getChildText("StartColorB")), Float.parseFloat(col
												.getChildText("StartColorA"))));
				points.setEndColor(new ColorRGBA(Float.parseFloat(col
						.getChildText("EndColorR")), Float.parseFloat(col
								.getChildText("EndColorG")), Float.parseFloat(col
										.getChildText("EndColorB")), Float.parseFloat(col
												.getChildText("EndColorA"))));
				break;			
			}

		}
	}
		
	private void loadController(Element col){
		if(col != null){
			
			switch(particleType){
			case 0:
				// create the controller
				geometry.addController(new ParticleController(Float.parseFloat(col
						.getChildText("Life")), geometry, Boolean.parseBoolean(col
						.getChildText("ParticleMove")), paths, Float.parseFloat(col
						.getChildText("ParticleIncrease")), Boolean
						.parseBoolean(col.getChildText("ParticleCircle")), Float
						.parseFloat(col.getChildText("EmissionIncrease")),
						emissions, Boolean.parseBoolean(col
								.getChildText("EmissionCircle")), Boolean
								.parseBoolean(col.getChildText("EmissionChange"))));

				// set speed
				geometry.getParticleController().setSpeed(
						Float.parseFloat(col.getChildText("Speed")));

				//set the type used to repeat
				if (col.getChild("RepeatType").equals("Wrap")) {
					geometry.getParticleController().setRepeatType(Controller.RT_WRAP);
				} else if (col.getChild("RepeatType").equals("Cycle")) {
					geometry.getParticleController().setRepeatType(Controller.RT_CYCLE);
				} else if (col.getChild("RepeatType").equals("Clamp")) {
					geometry.getParticleController().setRepeatType(Controller.RT_CLAMP);
				}

				// particle flow
				geometry.getParticleController().setControlFlow(
						Boolean.parseBoolean(col.getChildText("ControlFlow")));
				// set interations
				geometry.warmUp(Integer.parseInt(col.getChildText("Iterations")));
		
				break;
			case 1:
				// create the controller
				lines.addController(new ParticleController(Float.parseFloat(col
						.getChildText("Life")), lines, Boolean.parseBoolean(col
						.getChildText("ParticleMove")), paths, Float.parseFloat(col
						.getChildText("ParticleIncrease")), Boolean
						.parseBoolean(col.getChildText("ParticleCircle")), Float
						.parseFloat(col.getChildText("EmissionIncrease")),
						emissions, Boolean.parseBoolean(col
								.getChildText("EmissionCircle")), Boolean
								.parseBoolean(col.getChildText("EmissionChange"))));

				// set speed
				lines.getParticleController().setSpeed(
						Float.parseFloat(col.getChildText("Speed")));

				//set the type used to repeat
				if (col.getChild("RepeatType").equals("Wrap")) {
					lines.getParticleController().setRepeatType(Controller.RT_WRAP);
				} else if (col.getChild("RepeatType").equals("Cycle")) {
					lines.getParticleController().setRepeatType(Controller.RT_CYCLE);
				} else if (col.getChild("RepeatType").equals("Clamp")) {
					lines.getParticleController().setRepeatType(Controller.RT_CLAMP);
				}

				// particle flow
				lines.getParticleController().setControlFlow(
						Boolean.parseBoolean(col.getChildText("ControlFlow")));
				// set interations
				lines.warmUp(Integer.parseInt(col.getChildText("Iterations")));
		
				break;
			case 2:
				// create the controller
				points.addController(new ParticleController(Float.parseFloat(col
						.getChildText("Life")), points, Boolean.parseBoolean(col
						.getChildText("ParticleMove")), paths, Float.parseFloat(col
						.getChildText("ParticleIncrease")), Boolean
						.parseBoolean(col.getChildText("ParticleCircle")), Float
						.parseFloat(col.getChildText("EmissionIncrease")),
						emissions, Boolean.parseBoolean(col
								.getChildText("EmissionCircle")), Boolean
								.parseBoolean(col.getChildText("EmissionChange"))));

				// set speed
				points.getParticleController().setSpeed(
						Float.parseFloat(col.getChildText("Speed")));

				//set the type used to repeat
				if (col.getChild("RepeatType").equals("Wrap")) {
					points.getParticleController().setRepeatType(Controller.RT_WRAP);
				} else if (col.getChild("RepeatType").equals("Cycle")) {
					points.getParticleController().setRepeatType(Controller.RT_CYCLE);
				} else if (col.getChild("RepeatType").equals("Clamp")) {
					points.getParticleController().setRepeatType(Controller.RT_CLAMP);
				}

				// particle flow
				points.getParticleController().setControlFlow(
						Boolean.parseBoolean(col.getChildText("ControlFlow")));
				// set interations
				points.warmUp(Integer.parseInt(col.getChildText("Iterations")));
		
				break;			
			}

		}
	}

	private void loadGravity(Element gra){
		if(gra != null){
			
			switch(particleType){
			case 0:
				geometry.addInfluence(SimpleParticleInfluenceFactory
						.createBasicGravity(new Vector3f(Float.parseFloat(gra
								.getChildText("GravityDirectionX")), Float
								.parseFloat(gra.getChildText("GravityDirectionY")),
								Float.parseFloat(gra
										.getChildText("GravityDirectionZ"))),
								Boolean.parseBoolean(gra
										.getChildText("GravityRotateWithScene"))));
				break;
			case 1:
				lines.addInfluence(SimpleParticleInfluenceFactory
						.createBasicGravity(new Vector3f(Float.parseFloat(gra
								.getChildText("GravityDirectionX")), Float
								.parseFloat(gra.getChildText("GravityDirectionY")),
								Float.parseFloat(gra
										.getChildText("GravityDirectionZ"))),
								Boolean.parseBoolean(gra
										.getChildText("GravityRotateWithScene"))));
				break;
			case 2:
				points.addInfluence(SimpleParticleInfluenceFactory
						.createBasicGravity(new Vector3f(Float.parseFloat(gra
								.getChildText("GravityDirectionX")), Float
								.parseFloat(gra.getChildText("GravityDirectionY")),
								Float.parseFloat(gra
										.getChildText("GravityDirectionZ"))),
								Boolean.parseBoolean(gra
										.getChildText("GravityRotateWithScene"))));
				break;			
			}

		}
	}

	private void loadWind(Element win){
		if(win != null){
			switch(particleType){
			case 0:
				geometry.addInfluence(SimpleParticleInfluenceFactory
						.createBasicWind(Float.parseFloat(win
								.getChildText("WindStrenght")), new Vector3f(Float
										.parseFloat(win.getChildText("WindDirectionX")),
										Float
										.parseFloat(win
												.getChildText("WindDirectionY")),
												Float
												.parseFloat(win
														.getChildText("WindDirectionZ"))),
														Boolean
														.parseBoolean(win
																.getChildText("WindRandom")),
																Boolean.parseBoolean(win
																		.getChildText("WindRotateWithScene"))));
				break;
			case 1:
				lines.addInfluence(SimpleParticleInfluenceFactory
						.createBasicWind(Float.parseFloat(win
								.getChildText("WindStrenght")), new Vector3f(Float
										.parseFloat(win.getChildText("WindDirectionX")),
										Float
										.parseFloat(win
												.getChildText("WindDirectionY")),
												Float
												.parseFloat(win
														.getChildText("WindDirectionZ"))),
														Boolean
														.parseBoolean(win
																.getChildText("WindRandom")),
																Boolean.parseBoolean(win
																		.getChildText("WindRotateWithScene"))));
				break;
			case 2:
				points.addInfluence(SimpleParticleInfluenceFactory
						.createBasicWind(Float.parseFloat(win
								.getChildText("WindStrenght")), new Vector3f(Float
										.parseFloat(win.getChildText("WindDirectionX")),
										Float
										.parseFloat(win
												.getChildText("WindDirectionY")),
												Float
												.parseFloat(win
														.getChildText("WindDirectionZ"))),
														Boolean
														.parseBoolean(win
																.getChildText("WindRandom")),
																Boolean.parseBoolean(win
																		.getChildText("WindRotateWithScene"))));
				break;			
			}

		}
	}

	private void loadDrag(Element dra){
		if(dra != null){
			switch (particleType) {
			case 0:
				// create the drag influence
				geometry.addInfluence(SimpleParticleInfluenceFactory
						.createBasicDrag(Float.parseFloat(dra
								.getChildText("DragCoef"))));
				break;
			case 1:
				// create the drag influence
				lines.addInfluence(SimpleParticleInfluenceFactory
						.createBasicDrag(Float.parseFloat(dra
								.getChildText("DragCoef"))));
				break;
			case 2:
				// create the drag influence
				points.addInfluence(SimpleParticleInfluenceFactory
						.createBasicDrag(Float.parseFloat(dra
								.getChildText("DragCoef"))));
				break;
			}

		}
	}

	private void loadVortex(Element vor){
		if(vor != null){
			
			//particle.addInfluence(SimpleParticleInfluenceFactory.createBasicVortex(strength, divergence, axis, random, transformWithScene);

			Line line = new Line(new Vector3f(Float.parseFloat(vor
					.getChildText("VortexOriginX")), Float.parseFloat(vor
							.getChildText("VortexOriginY")), Float.parseFloat(vor
									.getChildText("VortexOriginZ"))), new Vector3f(Float
											.parseFloat(vor.getChildText("VortexDirectionX")), Float
											.parseFloat(vor.getChildText("VortexDirectionY")), Float
											.parseFloat(vor.getChildText("VortexDirectionZ"))));

			switch (particleType) {
			case 0:

				geometry
				.addInfluence(SimpleParticleInfluenceFactory
						.createBasicVortex(
								Float
								.parseFloat(vor
										.getChildText("VortexStrenght")),
										Float
										.parseFloat(vor
												.getChildText("VortexDivergence")),
												line,
												Boolean.parseBoolean(vor
														.getChildText("VortexRandom")),
														Boolean
														.parseBoolean(vor
																.getChildText("VortexTransformWithScene"))));

				break;
			case 1:
				lines
				.addInfluence(SimpleParticleInfluenceFactory
						.createBasicVortex(
								Float
								.parseFloat(vor
										.getChildText("VortexStrenght")),
										Float
										.parseFloat(vor
												.getChildText("VortexDivergence")),
												line,
												Boolean.parseBoolean(vor
														.getChildText("VortexRandom")),
														Boolean
														.parseBoolean(vor
																.getChildText("VortexTransformWithScene"))));
				break;
			case 2:
				points
				.addInfluence(SimpleParticleInfluenceFactory
						.createBasicVortex(
								Float
								.parseFloat(vor
										.getChildText("VortexStrenght")),
										Float
										.parseFloat(vor
												.getChildText("VortexDivergence")),
												line,
												Boolean.parseBoolean(vor
														.getChildText("VortexRandom")),
														Boolean
														.parseBoolean(vor
																.getChildText("VortexTransformWithScene"))));
				break;
			}

		}
	}

	private void loadSwarn(Element swa){
		if(swa != null){
			
				// create an influence of swarn
			SwarmInfluence swarm = new SwarmInfluence(new Vector3f(Float
					.parseFloat(swa.getChildText("SwarmPositionX")), Float
					.parseFloat(swa.getChildText("SwarmPositionY")), Float
					.parseFloat(swa.getChildText("SwarmPositionZ"))), Float
					.parseFloat(swa.getChildText("SwarmRange")));
			// set the max speed
			swarm.setMaxSpeed(Float.parseFloat(swa.getChildText("MaxSpeed")));
			// set the speed bump
			swarm.setSpeedBump(Float.parseFloat(swa.getChildText("SpeedBump")));
			// set the turn speed
			swarm.setTurnSpeed(Float.parseFloat(swa.getChildText("TurnSpeed")));
				
			
			switch (particleType) {
			case 0:
				// put the influence in the particle
				geometry.addInfluence(swarm);
				break;
			case 1:

				// put the influence in the particle
				lines.addInfluence(swarm);
				break;
			case 2:

				// put the influence in the particle
				points.addInfluence(swarm);
				break;
			}

		}
	}

	private void loadPaths(Element pat){
		if(pat != null){
			//get the array
			paths = createVectors(pat, "Position");
		}
	}

	private void loadEmissions(Element emi){
		if(emi != null){
			//get the array
			emissions = createVectors(emi, "Emission");
		}
	}
	
	private void loadLine(Element lin){
		if(lin != null){
			 // set width of the line
			lines.setLineWidth(Float.parseFloat(lin.getChildText("LineWidth")));
						
			// segment or connected
			if (lin.getChildText("Mode").equals("Segments")) {				
				lines.setMode(0);
			} else if (lin.getChildText("Mode").equals("Connected")) {				
				lines.setMode(1);
			} else if (lin.getChildText("Mode").equals("Loop")) {				
				lines.setMode(2);
			}
			
			// set orientation
			lines.setParticleOrientation(Float.parseFloat(lin
					.getChildText("Orientation")));	
		}	
	}
	
	private void loadPoint(Element poi){
		if(poi != null){			
			//set the size of the points
			points.setPointSize(Float
					.parseFloat(poi.getChildText("PointSize")));			
		}
	}
	
	private void createPoint(Element element) {
		//create the particle
		ParticlePoints pPoints = ParticleFactory.buildPointParticles(element
				.getChildText("Name"), Integer.parseInt(element
						.getChildText("Number")));
		//set the size of the points
		pPoints.setPointSize(Float
				.parseFloat(element.getChildText("PointSize")));

		// set antialiased
		pPoints.setAntialiased(true);

		// set emission direction
		pPoints.setEmissionDirection(new Vector3f(Float.parseFloat(element
				.getChildText("EmissionX")), Float.parseFloat(element
						.getChildText("EmissionY")), Float.parseFloat(element
								.getChildText("EmissionZ"))));
		// set velocity
		pPoints.setInitialVelocity(Float.parseFloat(element
				.getChildText("InitialVelocity")));
		// set size
		pPoints.setStartSize(Float.parseFloat(element
				.getChildText("InitialSize")));
		pPoints.setEndSize(Float.parseFloat(element.getChildText("FinalSize")));
		// set life time
		pPoints.setMinimumLifeTime(Float.parseFloat(element
				.getChildText("MinimumLifeTime")));
		pPoints.setMaximumLifeTime(Float.parseFloat(element
				.getChildText("MaximumLifeTime")));
		// set color
		pPoints.setStartColor(new ColorRGBA(Float.parseFloat(element
				.getChildText("StartColorR")), Float.parseFloat(element
						.getChildText("StartColorG")), Float.parseFloat(element
								.getChildText("StartColorB")), Float.parseFloat(element
										.getChildText("StartColorA"))));
		pPoints.setEndColor(new ColorRGBA(Float.parseFloat(element
				.getChildText("EndColorR")), Float.parseFloat(element
						.getChildText("EndColorG")), Float.parseFloat(element
								.getChildText("EndColorB")), Float.parseFloat(element
										.getChildText("EndColorA"))));
		// set angle
		pPoints.setMinimumAngle(Float.parseFloat(element
				.getChildText("MinimumAngle")));
		pPoints.setMaximumAngle(Float.parseFloat(element
				.getChildText("MaximumAngle")));

		// particle flow
		pPoints.getParticleController().setControlFlow(false);
		// set interations
		pPoints.warmUp(Integer.parseInt(element.getChildText("Iterations")));

		// alpha
		AlphaState as1 = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);

		// z-buffer
		ZBufferState zs = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createZBufferState();
		zs.setWritable(false);
		zs.setEnabled(true);

		// put the render states necessary
		pPoints.setRenderState(as1);
		pPoints.setRenderState(zs);

		// get if the use of path is needed
		int usePath = Integer.parseInt(element.getChildText("QuantityPath"));
		// create an array of Vector3f to get the paths
		Vector3f paths[] = null;

		if (usePath > 0) {

			// create if is needed
			paths = createPath(element, usePath);

			// set initial position of the particles
			pPoints.setOriginOffset(paths[0]);

		} else {
			// set position of the particles
			pPoints.setOriginOffset(new Vector3f(Float.parseFloat(element
					.getChildText("PositionX")), Float.parseFloat(element
							.getChildText("PositionY")), Float.parseFloat(element
									.getChildText("PositionZ"))));
		}

		// get if the use of path is needed
		int useEmission = Integer.parseInt(element
				.getChildText("QuantityEmission"));
		// create an array of Vector3f to get the paths
		Vector3f emission[] = null;

		if (useEmission > 0) {

			// create the paths if is needed
			emission = createEmission(element, useEmission);

			// set initial position of the particles
			pPoints.setOriginOffset(emission[0]);

		} else {
			// set position of the particles
			pPoints.setOriginOffset(new Vector3f(Float.parseFloat(element
					.getChildText("EmissionX")), Float.parseFloat(element
							.getChildText("EmissionY")), Float.parseFloat(element
									.getChildText("EmissionZ"))));
		}
		
//		 create the controller
		pPoints.addController(new ParticleController(Float.parseFloat(element
				.getChildText("Life")), pPoints, Boolean.parseBoolean(element
				.getChildText("ParticleMove")), paths, Float.parseFloat(element
				.getChildText("ParticleIncrease")), Boolean
				.parseBoolean(element.getChildText("ParticleCircle")), Float
				.parseFloat(element.getChildText("EmissionIncrease")),
				emission, Boolean.parseBoolean(element
						.getChildText("EmissionCircle")), Boolean
						.parseBoolean(element.getChildText("EmissionChange"))));

		// attach node
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
		.attachChild(pPoints);

	}

	private void createLines(Element element) {

		// create the particle
		ParticleLines pLines = ParticleFactory.buildLineParticles(element
				.getChildText("Name"), Integer.parseInt(element
						.getChildText("Number")));

		// set width of the line
		pLines
		.setLineWidth(Float.parseFloat(element
				.getChildText("LineWidth")));

		// segment or connected
		if (element.getChild("Mode").equals("Segments")) {
			pLines.setMode(0);
		} else if (element.getChild("Mode").equals("Connected")) {
			pLines.setMode(1);
		} else if (element.getChild("Mode").equals("Loop")) {
			pLines.setMode(2);
		}

		// set antialiased
		pLines.setAntialiased(true);
		// set orientation
		pLines.setParticleOrientation(Float.parseFloat(element
				.getChildText("Orientation")));

		// set emission direction
		pLines.setEmissionDirection(new Vector3f(Float.parseFloat(element
				.getChildText("EmissionX")), Float.parseFloat(element
						.getChildText("EmissionY")), Float.parseFloat(element
								.getChildText("EmissionZ"))));
		// set velocity
		pLines.setInitialVelocity(Float.parseFloat(element
				.getChildText("InitialVelocity")));
		// set size
		pLines.setStartSize(Float.parseFloat(element
				.getChildText("InitialSize")));
		pLines.setEndSize(Float.parseFloat(element.getChildText("FinalSize")));
		// set life time
		pLines.setMinimumLifeTime(Float.parseFloat(element
				.getChildText("MinimumLifeTime")));
		pLines.setMaximumLifeTime(Float.parseFloat(element
				.getChildText("MaximumLifeTime")));
		// set color
		pLines.setStartColor(new ColorRGBA(Float.parseFloat(element
				.getChildText("StartColorR")), Float.parseFloat(element
						.getChildText("StartColorG")), Float.parseFloat(element
								.getChildText("StartColorB")), Float.parseFloat(element
										.getChildText("StartColorA"))));
		pLines.setEndColor(new ColorRGBA(Float.parseFloat(element
				.getChildText("EndColorR")), Float.parseFloat(element
						.getChildText("EndColorG")), Float.parseFloat(element
								.getChildText("EndColorB")), Float.parseFloat(element
										.getChildText("EndColorA"))));
		// set angle
		pLines.setMinimumAngle(Float.parseFloat(element
				.getChildText("MinimumAngle")));
		pLines.setMaximumAngle(Float.parseFloat(element
				.getChildText("MaximumAngle")));

		// set the spin
		pLines.setParticleSpinSpeed(Float.parseFloat(element
				.getChildText("Spin")));
		// particle flow
		pLines.getParticleController().setControlFlow(false);
		// set interations
		pLines.warmUp(Integer.parseInt(element.getChildText("Iterations")));

		// alpha
		AlphaState as1 = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);

		// z-buffer
		ZBufferState zs = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createZBufferState();
		zs.setWritable(false);
		zs.setEnabled(true);

		// put the render states necessary
		pLines.setRenderState(as1);
		pLines.setRenderState(zs);

		//get if the use of path is needed
		int usePath = Integer.parseInt(element.getChildText("QuantityPath"));
		//create an array of Vector3f to get the paths		
		Vector3f paths[] = null;

		if (usePath > 0) {

			//create if is needed
			paths = createPath(element, usePath);

			//set initial position of the particles
			pLines.setOriginOffset(paths[0]);

		} else {
			// set position of the particles
			pLines.setOriginOffset(new Vector3f(Float.parseFloat(element
					.getChildText("PositionX")), Float.parseFloat(element
							.getChildText("PositionY")), Float.parseFloat(element
									.getChildText("PositionZ"))));
		}

		// get if the use of path is needed
		int useEmission = Integer.parseInt(element
				.getChildText("QuantityEmission"));
		//create an array of Vector3f to get the paths
		Vector3f emission[] = null;

		if (useEmission > 0) {

			//create the paths if is needed
			emission = createEmission(element, useEmission);

			//set initial position of the particles
			pLines.setOriginOffset(emission[0]);

		} else {
			// set position of the particles
			pLines.setOriginOffset(new Vector3f(Float.parseFloat(element
					.getChildText("EmissionX")), Float.parseFloat(element
							.getChildText("EmissionY")), Float.parseFloat(element
									.getChildText("EmissionZ"))));
		}

//		 create the controller
		pLines.addController(new ParticleController(Float.parseFloat(element
				.getChildText("Life")), pLines, Boolean.parseBoolean(element
				.getChildText("ParticleMove")), paths, Float.parseFloat(element
				.getChildText("ParticleIncrease")), Boolean
				.parseBoolean(element.getChildText("ParticleCircle")), Float
				.parseFloat(element.getChildText("EmissionIncrease")),
				emission, Boolean.parseBoolean(element
						.getChildText("EmissionCircle")), Boolean
						.parseBoolean(element.getChildText("EmissionChange"))));
		
		// attach node
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
		.attachChild(pLines);

	}

	private void createCustom(Element element) {

		// create an instance
		ParticleGeometry particle = ParticleFactory.buildParticles(element
				.getChildText("Name"), Integer.parseInt(element
						.getChildText("Number")));

		// set velocity
		particle.setInitialVelocity(Float.parseFloat(element
				.getChildText("InitialVelocity")));
		// set size
		particle.setStartSize(Float.parseFloat(element
				.getChildText("InitialSize")));
		particle
		.setEndSize(Float.parseFloat(element.getChildText("FinalSize")));
		// set life time
		particle.setMinimumLifeTime(Float.parseFloat(element
				.getChildText("MinimumLifeTime")));
		particle.setMaximumLifeTime(Float.parseFloat(element
				.getChildText("MaximumLifeTime")));
		// set color
		particle.setStartColor(new ColorRGBA(Float.parseFloat(element
				.getChildText("StartColorR")), Float.parseFloat(element
						.getChildText("StartColorG")), Float.parseFloat(element
								.getChildText("StartColorB")), Float.parseFloat(element
										.getChildText("StartColorA"))));
		particle.setEndColor(new ColorRGBA(Float.parseFloat(element
				.getChildText("EndColorR")), Float.parseFloat(element
						.getChildText("EndColorG")), Float.parseFloat(element
								.getChildText("EndColorB")), Float.parseFloat(element
										.getChildText("EndColorA"))));
		// set angle
		particle.setMinimumAngle(Float.parseFloat(element
				.getChildText("MinimumAngle")));
		particle.setMaximumAngle(Float.parseFloat(element
				.getChildText("MaximumAngle")));

		// set the release rate of particles
		particle.setReleaseRate(Integer.parseInt(element
				.getChildText("ReleaseRate")));
		//set the variance in the release
		particle.setReleaseVariance(Float.parseFloat(element
				.getChildText("ReleaseVariance")));
		//set the speed of the spin
		particle.setParticleSpinSpeed(Float.parseFloat(element
				.getChildText("SpinSpeed")));

		// get if the use of path is needed
		int usePath = Integer.parseInt(element.getChildText("QuantityPath"));
		//create an array of Vector3f to get the paths
		Vector3f paths[] = null;

		if (usePath > 0) {

			//get the path
			paths = createPath(element, usePath);

			//set initial position of the particles
			particle.setOriginOffset(paths[0]);

		} else {			
			// set position of the particles
			particle.setOriginOffset(new Vector3f(Float.parseFloat(element
					.getChildText("PositionX")), Float.parseFloat(element
							.getChildText("PositionY")), Float.parseFloat(element
									.getChildText("PositionZ"))));
		}

		//get if the use of path is needed
		int useEmission = Integer.parseInt(element
				.getChildText("QuantityEmission"));
		//create an array of Vector3f to get the paths
		Vector3f emission[] = null;

		if (useEmission > 0) {

			//create the paths if is needed
			emission = createEmission(element, useEmission);

			//set initial position of the particles
			particle.setOriginOffset(emission[0]);
			
		} else {
			// set position of the particles
			particle.setOriginOffset(new Vector3f(Float.parseFloat(element
					.getChildText("EmissionX")), Float.parseFloat(element
							.getChildText("EmissionY")), Float.parseFloat(element
									.getChildText("EmissionZ"))));
		}
		
		// create the controller
		particle.addController(new ParticleController(Float.parseFloat(element
				.getChildText("Life")), particle, Boolean.parseBoolean(element
				.getChildText("ParticleMove")), paths, Float.parseFloat(element
				.getChildText("ParticleIncrease")), Boolean
				.parseBoolean(element.getChildText("ParticleCircle")), Float
				.parseFloat(element.getChildText("EmissionIncrease")),
				emission, Boolean.parseBoolean(element
						.getChildText("EmissionCircle")), Boolean
						.parseBoolean(element.getChildText("EmissionChange"))));
		
		//set speed
		particle.getParticleController().setSpeed(
				Float.parseFloat(element.getChildText("Speed")));

		//set the type used to repeat
		if (element.getChild("RepeatType").equals("Wrap")) {
			particle.getParticleController().setRepeatType(Controller.RT_WRAP);
		} else if (element.getChild("RepeatType").equals("Cycle")) {
			particle.getParticleController().setRepeatType(Controller.RT_CYCLE);
		} else if (element.getChild("RepeatType").equals("Clamp")) {
			particle.getParticleController().setRepeatType(Controller.RT_CLAMP);
		}

		// particle flow
		particle.getParticleController().setControlFlow(
				Boolean.parseBoolean(element.getChildText("ControlFlow")));
		// set interations
		particle.warmUp(Integer.parseInt(element.getChildText("Iterations")));

		//add gravity influence
		if (element.getChildText("GravityInfluence").equals("Use")) {
			particle.addInfluence(SimpleParticleInfluenceFactory
					.createBasicGravity(new Vector3f(Float.parseFloat(element
							.getChildText("GravityDirectionX")), Float
							.parseFloat(element
									.getChildText("GravityDirectionY")), Float
									.parseFloat(element
											.getChildText("GravityDirectionZ"))),
											Boolean.parseBoolean(element
													.getChildText("GravityRotateWithScene"))));
		}

		//add the wind influence
		if (element.getChildText("WindInfluence").equals("Use")) {

			particle.addInfluence(SimpleParticleInfluenceFactory
					.createBasicWind(Float.parseFloat(element
							.getChildText("WindStrenght")), new Vector3f(
									Float.parseFloat(element
											.getChildText("WindDirectionX")), Float
											.parseFloat(element
													.getChildText("WindDirectionY")),
													Float.parseFloat(element
															.getChildText("WindDirectionZ"))), Boolean
															.parseBoolean(element.getChildText("WindRandom")),
															Boolean.parseBoolean(element
																	.getChildText("WindRotateWithScene"))));

		}

		//add the drag influence
		if (element.getChildText("DragInfluence").equals("Use")) {
			//create the drag influence
			particle.addInfluence(SimpleParticleInfluenceFactory
					.createBasicDrag(Float.parseFloat(element
							.getChildText("DragCoef"))));
		}

		//add the vortex influence
		if (element.getChildText("VortexInfluence").equals("Use")) {
			//particle.addInfluence(SimpleParticleInfluenceFactory.createBasicVortex(strength, divergence, axis, random, transformWithScene);

			Line line = new Line(new Vector3f(Float.parseFloat(element
					.getChildText("VortexOriginX")), Float.parseFloat(element
							.getChildText("VortexOriginY")), Float.parseFloat(element
									.getChildText("VortexOriginZ"))), new Vector3f(Float
											.parseFloat(element.getChildText("VortexDirectionX")),
											Float.parseFloat(element.getChildText("VortexDirectionY")),
											Float.parseFloat(element.getChildText("VortexDirectionZ"))));

			particle
			.addInfluence(SimpleParticleInfluenceFactory
					.createBasicVortex(
							Float.parseFloat(element
									.getChildText("VortexStrenght")),
									Float.parseFloat(element
											.getChildText("VortexDivergence")),
											line,
											Boolean.parseBoolean(element
													.getChildText("VortexRandom")),
													Boolean
													.parseBoolean(element
															.getChildText("VortexTransformWithScene"))));

		}

		//add swarn influence
		if (element.getChildText("SwarnInfluence").equals("Use")) {

			// create an influence of swarn
			SwarmInfluence swarm = new SwarmInfluence(new Vector3f(Float
					.parseFloat(element.getChildText("SwarnPositionX")), Float
					.parseFloat(element.getChildText("SwarnPositionY")), Float
					.parseFloat(element.getChildText("SwarnPositionZ"))), Float
					.parseFloat(element.getChildText("SwarnRange")));
			// set the max speed
			swarm.setMaxSpeed(Float
					.parseFloat(element.getChildText("MaxSpeed")));
			// set the speed bump
			swarm.setSpeedBump(Float.parseFloat(element
					.getChildText("SpeedBump")));
			// set the turn speed
			swarm.setTurnSpeed(Float.parseFloat(element
					.getChildText("TurnSpeed")));
			// put the influence in the particle
			particle.addInfluence(swarm);
		}

		// tests
		particle.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		particle.setLightCombineMode(LightState.OFF);
		particle.setTextureCombineMode(TextureState.REPLACE);

		// alpha
		AlphaState as1 = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createAlphaState();
		as1.setBlendEnabled(true);
		as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
		as1.setDstFunction(AlphaState.DB_ONE);
		as1.setTestEnabled(true);
		as1.setTestFunction(AlphaState.TF_GREATER);
		as1.setEnabled(true);

		// z-buffer
		ZBufferState zs = FactoryManager.getFactoryManager()
		.getGraphicsManager().getRender().getDisplay().getRenderer()
		.createZBufferState();
		zs.setWritable(false);
		zs.setEnabled(true);

		// put the render states necessary
		particle.setRenderState(as1);
		particle.setRenderState(zs);

		if (element.getChild("UseTexture").equals("Yes")) {
			// texture
			TextureState ts = FactoryManager.getFactoryManager()
			.getGraphicsManager().getRender().getDisplay()
			.getRenderer().createTextureState();
			ts.setTexture(TextureManager.loadTexture(ParticleSystem.class
					.getClassLoader().getResource(
							element.getChildText("TexturePath")
							+ element.getChildText("Texture")),
							Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR));
			ts.setEnabled(true);
			particle.setRenderState(ts);
		}

		// attach node
		FactoryManager.getFactoryManager().getGraphicsManager().getRootNode()
		.attachChild(particle);

	}

	public Vector3f[] createPath(Element element, int usePath) {
		//create an array of Vector3f to get the paths
		Vector3f paths[] = null;

		if (usePath > 0) {

			//create the paths if is needed
			paths = new Vector3f[usePath + 1];

			//get the first position			
			paths[0] = new Vector3f(Float.parseFloat(element
					.getChildText("PositionX")), Float.parseFloat(element
							.getChildText("PositionY")), Float.parseFloat(element
									.getChildText("PositionZ")));

			//set initial position of the particles
			//particle.setOriginOffset(paths[0]);

			//go until the end to get all the paths
			for (int i = 1; i <= usePath; i++) {
				paths[i] = new Vector3f(Float.parseFloat(element
						.getChildText("Path" + i + "PositionX")), Float
						.parseFloat(element.getChildText("Path" + i
								+ "PositionY")), Float.parseFloat(element
										.getChildText("Path" + i + "PositionZ")));
			}
		}

		return paths;
	}

	public Vector3f[] createEmission(Element element, int useEmission) {

		//create an array of Vector3f to get the paths
		Vector3f emission[] = null;

		if (useEmission > 0) {

			//create the paths if is needed
			emission = new Vector3f[useEmission + 1];

			//get the first position			
			emission[0] = new Vector3f(Float.parseFloat(element
					.getChildText("EmissionX")), Float.parseFloat(element
							.getChildText("EmissionY")), Float.parseFloat(element
									.getChildText("EmissionZ")));

			//go until the end to get all the paths
			for (int i = 1; i <= useEmission; i++) {
				emission[i] = new Vector3f(Float.parseFloat(element
						.getChildText("Path" + i + "EmissionX")), Float
						.parseFloat(element.getChildText("Path" + i
								+ "EmissionY")), Float.parseFloat(element
										.getChildText("Path" + i + "EmissionZ")));
			}
		}
		return emission;
	}
	
	public Vector3f[] createVectors(Element element, String type){
		
		//create an array to get the values
		Vector3f[] array = null;
		
		//get the children 
		List list = element.getChildren();

		// create the iterator to access the list
		Iterator i = list.iterator();

		// temporary element used to get the elements
		Element temp;
		int cont = 1;

		//initialize the array
		array = new Vector3f[list.size() + 1];
		
		//if its the initial position
		if(type.equals("Position")){
			array[0] = position;
		}else{
			array[0] = emission;
		}
		
		// get the elements from the list
		while (i.hasNext()) {

			// get the first object
			temp = (Element) i.next();

			//put the object in the position of the array
			array[cont] = new Vector3f(Float.parseFloat(temp
					.getChildText(type + "X")), Float
					.parseFloat(temp.getChildText(type + "Y")), Float.parseFloat(temp
									.getChildText(type + "Z")));
			
			//increase
			cont++;			
		}
		
		return array;
	}

}
