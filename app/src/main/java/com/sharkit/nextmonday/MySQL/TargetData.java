package com.sharkit.nextmonday.MySQL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.FirebaseEntity.TargetEntity;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.Users.Target;
import com.sharkit.nextmonday.Users.Users;
import com.sharkit.nextmonday.Users.Week;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TargetData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "my_target" ; // название таблицы в бд
    private static String DB_PATH;
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
    private static final String COLUMN_ALARM= "time_alarm";


    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String id = auth.getCurrentUser().getUid();


    private Context myContext;

    final String TAG = "qwerty";

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
                ""+COLUMN_DESCRIPTION+" TEXT, " +COLUMN_DATE+" TEXT, "+ COLUMN_ALARM+" TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void addNewTarget(MyTarget target){
        SQLiteDatabase db = this.getReadableDatabase();
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
                target.getDate() + "','" +
                target.getTime_alarm() + "');");
        db.close();
    }

    public ArrayList<MyTarget> findAllTarget (ArrayList<MyTarget> targets, String time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +"' AND " + COLUMN_DATE + " = '" + time + "'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
         target.setName(cursor.getString(1));
         target.setStatus(Boolean.parseBoolean(cursor.getString(2)));
         target.setRepeat_sunday(Boolean.parseBoolean(cursor.getString(3)));
         target.setRepeat_saturday(Boolean.parseBoolean(cursor.getString(4)));
         target.setRepeat_friday(Boolean.parseBoolean(cursor.getString(5)));
         target.setRepeat_thursday(Boolean.parseBoolean(cursor.getString(6)));
         target.setRepeat_wednesday(Boolean.parseBoolean(cursor.getString(7)));
         target.setRepeat_tuesday(Boolean.parseBoolean(cursor.getString(8)));
         target.setRepeat_monday(Boolean.parseBoolean(cursor.getString(9)));
         target.setRepeat(cursor.getString(10));
         target.setDescription(cursor.getString(11));
         target.setDate(cursor.getString(12));
         target.setTime_alarm(cursor.getString(13));
         targets.add(target);
        }
        cursor.close();
        db.close();

        return targets;
    }
    public int getCompleteCount(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_DATE + " = '" + date + "' AND " + COLUMN_STATUS + " = '" + true + "'", null);
         return cursor.getCount();
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +"'", null);
        return cursor.getCount();
    }


    public void deleteItemForDate(String time_alarm) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + time_alarm + "'");
        db.close();
    }

    public MyTarget findItemForDate(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        MyTarget target = new MyTarget();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date + "'", null);

        while (cursor.moveToNext()){
            target.setName(cursor.getString(1));
            target.setStatus(Boolean.parseBoolean(cursor.getString(2)));
            target.setRepeat_sunday(Boolean.parseBoolean(cursor.getString(3)));
            target.setRepeat_saturday(Boolean.parseBoolean(cursor.getString(4)));
            target.setRepeat_friday(Boolean.parseBoolean(cursor.getString(5)));
            target.setRepeat_thursday(Boolean.parseBoolean(cursor.getString(6)));
            target.setRepeat_wednesday(Boolean.parseBoolean(cursor.getString(7)));
            target.setRepeat_tuesday(Boolean.parseBoolean(cursor.getString(8)));
            target.setRepeat_monday(Boolean.parseBoolean(cursor.getString(9)));
            target.setRepeat(cursor.getString(10));
            target.setDescription(cursor.getString(11));
            target.setDate(cursor.getString(12));
            target.setTime_alarm(cursor.getString(13));
        }
        cursor.close();
        db.close();
        return target;
    }

    public void updateItemForDate(String timeForChange, MyTarget target) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE " + TABLE + " SET " +
                COLUMN_TEXT_TARGET + " = '" + target.getName() + "' , " +
                COLUMN_REPEAT_SUNDAY + " = '" + target.isRepeat_sunday() + "' , " +
                COLUMN_REPEAT_SATURDAY + " = '" + target.isRepeat_saturday() + "' , " +
                COLUMN_REPEAT_FRIDAY + " = '" + target.isRepeat_friday() + "' , " +
                COLUMN_REPEAT_THURSDAY + " = '" + target.isRepeat_thursday() + "' , " +
                COLUMN_REPEAT_WEDNESDAY + " = '" + target.isRepeat_wednesday() + "' , " +
                COLUMN_REPEAT_TUESDAY + " = '" + target.isRepeat_tuesday() + "' , " +
                COLUMN_REPEAT_MONDAY + " = '" + target.isRepeat_monday() + "' , " +
                COLUMN_REPEAT + " = '" + target.getRepeat() + "' , " +
                COLUMN_DESCRIPTION + " = '" + target.getDescription() + "' , " +
                COLUMN_DATE + " = '" + target.getDate() + "' , " +
                COLUMN_ALARM + " = '" + target.getTime_alarm() + "' WHERE " +
                COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + timeForChange + "'"
        );
        db.close();
    }

    public ArrayList<MyTarget> findAllComplete(ArrayList<MyTarget> targets) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_STATUS + " = '" + false + "'", null);
        while (cursor.moveToNext()){
            MyTarget target = new MyTarget();
            target.setName(cursor.getString(1));
            target.setStatus(Boolean.parseBoolean(cursor.getString(2)));
            target.setRepeat_sunday(Boolean.parseBoolean(cursor.getString(3)));
            target.setRepeat_saturday(Boolean.parseBoolean(cursor.getString(4)));
            target.setRepeat_friday(Boolean.parseBoolean(cursor.getString(5)));
            target.setRepeat_thursday(Boolean.parseBoolean(cursor.getString(6)));
            target.setRepeat_wednesday(Boolean.parseBoolean(cursor.getString(7)));
            target.setRepeat_tuesday(Boolean.parseBoolean(cursor.getString(8)));
            target.setRepeat_monday(Boolean.parseBoolean(cursor.getString(9)));
            target.setRepeat(cursor.getString(10));
            target.setDescription(cursor.getString(11));
            target.setDate(cursor.getString(12));
            target.setTime_alarm(cursor.getString(13));
            targets.add(target);

        }
        cursor.close();
        db.close();

        return targets;
    }

    public ArrayList<MyTarget> findItemForName(String text, ArrayList<MyTarget> targets) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id
                + "' AND " + COLUMN_TEXT_TARGET +" LIKE '" + text + "%'", null);
        while (cursor.moveToNext()){
            MyTarget target = new MyTarget();
            target.setName(cursor.getString(1));
            target.setStatus(Boolean.parseBoolean(cursor.getString(2)));
            target.setRepeat_sunday(Boolean.parseBoolean(cursor.getString(3)));
            target.setRepeat_saturday(Boolean.parseBoolean(cursor.getString(4)));
            target.setRepeat_friday(Boolean.parseBoolean(cursor.getString(5)));
            target.setRepeat_thursday(Boolean.parseBoolean(cursor.getString(6)));
            target.setRepeat_wednesday(Boolean.parseBoolean(cursor.getString(7)));
            target.setRepeat_tuesday(Boolean.parseBoolean(cursor.getString(8)));
            target.setRepeat_monday(Boolean.parseBoolean(cursor.getString(9)));
            target.setRepeat(cursor.getString(10));
            target.setDescription(cursor.getString(11));
            target.setDate(cursor.getString(12));
            target.setTime_alarm(cursor.getString(13));
            targets.add(target);
            Log.d(TAG, cursor.getCount()  +""  );
        }
        cursor.close();
        db.close();

        return targets;
    }
    public void completeTarget(String time_alarm, boolean complete){
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("UPDATE " + TABLE + " SET " + COLUMN_STATUS + " = '" + complete + "' WHERE " + COLUMN_ALARM + " = '" + time_alarm + "'");
        db.close();
    }

    public void synchronised(){
        TargetEntity entity = new TargetEntity();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            target.setName(cursor.getString(1));
            target.setStatus(Boolean.parseBoolean(cursor.getString(2)));
            target.setRepeat_sunday(Boolean.parseBoolean(cursor.getString(3)));
            target.setRepeat_saturday(Boolean.parseBoolean(cursor.getString(4)));
            target.setRepeat_friday(Boolean.parseBoolean(cursor.getString(5)));
            target.setRepeat_thursday(Boolean.parseBoolean(cursor.getString(6)));
            target.setRepeat_wednesday(Boolean.parseBoolean(cursor.getString(7)));
            target.setRepeat_tuesday(Boolean.parseBoolean(cursor.getString(8)));
            target.setRepeat_monday(Boolean.parseBoolean(cursor.getString(9)));
            target.setRepeat(cursor.getString(10));
            target.setDescription(cursor.getString(11));
            target.setDate(cursor.getString(12));
            target.setTime_alarm(cursor.getString(13));
            entity.createNewTarget(target);
        }
    }
}
