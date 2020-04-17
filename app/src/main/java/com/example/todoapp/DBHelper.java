package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_Name = "Tester";
    private static final int DB_Version = 1;
    private static final String DB_Table = "Task";
    private static final String DB_Column = "TaskName";

    DBHelper(Context context){
        super(context, DB_Name,null,DB_Version);   //Constructor creating database for us.
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);", DB_Table, DB_Column);
            db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = String.format("DELETE TABLE IF EXISTS %s",DB_Table);
        db.execSQL(query);
        onCreate(db);
    }

        void insertTask (String task){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_Column, task);
        db.insertWithOnConflict(DB_Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

    }
    void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_Table, DB_Column + "= ?", new String[]{task});
        db.close();
    }

    ArrayList<String> getTaskList(){

        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cursor = db.query(DB_Table, new String[]{DB_Column}, null, null, null, null, null );
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_Column);
            taskList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();
        return taskList;
    }
}
