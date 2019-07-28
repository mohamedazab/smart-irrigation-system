package com.example.catastrophe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPlantActivity extends AppCompatActivity {

    EditText plant_name;
    int positionx;
    int positiony;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plant_options);
        plant_name = (EditText) findViewById(R.id.plant_name_from_user);
        mQueue = Volley.newRequestQueue(this);

        positionx = Character.getNumericValue(getIntent().getStringExtra("position").charAt(0));
        positiony = Character.getNumericValue(getIntent().getStringExtra("position").charAt(1));

        Log.d("Add",getIntent().getStringExtra("position").toString());
        Log.d("positionx" , positionx+"");
        Log.d("positiony" , positiony+"");

        plant_name.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN ) && (i == KeyEvent.KEYCODE_ENTER)){
                    // TODO: 7/25/2019 send the name of the plant to the backend
                    String url = "http://159.122.174.163:31175/api/user/add";

                    JSONObject postparams = new JSONObject();
                    try {
                        postparams.put("positionX", positionx);
                        postparams.put("positionY", positiony);
                        postparams.put("plant_name", plant_name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, postparams,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Add",response.toString());
                                    Toast toast = Toast.makeText(getApplicationContext(),"Plant "+ plant_name.getText().toString() + " added successfully",Toast.LENGTH_SHORT);
                                    toast.setMargin(0,0.6f);
                                    toast.show();
                                    try {
                                        GotoGrid(response.getJSONObject("data"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Add plant",error.networkResponse.statusCode+"");
                                    Toast toast = Toast.makeText(getApplicationContext(),"Failed to add Plant "+ plant_name.getText().toString(),Toast.LENGTH_SHORT);
                                    toast.setMargin(0,0.6f);
                                    toast.show();
                                    error.printStackTrace();
                                }
                            });
                    mQueue.add(request);
                    return true;
                }else {
                    return false;
                }
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

    void GotoGrid(JSONObject data){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("refresh",true);
        intent.putExtra("data",data.toString());
        startActivity(intent);
    }

}