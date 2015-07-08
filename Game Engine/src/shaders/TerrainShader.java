package shaders;

import org.lwjgl.util.vector.Matrix4f;

import camera.Camera;
import dataManager.Utilities;

public class TerrainShader extends ShaderProgram{

	private static final String VERTEX_FILE = Utilities.shaders + "terrainVertexShader";
	private static final String FRAGMENT_FILE = Utilities.shaders + "terrainFragmentShader";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normals");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		
		location_lightPosition = super.getUniformLocation("lightPosition");
		
		location_lightColor = super.getUniformLocation("lightColor");
		
		location_shineDamper = super.getUniformLocation("shineDamper");
		
		location_reflectivity = super.getUniformLocation("reflectivity");
	}
	
	public void loadLight(Light light){
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = camera.getViewMatrix();
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadShineDamper(float shineDamper){
		super.loadFloat(location_shineDamper, shineDamper);
	}
	
	public void loadReflectivity(float reflectivity){
		super.loadFloat(location_reflectivity, reflectivity);
	}

}
