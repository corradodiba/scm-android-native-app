package com.demo.stevejobsclassmanaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.demo.stevejobsclassmanaging.adapters.RecyclerViewAdapterSubject;
import com.demo.stevejobsclassmanaging.model.Subject;
import com.demo.stevejobsclassmanaging.model.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GetSubjectActivity extends MenuActivity {

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Subject> lstSubjects;
    private RecyclerView recyclerView;
    private ProgressBar bar;
    private SharedPreferences sharedpreferences;
    private static final  String SHARED_PREF_NAME = "stevejobsclassmanaging";
    private static final  String KEY_TOKEN = "Keytoken";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_get_subject, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_view_subject_list);
        lstSubjects = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewidSubjects);
        jsonrequestSubjects();
        bar = findViewById(R.id.progressbar);
        bar.setVisibility(VISIBLE);
    }

    private void jsonrequestSubjects() {

        request = new JsonArrayRequest(  Request.Method.GET, URLs.URL_SUBJECTS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        Subject subject = new Subject();
                        subject.set_id(jsonObject.getString("id"));
                        subject.setName(jsonObject.getString("name"));
                        subject.setHours(jsonObject.getString("hours"));
                        lstSubjects.add( subject );
                        bar.setVisibility(GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview( lstSubjects );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: ", error.getMessage() );
                error.printStackTrace();
                Toast.makeText( GetSubjectActivity.this, "Network failure", Toast.LENGTH_SHORT).show();
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

        requestQueue = Volley.newRequestQueue( GetSubjectActivity.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<Subject> lstSubjects) {

        RecyclerViewAdapterSubject myadapter = new RecyclerViewAdapterSubject(this, lstSubjects );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
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
