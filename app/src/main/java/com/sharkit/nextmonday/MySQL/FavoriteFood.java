package com.sharkit.nextmonday.MySQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteFood extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "FavoriteFood" ; // название таблицы в бд
    private static String DB_PATH;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PORTION = "portion";
    public static final String COLUMN_CALORIE = "calorie";
    public static final String COLUMN_PROTEIN = "protein";
    public static final String COLUMN_WHEY_PROTEIN = "whey_protein";
    public static final String COLUMN_SOY_PROTEIN = "soy_protein";
    public static final String COLUMN_CASEIN_PROTEIN = "casein_protein";
    public static final String COLUMN_AGG_PROTEIN = "agg_protein";
    public static final String COLUMN_CARBOHYDRATE = "carbohydrate";
    public static final String COLUMN_COMPLEX_C = "complex_carbohydrate";
    public static final String COLUMN_SIMPLE_C = "simple_carbohydrate";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_SATURATED_FAT = "saturated_fat";
    public static final String COLUMN_TRANS_FAT = "trans_fat";
    public static final String COLUMN_OMEGA_9 = "omega_9";
    public static final String COLUMN_OMEGA_6 = "omega_6";
    public static final String COLUMN_OMEGA_3 = "omega_3";
    public static final String COLUMN_ALA = "ala";
    public static final String COLUMN_DHA = "dha";
    public static final String COLUMN_EPA = "epa";
    public static final String COLUMN_CELLULOSE = "cellulose";
    public static final String COLUMN_SALT = "salt";
    public static final String COLUMN_WATTER = "watter";
    public static final String COLUMN_CALCIUM = "calcium";
    public static final String COLUMN_POTASSIUM = "potassium";
    public static final String COLUMN_BAR_CODE = "bar_code";

    Context myContext;

    public FavoriteFood(Context context){
        super(context, DATABASE_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( "+COLUMN_ID+" TEXT,"+COLUMN_NAME+" TEXT , "+COLUMN_PORTION+" TEXT," +
                ""+COLUMN_CALORIE+" TEXT, "+COLUMN_PROTEIN+" TEXT," +
                ""+COLUMN_WHEY_PROTEIN+" TEXT, "+COLUMN_SOY_PROTEIN+" TEXT, " +
                ""+COLUMN_CASEIN_PROTEIN+" TEXT, "+COLUMN_AGG_PROTEIN+" TEXT," +
                ""+COLUMN_CARBOHYDRATE+" TEXT, "+COLUMN_COMPLEX_C+" TEXT, "+COLUMN_SIMPLE_C+" TEXT, " +
                ""+COLUMN_FAT+" TEXT, "+COLUMN_SATURATED_FAT+" TEXT, "+COLUMN_TRANS_FAT+" TEXT, "+COLUMN_OMEGA_9+" TEXT,"
                +COLUMN_OMEGA_6+" TEXT ," +COLUMN_OMEGA_3+" TEXT," +COLUMN_ALA+" TEXT," + COLUMN_DHA +" TEXT," + COLUMN_EPA+" TEXT," +
                COLUMN_CELLULOSE+" TEXT, " + COLUMN_SALT +" TEXT," +COLUMN_WATTER+" TEXT," + COLUMN_CALCIUM+" TEXT," +
                COLUMN_POTASSIUM +" TEXT, " + COLUMN_BAR_CODE + " TEXT UNIQUE )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
