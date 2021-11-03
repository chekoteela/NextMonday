package com.sharkit.nextmonday.db.sqlite.calculator.ration_list;

import static com.sharkit.nextmonday.configuration.constant.AlertButton.SHOW_DATE_FORMAT;
import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@SuppressLint("Recycle")
public class RationLinkData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "ration_list"; // название таблицы в бд
    // названия столбцов
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_NAME_MEAL = "name_meal";
    private static final String COLUMN_VISIBLE = "visible";
    private static final String COLUMN_PORTION = "portion";
    private static final String COLUMN_LINK_ID = "link_id";
    private static final String COLUMN_DATE = "date";


    private final SQLiteDatabase db = this.getReadableDatabase();
    private final String id;

    public RationLinkData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        id = context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( " + COLUMN_ID + " TEXT," + COLUMN_LINK + " TEXT , " + COLUMN_NAME_MEAL + " BOOL," +
                "" + COLUMN_VISIBLE + " BOOL, " + COLUMN_PORTION + " REAL," +
                "" + COLUMN_LINK_ID + " INTEGER UNIQUE, " + COLUMN_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    @SuppressLint("SimpleDateFormat")
    public void create(LinkFoodDTO linkFoodDTO) {
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id +
                "','" + linkFoodDTO.getLink() + "','" + linkFoodDTO.getMeal() +
                "','" + linkFoodDTO.isVisible() + "','" + linkFoodDTO.getPortion() +
                "','" + linkFoodDTO.getId() + "','" + new SimpleDateFormat(SHOW_DATE_FORMAT).format(linkFoodDTO.getId()) + "');");
    }

    public ArrayList<LinkFoodDTO> findByMealAndDate(String meal, String date) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +
                "' AND " + COLUMN_NAME_MEAL + " = '" + meal + "' AND " + COLUMN_VISIBLE + " = '" + true +
                "' AND " + COLUMN_DATE + " = '" + date + "'", null);
        ArrayList<LinkFoodDTO> links = new ArrayList<>();
        while (cursor.moveToNext()){
            links.add(getResult(cursor));
        }
        return links;
    }

    public ArrayList<LinkFoodDTO> findAllByDate(String date) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id +
                "' AND " + COLUMN_VISIBLE + " = '" + true +
                "' AND " + COLUMN_DATE + " = '" + date + "'", null);
        ArrayList<LinkFoodDTO> linkFoodDTOS = new ArrayList<>();
        while (cursor.moveToNext()){
            linkFoodDTOS.add(getResult(cursor));
        }
        return linkFoodDTOS;
    }

    private LinkFoodDTO getResult(Cursor cursor){
        LinkFoodDTO linkFoodDTO = new LinkFoodDTO();
        linkFoodDTO.setLink(cursor.getString(1));
        linkFoodDTO.setMeal(cursor.getString(2));
        linkFoodDTO.setVisible(Boolean.parseBoolean(cursor.getString(3)));
        linkFoodDTO.setPortion(cursor.getFloat(4));
        return linkFoodDTO;
    }
}
