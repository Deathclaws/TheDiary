package com.deathclaws.thediary.messaging;

import java.util.HashMap;
import java.util.Map;

public final class Messenger {

	public final static Messenger defaut;
	
	static {
		defaut = new Messenger(); 
	}
	
	private final Map<Class<?>, ActionMessage<? extends Message>> actions;
	
	private Messenger() {
		actions = new HashMap<Class<?>, ActionMessage<? extends Message>>();
	}
	
    public <T extends Message> void register(Class<T> t, ActionMessage<T> action) {
    	actions.put(t, action);
    }

    @SuppressWarnings("unchecked")
	public <T extends Message> void send(T message) {
    	Class<?> clazz = message.getClass();
    	ActionMessage<Message> actionMessage = (ActionMessage<Message>) actions.get(clazz);
    	actionMessage.invoke(message);
    }
	
}
