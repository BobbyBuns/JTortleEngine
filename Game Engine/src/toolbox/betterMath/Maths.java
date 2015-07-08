package toolbox.betterMath;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector3f position, Quaternion rotation, float scale) {

		Matrix4f dest = new Matrix4f();
		
		float q00 = 2.0f * rotation.x * rotation.x;
        float q11 = 2.0f * rotation.y * rotation.y;
        float q22 = 2.0f * rotation.z * rotation.z;
        float q01 = 2.0f * rotation.x * rotation.y;
        float q02 = 2.0f * rotation.x * rotation.z;
        float q03 = 2.0f * rotation.x * rotation.w;
        float q12 = 2.0f * rotation.y * rotation.z;
        float q13 = 2.0f * rotation.y * rotation.w;
        float q23 = 2.0f * rotation.z * rotation.w;
        
        dest.m00 = (1.0f - q11 - q22) * scale; //x
        dest.m01 = (q01 + q23) * scale; //x
        dest.m02 = (q02 - q13) * scale; //x
        dest.m03 = 0.0f;
        dest.m10 = (q01 - q23) * scale; //y
        dest.m11 = (1.0f - q22 - q00) * scale; //y
        dest.m12 = (q12 + q03) * scale; //y
        dest.m13 = 0.0f;
        dest.m20 = (q02 + q13) * scale; //z
        dest.m21 = (q12 - q03) * scale; //z
        dest.m22 = (1.0f - q11 - q00) * scale; //z
        dest.m23 = 0.0f;
        dest.m30 = position.x;
        dest.m31 = position.y;
        dest.m32 = position.z;
        dest.m33 = 1.0f;

        return dest;
    }

	public static Matrix4f createTransformationMatrix(Vector3f translation, float scale){
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;

	}
	
	public static Quaternion multiplyByScalar(Quaternion quat, float scalar){
		return new Quaternion(quat.x * scalar, quat.y * scalar, quat.z * scalar, quat.w * scalar);
	}

	public static Quaternion fromEuler(Vector3f euler){
		//when Testing I need to see if this method uses rads or degs, I am assuming degs for now
		float yaw = (float) Math.toRadians(euler.y);
		float pitch = (float) Math.toRadians(euler.x);
		float roll = (float) Math.toRadians(euler.z);
		float halfYaw = yaw * 0.5f;
		float halfPitch = pitch * 0.5f;
		float halfRoll = roll * 0.5f;
		float cosYaw = (float)Math.cos(halfYaw);
		float sinYaw = (float)Math.sin(halfYaw);
		float cosPitch = (float)Math.cos(halfPitch);
		float sinPitch = (float)Math.sin(halfPitch);
		float cosRoll = (float)Math.cos(halfRoll);
		float sinRoll = (float)Math.sin(halfRoll);
		Quaternion quat = new Quaternion();
		quat.x = cosRoll * sinPitch * cosYaw + sinRoll * cosPitch * sinYaw;
		quat.y = cosRoll * cosPitch * sinYaw - sinRoll * sinPitch * cosYaw;
		quat.z = sinRoll * cosPitch * cosYaw - cosRoll * sinPitch * sinYaw;
		quat.w = cosRoll * cosPitch * cosYaw + sinRoll * sinPitch * sinYaw;
		quat.normalise();
		return quat;
	}
	
	public static Quaternion fromAxisAligned(Vector4f axisAligned){
		float s = (float) Math.sin(axisAligned.w/2);
		return new Quaternion(axisAligned.x * s, axisAligned.y * s, axisAligned.z * s, (float) Math.cos(Math.toRadians(axisAligned.w) / 2));
	}

	public static Quaternion slerp(Quaternion initialq, Quaternion finalq, float t){
		
		float cosAngle = Quaternion.dot(initialq, finalq);
		
        float c1, c2;
        // Linear interpolation for close orientations
        if ((1.0 - Math.abs(cosAngle)) < 0.01) {
                c1 = 1.0f - t;
                c2 = t;
        } else {
                // Spherical interpolation
                float angle = (float) Math.acos(Math.abs(cosAngle));
                float sinAngle = (float) Math.sin(angle);
                c1 = (float) Math.sin(angle * (1.0f - t)) / sinAngle;
                c2 = (float) Math.sin(angle * t) / sinAngle;
        }

        // Use the shortest path
        if (cosAngle < 0.0)
                c1 = -c1;

        return new Quaternion(c1 * initialq.x + c2 * finalq.x, c1 * initialq.y + c2 * finalq.y, c1 * initialq.z
                        + c2 * finalq.z, c1 * initialq.w + c2 * finalq.w);
		
	}
	
	public static Quaternion fromEulerToQuat(Vector3f euler){
		float h = (float) ((euler.y)*Math.PI/360);
		float a = (float) ((euler.z)*Math.PI/360);
		float b = (float) ((euler.x)*Math.PI/360);
		float c1 = (float) Math.cos(h);
		float c2 = (float) Math.cos(a);
		float c3 = (float) Math.cos(b);
		float s1 = (float) Math.sin(h);
		float s2 = (float) Math.sin(a);
		float s3 = (float) Math.sin(b);
		float w = ((c1*c2*c3 - s1*s2*s3)*100000)/100000;
		float x = ((s1*s2*c3 + c1*c2*s3)*100000)/100000;
		float y = ((s1*c2*c3 + c1*s2*s3)*100000)/100000;
		float z = ((c1*s2*c3 - s1*c2*s3)*100000)/100000;
		
		//x, y, z, w
		
		Quaternion quat = new Quaternion();
		quat.x = x;
		quat.y = y;
		quat.z = z;
		quat.w = w;
		Quaternion.normalise(quat, quat);
		
		return quat;
		
	}
	
}