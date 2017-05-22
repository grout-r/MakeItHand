package com.example.roman.makeithand;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

class WritingController
{
    WritingController(Context context)
    {
        MyDBOpenHelper tdb = new MyDBOpenHelper(context, "prod.db", null, 1);
        sdb = tdb.getWritableDatabase();
    }

    boolean addWriting(String title, String value, String path)
    {
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("VALUE", value);
        cv.put("IMAGE_PATH", path);
        sdb.insert("writings", null, cv);
        return true;
    }


    ArrayList<Writing> getAllWritings()
    {
        ArrayList<Writing> retVal = new ArrayList<>();
        String table_name = "writings";
        String[] columns = {"ID", "TITLE", "VALUE", "IMAGE_PATH"};
        Cursor c = sdb.query(table_name, columns, null, null, null, null, null);
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            retVal.add(new Writing(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
            c.moveToNext();
        }
        c.close();
        return retVal;
    }

    void deleteWriting(int id)
    {
        String table_name = "writings";
        String where_clause = "ID = ?";
        String []where_args = {String.valueOf(id)};
        sdb.delete(table_name, where_clause, where_args);
    }

    private SQLiteDatabase sdb;
}
