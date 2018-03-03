package io.kitty.client;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * 基于Restful规范的client接口. <br />
 * <br />
 * 提供同步与异步两套接口,封装了HTTP方法中GET/POST/PUT/DELETE,
 * 同时可以通过通用的execute与asyncExecute调用其他的HTTP方法.
 */
public interface Restful {

    <T> T getForObject(String url, Class<T> responseType) throws RestfulException;

    <T> T getForObject(String url, Map<String, String> headers, Class<T> responseType) throws RestfulException;

    <T> T getForObject(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables)
	    throws RestfulException;

    <T> T getForObject(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables,
	    long timeout) throws RestfulException;

    <T> T getForObject(String url, Map<String, String> headers, TypeReference<T> responseType,
	    Map<String, ?> uriVariables, long timeout) throws RestfulException;

    <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestfulException;

    <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, Class<T> responseType)
	    throws RestfulException;

    <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, Class<T> responseType,
	    Map<String, ?> uriVariables) throws RestfulException;

    <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, Class<T> responseType,
	    Map<String, ?> uriVariables, long timeout) throws RestfulException;

    <T> ResponseEntity<T> getForEntity(String url, Map<String, String> headers, TypeReference<T> responseType,
	    Map<String, ?> uriVariables, long timeout) throws RestfulException;

    <T> Future<ResponseEntity<T>> asyncGetForEntity(String url, Map<String, String> headers, Class<T> responseType,
	    Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException;

    <T> Future<ResponseEntity<T>> asyncGetForEntity(String url, Map<String, String> headers,
	    TypeReference<T> responseType, Map<String, ?> uriVariables, AsyncCallback<T> callback)
		    throws RestfulException;

    <T> T postForObject(String url, Object request, Class<T> responseType) throws RestfulException;

    <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
	    throws RestfulException;

    <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables, long timeout)
	    throws RestfulException;

    <T> T postForObject(String url, Object request, TypeReference<T> responseType, Map<String, ?> uriVariables,
	    long timeout) throws RestfulException;

    <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType) throws RestfulException;

    <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)
	    throws RestfulException;

    <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables,
	    long timeout) throws RestfulException;

    <T> ResponseEntity<T> postForEntity(String url, Object request, TypeReference<T> responseType,
	    Map<String, ?> uriVariables, long timeout) throws RestfulException;

    <T> Future<ResponseEntity<T>> asyncPostForEntity(String url, Object request, Class<T> responseType,
	    Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException;

    <T> Future<ResponseEntity<T>> asyncPostForEntity(String url, Object request, TypeReference<T> responseType,
	    Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException;

    <T> ResponseEntity<T> putForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables,
	    long timeout) throws RestfulException;

    <T> ResponseEntity<T> putForEntity(String url, Object request, TypeReference<T> responseType,
	    Map<String, ?> uriVariables, long timeout) throws RestfulException;

    <T> Future<ResponseEntity<T>> asyncPutForEntity(String url, Object request, Class<T> responseType,
	    Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException;

    <T> Future<ResponseEntity<T>> asyncPutForEntity(String url, Object request, TypeReference<T> responseType,
	    Map<String, ?> uriVariables, AsyncCallback<T> callback) throws RestfulException;

    ResponseEntity<Void> deleteForEntity(String url, Map<String, String> headers, Map<String, ?> uriVariables,
	    long timeout) throws RestfulException;

    Future<ResponseEntity<Void>> asyncDeleteForEntity(String url, Map<String, String> headers,
	    Map<String, ?> uriVariables, AsyncCallback<Void> callback) throws RestfulException;

    <T, S> ResponseEntity<T> execute(RequestEntity<S> requestEntity, long timeout);

    <T, S> Future<ResponseEntity<T>> asyncExecute(RequestEntity<S> requestEntity, AsyncCallback<T> callback);
}
