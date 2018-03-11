package io.kitty.client;

import io.kitty.util.ToStringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpEntity<T> {

    public static final HttpEntity<?> EMPTY = new HttpEntity<Object>();
    public static final Map<String, String> EMPTY_HEADERS = new HashMap<String, String>(0);

    private final Map<String, String> headers;

    private final T body;

    protected HttpEntity() {
        this(null, null);
    }

    public HttpEntity(T body) {
        this(body, null);
    }

    public HttpEntity(Map<String, String> headers) {
        this(null, headers);
    }

    public HttpEntity(T body, Map<String, String> headers) {
        if (headers == null) {
            headers = EMPTY_HEADERS;
        }
        this.body = body;
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public T getBody() {
        return this.body;
    }

    public boolean hasBody() {
        return (this.body != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HttpEntity<?> that = (HttpEntity<?>) o;
        return Objects.equals(headers, that.headers) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, body);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        if (this.body != null) {
            builder.append(ToStringUtil.toString(this.body));
            if (this.headers != null) {
                builder.append(',');
            }
        }
        if (this.headers != null) {
            builder.append(ToStringUtil.toString(this.headers));
        }
        builder.append('>');
        return builder.toString();
    }
}
