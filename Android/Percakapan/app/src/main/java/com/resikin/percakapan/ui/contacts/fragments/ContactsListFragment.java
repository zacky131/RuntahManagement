package com.resikin.percakapan.ui.contacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.resikin.percakapan.core.ChatManager;
import com.resikin.percakapan.core.contacts.synchronizers.ContactsSynchronizer;
import com.resikin.percakapan.core.users.models.IChatUser;
import com.resikin.percakapan.ui.contacts.adapters.ContactListAdapter;
import com.resikin.percakapan.ui.contacts.listeners.OnContactClickListener;
import com.resikin.percakapan.ui.decorations.ItemDecoration;

import java.util.List;


/**
 * Created by stefanodp91 on 02/03/18.
 */

public class ContactsListFragment extends Fragment {
    private static final String TAG = com.resikin.percakapan.ui.contacts.fragments.ContactsListFragment.class.getName();

    private ContactsSynchronizer contactsSynchronizer;
    private OnContactClickListener onContactClickListener;

    private SearchView searchView;

    // contacts list recyclerview
    private RecyclerView recyclerViewContacts;
    private LinearLayoutManager lmRvContacts;
    private ContactListAdapter contactsListAdapter;

    // no contacts layout
    private RelativeLayout noContactsLayout;

    public static Fragment newInstance() {
        Fragment mFragment = new com.resikin.percakapan.ui.contacts.fragments.ContactsListFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        contactsSynchronizer = ChatManager.getInstance().getContactsSynchronizer();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "ContactsListFragment.onCreateView");
        View view = inflater.inflate(com.resikin.percakapan.R.layout.fragment_contacts_list, container, false);

        // init RecyclerView
        recyclerViewContacts = view.findViewById(com.resikin.percakapan.R.id.contacts_list);
        recyclerViewContacts.addItemDecoration(new ItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL,
                getResources().getDrawable(com.resikin.percakapan.R.drawable.decorator_activity_contact_list)));
        lmRvContacts = new LinearLayoutManager(getActivity());
        recyclerViewContacts.setLayoutManager(lmRvContacts);
        updateContactListAdapter(contactsSynchronizer.getContacts());

        // no contacts layout
        noContactsLayout = view.findViewById(com.resikin.percakapan.R.id.layout_no_contacts);
        toggleNoContactsLayoutVisibility(contactsListAdapter.getItemCount());

        return view;
    }

    public void updateContactListAdapter(List<IChatUser> list) {
        if (contactsListAdapter == null) {
            // init RecyclerView adapter
            contactsListAdapter = new ContactListAdapter(list);
            if (getOnContactClickListener() != null)
                contactsListAdapter.setOnContactClickListener(getOnContactClickListener());
            recyclerViewContacts.setAdapter(contactsListAdapter);
        } else {
            contactsListAdapter.setList(list);
            contactsListAdapter.notifyDataSetChanged();
        }
    }

    // toggle the no contacts layout visibilty.
    // if there are items show the list of item, otherwise show a placeholder layout
    private void toggleNoContactsLayoutVisibility(int itemCount) {
        if (itemCount > 0) {
            // show the item list
            recyclerViewContacts.setVisibility(View.VISIBLE);
            noContactsLayout.setVisibility(View.GONE);
        } else {
            // show the placeholder layout
            recyclerViewContacts.setVisibility(View.GONE);
            noContactsLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
        this.onContactClickListener = onContactClickListener;
    }

    public OnContactClickListener getOnContactClickListener() {
        return onContactClickListener;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
        inflater.inflate(com.resikin.percakapan.R.menu.menu_activity_contacts_list, menu);

        MenuItem item = menu.findItem(com.resikin.percakapan.R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setShowAsAction(item,
                MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW |
                        MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if (contactsListAdapter != null) contactsListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (contactsListAdapter != null) contactsListAdapter.getFilter().filter(query);
//                Log.d(TAG, "ContactListActivity.OnQueryTextListener.onQueryTextChange:" +
//                        " query == " + query);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater); // attach the activity menu
    }

    public void onBackPressed() {
        // close search view on back button pressed
        if (searchView != null && !searchView.isIconified()) {
//            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
            return;
        }
    }
}
