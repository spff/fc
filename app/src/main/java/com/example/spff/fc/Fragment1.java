package com.example.spff.fc;


import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ListView listView;
    public SimpleAdapter adapter;
    public List<Map<String, Object>> items;

    private GregorianCalendar gregorianCalendar;
    public ItemDataAccessObject itemDataAccessObject;

    public Fragment1() {
        // Required empty public constructor
    }

    public void updateList(int position, String string) {
        Map<String, Object> item = (HashMap<String, Object>) adapter.getItem(position);
        item.put("text", string);
        itemDataAccessObject.update(item);
        items.set(position, item);
        adapter.notifyDataSetChanged();
    }

    public void updateList(int position, Uri uri, String toPut) {//toPut should be "thumbnailURI" or "cropURI"
        Map<String, Object> item = (HashMap<String, Object>) adapter.getItem(position);
        if (/*toPut == "thumbnailURI" &&*/ items.get(position).get(toPut) instanceof Uri) {
            new File(((Uri) items.get(position).get(toPut)).getPath()).delete();
        }
        item.put(toPut, uri);
        itemDataAccessObject.update(item);
        items.set(position, item);

        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment1, container, false);

        replaceMenuFrag();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new SimpleAdapter(
                getContext(), items,
                R.layout.list_item,
                new String[]{"thumbnailURI", "text", "cropURI", "SQLid"},
                new int[]{R.id.list_img, R.id.list_text, R.id.list_uri}//not sure if we can have fewer "to" array than "from" array
        );

        SimpleAdapter.ViewBinder viewBinder = new SimpleAdapter.ViewBinder() {

            public boolean setViewValue(View view, Object data, String textRepresentation) {


                boolean isUri = false;

                if (view instanceof ImageView && data instanceof Uri) {
                    if (new File(((Uri) data).getPath()).exists()) {
                        ImageView imageView = (ImageView) view;
                        imageView.setImageURI((Uri) data);

                        isUri = true;
                    }
                }
                return isUri;
            }
        };
        adapter.setViewBinder(viewBinder);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int position, long id) {

                ((MainActivity) getActivity()).editFragment1List(
                        position,
                        (String) ((Map<String, Object>) adapter.getItem(position)).get("text"),
                        ((Map<String, Object>) adapter.getItem(position)).get("cropURI")
                );

            }
        });


        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long id) {
                                /*
                                 * 点击列表项时触发onItemClick方法，四个参数含义分别为
                                 * arg0：发生事件的AdapterView
                                 * arg1：AdapterView中被点击的View
                                 * position：当前点击的行在adapter的下标
                                 * id：当前点击的行的id
                                 */
                Toast.makeText(getContext(), adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                //list.remove(arg2);
                new AlertDialog.Builder(getContext())
                        .setMessage("Delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if( items.get(position).get("thumbnailURI") instanceof Uri) {
                                    new File(((Uri) items.get(position).get("thumbnailURI")).getPath()).delete();
                                }
                                if( items.get(position).get("cropURI") instanceof Uri) {
                                    new File(((Uri) items.get(position).get("cropURI")).getPath()).delete();
                                }
                                itemDataAccessObject.delete((String) items.get(position).get("SQLid"));
                                items.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).show();
                return true;
            }
        });

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    if( items.get(position).get("thumbnailURI") instanceof Uri) {
                                        new File(((Uri) items.get(position).get("thumbnailURI")).getPath()).delete();
                                    }
                                    if( items.get(position).get("cropURI") instanceof Uri) {
                                        new File(((Uri) items.get(position).get("cropURI")).getPath()).delete();
                                    }
                                    itemDataAccessObject.delete((String) items.get(position).get("SQLid"));
                                    items.remove(position);
                                    adapter.notifyDataSetChanged();

                                }

                            }
                        });
        listView.setOnTouchListener(touchListener);


        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gregorianCalendar = new GregorianCalendar();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
                dateFormat.setTimeZone(gregorianCalendar.getTimeZone());

                Map<String, Object> item = new HashMap<>();
                item.put("thumbnailURI", R.mipmap.ic_launcher);
                item.put("text", dateFormat.format(gregorianCalendar.getTime()));
                item.put("cropURI", R.mipmap.ic_launcher);


                itemDataAccessObject.insert(item);
                items.add(item);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void replaceMenuFrag() {

        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        transaction.replace(R.id.fragment1_left_for_menu_frag, menuFragment, "menuFragment");
        transaction.commit();

    }

}