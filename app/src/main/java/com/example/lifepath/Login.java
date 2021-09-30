package com.example.lifepath;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registro= (TextView) findViewById(R.id.registrobotton);
        final EditText usuarioT=(EditText)findViewById(R.id.user_login);
        final EditText claveT=(EditText)findViewById(R.id.clave_login);

        Button btnLogin = (Button) findViewById(R.id.loginBotton);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login.this, registro.class);
                Login.this.startActivity(registro);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usuario=usuarioT.getText().toString();
                final String clave=claveT.getText().toString();
                Response.Listener<String> respuesta =new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok= jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                String nombre = jsonRespuesta.getString("nombre");
                                int edad = jsonRespuesta.getInt("edad");
                                Intent i = new Intent(Login.this, Vacio.class);
                                Login.this.startActivity(i);
                                Login.this.finish();
                            }
                            else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Fallo en el Login")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
            }
        });
    }
}