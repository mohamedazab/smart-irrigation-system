package com.example.catastrophe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView bgapp,clover;
    Animation bganim,clovernim,frombottom;
    LinearLayout textsplash,texthome,menus,login_signup_form;
    Button login_tab,signup_tab,login_btn,signup_btn;
    EditText input_email,input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        texthome.setAlpha(0);
        login_signup_form = (LinearLayout) findViewById(R.id.login_signup_form);
//        menus = (LinearLayout) findViewById(R.id.menus);

        login_tab = (Button) findViewById(R.id.logintab);
        signup_tab = (Button) findViewById(R.id.signuptab);
        login_btn = (Button) findViewById(R.id.btn_login);
        signup_btn = (Button) findViewById(R.id.btn_signup);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);

        bganim = AnimationUtils.loadAnimation(this,R.anim.bganim);
        clovernim = AnimationUtils.loadAnimation(this,R.anim.clovernim);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        signup_btn.setVisibility(View.GONE);
        login_btn.setVisibility(View.VISIBLE);
        signup_tab.setClickable(true);
        login_tab.setClickable(false);
        signup_tab.setBackgroundColor(Color.argb(0,0,0,0));
        login_tab.setBackgroundColor(Color.argb(15,255,0,0));


//        bgapp.animate().translationY(-2250).setDuration(800).setStartDelay(800);
//        clover.animate().translationX(-300).alpha(0).setDuration(800).setStartDelay(800);
//        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(800);
//        texthome.startAnimation(frombottom);
//        menus.startAnimation(frombottom);

//        ((TextView)getWindow().getDecorView().findViewById(android.R.id.title)).setGravity(Gravity.CENTER);
    }

    boolean Validate(String email,String password){
        //Check with the backend
        // TODO: 7/23/2019
        return true;
    }

    void AddToDatabase(String email, String password){
        //Add this user to the database
        // TODO: 7/23/2019
    }

    public void Login(View view){
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        boolean valid = Validate(email,password);

        if (valid){
            //animate the scene
            bgapp.animate().translationY(-2250).setDuration(800).setStartDelay(800);
            clover.animate().translationX(-300).alpha(0).setDuration(800).setStartDelay(800);
            texthome.setAlpha(1);
            texthome.startAnimation(frombottom);
            textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(800);
            login_signup_form.animate().translationY(-2250).alpha(0).setDuration(800).setStartDelay(800);

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),"Wrong Email or Password",Toast.LENGTH_SHORT);
            toast.setMargin(0,0.6f);
            toast.show();
        }
    }

    public void SignUp(View view){
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        AddToDatabase(email,password);

        //if sign up was successful
        Toast toast = Toast.makeText(getApplicationContext(),"Sign Up Successful",Toast.LENGTH_SHORT);
        toast.setMargin(0,0.6f);
        toast.show();

        Login_tab(view);
    }

    public void Login_tab(View view){
        login_tab.setClickable(false);
        signup_tab.setClickable(true);
        login_btn.setVisibility(View.VISIBLE);
        signup_btn.setVisibility(View.GONE);
        login_tab.setBackgroundColor(Color.argb(15,255,0,0));
        signup_tab.setBackgroundColor(Color.argb(0,0,0,0));
    }
    public void Signup_tab(View view){
        login_tab.setClickable(true);
        signup_tab.setClickable(false);
        login_btn.setVisibility(View.GONE);
        signup_btn.setVisibility(View.VISIBLE);
        login_tab.setBackgroundColor(Color.argb(0,0,0,0));
        signup_tab.setBackgroundColor(Color.argb(15,255,0,0));
    }


    public void OpenProfiles(View view) {
        Intent intent = new Intent(this, ProfilesActivity.class);
        startActivity(intent);
    }
}