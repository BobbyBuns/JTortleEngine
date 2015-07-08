package examplePackage;

import org.lwjgl.util.vector.Quaternion;

import component.ComponentCollideable;
import component.ComponentPhysics;
import entities.Entity;
import loading.Loader;
import loading.StaticModelLoader;
import models.RawModel;
import models.TexturedModel;
import serial.ModelData;
import toolbox.betterMath.Vector3f;
import textures.ModelTexture;

public class ExampleEntityCube extends Entity{

	private static ModelData data = StaticModelLoader.loadOBJ("cube");
	private static RawModel rawModel = Loader.loadToVAO(data);
	private static TexturedModel model= new TexturedModel(rawModel, new ModelTexture(Loader.loadTexture("cubeTexture")), "staticEntity");
	
	public ExampleEntityCube(Vector3f position, Quaternion rotation, float scale){		
		super(model, position, rotation, scale);
		addComponent(new ComponentPhysics(this, 70));
		addComponent(new ComponentCollideable(this));
		model.getTexture().setShineDamper(1);
		model.getTexture().setReflectivity(0);
	}
	
}