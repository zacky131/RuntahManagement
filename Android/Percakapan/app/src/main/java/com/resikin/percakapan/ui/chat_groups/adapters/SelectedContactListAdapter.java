package com.resikin.percakapan.ui.chat_groups.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.resikin.percakapan.core.users.models.IChatUser;
import com.resikin.percakapan.ui.adapters.AbstractRecyclerAdapter;
import com.resikin.percakapan.ui.chat_groups.listeners.OnRemoveClickListener;
import com.resikin.percakapan.utils.image.CropCircleTransformation;

import java.util.List;

/**
 * Created by stefanodp91 on 07/12/17.
 */

public class SelectedContactListAdapter extends AbstractRecyclerAdapter<IChatUser,
        com.resikin.percakapan.ui.chat_groups.adapters.SelectedContactListAdapter.SelectedContactViewHolder> {

    private OnRemoveClickListener onRemoveClickListener;

    public SelectedContactListAdapter(Context context, List<IChatUser> mList) {
        super(context, mList);
    }

    public OnRemoveClickListener getOnRemoveClickListener() {
        return onRemoveClickListener;
    }

    // set a listener called when the "remove" button is pressed
    public void setOnRemoveClickListener(OnRemoveClickListener onRemoveClickListener) {
        this.onRemoveClickListener = onRemoveClickListener;
    }

    @Override
    public SelectedContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.resikin.percakapan.R.layout.row_selected_contact_list, parent, false);
        return new SelectedContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedContactViewHolder holder, final int position) {

        IChatUser contact = getItem(position);
        holder.bind(contact, position, getOnRemoveClickListener());
    }

    class SelectedContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView contact;
        private final ImageView profilePicture;
//        private final ImageView remove;

        SelectedContactViewHolder(View itemView) {
            super(itemView);
            contact = (TextView) itemView.findViewById(com.resikin.percakapan.R.id.username);
            profilePicture = (ImageView) itemView.findViewById(com.resikin.percakapan.R.id.profile_picture);
//            remove = (ImageView) itemView.findViewById(R.id.remove);
        }

        public void bind(IChatUser contact, int position, OnRemoveClickListener callback) {
            setDisplayName(contact.getFullName());
            loadProfileImage(contact);
            onRemoveClickListener(position, callback);
        }

        private void setDisplayName(String displayName) {
            contact.setText(displayName);
        }

        private void loadProfileImage(IChatUser contact) {

            String url = contact.getProfilePictureUrl();

            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(com.resikin.percakapan.R.drawable.ic_person_avatar)
                    .bitmapTransform(new CropCircleTransformation(itemView.getContext()))
                    .into(profilePicture);
        }

        private void onRemoveClickListener(final int position, final OnRemoveClickListener callback) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int returnedPosition = 0;
//                    if (position > 0) {
//                        returnedPosition = position;
//                    }

                    callback.onRemoveClickListener(position);
                }
            });
        }
    }
}