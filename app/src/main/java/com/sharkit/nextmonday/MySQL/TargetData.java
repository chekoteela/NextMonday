package com.sharkit.nextmonday.MySQL;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.Configuration.AlarmDiary;
import com.sharkit.nextmonday.FirebaseEntity.TargetEntity;
import com.sharkit.nextmonday.MainMenu;
import com.sharkit.nextmonday.Users.MyTarget;
import com.sharkit.nextmonday.ui.Diary.MainDiary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

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


    final String TAG = "qwerty";

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

    public void addNewTarget(MyTarget target) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id + "','" +
                target.getName() + "','" +
                target.isStatus() + "','" +
                target.isRepeat_sunday() + "','" +
                target.isRepeat_saturday() + "','" +
                target.isRepeat_friday() + "','" +
                target.isRepeat_thursday() + "','" +
                target.isRepeat_wednesday() + "','" +
                target.isRepeat_tuesday() + "','" +
                target.isRepeat_monday() + "','" +
                target.getRepeat() + "','" +
                target.getDescription() + "','" +
                target.getDate() + "','" +
                target.getTime_alarm() + "');");
//        db.close();
    }

    public ArrayList<MyTarget> findAllTarget(ArrayList<MyTarget> targets, String time) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE + " = '" + time + "'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            writeTarget(target, cursor);
            targets.add(target);
        }
//        cursor.close();
//        db.close();

        return targets;
    }

    public int getCompleteCount(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_DATE + " = '" + date + "' AND " + COLUMN_STATUS + " = '" + true + "'", null);
        return cursor.getCount();
    }

    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        return cursor.getCount();
    }


    public void deleteItemForDate(Long time_alarm) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + time_alarm + "'");
//        db.close();
    }

    public MyTarget findItemForDate(Long date) {
        SQLiteDatabase db = this.getReadableDatabase();
        MyTarget target = new MyTarget();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date + "'", null);

        while (cursor.moveToNext()) {
            writeTarget(target, cursor);
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
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            writeTarget(target, cursor);
            targets.add(target);

        }
//        cursor.close();
//        db.close();

        return targets;
    }

    public ArrayList<MyTarget> findItemForName(String text, ArrayList<MyTarget> targets) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id
                + "' AND " + COLUMN_TEXT_TARGET + " LIKE '" + text + "%'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            writeTarget(target, cursor);
            targets.add(target);
        }
//        cursor.close();
//        db.close();

        return targets;
    }

    public void completeTarget(Long time_alarm, boolean complete, String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("UPDATE " + TABLE + " SET " + COLUMN_STATUS + " = '" + complete + "' WHERE " + COLUMN_ALARM + " = '" + time_alarm + "' AND " + COLUMN_DATE + "= '" + date + "'");
        db.close();
    }

    public void synchronised() {
        TargetEntity entity = new TargetEntity();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        while (cursor.moveToNext()) {
            MyTarget target = new MyTarget();
            writeTarget(target, cursor);
            entity.createNewTarget(target);
        }
    }

    public void deleteAllSimilarTarget(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        TargetEntity entity = new TargetEntity();

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TEXT_TARGET + " = '" + name + "' AND " +
                COLUMN_STATUS + " = '" + false + "' AND " + COLUMN_REPEAT + " <> 'one not time' AND " + COLUMN_REPEAT + " <> 'one with time'", null);
        while (cursor.moveToNext()) {
            deleteItemForDate(cursor.getLong(13));
            entity.deleteTarget(cursor.getLong(13));
        }
    }

    @SuppressLint({"SimpleDateFormat", "Recycle"})
    public void findFromRepeat() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_DATE + " = '" + format.format(calendar.getTimeInMillis()) + "' AND " +
                COLUMN_STATUS + " = '" + false + "'", null);

        while (cursor.moveToNext()) {
            MyTarget target = writeTargets(cursor);
            calendar.setTimeInMillis(target.getTime_alarm());
            for (int i = 3; i < 10; i++) {
                if (Boolean.parseBoolean(cursor.getString(i))) {
                    writeRepeat(i, target, calendar);
                }
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        }
    }


    private void writeRepeat(int i, MyTarget target, Calendar calendar) {
        switch (i) {
            case 3:
                findDayOfWeek(Calendar.SUNDAY, target, calendar);
                break;
            case 4:
                findDayOfWeek(Calendar.SATURDAY, target,calendar);
                break;
            case 5:
                findDayOfWeek(Calendar.FRIDAY, target,calendar);
                break;
            case 6:
                findDayOfWeek(Calendar.THURSDAY, target,calendar);
                break;
            case 7:
                findDayOfWeek(Calendar.WEDNESDAY, target,calendar);
                break;
            case 8:
                findDayOfWeek(Calendar.TUESDAY, target,calendar);
                break;
            case 9:
                findDayOfWeek(Calendar.MONDAY, target,calendar);

                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void findDayOfWeek(int day, MyTarget target,Calendar calendar) {

        if (calendar.get(Calendar.DAY_OF_WEEK) == day) {
            addRepeatToNextWeek(target, calendar);
        }
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        target.setTime_alarm(calendar.getTimeInMillis());
        target.setDate(format.format(calendar.getTimeInMillis()));

        try {
            addNewTarget(target);
        } catch (SQLiteConstraintException ignored) {
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void addRepeatToNextWeek(MyTarget target, Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        target.setTime_alarm(calendar.getTimeInMillis());
        target.setDate(format.format(target.getTime_alarm()));
        try {
            addNewTarget(target);
        } catch (SQLiteConstraintException ignored) {
        }
    }


    private MyTarget writeTargets(Cursor cursor) {
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
        target.setTime_alarm(cursor.getLong(13));

        return target;
    }
    private MyTarget writeTarget(MyTarget target, Cursor cursor) {

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

        return target;
    }

    @SuppressLint("SimpleDateFormat")
    public void getAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_STATUS + " = '" + false + "' AND " + COLUMN_DATE + " = '" + dateFormat.format(calendar.getTimeInMillis()) + "' AND " +
                COLUMN_REPEAT + " = 'one with time' OR " + COLUMN_REPEAT + " = 'select day with time' OR " + COLUMN_REPEAT + " = 'every day with time'", null);
        while (cursor.moveToNext()) {
            setAlarm(cursor.getLong(13), cursor.getPosition(), context, cursor.getString(1), cursor.getString(11));
        }
    }

    private void setAlarm(long time, int count, Context context, String text, String description) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmDiary.class);
        intent.putExtra("part of the Next Monday", "Ежедневник")
                .putExtra("text of target", text)
                .putExtra("description", description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, count, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        Calendar calendar = Calendar.getInstance();
        if (time < calendar.getTimeInMillis() + 5000) {
            alarmManager.cancel(pendingIntent);
        }
    }


}
