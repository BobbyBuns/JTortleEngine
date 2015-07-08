package examplePackage;

import java.util.ArrayList;
import java.util.List;

import loading.Loader;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector4f;

import component.ComponentPhysics;
import toolbox.betterMath.Maths;
import toolbox.betterMath.Vector3f;
import entities.ControlManager;
import environment.Scene;
import gameInstantiation.MainGameLoop;
import terrains.HeightMap;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.SceneManager;
import annotations.AnnotationManager;
import annotations.PhysicsInit;
import annotations.PostInit;
import annotations.PrePhysicsUpdate;
import annotations.Update;

public class ExampleCore {
	
	public static Scene sceneOne;
	public static Scene sceneTwo;

	public static List<Terrain> terrains;
	public static List<Terrain> emptyTerrains;
	
	private static Terrain terrain;
	private static Terrain terrain2;
	private static Terrain terrain3;
	private static Terrain terrain4;
	
	public static ExampleEntityCube entity;
	public static ExampleHuman human;
	
	//PreInit, PostInit, Update, PreRender, PostRender, CleanUp

	public static void instantiateGame(){
		AnnotationManager.addListenerClass(ExampleCore.class);
		MainGameLoop.camera = new ExampleCamera(new Vector3f(0, 8, 20), new Vector3f(0, 0, 0));
	}

	static Quaternion a;
	static Quaternion b;
	static Quaternion c;
	
	@PostInit
	public static void init(){
		MainGameLoop.terrainSize = 200;

		terrain = new Terrain(0,0,MainGameLoop.loader, new HeightMap("exampleMap"), new ModelTexture(Loader.loadTexture("exampleMapTexture")));
		terrain2 = new Terrain(0,-1,MainGameLoop.loader, new HeightMap("exampleMap"), new ModelTexture(Loader.loadTexture("exampleMapTexture")));
		terrain3 = new Terrain(-1,0,MainGameLoop.loader, new HeightMap("exampleMap"), new ModelTexture(Loader.loadTexture("exampleMapTexture")));
		terrain4 = new Terrain(-1,-1,MainGameLoop.loader, new HeightMap("exampleMap"), new ModelTexture(Loader.loadTexture("exampleMapTexture")));
		
		terrains = new ArrayList<Terrain>();
		emptyTerrains = new ArrayList<Terrain>();
		
		sceneOne = new Scene(terrains, 0, true);
		sceneTwo = new Scene(emptyTerrains, 1, true);
		
		terrains.add(terrain);
		terrains.add(terrain2);
		terrains.add(terrain3);
		terrains.add(terrain4);
		
		SceneManager.setScene(sceneOne);
		
		entity = new ExampleEntityCube(new Vector3f(0, 0, 0), Maths.fromAxisAligned(new Vector4f(0, 0, 0, 0)), 1);
		human = new ExampleHuman(new Vector3f(0, 10, 0), new Quaternion(0, 0, 0, 1), 1);
		
		
		
		sceneOne.addEntity(entity);
		sceneOne.addEntity(human);
		ComponentPhysics physics = (ComponentPhysics) entity.getComponent(ComponentPhysics.ID);
		physics.setHasGravity(false);
		
		ControlManager.setControlledObject((ExampleCamera) MainGameLoop.camera);
	}
	
	@Update
	public static void test(){	
		
		ExampleCamera camera = (ExampleCamera) MainGameLoop.camera;
		if(Keyboard.isKeyDown(Keyboard.KEY_1)){
			camera.speed = 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_2)){
			camera.speed = 2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_3)){
			camera.speed = 3;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_4)){
			camera.speed = 4;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_5)){
			camera.speed = 5;
		}		
		if(Keyboard.isKeyDown(Keyboard.KEY_6)){
			camera.speed = 6;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_7)){
			camera.speed = 7;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_8)){
			camera.speed = 8;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_9)){
			camera.speed = 9;
		}
		

		if(Keyboard.isKeyDown(Keyboard.KEY_G)){
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_O)){
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		    GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		    GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_N)){
			
		
		}
		
		if(Mouse.isGrabbed()){
			float x = Mouse.getDX();
			float y = Mouse.getDY();
			if((y != 0) || (x != 0))
				camera.increaseRotationWorld(new Vector3f((float) y * 0.25f, (float) -x * 0.25f, 0));
		}
	}
	
	@PhysicsInit
	public static void test2(){
		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@PrePhysicsUpdate
	public static void test3(){
		if(entity != null){
			if(entity.hasComponent(ComponentPhysics.ID)){
				ComponentPhysics entityForce = (ComponentPhysics) entity.getComponent(ComponentPhysics.ID);
				if(Keyboard.isKeyDown(Keyboard.KEY_U)){
					entityForce.forces.add(new Vector3f(0, 70000, 0));
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_K)){
					entityForce.forces.add(new Vector3f(200000, 200000, 200000));
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_R)){
					entityForce.setVelocity(new Vector3f(0, 0, 0));
					entity.setPosition(new Vector3f(0, 25, 0));
				}
			}	
		}		
	}
	
}