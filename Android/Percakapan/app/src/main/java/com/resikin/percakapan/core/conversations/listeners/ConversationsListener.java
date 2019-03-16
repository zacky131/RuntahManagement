package com.resikin.percakapan.core.conversations.listeners;

import com.resikin.percakapan.core.conversations.models.Conversation;
import com.resikin.percakapan.core.exception.ChatRuntimeException;

/**
 * Created by andrealeo on 06/12/17.
 */

public interface ConversationsListener {

    public void onConversationAdded(Conversation conversation, ChatRuntimeException e);

    public void onConversationChanged(Conversation conversation, ChatRuntimeException e);

    public void onConversationRemoved(ChatRuntimeException e);

}

