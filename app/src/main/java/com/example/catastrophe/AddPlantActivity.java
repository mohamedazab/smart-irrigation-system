package com.example.catastrophe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.widget.Toast;

public class AddPlantActivity extends AppCompatActivity {

    EditText plant_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plant_options);
        plant_name = (EditText) findViewById(R.id.plant_name_from_user);

        plant_name.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN ) && (i == KeyEvent.KEYCODE_ENTER)){
                    // TODO: 7/25/2019 send the name of the plant to the backend
                    Toast toast = Toast.makeText(getApplicationContext(),"Plant "+ plant_name.getText().toString() +" added successfully",Toast.LENGTH_SHORT);
                    toast.setMargin(0,0.6f);
                    toast.show();
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Take_photo(View view ){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        // TODO: 7/25/2019 send the bitmap to the server for classification
        Toast toast = Toast.makeText(getApplicationContext(),"Plant added successfully",Toast.LENGTH_SHORT);
        toast.setMargin(0,0.6f);
        toast.show();
    }

}