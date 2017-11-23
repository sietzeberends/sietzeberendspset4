package com.example.user4.sietzeberendspset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User4 on 11/20/2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    private static TodoDatabase instance;

    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create a table that holds todoitems and insert some values
        db.execSQL("create table todos(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, completed INTEGER)");
        db.execSQL("insert into todos(title, completed) values('item_one', 0), ('item_two', 0), ('item_three', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(db);
    }

    // return the instance. If the instance doesn't exist, create it
    public static TodoDatabase getInstance(Context context){
        if(instance != null) {
            return instance;
        }

        else{
            String name = "DB_NAME";
            int version = 1;
            instance = new TodoDatabase(context, name, null, version);
            return instance;
        }
    }

    // return all rows
    public Cursor selectAll() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM todos", null);
        return cursor;
    }

    // insert a new row
    public void insert(String title, int completed) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("completed", completed);
        database.insert("todos", null, values);
    }

    // update a row
    public void update(long id, int completed) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println(completed);
        values.put("completed", completed);
        database.update("todos", values, "_id =" + id, null);
    }

    // delete a row
    public void delete(long id){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        database.delete("todos","_id =" + id, null);
    }
}
