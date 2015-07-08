package component;

import java.util.HashMap;
import java.util.Map;

import models.TexturedModel;
import animation.Animation;
import animation.Armature;
import animation.Pose;
import entities.Entity;

public class ComponentAnimated extends Component{

	private static final long serialVersionUID = 4042597555833258219L;

	public static int ID = ComponentManager.getIDFromClass(ComponentAnimated.class);

	private static Map<TexturedModel, Armature> armatures = new HashMap<TexturedModel, Armature>();
	private Pose currentPose;
	
	public ComponentAnimated(Entity entity, Pose referencePose) {
		super(entity);
		if(!entity.getModel().getRawModel().isAnimated()){
			System.err.println("WARING!!  YOU TRIED TO ADD INCOMPATIBLE COMPONENT, ComponentAnimated, TO A ENTITY THAT WAS NOT RIGGED!!!  ABORTING NOW!!!!!!");
			System.exit(1);
		}
		
		if(entity.getType() != "animatedEntity"){
			entity.setType("animatedEntity");
			entity.lockType();
		}else{entity.lockType();}
		
		if(!armatures.containsKey(entity.getModel())){
			armatures.put(entity.getModel(), new Armature(referencePose.joints));
		}
		
		currentPose = referencePose;
	}

	public void setPose(Pose pose){
		currentPose = pose;
	}
	
	public void setPose(Pose pose, float duration){
		
	}
	
	public void playAnimation(Animation animation){
		
	}
	
	public void startAnimation(Animation animation){
		
	}
	
	public void stopCurrentAnimation(){
		
	}
	
	public Armature getArmature(){
		return armatures.get(entity.getModel());
	}
	
	public Pose getCurrentPose(){
		return this.currentPose;
	}

	@Override
	public void update() {
		
	}

	@Override
	public int getID() {
		return ID;
	}

	



	
}
