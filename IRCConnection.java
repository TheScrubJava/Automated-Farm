package com.jsitarski.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class IRCConnection {
	protected String host;
	protected int port;
	protected volatile IRCStreamWriter outWriter = null;
	protected InputStream inputStream = null;
	protected BufferedReader bufferedReader = null;
	protected Socket socket;

	// unsecured..
	public IRCConnection(String host) {
		this(host, 6667);
	}

	public IRCConnection(String host, int port) {
		this.host = host;
		this.port = port;
		connect();
	}

	private void connect() {
		try {
			socket = new Socket(host, port);
			socket.setKeepAlive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (socket != null) {
			OutputStream oStream = null;
			try {
				oStream = socket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (oStream != null) {
				outWriter = new IRCStreamWriter(oStream);
			}
			InputStream istream = null;
			try {
				istream = socket.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(istream));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (istream != null) {
				inputStream = istream;
			}
		}
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public IRCStreamWriter getOutputWriter() {
		return outWriter;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public String toString() {
		return "IRCConnection [host=" + host + ", port=" + port + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IRCConnection))
			return false;
		IRCConnection other = (IRCConnection) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	public void close() {
		if (isConnected())
			try {
				outWriter.writeLine("/quit");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
