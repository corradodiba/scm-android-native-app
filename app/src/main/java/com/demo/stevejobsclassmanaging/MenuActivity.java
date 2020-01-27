package com.demo.stevejobsclassmanaging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    private ImageView github, slack, instagram, facebook, twitter, linkedin, home;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu );
        github = findViewById( R.id.github );
        slack = findViewById( R.id.slack );
        instagram = findViewById( R.id.instagram );
        facebook = findViewById( R.id.facebook );
        twitter = findViewById( R.id.twitter );
        linkedin = findViewById( R.id.linkedin );
        home = findViewById( R.id.home2 );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        github.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/corradodiba/stevejobs-class-managing")));
            }
        });
        slack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://steve-jobs-academy.slack.com/?redir=%2Farchives%2FGQRS4582H")));
            }
        });
        instagram.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.instagram.com/stevejobsacademy/")));
            }
        });
        facebook.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/stevejobsacademy/")));
            }
        });
        twitter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/sjobsacademy")));
            }
        });
        linkedin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/school/fits-steve-jobs/?originalSubdomain=it")));
            }
        });
        home.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimatedActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startAnimatedActivity(new Intent(getApplicationContext(), HomeActivity.class));
        } else if (id == R.id.nav_add_course) {
            startAnimatedActivity(new Intent(getApplicationContext(), PostCourseActivity.class));
        } else if (id == R.id.nav_view_courses_list) {
            startAnimatedActivity(new Intent(getApplicationContext(), GetCourseActivity.class));
        } else if (id == R.id.nav_add_user) {
            startAnimatedActivity(new Intent(getApplicationContext(), PostUserActivity.class));
        } else if (id == R.id.nav_view_users_list) {
            startAnimatedActivity(new Intent(getApplicationContext(), GetUsersActivity.class));
        } else if (id == R.id.nav_view_teacher_list) {
            startAnimatedActivity(new Intent(getApplicationContext(), GetTeacherActivity.class));
        } else if (id == R.id.nav_view_student_list) {
            startAnimatedActivity(new Intent(getApplicationContext(), GetStudentActivity.class));
        } else if (id == R.id.nav_view_admin_list) {
            startAnimatedActivity(new Intent(getApplicationContext(), GetAdminActivity.class));
        } else if (id == R.id.nav_view_subject_list) {
            startAnimatedActivity(new Intent(getApplicationContext(), GetSubjectActivity.class));
        } else if (id == R.id.nav_add_subject) {
            startAnimatedActivity(new Intent(getApplicationContext(), PostSubjectsActivity.class));
        } else if (id == R.id.nav_logout) {
            startAnimatedActivity(new Intent(getApplicationContext(), Login.class));
        } else if (id == R.id.nav_website) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://stevejobs.academy")));
        } else if (id == R.id.nav_github) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/corradodiba/stevejobs-class-managing")));
        } else if (id == R.id.nav_slack) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://steve-jobs-academy.slack.com/?redir=%2Farchives%2FGQRS4582H")));
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }

    public void startAnimatedActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
