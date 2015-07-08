package entities;

import org.lwjgl.input.Keyboard;

public class ControlManager {

	private static Controllable control;

	public static void setControlledObject(Controllable controller){
		control = controller;
	}

	public static int forwardKey = Keyboard.KEY_W;
	public static int backwardKey = Keyboard.KEY_S;
	public static int leftKey = Keyboard.KEY_A;
	public static int rightKey = Keyboard.KEY_D;
	public static int upKey = Keyboard.KEY_SPACE;
	public static int downKey = Keyboard.KEY_LSHIFT;
	
	public static void updateControl(){
		if(control == null){
			return;
		}

		if(Keyboard.isKeyDown(forwardKey)){
			control.forward();
		}

		if(Keyboard.isKeyDown(backwardKey)){
			control.backward();
		}

		if(Keyboard.isKeyDown(leftKey)){
			control.left();
		}

		if(Keyboard.isKeyDown(rightKey)){
			control.right();
		}

		if(Keyboard.isKeyDown(upKey)){
			control.up();
		}

		if(Keyboard.isKeyDown(downKey)){
			control.down();
		}
	}
}
