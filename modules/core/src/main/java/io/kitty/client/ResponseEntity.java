package io.kitty.client;

import io.kitty.util.ToStringUtil;

import java.util.Map;
import java.util.Objects;

public class ResponseEntity<T> extends HttpEntity<T> {

    private final HttpStatus status;

    public ResponseEntity(HttpStatus status) {
        this(null, null, status);
    }

    public ResponseEntity(T body, HttpStatus status) {
        this(body, null, status);
    }

    public ResponseEntity(Map<String, String> headers, HttpStatus status) {
        this(null, headers, status);
    }

    public ResponseEntity(T body, Map<String, String> headers, HttpStatus status) {
        super(body, headers);
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return this.status;
    }

    public int getStatusCodeValue() {
        return this.status.getValue();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ResponseEntity))
            return false;
        if (!super.equals(other))
            return false;
        ResponseEntity<?> that = (ResponseEntity<?>) other;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(this.status.getValue());
        builder.append(' ');
        builder.append(this.status.getReasonPhrase());
        builder.append(',');
        T body = getBody();
        Map<String, String> headers = getHeaders();
        if (body != null) {
            builder.append(ToStringUtil.toString(body));
            if (headers != null) {
                builder.append(',');
            }
        }
        if (headers != null) {
            builder.append(ToStringUtil.toString(headers));
        }
        builder.append('>');
        return builder.toString();
    }

    public static class HttpStatus {
        private final int value;

        private final String reasonPhrase;

        public HttpStatus(int value, String reasonPhrase) {
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        public int getValue() {
            return value;
        }

        public String getReasonPhrase() {
            return reasonPhrase;
        }
    }
}
