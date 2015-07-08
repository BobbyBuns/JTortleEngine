package environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import terrains.Terrain;
import entities.Entity;

public class Scene {
	
	private int ID;
	
	public int getID(){
		return ID;
	}
	
	
	
	List<Entity> entities = new ArrayList<Entity>();
	List<Terrain> terrains = new ArrayList<Terrain>();
	public boolean twoDimensional = false;
	
	public Scene(File sceneFile, int ID, boolean gravity){
		this.ID = ID;
	}
	
	public Scene(List<Terrain> terrains, int ID, boolean gravity){
		this.terrains = terrains;
		this.ID = ID;
	}
	
	public List<Entity> getEntities(){
		return entities;
	}
	
	public void addEntity(Entity entity){
		if(entities.contains(entity))return;
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity){
		if(!entities.contains(entity))return;
		entities.remove(entity);
	}
	
	public List<Terrain> getTerrains(){
		return terrains;
		
	}
	
	
	
	
}