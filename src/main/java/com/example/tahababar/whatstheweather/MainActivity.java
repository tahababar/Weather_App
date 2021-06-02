package com.example.tahababar.whatstheweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView show = (TextView) findViewById(R.id.result);
    public class DownloadTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");   //provide main chunk of data dealing with
                JSONArray arr = new JSONArray(weatherInfo); //converts whole data into array
                for(int i =0; i<arr.length(); i++)
                {
                    JSONObject part = arr.getJSONObject(i); //each element of array with key and value
                    show.setText(part.getString("main"));
                    show.setText(part.getString("description"));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void go(View view){
        show.setText("");
        DownloadTask task = new DownloadTask();
        TextView input = (TextView) findViewById(R.id.cityInput);
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + input.getText().toString() + "&appid=2c9b8a514f8f7bc06ab17cb2a39aa65d");

    }
}









// <uses-permission android:name="android.permission.INTERNET" /> add this in manifest>androidmanifest.xml