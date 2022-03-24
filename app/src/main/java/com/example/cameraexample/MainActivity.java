package com.example.cameraexample;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cameraexample.process.ProcessesActivity;
import com.example.cameraexample.process.RecyclerItem_recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ClickedHeartReceiver.clickedHeartReceiverInterface {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    private MediaScanner mMediaScanner; // 사진 저장 시 갤러리 폴더에 바로 반영사항을 업데이트 시켜주려면 이 것이 필요하다(미디어 스캐닝)
    private FragmentManager fm = getSupportFragmentManager();
    private fragment_board f_board = new fragment_board();
    private fragment_mine f_mine = new fragment_mine();
    private fragment_recommendation f_recommendation = new fragment_recommendation();
    private fragment_refrigerator f_refrigerator = new fragment_refrigerator();
    public ArrayList<RecyclerItem_mine> adapterList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("LifeCycle", "M_onCreate");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment, f_recommendation);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchFragment(item);
                return true;
            }
        });


        ImageButton search = (ImageButton) findViewById(R.id.imageButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TedPermission.with(getApplicationContext())
                        .setPermissionListener(permissionListener)
                        .setRationaleMessage("카메라 권한이 필요합니다.")
                        .setDeniedMessage("거부하셨습니다.")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 카메라 호출하기 위한 인텐트 생성.
                if (intent.resolveActivity(getPackageManager()) != null) { // 카메라 호출하기 위한 액티비티 존재 유무 파악(if문 실행 X)
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(); // 사진을 저장할 빈 파일을 만든다.
                    } catch (IOException e) {

                    }

                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile); // 파일의 uri 얻어오기.
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // 만들어진 빈 파일의 정보를 카메라 액티비티에 전송.
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); //카메라 호출.
                    }
                }
            }
        });

        ClickedHeartReceiver receiver = new ClickedHeartReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("test");
        registerReceiver(receiver, intentFilter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeCycle", "M_onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeCycle", "M_onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeCycle", "M_onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycle", "M_onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LifeCycle", "M_onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycle", "M_onDestroy()");
    }

    // 빈 파일을 하나 만듬.
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // 현재 날짜 저장
        String imageFileName = "TEST_" + timeStamp + "_"; // TEST_현재날짜_ 저장
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // media scanner를 이용할때 사용하는 저장 위치 저장.
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        ); // 인자 값 정보들로 빈 파일을 만든다.
        imageFilePath = image.getAbsolutePath(); // 빈파일의 위치를 저장.
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 카메라 호출 된 후 실행 코드
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(getBaseContext(), ProcessesActivity.class);
        intent.putExtra("imageFilePath", imageFilePath);
        startActivity(intent);
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();
        }
    };

    private void switchFragment(MenuItem item) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch ((item.getItemId())) {
            case R.id.bottomNavigationRecommendationMenuId: {
                fragmentTransaction.replace(R.id.fragment, f_recommendation).commitAllowingStateLoss();
                break;
            }

            case R.id.bottomNavigationMineMenuId: {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("List", adapterList);
                f_mine.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment, f_mine).commitAllowingStateLoss();
                break;
            }

            case R.id.bottomNavigationRefrigeratorMenuId: {
                fragmentTransaction.replace(R.id.fragment, f_refrigerator).commitAllowingStateLoss();
                break;
            }

            case R.id.bottomNavigationBoardMenuId: {
                fragmentTransaction.replace(R.id.fragment, f_board).commitAllowingStateLoss();
                break;
            }
        }
    }

    @Override
    public void ReceiverInterface(RecyclerItem_recipe item_recipe) {
        //Checked
        if(item_recipe.getCheckedOrNot()) {
            int recipe_id = item_recipe.getIdForFoodDrawable();
            boolean duplicationCheck = false;
            for(int i = 0; i < adapterList.size(); i++) {
                duplicationCheck = (adapterList.get(i).getIdForFoodDrawable() == recipe_id);
                if(duplicationCheck) break;
            }

            RecyclerItem_mine item = new RecyclerItem_mine();

            if(!duplicationCheck) {
                item.setIdForFoodDrawable(item_recipe.getIdForFoodDrawable());
                item.setTitle(item_recipe.getTitle());
                item.setHash(item_recipe.getHash());
                adapterList.add(item);
            }
        }
        //Not Checked
        else {
            int recipe_id = item_recipe.getIdForFoodDrawable();

            for(int i = 0; i < adapterList.size(); i++) {
                if(adapterList.get(i).getIdForFoodDrawable() == recipe_id) {
                    adapterList.remove(i);
                    break;
                }
            }
        }
    }

}