package examplePackage;

import camera.QuaternionCamera;
import toolbox.betterMath.Vector3f;
import entities.Controllable;

public class ExampleCamera extends QuaternionCamera implements Controllable{

	public float speed = 1;
	
	public ExampleCamera(Vector3f position, Vector3f rotation) {
		super(position, rotation);
	}

	
	@Override
	public void forward() {
		tranlateRelative(new Vector3f(0, 0, 1), speed);
	}
	

	@Override
	public void backward() {
		tranlateRelative(new Vector3f(0, 0, -1), speed);
	}


	@Override
	public void left() {
		tranlateRelative(new Vector3f(-1, 0, 0), speed);
	}


	@Override
	public void right() {
		tranlateRelative(new Vector3f(1, 0, 0), speed);
	}


	@Override
	public void up() {
		tranlateRelative(new Vector3f(0, 1, 0), speed);		
	}


	@Override
	public void down() {
		tranlateRelative(new Vector3f(0, -1, 0), speed);		
	}

}
