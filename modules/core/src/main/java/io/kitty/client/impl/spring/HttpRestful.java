package io.kitty.client.impl.spring;

import io.kitty.client.AsyncCallback;
import io.kitty.client.HttpEntity;
import io.kitty.client.RequestEntity;
import io.kitty.client.ResponseEntity;
import io.kitty.client.Restful;
import io.kitty.client.RestfulException;
import io.kitty.client.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpRestful implements Restful {
    private static final Logger logger = LoggerFactory.getLogger(HttpRestful.class);
    private static final Map<String, String> EMPTY = new HashMap<String, String>(0);

    private long timeout = 500L;
    private AsyncRestTemplate asyncRestTemplate;

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setAsyncRestTemplate(AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    protected AsyncRestTemplate getAsyncRestTemplate() {
        return asyncRestTemplate;
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType) throws RestfulException {
        return getForObject(url, EMPTY, responseType, EMPTY, timeout);
    }

    @Override
    public <T> T getForObject(String url, Map<String, String> headers, Class<T> responseType) throws RestfulException {
        return getForObject(url, headers, responseType, EMPTY, timeout);
    }

    @Override
    public <T> T getForObject(String url, Map<String, String> headers, Class<T> responseType,
                              Map<String, ?> uriVariables) throws RestfulException {
        return getForObject(url, headers, responseType, uriVariables, timeout);
    }

    @Override
    public <T> T getForObject(String url, Map<String, String> headers, Class<T> responseType,
                              Map<String, ?> uriVariables, long timeout) throws RestfulException {
        ResponseEntity<T> responseEntity = getForEntity(url, headers, responseType, uriVariables, timeout);
        return responseEntity.getBody();
    }

    @Override
    public <T> T getForObject(String url, Map<String, String> headers, TypeReference<T> responseType,
                              Map<String, ?> uriVariables, long timeout) throws RestfulException {
        ResponseEntity<T> responseEntity = getForEntity(url, headers, responseType, uriVariables, timeout);
        return responseEntity.getBody();
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestfulException {
        return getForEntity(url, EMPTY, responseType, EMPTY, timeout);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, Class<T> responseType)
            throws RestfulException {
        return getForEntity(url, headers, responseType, EMPTY, timeout);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, Class<T> responseType,
                                              Map<String, ?> uriVariables) throws RestfulException {
        return getForEntity(url, headers, responseType, uriVariables, timeout);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, Class<T> responseType,
                                              Map<String, ?> uriVariables, long timeout) throws RestfulException {
        RequestEntity<Void> requestEntity = assembleRequestEntity(url, HttpMethod.GET, headers, null, responseType,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, TypeReference<T> responseType,
                                              Map<String, ?> uriVariables, long timeout) throws RestfulException {
        RequestEntity<Void> requestEntity = assembleRequestEntity(url, HttpMethod.GET, headers, null, responseType,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public <T> Future<ResponseEntity<T>> asyncGetForEntity(String url, Map<String, String> headers,
                                                           Class<T> responseType, Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException {
        RequestEntity<Void> requestEntity = assembleRequestEntity(url, HttpMethod.GET, headers, null, responseType,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public <T> Future<ResponseEntity<T>> asyncGetForEntity(String url, Map<String, String> headers,
                                                           TypeReference<T> responseType, Map<String, ?> uriVariables, AsyncCallback<T> callback)
            throws RestfulException {
        RequestEntity<Void> requestEntity = assembleRequestEntity(url, HttpMethod.GET, headers, null, responseType,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType) throws RestfulException {
        return postForObject(url, request, responseType, EMPTY, timeout);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
            throws RestfulException {
        return postForObject(url, request, responseType, uriVariables, timeout);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables,
                               long timeout) throws RestfulException {
        ResponseEntity<T> responseEntity = postForEntity(url, request, responseType, uriVariables, timeout);
        return responseEntity.getBody();
    }

    @Override
    public <T> T postForObject(String url, Object request, TypeReference<T> responseType, Map<String, ?> uriVariables,
                               long timeout) throws RestfulException {
        ResponseEntity<T> responseEntity = postForEntity(url, request, responseType, uriVariables, timeout);
        return responseEntity.getBody();
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType)
            throws RestfulException {
        return postForEntity(url, request, responseType, EMPTY, timeout);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
                                               Map<String, ?> uriVariables) throws RestfulException {
        return postForEntity(url, request, responseType, uriVariables, timeout);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
                                               Map<String, ?> uriVariables, long timeout) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.POST, request, responseType,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, TypeReference<T> responseType,
                                               Map<String, ?> uriVariables, long timeout) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.POST, request, responseType,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public <T> Future<ResponseEntity<T>> asyncPostForEntity(String url, Object request, Class<T> responseType,
                                                            Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.POST, request, responseType,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public <T> Future<ResponseEntity<T>> asyncPostForEntity(String url, Object request, TypeReference<T> responseType,
                                                            Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.POST, request, responseType,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public <T> ResponseEntity<T> putForEntity(String url, Object request, Class<T> responseType,
                                              Map<String, ?> uriVariables, long timeout) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.PUT, request, responseType,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public <T> ResponseEntity<T> putForEntity(String url, Object request, TypeReference<T> responseType,
                                              Map<String, ?> uriVariables, long timeout) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.PUT, request, responseType,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public <T> Future<ResponseEntity<T>> asyncPutForEntity(String url, Object request, Class<T> responseType,
                                                           Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.PUT, request, responseType,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public <T> Future<ResponseEntity<T>> asyncPutForEntity(String url, Object request, TypeReference<T> responseType,
                                                           Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException {
        RequestEntity<?> requestEntity = assembleRequestEntity(url, HttpMethod.PUT, request, responseType,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public ResponseEntity<Void> deleteForEntity(String url, Map<String, String> headers, Map<String, ?> uriVariables,
                                                long timeout) throws RestfulException {
        RequestEntity<Void> requestEntity = assembleRequestEntity(url, HttpMethod.DELETE, headers, null, Void.class,
                uriVariables);
        return execute(requestEntity, timeout);
    }

    @Override
    public Future<ResponseEntity<Void>> asyncDeleteForEntity(String url, Map<String, String> headers,
                                                             Map<String, ?> uriVariables, AsyncCallback<Void> callback) throws RestfulException {
        RequestEntity<Void> requestEntity = assembleRequestEntity(url, HttpMethod.DELETE, headers, null, Void.class,
                uriVariables);
        return asyncExecute(requestEntity, callback);
    }

    @Override
    public <T, S> ResponseEntity<T> execute(RequestEntity<S> requestEntity, long timeout) {
        long begin = System.currentTimeMillis();
        URI uri = requestEntity.getUri();
        String method = requestEntity.getMethod();
        ListenableFutureCallback<org.springframework.http.ResponseEntity<T>> recordResponseInfoCallback = new RecordResponseInfoCallback<T, S>(
                begin, requestEntity);
        try {
            org.springframework.http.HttpEntity<?> httpEntity = convertRequestEntity(requestEntity);
            ListenableFuture<org.springframework.http.ResponseEntity<T>> responseEntityFuture = getAsyncRestTemplate()
                    .exchange(uri, HttpMethod.resolve(method), httpEntity,
                            ParameterizedTypeReference.forType(requestEntity.getType()));
            responseEntityFuture.addCallback(recordResponseInfoCallback);
            org.springframework.http.ResponseEntity<T> responseEntity = responseEntityFuture.get(timeout,
                    TimeUnit.MILLISECONDS);
            return convertResponseEntity(responseEntity);
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            RestfulException restfulException = new RestfulException(e.getMessage(), e);
            Throwable cause = e.getCause();
            if (cause != null) {
                restfulException.initCause(cause);
            }
            throw restfulException;
        }
    }

    @Override
    public <T, S> Future<ResponseEntity<T>> asyncExecute(RequestEntity<S> requestEntity, AsyncCallback<T> callback) {
        long begin = System.currentTimeMillis();
        URI uri = requestEntity.getUri();
        String method = requestEntity.getMethod();
        ListenableFutureCallback<org.springframework.http.ResponseEntity<T>> futureCallbackAdapter = new ListenableFutureCallbackAdapter<T, S>(
                begin, requestEntity, callback);

        try {
            org.springframework.http.HttpEntity<?> httpEntity = convertRequestEntity(requestEntity);
            ListenableFuture<org.springframework.http.ResponseEntity<T>> responseEntityFuture = getAsyncRestTemplate()
                    .exchange(uri, HttpMethod.resolve(method), httpEntity,
                            ParameterizedTypeReference.forType(requestEntity.getType()));
            responseEntityFuture.addCallback(futureCallbackAdapter);
            return new FutureAdapter<T>(responseEntityFuture);
        } catch (RestClientException e) {
            throw new RestfulException(e.getMessage(), e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T, S> RequestEntity<S> assembleRequestEntity(String url, HttpMethod method, Object request,
                                                          Class<T> responseType, Map<String, ?> uriVariables) {
        Map<String, String> headers = EMPTY;
        S body = null;
        if (request instanceof HttpEntity) {
            HttpEntity<S> httpEntity = (HttpEntity) request;
            headers = httpEntity.getHeaders();
            body = httpEntity.getBody();
        } else if (request != null) {
            body = (S) request;
        }
        return assembleRequestEntity(url, method, headers, body, responseType, uriVariables);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T, S> RequestEntity<S> assembleRequestEntity(String url, HttpMethod method, Object request,
                                                          TypeReference<T> responseType, Map<String, ?> uriVariables) {
        Map<String, String> headers = EMPTY;
        S body = null;
        if (request instanceof HttpEntity) {
            HttpEntity<S> httpEntity = (HttpEntity) request;
            headers = httpEntity.getHeaders();
            body = httpEntity.getBody();
        } else if (request != null) {
            body = (S) request;
        }
        return assembleRequestEntity(url, method, headers, body, responseType, uriVariables);
    }

    private <T, S> RequestEntity<S> assembleRequestEntity(String url, HttpMethod method, Map<String, String> headers,
                                                          S body, Class<T> responseType, Map<String, ?> uriVariables) {
        if (uriVariables == null) {
            uriVariables = EMPTY;
        }
        URI uri = getAsyncRestTemplate().getUriTemplateHandler().expand(url, uriVariables);
        return new RequestEntity<S>(body, headers, method.name(), uri, responseType);
    }

    private <T, S> RequestEntity<S> assembleRequestEntity(String url, HttpMethod method, Map<String, String> headers,
                                                          S body, TypeReference<T> responseType, Map<String, ?> uriVariables) {
        if (uriVariables == null) {
            uriVariables = EMPTY;
        }
        URI uri = getAsyncRestTemplate().getUriTemplateHandler().expand(url, uriVariables);
        return new RequestEntity<S>(body, headers, method.name(), uri,
                responseType.getRawType());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <S> org.springframework.http.HttpEntity<?> convertRequestEntity(RequestEntity<S> request) {
        Map<String, String> headers = request.getHeaders();
        if (headers != null) {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
            multiValueMap.setAll(headers);
            return (org.springframework.http.HttpEntity<?>) new org.springframework.http.HttpEntity(
                    request.getBody(), multiValueMap);
        } else {
            return new org.springframework.http.HttpEntity(request.getBody());
        }
    }

    private static <T> ResponseEntity<T> convertResponseEntity(
            org.springframework.http.ResponseEntity<T> springResponseEntity) {
        T body = springResponseEntity.getBody();
        Map<String, String> headers = springResponseEntity.getHeaders().toSingleValueMap();
        ResponseEntity.HttpStatus status = new ResponseEntity.HttpStatus(springResponseEntity.getStatusCode().value(),
                springResponseEntity.getStatusCode().getReasonPhrase());

        return new ResponseEntity<T>(body, headers, status);
    }

    private static class RecordResponseInfoCallback<T, S>
            implements ListenableFutureCallback<org.springframework.http.ResponseEntity<T>> {
        private final long begin;
        private final RequestEntity<S> requestEntity;

        public RecordResponseInfoCallback(long begin, RequestEntity<S> requestEntity) {
            this.begin = begin;
            this.requestEntity = requestEntity;
        }

        @Override
        public void onSuccess(org.springframework.http.ResponseEntity<T> responseEntity) {
            long end = System.currentTimeMillis();
            String path = requestEntity.getUri().getPath();
            int status = responseEntity.getStatusCodeValue();
            if (logger.isDebugEnabled()) {
                logger.debug("{} request:{},response:{}", path, requestEntity, convertResponseEntity(responseEntity));
            }
            logger.info("{} {} response status[{}] cost {}ms.", path, requestEntity.getMethod(), status, (end - begin));
        }

        @Override
        public void onFailure(Throwable ex) {
            long end = System.currentTimeMillis();
            String path = requestEntity.getUri().getPath();
            if (logger.isDebugEnabled()) {
                logger.debug("{} request:{}", path, requestEntity);
            }
            logger.error("{} {} failure cost {}ms.", path, requestEntity.getMethod(), (end - begin), ex);
        }

    }

    private static class ListenableFutureCallbackAdapter<T, S> extends RecordResponseInfoCallback<T, S>
            implements ListenableFutureCallback<org.springframework.http.ResponseEntity<T>> {
        private final AsyncCallback<T> asyncCallback;

        public ListenableFutureCallbackAdapter(long begin, RequestEntity<S> requestEntity,
                                               AsyncCallback<T> asyncCallback) {
            super(begin, requestEntity);
            this.asyncCallback = asyncCallback;
        }

        @Override
        public void onSuccess(org.springframework.http.ResponseEntity<T> responseEntity) {
            try {
                if (asyncCallback != null) {
                    asyncCallback.onSuccess(convertResponseEntity(responseEntity));
                }
            } finally {
                super.onSuccess(responseEntity);
            }
        }

        @Override
        public void onFailure(Throwable ex) {
            try {
                if (asyncCallback != null) {
                    asyncCallback.onFailure(ex);
                }
            } catch (Exception e) {
                // ignore
            } finally {
                super.onFailure(ex);
            }
        }
    }

    private static class FutureAdapter<T> implements Future<ResponseEntity<T>> {
        private final ListenableFuture<org.springframework.http.ResponseEntity<T>> listenableFuture;

        public FutureAdapter(ListenableFuture<org.springframework.http.ResponseEntity<T>> listenableFuture) {
            this.listenableFuture = listenableFuture;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return listenableFuture.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return listenableFuture.isCancelled();
        }

        @Override
        public boolean isDone() {
            return listenableFuture.isDone();
        }

        @Override
        public ResponseEntity<T> get() throws InterruptedException, ExecutionException {
            org.springframework.http.ResponseEntity<T> springResponseEntity = listenableFuture.get();
            return convertResponseEntity(springResponseEntity);
        }

        @Override
        public ResponseEntity<T> get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException {
            org.springframework.http.ResponseEntity<T> springResponseEntity = listenableFuture.get(timeout, unit);
            return convertResponseEntity(springResponseEntity);
        }
    }

}
