package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import camera.Camera;
import entities.Entity;
import environment.Scene;
import models.TexturedModel;
import shaders.AnimatedShader;
import shaders.EntityShader;
import shaders.Light;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;
import toolbox.SceneManager;

public class MasterRenderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private static Matrix4f projectionMatrix;
	
	private StaticEntityRenderer staticEntityRenderer;
	private StaticShader staticEntityShader = new StaticShader();
	
	private AnimatedEntityRenderer animatedEntityRenderer;
	private AnimatedShader animatedEntityShader = new AnimatedShader();
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private List<RenderType> types = new ArrayList<RenderType>();
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>(); 
	
	public void setScene(Scene scene){
		this.terrains = scene.getTerrains();
	}
	
	public MasterRenderer(){
		createProjectionMatrix();
		staticEntityRenderer = new StaticEntityRenderer(staticEntityShader, projectionMatrix);
		animatedEntityRenderer = new AnimatedEntityRenderer(animatedEntityShader, projectionMatrix);
		types.add(new RenderType("staticEntity", staticEntityRenderer, staticEntityShader));
		types.add(new RenderType("animatedEntity", animatedEntityRenderer, animatedEntityShader));
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	//TODO: parameters too fixed
	public void render(Light sun, Camera camera){
		prepare();
		
		for(int i = 0; i < types.size(); i++){
			Map<TexturedModel, List<Entity>> newEntities = new HashMap<TexturedModel, List<Entity>>();
			
			for (Map.Entry <TexturedModel, List<Entity>> entry : entities.entrySet()){
				
				if(entry.getKey().getType() == types.get(i).name){					
					newEntities.put(entry.getKey(), entry.getValue());
				}
				
			}
			
			renderEntityType(types.get(i).renderer, types.get(i).shader, newEntities);
		}
		
		terrainShader.start();
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		entities.clear();
	}
	
	private void renderEntityType(EntityRenderer renderer, EntityShader shader, Map<TexturedModel, List<Entity>> entities){
		shader.start();
		shader.load();
		renderer.render(entities);
		shader.stop();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	private void processEntity(Entity entity){
			TexturedModel entitymodel = entity.getModel();
			List<Entity>batch = entities.get(entitymodel);
			if(batch!=null){
				batch.add(entity);
			}else{
				List<Entity> newBatch = new ArrayList<Entity>();
				newBatch.add(entity);
				entities.put(entitymodel, newBatch);
			}
	}

	public void addRenderType(RenderType type){
		boolean contained = false;
		for(RenderType renderType : types){
			if(type.name.equalsIgnoreCase(renderType.name)){
				contained = true;
				break;
			}
		}
		if(!contained)
			types.add(type);
	}
	
	public void cleanUp(){
		staticEntityShader.cleanUp();
		terrainShader.cleanUp();
	}
	
	
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 1, 1, 1);
		
		for(Entity entity : new ArrayList<>(SceneManager.currentScene.getEntities())){
			processEntity(entity);
		}
	}
	
	
	private static void createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
