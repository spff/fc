package com.example.spff.fc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button toFragment1 = (Button) view.findViewById(R.id.fragment_menu_button_to_fragment1);
        Button toFragment2 = (Button) view.findViewById(R.id.fragment_menu_button_to_fragment2);
        Button toFragment3 = (Button) view.findViewById(R.id.fragment_menu_button_to_fragment3);

        toFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchFragment(1);
            }
        });

        toFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchFragment(2);
            }
        });

        toFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).switchFragment(3);
            }
        });
    }


}
