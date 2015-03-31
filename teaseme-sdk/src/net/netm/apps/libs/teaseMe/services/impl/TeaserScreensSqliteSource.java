package net.netm.apps.libs.teaseMe.services.impl;

import java.util.ArrayList;
import java.util.List;

import net.netm.apps.libs.teaseMe.utils.MySQLiteHelper;
import net.netm.apps.libs.teaseMe.utils.Utils;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ahoban on 29.03.15.
 */
public class TeaserScreensSqliteSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;


    public TeaserScreensSqliteSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        open();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_SCREEN};


    public void close() {
        dbHelper.close();
    }

    public String getScreen(Integer screenId) {

        if (!database.isOpen())
            open();

        try {
            Cursor cursor = database.rawQuery("select " + MySQLiteHelper.COLUMN_SCREEN + " from " + MySQLiteHelper.TABLE_SCREENS + " where " + MySQLiteHelper.COLUMN_ID + "=" + screenId, null);
            cursor.moveToFirst();
            return cursorToScreen(cursor);
        }
        catch (Exception e) {
            return null;
        }



    }

    public String createScreen(JSONObject screen, Long id) {

        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_SCREEN, screen.toString());

        values.put(MySQLiteHelper.COLUMN_ID, Utils.safeLongToInt(id));

        long insertId = database.insert(MySQLiteHelper.TABLE_SCREENS, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCREENS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        String newScreen = cursorToScreen(cursor);

        cursor.close();

        return newScreen;
    }

    public void deleteScreen(Long screenId) {

        if (!database.isOpen())
            open();

        database.delete(MySQLiteHelper.TABLE_SCREENS, MySQLiteHelper.COLUMN_ID + " = " + Utils.safeLongToInt(screenId), null);
    }

    private String cursorToScreen(Cursor cursor) {

        String screen;

        try {
            return cursor.getString(0);
        } catch (CursorIndexOutOfBoundsException e) {
            return null;
        }

    }

    public List<String> getAllScreens() {

        List<String> screens = new ArrayList<String>();

        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCREENS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String screen = cursorToScreen(cursor);
            screens.add(screen);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return screens;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

}
