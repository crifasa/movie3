package com.example.movies;

import static com.example.movies.Utils.getDate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DetallesPelis extends AppCompatActivity {

    private ImageView MovieThumbnailImg, MovieCoverImg;
    private TextView tv_title, tv_description;
    String titulo, email;
    Date fecha;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pelis);
        iniViews();

        tv_title = findViewById(R.id.et6);
        tv_description = findViewById(R.id.et5);
        email = getIntent().getStringExtra("email");


    }

    void iniViews() {

        String movieTitle = getIntent().getExtras().getString("title");
        int imageResourceId = getIntent().getExtras().getInt("imgURL");
        MovieThumbnailImg = findViewById(R.id.imageView3);
        Glide.with(this).load(imageResourceId).into(MovieThumbnailImg);
        MovieThumbnailImg.setImageResource(imageResourceId);
        tv_title = findViewById(R.id.et6);
        tv_title.setText(movieTitle);
        tv_description = findViewById(R.id.et5);


    }

    public void comprar(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(DetallesPelis.this);
        alerta.setMessage("¿Desea comprar esta pelicula por $250.000?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MoviesSQLiteOpenHelper movies = new MoviesSQLiteOpenHelper(DetallesPelis.this, "movie", null, 1);
                        int saldo = Integer.parseInt(movies.checksaldo(email));

                        if (saldo >= 25000) {
                            int saldofinal = saldo - 250000;
                            SQLiteDatabase db = movies.getWritableDatabase();
                            ContentValues registro = new ContentValues();
                            registro.put("titulo", tv_title.getText().toString());
                            registro.put("email", email);
                            registro.put("fecha", getDate());
                            registro.put("precio", 250000);
                            db.insert("peliculas", null, registro);
                            db.close();

                            SQLiteDatabase bd = movies.getWritableDatabase();
                            ContentValues contsaldo = new ContentValues();
                            contsaldo.put("saldo", String.valueOf(saldofinal));
                            bd.update("usuarios", contsaldo, "email=?", new String[]{email});
                            bd.close();

                            Intent intent = new Intent(DetallesPelis.this, PaginaPelis.class);
                            intent.putExtra("email", email);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                            Toast.makeText(DetallesPelis.this, "Compra Exitosa!!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DetallesPelis.this, "saldo insuficiente", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alerta.create();
        alerta.show();


    }

    public List<Pelis> mostrarPeliculas() {
        List<Pelis> peliculas = new ArrayList<>();
        return peliculas;
    }

    public void atras(View view) {
        finish();
    }


}