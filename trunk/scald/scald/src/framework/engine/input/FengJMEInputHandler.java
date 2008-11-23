package framework.engine.input;

import org.fenggui.event.mouse.MouseButton;

import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.input.MouseInputListener;

import framework.FactoryManager;
import framework.engine.event.Event;

public class FengJMEInputHandler extends InputHandler {

	/**
	 * Object used to know if its necessary to handle the mouse
	 */
	private boolean mouseHandled;


	/**
	 * Constructor of the class, creates the listener used for the mouse
	 */
	public FengJMEInputHandler() {

		//creates the listener
		MouseInput.get().addListener(new MouseListener());

		//show the cursor
		MouseInput.get().setCursorVisible(true);

	}	

	/* (non-Javadoc)
	 * @see com.jme.input.InputHandler#update(float)
	 */
	public void update(float time) {
		//mouse not handled
		mouseHandled = false;
		//calls the super class to update
		super.update(time);			
	}

	/**
	 * Method used to know if the mouse was handled
	 * @return returns a boolean
	 */
	public boolean wasMouseHandled() {
		return mouseHandled;
	}

	private class MouseListener implements MouseInputListener {

		private boolean down;

		private int lastButton;
		
		public void onButton(int button, boolean pressed, int x, int y) {
			down = pressed;
			lastButton = button;

			// show the cursor
			FactoryManager.getFactoryManager().getGraphicsManager().getGui()
					.getCursor().show();
			
			//don´t interact with the widgets like normal
			if (pressed) {
				//creates an event
				Event mouse = new Event("mouse", "pressed", "", x, y);
				//uses the singleton, to don´t need to use a vector to pass the events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(mouse);

			} else {
				//creates an event
				Event mouse = new Event("mouse", "released", "", x, y);
				//uses the singleton, to don´t need to use a vector to pass the events
				FactoryManager.getFactoryManager().getEventManager()
						.registerEvent(mouse);

			}

			//interact with the widgets
			if (pressed) {
				mouseHandled = FactoryManager.getFactoryManager()
						.getGraphicsManager().getGui().getDisp()
						.fireMousePressedEvent(x, y, getMouseButton(button), 1);

			} else {
				mouseHandled = FactoryManager
						.getFactoryManager()
						.getGraphicsManager()
						.getGui()
						.getDisp()
						.fireMouseReleasedEvent(x, y, getMouseButton(button), 1);
				}
			
		}

		public void onMove(int xDelta, int yDelta, int newX, int newY) {

			//create event
			
			//send the event to the event manager
			
			
			// If the button is down, the mouse is being dragged
			if (down)
				mouseHandled = FactoryManager.getFactoryManager()
						.getGraphicsManager().getGui().getDisp()
						.fireMouseDraggedEvent(newX, newY,
								getMouseButton(lastButton));
			else
				mouseHandled = FactoryManager.getFactoryManager()
						.getGraphicsManager().getGui().getDisp()
						.fireMouseMovedEvent(newX, newY);
			
			// show the cursor
			FactoryManager.getFactoryManager().getGraphicsManager().getGui()
					.getCursor().show();
			
		}

		public void onWheel(int wheelDelta, int x, int y) {
			// wheelDelta is positive if the mouse wheel rolls up
			if (wheelDelta > 0)
				mouseHandled = FactoryManager.getFactoryManager()
						.getGraphicsManager().getGui().getDisp()
						.fireMouseWheel(x, y, true, wheelDelta);
			else
				mouseHandled = FactoryManager.getFactoryManager()
						.getGraphicsManager().getGui().getDisp()
						.fireMouseWheel(x, y, false, wheelDelta);

			// note (johannes): wheeling code not tested on jME, please report
			// problems on www.fenggui.org/forum/
			
			// show the cursor
			FactoryManager.getFactoryManager().getGraphicsManager().getGui()
					.getCursor().show();
		}

		/**
		 * Helper method that maps the mouse button to the equivalent FengGUI
		 * MouseButton enumeration.
		 * 
		 * @param button
		 *            The button pressed or released.
		 * @return The FengGUI MouseButton enumeration matching the button.
		 */
		private MouseButton getMouseButton(int button) {
			switch (button) {
			case 0:
				return MouseButton.LEFT;
			case 1:
				return MouseButton.RIGHT;
			case 2:
				return MouseButton.MIDDLE;
			default:
				return MouseButton.LEFT;
			}
		}

	}

}