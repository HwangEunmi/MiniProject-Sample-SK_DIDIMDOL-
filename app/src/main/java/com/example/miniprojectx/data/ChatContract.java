package com.example.miniprojectx.data;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ChatContract {
    public interface ChatUser extends BaseColumns {
        public static final String TABLE = "chatuser";
        public static final String COLUMN_SERVER_ID = "sid";
        public static final String COLUMN_NAME = "name";
        // User의 Name
        public static final String COLUMN_EMAIL = "email";
        // User의 Email
        public static final String COLUMN_LAST_MESSAGE_ID = "lastid";
        // 가장 최근의 메시지를 오름차순으로 목록을 띄우려고 만든 애(COLUMN_LAST_MESSAGE_ID)
    }

    public interface ChatMessage extends BaseColumns {
        public static final int TYPE_SEND = 0;
        public static final int TYPE_RECEIVE = 1;
        public static final int TYPE_DATE = 2;
        // SEND인지 RECEIVE인지 알 수 있도록 타입을 정의한 애(TYPE_SEND / TYPE_RECEIVE)

        public static final String TABLE = "chatmessage";
        public static final String COLUMN_USER_ID = "uid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MESSAGE = "message";
        // 채팅 Message
        public static final String COLUMN_CREATED = "created";
    }
}
