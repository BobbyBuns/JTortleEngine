package component;

import java.util.ArrayList;
import java.util.List;

public class ComponentManager {

	private static List<Class<?>> componentTypes = new ArrayList<Class<?>>();
	
	public static int getIDFromClass(Class<?> component){
		if(!Component.class.isAssignableFrom(component)){
			return -1;
		}		
		if(componentTypes.contains(component)){
			return componentTypes.indexOf(component);
		}else{
			componentTypes.add(component);
			doNothing();
			int ID = componentTypes.indexOf(component);
			return ID;
		}
	}

	private static void doNothing(){
		
	}
	
}
