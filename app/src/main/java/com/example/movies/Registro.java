package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;
import static com.example.movies.Utils.getDate;
import java.util.Objects;


import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    private EditText et1, et2, et3, et4;
    String nombre, email, password, comfirpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);


    }

    public void confirmar(View view) {
        nombre = et4.getText().toString().trim();
        email = et1.getText().toString().trim();
        password = et2.getText().toString().trim();
        comfirpassword = et3.getText().toString().trim();



        MoviesSQLiteOpenHelper movies = new MoviesSQLiteOpenHelper(Registro.this, "movie", null, 1);
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            Toast.makeText(Registro.this, "llene todos los campos", Toast.LENGTH_SHORT).show();
        else {
        SQLiteDatabase bd = movies.getWritableDatabase();
        Cursor cursor = bd.rawQuery("select email from usuarios where email=?", new String[]{email});
        if (cursor.moveToFirst()) {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Objects.equals(password, comfirpassword)) {
            Toast.makeText(this, "las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues registro = new ContentValues();

        registro.put("email", email);
        registro.put("nombre", nombre);
        registro.put("password", password);
        registro.put("saldo", 1000000);
        registro.put("comfirpassword", comfirpassword);
        registro.put("fecha", getDate());
        bd.insert("usuarios", null, registro);

        bd.close();

        if (validar()) {
            Toast.makeText(this, "Se realizo el registro con éxito", Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            finish();
        }

        }

    }



    public boolean validar() {

        if (nombre.isEmpty()) {
            et4.setError("campo obligatorio");
            return false;
        }
        if (email.isEmpty()) {
            et1.setError("campo obligatorio");
            return false;
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            et1.setError("escriba un correo valido");
            return false;
        }

        if (password.isEmpty()) {
            et2.setError("campo obligatorio");
            return false;
        }

        if (comfirpassword.isEmpty()) {
            et3.setError("campo obligatorio");
            return false;
        }


        return true;

    }

    public void atras(View view) {
        finish();
    }


}