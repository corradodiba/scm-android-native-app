package com.demo.stevejobsclassmanaging;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.demo.stevejobsclassmanaging.model.URLs;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

        EditText email, password;
        Button login;
        TextView register;
        boolean isEmailValid, isPasswordValid;
        TextInputLayout emailError, passError;
        private RequestQueue requestQueue;
        private SharedPreferences sharedpreferences;
        private static final  String SHARED_PREF_NAME = "stevejobsclassmanaging";
        private static final  String KEY_TOKEN = "Keytoken";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            login = findViewById(R.id.login);
            register = findViewById(R.id.register);
            emailError = findViewById(R.id.emailError);
            passError = findViewById(R.id.passError);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   LoginValidation();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }
        public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

        public void LoginValidation() {

            if (email.getText().toString().isEmpty()) {
                emailError.setError(getResources().getString(R.string.email_error));
                isEmailValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                emailError.setError(getResources().getString(R.string.error_invalid_email));
                isEmailValid = false;
            } else  {
                isEmailValid = true;
                emailError.setErrorEnabled(false);
            }

            if (password.getText().toString().isEmpty()) {
                passError.setError(getResources().getString(R.string.password_error));
                isPasswordValid = false;
            } else if (password.getText().length() < 3) {
                passError.setError(getResources().getString(R.string.error_invalid_password));
                isPasswordValid = false;
            } else {
                isPasswordValid = true;
                passError.setErrorEnabled(false);
            }

            if (isEmailValid  && isPasswordValid) {
                jsonrequestSubjects();
            }
        }
        private void jsonrequestSubjects() {

        JSONObject jsonObject = new JSONObject();
        final String emails = email.getText().toString();
        final String passwords = password.getText().toString();
        try {
            jsonObject.put( "email", emails );
            jsonObject.put( "password", passwords );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue( Login.this);

        JsonObjectRequest subject = new JsonObjectRequest( Request.Method.POST, URLs.URL_LOGIN, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject josnOBJ = response;

                        String token = null;
                        try {
                            token = josnOBJ.getString( "token" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sharedpreferences = getSharedPreferences( SHARED_PREF_NAME, MODE_PRIVATE );
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString( KEY_TOKEN, token );
                        editor.apply();
                        Toast.makeText( Login.this, "Login Succesfully", Toast.LENGTH_SHORT ).show();
                        final Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: ", error.getMessage() );
                error.printStackTrace();
                Toast.makeText( Login.this, "Incorrect credentials", Toast.LENGTH_SHORT ).show();
            }
        } );
        requestQueue.add( subject );
    }
}