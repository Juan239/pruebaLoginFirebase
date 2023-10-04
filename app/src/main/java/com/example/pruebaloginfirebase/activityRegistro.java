package com.example.pruebaloginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activityRegistro extends AppCompatActivity {
    EditText nombreUsuario, correo, nombreEmpresa, contrasenia;
    Button botonRegistro;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView redLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreUsuario = findViewById(R.id.nombreUsuarioRegistro);
        correo = findViewById(R.id.correoRegistro);
        nombreEmpresa = findViewById(R.id.nombreEmpresaRegistro);
        contrasenia = findViewById(R.id.contraseniaRegistro);
        botonRegistro = findViewById(R.id.btnRegistrarse);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String username = nombreUsuario.getText().toString();
                String email = correo.getText().toString();
                String companyName = nombreEmpresa.getText().toString();
                String password = contrasenia.getText().toString();

                helperClass helperClass = new helperClass(username, companyName, email, password);
                reference.child(username).setValue(helperClass);


                Toast.makeText(activityRegistro.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
            }
        });



        redLogin = findViewById(R.id.redireccionLogin);
        redLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityRegistro.this, activityIniciarSesion.class);
                startActivity(intent);
                finish();
            }
        });
    }

}