package serial;

import java.io.Serializable;
 
public class ModelData implements Cloneable, Serializable{
	
	private static final long serialVersionUID = -3194077312763780310L;
	
	private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;
 
    public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
            float furthestPoint) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.furthestPoint = furthestPoint;
    }
 
    public float[] getVertices() {
        return vertices;
    }
 
    public float[] getTextureCoords() {
        return textureCoords;
    }
 
    public float[] getNormals() {
        return normals;
    }
 
    public int[] getIndices() {
        return indices;
    }
 
    public float getFurthestPoint() {
        return furthestPoint;
    }
 
}