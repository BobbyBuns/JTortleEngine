package gameInstantiation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

import loading.Loader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import camera.Camera;
import toolbox.betterMath.Vector3f;
import annotations.AnnotationManager;
import physics.PhysicsManager;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import shaders.Light;
import terrains.Terrain;
import toolbox.SceneManager;
import entities.ControlManager;
import environment.Scene;

public class MainGameLoop {
	
	public static MasterRenderer renderer;
	
	public static final Scene defaultScene = new Scene(new ArrayList<Terrain>(), 0, true);
	
	public static Light sun;
	
	public static Camera camera;
	
	public static PhysicsManager physics = new PhysicsManager();
	
	public static Loader loader;

	private static boolean running = true;

	private static GameProperties properties;

	private static String title;

	public static int terrainSize = 400;
	
	public static void main(String[] args) {

		instantiateGame();
		
		AnnotationManager.preInitCall();
		
		prepare();
		
		AnnotationManager.postInitCall();
		
		gameLoop();
		
		running = false;
		
		cleanUp();
	}
	
	private static void instantiateGame(){
		properties = parser();

		boolean didFind = false;

		for(Method method : properties.getMainClass().getMethods()){
			if(!method.getName().equals("instantiateGame"))
				continue;
			else{
				try {
					method.invoke(null);
					didFind = true;
					break;
				} catch (Exception e){
					System.err.println("ERROR: Could not run instantiating method.  Program will now close. Error Stack Trace:");
					e.printStackTrace();
					shutDownProgram();
				}
			}
		}
		
		if(!didFind){
			System.err.println("ERROR: Could not locate main method!");	
			shutDownProgram();
		}
		
		title = properties.getTitle();
	}
	
	private static GameProperties parser(){
		GameProperties properties = null;
		try{
			String title = "";
			Class<?> cl = null;
			
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("resources/GameProperties.txt");
			BufferedReader input = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = input.readLine()) != null) {
				if(line.startsWith("Title")){
					title = line.substring(7);
					continue;
				}
				if(line.startsWith("Main: ")){
					String mainPath = line.substring(6);
					cl=Class.forName(mainPath);
				}
			}

			properties = new GameProperties(title, cl);
		
		}catch(Exception e){
			System.err.println("ERROR: Could not load GameProperties.txt properly! The game will now shut down!  Stack Trace:");
			e.printStackTrace();
			shutDownProgram();
		}
		
		return properties;
	}
	
	private static void prepare(){
		DisplayManager.createDisplay(title);
		
		loader = new Loader();
		
		sun = new Light(new Vector3f(0,15,0), new Vector3f(1, 1, 1));
		
		renderer = new MasterRenderer();
		
		SceneManager.setScene(defaultScene);
		
		physics.start();
		
	}
	
	private static void gameLoop(){
		while(running && !Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			update();
			AnnotationManager.preRenderCall();
			render();
			AnnotationManager.postRenderCall();
		}
	}
	
	private static void update(){
		AnnotationManager.updateCall();
		
		SceneManager.updateGame();
		
		ControlManager.updateControl();
	}
	
	private static void render(){
		renderer.render(sun, camera);
		
		DisplayManager.updateDisplay();
	}
	
	private static void cleanUp(){
		AnnotationManager.cleanUpCall();
		renderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.closeDisplay();
		System.exit(0);
	}

	public static void shutDownProgram(){
		
		running = false;
	}
	
	public static boolean getRunning(){
		return running;
	}
}
