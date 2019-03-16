package com.resikin.percakapan.ui.contacts.listeners;

import com.resikin.percakapan.core.users.models.IChatUser;

import java.io.Serializable;

/**
 * Created by stefanodp91 on 29/03/17.
 */
public interface OnContactClickListener extends Serializable {
    void onContactClicked(IChatUser contact, int position);
}
