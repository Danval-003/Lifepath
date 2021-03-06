package com.example.lifepath;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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
        btnLogin.setOnClickListener(view -> {
            final String usuario=usuarioT.getText().toString();
            final String clave=claveT.getText().toString();
            System.out.println("pasote");
            Response.Listener<String> respuesta =new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("pasote0");
                    System.out.println(response);
                    try{
                        JSONObject jsonRespuesta = new JSONObject(response);
                        boolean ok= jsonRespuesta.getBoolean("success");
                        if(ok==true){
                            String nombre = jsonRespuesta.getString("nombre");
                            int edad = jsonRespuesta.getInt("edad");
                            Intent i = new Intent(Login.this, Menu.class);
                            i.putExtra("nombre", nombre);
                            i.putExtra("edad", edad+"");
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
                        e.printStackTrace();
                    }
                }
            };
            LoginOnline r= new LoginOnline(usuario,clave,respuesta);
            RequestQueue cola = Volley.newRequestQueue(Login.this);
            cola.add(r);
        });
    }
}