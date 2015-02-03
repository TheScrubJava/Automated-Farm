package com.jsitarski.irc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class IRCStreamWriter extends OutputStreamWriter {

	public IRCStreamWriter(OutputStream outputStream) {
		super(outputStream);
	}

	/**
	 * Sorry infectous....
	 * 
	 * @param message
	 *            - the string u want to send u still need to send commands
	 * @throws IOException
	 */
	public void writeLine(String message) throws IOException {
		if (message != null) {
			if (!message.endsWith("\r\n")) {
				message = message + "\r\n";
			}
			write(message);
			flush();
		}
	}

}
