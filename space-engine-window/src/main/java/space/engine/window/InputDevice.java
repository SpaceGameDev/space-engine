package space.engine.window;

import org.jetbrains.annotations.Nullable;
import space.engine.event.Event;

public interface InputDevice {
	
	String getName();
	
	//basic
	interface KeyInputDevice extends InputDevice {
		
		@WindowThread
		boolean isKeyDown(int key);
		
		@WindowThread
		@Nullable String getKeyName(int key);
		
		Event<@WindowThread KeyInputEvent> getKeyInputEvent();
		
		Event<@WindowThread CharacterInputEvent> getCharacterInputEvent();
		
		@FunctionalInterface
		interface KeyInputEvent {
			
			/**
			 * callback for key presses
			 *
			 * @param key        the platform specific key id
			 * @param wasPressed true if it was pressed, false if released
			 */
			void onKeyInput(int key, boolean wasPressed);
		}
		
		@FunctionalInterface
		interface CharacterInputEvent {
			
			/**
			 * callback for character input
			 *
			 * @param str the input string
			 */
			void onKeyInput(String str);
		}
	}
	
	interface PointerInputDevice extends InputDevice {
		
		/**
		 * gets the position of the mouse
		 *
		 * @return a double[2] array with x and y coordinates as double[0] and double[1]
		 */
		@WindowThread
		double[] getPosition();
	}
	
	//specific
	interface Keyboard extends KeyInputDevice {
	
	}
	
	interface Mouse extends PointerInputDevice, KeyInputDevice {
	
	}
}
