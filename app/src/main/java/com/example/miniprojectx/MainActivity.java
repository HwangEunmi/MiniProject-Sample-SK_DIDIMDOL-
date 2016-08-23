package com.example.miniprojectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.login.SimpleLoginActivity;
import com.example.miniprojectx.manager.NetworkManager;
import com.example.miniprojectx.manager.NetworkRequest;
import com.example.miniprojectx.manager.PropertyManager;
import com.example.miniprojectx.request.LogOutRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // ListView listView;
    // ArrayAdapter<UserResult> mAdapter;

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //tabHost.addTab(tabHost.newTabSpec("capture").setIndicator("Capture"), CaptureImageFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("main").setIndicator("Main"), MainFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("chat").setIndicator("Chat"), ChatUserFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("content").setIndicator("Content"), ContentFragment.class, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            LogOutRequest request = new LogOutRequest(this);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<String>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<String>> request, NetworkResult<String> result) {
                    PropertyManager.getInstance().setEmail("");
                    PropertyManager.getInstance().setPassword("");
                    Intent intent = new Intent(MainActivity.this, SimpleLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    // Activity는 Task위에서 구동됨
                    // 여기는 지금 로그아웃하고 나서 다시 로그인 창 떠야 되는 곳이니까
                    // 지금까지 쌓였던 액티비티들 다 지워버리고 새로운 액티비티 창 하나 뜨게 해주는 것임
                    // 저 Intent.addFlags저거
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<String>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
