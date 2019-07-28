package com.example.catastrophe;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    Some view variables
    ImageView bgapp,clover,test_btn,profile_pic;
    Animation bganim,clovernim,frombottom,fromup;
    LinearLayout textsplash,texthome,menus,login_signup_form;
    GridLayout grid_3;
    Button login_tab,signup_tab,login_btn,signup_btn;
    EditText input_email,input_password;

//    a 2D array to hold the plants of the grid
    Plant[][] grid;

//    A volley request queue
    private RequestQueue mQueue;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Assigning some UI variables to their corresponding UI elements
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);


        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        texthome.setAlpha(0);
        login_signup_form = (LinearLayout) findViewById(R.id.login_signup_form);
        grid_3 = (GridLayout) findViewById(R.id.grid_3);

        login_tab = (Button) findViewById(R.id.logintab);
        signup_tab = (Button) findViewById(R.id.signuptab);
        login_btn = (Button) findViewById(R.id.btn_login);
        signup_btn = (Button) findViewById(R.id.btn_signup);


        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);

        bganim = AnimationUtils.loadAnimation(this,R.anim.bganim);
        clovernim = AnimationUtils.loadAnimation(this,R.anim.clovernim);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromup = AnimationUtils.loadAnimation(this,R.anim.fromup);

//        Checking if we got into this activity from a different activity
        if(getIntent().getBooleanExtra("refresh",false) == false) {
//            if this activity started because we first opened the app
//            handling some UI elements' properties
            signup_btn.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
            signup_tab.setClickable(true);
            login_tab.setClickable(false);
            signup_tab.setBackgroundColor(Color.argb(0, 0, 0, 0));
            login_tab.setBackgroundColor(Color.argb(15, 255, 0, 0));
            grid_3.setVisibility(View.GONE);

//            handling the session cookie
            CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);
        }else{
//            if we got into this activity from a different activity
//            handling some UI elements' properties
            bgapp.setTranslationY(-2250);
            clover.setTranslationX(-300);
            texthome.setAlpha(1);
            grid_3.setVisibility(View.VISIBLE);
            textsplash.setAlpha(0);
            login_signup_form.setAlpha(0);
            login_signup_form.setTranslationY(-2250);

//            receiving the info of the new grid from the previous activity
            try {
                RefreshGrid(new JSONObject(getIntent().getStringExtra("data")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        initializing the volley request queue
        mQueue = Volley.newRequestQueue(this);
    }

//    This method is called when the login button is pressed
    public void Login(View view){
//        getting the email and the password from the text views
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

//        The login URL of the deployed server
        String url = "http://159.122.174.163:31175/api/login";

//        Adding the body parameters to the body of the request
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("email", email);
            postparams.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Making the POST request on the login URL
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, postparams,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
//                        if the response was not an error then the success parameter is checked
                        Boolean result = false;
                        Log.d("Login",response.toString());
                        try {
                            result = response.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result) {
//                            if the success parameter was true then we login
                            LoginSuccess();
//                            we refresh the grid after a successful login
                            try {
                                RefreshGrid(response.getJSONObject("data"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
//                            if the success parameter was false then the login will fail
                            LoginFailure();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if we got a 300 error then the login will fail as well
                        Log.d("Login",error.networkResponse.toString());
                        if (error.networkResponse.statusCode >= 300){
                            Log.d("Login",error.networkResponse.statusCode + "");
                            LoginFailure();
                        }else {
                            error.printStackTrace();
                        }
                    }
                });

//        we add the previous login request to the volley queue
        mQueue.add(request);
    }

    public void LoginSuccess(){
//        if login was successful then some animations will be done and some UI parameters will be handled
        Log.d("Login","Success");
        bgapp.animate().translationY(-2250).setDuration(800);
        clover.animate().translationX(-300).alpha(0).setDuration(800);
        texthome.setAlpha(1);
        texthome.startAnimation(frombottom);
        grid_3.startAnimation(frombottom);
        textsplash.animate().translationY(140).alpha(0).setDuration(800);
        login_signup_form.animate().translationY(-2250).alpha(0).setDuration(800);
        grid_3.setVisibility(View.VISIBLE);
    }

    public void LoginFailure(){
//        if login operation was not successful then we send a pop up to the user saying that either the email or the password was wrong
        Toast toast = Toast.makeText(getApplicationContext(),"Wrong Email or Password",Toast.LENGTH_SHORT);
        toast.setMargin(0,0.6f);
        toast.show();
    }

//    This method is called when the sign up button is pressed
    public void SignUp(View view){
//        we get the email and the password from the text views
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

//        The sign up URL of the deployed server
        String url = "http://159.122.174.163:31175/api/sign-up";

//        Adding the body parameters to the body of the request
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("email", email);
            postparams.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        making the sign up POST request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                      if the response was not an error then we check the success parameter of the body
                        Boolean result = false;
                        try {
                            result = response.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result){
//                            if the success parameter was true then we send a pop up saying that sign up was successful and switch to the login form
                            Toast toast = Toast.makeText(getApplicationContext(),"Sign Up Successful",Toast.LENGTH_SHORT);
                            toast.setMargin(0,0.6f);
                            toast.show();
                            Login_tab(login_tab);
                        }else{
//                            if the success parameter was false then we send a pop up saying that the sign up failed
                            Toast toast = Toast.makeText(getApplicationContext(),"Sign Up Failure",Toast.LENGTH_SHORT);
                            toast.setMargin(0,0.6f);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        if we received and error then we send a pop up saying that the sign failed
                        Toast toast = Toast.makeText(getApplicationContext(),"Sign Up Failure",Toast.LENGTH_SHORT);
                        toast.setMargin(0,0.6f);
                        toast.show();
                        error.printStackTrace();
                    }
                });
//        then we add the sign up request to the volley queue
        mQueue.add(request);
    }

//    This method is called when the login tab is pressed
    public void Login_tab(View view){
//        switching to the login form
        login_tab.setClickable(false);
        signup_tab.setClickable(true);
        login_btn.setVisibility(View.VISIBLE);
        signup_btn.setVisibility(View.GONE);
        login_tab.setBackgroundColor(Color.argb(15,255,0,0));
        signup_tab.setBackgroundColor(Color.argb(0,0,0,0));
    }

//    This method is called when the sign up tab is pressed
    public void Signup_tab(View view){
//        switching to the sign up form
        login_tab.setClickable(true);
        signup_tab.setClickable(false);
        login_btn.setVisibility(View.GONE);
        signup_btn.setVisibility(View.VISIBLE);
        login_tab.setBackgroundColor(Color.argb(0,0,0,0));
        signup_tab.setBackgroundColor(Color.argb(15,255,0,0));
    }

//    This method is called when the user presses on a grid cell that has a plant
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void View_plant(View view){
//        In this method we get the info about this plant from the 2d array that we created earlier and send
//        this info to the Plant profile activity
        Intent intent = new Intent(this, PlantProfileActivity.class);
        int posX ;
        posX = Character.getNumericValue(view.getTag().toString().charAt(0));
        int posY ;
        posY = Character.getNumericValue(view.getTag().toString().charAt(1));
        intent.putExtra("name",grid[posX][posY].name);
        intent.putExtra("moisture_threshold",grid[posX][posY].moisture_threshold);
        intent.putExtra("recommended_ph",grid[posX][posY].recommended_ph);
        intent.putExtra("recommended_temperature",grid[posX][posY].recommended_temperature);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(view,"transition_profile_1");
        pairs[1] = new Pair<View,String>(bgapp,"transition_profile_2");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this
                ,pairs);

        startActivity(intent,options.toBundle());
    }

//    This method is called when the user presses on a grid cell that doesn't have a plant
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Add_plant(View view){
        Intent intent = new Intent(this, AddPlantActivity.class);
        intent.putExtra("position",view.getTag().toString());
        Log.d("Add",view.getTag().toString());
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(bgapp,"transition_profile_2");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this
                ,pairs);

        startActivity(intent,options.toBundle());
    }

