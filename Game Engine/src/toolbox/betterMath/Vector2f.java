package toolbox.betterMath;

public class Vector2f {
	
	public float x;

	public float y;


	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}

	public Vector2f(){
		this.x = 0;
		this.y = 0;
	}

	public Vector2f(Vector2f vector){
		this.x = vector.x;
		this.y = vector.y;
	}

	public Vector2f add(Vector2f vector){
		return new Vector2f(this.x + vector.x, this.y + vector.y);
	}

	public Vector2f subtract(Vector2f vector){
		return new Vector2f(this.x - vector.x, this.y - vector.y);
	}

	public Vector2f multiply(float number){
		return new Vector2f(this.x * number, this.y * number);
	}

	public Vector2f divide(float number){
		return new Vector2f(this.x / number, this.y / number);
	}
}
