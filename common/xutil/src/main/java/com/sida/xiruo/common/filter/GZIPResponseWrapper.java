package com.sida.xiruo.common.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: ProBIZ Date: 2009-1-13 Time: 15:30:02 To
 * change this template use File | Settings | File Templates.
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {
	protected int error = 0;
	private static final Logger LOG = Logger.getLogger(GZIPResponseWrapper.class);
	protected HttpServletResponse origResponse = null;
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;

	public GZIPResponseWrapper(HttpServletResponse response) {
		super(response);
		origResponse = response;
	}

	public ServletOutputStream createOutputStream() throws IOException {
		return (new GZIPResponseStream(origResponse));
	}

	public void finishResponse() {
		try {
			if (writer != null) {
				writer.close();
			} else {
				if (stream != null) {
					stream.close();
				}
			}
		} catch (IOException e) {
		}
	}

	public void flushBuffer() throws IOException {
		if (stream != null) {
			stream.flush();
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException(
					"getWriter() has already been called!");
		}

		if (stream == null) {
			stream = createOutputStream();
		}

		return (stream);
	}

	public PrintWriter getWriter() throws IOException {
		// From cmurphy@intechtual.com to fix:
		// https://appfuse.dev.java.net/issues/show_bug.cgi?id=59
		if (this.origResponse != null && this.origResponse.isCommitted()) {
			return super.getWriter();
		}

		if (writer != null) {
			return (writer);
		}

		if (stream != null) {
			throw new IllegalStateException(
					"getOutputStream() has already been called!");
		}

		stream = createOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(stream, origResponse
				.getCharacterEncoding()));

		return (writer);
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#sendError(int,
	 *      java.lang.String)
	 */
	public void sendError(int error, String message) throws IOException {
		super.sendError(error, message);
		this.error = error;

		if (LOG.isDebugEnabled()) {
			LOG.debug("sending error: " + error + " [" + message + "]");
		}
	}

	public void setContentLength(int length) {
	}
}
