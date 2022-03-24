package com.example.cameraexample.process;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cameraexample.R;

import java.util.ArrayList;

public class fragment_processes_recipe extends Fragment {

    RecyclerView mRecyclerView = null;
    RecyclerAdapter_recipe mAdapter = null;
    ArrayList<RecyclerItem_recipe> mList = new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("LifeCycle", "f_p_r_onAttach");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("LifeCycle", "f_p_r_onCreateView");
        View view = inflater.inflate(R.layout.fragment_processes_recipe, container, false);
        mRecyclerView = view.findViewById(R.id.recipeRecycler);

        mAdapter = new RecyclerAdapter_recipe(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(mList.isEmpty())
        addItem(R.drawable.a, "Title1", "hash1", "https://www.youtube.com/watch?v=5eQ1rf642QI");
        //addItem(R.drawable.b, "Title2", "hash2", "https://www.youtube.com/watch?v=5eQ1rf642QI");
        //addItem(R.drawable.c, "Title3", "hash3", "https://www.youtube.com/watch?v=5eQ1rf642QI");

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LifeCycle", "f_p_r_onCreate");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("LifeCycle", "f_p_r_onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("LifeCycle", "f_p_r_onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LifeCycle", "f_p_r_onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("LifeCycle", "f_p_r_onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("LifeCycle", "f_p_r_onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("LifeCycle", "f_p_r_onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycle", "f_p_r_onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("LifeCycle", "f_p_r_onDetach");
    }

    public void addItem(int id, String title, String hash, String url) {
        RecyclerItem_recipe item = new RecyclerItem_recipe();

        item.setIdForFoodDrawable(id);
        item.setTitle(title);
        item.setHash(hash);
        item.setUrl(url);

        mList.add(item);
    }
}