package com.example.contactos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText nombre,materia,teléfono,correo,turl;
    Button btnAñadir,btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nombre = (EditText)findViewById(R.id.txtNombre);
        materia = (EditText)findViewById(R.id.txtMateria);
        teléfono = (EditText)findViewById(R.id.txtTelefono);
        correo = (EditText)findViewById(R.id.txtCorreo);
        turl = (EditText)findViewById(R.id.txtUrlImagen);

        btnAñadir = (Button)findViewById(R.id.btnAñadir);
        btnRegresar = (Button)findViewById(R.id.btnRegresar);

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserData();
                clearAll();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inserData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("nombre",nombre.getText().toString());
        map.put("materia",materia.getText().toString());
        map.put("teléfono",teléfono.getText().toString());
        map.put("correo",correo.getText().toString());
        map.put("turl",turl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("docentes").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Datos insertados exitosamente.",Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result_key", "Hello from SecondActivity!");
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Error al insertar.",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearAll()
    {
        nombre.setText("");
        materia.setText("");
        teléfono.setText("");
        correo.setText("");
        turl.setText("");
    }
}