package toolbox.betterMath;

import org.lwjgl.util.vector.Quaternion;

public class Vector3f {
	
	public float x;

	public float y;

	public float z;

	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3f(Vector3f vector){
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}

	public void add(Vector3f vector){
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
	}

	public Vector3f plus(Vector3f vector){
		return new Vector3f(this.x + vector.x, this.y + vector.y, this.z + vector.z);
	}

	public void subtract(Vector3f vector){
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
	}

	public Vector3f minus(Vector3f vector){
		return new Vector3f(this.x - vector.x, this.y - vector.y, this.z - vector.z);
	}

	public Vector3f times(float number){
		return new Vector3f(this.x * number, this.y * number, this.z * number);
	}

	public void multiply(float number){
		this.x *= number;
		this.y *= number;
		this.z *= number;
	}

	public Vector3f dividedBy(float number){
		return new Vector3f(this.x / number, this.y / number, this.z / number);
	}

	public void divide(float number){
		this.x /= number;
		this.y /= number;
		this.z /= number;
	}

	public org.lwjgl.util.vector.Vector3f convertToStandard(){
		return new org.lwjgl.util.vector.Vector3f(this.x, this.y, this.z);
	}

	public void applyQuaternion(Quaternion q){
		float ix =  q.w * x + q.y * z - q.z * y;
		float iy =  q.w * y + q.z * x - q.x * z;
		float iz =  q.w * z + q.x * y - q.y * x;
		float iw = - q.x * x - q.y * y - q.z * z;

		// calculate result * inverse quat

		this.x = ix * q.w + iw * - q.x + iy * - q.z - iz * - q.y;
		this.y = iy * q.w + iw * - q.y + iz * - q.x - ix * - q.z;
		this.z = iz * q.w + iw * - q.z + ix * - q.y - iy * - q.x;
	}
	
	public void invert(){
		this.x = -this.x;
		this.y = -this.y;
		this.z = -this.z;
	}

	public float getMagnitude(){
		float a = (x * x) + (z* z);
		float b = (a * a) + (y * y);
		return (float) Math.sqrt(b);
	}
	
	@Override
	public String toString(){
		return "x: " + x + " y: " + y + " z: " + z;
		
	}
}
