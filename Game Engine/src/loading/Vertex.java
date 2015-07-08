package loading;
 
import org.lwjgl.util.vector.Vector3f;
 
public class Vertex {
     
	protected static final int NO_INDEX = -1;
    
    protected Vector3f position;
    protected int textureIndex = NO_INDEX;
    protected int normalIndex = NO_INDEX;
    protected Vertex duplicateVertex = null;
    protected int index;
    protected float length;
     
    public Vertex(int index,Vector3f position){
        this.index = index;
        this.position = position;
        this.length = position.length();
    }
     
    public int getIndex(){
        return index;
    }
     
    public float getLength(){
        return length;
    }
     
    public boolean isSet(){
        return textureIndex!=NO_INDEX && normalIndex!=NO_INDEX;
    }
     
    public boolean hasSameAttributes(int textureIndexOther,int normalIndexOther){
        return textureIndexOther==textureIndex && normalIndexOther==normalIndex;
    }
     
    public void setTextureIndex(int textureIndex){
        this.textureIndex = textureIndex;
    }
     
    public void setNormalIndex(int normalIndex){
        this.normalIndex = normalIndex;
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public int getTextureIndex() {
        return textureIndex;
    }
 
    public int getNormalIndex() {
        return normalIndex;
    }
 
    public Vertex getDuplicateVertex() {
        return duplicateVertex;
    }
 
    public void setDuplicateVertex(Vertex duplicateVertex) {
        this.duplicateVertex = duplicateVertex;
    }
 
}