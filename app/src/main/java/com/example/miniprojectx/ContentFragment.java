package com.example.miniprojectx;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.miniprojectx.data.ContentData;
import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.manager.NetworkManager;
import com.example.miniprojectx.manager.NetworkRequest;
import com.example.miniprojectx.request.ContentListRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {


    public ContentFragment() {

    }


    @BindView(R.id.rv_list)
    RecyclerView listView;

    ContentAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ContentAdapter();
        setHasOptionsMenu(true);
        // 이 명령어로 인해 액티비티보다 프레그먼트의 메뉴가 더 우선시 됨

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_content, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_content_add) {
            Intent intent = new Intent(getContext(), ContentAddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        // 시작하면
        super.onStart();

        ContentListRequest request = new ContentListRequest(getContext());
        // ContentListRequest 받아와서
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<ContentData>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<ContentData>>> request, NetworkResult<List<ContentData>> result) {
                // 성공하면
                List<ContentData> list = result.getResult();
               // List<ContentData>의 값들을 list라는 변수에 넣고

                mAdapter.clear();
                mAdapter.addAll(list);
                // 어댑터에 추가
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<ContentData>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "network fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
