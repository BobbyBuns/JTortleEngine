package camera;

import org.lwjgl.util.vector.Matrix4f;

import renderEngine.DisplayManager;
import toolbox.betterMath.Vector3f;

public class EulerCamera extends Camera{

	public EulerCamera(Vector3f position, Vector3f rotation) {
		super(position);
		this.orientation = rotation;
	}

	protected Vector3f orientation;

	public Vector3f getOrientation() {
		return orientation;
	}

	public void setOrientation(Vector3f orientation) {
		this.orientation = orientation;
	}

	public void increaseRotation(Vector3f rotation){
		this.orientation = orientation.plus(rotation);
	}

	protected void moveForward(float degrees, float speed){
		this.position.x += (Math.sin(Math.toRadians(degrees))  * DisplayManager.getDelta() * speed * 10);
		this.position.z += (-Math.cos(Math.toRadians(degrees)) * DisplayManager.getDelta() * speed * 10);
	}

	@Override
	public Matrix4f getViewMatrix(){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		viewMatrix.rotate((float) Math.toRadians(-orientation.x), new Vector3f(1, 0, 0).convertToStandard());
		viewMatrix.rotate((float) Math.toRadians(orientation.y), new Vector3f(0, 1, 0).convertToStandard());
		viewMatrix.rotate((float) Math.toRadians(orientation.z), new Vector3f(0, 0, 1).convertToStandard());
		Vector3f invertedPosition = new Vector3f(-position.x, -position.y, -position.z);
		viewMatrix.translate(invertedPosition.convertToStandard(),viewMatrix);
		return viewMatrix;
	}
	
}
