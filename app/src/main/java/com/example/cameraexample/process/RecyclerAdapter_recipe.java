package com.example.cameraexample.process;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cameraexample.R;

import java.util.ArrayList;

public class RecyclerAdapter_recipe extends RecyclerView.Adapter<RecyclerAdapter_recipe.ViewHolder> {


    private ArrayList<RecyclerItem_recipe> mData = null;

    public RecyclerAdapter_recipe(ArrayList<RecyclerItem_recipe> list) {
        mData = list;
    }

    @Override
    public RecyclerAdapter_recipe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_recipe, parent, false);
        RecyclerAdapter_recipe.ViewHolder vh = new RecyclerAdapter_recipe.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_recipe.ViewHolder holder, int position) {

        RecyclerItem_recipe item = mData.get(position);

        holder.food.setImageResource(item.getIdForFoodDrawable());
        //holder.food.setImageDrawable(item.getFood());
        holder.title.setText(item.getTitle());
        holder.hash.setText(item.getHash());
        holder.url = item.getUrl();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView food;
        TextView title;
        TextView hash;
        String url;

        ViewHolder(View itemView) {
            super(itemView);

            food = itemView.findViewById(R.id.food);
            title = itemView.findViewById(R.id.title);
            hash = itemView.findViewById(R.id.hash);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse(url))
                            .setPackage("com.google.android.youtube"));
                }
            });

            CheckBox checkBox;
            checkBox = itemView.findViewById(R.id.recyclerview_checkbox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if(((CheckBox)v).isChecked()) {
                            mData.get(pos).setCheckedOrNot(true);

                            Context context = v.getContext();
                            Intent intent = new Intent("test");
                            intent.putExtra("Parcelable", mData.get(pos));

                            context.sendBroadcast(intent);
                        } else {
                            mData.get(pos).setCheckedOrNot(false);

                            Context context = v.getContext();
                            Intent intent = new Intent("test");
                            intent.putExtra("Parcelable", mData.get(pos));

                            context.sendBroadcast(intent);
                        }
                    }
                }
            });
        }
    }

}

