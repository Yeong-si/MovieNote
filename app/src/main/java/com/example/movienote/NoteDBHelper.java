package com.example.movienote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    NoteDBHelper(Context context){
        super(context, "memodb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String memoSQL = "create table tb_memo"+
                "(_id integer primary key autoincrement," +
                "title, " +
                "poster)";
        db.execSQL(memoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

        // ...

        public void deleteData(String tableName, String conditionColumn, String conditionValue) {
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = conditionColumn + " = ?";
            String[] whereArgs = {conditionValue};
            db.delete(tableName, whereClause, whereArgs);
            db.close();
        }

}
