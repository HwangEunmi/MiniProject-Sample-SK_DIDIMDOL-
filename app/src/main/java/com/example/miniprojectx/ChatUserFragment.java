package com.example.miniprojectx;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.miniprojectx.data.ChatContract;
import com.example.miniprojectx.data.User;
import com.example.miniprojectx.manager.DBManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends Fragment {


    public ChatUserFragment() {
    }

    @BindView(R.id.listView)
    ListView listView;


    SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] from = {ChatContract.ChatUser.COLUMN_NAME, ChatContract.ChatUser.COLUMN_EMAIL, ChatContract.ChatMessage.COLUMN_MESSAGE};
        // ChatUser의 Name, Email과 / ChatMessage의 MESSAGE를 셋팅
        // SimpleCursorAdapter (again)
        int[] to = {R.id.text_name, R.id.text_email, R.id.text_last_message};

        mAdapter = new SimpleCursorAdapter(getContext(), R.layout.view_chat_user, null, from, to, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_user, container, false);
        // 채팅 목록이 뜰 ListView
        ButterKnife.bind(this, view);

        listView.setAdapter(mAdapter);
        // 이 ListView에 SimpleCursorAdapter 셋팅(그래서 유저의 Name, Email 그리고 메시지를 사용하나 봄)
        return view;
    }


    @OnItemClick(R.id.listView)
    public void onItemClick(int position, long id) {
        // ListView를 클릭했을 때(각각의 아이템을)
        Cursor cursor = (Cursor)listView.getItemAtPosition(position);
        // Cursor를 이용해서 해당 아이템의 position을 얻어옴(그리고 Cursor에 저장)
        User user = new User();
        // ID, NAME, EMAIL이 있는 User의 객체를 생성
        user.setId(cursor.getLong(cursor.getColumnIndex(ChatContract.ChatUser.COLUMN_SERVER_ID)));
        // User의 ID에 Long값으로 셋팅(ID가 Long값이니까) 즉,Server_Id 행의 index값을 Cursor로 얻어와서 Long값으로 Cursor로 얻어와서
        // user 객체의 id에 셋팅
        // 그러니까 즉! DB의 행 위치를 커서로 얻어왔는데 user.setId했으니까, 그 위치에 값을 셋팅한 것임
        user.setEmail(cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.COLUMN_EMAIL)));
        user.setUserName(cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.COLUMN_NAME)));
        // Email과 UserName도 마찬가지

        Intent intent = new Intent(getContext(),ChatActivity.class);
        // Intent 객체 생성해서 (이곳과 ChatActivity를 연결하는) (ChatActivity 뜨게 하려고)
        intent.putExtra(ChatActivity.EXTRA_USER, user);
        // Intent로 ChatActivity의 "user"라는 키에 방금 셋팅한 user 내용을 셋팅해서 보냄
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Cursor c = DBManager.getInstance().getChatUser();
        // DB매니저의 객체를 이용해서 ChatUser의 Table에서의 값을 객체로 받아서 Cursor에 넣음
        // (DB에 저장되어있는 모든 유저의 값을 불러오는 것)
        mAdapter.changeCursor(c);
        // 어댑터에 바뀐 값을 셋팅
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.changeCursor(null);
    }
}
