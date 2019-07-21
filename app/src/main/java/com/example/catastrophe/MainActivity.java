package com.example.catastrophe;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ((TextView)getWindow().getDecorView().findViewById(android.R.id.title)).setGravity(Gravity.CENTER);
    }

    public void OpenProfiles(View view) {
        Intent intent = new Intent(this, ProfilesActivity.class);
        startActivity(intent);
    }
}