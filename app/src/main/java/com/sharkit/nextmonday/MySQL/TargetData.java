package com.sharkit.nextmonday.MySQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Week;

public class TargetData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "my_target" ; // название таблицы в бд
    private static String DB_PATH;
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT_TARGET = "text_target";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_REPEAT_SUNDAY = "sunday";
    public static final String COLUMN_REPEAT_SATURDAY = "saturday";
    public static final String COLUMN_REPEAT_FRIDAY = "friday";
    public static final String COLUMN_REPEAT_THURSDAY = "thursday";
    public static final String COLUMN_REPEAT_WEDNESDAY = "wednesday";
    public static final String COLUMN_REPEAT_TUESDAY = "tuesday";
    public static final String COLUMN_REPEAT_MONDAY = "monday";
    public static final String COLUMN_REPEAT = "repeat";
    public static final String COLUMN_ALARM= "time_alarm";

    private Context myContext;


    public TargetData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( "+COLUMN_ID+" TEXT,"+COLUMN_TEXT_TARGET+" TEXT , "+COLUMN_STATUS+" BOOL," +
                ""+COLUMN_REPEAT_SUNDAY+" BOOL, "+COLUMN_REPEAT_SATURDAY+" BOOL," +
                ""+COLUMN_REPEAT_FRIDAY+" BOOL, "+COLUMN_REPEAT_THURSDAY+" BOOL, " +
                ""+COLUMN_REPEAT_WEDNESDAY+" BOOL, "+COLUMN_REPEAT_TUESDAY+" BOOL," +
                ""+COLUMN_REPEAT_MONDAY+" BOOL, "+COLUMN_REPEAT+" TEXT, " +
                ""+COLUMN_DESCRIPTION+" TEXT, " + COLUMN_ALARM+" TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void addNewTarget(MyTarget target, SQLiteDatabase db, String id){
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id + "','" +
                target.getName() + "','" +
                target.isStatus() + "','" +
                target.isRepeat_sunday() + "','" +
                target.isRepeat_saturday() + "','" +
                target.isRepeat_friday() + "','" +
                target.isRepeat_thursday() + "','" +
                target.isRepeat_wednesday()+ "','" +
                target.isRepeat_tuesday() + "','" +
                target.isRepeat_monday() + "','" +
                target.getRepeat() + "','" +
                target.getDescription() + "','" +
                target.getTime_alarm() + "');");

    }

}
