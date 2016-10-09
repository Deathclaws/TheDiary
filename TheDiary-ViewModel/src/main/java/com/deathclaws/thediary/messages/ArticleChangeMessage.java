package com.deathclaws.thediary.messages;

import com.deathclaws.thediary.messaging.Message;

public class ArticleChangeMessage implements Message {

	final private long identifier;

	public ArticleChangeMessage() {
		this.identifier = 0;
	}
	
	public ArticleChangeMessage(long identifier) {
		this.identifier = identifier;
	}

	public long getIdentifier() {
		return identifier;
	}
	
}
