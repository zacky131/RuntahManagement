package com.resikin.percakapan.core.contacts.listeners;

import com.resikin.percakapan.core.exception.ChatRuntimeException;

/**
 * Created by stefanodp91 on 28/02/18.
 */

public interface OnContactCreatedCallback {
    void onContactCreatedSuccess(ChatRuntimeException exception);
}
