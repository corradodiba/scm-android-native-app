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

public class PostCourseActivity extends MenuActivity {

    private RequestQueue requestQueue;
    private EditText etname, etstatus, etyear;
    private Button btnPostCourse;
    private SharedPreferences sharedpreferences;
    private static final  String SHARED_PREF_NAME = "stevejobsclassmanaging";
    private static final  String KEY_TOKEN = "Keytoken";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_post_course, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_add_course);

        etname = findViewById( R.id.etname );
        etstatus = findViewById( R.id.etstatus );
        etyear = findViewById( R.id.etyear );
        btnPostCourse = findViewById( R.id.btnPost );

        btnPostCourse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonrequestCourse();
            }
        });
    }

    private void jsonrequestCourse() {
        JSONObject jsonObject = new JSONObject();
        final String name = etname.getText().toString();
        final String status = etstatus.getText().toString();
        final String year = etyear.getText().toString();
        try {
            jsonObject.put( "name", name );
            jsonObject.put( "status", status);
            jsonObject.put( "year", year);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue( PostCourseActivity.this);

        JsonObjectRequest course = new JsonObjectRequest( Request.Method.POST, URLs.URL_COURSES, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText( PostCourseActivity.this, "Course added succesfull", Toast.LENGTH_SHORT ).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: ", error.getMessage() );
                error.printStackTrace();
                Toast.makeText( PostCourseActivity.this, "Incorrect Params", Toast.LENGTH_SHORT ).show();
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
        requestQueue.add( course );
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

