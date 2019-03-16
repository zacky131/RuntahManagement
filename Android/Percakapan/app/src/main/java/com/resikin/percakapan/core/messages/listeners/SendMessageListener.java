package com.resikin.percakapan.core.messages.listeners;

import com.resikin.percakapan.core.exception.ChatRuntimeException;
import com.resikin.percakapan.core.messages.models.Message;

/**
 * Created by andrealeo on 24/11/17.
 */

public interface SendMessageListener {

    void onBeforeMessageSent(Message message, ChatRuntimeException chatException);

    void onMessageSentComplete(Message message, ChatRuntimeException chatException);
}