package com.example.spff.fc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {


    public Fragment3() {
        // Required empty public constructor
    }

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment3, container, false);


        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        transaction.replace(R.id.fragment3_left_for_menu_frag, menuFragment, "menuFragment");
        transaction.commit();

        return view;
    }

}
