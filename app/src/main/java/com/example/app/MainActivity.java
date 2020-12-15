package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    protected Button button;
    protected TextView textView;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        registerReceiver(receiver, new IntentFilter(WeatherService.CHANNEL));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,WeatherService.class);
                startService(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(context, WeatherService.class);
        stopService(intent);
    }
    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                JSONObject json = new JSONObject(intent.getStringExtra(WeatherService.INFO));
                JSONArray gisArray = json.getJSONArray("gis");
//                textView.setText(gisArray.toString());
                textView.setText(gisArray.getString(0));
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Неверный формат JSON",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
}