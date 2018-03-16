package com.maxfarman.www.bdsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by et_c113 on 16/03/2018.
 */

public class AsistenteSQLite extends SQLiteOpenHelper {
    String cadenaSql="CREATE TABLE alumnos(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(100),dni VARCHAR(8),telefono VARCHAR(50))";

    public AsistenteSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(cadenaSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS alumnos");
    sqLiteDatabase.execSQL(cadenaSql);

    }
}
