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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageView;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.Checkable;
import android.net.Uri;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import android.widget.AdapterView;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
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
    private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

    private GregorianCalendar gregorianCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment1, container, false);

        replaceMenuFrag();


        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new MyAdapter(getContext(), items, R.layout.list_item, new String[]{"image", "text"},
                new int[]{R.id.list_img, R.id.list_text});
        listView.setAdapter(adapter);

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


        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gregorianCalendar = new GregorianCalendar();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
                dateFormat.setTimeZone(gregorianCalendar.getTimeZone());

                Map<String, Object> item = new HashMap<String, Object>();
                item.put("image", R.mipmap.ic_launcher);
                item.put("text", dateFormat.format(gregorianCalendar.getTime()));
                items.add(item);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public void replaceMenuFrag() {

        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        MenuFragment menuFragment = new MenuFragment();
        transaction.replace(R.id.fragment1_left_for_menu_frag, menuFragment, "menuFragment");
        transaction.commit();

    }


    public static class MyAdapter extends BaseAdapter implements Filterable {
        private int[] mTo;
        private String[] mFrom;
        private ViewBinder mViewBinder;

        private List<? extends Map<String, ?>> mData;

        private int mResource;
        private int mDropDownResource;
        private LayoutInflater mInflater;

        private SimpleFilter mFilter;
        private ArrayList<Map<String, ?>> mUnfilteredData;

        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         *                 in the from parameter.
         */
        public MyAdapter(Context context, List<? extends Map<String, ?>> data,
                         int resource, String[] from, int[] to) {
            mData = data;
            mResource = mDropDownResource = resource;
            mFrom = from;
            mTo = to;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        /**
         * @see android.widget.Adapter#getCount()
         */
        public int getCount() {
            return mData.size();
        }

        /**
         * @see android.widget.Adapter#getItem(int)
         */
        public Object getItem(int position) {
            return mData.get(position);
        }

        /**
         * @see android.widget.Adapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * @see android.widget.Adapter#getView(int, View, ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            return createViewFromResource(position, convertView, parent, mResource);
        }

        private View createViewFromResource(int position, View convertView,
                                            ViewGroup parent, int resource) {
            View v;
            if (convertView == null) {
                v = mInflater.inflate(resource, parent, false);
            } else {
                v = convertView;
            }

            bindView(position, v);

            return v;
        }

        /**
         * <p>Sets the layout resource to create the drop down views.</p>
         *
         * @param resource the layout resource defining the drop down views
         * @see #getDropDownView(int, android.view.View, android.view.ViewGroup)
         */
        public void setDropDownViewResource(int resource) {
            this.mDropDownResource = resource;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return createViewFromResource(position, convertView, parent, mDropDownResource);
        }

        private void bindView(int position, View view) {
            final Map dataSet = mData.get(position);
            if (dataSet == null) {
                return;
            }

            final ViewBinder binder = mViewBinder;
            final String[] from = mFrom;
            final int[] to = mTo;
            final int count = to.length;

            for (int i = 0; i < count; i++) {
                final View v = view.findViewById(to[i]);
                if (v != null) {
                    final Object data = dataSet.get(from[i]);
                    String text = data == null ? "" : data.toString();
                    if (text == null) {
                        text = "";
                    }

                    boolean bound = false;
                    if (binder != null) {
                        bound = binder.setViewValue(v, data, text);
                    }

                    if (!bound) {
                        if (v instanceof Checkable) {
                            if (data instanceof Boolean) {
                                ((Checkable) v).setChecked((Boolean) data);
                            } else if (v instanceof TextView) {
                                // Note: keep the instanceof TextView check at the bottom of these
                                // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                                setViewText((TextView) v, text);
                            } else {
                                throw new IllegalStateException(v.getClass().getName() +
                                        " should be bound to a Boolean, not a " +
                                        (data == null ? "<unknown type>" : data.getClass()));
                            }
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else if (v instanceof ImageView) {
                            if (data instanceof Integer) {
                                setViewImage((ImageView) v, (Integer) data);
                            } else {
                                setViewImage((ImageView) v, text);
                            }
                        } else {
                            throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                    " view that can be bounds by this SimpleAdapter");
                        }
                    }
                }
            }
        }

        /**
         * Returns the {@link ViewBinder} used to bind data to views.
         *
         * @return a ViewBinder or null if the binder does not exist
         * @see #setViewBinder
         */
        public ViewBinder getViewBinder() {
            return mViewBinder;
        }

        /**
         * Sets the binder used to bind data to views.
         *
         * @param viewBinder the binder used to bind data to views, can be null to
         *                   remove the existing binder
         * @see #getViewBinder()
         */
        public void setViewBinder(ViewBinder viewBinder) {
            mViewBinder = viewBinder;
        }

        /**
         * Called by bindView() to set the image for an ImageView but only if
         * there is no existing ViewBinder or if the existing ViewBinder cannot
         * handle binding to an ImageView.
         * <p>
         * This method is called instead of {@link #setViewImage(ImageView, String)}
         * if the supplied data is an int or Integer.
         *
         * @param v     ImageView to receive an image
         * @param value the value retrieved from the data set
         * @see #setViewImage(ImageView, String)
         */
        public void setViewImage(ImageView v, int value) {
            v.setImageResource(value);
        }

        /**
         * Called by bindView() to set the image for an ImageView but only if
         * there is no existing ViewBinder or if the existing ViewBinder cannot
         * handle binding to an ImageView.
         * <p>
         * By default, the value will be treated as an image resource. If the
         * value cannot be used as an image resource, the value is used as an
         * image Uri.
         * <p>
         * This method is called instead of {@link #setViewImage(ImageView, int)}
         * if the supplied data is not an int or Integer.
         *
         * @param v     ImageView to receive an image
         * @param value the value retrieved from the data set
         * @see #setViewImage(ImageView, int)
         */
        public void setViewImage(ImageView v, String value) {
            try {
                v.setImageResource(Integer.parseInt(value));
            } catch (NumberFormatException nfe) {
                v.setImageURI(Uri.parse(value));
            }
        }

        /**
         * Called by bindView() to set the text for a TextView but only if
         * there is no existing ViewBinder or if the existing ViewBinder cannot
         * handle binding to a TextView.
         *
         * @param v    TextView to receive text
         * @param text the text to be set for the TextView
         */
        public void setViewText(TextView v, String text) {
            v.setText(text);
        }

        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new SimpleFilter();
            }
            return mFilter;
        }

        /**
         * This class can be used by external clients of SimpleAdapter to bind
         * values to views.
         * <p>
         * You should use this class to bind values to views that are not
         * directly supported by SimpleAdapter or to change the way binding
         * occurs for views supported by SimpleAdapter.
         *
         * @see SimpleAdapter#setViewImage(ImageView, int)
         * @see SimpleAdapter#setViewImage(ImageView, String)
         * @see SimpleAdapter#setViewText(TextView, String)
         */
        public interface ViewBinder {
            /**
             * Binds the specified data to the specified view.
             * <p>
             * When binding is handled by this ViewBinder, this method must return true.
             * If this method returns false, SimpleAdapter will attempts to handle
             * the binding on its own.
             *
             * @param view               the view to bind the data to
             * @param data               the data to bind to the view
             * @param textRepresentation a safe String representation of the supplied data:
             *                           it is either the result of data.toString() or an empty String but it
             *                           is never null
             * @return true if the data was bound to the view, false otherwise
             */
            boolean setViewValue(View view, Object data, String textRepresentation);
        }

        /**
         * <p>An array filters constrains the content of the array adapter with
         * a prefix. Each item that does not start with the supplied prefix
         * is removed from the list.</p>
         */
        private class SimpleFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();

                if (mUnfilteredData == null) {
                    mUnfilteredData = new ArrayList<Map<String, ?>>(mData);
                }

                if (prefix == null || prefix.length() == 0) {
                    ArrayList<Map<String, ?>> list = mUnfilteredData;
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = prefix.toString().toLowerCase();

                    ArrayList<Map<String, ?>> unfilteredValues = mUnfilteredData;
                    int count = unfilteredValues.size();

                    ArrayList<Map<String, ?>> newValues = new ArrayList<Map<String, ?>>(count);

                    for (int i = 0; i < count; i++) {
                        Map<String, ?> h = unfilteredValues.get(i);
                        if (h != null) {

                            int len = mTo.length;

                            for (int j = 0; j < len; j++) {
                                String str = (String) h.get(mFrom[j]);

                                String[] words = str.split(" ");
                                int wordCount = words.length;

                                for (int k = 0; k < wordCount; k++) {
                                    String word = words[k];

                                    if (word.toLowerCase().startsWith(prefixString)) {
                                        newValues.add(h);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //noinspection unchecked
                mData = (List<Map<String, ?>>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }

}
