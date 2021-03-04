package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.rxjava.RxAction;
import com.example.myapplication.rxjava.RxManager;

public class MainActivity2 extends AppCompatActivity {

    private RxManager rxManager;
    private String TAG = "RxBus_Text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rxManager = new RxManager();
        rxManager.on("123123", new RxAction() {
            @Override
            protected void call(Object content) {
                Log.d(TAG, "MainActivity2 123 call: " + content);
            }
        });

        rxManager.on("321321", new RxAction() {
            @Override
            protected void call(Object content) {
                Log.d(TAG, "MainActivity2 321 call: " + content);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxManager.clear();
    }

    public void close1(View view) {

    }

    public void postMessage(View view) {
        rxManager.post("123123", "11111");

    }

    public void postMessage2(View view) {
        rxManager.post("321321", "22222");
    }
}