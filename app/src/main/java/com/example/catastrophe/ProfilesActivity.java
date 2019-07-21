package com.example.catastrophe;

import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**t
 * Created by gehad on 6/22/19.
 */

public class ProfilesActivity extends AppCompatActivity {

    ListView listView;
    List<Plant> plant_list = new ArrayList<Plant>();
    List<String> ids = new ArrayList<>();
    CustomAdapter customAdapter;
    String urlString = "http://172.20.10.2:8080/retrieve";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiles_view);
//        ((TextView)getWindow().getDecorView().findViewById(android.R.id.title)).setGravity(Gravity.CENTER);
        ids.add("21bc47d312e71947a2ebe3a59e6b7662");

        listView = (ListView) findViewById(R.id.list_of_plants);
        customAdapter = new CustomAdapter(plant_list, getApplicationContext());
        listView.setAdapter(customAdapter);

//        try {
//            for (int i = 0 ; i < ids.size() ; i++) {
//                URL url = new URL(urlString);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setConnectTimeout(8000);
//                connection.setRequestMethod("GET");
//                connection.setRequestProperty("id", ids.get(i));
//
//                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
//                {
//                    InputStream in = new BufferedInputStream(connection.getInputStream());
//                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder sb = new StringBuilder();
//                    String line ;
//                    while((line = r.readLine()) != null){
//                        sb.append(line);
//                    }
//                    Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
//                    JSONObject response = new JSONObject(sb.toString());
//
//                    plant_list.add(new Plant(response.getString("name"),response.getInt("class"),response.getInt("threshold"),response.getString("last watered"),response.getString("info")));
//                    customAdapter.notifyDataSetChanged();
//                }else{
//                    Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Plant plant1 = new Plant("Gehad",1,1,"nan","nan");
        plant_list.add(plant1);
        customAdapter.notifyDataSetChanged();

        Plant plant2 = new Plant("Ashley",2,2,"nan","nan");
        plant_list.add(plant2);
        customAdapter.notifyDataSetChanged();
    }
}
