package com.sharkit.nextmonday.configuration.annotation.realisation;

import static android.database.Cursor.FIELD_TYPE_BLOB;
import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_NULL;
import static android.database.Cursor.FIELD_TYPE_STRING;
import static com.sharkit.nextmonday.configuration.annotation.transformer.ByteArrayTransformer.toByteArray;
import static com.sharkit.nextmonday.configuration.annotation.transformer.ByteArrayTransformer.toObject;
import static com.sharkit.nextmonday.configuration.service.SharedPreference.USER_ID;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sharkit.nextmonday.configuration.annotation.Collection;
import com.sharkit.nextmonday.configuration.annotation.Id;
import com.sharkit.nextmonday.configuration.service.SharedPreference;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class SQLiteTemplate<O> extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NextMonday.db";
    private static final int SCHEMA = 1;
    private static final String LOG = "SQLiteTemplate";

    private static final String TYPE_STRING = "java.lang.String";
    private static final String TYPE_INTEGER = "java.lang.Integer";
    private static final String TYPE_INT = "int";
    private static final String SQL_STRING = "TEXT";
    private static final String SQL_INTEGER = "INTEGER";
    private static final String SQL_BLOB = "BLOB";
    private static final String SQL_UNIQUE = "TEXT UNIQUE";
    private static final String ID = "id";

    private String id = "";
    private Cursor cursor;
    private O object;

    private final Class<O> oClass;
    private final SQLiteDatabase db = this.getReadableDatabase();
    private final String userId;
    private final String collection;

    public SQLiteTemplate(Context context, Class<O> oClass) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.oClass = oClass;
        userId = (String) SharedPreference.getPreferences(context, SharedPreference.USER_PREFERENCES).getValueShared().get(USER_ID);
        collection = Optional.of(Objects.requireNonNull(oClass.getAnnotation(Collection.class)).collection())
                .orElseThrow(() -> new RuntimeException("Entity is not collection"));
        try {
            this.object = oClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String request = "CREATE TABLE IF NOT EXISTS " +
                Optional.ofNullable(oClass.getAnnotation(Collection.class))
                        .orElseThrow(() -> new RuntimeException("Entity is not collection")).collection() +
                "(%s%s)";
        StringBuilder requestBody = new StringBuilder();

        Arrays.stream(oClass.getDeclaredFields()).forEach(f -> {
            if (f.isAnnotationPresent(Id.class)) {
                id = String.format("%s %s", f.getName(), getFieldType(ID));
            } else {
                requestBody.append(getField(f));
            }
        });
        db.execSQL(String.format(request, id, requestBody.toString()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void create(O entity) {
        ContentValues data = new ContentValues();
        Arrays.stream(oClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                setContentValues(field, data, entity);
            } catch (IllegalAccessException e) {
                Log.e(LOG, e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        });
        db.insertOrThrow(collection, null, data);
    }

    public O findById(String id) {
        Arrays.stream(oClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .forEach(field -> cursor = db.rawQuery("SELECT * FROM " + collection
                        + " WHERE " + field.getName() + " = '" + id + "' AND userId = '" + userId + "'", null));

        if (cursor.moveToNext()) {
            Arrays.stream(oClass.getDeclaredFields()).forEach(f -> {
                f.setAccessible(true);
                try {
                    getValue(f);
                } catch (IllegalAccessException e) {
                    Log.e(LOG, e.getMessage(), e);
                    throw new RuntimeException(e.getMessage());
                }
            });
            return object;
        }
        cursor.close();
        return null;
    }

    private void getValue(Field field) throws IllegalAccessException {
        switch (cursor.getType(cursor.getColumnIndex(field.getName()))) {
            case FIELD_TYPE_NULL:
                field.set(object, null);
                break;
            case FIELD_TYPE_INTEGER:
                field.set(object, cursor.getInt(cursor.getColumnIndex(field.getName())));
                break;
            case FIELD_TYPE_FLOAT:
                field.set(object, cursor.getFloat(cursor.getColumnIndex(field.getName())));
                break;
            case FIELD_TYPE_STRING:
                field.set(object, cursor.getString(cursor.getColumnIndex(field.getName())));
                break;
            case FIELD_TYPE_BLOB:
                field.set(object, toObject(cursor.getBlob(cursor.getColumnIndex(field.getName()))));
        }
    }

    private void setContentValues(Field field, ContentValues values, O entity) throws IllegalAccessException {
        switch (field.getType().getName()) {
            case TYPE_STRING:
                values.put(field.getName(), (String) field.get(entity));
                break;
            case TYPE_INT:
            case TYPE_INTEGER:
                values.put(field.getName(), (Integer) Optional.ofNullable(field.get(entity)).orElse(null));
                break;
            default:
                values.put(field.getName(), toByteArray(field.get(entity)));
        }
    }

    @SuppressLint("NewApi")
    private String getField(Field field) {
        String fieldValue = " ,%s %s";
        return String.format(fieldValue, field.getName(), getFieldType(field.getGenericType().getTypeName()));
    }

    private String getFieldType(String genericType) {
        switch (genericType) {
            case ID:
                return SQL_UNIQUE;
            case TYPE_STRING:
                return SQL_STRING;
            case TYPE_INT:
                return SQL_INTEGER;
            default:
                return SQL_BLOB;
        }

    }
}
