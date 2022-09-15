package com.example.movies;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PaginaPelis extends AppCompatActivity implements MovieItemClick{
    Toolbar ab;
    TextView tv1, tv2;

    private List<Slide> lstSlides;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView rv1;
    Button bt2;
    String email;
    MoviesSQLiteOpenHelper movie;
    BroadcastReceiver receiver;
    IntentFilter filter;
    String nombre, saldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_pelis);

        movie = new MoviesSQLiteOpenHelper(this, "usuarios", null, 1);
        sliderpager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        rv1 = findViewById(R.id.rv1);
        bt2 = findViewById(R.id.bt2);
        email = getIntent().getStringExtra("email");
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaginaPelis.this, Lista.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        lstSlides = new ArrayList<>();
        lstSlides.add(new Slide(R.drawable.slide1, "THE AVENGERS  /AÑO: 2012"));
        lstSlides.add(new Slide(R.drawable.slide2, "THE AVENGERS AGE OF ULTRON /AÑO: 2015"));
        lstSlides.add(new Slide(R.drawable.slide3, "INFINITY WAR  /AÑO: 2018"));
        lstSlides.add(new Slide(R.drawable.slide4, "END GAME  /AÑO: 2019"));
        lstSlides.add(new Slide(R.drawable.slide5, "IRON MAN  /AÑO: 2007"));
        lstSlides.add(new Slide(R.drawable.slide6, "IRON MAN 2  /AÑO: 2010"));
        lstSlides.add(new Slide(R.drawable.slide7, "IRON MAN 3 /AÑO: 2013"));

        SliderPagerAdapter adapter = new SliderPagerAdapter(this,lstSlides);

        sliderpager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PaginaPelis.SliderTimer(), 4000, 6000);

        indicator.setupWithViewPager(sliderpager, true);

        List<Pelis> lstMovies = new ArrayList<>();
        lstMovies.add(new Pelis("THE AVENGERS", R.drawable.slide1));
        lstMovies.add(new Pelis("THE AVENGERS AGE OF ULTRON", R.drawable.slide2));
        lstMovies.add(new Pelis("THE AVENGERS INFINITY WAR", R.drawable.slide3));
        lstMovies.add(new Pelis("THE AVENGERS END GAME", R.drawable.slide4));
        lstMovies.add(new Pelis("IRON MAN", R.drawable.slide5));
        lstMovies.add(new Pelis("IRON MAN 2", R.drawable.slide6));
        lstMovies.add(new Pelis("IRON MAN 3", R.drawable.slide7));

        MovieAdapter movieAdapter = new MovieAdapter(this, lstMovies,this);
        rv1.setAdapter(movieAdapter);
        rv1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ab = findViewById(R.id.ab);
        setSupportActionBar(ab);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        MoviesSQLiteOpenHelper movies = new MoviesSQLiteOpenHelper(this, "movie", null, 1);
        nombre = movie.checkname(email);
        String saldo = movies.checksaldo(email);
        tv1.setText("Bienvenido "+ nombre);
        tv2.setText("Su saldo es:"+ saldo);



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        MoviesSQLiteOpenHelper movies = new MoviesSQLiteOpenHelper(this, "movie", null, 1);
         nombre = movie.checkname(email);
        String saldo = movies.checksaldo(email);
        tv1.setText("Bienvenido "+ nombre);
        tv2.setText("Su saldo es:"+ saldo);
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("API123",""+intent.getAction());
                MoviesSQLiteOpenHelper movies = new  MoviesSQLiteOpenHelper(PaginaPelis.this, "movie", null, 1);
               SQLiteDatabase bd = movies.getWritableDatabase();
                ContentValues consaldo = new ContentValues();
                consaldo.put("saldo", "1000000");
                bd.update("usuarios", consaldo, "email=?", new String[]{email});
                Toast.makeText(PaginaPelis.this, "Saldo actualizado!!", Toast.LENGTH_SHORT).show();
                tv2.setText("Su saldo es:"+ saldo);
                unregisterReceiver(receiver);
                onResume();
            }
        };
        registerReceiver(receiver, filter);
    }*/

    @Override
    public void onMovieClick(Pelis pelis, ImageView movieImageView) {

        Intent intent = new Intent(this, DetallesPelis.class);

        intent.putExtra("title", pelis.getTitle());
        intent.putExtra("imgURL",pelis.getThumbnail());
        intent.putExtra("description",pelis.getDescription());
        intent.putExtra("email", email);
        startActivity(intent);

        ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(PaginaPelis.this, movieImageView, "sharedNme");


        Toast.makeText(this,"Pelicula : " + pelis.getTitle(),Toast.LENGTH_LONG).show();

    }


    class SliderTimer extends TimerTask{

        @Override
        public void run() {

            PaginaPelis.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<lstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });

        }
    }



    public void cerrar (View view){
        AlertDialog.Builder alerta = new AlertDialog.Builder(PaginaPelis.this);
        alerta.setMessage("¿Desea cerrar sesion?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(PaginaPelis.this, "Vuelve pronto", Toast.LENGTH_SHORT).show();
                    }
                })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alerta.create();
        alerta.show();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}