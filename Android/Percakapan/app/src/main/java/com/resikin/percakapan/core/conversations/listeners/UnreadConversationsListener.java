package com.resikin.percakapan.core.conversations.listeners;

import com.resikin.percakapan.core.exception.ChatRuntimeException;

/**
 * Created by andrealeo on 06/12/17.
 */

public interface UnreadConversationsListener {

    void onUnreadConversationCounted(int count, ChatRuntimeException e);

}

