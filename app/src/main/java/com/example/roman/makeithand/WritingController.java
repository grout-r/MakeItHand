package com.example.roman.makeithand;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Data controller for Writing.
 * Contains the database instance.
 */
class WritingController
{
    /**
     * Call the database
     * @param context
     *          Activity which it is launched.
     */
    WritingController(Context context)
    {
        MyDBOpenHelper tdb = new MyDBOpenHelper(context, "prod.db", null, 1);
        sdb = tdb.getWritableDatabase();
    }

    /**
     * Add a new Writing in the database.
     * @param title
     *          Writing title.
     * @param value
     *          Writing value.
     * @param path
     *          Image path.
     * @return
     *          Return true.
     */
    boolean addWriting(String title, String value, String path)
    {
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("VALUE", value);
        cv.put("IMAGE_PATH", path);
        sdb.insert("writings", null, cv);
        return true;
    }

    /**
     * Return all the writings to display them on the list view on Main Activity
     * @return
     *      An ArrayList with all the Writing instances.
     */
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

    /**
     * Delete a writing based on his ID.
     * @param id
     *          ID to delete.
     */
    void deleteWriting(int id)
    {
        String table_name = "writings";
        String where_clause = "ID = ?";
        String []where_args = {String.valueOf(id)};
        sdb.delete(table_name, where_clause, where_args);
    }

    private SQLiteDatabase sdb;
}
