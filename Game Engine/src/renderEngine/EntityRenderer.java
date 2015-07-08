package renderEngine;

import java.util.List;
import java.util.Map;

import models.TexturedModel;
import entities.Entity;

public interface EntityRenderer {
	
	public void render(Map<TexturedModel, List<Entity>> entities);
	
}