//    This method is decides if the grid cell does or does not have a plant
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Add_Or_View_Plant(View view){
        Log.d("Image1", ((ImageView)view).getDrawable().getCurrent().toString());
        Log.d("Image2", getDrawable(R.drawable.ic_add_box_black_24dp).getCurrent().toString());

        if (((ImageView)view).getDrawable().isFilterBitmap() == false){
            Add_plant(view);
        }
        else{
            View_plant(view);
        }
    }

//    This method is responsible for refresh the grid view according to the data that is received from the server
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void RefreshGrid(JSONObject response){
        grid = new Plant[3][3];
        try {
            JSONArray plants = response.getJSONArray("grid");
            if(plants.length() != 0) {
                for (int i = 0; i < plants.length(); i++) {
                    JSONObject plant = plants.getJSONObject(i);
                    ImageView view = (ImageView) getViewsByTag(grid_3, plant.getInt("positionX") + "" + plant.getInt("positionY")).get(0);
                    view.setImageDrawable(getDrawable(R.mipmap.plant_default));
                    JSONObject crop = plant.getJSONObject("crop");
                    grid[plant.getInt("positionX")][plant.getInt("positionY")] = new Plant(
                      crop.getString("name"),
                      crop.getDouble("moisture_threshold"),
                      crop.getDouble("recommended_ph"),
                      crop.getDouble("recommended_temperature")
                    );
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    This method is responsible for searching for UI views by their tags
    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }
        }
        return views;
    }
}