package com.example.spff.fc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItemDetail extends Fragment {

    public String text;
    public int imageID;
    private EditText editText;
    private ImageView imageView;

    public FragmentItemDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_item_detail, container, false);


        editText = (EditText) view.findViewById(R.id.edit_text);
        editText.setText(text);
        imageView = (ImageView) view.findViewById(R.id.edit_img);
        imageView.setImageResource(imageID);

        return view;
    }

    @Override
    public void onDetach() {
        ((MainActivity) getActivity()).updateFragment1List(editText.getText().toString());
        super.onDetach();
    }
}
