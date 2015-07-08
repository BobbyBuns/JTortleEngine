//Applies forces to all loaded to entities.
package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Box;

import org.lwjgl.Sys;

import component.ComponentCollideable;
import component.ComponentPhysics;
import toolbox.betterMath.Vector3f;
import annotations.AnnotationManager;
import entities.Entity;
import gameInstantiation.MainGameLoop;
import toolbox.SceneManager;

public class PhysicsManager extends Thread{
	
	public static final int UPDATE_CAP = 120;
	
	private long lastFrameTime;
	private float delta;
	
	private Map<Entity, Entity> possibleCollisions = new HashMap<Entity, Entity>();
	
	public void run(){

		this.setPriority(MAX_PRIORITY);
		
		lastFrameTime = getCurrentTime();
		
		AnnotationManager.physicsInitCall();
		
		while(MainGameLoop.getRunning()){
			checkAll();
			AnnotationManager.prePhysicsUpdateCall();
			for(Entity physics : new ArrayList<>(SceneManager.currentScene.getEntities())){
				if(physics.hasComponent(ComponentPhysics.ID)){
					ComponentPhysics entity = (ComponentPhysics) physics.getComponent(ComponentPhysics.ID);
					
					if(entity.hasGravity()){
						entity.forces.add(gravity(entity));
					}
					
					entity.updatePhysics();
				}

				if(physics.hasComponent(ComponentCollideable.ID)){
					ComponentCollideable collide = (ComponentCollideable) physics.getComponent(ComponentCollideable.ID);
					collide.updateAABB(false);
				}
			}
			AnnotationManager.postPhysicsUpdateCall();			
			possibleCollisions.clear();
			
			try {
				Thread.sleep(this.getSleepTime());
			} catch (InterruptedException e) {
				System.err.println("Problem with the timing in the physics thread!");
				e.printStackTrace();
			}
			
			long currentFrameTime = getCurrentTime();
			delta = (currentFrameTime - lastFrameTime)/1000f;
			lastFrameTime = currentFrameTime;
			
		}
		
		AnnotationManager.physicsCleanUpCall();
		
	}
	
	private void checkAll(){
		List<Entity> checkedEntities = new ArrayList<Entity>();
		for(Entity entity : SceneManager.currentScene.getEntities()){
			if(checkedEntities.contains(entity)){
				continue;
			}
			for(Entity entity2 : SceneManager.currentScene.getEntities()){
				if(entity2 != entity){
					if(possibleCollisions.get(entity2) != entity){
						if(closeEnoughToCheck(entity, entity2)){
							ComponentCollideable collide = (ComponentCollideable) entity.getComponent(ComponentCollideable.ID);
								if(doesCollideWithAABB(collide.getAABB(), collide.getAABB())){
									possibleCollisions.put(entity, entity2);
								}
						}
					}
				}
			}
			checkedEntities.add(entity);
		}
	}

	private boolean closeEnoughToCheck(Entity entity1, Entity entity2){
		float added = entity1.getModelData().getFurthestPoint() + entity2.getModelData().getFurthestPoint();
		return (entity1.getPosition().minus(entity2.getPosition())).getMagnitude() < added;
		
	}
	
	public static boolean doesCollideWithAABB(Box first, Box last){
		Vector3f fcorner1 = new Vector3f(first.minimum);
		Vector3f fcorner2 = new Vector3f(first.maximum);
		Vector3f lcorner1 = new Vector3f(last.minimum);
		Vector3f lcorner2 = new Vector3f(last.maximum);
		
		Byte counter = 0;
		
		if(((fcorner1.x <= lcorner2.x) && (fcorner1.x >= lcorner1.x))||
				((fcorner2.x <= lcorner2.x) && (fcorner2.x >= lcorner1.x))||
				((lcorner1.x <= fcorner2.x) && (lcorner1.x >= fcorner1.x))||
				((lcorner2.x <= fcorner2.x) && (lcorner2.x >= fcorner1.x))
				){
			counter++;
		}
		
		if(((fcorner1.y <= lcorner2.y) && (fcorner1.y >= lcorner1.y))||
				((fcorner2.y <= lcorner2.y) && (fcorner2.y >= lcorner1.y))||
				((lcorner1.y <= fcorner2.y) && (lcorner1.y >= fcorner1.y))||
				((lcorner2.y <= fcorner2.y) && (lcorner2.y >= fcorner1.y))
				){
			counter++;
		}
		
		if(((fcorner1.z <= lcorner2.z) && (fcorner1.z > lcorner1.z))||
				((fcorner2.z <= lcorner2.z) && (fcorner2.z >= lcorner1.z))||
				((lcorner1.z <= fcorner2.z) && (lcorner1.z >= fcorner1.z))||
				((lcorner2.z <= fcorner2.z) && (lcorner2.z >= fcorner1.z))
				){
			counter++;
		}
		
		if(counter == 3){
			return true;
		}else{
			return false;
		}
	}

	public int getNumberOfPossibleCollisions(){
		return possibleCollisions.size();
	}
	
	private long getSleepTime(){
		int target = 1000/PhysicsManager.UPDATE_CAP;
		if(delta > target){
			return 0;
		}else{
			return (long) (target - delta);
		}	
	}
	
	public Vector3f gravity(ComponentPhysics entity){
		float gravityFloat =  -9.8f * entity.getMass();
		return new Vector3f(0, gravityFloat, 0);
	}
	
	public float getDelta(){
		return this.delta;
	}
	
	private static long getCurrentTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
}