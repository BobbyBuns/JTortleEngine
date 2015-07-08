package toolbox;

import java.util.ArrayList;

import entities.Entity;
import environment.Scene;
import gameInstantiation.MainGameLoop;

public class SceneManager {

	public static Scene currentScene;
	
	public static void updateGame(){
		for(Entity updating : new ArrayList<>(currentScene.getEntities())){
			updating.update();
		}
	}
	
	public static void removeEntity(Scene scene, Entity entity){
		scene.removeEntity(entity);
		entity = null;
		
	}
	
	public static void moveEntity(Scene from, Scene to, Entity entity) throws Exception{
		from.removeEntity(entity);
		to.addEntity(entity);
	}
	
	public static void setScene(Scene scene){
		SceneManager.currentScene = scene;
		MainGameLoop.renderer.setScene(currentScene);
	}
	
}
