package com.example.cameraexample.process;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cameraexample.R;

import java.util.ArrayList;


public class ListView_d_i_Adapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list;
    private Context context;

    private OnItemClick onItemClick;

    public ListView_d_i_Adapter(ArrayList<String> list, Context context, OnItemClick onItemClick) {
        this.list = list;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_detected_item, null);
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View innerView = inflater.inflate(R.layout.fragment_processes_ingredients, null);

        TextView listItemText = view.findViewById(R.id.detected_item_textView);
        listItemText.setText(list.get(position));

        Button deleteBtn = view.findViewById(R.id.detected_item_button);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(list.get(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void deleteItem(String ingredient) {
        int indexOfTarget = list.indexOf(ingredient);
        list.remove(indexOfTarget);
        notifyDataSetChanged();
    }

    public void AddItem(String ingredient) {
        list.add(ingredient);
        notifyDataSetChanged();
    }

}
