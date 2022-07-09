package com.example.amstlab3grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IngresarDatos extends AppCompatActivity {
    Button btn_envio;
    private RequestQueue mQueue;
    private String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_datos);
        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");
        btn_envio = (Button)findViewById(R.id.btn_enviar);
        btn_envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirData();
            }
        });
    }
    private void subirData(){
        final EditText temp = (EditText) findViewById(R.id.in_temp);
        final EditText hum = (EditText) findViewById(R.id.in_hum);
        final EditText peso = (EditText) findViewById(R.id.in_peso);
        String str_temp = temp.getText().toString();
        String str_hum = hum.getText().toString();
        String str_peso = peso.getText().toString();

        Map<String, String> params2 = new HashMap();
        params2.put("temperatura", str_temp);
        params2.put("humedad", str_hum);
        params2.put("peso", str_peso);
        JSONObject parametros = new JSONObject(params2);
        String login_url = "https://amst-labx.herokuapp.com/api/sensores";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, login_url, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            "Datos enviados exitosamente",
                                            Toast.LENGTH_SHORT);
                            toast1.show();
                        } catch (Exception e) {
                            Toast toast2 =
                                    Toast.makeText(getApplicationContext(),
                                            "Datos no enviados",
                                            Toast.LENGTH_SHORT);
                            toast2.show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast3 =
                        Toast.makeText(getApplicationContext(),
                                "Error en Volley",
                                Toast.LENGTH_SHORT);
                toast3.show();
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError
        {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Authorization", "JWT " + token);
            System.out.println(token);
            return params;
        }
        };
        mQueue.add(request);
    }
}