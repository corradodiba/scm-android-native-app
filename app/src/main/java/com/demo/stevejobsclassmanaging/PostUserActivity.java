package com.demo.stevejobsclassmanaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class PostUserActivity extends MenuActivity {

    private RequestQueue requestQueue;
    private EditText names, surname, dateOfBirth, fiscalCode, type, email, password;
    private Button btnPostUser;
    private SharedPreferences sharedpreferences;
    private static final  String SHARED_PREF_NAME = "stevejobsclassmanaging";
    private static final  String KEY_TOKEN = "Keytoken";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_post_user, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_add_user);

        names = findViewById( R.id.etnameUser );
        surname = findViewById( R.id.etsurnamUser );
        dateOfBirth = findViewById( R.id.etdteofbirthUser );
        fiscalCode = findViewById( R.id.etfisclcodeUser );
        type = findViewById( R.id.ettypeUser );
        email = findViewById( R.id.etemailUser );
        password = findViewById( R.id.etpasswordUser );
        btnPostUser = findViewById( R.id.btnPostUser );

        btnPostUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonrequestSignup();
            }
        });
    }

    private void jsonrequestSignup() {

        JSONObject jsonObject = new JSONObject();
        final String emails = email.getText().toString();
        final String passwords = password.getText().toString();
        final String fiscalCodes = fiscalCode.getText().toString();
        final String namess = names.getText().toString();
        final String surnames = surname.getText().toString();
        final String dateOfBirths = dateOfBirth.getText().toString();
        final String types = type.getText().toString();
        try {
            jsonObject.put( "email", emails );
            jsonObject.put( "password", passwords );
            jsonObject.put( "fiscalCode", fiscalCodes );
            jsonObject.put( "name", namess );
            jsonObject.put( "surname", surnames );
            jsonObject.put( "dateOfBirth", dateOfBirths );
            jsonObject.put( "type", types );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue( PostUserActivity.this);

        JsonObjectRequest user = new JsonObjectRequest( Request.Method.POST, URLs.URL_SIGNUP, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText( PostUserActivity.this, "User added succesfull", Toast.LENGTH_SHORT ).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: ", error.getMessage() );
                error.printStackTrace();
                Toast.makeText( PostUserActivity.this, "Incorrect Params", Toast.LENGTH_SHORT ).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                sharedpreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String token = sharedpreferences.getString( KEY_TOKEN, null );
                params.put("Authorization", token);
                return params;
            }};
        requestQueue.add( user );
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen( GravityCompat.START)) {
            drawer.closeDrawer( GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }
}

