package com.sharkit.nextmonday.db.sqlite.calculator.weight;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharkit.nextmonday.entity.calculator.Weight;

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

    protected void create (Weight weight){
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id +
                "','" + weight.getWeight() + "','" + weight.getDate() + "');");
    }
}
