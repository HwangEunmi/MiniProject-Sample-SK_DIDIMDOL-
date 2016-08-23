package com.example.miniprojectx;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.miniprojectx.data.ChatContract;
import com.example.miniprojectx.data.User;
import com.example.miniprojectx.manager.DBManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView listView;

    ChatAdapter mAdapter;

    @BindView(R.id.group_type)
    RadioGroup typeView;

    @BindView(R.id.edit_input)
    EditText inputView;

    public static final String EXTRA_USER = "user";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        user = (User)getIntent().getSerializableExtra(EXTRA_USER);
        // User객체에다가 EXTRA_USER라는 키값으로 받아오는 값을 Serializable로
        // 객체 직렬화 시켜서 얻어옴(Intent로 넘겨줬던것)

        mAdapter = new ChatAdapter();
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btn_send)
    public void onSend(View view) {
        String message = inputView.getText().toString();
        // EditText로 쓴 값 message라는 String변수로 넣음

        int type = ChatContract.ChatMessage.TYPE_SEND;
        // Send라는 타입을 int형 변수에 넣음

        switch (typeView.getCheckedRadioButtonId()) {
            // RadioGroup에 체크된 버튼의 id값을 얻어와서 봤을 때

            case R.id.radio_send :
                // send이면
                type = ChatContract.ChatMessage.TYPE_SEND;
                // type 변수에 Send라는 타입을 셋팅함
                break;

            case R.id.radio_receive :
                // receive이면
                type = ChatContract.ChatMessage.TYPE_RECEIVE;
                // type변수에 Receive라는 타입을 셋팅함
                break;
        }
        DBManager.getInstance().addMessage(user, type, message);
        // User객체랑, type이랑 message값을 message에 추가하는 addMessage 함수를 호출(DB매니저에 있음)
        updateMessage();
        // 메시지를 업뎃시키는 함수 호출
    }

    private void updateMessage() {
        Cursor c = DBManager.getInstance().getChatMessage(user);
        // 해당 User객체의 값을 getChatMessage메소드로 얻어와서 커서에 대입
        mAdapter.changeCursor(c);
        // 해당 값으로 체인지
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMessage();
        // start메소드에서는
        // updateMessage메소드 호출(커서로 읽어들인 값으로 체인지하는거)
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.changeCursor(null);
    }
}
