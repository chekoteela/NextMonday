package com.sharkit.nextmonday.MySQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyWeight extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "my_weight" ; // название таблицы в бд
    private static String DB_PATH;
//    _________________

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE_MILLIS = "date_millis";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_CHANGE = "change";
    public static final String COLUMN_DATE = "date";

    private Context mContext;

    public MyWeight(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.mContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( " + COLUMN_ID + " TEXT, " + COLUMN_DATE_MILLIS +
                " TEXT UNIQUE , " + COLUMN_WEIGHT + " INTEGER, " + COLUMN_CHANGE + " TEXT, " + COLUMN_DATE + " TEXT UNIQUE )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
