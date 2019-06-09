package com.example.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UsingThingspeakAPI";
    private static final String THINGSPEAK_CHANNEL_ID = " 721260";
    private static final String THINGSPEAK_API_KEY = "EY4L70885VKEIUY1"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "EY4L70885VKEIUY1";
    /* Be sure to use the correct fields for your own app*/
    private static final String THINGSPEAK_FIELD1 = "field1";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/feeds/last?";
    TextView t1,t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_layout);
        t1=(EditText)findViewById(R.id.editText4); 
        t2=(EditText)findViewById(R.id.editText3);
        b1=(Button) findViewById(R.id.button);
        t2.setText("");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new FetchThingspeakTask().execute();
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage(), e);

                }
            }
        });
    }
    class FetchThingspeakTask extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            t2.setText("Fetching Data from Server.Please Wait...");
        }
        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("https://api.thingspeak.com/channels/721260/feeds.json?api_key=EY4L70885VKEIUY1&results=2");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.e("stringBuilder",line);
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        protected void onPostExecute(String response) {
            if(response == null) {
                Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject channel = (JSONObject) new JSONTokener(response).nextValue();
                Log.v("test",channel.toString());
                JSONArray feeds=new JSONArray(channel.getString("feeds"));
                Log.v("test",feeds.toString());
               // JSONObject feed1=feeds.get(0);
               t1.setText((feeds.getJSONObject(0)).getString("field1"));
                t2.setText((feeds.getJSONObject(1)).getString("field1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}










//package com.example.myapplication;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//
//    private static final String TAG = "UsingThingspeakAPI";
//    private static final String THINGSPEAK_CHANNEL_ID = "721260";
//    private static final String THINGSPEAK_API_KEY = "EY4L70885VKEIUY1"; //GARBAGE KEY
//    private static final String THINGSPEAK_API_KEY_STRING = "EY4L70885VKEIUY1";
//    /* Be sure to use the correct fields for your own app*/
//    private static final String THINGSPEAK_FIELD1 = "field1";
//    private static final String THINGSPEAK_FIELD2 = "field2";
//    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
//    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
//    private static final String THINGSPEAK_FEEDS_LAST = "/feeds/last?";
//    TextView t1, t2;
//    Button b1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        t1 = (TextView) findViewById(R.id.textView2);
//        t2 = (TextView) findViewById(R.id.textView3);
//        b1 = (Button) findViewById(R.id.);
//        t2.setText("");
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    new FetchThingspeakTask().execute();
//                } catch (Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                }
//            }
//        });
//        class FetchThingspeakTask extends AsyncTask<Void, Void, String> {
//            protected void onPreExecute() {
//                t2.setText("Fetching Data from Server.Please Wait...");
//            }
//
//            @SuppressLint("WrongThread")
//            protected String doInBackground(Void... urls) {
//                try {
//                    URL url = new URL(THINGSPEAK_CHANNEL_URL + THINGSPEAK_CHANNEL_ID +
//                            THINGSPEAK_FEEDS_LAST + THINGSPEAK_API_KEY_STRING + "=" +
//                            THINGSPEAK_API_KEY + "");
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    try {
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                        StringBuilder stringBuilder = new StringBuilder();
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            stringBuilder.append(line).append("\n");
//                        }
//                        bufferedReader.close();
//                        return stringBuilder.toString();
//                    } finally {
//                        urlConnection.disconnect();
//                    }
//                } catch (Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                    return null;
//                }
//            }
//
//            protected void onPostExecute(String response) {
//                if (response == null) {
//                    Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                try {
//                    JSONObject channel = (JSONObject) new JSONTokener(response).nextValue();
//                    double v1 = channel.getDouble(THINGSPEAK_FIELD1);
//                    if (v1 >= 90)
//                        t1.setText("HI ALL  ");
//                    else
//                        t1.setText("NO VALUES");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


//    private Button submit_btn;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        submit_btn=findViewById(R.id.line2);
//        submit_btn.setOnClickListener(this);
//    }
//
//    @Override
//
//    public void onClick(View v) {
//
//        Intent NextActivity=new Intent(this,Inventory.class);
//        startActivity(NextActivity);
//
//    }


