package models;

import toolbox.betterMath.Vector3f;;

public class Box {
	

	public Vector3f[] boundings = new Vector3f[8];
	
	public Vector3f minimum;
	public Vector3f maximum;
	
	
	public Box(Vector3f minimum, Vector3f maximum, Vector3f[] boundings){
		this.minimum = new Vector3f(minimum);
		this.maximum = new Vector3f(maximum);
		if(boundings.length == 8){
			this.boundings = boundings;
		}
	}
	
	public Box(float x1, float y1, float z1, float x2, float y2, float z2, Vector3f[] boundings){
		this.minimum = new Vector3f(x1, y1, z1);
		this.maximum = new Vector3f(x2, y2, z2);
		if(boundings.length == 8){
			this.boundings = boundings;
		}	
	}
	
	public Box(){
		minimum = new Vector3f();
		maximum = new Vector3f();	
		boundings = new Vector3f[8];
	}

	public Box(Box box){
		minimum = box.minimum;
		maximum = box.maximum;
		boundings = box.boundings;		
	}

	public Box clone(){
		return new Box(new Vector3f(this.minimum), new Vector3f(this.maximum), this.boundings.clone());
	}
	
	
	
}
