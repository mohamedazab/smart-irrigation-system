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
//        initializing the volley queue
        mQueue = Volley.newRequestQueue(this);

//        receiving the position of the grid cell that was pressed from the previous activity
        positionx = Character.getNumericValue(getIntent().getStringExtra("position").charAt(0));
        positiony = Character.getNumericValue(getIntent().getStringExtra("position").charAt(1));

        Log.d("Add", getIntent().getStringExtra("position"));
        Log.d("positionx" , positionx+"");
        Log.d("positiony" , positiony+"");

//        whenever enter key was pressed on the keyboard
        plant_name.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN ) && (i == KeyEvent.KEYCODE_ENTER)){

//                    The add plant URL from the deployed server
                    String url = "http://159.122.174.163:31175/api/user/add";

//                    adding the position and name of the plant to the body of the request
                    JSONObject postparams = new JSONObject();
                    try {
                        postparams.put("positionX", positionx);
                        postparams.put("positionY", positiony);
                        postparams.put("plant_name", plant_name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    making the PUT request to add the plant
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, postparams,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                                    if the plant was added successfully then we send a pop up saying that this plant was added successfully
                                    Log.d("Add",response.toString());
                                    Toast toast = Toast.makeText(getApplicationContext(),"Plant "+ plant_name.getText().toString() + " added successfully",Toast.LENGTH_SHORT);
                                    toast.setMargin(0,0.6f);
                                    toast.show();
//                                    then we return to the MainActivity to refresh the grid
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
//                                    if we received an error then we send a pop up saying that we failed to add the plant
                                    Log.d("Add plant",error.networkResponse.statusCode+"");
                                    Toast toast = Toast.makeText(getApplicationContext(),"Failed to add Plant "+ plant_name.getText().toString(),Toast.LENGTH_SHORT);
                                    toast.setMargin(0,0.6f);
                                    toast.show();
                                    error.printStackTrace();
                                }
                            });
//                    then we add the PUT request to the volley queue
                    mQueue.add(request);
                    return true;
                }else {
                    return false;
                }
            }
        });

    }
//    This method is responsible for opening the camera to take a photo of a plant
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Take_photo(View view ){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

//    This method is responsible for receiving the photo of the plant from the camera and sending it to the backend
//    to classify the plant and send back its info and add it to the grid
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        String url = "http://159.122.174.163:31175/api/user/add";

        JSONObject postparams = new JSONObject();
        try {
            postparams.put("positionX", positionx);
            postparams.put("positionY", positiony);
            postparams.put("plant_name", "theplant");
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

        Toast toast = Toast.makeText(getApplicationContext(),"Plant added successfully",Toast.LENGTH_SHORT);
        toast.setMargin(0,0.6f);
        toast.show();
    }

//    This method is responsible for returning back to the main activity and refreshing the grid
    void GotoGrid(JSONObject data){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("refresh",true);
        intent.putExtra("data",data.toString());
        startActivity(intent);
    }

}