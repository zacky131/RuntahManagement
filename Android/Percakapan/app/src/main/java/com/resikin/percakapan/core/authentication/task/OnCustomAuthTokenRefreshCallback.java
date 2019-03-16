package com.resikin.percakapan.core.authentication.task;

/**
 * Created by stefanodp91 on 29/05/17.
 */

public interface OnCustomAuthTokenRefreshCallback<T> {
    void onRefreshSuccess(T data);

    void onRefreshError(Exception e);
}
