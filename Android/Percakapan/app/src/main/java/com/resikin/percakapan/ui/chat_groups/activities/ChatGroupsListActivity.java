package com.resikin.percakapan.ui.chat_groups.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.resikin.percakapan.core.chat_groups.models.ChatGroup;
import com.resikin.percakapan.core.messages.models.Message;
import com.resikin.percakapan.core.users.models.ChatUser;
import com.resikin.percakapan.core.users.models.IChatUser;
import com.resikin.percakapan.ui.ChatUI;
import com.resikin.percakapan.ui.chat_groups.fragments.ChatGroupsListFragment;
import com.resikin.percakapan.ui.chat_groups.listeners.OnGroupClickListener;
import com.resikin.percakapan.ui.messages.activities.MessageListActivity;

import static com.resikin.percakapan.ui.ChatUI.BUNDLE_CHANNEL_TYPE;

/**
 * Created by stefano on 25/08/2015.
 */
public class ChatGroupsListActivity extends AppCompatActivity implements OnGroupClickListener {
    private static final String TAG = com.resikin.percakapan.ui.chat_groups.activities.ChatGroupsListActivity.class.getSimpleName();

    private ChatGroupsListFragment contactsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.resikin.percakapan.R.layout.activity_chat_groups_list);

        contactsListFragment = new ChatGroupsListFragment();
        contactsListFragment.setOnChatGroupClickListener(this);

        // #### BEGIN TOOLBAR ####
        Toolbar toolbar = findViewById(com.resikin.percakapan.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // #### END  TOOLBAR ####

        // #### BEGIN CONTAINER ####
        getSupportFragmentManager()
                .beginTransaction()
                .replace(com.resikin.percakapan.R.id.container, contactsListFragment)
                .commit();
        // #### BEGIN CONTAINER ####
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "ContactListActivity.onOptionsItemSelected");

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupClicked(ChatGroup chatGroup, int position) {
        IChatUser groupRecipient = new ChatUser(chatGroup.getGroupId(), chatGroup.getName());

        // start the message list activity
        Intent intent = new Intent(com.resikin.percakapan.ui.chat_groups.activities.ChatGroupsListActivity.this, MessageListActivity.class);
        intent.putExtra(ChatUI.BUNDLE_RECIPIENT, groupRecipient);
        intent.putExtra(BUNDLE_CHANNEL_TYPE, Message.GROUP_CHANNEL_TYPE);
        startActivity(intent);
        finish();
    }
}