package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;
import static com.example.movies.Utils.getDate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    private EditText et1, et2;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
    }

    public void iniciar(View view) {

        email = et1.getText().toString().trim();
        password = et2.getText().toString().trim();

        MoviesSQLiteOpenHelper movies = new MoviesSQLiteOpenHelper(this, "movie", null, 1);


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            Toast.makeText(Principal.this, "llene todos los campos", Toast.LENGTH_SHORT).show();
        else {
            Boolean checkuserpass = movies.checkuserpassword(email, password);
            if (checkuserpass == true) {
                Toast.makeText(Principal.this, "logueo exitoso", Toast.LENGTH_SHORT).show();
                String fechaActual = getDate();

                String fechaguardada = movies.checkfecha(email);
                System.out.println("DIEGO ---> fechaActual "+fechaActual);
                System.out.println("DIEGO ---> fechaguardada "+fechaguardada);

                if (!fechaguardada.equals(fechaActual)){
                    SQLiteDatabase bd = movies.getWritableDatabase();
                    ContentValues newSaldo = new ContentValues();
                    newSaldo.put("saldo", "1000000");
                    bd.update("usuarios", newSaldo, "email=?", new String[]{email});

                }
                if (!fechaguardada.equals(fechaActual)){
                    SQLiteDatabase bd = movies.getWritableDatabase();
                    ContentValues newFecha = new ContentValues();
                    newFecha.put("fecha", fechaActual);
                    bd.update("usuarios", newFecha, "email=?", new String[]{email});

                }

                startActivity(new Intent(getApplicationContext(), PaginaPelis.class).putExtra("email", email));

            } else {
                Toast.makeText(Principal.this, "email, nombre o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
            if (validar()){
            et1.setText("");
            et2.setText("");

            }
        }


    }

    public boolean validar() {
        boolean retorno = true;

        String email = et1.getText().toString();
        String nombre = et1.getText().toString();
        String password = et2.getText().toString();


        if (email.isEmpty()) {
            et1.setError("campo obligatorio");
            retorno = false;
        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            et1.setError("escriba un correo valido");
            return false;
        }


        if (password.isEmpty()) {
            et2.setError("campo obligatorio");
            retorno = false;
        }


        return retorno;

    }


    public void registrar(View view) {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }
}