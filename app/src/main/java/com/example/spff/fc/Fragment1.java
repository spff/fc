package com.example.spff.fc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    public Fragment1() {
        // Required empty public constructor
    }


    private FragmentManager manager;
    private FragmentTransaction transaction;

    private ListView listView;
    private MyAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment1, container, false);

        replaceMenuFrag();

        listView = (ListView)view.findViewById(R.id.listView);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                adapter.addItem();
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public void replaceMenuFrag(){

        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        transaction.replace(R.id.fragment1_left_for_menu_frag, menuFragment, "menuFragment");
        transaction.commit();

    }




    public class MyAdapter extends BaseAdapter {
        private ArrayList<String> mList;
        private GregorianCalendar gregorianCalendar;


        public MyAdapter(){
            mList = new ArrayList<>();
        }

        public void addItem()
        {
            gregorianCalendar = new GregorianCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
            dateFormat.setTimeZone(gregorianCalendar.getTimeZone());
            mList.add(dateFormat.format(gregorianCalendar.getTime()));
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Holder holder;
            if(v == null){
                v = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.list_item, parent, false);

                holder = new Holder();
                holder.img = (ImageView) v.findViewById(R.id.list_img);
                holder.text = (TextView) v.findViewById(R.id.list_text);
                v.setTag(holder);

            } else{
                holder = (Holder) v.getTag();
            }
            holder.text.setText(mList.get(position));

            return v;
        }
        class Holder{
            ImageView img;
            TextView text;
        }
    }

}
