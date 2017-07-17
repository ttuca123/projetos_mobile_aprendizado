package br.com.android.chat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tuca on 21/06/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String name_db="AppChat.db";
    private static final String version_db="1";

    public SQLiteHelper(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
