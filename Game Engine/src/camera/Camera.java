package camera;

import org.lwjgl.util.vector.Matrix4f;

import toolbox.betterMath.Vector3f;

public class Camera {
	
	protected Vector3f position;

	public Camera(Vector3f position){
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Matrix4f getViewMatrix(){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Vector3f invertedPosition = new Vector3f(-position.x, -position.y, -position.z);
		viewMatrix.translate(invertedPosition.convertToStandard(), null);
		return viewMatrix;
	}

}
