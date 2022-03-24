package com.example.cameraexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter_mine extends RecyclerView.Adapter<RecyclerAdapter_mine.ViewHolder> {

    private ArrayList<RecyclerItem_mine> mData = null;

    public RecyclerAdapter_mine(ArrayList<RecyclerItem_mine> list) {
        mData = list;
    }

    @Override
    public RecyclerAdapter_mine.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_mine, parent, false);
        RecyclerAdapter_mine.ViewHolder vh = new RecyclerAdapter_mine.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_mine.ViewHolder holder, int position) {

        RecyclerItem_mine item = mData.get(position);

        holder.food.setImageResource(item.getIdForFoodDrawable());
        holder.title.setText(item.getTitle());
        holder.hash.setText(item.getHash());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView food;
        TextView title;
        TextView hash;

        ViewHolder(View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.food);
            title = itemView.findViewById(R.id.title);
            hash = itemView.findViewById(R.id.hash);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast myToast = Toast.makeText(v.getContext(),"1111", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            });

            CheckBox checkBox;
            checkBox = itemView.findViewById(R.id.recyclerview_checkbox);

        };
    }
}

