package com.example.miniprojectx;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.User;
import com.example.miniprojectx.login.SimpleLoginActivity;
import com.example.miniprojectx.manager.NetworkManager;
import com.example.miniprojectx.manager.NetworkRequest;
import com.example.miniprojectx.manager.PropertyManager;
import com.example.miniprojectx.request.LoginRequest;
import com.example.miniprojectx.request.ProfileRequest;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProfileRequest request = new ProfileRequest(this);
        // ProfileRequest의 request의 객체 만듬

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                moveMainActivity();
                // 성공하면 MainActivity로 가고
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                if (errorCode == -1) {
                    if (errorMessage.equals("not login")) {
                        loginSharedPreference();
                        return;
                    }
                }
                moveLoginActivity();
                // 실패하면 Login창으로 가기
            }
        });
    }

    private void loginSharedPreference() {
        if (isAutoLogin()) {
            processAutoLogin();
        } else {
            moveLoginActivity();
        }
    }
    private boolean isAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        return !TextUtils.isEmpty(email);
    }
    private void processAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        if (!TextUtils.isEmpty(email)) {
            String password = PropertyManager.getInstance().getPassword();
            String regid = PropertyManager.getInstance().getRegistrationId();
            LoginRequest request = new LoginRequest(this, email, password, regid);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                    moveMainActivity();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                    moveLoginActivity();
                }
            });
        }
    }

    private void moveMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void moveLoginActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SimpleLoginActivity.class));
                finish();
            }
        }, 1000);
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
}
