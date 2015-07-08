package dataManager;

import java.io.InputStream;

public class Utilities {
	

	public static final String resources = "resources";
	public static final String textures = resources + "/textures/";
	public static final String models = resources + "/models/";
	public static final String maps = resources + "/maps/";
	public static final String shaders = resources + "/shaders/";
	
	public static InputStream getTexture(String name){
		
		InputStream texture = ClassLoader.getSystemClassLoader().getResourceAsStream(textures + name + ".png");
		return texture;
		
	}
}
