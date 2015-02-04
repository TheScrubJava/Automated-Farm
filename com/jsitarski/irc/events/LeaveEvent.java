package com.jsitarski.irc.events;

import java.util.EventListener;

import com.jsitarski.irc.wrappers.IRCUser;

public class LeaveEvent extends MessageEvent {

	private String channelName;

	public LeaveEvent(IRCUser message, String channelName) {
		super(message);
		this.channelName = channelName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void fireEvent(EventListener el) {
		((MessageListener) el).onUserLeave(this);
	}

}
