package com.example.pruebaloginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class activityIniciarSesion extends AppCompatActivity {
    TextView redRegistro;
    Button iniciarSesion;
    EditText correo, contrasenia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        redRegistro = findViewById(R.id.redireccionRegistro);
        correo = findViewById(R.id.correoInicio);
        contrasenia = findViewById(R.id.contraseniaInicio);
        iniciarSesion = findViewById(R.id.btnInicioSesion);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validarCorreo() | !validarContrasenia()){

                }
                else{
                    revisarUsuario();
                }
            }
        });

        redRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityIniciarSesion.this, activityRegistro.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public boolean validarCorreo(){
        String val = correo.getText().toString();
        if(val.isEmpty()){
            correo.setError("El correo no puede estar vacío");
            return false;
        }else{
            correo.setError(null);
            return true;
        }
    }

    public boolean validarContrasenia(){
        String val = contrasenia.getText().toString();
        if(val.isEmpty()){
            contrasenia.setError("La contraseña no puede estar vacia");
            return false;
        }else{
            contrasenia.setError(null);
            return true;
        }
    }

    public void revisarUsuario(){
        String correoElectronico = correo.getText().toString().trim();
        String contraseniaUsuario = contrasenia.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        //Esta parte debe cambiar, no deberia de tener que tomar el nombre de usuario, sino el correo
        Query chequearUsuario = reference.orderByChild("nombreUsuario").equalTo(correoElectronico);

        chequearUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    correo.setError(null);
                    //El error ocurre aca, intenta buscar un "hijo" de correo electronico, pero en la base de datos el "padre" es el nombre de usuario
                    String contraseniaDesdeBD = snapshot.child(correoElectronico).child("contrasenia").getValue(String.class);
                    if( Objects.equals(contraseniaDesdeBD, contraseniaUsuario)){
                        contrasenia.setError(null);
                        Intent intent2 = new Intent(activityIniciarSesion.this, activitySesion.class);
                        startActivity(intent2);
                    }
                    else {
                        Toast.makeText(activityIniciarSesion.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                        contrasenia.requestFocus();
                    }
                }
                else{
                    Toast.makeText(activityIniciarSesion.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                    correo.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}