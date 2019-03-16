package com.resikin.percakapan.ui.users.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.resikin.percakapan.core.ChatManager;
import com.resikin.percakapan.core.contacts.synchronizers.ContactsSynchronizer;
import com.resikin.percakapan.core.presence.PresenceHandler;
import com.resikin.percakapan.core.presence.listeners.PresenceListener;
import com.resikin.percakapan.core.users.models.IChatUser;
import com.resikin.percakapan.ui.ChatUI;
import com.resikin.percakapan.utils.StringUtils;
import com.resikin.percakapan.utils.TimeUtils;

import static com.resikin.percakapan.utils.DebugConstants.DEBUG_USER_PRESENCE;

/**
 * Created by stefanodp91 on 04/08/17.
 * <p>
 * bugfix Issue #30
 */
public class PublicProfileActivity extends AppCompatActivity implements PresenceListener {
    private static final String TAG = com.resikin.percakapan.ui.users.activities.PublicProfileActivity.class.getName();

    private IChatUser contact;
    private PresenceHandler presenceHandler;
    private boolean conversWithOnline = false;
    private long conversWithLastOnline = -1;

    private TextView mToolbarSubTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.resikin.percakapan.R.layout.acticvity_public_profile);

        contact = (IChatUser) getIntent().getSerializableExtra(ChatUI.BUNDLE_RECIPIENT);

        presenceHandler = ChatManager.getInstance().getPresenceHandler(contact.getId());

        // BEGIN contactsSynchronizer
        ContactsSynchronizer contactsSynchronizer = ChatManager.getInstance().getContactsSynchronizer();
        if (contactsSynchronizer != null) {
            IChatUser matchedContact = contactsSynchronizer.findById(contact.getId());

            if(matchedContact != null) {
                contact = matchedContact;
            }
        }
        // END contactsSynchronizer

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(com.resikin.percakapan.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // fullname as title
        TextView mToolbarTitle = findViewById(com.resikin.percakapan.R.id.toolbar_title);
        mToolbarTitle.setText(StringUtils.isValid(contact.getFullName()) ?
                contact.getFullName() : contact.getId());

        // connection status (online/offline) as subtitle
        mToolbarSubTitle = findViewById(com.resikin.percakapan.R.id.toolbar_subtitle);
        mToolbarSubTitle.setText("");

        // set user email
        TextView mEmail = findViewById(com.resikin.percakapan.R.id.email);
        mEmail.setText(contact.getEmail());

        // set user id
        TextView mUID = findViewById(com.resikin.percakapan.R.id.userid);
        mUID.setText(contact.getId());

        // init user profile picture
        initProfilePicture();

        presenceHandler.upsertPresenceListener(this);
        presenceHandler.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "  PublicProfileActivity.onDestroy");

        presenceHandler.removePresenceListener(this);
        Log.d(DEBUG_USER_PRESENCE, "PublicProfileActivity.onDestroy: presenceHandler detached");
    }

    private void initProfilePicture() {
        Log.d(TAG, "initProfilePicture");

        ImageView profilePictureToolbar = (ImageView) findViewById(com.resikin.percakapan.R.id.image);

        Glide.with(getApplicationContext())
                .load(contact.getProfilePictureUrl())
                .placeholder(com.resikin.percakapan.R.drawable.ic_person_avatar)
//                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(profilePictureToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isUserOnline(boolean isConnected) {
        Log.d(DEBUG_USER_PRESENCE, "PublicProfileActivity.isUserOnline: " +
                "isConnected == " + isConnected);

        if (isConnected) {
            conversWithOnline = true;
            mToolbarSubTitle.setText(getString(com.resikin.percakapan.R.string.activity_public_profile_presence_online));
        } else {
            conversWithOnline = false;

            if (conversWithLastOnline != PresenceHandler.LAST_ONLINE_UNDEFINED) {
                mToolbarSubTitle.setText(TimeUtils
                        .getFormattedTimestamp(this, conversWithLastOnline));
                Log.d(DEBUG_USER_PRESENCE, "PublicProfileActivity.isUserOnline: " +
                        "conversWithLastOnline == " + conversWithLastOnline);
            } else {
                mToolbarSubTitle.setText(getString(com.resikin.percakapan.R.string.activity_public_profile_presence_offline));
            }
        }
    }

    @Override
    public void userLastOnline(long lastOnline) {
        Log.d(DEBUG_USER_PRESENCE, "PublicProfileActivity.userLastOnline: " +
                "lastOnline == " + lastOnline);

        conversWithLastOnline = lastOnline;

        if (!conversWithOnline) {
            mToolbarSubTitle.setText(TimeUtils.getFormattedTimestamp(this, lastOnline));
        }

        if (!conversWithOnline && lastOnline == PresenceHandler.LAST_ONLINE_UNDEFINED) {
            mToolbarSubTitle.setText(getString(com.resikin.percakapan.R.string.activity_public_profile_presence_offline));
        }
    }

    @Override
    public void onPresenceError(Exception e) {
        Log.e(DEBUG_USER_PRESENCE, "PublicProfileActivity.onMyPresenceError: " + e.toString());

        mToolbarSubTitle.setText(getString(com.resikin.percakapan.R.string.activity_public_profile_presence_offline));
    }
}