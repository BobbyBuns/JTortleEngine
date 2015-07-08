package loading;

import gameInstantiation.MainGameLoop;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import animation.AnimatedModelData;
import serial.ModelData;
import dataManager.Utilities;

public class Loader {
	
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();
	
	public static RawModel loadToVAO(ModelData data){
		int vaoID = createVAO();
		bindIndicesBuffer(data.getIndices());
		storeDataInAttributeList(0,3,data.getVertices());
		storeDataInAttributeList(1,2,data.getTextureCoords());
		storeDataInAttributeList(2,3,data.getNormals());
		unbindVAO();
		return new RawModel(vaoID,data.getIndices().length, data, false);
	}
	
	public static RawModel loadToAnimatedVAO(AnimatedModelData data){
		int vaoID = createVAO();
		bindIndicesBuffer(data.getIndices());
		storeDataInAttributeList(0,3,data.getVertices());
		storeDataInAttributeList(1,2,data.getTextureCoords());
		storeDataInAttributeList(2,3,data.getNormals());
		storeDataInAttributeList(3,4,data.getBones());
		storeDataInAttributeList(4,4,data.getWeights());
		unbindVAO();
		
		return new RawModel(vaoID, data.getIndices().length, data, true);
	}
	
	public static int loadTexture(String fileName) {
		Texture texture;
		try {
				texture = TextureLoader.getTexture("PNG", Utilities.getTexture(fileName));
				GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
			} catch (Exception e) {
				System.err.println("Tried to load texture " + fileName + ".png , didn't work, making this a colorless model");
				e.printStackTrace();
				MainGameLoop.shutDownProgram();
				return 0;
			}
		textures.add(texture.getTextureID());
		return texture.getTextureID();
	}
	
	public static void cleanUp(){
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}
	
	private static int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private static void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	

}
