package com.example.cameraexample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

public class fragment_mine extends Fragment {

    RecyclerView mRecyclerView = null;
    RecyclerAdapter_mine mAdapter = null;
    ArrayList<RecyclerItem_mine> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_processes_recipe, container, false);
        mRecyclerView = view.findViewById(R.id.recipeRecycler);
        mList = getArguments().getParcelableArrayList("List");
        mAdapter = new RecyclerAdapter_mine(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addItem(int id, String title, String hash) {
        RecyclerItem_mine item = new RecyclerItem_mine();

        item.setIdForFoodDrawable(id);
        item.setTitle(title);
        item.setHash(hash);

        mList.add(item);
    }

    public void deleteItem(int id) {
        for (int i = 0; i < mList.size(); i++) {
            if(id == mList.get(i).getIdForFoodDrawable()) {
                mList.remove(i);
            }
        }
    }
}
