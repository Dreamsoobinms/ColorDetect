package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btLogin,btRegister;
    EditText etEmail,etPass;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authlisten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        btLogin = (Button) findViewById(R.id.btLlogin);
        btRegister = (Button) findViewById(R.id.btLRegister);
        etEmail = (EditText) findViewById(R.id.eTLemail);
        etPass = (EditText) findViewById(R.id.eTLpass);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
    }
    private void Signin(){

        final String email,pass;
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(MainActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,DetectActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this,"Login error "+email,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
