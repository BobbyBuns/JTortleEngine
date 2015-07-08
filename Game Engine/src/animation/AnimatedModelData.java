package animation;

import java.io.Serializable;

import serial.ModelData;

public class AnimatedModelData extends ModelData implements Serializable{
	
	private static final long serialVersionUID = 2443915893727641911L;

	private Pose bindPose;
	
	private float[] bones;
	private float[] weights;

	public AnimatedModelData(float[] vertices, float[] textureCoords,
			float[] normals, int[] indices, float furthestPoint, float[] bones, float[] weights, Pose bindPose) {
		super(vertices, textureCoords, normals, indices, furthestPoint);
		this.bones = bones;
		this.weights = weights;
		this.bindPose = bindPose;
	}
	
	public float[] getBones(){
		return bones;
	}
	
	public float[] getWeights(){
		return weights;
	}
	
	public Pose getBindPose(){
		return bindPose;
	}

}
