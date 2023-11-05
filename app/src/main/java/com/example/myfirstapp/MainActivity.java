package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        llamarAPIGenero("https://api.genderize.io/?name=nombre_a_buscar" + message, Request.Method.GET);

        llamarAPIEdad("https://api.agify.io/?name=nombre_a_buscar" + message, Request.Method.GET);
    }

    private void llamarAPIGenero(String url, int httpVerb) {
        EditText campoTextoGenero = (EditText) findViewById(R.id.editTextText2);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(httpVerb, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject reader = new JSONObject(response);
                            String genero = reader.getString("gender");

                            if (genero.equals("male")) {
                                genero = "hombre";
                            } else {
                                genero = "mujero";
                            }
                            campoTextoGenero.setText(genero);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                campoTextoGenero.setText("ocurrio un error");
            }
        });
    }
}

