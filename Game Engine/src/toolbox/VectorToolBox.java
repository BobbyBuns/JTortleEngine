package toolbox;

import org.lwjgl.util.vector.Quaternion;

import toolbox.betterMath.Vector3f;

public class VectorToolBox {

	public static Vector3f multiply(Vector3f first, Vector3f last){
		Vector3f finalVector = new Vector3f();
		finalVector.x = first.x * last.x;
		finalVector.y = first.y * last.y;
		finalVector.z = first.z * last.z;
		return finalVector;
	}
	
	public static Vector3f multiply(Vector3f vector, float number){
		Vector3f finalVector = new Vector3f();
		finalVector.x = vector.x * number;
		finalVector.y = vector.y * number;
		finalVector.z = vector.z * number;
		return finalVector;
	}
	
	public static Vector3f multiply(float number, Vector3f vector){
		Vector3f finalVector = new Vector3f();
		finalVector.x = vector.x * number;
		finalVector.y = vector.y * number;
		finalVector.z = vector.z * number;
		return finalVector;
	}
	
	
	
	public static Vector3f divide(Vector3f first, Vector3f last){
		Vector3f finalVector = new Vector3f();
		finalVector.x = first.x / last.x;
		finalVector.y = first.y / last.y;
		finalVector.z = first.z / last.z;
		return finalVector;
		
	}
	
	public static Vector3f divide(Vector3f vector, float number){
		Vector3f finalVector = new Vector3f();
		finalVector.x = vector.x / number;
		finalVector.y = vector.y / number;
		finalVector.z = vector.z / number;
		return finalVector;
	}
	
	public static Vector3f add(Vector3f first, Vector3f last){
		Vector3f sum = new Vector3f();
		sum.x = first.x + last.x;
		sum.y = first.y + last.y;
		sum.z = first.z + last.z;
		return sum;
	}
	
	public static void invert(Vector3f vector){
		vector.x = -vector.x;
		vector.y = -vector.y;
		vector.z = -vector.z;
	}

	public static boolean isEqual(Vector3f first, Vector3f last){
		return (first.x == last.x && first.y == last.y && first.z == last.z);
	}

	public static boolean isEqual(Quaternion first, Quaternion last){
		return (first.x == last.x && first.y == last.y && first.z == last.z && first.w == last.w);
	}
	
	public static float getMagnitudeOfVector(Vector3f vect){
		
		return (float) Math.sqrt((vect.x * vect.x) + (vect.y * vect.y) + (vect.z * vect.z));
		
	}
	
	public static boolean isNan(float x){
		return x != x;
	}
	
	public static boolean isNan(Vector3f x){
		boolean nan = false;
		if(isNan(x.x))
			nan = true;
		if(isNan(x.y))
			nan = true;
		if(isNan(x.z))
			nan = true;
		return nan;
	}
	
	
	
	
}
