package models;

import textures.ModelTexture;

public class TexturedModel{
	
	private ModelTexture texture;
  
	private RawModel model;
	
	private String type;
	
	private boolean typeLocked = false;
	
	public TexturedModel(RawModel model, ModelTexture texture, String type){
		this.model = model;
		this.texture = texture;	
		this.type = type;
	}

	public RawModel getRawModel(){
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	public void setType(String type){
		if(!typeLocked)
			this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void lockType(){
		typeLocked = true;
	}
	
	public boolean isTypeLocked(){
		return typeLocked;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}else if(!(object instanceof TexturedModel)){
			return false;
		}else{
			TexturedModel other = (TexturedModel) object;
			if( (this.getRawModel().getVaoID() == other.getRawModel().getVaoID()) && (this.getTexture().equals(other.getTexture())) ){
				return true;
			}else{
				return false;
			}
		}
	}

}