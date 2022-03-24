package com.example.cameraexample.process;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cameraexample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class ProcessesActivity extends AppCompatActivity implements OnItemClick {

    private ByteBuffer imgData = ByteBuffer.allocateDirect(1 * 416 * 416 * 3 * 4);
    private int inputSize = 416;
    private int[] intValues = new int[inputSize * inputSize];
    private static final float IMAGE_MEAN = 128.0f;
    private static final float IMAGE_STD = 128.0f;
    private float[][][][] outputLocations = new float[1][13][13][55];
    private static int NUM_BOXES_PER_BLOCK = 5;
    private static int NUM_CLASSES = 6;
    private static ArrayList<Integer> Results = new ArrayList<>();
    private static ArrayList<Float> confidences = new ArrayList<>();
    private static ArrayList<Float> confidences2 = new ArrayList<>();

    private static JSONObject jsonObject = new JSONObject();
    private static JSONArray jsonArray = new JSONArray();
    private static JSONArray final_jsonArray = new JSONArray();
    private static JSONObject result_Json_Object;
    private static JSONArray result_Json_Array;
    private static JSONObject result_Json_Object1;
    private static JSONObject result_Json_Object2;
    private static String result_String1;
    private static String result_String2;

    private static HashMap<Integer, String> allIngredient = new HashMap<>();
    private static ArrayList<String> detectedIngredient = new ArrayList<>();
    private static ArrayList<String> ingredientOfListView = new ArrayList<>();

    private RequestQueue queue;

    public CheckBox checkBox;

    MutableLiveData<String> Button_delete = new MutableLiveData<>();
    private static ListView_d_i_Adapter detectedIngredientAdapter;

    final fragment_processes_ingredients fragment_processes_ingredients = new fragment_processes_ingredients();
    final fragment_processes_recipe fragment_processes_recipe = new fragment_processes_recipe();
    public RecyclerItem_recipe recyclerItem = new RecyclerItem_recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LifeCycle", "P_onCreate()");

        setContentView(R.layout.activity_processes);
        // Button_delete.setValue("Changed value");

        recyclerItem.setIdForFoodDrawable(1);
        recyclerItem.setHash("hash");
        recyclerItem.setTitle("title");

        //Log.i("LifeCycle", "fragment_before");
        //ingredients
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.ingredient_recipe, fragment_processes_ingredients,"ing");
        fragmentTransaction.commit();

        /*Log.i("LifeCycle", "fragment_After commitNow");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ingredient_recipe);
        Log.i("LifeCycle", "fragment_Before getView");
        final View fragmentIngredientView = fragment.getView();

        Button_delete.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String changedValue) {
                uncheckCheckbox(fragmentIngredientView, changedValue);
            }
        });

        fragment_processes_ingredients.CheckBoxToListView(detectedIngredientAdapter,fragmentIngredientView);

        fragment_processes_ingredients.initCheckBox(ingredientOfListView, fragmentIngredientView);*/


        Button addIngredient = findViewById(R.id.button1);
        Button findRecipe = findViewById(R.id.button3);

        addIngredient.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag_rToI();
                Log.i("LifeCycle", "after_changeFrag_rToI");
            }
        });

        findRecipe.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag_iToR();
            }
        });



        allIngredient.put(0, "배추김치");
        allIngredient.put(1, "두부");
        allIngredient.put(2, "삼겹살");
        allIngredient.put(3, "계란");
        allIngredient.put(4, "대파");
        allIngredient.put(5, "감자");

        try {
            MappedByteBuffer modelFile = loadModelFile(getAssets(), "my-tiny-yolo_6.tflite");
            Interpreter interpreter = new Interpreter(modelFile);
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.a);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 416, 416, true);
            processImage(resizedBitmap);
            interpreter.run(imgData, outputLocations);
        } catch (IOException e)
        {

        }

        Results = processOutput(outputLocations);

        // ListView
        for(int i = 0; i < Results.size(); i++) {
            int numOfClass = Results.get(i);
            if(allIngredient.containsKey(numOfClass)) {
                detectedIngredient.add(allIngredient.get(numOfClass));
            }
        }

        detectedIngredientAdapter = new ListView_d_i_Adapter(detectedIngredient, this, this);

        ListView lView = (ListView)findViewById(R.id.listView1);
        lView.setAdapter(detectedIngredientAdapter);


        // 재료 인식후 레시피를 얻기 위해서 서버와 통신
        try {
            jsonArray.put("김치");
            jsonArray.put("두부");
            jsonObject.put("label", jsonArray);
            final_jsonArray.put(jsonObject);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        queue = Volley.newRequestQueue(this);


        String url = "https://48e896674db5.ngrok.io/recipe";
        JsonArrayRequest AR_Object = new JsonArrayRequest(Request.Method.POST, url, final_jsonArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                result_Json_Array = response;
                try {
                    result_Json_Object1 = result_Json_Array.getJSONObject(1);
                    result_Json_Object2 = result_Json_Array.getJSONObject(2);
                    result_String1 = result_Json_Object1.getString("음식");
                    result_String2 = result_Json_Object1.getString("주소");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "네트워크 연결 오류.", Toast.LENGTH_SHORT).show();
                Log.i("VolleyError", "Volley Error in receiv");
            }
        });
        queue.add(AR_Object);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("LifeCycle", "P_onStart()");


        Fragment fragment = getSupportFragmentManager().findFragmentByTag("ing");

        if(fragment != null) {

            final View fragmentIngredientView = fragment.getView();

            Button_delete.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String changedValue) {
                    uncheckCheckbox(fragmentIngredientView, changedValue);
                }
            });

            fragment_processes_ingredients.CheckBoxToListView(detectedIngredientAdapter, fragmentIngredientView);

            fragment_processes_ingredients.initCheckBox(detectedIngredient, fragmentIngredientView);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeCycle", "P_onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeCycle", "P_onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycle", "P_onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LifeCycle", "P_onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycle", "P_onDestroy()");
    }

    @Override
    public void onClick (String value)
    {
        Button_delete.setValue(value);
    }


    private void changeFrag_iToR() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.ingredient_recipe, fragment_processes_recipe);
        fragmentTransaction.commit();

        if(!ingredientOfListView.isEmpty()) {
            ingredientOfListView.clear();
        }

        for(int i = 0; i < detectedIngredientAdapter.getCount(); i++) {
            ingredientOfListView.add((String) detectedIngredientAdapter.getItem(i));
        }
    }

    private void changeFrag_rToI() {
        Log.i("LifeCycle", "fragment_before");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.ingredient_recipe, fragment_processes_ingredients, "ing");
        fragmentTransaction.commitNow();
        Log.i("LifeCycle", "fragment_after_commitNow");

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ingredient_recipe);

        final View fragmentIngredientView = fragment.getView();

        Button_delete.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String changedValue) {
                uncheckCheckbox(fragmentIngredientView, changedValue);
            }
        });

        fragment_processes_ingredients.CheckBoxToListView(detectedIngredientAdapter,fragmentIngredientView);

        fragment_processes_ingredients.initCheckBox(ingredientOfListView, fragmentIngredientView);
    }





    private void uncheckCheckbox(View view, String position) {

        CheckBox checkBox;

        switch (position) {
            case "배추김치":
                checkBox = view.findViewById(R.id.배추김치);
                if (checkBox.isChecked()) {
                    checkBox.toggle();
                }
                break;
            case "두부":
                checkBox = view.findViewById(R.id.두부);
                if (checkBox.isChecked()) {
                    checkBox.toggle();
                }
                break;
            case "삼겹살":
                checkBox = view.findViewById(R.id.삼겹살);
                if (checkBox.isChecked()) {
                    checkBox.toggle();
                }
                break;
            case "계란":
                checkBox = view.findViewById(R.id.계란);
                if (checkBox.isChecked()) {
                    checkBox.toggle();
                }
                break;
            case "대파":
                checkBox = view.findViewById(R.id.대파);
                if (checkBox.isChecked()) {
                    checkBox.toggle();
                }
                break;
            case "감자":
                checkBox = view.findViewById(R.id.감자);
                if (checkBox.isChecked()) {
                    checkBox.toggle();
                }
                break;
            default:
                break;
        }
    }



    private static MappedByteBuffer loadModelFile(AssetManager assets, String modelFilename)
            throws IOException {
        AssetFileDescriptor fileDescriptor = assets.openFd(modelFilename);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    public void processImage(final Bitmap bitmap) {
        imgData.order(ByteOrder.nativeOrder());
        imgData.rewind();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 416, 416, true);
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());


        for (int i = 0; i < inputSize; ++i) {
            for (int j = 0; j < inputSize; ++j) {
                int pixelValue = intValues[i * inputSize + j];
                if (false) {
                    // Quantized model
                    imgData.put((byte) ((pixelValue >> 16) & 0xFF));
                    imgData.put((byte) ((pixelValue >> 8) & 0xFF));
                    imgData.put((byte) (pixelValue & 0xFF));
                } else { // Float model
                    imgData.putFloat((((pixelValue >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                    imgData.putFloat((((pixelValue >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                    imgData.putFloat(((pixelValue & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                }
            }
        }
    }


    private ArrayList<Integer> processOutput(float[][][][] data)
    {
        ArrayList<Integer> ResultList = new ArrayList<>();
        TreeSet<Integer> ResultTreeSet = new TreeSet<>();
        ArrayList<Integer> UniqueResultList;
        float[][][] output = data[0];
        ArrayList<Float> List = new ArrayList<>();

        int gridHeight = 13;
        int gridWidth = 13;

        for(int i = 0 ; i < gridHeight ; i++) {
            for(int j = 0; j < gridWidth ; j++) {
                for(float temp : output[i][j]){
                    List.add(temp);
                }
            }
        }

        for(int y = 0 ; y < gridHeight ; y++) {
            for(int x = 0; x < gridWidth ; x++) {
                for(int b = 0; b < NUM_BOXES_PER_BLOCK; b++) {
                    // 각 grid cell의 시작 지점
                    int offset = (gridWidth * (NUM_BOXES_PER_BLOCK * (NUM_CLASSES + 5)) * y
                            + NUM_BOXES_PER_BLOCK * (NUM_CLASSES + 5) * x
                            + (NUM_CLASSES + 5) * b);

                    float confidence = sigmoid(List.get(offset + 4));

                    confidences.add(confidence);

                    int detectedClass = -1;
                    float maxClass = 0f;

                    float[] classes = new float[NUM_CLASSES];
                    for (int c = 0; c < NUM_CLASSES; c++) {
                        classes[c] = List.get(offset + 5 + c);
                    }
                    softmax(classes);

                    for (int c = 0; c < NUM_CLASSES; c++) {
                        if (classes[c] > maxClass) {
                            detectedClass = c;
                            maxClass = classes[c];
                        }
                    }

                    float confidenceInClass = maxClass * confidence;
                    if (confidenceInClass > 0.1) {
                        ResultList.add(detectedClass);
                    }
                }
            }
        }

        ResultTreeSet = new TreeSet<>(ResultList);
        UniqueResultList = new ArrayList<>(ResultTreeSet);

        return UniqueResultList;
    }


    private float sigmoid(float x) {
        return (float)(1.0 / (1.0 + Math.exp(-x)));
    }

    private void softmax(float[] floats) {
        float max = Float.NEGATIVE_INFINITY;
        for (float number : floats) {
            max = Math.max(max, number);
        }
        float sum = 0.0f;
        for (int i = 0; i < NUM_CLASSES; i++) {
            floats[i] = (float)Math.exp((floats[i] - max));
            sum += floats[i];
        }

        for (int i = 0; i < NUM_CLASSES; i++) {
            floats[i] = floats[i] / sum;
        }
    }

}