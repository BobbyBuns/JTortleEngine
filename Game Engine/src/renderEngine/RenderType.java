package renderEngine;

import shaders.EntityShader;

public class RenderType {

	String name;
	
	EntityRenderer renderer;
	
	EntityShader shader;
	
	public RenderType(String name, EntityRenderer renderer, EntityShader shader){
		this.name = name;
		this.renderer = renderer;
		this.shader = shader;
	}
	
}
