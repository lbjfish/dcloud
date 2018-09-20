package com.sida.xiruo.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by IntelliJ IDEA.
 * User: ProBIZ
 * Date: 2009-1-13
 * Time: 15:29:26
 * To change this template use File | Settings | File Templates.
 */
public class GZIPFilter extends OncePerRequestFilter {
	private static final Logger LOG = Logger.getLogger(GZIPFilter.class);

	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if ( isGZIPSupported(request)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("GZIP supported, compressing response");
			}

			GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(
					response);

			chain.doFilter(request, wrappedResponse);
			wrappedResponse.finishResponse();

			return;
		}

		chain.doFilter(request, response);
	}

	protected void initFilterBean() throws ServletException {
	}

	/**
	 * Test for GZIP cababilities. Optimized to be used together with
	 * CacheFilter.
	 *
	 * @param request
	 *            The current user request
	 * @return boolean indicating GZIP support
	 */
	private boolean isGZIPSupported(HttpServletRequest request) {

		// If not cached, don't zip.
//		if (request.getAttribute("__oscache_filtered__cacheFilter") == null) {
//			return false;
//		}

		// test if browser can accept gzip encoding
		String browserEncodings = request.getHeader("accept-encoding");
		return ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
	}

}
