package com.resikin.percakapan.ui.chat_groups.listeners;

import com.resikin.percakapan.core.users.models.IChatUser;

/**
 * Created by stefanodp91 on 07/12/17.
 */

public interface OnGroupMemberClickListener {
    void onGroupMemberClicked(IChatUser groupMember, int position);
}
