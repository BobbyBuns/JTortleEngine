package camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.DisplayManager;
import toolbox.betterMath.Maths;
import toolbox.betterMath.Vector3f;

public class QuaternionCamera extends Camera{

	protected Quaternion orientation = new Quaternion(0, 0, 0, 1);

	public QuaternionCamera(Vector3f position) {
		super(position);
	}

	public QuaternionCamera(Vector3f position, Quaternion rotation){
		super(position);
		this.orientation = rotation;
	}
	
	public QuaternionCamera(Vector3f position, Vector3f rotation){
		super(position);
		this.orientation = Maths.fromEuler(rotation.convertToStandard());
	}
	
	public void setRotation(Quaternion rotation){
		this.orientation = rotation;
	}
	
	public void setRotation(Vector3f rotation){
		this.orientation = Maths.fromEuler(rotation.convertToStandard());
	}
	
	public void increaseRotationRelative(Quaternion rotation){
		Quaternion.mul(orientation, rotation, orientation);
	}
	
	public void increaseRotationWorld(Quaternion rotation){
		Quaternion.mul(rotation, orientation, orientation);
	}

	public void increaseRotationRelative(Vector3f rotation){
		Quaternion.mul(orientation, Maths.fromEuler(rotation.convertToStandard()), orientation);
	}
	
	public void increaseRotationWorld(Vector3f rotation){
		Vector4f xAlign = new Vector4f(1, 0, 0, (float) Math.toRadians(rotation.x));
		Vector4f yAlign = new Vector4f(0, 1, 0, (float) Math.toRadians(rotation.y));
		Vector4f zAlign = new Vector4f(0, 0, 1, (float) Math.toRadians(rotation.z));		

		Quaternion xq = Maths.fromAxisAligned(xAlign);
		Quaternion yq = Maths.fromAxisAligned(yAlign);
		Quaternion zq = Maths.fromAxisAligned(zAlign);		
		
		Quaternion.mul(orientation, xq, orientation);
		Quaternion.mul(yq, orientation, orientation);
		Quaternion.mul(orientation, zq, orientation);		
	}

	public Quaternion getRotation(){
		return orientation;
	}
	
	@Override
	public Matrix4f getViewMatrix(){

		if(orientation.length() != 1)
			this.orientation.normalise();
		
		Matrix4f viewMatrix = new Matrix4f();

		viewMatrix.setIdentity();
		
		viewMatrix = Maths.createTransformationMatrix(position.convertToStandard(), this.orientation, 1);

		viewMatrix.invert();

		Vector3f invertedPosition = new Vector3f(-position.x, -position.y, -position.z);
		
		viewMatrix.translate(invertedPosition.convertToStandard(), viewMatrix);
		
		return viewMatrix;
	}

	protected void tranlateRelative(Vector3f axis, float distance){
		Vector3f forwardVector = new Vector3f();
		
		forwardVector = new Vector3f(axis.x, axis.y, -axis.z);

		forwardVector.applyQuaternion(orientation);
		
		forwardVector.multiply(DisplayManager.getDelta() * 10 * distance);
		
		position.add(forwardVector);
	}
	
}