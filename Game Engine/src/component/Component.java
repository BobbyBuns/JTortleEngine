package component;

import java.io.Serializable;

import entities.Entity;
import entities.IEntity;

public abstract class Component implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4460129439143154380L;
	
	protected transient IEntity entity;

	public Component(Entity entity){
		this.entity = entity;
	}
	
	public abstract void update();

	public abstract int getID();
	
}