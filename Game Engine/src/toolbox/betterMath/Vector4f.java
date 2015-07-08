package toolbox.betterMath;

public class Vector4f {
	
	public float x;

	public float y;

	public float z;

	public float w;

	public Vector4f(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}

	public Vector4f(Vector4f vector){
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		this.w = vector.w;
	}

	public Vector4f add(Vector4f vector){
		return new Vector4f(this.x + vector.x, this.y + vector.y, this.z + vector.z, this.w + vector.w);
	}

	public Vector4f subtract(Vector4f vector){
		return new Vector4f(this.x - vector.x, this.y - vector.y, this.z - vector.z, this.w - vector.w);
	}

	public Vector4f multiply(float number){
		return new Vector4f(this.x * number, this.y * number, this.z * number, this.w * number);
	}

	public Vector4f divide(float number){
		return new Vector4f(this.x / number, this.y / number, this.z / number, this.w / number);
	}
}
