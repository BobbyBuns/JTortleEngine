package animation;

import java.util.ArrayList;
import java.util.List;

public class Animation {


	//in seconds
	@SuppressWarnings("unused")
	private float duration = 0;
	
	List<Keyframe> keyframes = new ArrayList<Keyframe>();
	
	public void addKeyframe(Keyframe keyframe){
		keyframes.add(keyframe);
		updateDuration();
	}

	private void updateDuration(){
		float longest = 0;
		for(Keyframe keyframe : keyframes){
			if(keyframe.time > longest)
				longest = keyframe.time;
		}
	}
	
}
