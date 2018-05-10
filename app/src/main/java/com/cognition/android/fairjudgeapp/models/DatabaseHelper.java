package com.cognition.android.fairjudgeapp.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "fair_judge_app_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Establishment.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", Establishment.TABLE_NAME));

        onCreate(db);
    }

    public long insertEstablishment(Establishment establishment) {
        SQLiteDatabase db = DatabaseHelper.this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Establishment.COLUMN_REVIEWER_ID, establishment.getReviewerId());
        values.put(Establishment.COLUMN_ESTABLISHMENT_NAME, establishment.getEstablishmentName());
        values.put(Establishment.COLUMN_ESTABLISHMENT_TYPE, establishment.getEstablishmentType());
        values.put(Establishment.COLUMN_FOOD_TYPE, establishment.getFoodType());
        values.put(Establishment.COLUMN_LOCATION, establishment.getLocation());
        values.put(Establishment.COLUMN_RATING, establishment.getRating());

        long id = db.insert(Establishment.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public Establishment getEstablishment(long id) {
        SQLiteDatabase db = DatabaseHelper.this.getReadableDatabase();

        Cursor cursor = db.query(
                Establishment.TABLE_NAME,
                Establishment.getAllColumns(),
                String.format("%s=?", Establishment.COLUMN_ID),
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Establishment establishment = new Establishment(
                cursor.getInt(cursor.getColumnIndex(Establishment.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_REVIEWER_ID)),
                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_ESTABLISHMENT_NAME)),
                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_ESTABLISHMENT_TYPE)),
                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_FOOD_TYPE)),
                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_LOCATION)),
                cursor.getInt(cursor.getColumnIndex(Establishment.COLUMN_RATING)),
                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_TIMESTAMP))
        );

        cursor.close();
        db.close();

        return establishment;
    }

    public List<Establishment> getAllEstablishments() {
        List<Establishment> establishments;

        SQLiteDatabase db = DatabaseHelper.this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Establishment.SELECT_ALL, null);

        if (cursor != null)
            establishments = new ArrayList<>();
        else
            return null;

        if (cursor.moveToFirst())
            do {
                establishments.add(
                        new Establishment(
                                cursor.getInt(cursor.getColumnIndex(Establishment.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_REVIEWER_ID)),
                                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_ESTABLISHMENT_NAME)),
                                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_ESTABLISHMENT_TYPE)),
                                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_FOOD_TYPE)),
                                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_LOCATION)),
                                cursor.getInt(cursor.getColumnIndex(Establishment.COLUMN_RATING)),
                                cursor.getString(cursor.getColumnIndex(Establishment.COLUMN_TIMESTAMP))
                        )
                );
            } while (cursor.moveToNext());

        cursor.close();
        db.close();

        return establishments;
    }

    public int getEstablishmentsCount() {
        int count;

        SQLiteDatabase db = DatabaseHelper.this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Establishment.SELECT_ALL, null);

        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        } else
            count = 0;

        db.close();

        return count;
    }

    public void deleteEstablishment(int id) {
        SQLiteDatabase db = DatabaseHelper.this.getWritableDatabase();
        db.delete(
                Establishment.TABLE_NAME,
                String.format("%s=?", Establishment.COLUMN_ID),
                new String[]{String.valueOf(id)}
        );
        db.close();
    }
}
