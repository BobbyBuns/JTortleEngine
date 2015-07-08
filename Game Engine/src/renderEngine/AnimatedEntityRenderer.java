package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import component.ComponentAnimated;

import shaders.AnimatedShader;
import textures.ModelTexture;
import toolbox.betterMath.Maths;
import models.RawModel;
import models.TexturedModel;
import entities.Entity;

public class AnimatedEntityRenderer implements EntityRenderer {

	private AnimatedShader shader;
	
	public AnimatedEntityRenderer(AnimatedShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	@Override
	public void render(Map<TexturedModel, List<Entity>> entities) {		
		for(TexturedModel model : entities.keySet()){
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model){
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
		
		ModelTexture texture = model.getTexture();
		shader.loadShineDamper(texture.getShineDamper());
		shader.loadReflectivity(texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	
	
	private void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity){
			Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition().convertToStandard(), entity.getRotation(), entity.getScale());
			shader.loadTransformationMatrix(transformationMatrix);
			ComponentAnimated animate = (ComponentAnimated) entity.getComponent(ComponentAnimated.ID);
			shader.loadSkeleton(animate.getArmature(), animate.getCurrentPose());
	}

}
