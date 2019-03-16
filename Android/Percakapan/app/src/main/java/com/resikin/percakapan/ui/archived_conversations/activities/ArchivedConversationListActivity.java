package com.resikin.percakapan.ui.archived_conversations.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.resikin.percakapan.ui.archived_conversations.fragments.ArchivedConversationListFragment;

/**
 * Created by stefano on 02/08/2018.
 */
public class ArchivedConversationListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.resikin.percakapan.R.layout.activity_archived_conversation_list);

        // #### BEGIN TOOLBAR ####
        Toolbar toolbar = findViewById(com.resikin.percakapan.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // #### END  TOOLBAR ####

        // #### BEGIN CONTAINER ####
        getSupportFragmentManager()
                .beginTransaction()
                .replace(com.resikin.percakapan.R.id.container, new ArchivedConversationListFragment())
                .commit();
        // #### BEGIN CONTAINER ####
    }
}