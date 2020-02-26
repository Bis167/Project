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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.Inet4Address;

public class SignUpPage extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private Button signUp;
    private TextView login;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        name = findViewById(R.id.getUserName);
        email = findViewById(R.id.getUserEmail);
        password = findViewById(R.id.getPassword);
        rePassword = findViewById(R.id.getConfirm);
        signUp = findViewById(R.id.btnSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = name.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPass = password.getText().toString().trim();
                String userConfirm = rePassword.getText().toString().trim();

                if(TextUtils.isEmpty(userName))
                {
                    Toast.makeText(SignUpPage.this, "Please give a UserName !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(userEmail))
                {
                    Toast.makeText(SignUpPage.this, "Please Enter your Email !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(userPass))
                {
                    Toast.makeText(SignUpPage.this, "Please enter a Password !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userConfirm))
                {
                    Toast.makeText(SignUpPage.this, "Please confirm your Password !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userPass.equals(userConfirm))
                {
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass)
                            .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(SignUpPage.this, LoginPage.class));
                                        Toast.makeText(SignUpPage.this, "SignUp Successful ...\n Please Login to Continue", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(SignUpPage.this, "SignUp Failed !!", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(toLogin);
            }
        });


    }
}
