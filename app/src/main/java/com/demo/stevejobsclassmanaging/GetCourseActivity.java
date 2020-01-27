package com.demo.stevejobsclassmanaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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
import com.demo.stevejobsclassmanaging.adapters.RecyclerViewAdapterCourse;
import com.demo.stevejobsclassmanaging.model.Course;
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


public class GetCourseActivity extends MenuActivity {

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Course> lstCourse;
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
        View contentView = inflater.inflate( R.layout.activity_get_course, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_view_courses_list);
        lstCourse = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewidCourse);
        jsonrequestCourse();
        bar = findViewById(R.id.progressbar);
        bar.setVisibility(VISIBLE);
    }

    private void jsonrequestCourse() {

        request = new JsonArrayRequest(  Request.Method.GET, URLs.URL_COURSES, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {


                    try {
                        jsonObject = response.getJSONObject(i);
                        Course course = new Course();
                        course.setStatus(jsonObject.getString("status"));
                        course.setName(jsonObject.getString("name"));
                        course.setYear(jsonObject.getString("year"));
                        lstCourse.add( course );
                        bar.setVisibility(GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                setuprecyclerview( lstCourse );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: ", error.getMessage() );
                error.printStackTrace();
                Toast.makeText( GetCourseActivity.this, "Network failure", Toast.LENGTH_SHORT).show();
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
        requestQueue = Volley.newRequestQueue( GetCourseActivity.this);
        requestQueue.add(request);
    }
    private void setuprecyclerview(List<Course> lstCourse) {
        RecyclerViewAdapterCourse myadapter = new RecyclerViewAdapterCourse(this, lstCourse );
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