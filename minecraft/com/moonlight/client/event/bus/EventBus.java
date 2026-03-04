package com.moonlight.client.event.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.event.Event;
import com.moonlight.client.event.annotations.EventLink;

public class EventBus {

	private CopyOnWriteArrayList<Object> registers = new CopyOnWriteArrayList<>();

	public void register(Object object) {
		if(registers.contains(object)) return;
		registers.add(object);
	}
	
	public void unregister(Object object) {
		registers.remove(object);
	}
	
	public void handle(Event event) {
	    for (Object o : registers) {
	        Class<?> cls = o.getClass();
	        Method[] methods = cls.getDeclaredMethods();
	        for (Method method : methods) {
	            if (method.isAnnotationPresent(EventLink.class) &&
	                    method.getParameterCount() == 1 &&
	                    method.getParameterTypes()[0] == event.getClass() &&
	                    method.getDeclaringClass().isAssignableFrom(cls)) {
	                try {
	                    method.invoke(o, event);
	                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }
	    }
	}
	
}
