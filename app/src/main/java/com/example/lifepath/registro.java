package com.example.lifepath;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
        btnResgistro.setOnClickListener(view -> {
            String name= nombre.getText().toString();
            String user= usuario.getText().toString();
            String clave= contrasena.getText().toString();
            int old= Integer.parseInt(edad.getText().toString());
            Response.Listener<String> respuesta=new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("pasotelo");
                    System.out.println(response);
                    try{
                        JSONObject jsonRespuesta = new JSONObject(response);
                        boolean ok= jsonRespuesta.getBoolean("success");

                        if(ok){
                            Intent i = new Intent(registro.this, Login.class);
                            registro.this.startActivity(i);
                            registro.this.finish();
                        }
                        else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(registro.this);
                            alerta.setMessage("Fallo en el registro")
                                    .setNegativeButton("Reintentar", null)
                                    .create()
                                    .show();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            registroOnline r= new registroOnline(name,user,clave,old,respuesta);
            RequestQueue cola = Volley.newRequestQueue(registro.this);
            cola.add(r);


        });

    }
}