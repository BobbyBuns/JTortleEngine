package annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationManager {
	
	private static List<Method> preInitMethods = new ArrayList<Method>();
	private static List<Method> postInitMethods = new ArrayList<Method>();
	private static List<Method> updateMethods = new ArrayList<Method>();
	private static List<Method> preRenderMethods = new ArrayList<Method>();
	private static List<Method> postRenderMethods = new ArrayList<Method>();
	private static List<Method> cleanUpMethods = new ArrayList<Method>();
	private static List<Method> prePhysicsUpdateMethods = new ArrayList<Method>();
	private static List<Method> postPhysicsUpdateMethods = new ArrayList<Method>();
	private static List<Method> physicsInitMethods = new ArrayList<Method>();
	private static List<Method> physicsCleanUpMethods = new ArrayList<Method>();
	
	
	
	public static void addListenerClass(Class<?> klass){
		
		for(Method method : klass.getDeclaredMethods()){
			method.setAccessible(true);
			List<Annotation> annotations = Arrays.asList(method.getDeclaredAnnotations());
			
			
			
			if(annotations.size() == 0){
				continue;
			}
			
			
			for(Annotation annotation : annotations){
				
				if(annotation instanceof PreInit){
					preInitMethods.add(method);
					continue;
				}
				
				if(annotation instanceof PostInit){
					postInitMethods.add(method);
					continue;
				}
				
				if(annotation instanceof Update){
					updateMethods.add(method);
					continue;
				}
				
				if(annotation instanceof PreRender){
					preRenderMethods.add(method);
					continue;
				}
				
				if(annotation instanceof PostRender){
					postRenderMethods.add(method);
					continue;
				}
				
				if(annotation instanceof CleanUp){
					cleanUpMethods.add(method);
					continue;
				}
				
				if(annotation instanceof PrePhysicsUpdate){
					prePhysicsUpdateMethods.add(method);
					continue;
				}
				
				if(annotation instanceof PostPhysicsUpdate){
					postPhysicsUpdateMethods.add(method);
					continue;
				}
				
				if(annotation instanceof PhysicsInit){
					physicsInitMethods.add(method);
				}
				
				if(annotation instanceof PhysicsCleanup){
					physicsCleanUpMethods.add(method);
				}
			}
		}
	}
	
	public static void preInitCall(){
		for(Method method : preInitMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void postInitCall(){
		for(Method method : postInitMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateCall(){
		for(Method method : updateMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void preRenderCall(){
		for(Method method : preRenderMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void prePhysicsUpdateCall(){
		for(Method method : prePhysicsUpdateMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void postPhysicsUpdateCall(){
		for(Method method : postPhysicsUpdateMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void postRenderCall(){
		for(Method method : postRenderMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void cleanUpCall(){
		for(Method method : cleanUpMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void physicsInitCall(){
		for(Method method : physicsInitMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void physicsCleanUpCall(){
		for(Method method : physicsCleanUpMethods){
			try {
				method.invoke(null);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

}
