package com.example.lifepath;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginOnline extends StringRequest {
    private static final String ruta="https://lifepathdiscordia.000webhostapp.com/login.php";
    private Map<String, String> parametros;
    public LoginOnline(String usuario, String clave, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("usuario", usuario+"");
        parametros.put("clave", clave+"");
    }

    protected Map<String, String> getParams() {
        return parametros;
    }
}
