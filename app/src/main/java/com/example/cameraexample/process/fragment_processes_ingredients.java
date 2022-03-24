package com.example.cameraexample.process;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.cameraexample.MainActivity;
import com.example.cameraexample.R;

import java.util.ArrayList;

public class fragment_processes_ingredients extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("LifeCycle", "f_p_i_onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LifeCycle", "f_p_i_onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("LifeCycle", "f_p_i_onCreateView");
        return inflater.inflate(R.layout.fragment_processes_ingredients, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("LifeCycle", "f_p_i_onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("LifeCycle", "f_p_i_onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LifeCycle", "f_p_i_onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("LifeCycle", "f_p_i_onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("LifeCycle", "f_p_i_onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("LifeCycle", "f_p_i_onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycle", "f_p_i_onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("LifeCycle", "f_p_i_onDetach");
    }

    // 배추김치, 두부, 삼겹살, 계란, 대파, 감자
    public void CheckBoxToListView(final ListView_d_i_Adapter adapter, View view) {
        CheckBox checkBox_0 = (CheckBox)view.findViewById(R.id.배추김치);
        checkBox_0.setOnClickListener(new CheckBox.OnClickListener(){
           @Override
           public void onClick(View v) {
               if (((CheckBox)v).isChecked()) {
                    adapter.AddItem("배추김치");
               } else {
                    adapter.deleteItem("배추김치");
               }
           }
        });

        CheckBox checkBox_1 = (CheckBox)view.findViewById(R.id.두부);
        checkBox_1.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    adapter.AddItem("두부");
                } else {
                    adapter.deleteItem("두부");
                }
            }
        });

        CheckBox checkBox_2 = (CheckBox)view.findViewById(R.id.삼겹살);
        checkBox_2.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    adapter.AddItem("삼겹살");
                } else {
                    adapter.deleteItem("삼겹살");
                }
            }
        });

        CheckBox checkBox_3 = (CheckBox)view.findViewById(R.id.계란);
        checkBox_3.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    adapter.AddItem("계란");
                } else {
                    adapter.deleteItem("계란");
                }
            }
        });

        CheckBox checkBox_4 = (CheckBox)view.findViewById(R.id.대파);
        checkBox_4.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    adapter.AddItem("대파");
                } else {
                    adapter.deleteItem("대파");
                }
            }
        });

        CheckBox checkBox_5 = (CheckBox)view.findViewById(R.id.감자);
        checkBox_5.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    adapter.AddItem("감자");
                } else {
                    adapter.deleteItem("감자");
                }
            }
        });
    }

    // 배추김치, 두부, 삼겹살, 계란, 대파, 감자
    public void initCheckBox(ArrayList<String> detectedItems, View view) {

        CheckBox checkBox;

        for (int i = 0; i < detectedItems.size(); i++) {
            String ingredient = detectedItems.get(i);
            switch (ingredient) {
                case "배추김치":
                    checkBox = view.findViewById(R.id.배추김치);
                    checkBox.setChecked(true);
                    break;
                case "두부":
                    checkBox = view.findViewById(R.id.두부);
                    checkBox.setChecked(true);
                    break;
                case "삼겹살":
                    checkBox = view.findViewById(R.id.삼겹살);
                    checkBox.setChecked(true);
                    break;
                case "계란":
                    checkBox = view.findViewById(R.id.계란);
                    checkBox.setChecked(true);
                    break;
                case "대파":
                    checkBox = view.findViewById(R.id.대파);
                    checkBox.setChecked(true);
                    break;
                case "감자":
                    checkBox = view.findViewById(R.id.감자);
                    checkBox.setChecked(true);
                    break;

            }
        }
    }
}