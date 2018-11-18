package com.example.hombr.beta.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.CUser;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;

public class RegisterActivity extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE=123;

    String mCurrentPhotoPath;
    TextView textView,camara;
    ImageView imageView;
    EditText editText;
    Uri uriProfileImage;
    String profileImageUrl;
    FirebaseAuth mAuth;
    static final int REQUEST_IMAGE_CAPTURE = 1;
boolean  verificaremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        Typeface fontbold = Typeface.createFromAsset(getAssets(), "font/googlebold.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "font/googleregular.ttf");

        Toolbar toolbar = findViewById(R.id.toolbarRegistro);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.Rusuario);
        imageView = (ImageView) findViewById(R.id.Foto);
        textView = (TextView) findViewById(R.id.VerificarEmail);
        camara = (TextView) findViewById(R.id.saludocamara);
        camara.setTypeface(fontbold);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Escoja una opcion")
                        .setItems(R.array.acciones, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        int hasWriteCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
                                        if (hasWriteCameraPermission != PackageManager.PERMISSION_GRANTED) {
                                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                                Snackbar snackbar = Snackbar.make(view, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                                                        .setAction(android.R.string.ok, new View.OnClickListener() {
                                                            @Override
                                                            @TargetApi(Build.VERSION_CODES.M)
                                                            public void onClick(View v) {
                                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                                                            }
                                                        });
                                            } else {
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                                            }
                                            return;

                                        }else if (hasWriteCameraPermission == PackageManager.PERMISSION_GRANTED){
                                            if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED) {

                                                // Permission is not granted
                                                // Should we show an explanation?
                                                if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                    Snackbar snackbar = Snackbar.make(view, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                                                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                                                @Override
                                                                @TargetApi(Build.VERSION_CODES.M)
                                                                public void onClick(View v) {
                                                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                                                                }
                                                            });
                                                } else {
                                                    // No explanation needed; request the permission
                                                    ActivityCompat.requestPermissions(RegisterActivity.this,
                                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                            REQUEST_WRITE_EXTERNAL_STORAGE);

                                                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                                    // app-defined int constant. The callback method gets the
                                                    // result of the request.
                                                }
                                            } else {
                                                // Permission has already been granted
                                            }
                                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                            }

                                        }

                                        break;
                                    case 1:
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
                                        break;
                                }
                            }
                        });
            builder.show();
            }

        });
        loadUserInformation();
        findViewById(R.id.Registrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificaremail==true){
                saveUserInformation();
            }}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MenuActivity.class));
        }
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }

            if (user.getDisplayName() != null) {
                editText.setText(user.getDisplayName());
            }

            if (user.isEmailVerified()) {
                verificaremail=true;
                textView.setText("Email Verified");
            }

                textView.setText("Email Not Verified (Click to Verify)");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        }
    }
    private void saveUserInformation() {

        String displayName = editText.getText().toString();

        if (displayName.isEmpty()) {
            editText.setError("Name required");
            editText.requestFocus();
            return;
        }
        Singleton.getInstance().setUser(displayName);
        final FirebaseUser user = mAuth.getCurrentUser();
        CUser datos = new CUser(
                Singleton.getInstance().getUser(),
                Singleton.getInstance().getEmail(),
                Singleton.getInstance().getPassword()
        );
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
            }
        });

        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Singleton.getInstance().setUser(mAuth.getCurrentUser().getDisplayName());
                                Singleton.getInstance().setEmail(mAuth.getCurrentUser().getEmail());
                                Singleton.getInstance().setFoto(mAuth.getCurrentUser().getPhotoUrl());

                                Toast.makeText(RegisterActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterActivity.this,MenuActivity.class));
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CHOOSE_IMAGE:
                if(resultCode == RESULT_OK){
                    uriProfileImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                        imageView.setImageBitmap(bitmap);
                        uploadImageToFirebaseStorage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                break;
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, "Title", null);
                    uriProfileImage = Uri.parse(path);
                    imageView.setImageBitmap(imageBitmap);
                    uploadImageToFirebaseStorage();

                }
                break;
        }


    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_register, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final FirebaseUser user = mAuth.getCurrentUser();
        switch (item.getItemId()) {
            case R.id.ActualizarRegistro:
                if (user.isEmailVerified()) {
                    textView.setText("Email Verified");
                } else {
                    textView.setText("Email Not Verified (Click to Verify)");
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }



                break;
        }

        return true;
    }





}
