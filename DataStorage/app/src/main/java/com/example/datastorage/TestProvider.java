package com.example.datastorage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class TestProvider extends ContentProvider {
    private UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBHelper dbhelper;

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, "TestDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE Passcode (_ID INTEGER PRIMARY KEY, passvalue TEXT)");
            Log.d("Test", "Create DB.");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch (urimatcher.match(uri)) {
            case 1:
                Log.d("Test", "Matched.");
                break;
        }
        Cursor result = dbhelper.getReadableDatabase().query("Passcode", strings, s, strings1, null, null, s1);
        return result;
    }

    @Override
    public boolean onCreate() {
        this.dbhelper = new DBHelper(this.getContext());
        urimatcher.addURI("com.example.datastorage.TestProvider", "Passcode", 1);

        return false;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long result = dbhelper.getWritableDatabase().insert("Passcode", "passvalue", contentValues);
        dbhelper.close();
        return ContentUris.withAppendedId(uri, result);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int result = dbhelper.getWritableDatabase().delete("Passcode", s, strings);
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
