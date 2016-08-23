package com.example.miniprojectx.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.miniprojectx.MyApplication;
import com.example.miniprojectx.data.ChatContract;
import com.example.miniprojectx.data.User;

import java.util.HashMap;
import java.util.Map;
// 주소록, 위치 등 개인의 정보와 관련된 것들은 모두 런타임퍼미션을 받아야 함(쓸 곳이 없어서 이 곳에 씀)

/**
 * Created by Administrator on 2016-08-11.
 */
public class DBManager extends SQLiteOpenHelper {
    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }// DB매니저의 객체 생성

    private static final String DB_NAME = "chat_db";
    // DB이름과
    private static final int DB_VERSION = 1;
    // DB의 version 초기화

    private DBManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
    }
    // DB매니저 생성자에 DB이름이랑 version이랑 (아직 Cursor는 null(이거 맞는지 again) context를 셋팅)

    @Override
    public void onCreate(SQLiteDatabase db) {
    // 테이블 생성
        String sql = "CREATE TABLE " + ChatContract.ChatUser.TABLE + "(" +
                // ChatUser의 테이블을 생성
                ChatContract.ChatUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatUser.COLUMN_SERVER_ID + " INTEGER," +
                ChatContract.ChatUser.COLUMN_NAME + " TEXT," +
                ChatContract.ChatUser.COLUMN_EMAIL + " TEXT NOT NULL," +
                ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE " + ChatContract.ChatMessage.TABLE + "(" +
                // ChatMessage의 테이블을 생성
                ChatContract.ChatMessage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatMessage.COLUMN_USER_ID + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_TYPE + " INTEGER," +
                ChatContract.ChatMessage.COLUMN_MESSAGE + " TEXT," +
                ChatContract.ChatMessage.COLUMN_CREATED + " INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long getUserId(long serverId) {
        String selection = ChatContract.ChatUser.COLUMN_SERVER_ID + " = ?";
        // ChatUser 테이블의 sid의 column 내용과 같다면? 를 (조건문) String 변수에 넣음
        String[] args = {""+serverId};
        // serverId값을 받은 것을 String배열에 넣음
        String[] columns = {ChatContract.ChatUser._ID};
        // 인터페이스 BaseColumn에 원래 있는 변수 id를 (ChatUser테이블의) 받아와서 String배열에 넣음
        SQLiteDatabase db = getReadableDatabase();
        // 읽는 db를 가지고 옴(again)
        Cursor c = db.query(ChatContract.ChatUser.TABLE, columns, selection, args, null, null, null);
        // ChatUser의 테이블과 저 위에 String배열을 넣고(columns), 또 저 위에 String배열을 넣고(args)
        // 조건문인 selection도 넣어서 검색하는 것을 커서에 넣음

        try {
            if (c.moveToNext()) {
                // 계속 넘어가면서
                long id = c.getLong(c.getColumnIndex(ChatContract.ChatUser._ID));
                // ChatUser테이블의 _ID Column의 index들을 long값으로 얻어와서 long변수에 넣고
                // 이 try부근 (again)
                return id;
                // 값 리턴
            }

        } finally {
            c.close();
        }
        return -1;
    }


    // DB에 자료 입력하기 : ContentValues  <-> DB 데이터를  입력 : Cursor
    ContentValues values = new ContentValues();
    // DB에 자료 넣으려고 ContentVlues 객체 생성

    public long addUser(User user) {
        // User 객체를 추가하는 메소드
        if (getUserId(user.getId()) == -1) {
            // UserId가 있을 때
            SQLiteDatabase db = getWritableDatabase();
            // 쓰는용 db 생성

            values.clear();
            // 깨끗하게 하고
            values.put(ChatContract.ChatUser.COLUMN_SERVER_ID, user.getId());
            values.put(ChatContract.ChatUser.COLUMN_NAME, user.getUserName());
            values.put(ChatContract.ChatUser.COLUMN_EMAIL, user.getEmail());
            // User객체로 받은 id, name, email을 DB의 ID, NAME, EMAIL에 넣어서 ContentVlues에 넣기(붙이기)
            return db.insert(ChatContract.ChatUser.TABLE, null, values);
            // ChatUser의 테이블과 방금 넣은 값 즉, ContentView를 (Cursor는 null) 을 DB에 Insert시킴
        }
        throw new IllegalArgumentException("aleady user added");
    }


    Map<Long, Long> resolveUserId = new HashMap<>();
    // 왜 맵을 쓰는거지????(key, value 이런 형태로 넣으려고 그러는건가??) (again)

    public long addMessage(User user, int type, String message) {
        Long uid = resolveUserId.get(user.getId());
        // User의 id를 얻은 값을 저 Map으로 얻어서 Long에 셋팅(again) (Long이 뭐임?!)

        if (uid == null) {
            long id = getUserId(user.getId());
            // getUserId메소드로 User객체의 ID값을 얻어서 long형 변수에 셋팅

            if (id == -1) {
                // id가 없으면
                id = addUser(user);
                // id에 addUser메소드를 이용해서 User객체를 추가함
            }
            resolveUserId.put(user.getId(), id);

            uid = id;
        }
        // 그냥 이 메소드 다시


        SQLiteDatabase db = getWritableDatabase();
        // 쓰기용 DB를 얻어옴

        values.clear();
        // 꺠끗하게

        values.put(ChatContract.ChatMessage.COLUMN_USER_ID, (long)uid);
        values.put(ChatContract.ChatMessage.COLUMN_TYPE, type);
        values.put(ChatContract.ChatMessage.COLUMN_MESSAGE, message);

        long current = System.currentTimeMillis();
        // 현재 시간을 얻어옴(long형 변수에)

        values.put(ChatContract.ChatMessage.COLUMN_CREATED, current);
        //  현재 시간을 ChatMessage테이블의 COLUMN_CREATED에 넣음(만들어진 시간 말하나 봄)

        try {
            db.beginTransaction();
            // DB의 연산 시작
            long mid = db.insert(ChatContract.ChatMessage.TABLE, null, values);
            // ContentValues값이랑 (Cursor는 null) ChatMessage테이블을 DB에 Insert(again)

            values.clear();
            // 깨끗하게

            values.put(ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID, mid);
            //
            String selection = ChatContract.ChatUser._ID + " = ?";
            String[] args = {"" + uid};

            db.update(ChatContract.ChatUser.TABLE, values, selection, args);

            db.setTransactionSuccessful();
            return mid;
        } finally {
            db.endTransaction();
        }
    }

    // (again)


    public Cursor getChatUser() {
        // ChatUser의 값을 가져옴 (나중에 ChatUserFragment에서 Cursor로 이 메소드에 접근할 것임)

        String table = ChatContract.ChatUser.TABLE + " INNER JOIN " +
                // INNER JOIN은 두 테이블의 Column 중 일치하는 것만 찾아서 결합하여 새로운 하나의 Table을 생성하는것
                ChatContract.ChatMessage.TABLE + " ON " +
                // ChatUser의 테이블과 ChatMessage의 테이블을 엮음, (ON은 조건을 말하는 것)
                ChatContract.ChatUser.TABLE + "." + ChatContract.ChatUser.COLUMN_LAST_MESSAGE_ID + " = " +
                // ChatUser의 테이블의 LAST_MESSAGE_ID와
                ChatContract.ChatMessage.TABLE + "." + ChatContract.ChatMessage._ID;
                // ChatMessage의 테이블의 ID가 같을 때만 찾아서 결합해서 하나의 Table 생성

        String[] columns = {ChatContract.ChatUser.TABLE + "." + ChatContract.ChatUser._ID,
                    // ChatUser의 테이블에서의 ChatUser의 ID와(ChatUser 테이블에 이 column값들을 넣는거야??) (again)
                ChatContract.ChatUser.COLUMN_SERVER_ID,
                ChatContract.ChatUser.COLUMN_EMAIL,
                ChatContract.ChatUser.COLUMN_NAME,
                ChatContract.ChatMessage.COLUMN_MESSAGE

        };

        String sort = ChatContract.ChatUser.COLUMN_NAME + " COLLATE LOCALIZED ASC";
        // ChatUser의 Name들을 오름차순으로 정렬하여 String변수에 넣음
        SQLiteDatabase db = getReadableDatabase();
        // 읽는 DB를 얻어옴

        return db.query(table, columns, null, null, null, null, sort);
        // DB 검색하기
    }

    public Cursor getChatMessage(User user) {
        // (ChatActivity에서 사용 될 메소드) 채팅 메시지 내용 가져오기

        long userid = -1;
        // User ID를 초기화
        Long uid = resolveUserId.get(user.getId());
        // (again) 위에꺼랑 똑같음

        if (uid == null) {
            long id = getUserId(user.getId());
            if (id != -1) {
                resolveUserId.put(user.getId(), id);
                userid = id;
            }
            // (again) 이것도 위에꺼랑 똑같음
        } else {
            userid = uid;
        }

        String[] columns = {ChatContract.ChatMessage._ID,
                ChatContract.ChatMessage.COLUMN_TYPE,
                ChatContract.ChatMessage.COLUMN_MESSAGE,
                ChatContract.ChatMessage.COLUMN_CREATED};
        // String배열에 ChatMessage의 ID, TYPE, MESSAGE, CREATED를 넣음

        String selection = ChatContract.ChatMessage.COLUMN_USER_ID + " = ?";
        // ChatMessage의 USER_ID와 같은 것을 찾는 조건문

        String[] args = {"" + userid};
        // User객체의 id에서 얻어온 값(User의 id)을 String배열에 넣음

        String sort = ChatContract.ChatMessage.COLUMN_CREATED + " ASC";
        // (again)!!          ASC랑 COLLATE LOCALIZED ASC의 차이는?!

        SQLiteDatabase db = getReadableDatabase();
        // 읽는 DB 얻어오기(얘 Transaction이야??)(again)

        return db.query(ChatContract.ChatMessage.TABLE, columns, selection, args, null, null, sort);
        // ChatMessage 테이블에서 각각의 속성으로 DB를 검색한 결과를 반환
    }

}
