package com.example.myminiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button login;
    private TextView attemptsLeft;
    private TextView signUp;
    private int counter = 5;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        user = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        login = findViewById(R.id.btnLogin);
        attemptsLeft = findViewById(R.id.attText);
        signUp = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = user.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(LoginPage.this, "Please Enter Email !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(userPassword))
                {
                    Toast.makeText(LoginPage.this, "Please Enter Password !!", Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPage.this, "Login Successful !!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginPage.this, MainActivity.class));
//                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
//                                    intent.putExtra("passToActivity", password.getText().toString());
//                                    startActivity(intent);

                                } else {
                                    Toast.makeText(LoginPage.this, "Authentication Failed !!", Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });




            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUp = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(toSignUp);
            }
        });


    }



}
