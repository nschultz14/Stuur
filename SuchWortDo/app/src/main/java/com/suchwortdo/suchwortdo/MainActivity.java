package com.suchwortdo.suchwortdo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static boolean init = true;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(2);
    }

    public static Dialog onCreateDialog(Bundle savedInstanceState, View v, String nick, String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(v.getContext());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.dialog_add_friend, null);
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // update the user
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // delete the user
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        EditText nickname_widget = (EditText) dialogView.findViewById(R.id.nickname);
        EditText key_widget = (EditText) dialogView.findViewById(R.id.key);
        TextView title = (TextView) dialogView.findViewById(R.id.dialog_title);
        nickname_widget.setText(nick);
        key_widget.setText(key);
        title.setText("Edit " + nick);

        return builder.create();
    }

    public static Dialog onCreateDialog(Bundle savedInstanceState, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(v.getContext());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_add_friend, null))
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add the user
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public static void shakeAnimation(View v) {
        Animation shake = AnimationUtils.loadAnimation(v.getContext(), R.anim.shake);
        v.findViewById(R.id.msg_box).startAnimation(shake);
    }

    public static void sendMsgAnimation(View v) {
        final View finalv = v;
        Animation move_up = AnimationUtils.loadAnimation(v.getContext(), R.anim.move_up);
        Animation replace = AnimationUtils.loadAnimation(v.getContext(), R.anim.replace);

        replace.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                EditText editText = (EditText) finalv.findViewById(R.id.msg_box);
                editText.setText("", TextView.BufferType.EDITABLE);

                CharSequence text = "Message Sent";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(finalv.getContext(), text, duration);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, -200);
                toast.show();
            }
        });

        v.findViewById(R.id.anim_msg_box).setVisibility(View.VISIBLE);
        v.findViewById(R.id.msg_box).startAnimation(move_up);
        v.findViewById(R.id.anim_msg_box).startAnimation(replace);
        v.findViewById(R.id.anim_msg_box).setVisibility(View.INVISIBLE);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
