package com.sharkit.nextmonday.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class TargetData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "my_target"; // название таблицы в бд
    // названия столбцов
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT_TARGET = "text_target";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_REPEAT_SUNDAY = "sunday";
    private static final String COLUMN_REPEAT_SATURDAY = "saturday";
    private static final String COLUMN_REPEAT_FRIDAY = "friday";
    private static final String COLUMN_REPEAT_THURSDAY = "thursday";
    private static final String COLUMN_REPEAT_WEDNESDAY = "wednesday";
    private static final String COLUMN_REPEAT_TUESDAY = "tuesday";
    private static final String COLUMN_REPEAT_MONDAY = "monday";
    private static final String COLUMN_REPEAT = "repeat";
    private static final String COLUMN_ALARM = "time_alarm";


    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

    public TargetData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        String DB_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( " + COLUMN_ID + " TEXT," + COLUMN_TEXT_TARGET + " TEXT , " + COLUMN_STATUS + " BOOL," +
                "" + COLUMN_REPEAT_SUNDAY + " BOOL, " + COLUMN_REPEAT_SATURDAY + " BOOL," +
                "" + COLUMN_REPEAT_FRIDAY + " BOOL, " + COLUMN_REPEAT_THURSDAY + " BOOL, " +
                "" + COLUMN_REPEAT_WEDNESDAY + " BOOL, " + COLUMN_REPEAT_TUESDAY + " BOOL," +
                "" + COLUMN_REPEAT_MONDAY + " BOOL, " + COLUMN_REPEAT + " TEXT, " +
                "" + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_ALARM + " INTEGER UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }


}
