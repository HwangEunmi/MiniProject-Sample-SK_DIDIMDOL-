package com.example.miniprojectx;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miniprojectx.data.ChatContract;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Cursor cursor;
    // 커서 선언(DB때문에)

    public void changeCursor(Cursor c) {
        // 커서 change하는 메소드
        if (cursor != null) {
            cursor.close();
            // 일단 커서 닫고
        }
        cursor = c;
        // 새로운 커서를 커서에 셋팅
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_SEND = 1;
    // Send의 변수
    private static final int VIEW_TYPE_RECEIVE = 2;
    // Receive의 변수

    @Override
    public int getItemViewType(int position) {
         // Receive인지 Send인지에 따라 달라지니까,
        // 둘 중 어떤 것이 들어왔는지!!!!!
        cursor.moveToPosition(position);
        // 커서를 해당 position(위치)로 옮김
        int type = cursor.getInt(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_TYPE));
        // Receive와 Send를 구별하기 위한 변수인 type을 설정함
        // COLUMN_TYPE의 행렬 index값을 CURSOR로 int형으로 받아와서 변수에 셋팅(Receive 아니면 Send임) (again)
        switch (type) {
            case ChatContract.ChatMessage.TYPE_SEND :
                // Send이면
                return VIEW_TYPE_SEND;

            case ChatContract.ChatMessage.TYPE_RECEIVE :
                // Receive이면
                return VIEW_TYPE_RECEIVE;
        }
        throw new IllegalArgumentException("invalid type");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SEND : {
                // Send이면
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_send, parent, false);
                return new SendViewHolder(view);
            }
            case VIEW_TYPE_RECEIVE : {
                // Receive이면
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_receive, parent, false);
                return new ReceiveViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        // 커서를 해당 위치로 옮기고
        switch (holder.getItemViewType()) {
            // 그 뷰의 type을 받아와서
            case VIEW_TYPE_SEND : {
                // send이면
                SendViewHolder svh = (SendViewHolder)holder;
                // Send의 뷰홀더 셋팅
                String message = cursor.getString(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_MESSAGE));
                // DB내에서 메시지의 행 위치값을 커서로 얻어와서 변수인 message에 셋팅함
                svh.setMessage(message);
                // Send의 뷰 홀더에 변수 message값을 셋팅함(setMessage)
                break;
            }
            case VIEW_TYPE_RECEIVE :{
                // Receive일때도 마찬가지임
                ReceiveViewHolder rvh = (ReceiveViewHolder)holder;
                String message = cursor.getString(cursor.getColumnIndex(ChatContract.ChatMessage.COLUMN_MESSAGE));
                rvh.setMessage(message);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        // 커서가 없을 때 0을 리턴함
        return cursor.getCount();
        // 커서로 얻은 갯수들을 return
    }
}
