package com.demo.stevejobsclassmanaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PostSubjectsActivity extends MenuActivity {

    private RequestQueue requestQueue;
    private EditText etname, ethours;
    private Button btnPost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate( R.layout.activity_post_subjects, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_add_subject);
        etname = findViewById( R.id.etname );
        ethours = findViewById( R.id.ethours );
        btnPost = findViewById( R.id.btnPost );

        btnPost.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonrequestSubjects();
            }
        });
    }

    private void jsonrequestSubjects() {
        JSONObject jsonObject = new JSONObject();
        final String name = etname.getText().toString();
        final String hours = ethours.getText().toString();
        try {
            jsonObject.put( "name", name );
            jsonObject.put( "hours", hours );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue( PostSubjectsActivity.this);

        JsonObjectRequest subject = new JsonObjectRequest( Request.Method.POST, URLs.URL_SUBJECTS, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText( PostSubjectsActivity.this, "Subject added succesfull", Toast.LENGTH_SHORT ).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: ", error.getMessage() );
                error.printStackTrace();
                Toast.makeText( PostSubjectsActivity.this, "Incorrect Params", Toast.LENGTH_SHORT ).show();
            }
        } );
        requestQueue.add( subject );
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

