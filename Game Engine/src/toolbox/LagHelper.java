package toolbox;

import org.lwjgl.Sys;

public class LagHelper {

	private static long startTime;
	private static float timeBetween;

	private static boolean enabled;

	public static void start(){
		if(enabled)
			return;
		else{
			startTime = getCurrentTime();
			enabled = true;
		}
	}

	public static float finish(){
		return finish(0f);
	}

	public static float finish(float minimum){
		if(enabled){
			long currentFrameTime = getCurrentTime();
			timeBetween = (currentFrameTime - startTime)/1000f;
			if(timeBetween >= minimum)
				System.out.println(timeBetween);
			enabled = false;
			return timeBetween;
		}
		return 0;
	}

	private static long getCurrentTime(){
		return Sys.getTime()*1000 / Sys.getTimerResolution();
	}
	
}
