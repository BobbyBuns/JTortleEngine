package shaders;


public abstract class EntityShader extends ShaderProgram{

	public EntityShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}
	
	public abstract void load();
}
