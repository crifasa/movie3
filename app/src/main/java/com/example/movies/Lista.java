package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class Lista extends AppCompatActivity {
    TextView tv1;
    RecyclerView lv1;
    ArrayList<Pelis> pelisArrayList;

    SQLiteOpenHelper movies;
    private PelisAdapter pelisAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        tv1 = findViewById(R.id.tv1);
        lv1 = (RecyclerView) findViewById(R.id.lv1);

        movies = new MoviesSQLiteOpenHelper(this, "movie", null, 1);

        pelisAdapter = new PelisAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.lv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pelisAdapter);
        mostardatos(getIntent().getStringExtra("email"));

    }


    public void mostardatos(String email) {

        SQLiteDatabase sqLiteDatabase =
                movies.getReadableDatabase();
        Pelis pelis = null;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from peliculas where email=?", new String[]{email}, null);
        while (cursor.moveToNext()) {
            pelis = new Pelis();
            pelis.setTitle(cursor.getString(0));
            pelis.setEmail(cursor.getString(1));
            pelis.setFecha(cursor.getString(2));
            pelis.setPrecio(cursor.getString(3));

            pelisAdapter.agregarPelicula(pelis);
        }

    }

    public void atras(View view) {
        finish();
    }

}