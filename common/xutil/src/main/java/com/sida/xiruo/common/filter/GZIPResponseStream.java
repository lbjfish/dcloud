package com.sida.xiruo.common.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by IntelliJ IDEA. User: ProBIZ Date: 2009-1-13 Time: 15:30:29 To
 * change this template use File | Settings | File Templates.
 */
public class GZIPResponseStream extends ServletOutputStream {
	// abstraction of the output stream used for compression
	protected OutputStream bufferedOutput = null;

	// state keeping variable for if close() has been called
	protected boolean closed = false;

	// reference to the output stream to the client's browser
	protected ServletOutputStream output = null;

	// reference to original response
	protected HttpServletResponse response = null;

	public GZIPResponseStream(HttpServletResponse response) throws IOException {
		super();
		closed = false;
		this.response = response;
		this.output = response.getOutputStream();
		bufferedOutput = new ByteArrayOutputStream();
	}

	public void close() throws IOException {
		// verify the stream is yet to be closed
		if (closed) {
			throw new IOException("This output stream has already been closed");
		}

		// if we buffered everything in memory, gzip it
		if (bufferedOutput instanceof ByteArrayOutputStream) {
			// get the content
			ByteArrayOutputStream baos = (ByteArrayOutputStream) bufferedOutput;

			// prepare a gzip stream
			ByteArrayOutputStream compressedContent = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(
					compressedContent);
			byte[] bytes = baos.toByteArray();
			gzipstream.write(bytes);
			gzipstream.finish();

			// get the compressed content
			byte[] compressedBytes = compressedContent.toByteArray();

			// set appropriate HTTP headers
			response.setContentLength(compressedBytes.length);
			response.addHeader("Content-Encoding", "gzip");
			output.write(compressedBytes);
			output.flush();
			output.close();
			closed = true;
		}
		// if things were not buffered in memory, finish the GZIP stream and
		// response
		else if (bufferedOutput instanceof GZIPOutputStream) {
			// cast to appropriate type
			GZIPOutputStream gzipstream = (GZIPOutputStream) bufferedOutput;

			// finish the compression
			gzipstream.finish();

			// finish the response
			output.flush();
			output.close();
			closed = true;
		}
	}

	public boolean closed() {
		return (this.closed);
	}

	public void flush() throws IOException {
		if (closed) {
			throw new IOException("Cannot flush a closed output stream");
		}

		bufferedOutput.flush();
	}

	public void reset() {
		// noop
	}

	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	public void write(byte[] b, int off, int len) throws IOException {

		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}

		// write the content to the buffer
		bufferedOutput.write(b, off, len);
	}

	public void write(int b) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}

		// write the byte to the temporary output
		bufferedOutput.write((byte) b);
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {

	}
}
