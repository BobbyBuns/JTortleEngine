package models;

import serial.ModelData;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private ModelData data;
	private boolean animated;
	
	public RawModel(int vaoID, int vertexCount, ModelData data, boolean animated){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.data = data;
		this.animated = animated;
	}

	public int getVaoID(){
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public ModelData getModelData(){
		return data;
	}
	
	public boolean isAnimated(){
		return animated;
	}
	

}
