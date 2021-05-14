package com.sharkit.nextmonday.MySQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "target" ; // название таблицы в бд
    private static String DB_PATH;
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT_TARGET = "text_target";
    public static final String COLUMN_COMPLETE = "complete";
    public static final String COLUMN_REPEAT_SUNDAY = "sunday";
    public static final String COLUMN_REPEAT_SATURDAY = "saturday";
    public static final String COLUMN_REPEAT_FRIDAY = "friday";
    public static final String COLUMN_REPEAT_THURSDAY = "thursday";
    public static final String COLUMN_REPEAT_WEDNESDAY = "wednesday";
    public static final String COLUMN_REPEAT_TUESDAY = "tuesday";
    public static final String COLUMN_REPEAT_MONDAY = "monday";
    public static final String COLUMN_REPEAT = "repeat";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_YEAR= "year";
    public static final String COLUMN_TIME= "time";

    private Context myContext;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( "+COLUMN_ID+" TEXT,"+COLUMN_TEXT_TARGET+" TEXT , "+COLUMN_COMPLETE+" INTEGER," +
                ""+COLUMN_REPEAT_SUNDAY+" INTEGER, "+COLUMN_REPEAT_SATURDAY+" INTEGER," +
                ""+COLUMN_REPEAT_FRIDAY+" INTEGER, "+COLUMN_REPEAT_THURSDAY+" INTEGER, " +
                ""+COLUMN_REPEAT_WEDNESDAY+" INTEGER, "+COLUMN_REPEAT_TUESDAY+" INTEGER," +
                ""+COLUMN_REPEAT_MONDAY+" INTEGER, "+COLUMN_REPEAT+" INTEGER, "+COLUMN_MINUTE+" INTEGER, " +
                ""+COLUMN_HOUR+" INTEGER, "+COLUMN_MONTH+" INTEGER, "+COLUMN_DAY+" INTEGER, "+COLUMN_YEAR+" INTEGER,"+COLUMN_TIME+" INTEGER UNIQUE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

}
