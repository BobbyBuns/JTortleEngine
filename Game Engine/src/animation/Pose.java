package animation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import toolbox.betterMath.Maths;

public class Pose implements Serializable{

	private static final long serialVersionUID = 884137308397185277L;

	public Joint joints[] = new Joint[100];

	private int length;
	
	public Pose(Joint joints[]){
		this.joints = joints;
	}
	
	public Joint[] getLocalJoints(){
		return joints;
	}
	
	public Matrix4f[] getGlobalJoints(){

		joints = clean(joints);

		length = joints.length;
		
		Matrix4f jointMatrices[] = new Matrix4f[joints.length];
		
		List<Integer> usedRoots = new ArrayList<Integer>();
 
		List<Integer> currentParents = new ArrayList<Integer>();
		List<Integer> nextParents = new ArrayList<Integer>();
		boolean readyForNext = true;
		boolean more = true;
		while(more){
			for(int i = 0; i < joints.length; i++){
				if((joints[i].parentIndex > joints.length-1) && !usedRoots.contains(i)){
					Matrix4f rootTransform = Maths.createTransformationMatrix(joints[i].offset, joints[i].rotation, 1);
					jointMatrices[i] = rootTransform;
					currentParents.add(i);
					readyForNext = false;
					usedRoots.add(i);
					continue;
				}
				
				if(currentParents.contains(joints[i].parentIndex)){
					Matrix4f localTransform = Maths.createTransformationMatrix(joints[i].offset, joints[i].rotation, 1);
					Matrix4f globalTransform = new Matrix4f();
					Matrix4f.mul(jointMatrices[joints[i].parentIndex], localTransform, globalTransform);
					jointMatrices[i] = globalTransform;
					if(joints[i].childrenIndices.length != 0){
						nextParents.add(i);
					}
				}
			
			}
			
			if(nextParents.size() == 0){
				if(!readyForNext)
					readyForNext = true;
				else
					more = false;
				currentParents.clear();
			}else{
				currentParents.clear();
				currentParents.addAll(nextParents);
				nextParents.clear();
			}
		}

		return jointMatrices;
	}

	public int getLength(){
		return length;
	}

	private static Joint[] clean(Joint[] joints){
		List<Joint> jointList = new ArrayList<Joint>();
		for(int i = 0; i < joints.length; i++){
			if(joints[i] != null){
				jointList.add(joints[i]);
			}
		}

		Joint finishedArray[] = new Joint[jointList.size()];
		
		for(int i = 0; i < finishedArray.length; i++){
			finishedArray[i] = jointList.get(i);
		}
		
		return finishedArray;
	}
	
}
