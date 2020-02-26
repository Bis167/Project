package com.example.myminiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnGetData, btnShowData, getUserId; /*btnShowTime*/;
    TextView showLevel;
    ProgressBar waterProgress;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myDatabase;
    FirebaseUser user;
    public  int[] weeklyStack = new int[7];

//    Calendar getTime = Calendar.getInstance()



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = findViewById(R.id.btnGiveData);
        btnShowData = findViewById(R.id.btnGetData);
        showLevel = findViewById(R.id.showLevel);
        waterProgress = findViewById(R.id.progressBar);
        getUserId = findViewById(R.id.getUserId);
//        btnShowData = findViewById(R.id.btnShowTime);

        user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myDatabase = firebaseDatabase.getReference(uid);

        final int weekNo = GiveData.weekCount;

        getUserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLevel.setText(uid);
            }
        });

//        btnShowTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showLevel.setText(String.valueOf(getTime.getTime()));
//            }
//        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, GiveData.class));
                Toast.makeText(MainActivity.this, "Please Give Us the Data !!", Toast.LENGTH_SHORT).show();
            }
        });
//        getUserId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showLevel.setText(uid);
//
//            }
//        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (int i = 0; i<7; i++)
                        {
                            weeklyStack[i] = Integer.parseInt(dataSnapshot.child("Water Level").child("Week "+String.valueOf(weekNo)).child("Day "+String.valueOf(i)).child("waterLevel").getValue().toString());
                        }
                        long  waterCalculation = WaterCalculation();

                        showLevel.setText(String.valueOf(waterCalculation));

                        //int waterStatus = Integer.parseInt(waterLevel);
                        //waterProgress.setProgress(waterCalculation);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    public  long WaterCalculation()
    {
        long AverageLevel = 0;

        for (int i: weeklyStack)
        {
            AverageLevel += weeklyStack[i];
        }
        return AverageLevel;
    }

}
