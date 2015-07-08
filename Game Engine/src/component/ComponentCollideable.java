package component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector4f;

import models.Box;
import serial.ModelData;
import toolbox.betterMath.Maths;
import toolbox.betterMath.Vector3f;
import static toolbox.VectorToolBox.isEqual;
import entities.Entity;

public class ComponentCollideable extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1460064678868757644L;

	public static int ID = ComponentManager.getIDFromClass(ComponentCollideable.class);
	
	protected Box standardAABB;

	//This is a box object that contains the AABB
	protected Box AABB;

	private Vector3f lastPosition;

	private Quaternion lastRotation;

	private float lastScale;

	public ComponentCollideable(Entity entity) {
		super(entity);
		createGeneralAABB(this.entity.getModelData());
		if(entity.getPosition() != null)
			this.lastPosition = entity.getPosition();
		if(entity.getRotation() != null)
			this.lastRotation = entity.getRotation();
		if(!Float.isNaN(entity.getScale()))
			this.lastScale = entity.getScale();
	}

	@Override
	public void update() {
		
	}


	
	//This code is very bad and must be cleaned up ASAP
	private void createGeneralAABB(ModelData data){
		Box newAABB = new Box();
		
		//processing ModelData... this is not going to be fun :P
		List<Float> positionsx = new ArrayList<Float>();
		List<Float> positionsy = new ArrayList<Float>();
		List<Float> positionsz = new ArrayList<Float>();
		
		//temporary data for processing:
		Byte type = 0;
		
		for(float coord : data.getVertices()){
			if(type == 0){
				positionsx.add(coord);
			}else if(type == 1){
				positionsy.add(coord);
			}else if(type == 2){
				positionsz.add(coord);
			}
			//then cycle
			if(type == 2){
				type = 0;
			}else{
				type = (byte) (type + 1);
			}
			
		}
		
		
		
		//now we have all of our floats sorted into x, y, and z we can now use this to find the highest and lowest of each one
		float maxx, minx, maxy, miny, maxz, minz;
		maxx = Collections.max(positionsx);
		minx = Collections.min(positionsx);
		maxy = Collections.max(positionsy);
		miny = Collections.min(positionsy);
		maxz = Collections.max(positionsz);
		minz = Collections.min(positionsz);
		//Thank god for Collections.max and Collections.min

		Vector3f minimum = new Vector3f(minx, miny, minz);
		Vector3f maximum = new Vector3f(maxx, maxy, maxz);

		Vector3f[] boundings = new Vector3f[8];
		boundings[0] = new Vector3f(minx, miny, maxz);
		boundings[1] = new Vector3f(minx, maxy, maxz);
		boundings[2] = new Vector3f(minx, maxy, minz);
		boundings[3] = new Vector3f(minx, miny, minz);
		boundings[4] = new Vector3f(maxx, miny, maxz);
		boundings[5] = new Vector3f(maxx, maxy, maxz);
		boundings[6] = new Vector3f(maxx, maxy, minz);
		boundings[7] = new Vector3f(maxx, miny, minz);
		
		
		newAABB.minimum = minimum;
		newAABB.maximum = maximum;

		newAABB.boundings = boundings;
		
		this.standardAABB = newAABB;
		

		
		updateAABB(true);
		
		data = null;
	}

	public void updateAABB(boolean first){

		if(lastPosition == null)
			lastPosition = entity.getPosition();
		if(lastRotation == null)
			lastRotation = entity.getRotation();
		if(Float.isNaN(lastScale))
			lastScale = entity.getScale();
		
		
		if(!((isEqual(lastPosition, this.entity.getPosition()))
				&& (isEqual(lastRotation, this.entity.getRotation())) 
				&& (lastScale == this.entity.getScale()))){
			
		Box newAABB = new Box(standardAABB.clone());
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(this.entity.getPosition().convertToStandard(), this.entity.getRotation(), this.entity.getScale());
		
		Vector3f boundingOne = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[0]), null));
		Vector3f boundingTwo = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[1]), null));
		Vector3f boundingThree = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[2]), null));
		Vector3f boundingFour = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[3]), null));
		Vector3f boundingFive = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[4]), null));
		Vector3f boundingSix = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[5]), null));
		Vector3f boundingSeven= toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[6]), null));
		Vector3f boundingEight = toVec3(Matrix4f.transform(transformationMatrix, toVec4(newAABB.boundings[7]), null));
		
		newAABB.boundings[0] = boundingOne;
		newAABB.boundings[1] = boundingTwo;
		newAABB.boundings[2] = boundingThree;
		newAABB.boundings[3] = boundingFour;
		newAABB.boundings[4] = boundingFive;
		newAABB.boundings[5] = boundingSix;
		newAABB.boundings[6] = boundingSeven;
		newAABB.boundings[7] = boundingEight;
		
		List<Float> xcoords = new ArrayList<Float>();
		List<Float> ycoords = new ArrayList<Float>();
		List<Float> zcoords = new ArrayList<Float>();
		
		for(Vector3f vec : Arrays.asList(newAABB.boundings)){
			xcoords.add(vec.x);
			ycoords.add(vec.y);
			zcoords.add(vec.z);
		}
		newAABB.minimum = new Vector3f(Collections.min(xcoords), Collections.min(ycoords), Collections.min(zcoords));
		newAABB.maximum = new Vector3f(Collections.max(xcoords), Collections.max(ycoords), Collections.max(zcoords));
		
			
		
		this.AABB = newAABB;

		}

		lastPosition = this.entity.getPosition();
		lastRotation = this.entity.getRotation();
		lastScale = this.entity.getScale();		
		
	}


	
	public Box getAABB(){
		return AABB.clone();
	}



	private static Vector4f toVec4(Vector3f vec){
		return new Vector4f(vec.x, vec.y, vec.z, 1);
	}

	private static Vector3f toVec3(Vector4f vec){
		return new Vector3f(vec.x, vec.y, vec.z);
	}

	@Override
	public int getID() {
		return ID;
	}	

}
