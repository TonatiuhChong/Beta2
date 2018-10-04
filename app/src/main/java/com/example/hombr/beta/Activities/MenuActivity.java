package com.example.hombr.beta.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hombr.beta.ConfiguracionActivity;
import com.example.hombr.beta.Fragments.ControlFragment;
import com.example.hombr.beta.Fragments.Recon2Fragment;
import com.example.hombr.beta.Fragments.ReconFragment;
import com.example.hombr.beta.Fragments.SensorFragment;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private TextView Usuario, Email;
    private ImageView Fusuario;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        LinearLayout nave = (LinearLayout) hView.findViewById(R.id.Wall);


        Usuario = (TextView) hView.findViewById(R.id.TxtUsuario);
        Email = (TextView) hView.findViewById(R.id.TxtEmail);
        Fusuario = (ImageView) hView.findViewById(R.id.Fusuario);

        Usuario.setText(Singleton.getInstance().getUser());
        Email.setText(Singleton.getInstance().getEmail());
        //Glide.with(this).load(Singleton.getInstance().getFoto()).into(Fusuario);
        Glide.with(this).load(Singleton.getInstance().getFoto()).apply(RequestOptions.circleCropTransform()).into(Fusuario);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        tx.replace(R.id.escenario,  new ControlFragment());
        tx.commit();


        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);


        if (currentHour < 7) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.night));
        }
        if (currentHour < 12 & currentHour > 9) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.sunny));

        }
        if (currentHour < 19 & currentHour > 12) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.afternoon));
        }
        if (currentHour > 19) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.night));
        }
        if (currentHour > 7 & currentHour < 9) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.morning));

        }
        else nave.setBackground(this.getResources().getDrawable(R.drawable.wallpaperpixel));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.CerrarSesion) {
            FirebaseAuth.getInstance().signOut();
            Singleton.getInstance().setFoto(null);
            Singleton.getInstance().setPassword(null);
            Singleton.getInstance().setEmail(null);
            Singleton.getInstance().setUser(null);
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        goLogInScreen();
                    } else {
                        Toast.makeText(MenuActivity.this, R.string.NOCERRO, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void goLogInScreen() {
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();
        if (id == R.id.ReconocimientoFacial) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.escenario, new Recon2Fragment()).commit();
        } else if (id == R.id.ControlDelHogar) {
            fm.beginTransaction().replace(R.id.escenario, new ControlFragment()).commit();
        } else if (id == R.id.Sensores) {
            fm.beginTransaction().replace(R.id.escenario, new SensorFragment()).commit();
        } else if (id == R.id.Configuracion) {
            startActivity(new Intent(this, ConfiguracionActivity.class));

        } else if (id == R.id.Compartir) {

            Toast.makeText(getApplicationContext(), "Esta Página Sigue en Desarrollo", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Soporte) {
            Toast.makeText(getApplicationContext(), "Esta Página Sigue en Desarrollo", Toast.LENGTH_SHORT).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Singleton.getInstance().getEmail()== null){
            FirebaseAuth.getInstance().signOut();
            mAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class
            ));

        }
    }
}






