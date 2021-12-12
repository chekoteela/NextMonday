package com.sharkit.nextmonday.db.sqlite.calculator.product;

import static com.sharkit.nextmonday.configuration.constant.BundleTag.DEFAULT;
import static com.sharkit.nextmonday.configuration.constant.UserServiceTag.USER_ID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharkit.nextmonday.entity.calculator.FoodInfo;

public class ProductPFCData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserTarget.db"; // название бд
    private static final int SCHEMA = 6; // версия базы данных
    public static final String TABLE = "product" ; // название таблицы в бд

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

    private final SQLiteDatabase db = this.getReadableDatabase();
    private final String id;

    public ProductPFCData(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        id = context.getSharedPreferences(Context.ACCOUNT_SERVICE, Context.MODE_PRIVATE).getString(USER_ID, DEFAULT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "( "+COLUMN_ID+" TEXT,"+COLUMN_NAME+" TEXT , "+COLUMN_PORTION+" INTEGER," +
                ""+COLUMN_CALORIE+" INTEGER, "+COLUMN_PROTEIN+" REAL," +
                ""+COLUMN_WHEY_PROTEIN+" REAL, "+COLUMN_SOY_PROTEIN+" REAL, " +
                ""+COLUMN_CASEIN_PROTEIN+" REAL, "+COLUMN_AGG_PROTEIN+" REAL," +
                ""+COLUMN_CARBOHYDRATE+" REAL, "+COLUMN_COMPLEX_C+" REAL, "+COLUMN_SIMPLE_C+" REAL, " +
                ""+COLUMN_FAT+" REAL, "+COLUMN_SATURATED_FAT+" REAL, "+COLUMN_TRANS_FAT+" REAL, "+COLUMN_OMEGA_9+" REAL,"
                +COLUMN_OMEGA_6+" REAL ," +COLUMN_OMEGA_3+" REAL," +COLUMN_ALA+" REAL," + COLUMN_DHA +" REAL," + COLUMN_EPA+" REAL," +
                COLUMN_CELLULOSE+" REAL, " + COLUMN_SALT +" REAL," +COLUMN_WATTER+" REAL," + COLUMN_CALCIUM+" REAL," +
                COLUMN_POTASSIUM +" REAL, " + COLUMN_BAR_CODE + " TEXT UNIQUE )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void create(FoodInfo foodInfo) {
        db.execSQL("INSERT INTO " + TABLE + " VALUES ('" + id + "','" + foodInfo.getName() + "','" + foodInfo.getPortion() +
                "','" + foodInfo.getCalorie() +
                "','" + foodInfo.getProtein() +
                "','" + foodInfo.getWheyProtein() +
                "','" + foodInfo.getSoyProtein() +
                "','" + foodInfo.getCaseinProtein() +
                "','" + foodInfo.getAggProtein() +
                "','" + foodInfo.getCarbohydrate() +
                "','" + foodInfo.getComplexCarbohydrate() +
                "','" + foodInfo.getSimpleCarbohydrate() +
                "','" + foodInfo.getFat() +
                "','" + foodInfo.getSaturatedFat() +
                "','" + foodInfo.getTransFat() +
                "','" + foodInfo.getOmega9() +
                "','" + foodInfo.getOmega6() +
                "','" + foodInfo.getOmega3() +
                "','" + foodInfo.getAla() +
                "','" + foodInfo.getDha() +
                "','" + foodInfo.getEpa() +
                "','" + foodInfo.getCellulose() +
                "','" + foodInfo.getSalt() +
                "','" + foodInfo.getWater() +
                "','" + foodInfo.getCalcium() +
                "','" + foodInfo.getPotassium() +
                "','" + foodInfo.getId() + "');");
    }

    public FoodInfo findById(String barCode) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_ID + " = '" + id + "' AND " +
                COLUMN_BAR_CODE + " = '" + barCode +"'", null);
        cursor.moveToNext();
        return getResult(cursor);

    }

    private FoodInfo getResult(Cursor cursor) {
        FoodInfo foodInfo = new FoodInfo();
        foodInfo.setName(cursor.getString(1));
        foodInfo.setPortion(cursor.getInt(2));
        foodInfo.setCalorie(cursor.getInt(3));
        foodInfo.setProtein(cursor.getFloat(4));
        foodInfo.setWheyProtein(cursor.getFloat(5));
        foodInfo.setSoyProtein(cursor.getFloat(6));
        foodInfo.setCaseinProtein(cursor.getFloat(7));
        foodInfo.setAggProtein(cursor.getFloat(8));
        foodInfo.setCarbohydrate(cursor.getFloat(9));
        foodInfo.setComplexCarbohydrate(cursor.getFloat(10));
        foodInfo.setSimpleCarbohydrate(cursor.getFloat(11));
        foodInfo.setFat(cursor.getFloat(12));
        foodInfo.setSaturatedFat(cursor.getFloat(13));
        foodInfo.setTransFat(cursor.getFloat(14));
        foodInfo.setOmega9(cursor.getFloat(15));
        foodInfo.setOmega6(cursor.getFloat(16));
        foodInfo.setOmega3(cursor.getFloat(17));
        foodInfo.setAla(cursor.getFloat(18));
        foodInfo.setDha(cursor.getFloat(19));
        foodInfo.setEpa(cursor.getFloat(20));
        foodInfo.setCellulose(cursor.getFloat(21));
        foodInfo.setSalt(cursor.getFloat(22));
        foodInfo.setWater(cursor.getFloat(23));
        foodInfo.setCalcium(cursor.getFloat(24));
        foodInfo.setPotassium(cursor.getFloat(25));
        foodInfo.setId(cursor.getString(26));
        return foodInfo;
    }
}
