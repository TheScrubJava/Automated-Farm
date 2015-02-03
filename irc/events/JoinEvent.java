package com.jsitarski.irc.events;

import java.util.EventListener;

import com.jsitarski.irc.wrappers.IRCUser;

public class JoinEvent extends MessageEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -196052511340185208L;

	public JoinEvent(IRCUser message) {
		super(message);
	}

	@Override
	public void fireEvent(EventListener ml) {
		((MessageListener) ml).onUserJoin(this);

	}
}
