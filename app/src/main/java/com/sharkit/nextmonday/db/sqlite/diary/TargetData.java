package com.sharkit.nextmonday.db.sqlite.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.sharkit.nextmonday.entity.diary.ChildItemTargetDTO;
import com.sharkit.nextmonday.entity.diary.TargetDateForAlarmDTO;
import com.sharkit.nextmonday.entity.diary.TargetDiary;
import com.sharkit.nextmonday.entity.diary.TargetDiaryDTO;

import java.util.ArrayList;
import java.util.Objects;

@SuppressLint("Recycle")
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
    private static final String COLUMN_IS_ALARM = "alarm";
    private static final String COLUMN_VISIBLE = "visible";


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
                "" + COLUMN_REPEAT_MONDAY + " BOOL, " + COLUMN_IS_ALARM + " BOOL, " +
                "" + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_ALARM + " INTEGER UNIQUE, " + COLUMN_VISIBLE + " BOOL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void create(TargetDiary targetDiary) {
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id + "','" + targetDiary.getText() + "','" + targetDiary.isStatus() +
                "','" + targetDiary.isRepeatSunday() + "','" + targetDiary.isRepeatSaturday() +
                "','" + targetDiary.isRepeatFriday() + "','" + targetDiary.isRepeatThursday() +
                "','" + targetDiary.isRepeatWednesday() + "','" + targetDiary.isRepeatTuesday() +
                "','" + targetDiary.isRepeatMonday() + "','" + targetDiary.isAlarm() + "','" + targetDiary.getDescription() +
                "','" + targetDiary.getDate() + "','" + targetDiary.getTimeForAlarm() + "' , '" + true + "');");
    }

    public void update(TargetDiary targetDiary, long date) {
        Log.d("qwerty", targetDiary.getText());

        db.execSQL(("UPDATE " + TABLE + " SET " + COLUMN_TEXT_TARGET + " = '" + targetDiary.getText() + "' , " +
                COLUMN_REPEAT_SUNDAY + " = '" + targetDiary.isRepeatSunday() + "' , " +
                COLUMN_REPEAT_SATURDAY + " = '" + targetDiary.isRepeatSaturday() + "' , " +
                COLUMN_REPEAT_FRIDAY + " = '" + targetDiary.isRepeatFriday() + "' , " +
                COLUMN_REPEAT_THURSDAY + " = '" + targetDiary.isRepeatThursday() + "' , " +
                COLUMN_REPEAT_WEDNESDAY + " = '" + targetDiary.isRepeatWednesday() + "' , " +
                COLUMN_REPEAT_TUESDAY + " = '" + targetDiary.isRepeatTuesday() + "' , " +
                COLUMN_REPEAT_MONDAY + " = '" + targetDiary.isRepeatMonday() + "' , " +
                COLUMN_IS_ALARM + " = '" + targetDiary.isAlarm() + "' , " +
                COLUMN_DESCRIPTION + " = '" + targetDiary.getDescription() + "' , " +
                COLUMN_DATE + " = '" + targetDiary.getDate() + "' , " +
                COLUMN_ALARM + " = '" + targetDiary.getTimeForAlarm() +
                "' WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date + "'"));
    }

    @Override
    public Object findById(long id) {
        return null;
    }

    public TargetDiary findByDate(long date) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +
                "' AND " + COLUMN_ALARM + " = '" + date + "' AND " + COLUMN_VISIBLE + " = '" + true + "'", null);
        cursor.moveToNext();
        return getResult(cursor);
    }
    public ArrayList<TargetDiary> findAllTargetForDay(String date){
        ArrayList<TargetDiary> targetDiaries = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE+ " = '" + date +
                "' AND " + COLUMN_VISIBLE + " = '" + true + "'", null);
        while (cursor.moveToNext()){
            targetDiaries.add(getResult(cursor));
        }
        return targetDiaries;
    }

    public ArrayList<ChildItemTargetDTO> findAllByDate(String date) {
        ArrayList<ChildItemTargetDTO> itemTargetDTOS = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE + " = '" + date +
                "' AND " + COLUMN_VISIBLE + " = '" + true + "'", null);
        while (cursor.moveToNext()) {
            itemTargetDTOS.add(new ChildItemTargetDTO().transform(getResult(cursor)));
        }
        return itemTargetDTOS;
    }

    private TargetDiary getResult(Cursor cursor) {
        TargetDiaryDTO targetDiaryDTO = new TargetDiaryDTO();
        targetDiaryDTO.setId(cursor.getString(0));
        targetDiaryDTO.setText(cursor.getString(1));
        targetDiaryDTO.setStatus(Boolean.parseBoolean(cursor.getString(2)));
        targetDiaryDTO.setRepeatSunday(Boolean.parseBoolean(cursor.getString(3)));
        targetDiaryDTO.setRepeatSaturday(Boolean.parseBoolean(cursor.getString(4)));
        targetDiaryDTO.setRepeatFriday(Boolean.parseBoolean(cursor.getString(5)));
        targetDiaryDTO.setRepeatThursday(Boolean.parseBoolean(cursor.getString(6)));
        targetDiaryDTO.setRepeatWednesday(Boolean.parseBoolean(cursor.getString(7)));
        targetDiaryDTO.setRepeatTuesday(Boolean.parseBoolean(cursor.getString(8)));
        targetDiaryDTO.setRepeatMonday(Boolean.parseBoolean(cursor.getString(9)));
        targetDiaryDTO.setAlarm(Boolean.parseBoolean(cursor.getString(10)));
        targetDiaryDTO.setDescription(cursor.getString(11));
        targetDiaryDTO.setDate(cursor.getString(12));
        targetDiaryDTO.setTimeForAlarm(cursor.getLong(13));
        return new TargetDiary().transform(targetDiaryDTO);
    }


    public int getCompleteTarget(String date) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE + " = '" + date +
                "' AND " + COLUMN_STATUS + " = '" + true + "'", null);
        return cursor.getCount();
    }

    public void delete(long date) {
        db.execSQL(("UPDATE " + TABLE + " SET " + COLUMN_VISIBLE + " = '" + false + "' " +
                "WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date + "'"));    }

    public void setCheckedTarget(long date, boolean isChecked) {
        db.execSQL(("UPDATE " + TABLE + " SET " + COLUMN_STATUS + " = '" + isChecked + "' " +
                "WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date + "'"));
    }

    public TargetDateForAlarmDTO getRepeatForAlarmDTO(long date) {
        TargetDateForAlarmDTO alarmDTO = new TargetDateForAlarmDTO();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_ALARM + " = '" + date +
                "' AND " + COLUMN_VISIBLE + " = '" + true + "'", null);
        while (cursor.moveToNext()) {
            alarmDTO = getResult(cursor).transform(alarmDTO);
        }
        return alarmDTO;
    }

    public void deleteSimilar(boolean alarm, String text, String description) {
        db.execSQL("DELETE FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TEXT_TARGET + " = '" + text + "' AND " +
                COLUMN_IS_ALARM + " = '" + alarm + "' AND " + COLUMN_DESCRIPTION + " = '" + description + "'");

    }
}
