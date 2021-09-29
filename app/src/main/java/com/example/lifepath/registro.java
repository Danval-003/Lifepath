package com.example.lifepath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        TextView nombre = (EditText) findViewById(R.id.name_registro);
        TextView usuario = (EditText) findViewById(R.id.user_registro);
        TextView contrasena = (EditText) findViewById(R.id.clave_registro);
        TextView edad = (EditText) findViewById(R.id.edad_registro);
        Button btnResgistro = (Button) findViewById(R.id.resgistrobott);
        btnResgistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= nombre.getText().toString();
                String user= usuario.getText().toString();
                String clave= contrasena.getText().toString();
                int old= Integer.parseInt(edad.getText().toString());


            }
        });

    }
}