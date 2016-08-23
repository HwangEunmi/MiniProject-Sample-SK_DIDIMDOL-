package com.example.miniprojectx;//package com.begentgroup.miniproject;
//
//import android.app.Activity;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//
//import java.io.File;
//
//import butterknife.BindView;
//
//
//public class CaptureImageFragment extends Fragment {
//
//    @BindView(R.id.capture_image)
//    ImageView imageView;
//    @BindView(R.id.btn_capture_image)
//    Button button;
//
//    public CaptureImageFragment() {
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // 인텐트에 캡쳐 기능의 액션을 부여하고
//                 Uri uri = getSaveFile();
//                // 경로 즉, uri에는 getSaveFile메소드에서 얻어옴
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                //EXTRA_OUTPUT은 결과를 받을 수 있는 액티비티를 실행한다는 뜻
//                startActivityForResult(intent, RC_CAMERA);
//            }
//        });
//
//        if(savedInstanceState != null) {
//            String path = savedInstanceState.getString("savedfile");
//            // (again)
//            if(!TextUtils.isEmpty(path)) {
//                // path, 즉 경로가 있으면
//                mSavedFile = new File(path);
//                // mSavedFile이라는 File을 생성
//            }
//
//            path = savedInstanceState.getString("contentfile");
//            if (!TextUtils.isEmpty(path)){
//                mContentFile = new File(path);
//
//                Glide.with(this)
//                        .load(mContentFile)
//                        .into(imageView);
//
//            }
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_capture_image, null);
//        return view;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RC_CAMERA) {
//            if(requestCode == Activity.RESULT_OK) {
//                mContentFile = mSavedFile;
//
//                Glide.with(this)
//                        .load(mContentFile)
//                        .into(imageView);
//            }
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mSavedFile != null) {
//            // mSavedFile이 존재하면
//            outState.putString("savedfile", mSavedFile.getAbsolutePath());
//            // "savedfile"은 키이고, getAbsolutePath()로 mSavedFile의 절대경로를 put함 (again)
//        }
//    }
//
//    private static final int RC_CAMERA = 1;
//    // 코드 생성
//    File mSavedFile, mContentFile;
//
//    public Uri getSaveFile() {
//        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "my_image");
//        // ENVIRONMENT.directory_pictures는 type
//        // Environment.getExternalStoragePublicDirectory(type)메소드를 이용하면 외부 저장소인 SD카드의 경로와 type을 지정할 수 있음
//        // 메소드 인자인 type은 SD카드에 자동으로 생성된 저장 폴더 경로를 의미하는 상수값임
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        // directory가 존재하지 않으면 directory를 만들기
//        mSavedFile = new File(dir, "my_picture_" + System.currentTimeMillis() + ".jpg");
//        //  (again)
//        return Uri.fromFile(mSavedFile);
//        // (again)
//    }
//}
