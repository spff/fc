package com.example.spff.fc;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    final static String TAG = "Fragment3";
    boolean valid = false;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment3, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment3_left_for_menu_frag, new MenuFragment(), "menuFragment")
                .commit();

        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean b2 = view.requestFocusFromTouch();


                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .create();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        view.setTag(true);
                        boolean b2 = view.requestFocusFromTouch();

                        boolean b1 = view.requestFocus();
                        Log.e(TAG, "1in  " + b1 + " " + b2);
                    }
                });
                dialog.show();
            }
        });

        //TODO override a  View.OnClickListener() which will call view.requestFocusFromTouch();
        //TODO and  override onDismiss(DialogInterface dialogInterface) view.setTag(true);
        //TODO and super() it
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                boolean b2 = view.requestFocusFromTouch();
                //boolean b3 = view.requestFocus();


                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .create();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        view.setTag(true);

                        boolean b2 = view.requestFocusFromTouch();
                        boolean b1 = view.requestFocus();
                        Log.e(TAG, "2in  " + b1 + " " + b2);
                    }
                });
                dialog.show();
            }
        });

        button1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e(TAG, "1 " + b);
                if (b){
                    view.setBackgroundColor(Color.WHITE);

                } else {
                    view.setBackgroundColor(Color.BLACK);
                }
                if( !b && (view.getTag() != null && (boolean)view.getTag())){
                    Log.d(TAG, "1 r");
                    view.requestFocusFromTouch();
                    view.requestFocus();
                    view.setTag(false);
                }
            }
        });
        button2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e(TAG, "2 " + b);
                if (b){
                    view.setBackgroundColor(Color.WHITE);

                } else {
                    view.setBackgroundColor(Color.BLACK);
                }
                if( !b && (view.getTag() != null && (boolean)view.getTag())){
                    view.requestFocusFromTouch();
                    view.requestFocus();
                    view.setTag(false);
                }
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                getView().setTag(true);
        }

        return false;
    }


}
