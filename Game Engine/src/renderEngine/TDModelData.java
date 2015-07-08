package renderEngine;

public class TDModelData {
	
	private float[] vertices;
    public TDModelData(float[] vertices) {
    	this.vertices = vertices;
    }
 
    public float[] getVertices(){
    	return vertices;
    }
    
    public TDModelData clone(){
    	return new TDModelData(this.vertices);
    }
}
