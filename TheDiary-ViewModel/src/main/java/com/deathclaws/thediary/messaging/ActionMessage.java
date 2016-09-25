package com.deathclaws.thediary.messaging;

public interface ActionMessage<T> {
	public void invoke(T arg);
}
