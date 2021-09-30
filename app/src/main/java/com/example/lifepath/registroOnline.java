package com.example.lifepath;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class registroOnline extends StringRequest {
    private static final String ruta="https://lifepathdiscordia.000webhostapp.com/registro.php";
    private Map<String, String> parametros;
    public registroOnline(String name, String usuario, String clave, int edad, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("nombre", name+"");
        parametros.put("usuario", usuario+"");
        parametros.put("clave", clave+"");
        parametros.put("edad", edad+"");
    }

    protected Map<String, String> getParams() {
        return parametros;
    }
}
