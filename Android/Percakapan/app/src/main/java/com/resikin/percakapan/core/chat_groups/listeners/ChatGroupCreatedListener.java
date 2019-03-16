package com.resikin.percakapan.core.chat_groups.listeners;

import com.resikin.percakapan.core.chat_groups.models.ChatGroup;
import com.resikin.percakapan.core.exception.ChatRuntimeException;

/**
 * Created by stefanodp91 on 29/01/18.
 */

public interface ChatGroupCreatedListener {
    void onChatGroupCreated(ChatGroup chatGroup, ChatRuntimeException chatException);
}
