package com.marco.amorim.gamelib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "GameLib.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_games";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GAME_TITLE = "game_title";
    private static final String COLUMN_GAME_STUDIO = "game_studio";
    private static final String COLUMN_GAME_STORE_LINK = "game_store_link";

    DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GAME_TITLE + " TEXT, " +
                COLUMN_GAME_STUDIO + " TEXT, " +
                COLUMN_GAME_STORE_LINK + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addGame(String gameTitle, String gameStudio, String gameStoreLink) {
        if (gameTitle.isEmpty() || gameStudio.isEmpty() || gameStoreLink.isEmpty()) {
            Toast.makeText(context, "Please, fill all the inputs!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!URLUtil.isValidUrl(gameStoreLink)) {
            Toast.makeText(context, "Please, insert a valid URL in the Store Link", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GAME_TITLE, gameTitle);
        cv.put(COLUMN_GAME_STUDIO, gameStudio);
        cv.put(COLUMN_GAME_STORE_LINK, gameStoreLink);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateGame(String rowId, String gameTitle, String gameStudio, String gameStoreLink) {
        if (gameTitle.isEmpty() || gameStudio.isEmpty() || gameStoreLink.isEmpty()) {
            Toast.makeText(context, "Please, fill all the inputs!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!URLUtil.isValidUrl(gameStoreLink)) {
            Toast.makeText(context, "Please, insert a valid URL in the Store Link", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GAME_TITLE, gameTitle);
        cv.put(COLUMN_GAME_STUDIO, gameStudio);
        cv.put(COLUMN_GAME_STORE_LINK, gameStoreLink);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{rowId});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
