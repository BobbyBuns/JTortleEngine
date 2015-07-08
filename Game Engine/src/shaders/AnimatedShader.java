package shaders;

import org.lwjgl.util.vector.Matrix4f;

import animation.Armature;
import animation.Pose;
import camera.Camera;
import gameInstantiation.MainGameLoop;
import dataManager.Utilities;

public class AnimatedShader extends EntityShader {

	private static final String VERTEX_FILE = Utilities.shaders + "animatedVertexShader";
	private static final String FRAGMENT_FILE = Utilities.shaders + "fragmentShader";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_joints;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	
	public AnimatedShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "bones");
		super.bindAttribute(4, "weights");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		
		location_lightPosition = super.getUniformLocation("lightPosition");
		
		location_joints = super.getUniformLocation("joints");
		
		location_lightColor = super.getUniformLocation("lightColor");
		
		location_shineDamper = super.getUniformLocation("shineDamper");
		
		location_reflectivity = super.getUniformLocation("reflectivity");
	}
	
	@Override
	public void load() {
		loadLight(MainGameLoop.sun);
		loadViewMatrix(MainGameLoop.camera);
	}
	
	public static Matrix4f[] tempJoints;
	
	public void loadSkeleton(Armature armature, Pose pose){
		//converting armature object to 100 4x4 matrices for processing in the shader
		//renderer's 2 call, not load method
		Matrix4f matrices[] = new Matrix4f[100];
		
		//form matrix array using pose variable passed
		//matrices inside armature must be converted 2 global
		
		Matrix4f[] poseGlobal = pose.getGlobalJoints();
		
		tempJoints = poseGlobal;
		
		for(int i = 0; i < armature.getLength(); i++){
			matrices[i] = Matrix4f.mul(poseGlobal[i], armature.inverseJointMatrices[i], null);
		}
		
		//super.loadMatrices(location_joints, matrices);
		super.loadMatrices(location_joints, matrices);
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
