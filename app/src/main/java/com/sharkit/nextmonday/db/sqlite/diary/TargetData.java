package com.sharkit.nextmonday.db.sqlite.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;

import java.util.ArrayList;
import java.util.Objects;

public class TargetData extends SQLiteOpenHelper implements TargetMethod {
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
    private static final String COLUMN_ALARM = "time_alarm";


    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
    private final SQLiteDatabase db = this.getReadableDatabase();

    public TargetData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( " + COLUMN_ID + " TEXT," + COLUMN_TEXT_TARGET + " TEXT , " + COLUMN_STATUS + " BOOL," +
                "" + COLUMN_REPEAT_SUNDAY + " BOOL, " + COLUMN_REPEAT_SATURDAY + " BOOL," +
                "" + COLUMN_REPEAT_FRIDAY + " BOOL, " + COLUMN_REPEAT_THURSDAY + " BOOL, " +
                "" + COLUMN_REPEAT_WEDNESDAY + " BOOL, " + COLUMN_REPEAT_TUESDAY + " BOOL," +
                "" + COLUMN_REPEAT_MONDAY + " BOOL, " +
                "" + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_ALARM + " INTEGER UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    @Override
    public Object findById(long id) {
        return null;
    }

    @SuppressLint("Recycle")
    public ArrayList<TargetDiary> findAllByDate(String date) {
        ArrayList<TargetDiary> listByDate = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE + " = '" + date + "'", null);
        while (cursor.moveToNext()){
            listByDate.add(getResult(cursor));
        }
        return listByDate;
    }

    public TargetDiary getResult(Cursor cursor) {
        TargetDiaryDTO targetDiary = new TargetDiaryDTO();
        targetDiary.setId(cursor.getString(0));
        targetDiary.setText(cursor.getString(1));
        targetDiary.setStatus(Boolean.parseBoolean(cursor.getString(2)));
        targetDiary.setRepeatSunday(Boolean.parseBoolean(cursor.getString(3)));
        targetDiary.setRepeatSaturday(Boolean.parseBoolean(cursor.getString(4)));
        targetDiary.setRepeatFriday(Boolean.parseBoolean(cursor.getString(5)));
        targetDiary.setRepeatThursday(Boolean.parseBoolean(cursor.getString(6)));
        targetDiary.setRepeatWednesday(Boolean.parseBoolean(cursor.getString(7)));
        targetDiary.setRepeatTuesday(Boolean.parseBoolean(cursor.getString(8)));
        targetDiary.setRepeatMonday(Boolean.parseBoolean(cursor.getString(9)));
        targetDiary.setDescription(cursor.getString(10));
        targetDiary.setDate(cursor.getString(11));
        targetDiary.setTimeForAlarm(cursor.getLong(12));
        return new TargetDiary().transform(targetDiary);
    }
}
