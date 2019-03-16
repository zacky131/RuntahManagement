package com.resikin.percakapan.core.chat_groups.listeners;

import com.resikin.percakapan.core.chat_groups.models.ChatGroup;
import com.resikin.percakapan.core.exception.ChatRuntimeException;

/**
 * Created by stefanodp91 on 24/01/18.
 */

public interface ChatGroupsListener {

    void onGroupAdded(ChatGroup chatGroup, ChatRuntimeException e);

    void onGroupChanged(ChatGroup chatGroup, ChatRuntimeException e);

    void onGroupRemoved(ChatRuntimeException e);
}
