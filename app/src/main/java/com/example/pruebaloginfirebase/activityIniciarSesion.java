package com.example.pruebaloginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class activityIniciarSesion extends AppCompatActivity {
    TextView redRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        redRegistro = findViewById(R.id.redireccionRegistro);
        redRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityIniciarSesion.this, activityRegistro.class);
                startActivity(intent);
                finish();
            }
        });

    }
}