package com.example.hombr.beta.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hombr.beta.ConfiguracionActivity;
import com.example.hombr.beta.Fragments.AdminFragment;
import com.example.hombr.beta.Fragments.ControlFragment;
import com.example.hombr.beta.Fragments.ReconFragment;
import com.example.hombr.beta.Fragments.SensorFragment;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Acmin;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

import java.util.Calendar;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, FingerPrintAuthCallback {
    private TextView Usuario, Email;
    private ImageView Fusuario;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "MenuActivity";
    private FingerPrintAuthHelper mFingerPrintAuthHelper;
    private Dialog finger;
    String p;

    @Override
    protected void onResume() {
        super.onResume();
if(Singleton.getInstance().isActivacioncontrol()==false)
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (Singleton.getInstance().isActivacioncontrol()==false){
            //FINGER
            mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
            finger = new Dialog(this);
            finger.setContentView(R.layout.fingers);
            finger.setCancelable(false);
            finger.show();
            //
        }


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

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
         p= Acmin.getInstance().getUserAcmin();
         if (p==null)goLogInScreen();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         switch (p){
             case "MANAGER":
                 if (mAuth.getCurrentUser().getEmail()=="lyanvilchis@gmail.com"){
                 getWindow().setNavigationBarColor(getResources().getColor(R.color.admin));
                 getWindow().setStatusBarColor(getResources().getColor(R.color.admin));
                 tx.replace(R.id.escenario,  new AdminFragment(),"Control1");
                 tx.commit();
                 getSupportFragmentManager().popBackStack();}
                 else {
                     getWindow().setNavigationBarColor(getResources().getColor(R.color.primary_light));
                     getWindow().setStatusBarColor(getResources().getColor(R.color.primary_light));
                     getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_light)));
                     tx.replace(R.id.escenario, new ControlFragment()).commit();
                 }
             break;
             default:
                 getWindow().setNavigationBarColor(getResources().getColor(R.color.primary_light));
                 getWindow().setStatusBarColor(getResources().getColor(R.color.primary_light));
                 getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_light)));
                 tx.replace(R.id.escenario, new ReconFragment()).commit();

                 getSupportFragmentManager().popBackStack();
             break;
         }
Wallpaper(nave);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.ReconocimientoFacial) {
            if (Singleton.getInstance().isActivacioncontrol()==false){

            }
            if (Singleton.getInstance().isActivacioncontrol()==true) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

                switch (p){
                    case "MANAGER":
                        fm.beginTransaction().replace(R.id.escenario,  new AdminFragment(),"ACMIN").addToBackStack(null).commit();
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.admin)));

                    break;
                    default:
                        fm.beginTransaction().replace(R.id.escenario, new ReconFragment()).commit();
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_light)));

                    break;
                }
            }

        } else if (id == R.id.ControlDelHogar) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            fm.beginTransaction().replace(R.id.escenario, new ControlFragment()).addToBackStack(null).commit();
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        } else if (id == R.id.Sensores) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            fm.beginTransaction().replace(R.id.escenario, new SensorFragment()).addToBackStack(null).commit();
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Mix1)));
        } else if (id == R.id.Configuracion) {
            finish();
            startActivity(new Intent(this, ConfiguracionActivity.class));

        } else if (id == R.id.Compartir) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Quieres ser parte del club del cambio? Buscanos en Facebook! https://www.facebook.com/WeHouse-717686338586239/ ");
            startActivity(Intent.createChooser(share, "Comparte WeHouse!"));


        } else if (id == R.id.Soporte) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            final EditText input = new EditText(MenuActivity.this);

            builder.setTitle("Solicitud de Comentarios de Aplicacion").setView(input)
                    .setMessage("Escribe tu duda o comentario para contactarnos contigo! :)")
                    .setPositiveButton("Petición", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();

                            // continue with delete
                            String Comentario=input.getText().toString();
                            if (Comentario.isEmpty()){
                                input.setError("Campo Vacio");
                                input.requestFocus();
                            }if (Comentario.length()<10){
                                input.setError("Escribe un poco mas ;)");
                                input.requestFocus();
                            }

                            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                            Uri data = Uri.parse("mailto:?subject=" + "Comentarios acerca WeHouse"+ "&body=" + Comentario + "&to=" + "zonitetzr@mail.com");
                            mailIntent.setData(data);
                            startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                            }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


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

    @Override
    public void onNoFingerPrintHardwareFound() {
    AlertDialog.Builder gg;
    gg=new AlertDialog.Builder(this);
    final EditText edit= new EditText( MenuActivity.this);
    gg.setTitle("Verificación").setView(edit)
            .setMessage("Pon tu nombre")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Singleton.getInstance().getUser()==edit.getText().toString()){
                        Toast.makeText(MenuActivity.this, "Aceptado ", Toast.LENGTH_SHORT).show();
                        Singleton.getInstance().setActivacioncontrol(true);
                    }
                }
            });
    gg.show();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        AlertDialog.Builder gg;
        gg=new AlertDialog.Builder(this);
        final EditText edit= new EditText( MenuActivity.this);
        gg.setTitle("Verificación").setView(edit)
                .setMessage("Pon tu nombre")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Singleton.getInstance().getUser()==edit.getText().toString()){
                            Toast.makeText(MenuActivity.this, "Aceptado ", Toast.LENGTH_SHORT).show();
                            Singleton.getInstance().setActivacioncontrol(true);
                        }
                    }
                });
    }

    @Override
    public void onBelowMarshmallow() {
        AlertDialog.Builder gg;
        gg=new AlertDialog.Builder(this);
        final EditText edit= new EditText( MenuActivity.this);
        gg.setTitle("Verificación").setView(edit)
                .setMessage("Pon tu nombre")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Singleton.getInstance().getUser()==edit.getText().toString()){
                            Toast.makeText(MenuActivity.this, "Aceptado ", Toast.LENGTH_SHORT).show();
                            Singleton.getInstance().setActivacioncontrol(true);
                        }
                    }
                });
       gg.show();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        finger.dismiss();
        Singleton.getInstance().setActivacioncontrol(true);

    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {

    }


    private void Wallpaper(LinearLayout nave) {

        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);


        if (currentHour < 7) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.night));
        }
        if (currentHour > 7 && currentHour < 9) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.morning));

        }
        if (currentHour < 12 && currentHour > 9) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.sunny));

        }
        if (currentHour < 19 && currentHour > 12) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.afternoon));
        }
        if (currentHour > 19) {
            nave.setBackground(this.getResources().getDrawable(R.drawable.night));
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
            Singleton.getInstance().setActivacioncontrol(false);

            Acmin.getInstance().setContadoragregar(0);
            Acmin.getInstance().setTotalagregar(0);
            Acmin.getInstance().setUserAcmin(null);
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

}






