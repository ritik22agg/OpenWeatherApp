package com.example.openweatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText text;
    TextView v;

    public void onplay(View view){
        text = (EditText) findViewById(R.id.editText);
        String city = text.getText().toString();
        String start = "http://samples.openweathermap.org/data/2.5/weather?q=";
        String end = "&appid=b6907d289e10d714a6e88b30761fae22";
        String url = start + city + end;

        DownloadTask task = new DownloadTask();
        task.execute(url);
    }

    public class DownloadTask extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... urls){

            URL u;
            HttpURLConnection connections = null;
            String result = "";

            try {
                u = new URL(urls[0]);
                connections = (HttpURLConnection) u.openConnection();
                InputStream in = connections.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){
                    char ch = (char) data;
                    result += ch;

                    data = reader.read();
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                v = (TextView) findViewById(R.id.textView3);
                JSONObject json = new JSONObject(s);

                String weather = json.getString("weather");

                JSONArray arr = new JSONArray(weather);

                for(int i = 0; i< arr.length();i++){
                    JSONObject child = arr.getJSONObject(i);
                    String ans1 = child.getString("main");
                    String ans2 = child.getString("description");

                    v.setText(ans1 + ", " + ans2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
