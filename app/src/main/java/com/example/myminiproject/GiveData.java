package com.example.myminiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class GiveData extends AppCompatActivity {

    EditText giveData;
    Button btnBackToMain, btnUpdateData, btnResetData;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myDatabase;
    FirebaseUser user;

    SharedPreferences sharedPreferences;

    int dayCount = 0;
    public  static int weekCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_data);

        giveData = findViewById(R.id.editEnterData);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        btnResetData = findViewById(R.id.btnResetData);
        sharedPreferences = getSharedPreferences("getDate", MODE_PRIVATE);
        final String id = user.getUid();

        final HashMap<String, String> weeks = new HashMap<>();
        final HashMap<String, String> days = new HashMap<>();

        LoadDate();

        btnResetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dayCount = 0;
                weekCount = 0;
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GiveData.this, MainActivity.class));
            }
        });
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if((dayCount % 8) > 6)
                {
                    //weeks.put("weeks", "Week "+String.valueOf(weekCount));
                    weekCount++;
                    dayCount = 0;

                }
                days.put("days", "Day "+String.valueOf(dayCount % 8));
                weeks.put("weeks", "Week "+String.valueOf(weekCount));

                myDatabase = firebaseDatabase.getReference(id).child("Water Level").child(weeks.get("weeks")).child(days.get("days"));


                String waterLevel = giveData.getText().toString();

                if (TextUtils.isEmpty(waterLevel))
                {
                    Toast.makeText(GiveData.this, "Please Enter Data first !!", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    UserData userData = new UserData(waterLevel);
                    myDatabase.setValue(userData);
                    Toast.makeText(GiveData.this, "Data Updated Successfully !!", Toast.LENGTH_SHORT).show();
                }
                dayCount++;

            }
        });
    }


    public void SaveDate()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("weekCount", weekCount);
        editor.putInt("dayCount", dayCount);
        editor.apply();
    }

    public void LoadDate()
    {

        weekCount = sharedPreferences.getInt("weekCount", MODE_PRIVATE);
        dayCount = sharedPreferences.getInt("dayCount", MODE_PRIVATE);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SaveDate();
    }


}