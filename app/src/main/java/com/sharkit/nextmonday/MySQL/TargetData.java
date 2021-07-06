package com.sharkit.nextmonday.MySQL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.FirebaseEntity.TargetEntity;
import com.sharkit.nextmonday.Users.MyTarget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class TargetData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "my_target" ; // название таблицы в бд
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


    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();


    final String TAG = "qwerty";

    public TargetData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        String DB_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( "+COLUMN_ID+" TEXT,"+COLUMN_TEXT_TARGET+" TEXT , "+COLUMN_STATUS+" BOOL," +
                ""+COLUMN_REPEAT_SUNDAY+" BOOL, "+COLUMN_REPEAT_SATURDAY+" BOOL," +
                ""+COLUMN_REPEAT_FRIDAY+" BOOL, "+COLUMN_REPEAT_THURSDAY+" BOOL, " +
                ""+COLUMN_REPEAT_WEDNESDAY+" BOOL, "+COLUMN_REPEAT_TUESDAY+" BOOL," +
                ""+COLUMN_REPEAT_MONDAY+" BOOL, "+COLUMN_REPEAT+" TEXT, " +
                ""+COLUMN_DESCRIPTION+" TEXT, " +COLUMN_DATE+" TEXT, "+ COLUMN_ALARM+" INTEGER UNIQUE)");
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
//        db.close();
    }

    public ArrayList<MyTarget> findAllTarget (ArrayList<MyTarget> targets, String time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +"' AND " + COLUMN_DATE + " = '" + time + "'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            writeTarget(target,cursor);
         targets.add(target);
        }
//        cursor.close();
//        db.close();

        return targets;
    }
    public int getCompleteCount(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_DATE + " = '" + date + "' AND " + COLUMN_STATUS + " = '" + true + "'", null);
         return cursor.getCount();
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +"'", null);
        return cursor.getCount();
    }


    public void deleteItemForDate(Long time_alarm) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + time_alarm + "'");
//        db.close();
    }

    public MyTarget findItemForDate(Long date){
        SQLiteDatabase db = this.getReadableDatabase();
        MyTarget target = new MyTarget();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date + "'", null);

        while (cursor.moveToNext()){
            writeTarget(target,cursor);
        }
//        cursor.close();
//        db.close();
        return target;
    }

    public void updateItemForDate(Long timeForChange, MyTarget target) {
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
//        db.close();
    }

    public ArrayList<MyTarget> findAllComplete(ArrayList<MyTarget> targets) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_STATUS + " = '" + false + "'", null);
        while (cursor.moveToNext()){
            MyTarget target = new MyTarget();
            writeTarget(target,cursor);
            targets.add(target);

        }
//        cursor.close();
//        db.close();

        return targets;
    }

    public ArrayList<MyTarget> findItemForName(String text, ArrayList<MyTarget> targets) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id
                + "' AND " + COLUMN_TEXT_TARGET +" LIKE '" + text + "%'", null);
        while (cursor.moveToNext()){
            MyTarget target = new MyTarget();
            writeTarget(target,cursor);
            targets.add(target);
            Log.d(TAG, cursor.getCount()  +""  );
        }
//        cursor.close();
//        db.close();

        return targets;
    }
    public void completeTarget(Long time_alarm, boolean complete, String date){
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("UPDATE " + TABLE + " SET " + COLUMN_STATUS + " = '" + complete + "' WHERE " + COLUMN_ALARM + " = '" + time_alarm + "' AND " + COLUMN_DATE + "= '" + date + "'");
        db.close();
    }

    public void synchronised(){
        TargetEntity entity = new TargetEntity();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            writeTarget(target,cursor);
            entity.createNewTarget(target);
        }
    }
    public void deleteAllSimilarTarget(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        TargetEntity entity = new TargetEntity();

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TEXT_TARGET + " = '" + name + "' AND " +
                COLUMN_STATUS + " = '" + false + "' AND " + COLUMN_REPEAT + " <> 'one not time' AND " + COLUMN_REPEAT + " <> 'one with time'",null);
        while (cursor.moveToNext()){
            deleteItemForDate(cursor.getLong(13));
            entity.deleteTarget(cursor.getLong(13));
        }
    }
    @SuppressLint({"SimpleDateFormat","Recycle"})
    public void findFromRepeat(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE + " = '" + format.format(calendar.getTimeInMillis()) + "' AND "+
                COLUMN_STATUS + " = '" + false + "'",null);
        while (cursor.moveToNext()){
            MyTarget target = new MyTarget();
            writeTarget(target,cursor);
            for (int i = 3; i < 10; i++){
                if (Boolean.parseBoolean(cursor.getString(i))){
                    writeRepeat(i,target);
                }
            }
        }
    }


    private void writeRepeat(int i, MyTarget target) {
        Calendar calendar = Calendar.getInstance();
        switch (i){
            case 3:
                findDayOfWeek(Calendar.SUNDAY, calendar, target);
                break;
            case 4:
                findDayOfWeek(Calendar.SATURDAY, calendar, target);
                break;
            case 5:
                findDayOfWeek(Calendar.FRIDAY, calendar,target);
                break;
            case 6:
                findDayOfWeek(Calendar.THURSDAY, calendar, target);
                break;
            case 7:
                findDayOfWeek(Calendar.WEDNESDAY, calendar, target);
                break;
            case 8:
                findDayOfWeek(Calendar.TUESDAY,calendar,target);
                break;
            case 9:
                findDayOfWeek(Calendar.MONDAY,calendar,target);

                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void findDayOfWeek(int day, Calendar calendar, MyTarget target){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        if (calendar.get(Calendar.DAY_OF_WEEK) == day){
            target.setTime_alarm(target.getTime_alarm() + 604800000);
            target.setDate(format.format(target.getTime_alarm()));
            try {
                addNewTarget(target);
            }catch (SQLiteConstraintException ignored){}
            return;
        }

        while (calendar.get(Calendar.DAY_OF_WEEK) != day){
            calendar.add(Calendar.DAY_OF_WEEK,1);
        }
            target.setTime_alarm(switchRepeat(target.getRepeat(), target.getTime_alarm()));
            target.setDate(format.format(calendar.getTimeInMillis()));

        try {
            addNewTarget(target);
        }catch (SQLiteConstraintException ignored){}
    }

    private Long switchRepeat(String repeat, Long time_alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time_alarm);
        switch (repeat){
            case "every day not time":
            case "every day with time":
               time_alarm = calendar.getTimeInMillis() +  86400000;
                break;
            case "select day not time":
            case "select day with time":
                time_alarm = calendar.getTimeInMillis() +  604800000;
                break;
        }

       return time_alarm;
    }

    private void writeTarget(MyTarget target, Cursor cursor) {
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
        target.setTime_alarm(cursor.getLong(13));
    }

    @SuppressLint("SimpleDateFormat")
    public long getAlarm() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_STATUS + " = '" + false + "' AND " + COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "' AND " +
                COLUMN_REPEAT + " = 'one with time' OR " + COLUMN_REPEAT + " = 'select day with time' OR " + COLUMN_REPEAT + " = 'every day with time'", null);
        while (cursor.moveToNext()){
            return cursor.getLong(13);
        }
        return calendar.getTimeInMillis()-1;
    }
    @SuppressLint("SimpleDateFormat")
    public int getCountAlarm(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_STATUS + " = '" + false + "' AND " + COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "' AND " +
                COLUMN_REPEAT + " = 'one with time' OR " + COLUMN_REPEAT + " = 'select day with time' OR " + COLUMN_REPEAT + " = 'every day with time'", null);

         return cursor.getCount();
    }


}
