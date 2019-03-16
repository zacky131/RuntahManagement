package com.resikin.percakapan.core.messages.listeners;

import com.resikin.percakapan.core.exception.ChatRuntimeException;
import com.resikin.percakapan.core.messages.models.Message;

/**
 * Created by andrealeo on 06/12/17.
 */

public interface ConversationMessagesListener {

        public void onConversationMessageReceived(Message message, ChatRuntimeException e);
        public void onConversationMessageChanged(Message message, ChatRuntimeException e);

}

