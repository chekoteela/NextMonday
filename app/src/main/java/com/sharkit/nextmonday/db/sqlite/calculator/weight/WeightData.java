package com.sharkit.nextmonday.db.sqlite.calculator.weight;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;
import static com.sharkit.nextmonday.entity.transformer.TransformerCalculator.transform;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sharkit.nextmonday.entity.calculator.Weight;
import com.sharkit.nextmonday.entity.calculator.WeightDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@SuppressLint({"Recycle", "SimpleDateFormat"})
public class WeightData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "weight_list"; // название таблицы в бд
    // названия столбцов
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WEIGHT = "weight";

    private final SQLiteDatabase db = this.getReadableDatabase();
    private final String id;

    public WeightData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        id = context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( " + COLUMN_ID + " TEXT, " + COLUMN_WEIGHT + " REAL, " +
                COLUMN_DATE + " TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    protected void create(Weight weight) {
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id +
                "','" + weight.getWeight() +
                "','" + weight.getDate() + "');");
    }

    protected boolean exist(String date) {
        return db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +
                "' AND " + COLUMN_DATE + " = '" + date + "'", null).moveToNext();
    }

    protected void update(Weight weight) {
        db.execSQL("UPDATE " + TABLE + " SET " +
                COLUMN_WEIGHT + " = '" + weight.getWeight() + "' WHERE " +
                COLUMN_ID + " = '" + id + "' AND " + COLUMN_DATE + " = '" +weight.getDate() + "'");
    }

    protected ArrayList<WeightDTO> getAllWeightDTO() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        float previousWeight = 0;
        ArrayList<WeightDTO> weightDTOS = new ArrayList<>();
        while (cursor.moveToNext()) {
            WeightDTO weightDTO = transform(getResult(cursor));
            Log.d("qwerty", previousWeight + "");
            if (previousWeight == 0){
                weightDTO.setDifference(0);
            }else {
                weightDTO.setDifference(cursor.getFloat(1) - previousWeight);
            }
            previousWeight = cursor.getFloat(1);
            weightDTOS.add(weightDTO);
        }
        return weightDTOS;
    }

    private Weight getResult(Cursor cursor) {
        Weight weight = new Weight();
        weight.setWeight(cursor.getFloat(1));
        weight.setDate(cursor.getString(2));
        return weight;
    }

    protected float getWeight() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        cursor.moveToLast();
        return cursor.getFloat(1);
    }
}
