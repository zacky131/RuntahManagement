package com.resikin.percakapan.ui.archived_conversations.listeners;

import com.resikin.percakapan.core.conversations.models.Conversation;

public interface OnSwipeMenuReopenClickListener {
    void onSwipeMenuReopened(Conversation conversation, int position);
}
