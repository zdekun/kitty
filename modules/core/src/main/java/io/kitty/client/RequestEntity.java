package io.kitty.client;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

public class RequestEntity<T> extends HttpEntity<T> {
    private final String method;

    private final URI uri;

    private final Type type;

    public RequestEntity(String method, URI uri) {
	this(null, null, method, uri);
    }

    public RequestEntity(T body, String method, URI uri) {
	this(body, null, method, uri, null);
    }

    public RequestEntity(T body, String method, URI uri, Type type) {
	this(body, null, method, uri, type);
    }

    public RequestEntity(Map<String, String> headers, String method, URI uri) {
	this(null, headers, method, uri, null);
    }

    public RequestEntity(T body, Map<String, String> headers, String method, URI uri) {
	this(body, headers, method, uri, null);
    }

    public RequestEntity(T body, Map<String, String> headers, String method, URI uri, Type type) {
	super(body, headers);
	this.method = method;
	this.uri = uri;
	this.type = type;
    }

    public String getMethod() {
	return this.method;
    }

    public URI getUri() {
	return this.uri;
    }

    public Type getType() {
	if (this.type == null) {
	    T body = getBody();
	    if (body != null) {
		return body.getClass();
	    }
	}
	return this.type;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	if (!super.equals(o))
	    return false;
	RequestEntity<?> that = (RequestEntity<?>) o;
	return Objects.equals(method, that.method) && Objects.equals(uri, that.uri) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

	return Objects.hash(super.hashCode(), method, uri, type);
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder("<");
	builder.append(getMethod());
	builder.append(' ');
	builder.append(getUri());
	builder.append(',');
	T body = getBody();
	Map<String, String> headers = getHeaders();
	if (body != null) {
	    builder.append(body);
	    if (headers != null) {
		builder.append(',');
	    }
	}
	if (headers != null) {
	    builder.append(headers);
	}
	builder.append('>');
	return builder.toString();
    }
}
