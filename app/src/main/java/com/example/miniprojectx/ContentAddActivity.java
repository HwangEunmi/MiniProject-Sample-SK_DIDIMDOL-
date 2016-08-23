package com.example.miniprojectx;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.miniprojectx.data.ContentData;
import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.manager.NetworkManager;
import com.example.miniprojectx.manager.NetworkRequest;
import com.example.miniprojectx.request.UploadRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentAddActivity extends AppCompatActivity {

    @BindView(R.id.edit_message)
    EditText messageView;
    @BindView(R.id.image_picture)
    ImageView pictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add);
        ButterKnife.bind(this);
    }

    private static final int RC_GET_IMAGE = 1;

    @OnClick(R.id.btn_get_image)
    public void onGetImageClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Intent.ACTION_PICK은 데이터베이스에서 하나의 항목을 선택 할 수 있는 것.
        // 그러므로 외부 URI 이니까, SD카드에 있는 이미지를 가지고 오는 것
        intent.setType("image/*");
        // 이미지의 타입은 전체 다
        startActivityForResult(intent, RC_GET_IMAGE);
    }

    File uploadFile = null;
    // File은 논리적인 연속적인 데이터의 묶음임
    // 실제적으로는 떨어져 있지만 묶음이라고 표현함

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();

                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                // ContentProvider로 제공되는 자료는 ContentResolver로 접근 가능(이미지니까)

                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    // 이미지 파일의 PATH를 만듬 (DATA는 이미지 DATA를 말함(again))
                    uploadFile = new File(path);
                    // 파일 생성
                    Glide.with(this)
                            // 이미지 다루는 Glide
                            .load(uploadFile)
                            .into(pictureView);
                            // ImageView에 넣음(파일을)
                }
            }
        }
    }

    @OnClick(R.id.btn_upload)
    public void onUpload() {
        String content = messageView.getText().toString();
        if (!TextUtils.isEmpty(content) && uploadFile != null) {
            UploadRequest request = new UploadRequest(this, content, uploadFile);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<ContentData>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<ContentData>> request, NetworkResult<ContentData> result) {
                    Toast.makeText(ContentAddActivity.this, "success", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<ContentData>> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(ContentAddActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
}
