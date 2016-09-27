package com.deathclaws.thediary.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Messenger {

	public final static Messenger defaut;
	
	static {
		defaut = new Messenger(); 
	}
	
	private final Map<Class<?>, List<ActionMessage<? extends Message>>> actions;
	
	private Messenger() {
		actions = new HashMap<Class<?>, List<ActionMessage<? extends Message>>>();
	}
	
    public <T extends Message> void register(Class<T> t, ActionMessage<T> action) {
    	List<ActionMessage<? extends Message>> listAction = actions.get(t);
    	if(listAction == null) listAction = new ArrayList<>();
    	listAction.add(action);
    	actions.put(t, listAction);
    }

    @SuppressWarnings("unchecked")
	public <T extends Message> void send(T message) {
    	Class<?> clazz = message.getClass();
    	List<ActionMessage<? extends Message>> listAction = actions.get(clazz);
    	if(listAction == null) return;
    	for (ActionMessage<? extends Message> actionMessage : listAction) {
    		((ActionMessage<Message>) actionMessage).invoke( message);
		}
    }
	
}
