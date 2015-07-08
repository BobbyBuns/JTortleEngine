package textures;

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1;
	
	private float reflectivity = 0;
	
	
	
	public ModelTexture(int texture){
		this.textureID = texture;
	}
	
	public int getID(){
		return textureID;
	}

	
	
	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	
	public boolean equals(Object object){
		if(object == null || !(object instanceof ModelTexture)){
			return false;
		}else{
			ModelTexture other = (ModelTexture) object;
			if( (this.getID() == other.getID()) && (this.getReflectivity() == other.getReflectivity()) && (this.getShineDamper() == other.getShineDamper())){
				return true;
			}else{
				return false;
			}
		}
	}
	
	

}
