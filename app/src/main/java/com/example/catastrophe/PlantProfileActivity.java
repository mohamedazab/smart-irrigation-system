package com.example.catastrophe;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlantProfileActivity extends AppCompatActivity {

    TextView name,moisture_threshold,recommended_ph,recommended_temprature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_view);
        name = (TextView) findViewById(R.id.Plant_name);
        moisture_threshold = (TextView) findViewById(R.id.Moisture_Threshold);
        recommended_ph = (TextView) findViewById(R.id.Recommended_PH);
        recommended_temprature = (TextView) findViewById(R.id.Recommended_Temperature);

//        Here we receive the plant's info from the previous activity to show it on the UI
        name.setText(getIntent().getStringExtra("name"));
        moisture_threshold.setText(getIntent().getDoubleExtra("moisture_threshold",0)+"");
        recommended_ph.setText(getIntent().getDoubleExtra("recommended_ph",0)+"");
        recommended_temprature.setText(getIntent().getDoubleExtra("recommended_temprature",0)+"");
    }

//    This method is responsible for deleting the plant from the grid but it is currently unimplemented
    public void Delete_plant(View view){

    }
}
