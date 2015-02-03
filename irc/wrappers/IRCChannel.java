package com.jsitarski.irc.wrappers;

import java.io.IOException;

import com.jsitarski.irc.IRCStreamWriter;

public class IRCChannel {

	private String channelName;
	private IRCStreamWriter outWriter;

	public IRCChannel(String channelName, IRCStreamWriter outWriter) {
		this.channelName = "#" + channelName;
		this.outWriter = outWriter;
		connect();
	}

	@Override
	public String toString() {
		return "IRCChannel [channelName=" + channelName + ", outWriter=" + outWriter + "]";
	}

	public String getChannelName() {
		return channelName;
	}

	public IRCStreamWriter getOutWriter() {
		return outWriter;
	}

	public void connect() {
		if (outWriter != null) {
			try {
				outWriter.writeLine("JOIN " + channelName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void leave() {
		if (outWriter != null) {
			try {
				outWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendPublicMessage(String message) throws IOException {
		if (outWriter != null) {
			sendPrivmsg(channelName, message);
		}
	}

	public void sendPrivmsg(String target, String message) throws IOException {
		if (outWriter != null) {
			outWriter.writeLine("PRIVMSG " + target + " :" + message);
		}
	}
}
