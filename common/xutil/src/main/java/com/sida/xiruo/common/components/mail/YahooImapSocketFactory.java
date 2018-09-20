package com.sida.xiruo.common.components.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.net.SocketFactory;




public class YahooImapSocketFactory extends SocketFactory {
	@Override
	public Socket createSocket() {
		return new YahooImapSocket();
	}
	@Override
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		return new YahooImapSocket(host, port);
	}


	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return new YahooImapSocket(host, port);
	}


	@Override
	public Socket createSocket(String host, int port, InetAddress localHost,
			int localPort) throws IOException, UnknownHostException {
		return new YahooImapSocket(host, port, localHost, localPort);
	}


	@Override
	public Socket createSocket(InetAddress address, int port,
			InetAddress localAddress, int localPort) throws IOException {
		return new YahooImapSocket(address, port, localAddress, localPort);
	}
	
	public static SocketFactory getDefault() {
		return new YahooImapSocketFactory();
	}


	private class YahooImapSocket extends Socket {
		boolean hacked = false;
		public YahooImapSocket() {
			super();
		}
		public YahooImapSocket(String host, int port) throws IOException,
				UnknownHostException {
			super(host, port);
		}
		public YahooImapSocket(InetAddress host, int port) throws IOException {
			super(host, port);
		}
		public YahooImapSocket(String host, int port, InetAddress localHost,
				int localPort) throws IOException, UnknownHostException {
			super(host, port, localHost, localPort);
		}
		public YahooImapSocket(InetAddress address, int port,
				InetAddress localAddress, int localPort) throws IOException {
			super(address, port, localAddress, localPort);
		}
		public void connect(SocketAddress endpoint) throws IOException {
			super.connect(endpoint);
		}
		public void connect(SocketAddress endpoint, int timeout) throws IOException {
			super.connect(endpoint, timeout);
		}
		public OutputStream getOutputStream() throws IOException {
			if (!hacked) {
				super.getOutputStream().write("ZZ id (\"GUID\" \"1\")\r\n".getBytes());
				hacked = true;
			}
			return super.getOutputStream();
		}


	}
}