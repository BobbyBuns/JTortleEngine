package component;

import java.util.ArrayList;
import java.util.List;

import toolbox.VectorToolBox;
import toolbox.betterMath.Vector3f;
import entities.Entity;
import gameInstantiation.MainGameLoop;

public class ComponentPhysics extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6713539275402268696L;

	public static int ID = ComponentManager.getIDFromClass(ComponentPhysics.class);

	protected float mass;
	
	public Vector3f netforce = new Vector3f(0, 0, 0);
	public volatile List<Vector3f> forces = new ArrayList<Vector3f>();
	
	protected Vector3f velocity = new Vector3f(0, 0, 0);
	
	protected Vector3f angularVelocity = new Vector3f(0, 0, 0);
	
	private Vector3f acceleration = new Vector3f(0, 0, 0);
	private boolean hasGravity = true;

	public ComponentPhysics(Entity entity, float mass) {
		super(entity);
		this.mass = mass;
	}

	@Override
	public void update() {
		
	}


	public Vector3f getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	//This method is a simple method which performs the velocity verlet, I will put it in a different method once I add more to the physics engine
	public synchronized void updatePhysics(){
		positional();
		rotational();
	}
	
	private void positional(){
		calculateNetForce();
		float delta = MainGameLoop.physics.getDelta();
		Vector3f temporaryVelocity = new Vector3f(velocity);
		Vector3f tsVelocity = temporaryVelocity.times(delta);
		Vector3f p = VectorToolBox.multiply(0.5f, VectorToolBox.multiply(acceleration, delta * delta));
		Vector3f o = new Vector3f(0, 0, 0);
	    o = tsVelocity.plus(p);
	    entity.setPosition(entity.getPosition().plus(o));
		Vector3f newAcceleration = VectorToolBox.divide(netforce, mass);
		Vector3f averageAcceleration = VectorToolBox.multiply(0.5f, VectorToolBox.add(newAcceleration, acceleration));
		velocity = VectorToolBox.add(velocity, VectorToolBox.multiply(averageAcceleration, delta));
		acceleration = averageAcceleration;
		forces.clear();
	}
	
	private void rotational(){
		
	}
	
	private void calculateNetForce(){
		Vector3f netForce = new Vector3f(0, 0, 0);
		for(Vector3f force : this.forces){
			netForce = VectorToolBox.add(netForce, force);
		}
		this.netforce = netForce;
	}

	public boolean hasGravity() {
		return hasGravity;
	}



	public void setHasGravity(boolean hasGravity) {
		this.hasGravity = hasGravity;
	}

	public float getMass(){
		return mass;
	}
	
	public void setMass(float mass){
		this.mass = mass;
	}

	@Override
	public int getID() {
		return ID;
	}
	

	
	
}
