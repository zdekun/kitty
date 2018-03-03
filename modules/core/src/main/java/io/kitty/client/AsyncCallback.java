package io.kitty.client;

public interface AsyncCallback<T> {

    void onSuccess(ResponseEntity<T> responseEntity);

    void onFailure(Throwable ex);

}
