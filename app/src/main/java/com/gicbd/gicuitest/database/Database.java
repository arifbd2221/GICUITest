package com.gicbd.gicuitest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gicbd.gicuitest.model.User;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase db;

    private static final String DBNAME = "gic_user_database";
    private static final int DB_Version=1;

    private static final String TABLE_NAME = "ap_Rec";
    private static final String ID = "_id";
    private static final String USER_NAME = "Name";
    private static final String USER_EMAIL = "E_mail";
    private static final String USER_PHONE = "Phone_Number";
    private static final String USER_PASSWORD = "Password";


    private static final String createTable = "create table "+TABLE_NAME+" " +
            "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_NAME+" varchar(50), "
            +USER_EMAIL+" varchar(50), "+USER_PHONE+" number(13), "+USER_PASSWORD+" varchar(50) );";

    private static final String dropTable = "drop table if exists "+TABLE_NAME;


    public Database(Context context) {
        super(context, DBNAME,null, DB_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL(dropTable);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public long insertUser(User user){
        db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_NAME,user.getName());
        values.put(USER_EMAIL,user.getEmail());
        values.put(USER_PHONE,user.getPhone());
        values.put(USER_PASSWORD,user.getPassword());

       long num = db.insert(TABLE_NAME,null,values);

       db.close();

       return num;
    }


    public String getUserPasswordByName(String name){

        db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,new String[]{USER_PASSWORD},USER_NAME+" =? ",new String[]{name},null,null,null);

        for (cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
        }

        return null;

    }


}
