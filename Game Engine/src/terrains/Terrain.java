package terrains;

import gameInstantiation.MainGameLoop;

import org.lwjgl.util.vector.Vector3f;

import loading.Loader;
import models.RawModel;
import serial.ModelData;
import textures.ModelTexture;

public class Terrain {
	public static float SIZE = MainGameLoop.terrainSize;
	public int VERTEX_COUNT;
	
	private float x;
	private float z;
	private RawModel model;
	private ModelTexture texture;
	private HeightMap heightMap;
	
	public Terrain(int gridX, int gridZ, Loader loader, HeightMap heightMap, ModelTexture texture){
		this.heightMap = heightMap;
		this.VERTEX_COUNT = heightMap.verticeSideAmount;
		this.texture = texture;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader);
	}
	
	public ModelTexture getTexture(){
		return texture;
	}
	

	public float getX(){
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	private RawModel generateTerrain(Loader loader){
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float x = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float z = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3] = x;
				vertices[vertexPointer*3+1] = heightMap.getHeight(i, j);
				vertices[vertexPointer*3+2] = z;
				float heightL = 0, heightR = 0, heightD = 0, heightU = 0;
				try{heightL = heightMap.getHeight(j-1, i);}catch(Exception e){}
				try{heightR = heightMap.getHeight(j+1, i);}catch(Exception e){}
				try{heightD = heightMap.getHeight(j, i-1);}catch(Exception e){}
				try{heightU = heightMap.getHeight(j, i+1);}catch(Exception e){}
				Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD-heightU);
				normal.normalise();
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return Loader.loadToVAO(new ModelData(vertices, textureCoords, normals, indices, 0));
	}
}	