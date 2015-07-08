package entities;

import models.TexturedModel;

import org.lwjgl.util.vector.Quaternion;

import serial.ModelData;
import toolbox.betterMath.Vector3f;
import component.Component;

public interface IEntity {

	public Component getComponent(int ID);

	public boolean hasComponent(int ID);

	public Vector3f getPosition();

	public Quaternion getRotation();

	public void setRotation(Quaternion q);

	public void setRotation(Vector3f rotation);

	public void increaseRotationRelative(Vector3f add);

	public void increaseRotationWorld(Vector3f add);

	public void tranlateRelative(Vector3f axis, float distance);

	public float getScale();

	public ModelData getModelData();

	public void setPosition(Vector3f position);
	
	public void setType(String type);
	
	public String getType();
	
	public void lockType();
	
	public TexturedModel getModel();
}
