package io.kitty.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

public class HelloServlet extends HttpServlet {

    private static final long serialVersionUID = -1825597522411077775L;
    private static final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String method = req.getMethod();
	logger.error("method:{}", method);
	StringBuilder buff = new StringBuilder();
	Enumeration<String> headerNames = req.getHeaderNames();
	while (headerNames.hasMoreElements()) {
	    String headerName = headerNames.nextElement();
	    buff.append(headerName);
	    buff.append("=[");
	    Enumeration<String> headerValues = req.getHeaders(headerName);
	    while (headerValues.hasMoreElements()) {
		String headerValue = headerValues.nextElement();
		buff.append(headerValue).append(",");
	    }
	    buff.append("]\r\n");
	}
	logger.error("header:{}", buff.toString());
	InputStream input = req.getInputStream();
	String body = StreamUtils.copyToString(input, Charset.forName("UTF-8"));
	logger.error("body:{}", body);
	super.service(req, resp);
    }

}
