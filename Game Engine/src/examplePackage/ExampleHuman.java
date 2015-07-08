package examplePackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import loading.Loader;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.util.vector.Quaternion;

import animation.AnimatedModelData;
import component.ComponentAnimated;
import dataManager.Utilities;
import textures.ModelTexture;
import toolbox.betterMath.Vector3f;
import entities.Entity;

public class ExampleHuman extends Entity{
	
	private static AnimatedModelData data = getModelData("human");
	private static RawModel rawModel = Loader.loadToAnimatedVAO(data);
	private static ModelTexture texture = new ModelTexture(Loader.loadTexture("cubeTexture"));
	private static TexturedModel texturedModel = new TexturedModel(rawModel, texture, "animatedEntity");
	
	public ExampleHuman(Vector3f position,
			Quaternion rotation, float scale) {
		super(texturedModel, position, rotation, scale);		
		addComponent(new ComponentAnimated(this, data.getBindPose()));
		
		texturedModel.getTexture().setShineDamper(1);
		texturedModel.getTexture().setReflectivity(0);
	}

	private static AnimatedModelData getModelData(String fileName){
		try{
			InputStream fs = ClassLoader.getSystemClassLoader().getResourceAsStream(Utilities.models + fileName + ".bin");
			ObjectInputStream os = new ObjectInputStream(fs);
			AnimatedModelData data = (AnimatedModelData) os.readObject();
			os.close();
			fs.close();
			return data;
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
