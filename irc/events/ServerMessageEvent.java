package com.jsitarski.irc.events;

import java.util.EventListener;

public class ServerMessageEvent extends MessageEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3682622199443933428L;

	public ServerMessageEvent(Object message) {
		super(message);
	}

	@Override
	public void fireEvent(EventListener ml) {
		((MessageListener) ml).onServerMessage(this);
	}

}
