package com.example.movies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MoviesSQLiteOpenHelper extends SQLiteOpenHelper {

    public MoviesSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(email text PRIMARY KEY ,nombre text,password text,comfirpassword text, saldo text, fecha text)");
        db.execSQL("create table peliculas(titulo text, email text, fecha text, precio text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("drop Table if exists usuarios");
        db.execSQL("drop Table if exists peliculas");
    }

    public boolean checkuserpassword(String email, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where email=? and password=?",new String[] {email, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public String checkname(String email){
        String nombre = "";

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select nombre from usuarios where email=?",new String[] {email});
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(0);
        }
        return nombre;
    }

    public String checksaldo(String email){
        String saldo = "";

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select saldo from usuarios where email=?",new String[] {email});
        if (cursor.moveToFirst()) {
            saldo = cursor.getString(0);
        }
        return saldo;
    }

    public String checkfecha(String email){
        String fecha = "";

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select fecha from usuarios where email=?",new String[] {email});
        if (cursor.moveToFirst()) {
            fecha = cursor.getString(0);
        }
        return fecha;
    }





}
