package com.stuur.stuur;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stuur.stuur.R;

import org.xmlpull.v1.XmlPullParser;

import static com.stuur.stuur.MainActivity.cur_group_name;
import static com.stuur.stuur.MainActivity.onCreateDialog;
import static com.stuur.stuur.MainActivity.receiveMsgAnimation;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    public static boolean init = true;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        //unique(?) phone id, temporarily in the first friends list position
        String android_id = Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID);
        String[] page_title = {"Settings","Friend List","Friends","Local","Global"};
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        int section_num = getArguments().getInt(ARG_SECTION_NUMBER)-1;
        textView.setText(page_title[section_num]);

        // hide/show contents for seperate pages
        final EditText msg_box = (EditText) rootView.findViewById(R.id.msg_box);
        Button add_friend_btn = (Button) rootView.findViewById(R.id.add_friend_btn);
        if(section_num == 0) {
            // settings page
            msg_box.setVisibility(rootView.GONE);
            add_friend_btn.setVisibility(rootView.GONE);
            MainActivity.hideKeyboard(getActivity());
        } else if(section_num == 1) {
            // friend list page
            msg_box.setVisibility(rootView.GONE);
            add_friend_btn.setVisibility(rootView.VISIBLE);
            MainActivity.hideKeyboard(getActivity());

            //friends[0] used to be "bob"
            final String[] friends = {android_id,"george","sally","bob","george","sally","bob","george","sally","bob","george","sally"};
            final String[] keys = {"5FWAS","234SD","WWWWW","5FWAS","234SD","WWWWW","5FWAS","234SD","WWWWW","5FWAS","234SD","WWWWW"};

            // friends
            ArrayAdapter<String> friends_Adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, friends);
            final ListView friends_listView = (ListView) rootView.findViewById(R.id.friend_list);
            friends_listView.setAdapter(friends_Adapter);
            friends_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected_nick = friends[position];
                    String selected_key = keys[position];
                    Dialog dialog = MainActivity.onCreateDialog(savedInstanceState, view, selected_nick, selected_key);
                    dialog.show();
                }
            });

            // keys
            ArrayAdapter<String> keys_Adapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, keys);
            ListView keys_listView = (ListView) rootView.findViewById(R.id.key_list);
            keys_listView.setAdapter(keys_Adapter);
            keys_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected_nick = friends[position];
                    String selected_key = keys[position];
                    Dialog dialog = MainActivity.onCreateDialog(savedInstanceState, view, selected_nick, selected_key);
                    dialog.show();
                }
            });

            // add friend button
            add_friend_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = MainActivity.onCreateDialog(savedInstanceState, v);
                    dialog.show();

                }
            });

        } else {

            if(section_num == 2) cur_group_name = "friends";
            else if(section_num == 3) cur_group_name = "local";
            else cur_group_name = "global";

            receiveMsgAnimation(rootView);

            msg_box.setVisibility(rootView.VISIBLE);
            add_friend_btn.setVisibility(rootView.GONE);

            msg_box.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (event.getAction() == KeyEvent.ACTION_UP) {
                            if (msg_box.getText().length() == 0) {
                                MainActivity.shakeMsgBox(rootView);
                            } else {
                                MainActivity.sendMsgAnimation(rootView);
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });

            // click message to see next message
            /*
            msg_box.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    receiveMsgAnimation(v, final_group_name);
                }
            });
            */

            // click screen to see next message
            RelativeLayout rlayout = (RelativeLayout) rootView.findViewById(R.id.fragmentxml);
            rlayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    receiveMsgAnimation(v);
                }

            });
        }

        return rootView;
    }
}