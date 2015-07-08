package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector4f;

import component.Component;
import serial.ModelData;
import toolbox.betterMath.Maths;
import toolbox.betterMath.Vector3f;
import renderEngine.DisplayManager;
import models.TexturedModel;


//entity class, MovingEntity and StaticEntity extend it, this class covers the bare minimum for rendering.
public class Entity implements IEntity{
	
	public Matrix4f customTransform = null;
	
	//stores the position, this variable is used when creating the transformation matrix
	protected Vector3f position = new Vector3f(0, 0, 0);
	//this Vector4f stores the rotation of the entity
	protected Quaternion rotation = new Quaternion(0, 0, 0, 1);
	//this stores the model data (vertices, texture coords, etc., it is used for generating the AABB and then it is deleted.
	protected ModelData data;
	//this variable stores the scale, I will never use it too much, but maybe one day it will be useful
	protected float scale;
	
	//this is the model, it stores the texture and data, the MasterRenderer uses it
	private TexturedModel model;

	private List<Component> components = new ArrayList<Component>();
	
	public Entity(TexturedModel model, Vector3f position, Quaternion rotation, float scale) {
		this.model = model;
		this.position = position;
		Quaternion.normalise(rotation, rotation);
		this.rotation = rotation;
		this.scale = scale;
		this.data = model.getRawModel().getModelData();
	}
	
	public Entity(TexturedModel model, Vector3f position){
		this.model = model;
		this.position = position;
		this.rotation = new Quaternion(0, 0, 0, 1);
		this.scale = 1;
		this.data = model.getRawModel().getModelData();
	}
	
	//Just a few getters
	public TexturedModel getModel(){
		return model;
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}


	@Override
	public void setRotation(Quaternion q) {
		this.rotation = q;
	}

	@Override
	public void setRotation(Vector3f rotation){
		this.rotation = Maths.fromEuler(rotation.convertToStandard());
	}
	
	@Override
	public Quaternion getRotation(){
		return rotation;
	}

	@Override
	public void increaseRotationRelative(Vector3f add){
		Quaternion.mul(rotation, Maths.fromEuler(add.convertToStandard()), rotation);
	}
	
	@Override
	public void increaseRotationWorld(Vector3f add){
		Vector4f xAlign = new Vector4f(1, 0, 0, (float) Math.toRadians(add.x));
		Vector4f yAlign = new Vector4f(0, 1, 0, (float) Math.toRadians(add.y));
		Vector4f zAlign = new Vector4f(0, 0, 1, (float) Math.toRadians(add.z));		

		Quaternion xq = Maths.fromAxisAligned(xAlign);
		Quaternion yq = Maths.fromAxisAligned(yAlign);
		Quaternion zq = Maths.fromAxisAligned(zAlign);		
		
		Quaternion.mul(rotation, xq, rotation);
		Quaternion.mul(yq, rotation, rotation);
		Quaternion.mul(rotation, zq, rotation);		
	}

	@Override
	public float getScale(){
		return scale;
	}
	
	public void update(){

		if(rotation.length() != 1)
			rotation.normalise();
		
		for(Component component: components){
			component.update();
		}
	}

	@Override
	public Component getComponent(int ID) {
		for(Component component : components){
			if(component.getID() == ID)
				return component;
		}
		
		return null;
		
	}

	@Override
	public void tranlateRelative(Vector3f axis, float distance){
		Vector3f forwardVector = new Vector3f();
		
		forwardVector = new Vector3f(axis.x, axis.y, -axis.z);

		forwardVector.applyQuaternion(rotation);
		
		forwardVector.multiply(DisplayManager.getDelta() * 10 * distance);
		
		position.add(forwardVector);
	}
	
	@Override
	public boolean hasComponent(int ID){
		for(Component component: components){
			if(component.getID() == ID)
				return true;
		}
		return false;
	}

	public void addComponent(Component component){
		if(checkComponent(component))
			this.components.add(component);
		else
			System.err.println("WARNING, YOU TRIED TO APPLY COMPONENT " + component.toString() + "TO ENTITY " + this.toString() + " AND FAILED!");
	}

	@Override
	public ModelData getModelData() {
		return data;
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	@Override
	public void setType(String type){
		this.model.setType(type);
	}
	
	@Override
	public String getType(){
		return this.model.getType();
	}
	
	@Override
	public void lockType(){
		model.lockType();
	}

	private boolean checkComponent(Component component){
		//things to check: whether the ID was generated correctly, whether it follows correct format for ID retrieving
		

		return true;
	}
	
}