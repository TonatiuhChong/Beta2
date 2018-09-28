package com.example.hombr.beta.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hombr.beta.R;
import com.example.hombr.beta.Singletons.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail, editTextPassword,editTextPassword2;
    private TextView saludo,email,contra,contra2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Typeface fontbold = Typeface.createFromAsset(getAssets(), "font/googlebold.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "font/googleregular.ttf");
        //EDIT
        editTextEmail = (EditText) findViewById(R.id.REmail);
        editTextPassword = (EditText) findViewById(R.id.Password);
        editTextPassword2 = (EditText) findViewById(R.id.Password2);
        //SALUDOS
        saludo=(TextView)findViewById(R.id.saludoRegistro);
        email=(TextView)findViewById(R.id.saludoemail);
        contra=(TextView)findViewById(R.id.RefPassword);
        contra2=(TextView)findViewById(R.id.RefPassword2);
        saludo.setTypeface(fontbold);
        email.setTypeface(font);
        contra.setTypeface(font);
        contra2.setTypeface(font);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.EntrarMenu).setOnClickListener(this);
        findViewById(R.id.Login).setOnClickListener(this);
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }if (!password.equals(password2)) {
            editTextPassword2.setError("Password missmatch");
            editTextPassword.requestFocus();
            editTextPassword2.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Singleton.getInstance().setEmail(email);
                    Singleton.getInstance().setPassword(password);
                    finish();
                    startActivity(new Intent(Register2Activity.this, RegisterActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.EntrarMenu:
                registerUser();
                break;

            case R.id.Login:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
