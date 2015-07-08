package animation;

import java.io.Serializable;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class Joint implements Serializable{
	
	private static final long serialVersionUID = 4815081524401135295L;

	/*
	 * Rotation in local space
	 */
	public Quaternion rotation = new Quaternion();

	
	/*
	 * Offset Relative to parent
	 */
	public Vector3f offset = new Vector3f();
	
	/*
	 * Parent Joint, null if root node
	 */
	public int parentIndex;
	
	/*
	 * Array Of Children
	 */
	public int[] childrenIndices;
	
	public Joint(Quaternion rotation, Vector3f offset, int parentIndex, int[] childrenIndices){
		this.rotation = rotation;
		this.offset = offset;
		this.parentIndex = parentIndex;
		this.childrenIndices = childrenIndices;
	}
	
	public void setRotation(Quaternion q){
		this.rotation = q;
	}
	
	public Quaternion getRotation(){
		return rotation;
	}
	
	public void setOffset(Vector3f v){
		this.offset = v;
	}
	
	public Vector3f getOffset(){
		return this.offset;
	}
}
