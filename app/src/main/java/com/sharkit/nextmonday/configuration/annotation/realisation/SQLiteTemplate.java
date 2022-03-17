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
import com.sharkit.nextmonday.configuration.annotation.Query;
import com.sharkit.nextmonday.configuration.service.SharedPreference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SQLiteTemplate<O> extends SQLiteOpenHelper implements SQLiteRepository<O> {

    public static final String DATABASE_NAME = "NextMonday.db";
    private static final int SCHEMA = 1;

    private static final String TYPE_STRING = "java.lang.String";
    private static final String TYPE_INTEGER = "java.lang.Integer";
    private static final String TYPE_INT = "int";
    private static final String TYPE_BOOLEAN = "java.lang.Boolean";
    private static final String TYPE_BOOL = "boolean";
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

    protected SQLiteTemplate(Context context, Class<O> oClass) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.oClass = oClass;
        userId = (String) SharedPreference.getPreferences(context, SharedPreference.USER_PREFERENCES).getValueShared().get(USER_ID);
        collection = Optional.of(Objects.requireNonNull(oClass.getAnnotation(Collection.class)).collection())
                .orElseThrow(() -> new RuntimeException("Entity is not collection"));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String request = "CREATE TABLE IF NOT EXISTS " +
                Optional.ofNullable(oClass.getAnnotation(Collection.class))
                        .orElseThrow(() -> new RuntimeException("Entity is not collection"))
                        .collection() + "(%s%s)";
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

    @Override
    public void create(O entity) {
        ContentValues data = new ContentValues();
        Arrays.stream(oClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                setContentValues(field, data, entity);
            } catch (IllegalAccessException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        });
        db.insertOrThrow(collection, null, data);
    }

    @Override
    public boolean update(O entity, String id) {
        ContentValues data = new ContentValues();
        Arrays.stream(oClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                setContentValues(field, data, entity);
            } catch (IllegalAccessException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        });
        db.update(collection, data, "id = ?", new String[]{id});
        return true;
    }


    @Override
    public boolean delete(String id) {
        db.execSQL(String.format("DELETE FROM %s WHERE userId = '%s' AND id = '%s';", collection, userId, id));
        return true;
    }

    @Override
    public Optional<O> findById(String id) {
        Arrays.stream(oClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .forEach(field -> cursor = db.rawQuery("SELECT * FROM " + collection
                        + " WHERE " + field.getName() + " = '" + id + "' AND userId = '" + userId + "'", null));

        if (cursor.moveToNext()) {
            return Optional.ofNullable(getObject());
        }

        cursor.close();
        return Optional.empty();
    }

    private boolean updateByQuery(Query annotation, Object... args) {
        String request = String.format("UPDATE %s SET %s AND userId = '%s';",
                collection,
                String.format(String.format("%s WHERE %s", annotation.value(),
                        annotation.condition()),
                        args),
                userId);
        db.execSQL(request);
        return true;
    }

    private List<O> findByQuery(String query, Object... args) {
        String request = String.format("SELECT * FROM %s WHERE userId = '%s' AND %s", collection, userId, String.format(query, args));
        cursor = db.rawQuery(request, null);
        List<O> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            list.add(getObject());
        }
        return list;
    }

    private O getObject() {
        try {
            object = oClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        Arrays.stream(oClass.getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            try {
                getValue(f);
            } catch (IllegalAccessException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        });
        return object;
    }

    protected <K> Optional<List<O>> find(Class<K> repositoryClass, String methodName, Object... args) {
        return Arrays.stream(repositoryClass.getMethods())
                .filter(method -> method.getName().equals(methodName))
                .map(method -> findByQuery(Objects.requireNonNull(method.getAnnotation(Query.class)).condition(), args))
                .findAny();
    }

    protected <K> Optional<Boolean> update(Class<K> repositoryClass, String methodName, Object... args) {
        return Arrays.stream(repositoryClass.getMethods())
                .filter(method -> method.getName().equals(methodName))
                .map(method -> updateByQuery(Objects.requireNonNull(method.getAnnotation(Query.class)), args))
                .findAny();
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
                if (field.getType().getName().equals(TYPE_BOOL) || field.getType().getName().equals(TYPE_BOOLEAN)) {
                    field.set(object, Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(field.getName()))));
                } else {
                    field.set(object, cursor.getString(cursor.getColumnIndex(field.getName())));
                }
                break;
            case FIELD_TYPE_BLOB:
                field.set(object, toObject(cursor.getBlob(cursor.getColumnIndex(field.getName()))));
        }
    }

    private void setContentValues(Field field, ContentValues values, O entity) throws
            IllegalAccessException {
        switch (field.getType().getName()) {
            case TYPE_STRING:
                values.put(field.getName(), (String) field.get(entity));
                break;
            case TYPE_INT:
            case TYPE_INTEGER:
                values.put(field.getName(), (Integer) Optional.ofNullable(field.get(entity)).orElse(null));
                break;
            case TYPE_BOOL:
            case TYPE_BOOLEAN:
                values.put(field.getName(), (Boolean) Optional.ofNullable(field.get(entity)).orElse(false));
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
            case TYPE_BOOL:
            case TYPE_BOOLEAN:
                return SQL_STRING;
            case TYPE_INT:
            case TYPE_INTEGER:
                return SQL_INTEGER;
            default:
                return SQL_BLOB;
        }

    }
}
