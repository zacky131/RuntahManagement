package com.resikin.percakapan.ui.archived_conversations.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.resikin.percakapan.core.ChatManager;
import com.resikin.percakapan.core.conversations.ArchivedConversationsHandler;
import com.resikin.percakapan.core.conversations.listeners.ConversationsListener;
import com.resikin.percakapan.core.conversations.models.Conversation;
import com.resikin.percakapan.core.exception.ChatRuntimeException;
import com.resikin.percakapan.core.users.models.IChatUser;

/**
 * Created by stefano on 02/08/2018.
 */
public class BSArchivedConversationsListFragmentLongPress extends BottomSheetDialogFragment implements View.OnClickListener /**, ConversationsListener */
{

    private static final String DEBUG_TAG = BSArchivedConversationsListFragmentLongPress.class.getName();

    private static final String _BOTTOM_SHEET_CONVERSATIONS_LIST_FRAGMENT_LONG_PRESS_EXTRAS_CONVERSATION =
            "_BOTTOM_SHEET_CONVERSATIONS_LIST_FRAGMENT_LONG_PRESS_EXTRAS_CONVERSATION";

    private Conversation mConversation;
    private IChatUser mLoggedUser;

    private Button mDeleteConversationView;

    private ArchivedConversationsHandler conversationsHandler;

    public static BSArchivedConversationsListFragmentLongPress
    newInstance(Conversation conversation) {
        BSArchivedConversationsListFragmentLongPress f =
                new BSArchivedConversationsListFragmentLongPress();
        Bundle args = new Bundle();
        args.putSerializable(_BOTTOM_SHEET_CONVERSATIONS_LIST_FRAGMENT_LONG_PRESS_EXTRAS_CONVERSATION, conversation);
        f.setArguments(args);
        return f;
    }

    public ArchivedConversationsHandler getConversationsHandler() {
        return this.conversationsHandler;
    }

    public void setConversationsHandler(ArchivedConversationsHandler conversationsHandler) {
        this.conversationsHandler = conversationsHandler;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        conversationsHandler = getConversationsHandler();

        mConversation = (Conversation) getArguments()
                .getSerializable(_BOTTOM_SHEET_CONVERSATIONS_LIST_FRAGMENT_LONG_PRESS_EXTRAS_CONVERSATION);
        Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress.onCreate: " +
                "mConversation == " + mConversation.toString());

        mLoggedUser = ChatManager.getInstance().getLoggedUser();
        Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress.onCreate:" +
                " mLoggedUser == " + mLoggedUser.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.resikin.percakapan.R.layout.fragment_bottom_sheet_archived_conversation_list_long_press, container, false);

        mDeleteConversationView = view.findViewById(com.resikin.percakapan.R.id.btn_delete_conversation);
        mDeleteConversationView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == com.resikin.percakapan.R.id.btn_delete_conversation) {
            mLoggedUser = ChatManager.getInstance().getLoggedUser();
            Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress.onClick: btn_delete_conversation ");

            showRemoveMemberAlertDialog();
        }
    }

    private void showRemoveMemberAlertDialog() {
        Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress.showRemoveMemberAlertDialog");

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(com.resikin.percakapan.R.string.bottom_sheet_conversation_list_confirm_delete_conversation_alert_title))
                .setMessage(getString(com.resikin.percakapan.R.string.bottom_sheet_conversation_list_confirm_delete_conversation_alert_message))
                .setPositiveButton(getString(com.resikin.percakapan.R.string.bottom_sheet_conversation_list_confirm_delete_conversation_alert_positive_button_label),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress" +
                                        ".showRemoveMemberAlertDialog.setPositiveButton");

                                perfomDeleteConversation();
                            }
                        })
                .setNegativeButton(getString(com.resikin.percakapan.R.string.bottom_sheet_conversation_list_confirm_delete_conversation_alert_positive_button_negative),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress" +
                                        ".showRemoveMemberAlertDialog.setNegativeButton");

                                // dismiss the dialog
                                dialogInterface.dismiss();

                                // dismiss the bottomsheet
                                getDialog().dismiss();
                            }
                        }).show();
    }

    private void perfomDeleteConversation() {
        Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress.perfomDeleteConversation");

        String conversationId = mConversation.getConversationId();

        if(getConversationsHandler() != null) {
            getConversationsHandler().deleteConversation(conversationId, conversationsListener);
        }
    }

    private ConversationsListener conversationsListener = new ConversationsListener() {

        @Override
        public void onConversationAdded(Conversation conversation, ChatRuntimeException e) {
            // do nothing
        }

        @Override
        public void onConversationChanged(Conversation conversation, ChatRuntimeException e) {
            // do nothing
        }

        @Override
        public void onConversationRemoved(ChatRuntimeException e) {
            if(e == null) {
                Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress" +
                        ".conversationsListener.onConversationRemoved: no errors");

                // dismiss the bottomsheet
                getDialog().dismiss();
            } else {
                // there are error
                Log.d(DEBUG_TAG, "BSArchivedConversationsListFragmentLongPress" +
                        ".conversationsListener.onConversationRemoved: " + e.toString());

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        }
    };
}